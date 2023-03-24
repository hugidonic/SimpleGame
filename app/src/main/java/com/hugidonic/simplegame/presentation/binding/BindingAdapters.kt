package com.hugidonic.simplegame.presentation.binding

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.hugidonic.simplegame.R
import com.hugidonic.simplegame.domain.entity.GameResult


interface OnOptionClickListener {
	fun onOptionClick(option: Int)
}

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

@BindingAdapter("progressBarColor")
fun bindProgressBarColor(progressBar: ProgressBar, enoughPercent: Boolean) {
	progressBar.progressTintList = ColorStateList.valueOf(
		getColorByState(progressBar.context, enoughPercent)
	)
}
@BindingAdapter("answersProgressTextColor")
fun bindProgressBarColor(textView: TextView, enoughCount: Boolean) {
	textView.setTextColor(getColorByState(textView.context, enoughCount))
}

private fun getColorByState(context: Context, isEnough: Boolean): Int {
	val colorResId = if (isEnough) {
		android.R.color.holo_green_light
	} else  {
		android.R.color.holo_red_light
	}
	return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
	textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
	textView.setOnClickListener {
		clickListener.onOptionClick(textView.text.toString().toInt())
	}
}