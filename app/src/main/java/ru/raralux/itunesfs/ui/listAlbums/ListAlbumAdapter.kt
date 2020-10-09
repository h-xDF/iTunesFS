package ru.raralux.itunesfs.ui.listAlbums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_item.view.*
import ru.raralux.itunesfs.R
import ru.raralux.itunesfs.service.model.AlbumModel

class ListAlbumAdapter(private val albumList: MutableList<AlbumModel>):
    RecyclerView.Adapter<ListAlbumAdapter.ListAlbumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAlbumHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_item, parent, false)
        return ListAlbumHolder(itemView)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ListAlbumHolder, position: Int) {
        val album = albumList[position]
        holder.bind(album)
    }

    class ListAlbumHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.ai_ava_iv
        val albumName: TextView = itemView.ai_album_name_tv
        val artistName: TextView = itemView.ai_artist_name_tv
        val genre: TextView = itemView.ai_genre_name_date_tv

        fun bind(album: AlbumModel) {
            Picasso.get().load(album.artworkUrl60).into(image)

            albumName.text = album.collectionName
            artistName.text = album.artistName
            genre.text = album.primaryGenreName
        }
    }
}