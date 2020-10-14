package ru.raralux.itunesfs.ui.detailsAlbum

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Converter
import ru.raralux.itunesfs.service.Event
import ru.raralux.itunesfs.service.ITunesServices
import ru.raralux.itunesfs.service.RetrofitClient
import ru.raralux.itunesfs.model.TrackModel
import ru.raralux.itunesfs.service.response.ErrorResponse
import ru.raralux.itunesfs.utils.ErrorUtils

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

        Log.d(TAG, "status code: ${response.code()}")
        if (response.isSuccessful) {
            response.body()?.results?.let { resultResponse ->
                resultResponse.removeAt(0) // Первый объект - информация про альбом
                if (resultResponse.size == 0) {
                    val event = Event.empty(resultResponse)
                    tracksLiveData.postValue(event)
                } else{
                    val event = Event.success(resultResponse)
                    tracksLiveData.postValue(event)
                }
            }
        } else {
            val error = ErrorUtils().parseError(response.errorBody())
            tracksLiveData.postValue(Event.error(error?.errorMessage))
            Log.e(TAG, "error code: ${response.code()}; error message: ${error?.errorMessage}")
        }
    }
}