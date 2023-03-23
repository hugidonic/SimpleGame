package com.hugidonic.simplegame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.hugidonic.simplegame.R
import com.hugidonic.simplegame.databinding.FragmentGameFinishedBinding
import com.hugidonic.simplegame.domain.entity.GameResult

class GameFinishedFragment: Fragment() {

	private lateinit var gameResult: GameResult
	private var _binding: FragmentGameFinishedBinding? = null
	private val binding: FragmentGameFinishedBinding
		get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		parseArgs()
	}

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
		requireActivity().onBackPressedDispatcher
			.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
				override fun handleOnBackPressed() {
					retryGame()
				}
			})
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

	private fun parseArgs() {
		@Suppress("DEPRECATION")
		requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
			gameResult = it
		}
	}

	private fun retryGame() {
		requireActivity().supportFragmentManager.popBackStack(GameFragment.TAG, POP_BACK_STACK_INCLUSIVE)
	}

	companion object {
		private const val KEY_GAME_RESULT = "game_result"
		const val TAG = "GameFinishedFragment"

		@JvmStatic
		fun newInstance(gameResult: GameResult): Fragment {
			return GameFinishedFragment().apply {
				arguments = Bundle().apply {
					putParcelable(KEY_GAME_RESULT, gameResult)
				}
			}
		}
	}
}