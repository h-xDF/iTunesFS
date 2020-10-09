package ru.raralux.itunesfs.service.model

data class ResultAlbumModel(
    var resultCount: Int? = null,
    var results: MutableList<AlbumModel>? = null
)