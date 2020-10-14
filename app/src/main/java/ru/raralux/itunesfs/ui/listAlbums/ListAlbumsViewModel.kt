package ru.raralux.itunesfs.ui.listAlbums

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.raralux.itunesfs.service.Event
import ru.raralux.itunesfs.service.ITunesServices
import ru.raralux.itunesfs.service.RetrofitClient
import ru.raralux.itunesfs.service.response.ResultAlbumModel
import ru.raralux.itunesfs.utils.ErrorUtils
import java.lang.Error

class ListAlbumsViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "RESPONSE"
    private var api: ITunesServices = RetrofitClient.getClient()

    val albumsLiveData :MutableLiveData<Event<ResultAlbumModel>> = MutableLiveData()

    fun getAlbum(searchString: String) {
        viewModelScope.launch {
            fetchAlbums(searchString)
        }
    }

    private suspend fun fetchAlbums(searchString: String) {
        Log.d(TAG, "searchString: $searchString")
        albumsLiveData.postValue(Event.loading())

        val data = mapOf<String?, String?>("entity" to "album", "term" to searchString)
        val response = api.getAlbums(data)

        if (response.isSuccessful) {
            if (response.body()?.resultCount == 0) {
                albumsLiveData.postValue(Event.empty(response.body()))
            } else {
                albumsLiveData.postValue(Event.success(response.body()))
            }
        } else {
            val error = ErrorUtils().parseError(response.errorBody())
            albumsLiveData.postValue(Event.error(error?.errorMessage))
            Log.e(TAG, "error code: ${response.code()}; error message: ${error?.errorMessage}")
        }
    }
}