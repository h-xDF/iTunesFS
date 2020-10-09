package ru.raralux.itunesfs.service.model

data class ResultSongModel(
    var resultCount: Int? = null,
    var results: MutableList<TrackModel>? = null
)