package com.example.githubauthorization.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemHolder(val item: Item, val isFavorite: Boolean): Parcelable

