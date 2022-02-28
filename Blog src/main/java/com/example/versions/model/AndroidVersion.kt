package com.example.versions.model

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class AndroidVersion(@StringRes val stringResourceID: Int , @DrawableRes val imageResourceID: Int)
