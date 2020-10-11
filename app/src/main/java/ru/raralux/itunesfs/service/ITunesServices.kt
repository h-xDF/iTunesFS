package ru.raralux.itunesfs.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.raralux.itunesfs.service.response.ResultAlbumModel
import ru.raralux.itunesfs.service.response.ResultSongModel

interface ITunesServices {

    @GET("/search")
    suspend fun getAlbums(@QueryMap options: Map<String?, String?>?): Response<ResultAlbumModel?>

    @GET("/lookup")
    suspend fun getTrack(@QueryMap options: Map<String?, String?>?): Response<ResultSongModel?>
}