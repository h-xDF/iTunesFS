package ru.raralux.itunesfs.service.response

import ru.raralux.itunesfs.model.TrackModel

data class ResultSongModel(
    var resultCount: Int? = null,
    var results: MutableList<TrackModel>? = null
)