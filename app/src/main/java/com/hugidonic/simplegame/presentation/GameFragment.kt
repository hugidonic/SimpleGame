package com.hugidonic.simplegame.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hugidonic.simplegame.databinding.FragmentGameBinding
import com.hugidonic.simplegame.domain.entity.GameResult
import com.hugidonic.simplegame.domain.entity.Question

class GameFragment: Fragment() {
	//	Arguments
	private val args by navArgs<GameFragmentArgs>()

	//	View model factory
	private val viewModelFactory by lazy {
		GameViewModelFactory(
			requireActivity().application,
			args.level
		)
	}

	//	ViewModel
	private val viewModel by lazy {
		ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
	}

	private val tvOptions by lazy {
		mutableListOf<TextView>().apply {
			add(binding.tvOption1)
			add(binding.tvOption2)
			add(binding.tvOption3)
			add(binding.tvOption4)
			add(binding.tvOption5)
			add(binding.tvOption6)
		}
	}

	private var _binding: FragmentGameBinding? = null
	private val binding: FragmentGameBinding
		get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGameBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

//		Generate question
		viewModel.question.observe(viewLifecycleOwner) {
			initQuestion(it)
			initOptionListeners()
		}

//		Update timer via viewModel
		viewModel.formattedTime.observe(viewLifecycleOwner) {
			binding.tvTimer.text = it
		}

//		Observe progress of right answers
		viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
			binding.progressBar.setProgress(it, true)
		}

//		if percentage is enough it shines green else red
		viewModel.enoughCount.observe(viewLifecycleOwner) {
			binding.tvAnswersProgress.setTextColor(getColorByState(it))
		}

//		if percentage is enough it shines green else red
		viewModel.enoughPercent.observe(viewLifecycleOwner) {
			binding.progressBar.progressTintList = ColorStateList.valueOf(getColorByState(it))
		}

//		Secondary progress
		viewModel.minPercent.observe(viewLifecycleOwner) {
			binding.progressBar.secondaryProgress = it
		}

//		Ending the game and launch finish screen
		viewModel.gameResult.observe(viewLifecycleOwner) {
			launchGameFinishedFragment(it)
		}

		viewModel.progressAnswers.observe(viewLifecycleOwner) {
			binding.tvAnswersProgress.text = it
		}
	}

	private fun getColorByState(isEnough: Boolean): Int {
		val colorResId = if (isEnough) {
			android.R.color.holo_green_light
		} else  {
			android.R.color.holo_red_light
		}
		return ContextCompat.getColor(requireContext(), colorResId)
	}

	private fun initQuestion(question: Question) = with(binding) {
		tvSum.text = question.sum.toString()
		tvLeftNumber.text = question.visibleNumber.toString()
		for (i in 0 until tvOptions.size) {
			tvOptions[i].text = question.options[i].toString()
		}
	}

	private fun initOptionListeners() {
		for (i in 0 until tvOptions.size) {
			tvOptions[i].setOnClickListener {
				viewModel.chooseAnswer(tvOptions[i].text.toString().toInt())
			}
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun launchGameFinishedFragment(gameResult: GameResult) {
		findNavController().navigate(
			GameFragmentDirections
				.actionGameFragmentToGameFinishedFragment(gameResult)
		)
	}
}