package com.hugidonic.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level: Parcelable {
	TEST, EASY, NORMAL, HARD
}