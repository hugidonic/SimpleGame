package com.hugidonic.simplegame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hugidonic.simplegame.databinding.FragmentGameBinding
import com.hugidonic.simplegame.domain.entity.GameResult

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

		binding.viewModel = viewModel
		binding.lifecycleOwner = viewLifecycleOwner

//		Ending the game and launch finish screen
		viewModel.gameResult.observe(viewLifecycleOwner) {
			launchGameFinishedFragment(it)
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