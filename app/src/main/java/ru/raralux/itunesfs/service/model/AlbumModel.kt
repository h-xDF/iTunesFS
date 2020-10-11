package ru.raralux.itunesfs.service.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumModel(
    var wrapperType: String? = null,
    var collectionType: String? = null,
    var artistId: Int? = null,
    var collectionId: Int? = null,
    var artistName: String? = null,
    var collectionName: String? = null,
    var collectionCensoredName: String? = null,
    var artistViewUrl: String? = null,
    var collectionViewUrl: String? = null,
    var artworkUrl60: String? = null,
    var artworkUrl100: String? = null,
    var collectionPrice: Float? = null,
    var collectionExplicitness: String? = null,
    var contentAdvisoryRating: String? = null,
    var trackCount: Int? = null,
    var copyright: String? = null,
    var country: String? = null,
    var currency: String? = null,
    var releaseDate: String? = null,
    var primaryGenreName: String? = null
) : Parcelable