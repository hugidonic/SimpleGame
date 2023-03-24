package com.hugidonic.simplegame.presentation.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hugidonic.simplegame.R
import com.hugidonic.simplegame.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
	textView.text = String.format(
		textView.context.getString(R.string.required_score),
		count
	)
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, countOfRightAnswers: Int) {
	textView.text = String.format(
		textView.context.getString(R.string.score_answers),
		countOfRightAnswers
	)
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percent: Int) {
	textView.text = String.format(
		textView.context.getString(R.string.required_percentage),
		percent
	)
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
	textView.text = String.format(
		textView.context.getString(R.string.score_percentage),
		getPercentOfRightAnswers(gameResult)
	)
}

private fun getPercentOfRightAnswers(gameResult: GameResult): Int {
	return if (gameResult.countOfQuestions == 0) {
		0
	} else {
		((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble())
				* 100).toInt()
	}
}

@BindingAdapter("emojiResult")
fun bindEmojiResult(imageView: ImageView, isWinner: Boolean) {
	val smileImageId = if (isWinner) {
		R.drawable.ic_smile
	} else {
		R.drawable.ic_sad
	}
	imageView.setImageResource(smileImageId)
}
