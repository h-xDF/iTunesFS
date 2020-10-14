package ru.raralux.itunesfs.service

data class Event<out T>(val status: Status, val data: T?, val error: String?) {
    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> empty(data: T?): Event<T> {
            return Event(Status.EMPTY_DATA, data, null)
        }

        fun <T> error(error: String?): Event<T> {
            return Event(Status.ERROR, null, error)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    EMPTY_DATA,
    LOADING
}