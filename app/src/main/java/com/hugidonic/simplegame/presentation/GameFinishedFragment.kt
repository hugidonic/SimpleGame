package com.hugidonic.simplegame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hugidonic.simplegame.R
import com.hugidonic.simplegame.databinding.FragmentGameFinishedBinding

class GameFinishedFragment: Fragment() {

	private val args by navArgs<GameFinishedFragmentArgs>()

	private val gameResult by lazy {
		args.gameResult
	}

	private var _binding: FragmentGameFinishedBinding? = null
	private val binding: FragmentGameFinishedBinding
		get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		setupClickListeners()
		setTextValues()
		setSmileImage()
	}

	private fun setupClickListeners() {
		binding.buttonRetry.setOnClickListener { retryGame() }
	}

	private fun setSmileImage() = with(binding) {
		val smileImageId = if (gameResult.isWinner) {
			R.drawable.ic_smile
		} else {
			R.drawable.ic_sad
		}
		emojiResult.setImageResource(smileImageId)
	}

	private fun setTextValues() = with(binding) {
		tvRequiredAnswers.text = String.format(
			getString(R.string.required_score),
			gameResult.gameSettings.minCountOfRightAnswers
		)
		tvScoreAnswers.text = String.format(
			getString(R.string.score_answers),
			gameResult.countOfRightAnswers
		)
		tvRequiredPercentage.text = String.format(
			getString(R.string.required_percentage),
			gameResult.gameSettings.minPercentageOfRightAnswers
		)
		tvScorePercentage.text = String.format(
			getString(R.string.score_percentage),
			getPercentOfRightAnswers()
		)
	}

	private fun getPercentOfRightAnswers(): Int {
		return ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100)
			.toInt()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun retryGame() {
		findNavController().popBackStack()
	}
}