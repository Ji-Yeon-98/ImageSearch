package com.example.imagesearch.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageDataModel(
    val img: String,
    val title: String,
    val dateTime: String,
    var heart: Boolean
): Parcelable