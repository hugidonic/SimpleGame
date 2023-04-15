package com.hugidonic.simplegame.presentation.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hugidonic.domain.entity.GameResult
import com.hugidonic.domain.entity.GameSettings
import com.hugidonic.domain.entity.Level
import com.hugidonic.domain.entity.Question
import com.hugidonic.domain.repository.GameRepository
import com.hugidonic.domain.usecases.GenerateQuestionUseCase
import com.hugidonic.domain.usecases.GetGameSettingsUseCase
import javax.inject.Inject

class GameViewModel (
	private val generateQuestionUseCase: GenerateQuestionUseCase,
	private val getGameSettingsUseCase: GetGameSettingsUseCase,
//	Assisted
	private val level: Level,
	private val progressAnswersString: String,
): ViewModel() {

//	Lateinit vars
	private lateinit var gameSettings: GameSettings

//	Timer
	private var timer: CountDownTimer? = null

//	LiveData
	private val _question = MutableLiveData<Question>()
	val question: LiveData<Question>
		get() = _question

	private val _formattedTime = MutableLiveData<String>()
	val formattedTime: LiveData<String>
		get() = _formattedTime

//	Current right answers progress
	private val _minPercent = MutableLiveData<Int>()
	val minPercent: LiveData<Int>
		get() = _minPercent

	private val _percentOfRightAnswers = MutableLiveData<Int>()
	val percentOfRightAnswers: LiveData<Int>
		get() = _percentOfRightAnswers

	private val _progressAnswers = MutableLiveData<String>()
	val progressAnswers: LiveData<String>
		get() = _progressAnswers

//	Are right answers enough
	private val _enoughCount = MutableLiveData<Boolean>()
	val enoughCount: LiveData<Boolean>
		get() = _enoughCount

	private val _enoughPercent = MutableLiveData<Boolean>()
	val enoughPercent: LiveData<Boolean>
		get() = _enoughPercent

//	Game result
	private val _gameResult = MutableLiveData<GameResult>()
	val gameResult: LiveData<GameResult>
		get() = _gameResult

	private var countOfRightAnswers = 0
	private var countOfQuestions = 0

	init {
		startGame()
	}

	private fun startGame() {
		getGameSettings()
		startTimer()
		generateQuestion()
		updateProgress()
	}

	fun chooseAnswer(number: Int) {
		checkForRightAnswer(number)
		updateProgress()
		generateQuestion()
	}

	private fun getGameSettings() {
		this.gameSettings = getGameSettingsUseCase(level)
		_minPercent.value = gameSettings.minPercentageOfRightAnswers
	}

	private fun updateProgress() {
		val percent = calculatePercent()
		_percentOfRightAnswers.value = percent
		_progressAnswers.value = String.format(
			progressAnswersString,
			countOfRightAnswers,
			gameSettings.minCountOfRightAnswers
		)

		_enoughCount.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswers
		_enoughPercent.value = percent >= gameSettings.minPercentageOfRightAnswers
	}

	private fun calculatePercent(): Int {
		if (countOfQuestions == 0) return 0
		return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
	}

	private fun checkForRightAnswer(number: Int) {
		val rightAnswer = question.value?.rightAnswer
		if (number == rightAnswer) {
			countOfRightAnswers++
		}
		countOfQuestions++
	}

	private fun startTimer() {
		timer = object : CountDownTimer(gameSettings.gameTimeInSeconds* MILLIS_IN_SECONDS, 1000) {
			override fun onTick(millisUntilFinished: Long) {
				_formattedTime.value = formatTime(millisUntilFinished)
			}

			override fun onFinish() {
				finishGame()
			}
		}
		timer?.start()
	}

	private fun formatTime(millisUntilFinished: Long): String {
		val seconds = millisUntilFinished / MILLIS_IN_SECONDS
		val minutes = seconds / SECONDS_IN_MINUTES
		val leftSeconds = seconds - (minutes * seconds)
		return String.format("%02d:%02d", minutes, leftSeconds)
	}

	private fun generateQuestion() {
		_question.value = generateQuestionUseCase(gameSettings.maxSumValue)
	}

	private fun finishGame() {
		_gameResult.value = GameResult(
			enoughCount.value == true
					&& enoughPercent.value == true,
			countOfRightAnswers,
			countOfQuestions,
			gameSettings
		)
	}

	override fun onCleared() {
		super.onCleared()
		timer?.cancel()
	}


	companion object {
		private const val MILLIS_IN_SECONDS = 1000L
		private const val SECONDS_IN_MINUTES = 60
	}
}