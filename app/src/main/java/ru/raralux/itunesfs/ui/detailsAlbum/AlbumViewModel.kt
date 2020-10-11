package ru.raralux.itunesfs.ui.detailsAlbum

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.raralux.itunesfs.service.Event
import ru.raralux.itunesfs.service.ITunesServices
import ru.raralux.itunesfs.service.RetrofitClient
import ru.raralux.itunesfs.model.TrackModel

class AlbumViewModel(application: Application) :AndroidViewModel(application) {
    private val TAG = "RESPONSE"
    private val api: ITunesServices = RetrofitClient.getClient()

    val tracksLiveData :MutableLiveData<Event<MutableList<TrackModel>>> = MutableLiveData()

    fun getTracks(collectionId: String) {
        viewModelScope.launch {
            fetchTracks(collectionId)
        }
    }

    private suspend fun fetchTracks(collectionId: String) {
        tracksLiveData.postValue(Event.loading())

        val data = mapOf<String?, String?>("entity" to "song", "id" to collectionId)
        val response = api.getTrack(data)

        if (response.isSuccessful) {
            response.body()?.results?.let { resultResponse ->
                resultResponse.removeAt(0) // Первый объект - информация про альбом
                val event = Event.success(resultResponse)
                tracksLiveData.postValue(event)
            }
        } else {
            Log.d(TAG, "response is not successful: ${response.message()}")
        }
    }
}