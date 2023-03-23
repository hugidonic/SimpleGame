package com.hugidonic.simplegame.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
	val maxSumValue: Int,
	val minCountOfRightAnswers: Int,
	val minPercentageOfRightAnswers: Int,
	val gameTimeInSeconds: Int,
): Parcelable
