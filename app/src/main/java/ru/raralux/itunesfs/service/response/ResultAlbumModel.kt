package ru.raralux.itunesfs.service.response

import ru.raralux.itunesfs.model.AlbumModel

data class ResultAlbumModel(
    var resultCount: Int? = null,
    var results: MutableList<AlbumModel>? = null
)