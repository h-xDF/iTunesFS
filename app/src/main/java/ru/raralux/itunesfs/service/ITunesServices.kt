package ru.raralux.itunesfs.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.raralux.itunesfs.service.model.ResultAlbumModel
import ru.raralux.itunesfs.service.model.ResultSongModel

interface ITunesServices {

    @GET("/search")
    fun getAlbums(@QueryMap options: Map<String?, String?>?): Response<ResultAlbumModel?>

    @GET("/lookup")
    fun getTrack(@QueryMap options: Map<String?, String?>?): Response<ResultSongModel?>
}