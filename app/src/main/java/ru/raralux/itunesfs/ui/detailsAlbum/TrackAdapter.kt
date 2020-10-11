package ru.raralux.itunesfs.ui.detailsAlbum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.song_item.view.*
import ru.raralux.itunesfs.R
import ru.raralux.itunesfs.model.TrackModel

class TrackAdapter(private var trackList: MutableList<TrackModel>?):
    RecyclerView.Adapter<TrackAdapter.TrackHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_item, parent, false)
        return TrackHolder(itemView)
    }

    override fun getItemCount(): Int {
        return trackList?.size?:0
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val track = trackList?.get(position)
        holder.bind(track)
    }

    fun submitTrackList(data :MutableList<TrackModel>?) {
        trackList = data
        notifyDataSetChanged()
    }

    class TrackHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val trackNumber = itemView?.si_song_number_tv
        val trackName = itemView?.si_song_name_tv
        val trackDuration = itemView?.si_time_tv

        fun bind(track: TrackModel?) {
            trackNumber?.text = track?.trackNumber.toString()
            trackName?.text = track?.trackName
            trackDuration?.text = transferTime(track?.trackTimeMillis)
        }

        /**
         * @param timeMillis duration in milliseconds
         * @return string template MM:SS
         */
        private fun transferTime(timeMillis: Long?): String {
            return if (timeMillis == null || timeMillis == 0L) {
                "00:00"
            } else {
                val minutes = timeMillis / 60000
                val seconds = (timeMillis - minutes * 60000) / 1000
                val buf = StringBuilder()

                if (minutes == 0L) {
                    buf.append("00:")
                } else {
                    buf.append("$minutes:")
                }

                when {
                    seconds == 0L -> buf.append("00")
                    seconds <= 9 -> buf.append("0$seconds")
                    else -> buf.append(seconds)
                }
                buf.toString()
            }
        }
    }
}