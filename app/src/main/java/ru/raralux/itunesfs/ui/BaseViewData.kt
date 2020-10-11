package ru.raralux.itunesfs.ui

import androidx.lifecycle.ViewModel
import ru.raralux.itunesfs.service.ITunesServices
import ru.raralux.itunesfs.service.RetrofitClient

abstract class BaseViewData: ViewModel() {

    var api: ITunesServices = RetrofitClient.getClient()

//
//    fun <T> requestWithLiveData(
//        liveData: MutableLiveData<Event<T>>,
//        request: suspend () -> Response<MutableList<ResultAlbumModel>>) {
//
//        liveData.postValue(Event.loading())
//
//        this.viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = request.invoke()
//                if (response.isSuccessful) {
//                    liveData.postValue(Event.success(response))
//                } else if (response != null) {
//                    liveData.postValue(Event.error(response.error))
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                liveData.postValue(Event.error(null))
//            }
//        }
//    }
//
//    fun <T> requestWithCallback(
//        request: suspend () -> ResponseWrapper<T>,
//        response: (Event<T>) -> Unit) {
//
//        response(Event.loading())
//
//        this.viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val res = request.invoke()
//
//                launch(Dispatchers.Main) {
//                    if (res.data != null) {
//                        response(Event.success(res.data))
//                    } else if (res.error != null) {
//                        response(Event.error(res.error))
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                launch(Dispatchers.Main) {
//                    response(Event.error(null))
//                }
//            }
//        }
//    }
}