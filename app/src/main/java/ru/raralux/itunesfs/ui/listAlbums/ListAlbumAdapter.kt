package ru.raralux.itunesfs.ui.listAlbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_item.view.*
import ru.raralux.itunesfs.R
import ru.raralux.itunesfs.model.AlbumModel
import ru.raralux.itunesfs.ui.detailsAlbum.AlbumFragment

class ListAlbumAdapter(private var albumList: MutableList<AlbumModel>?,
                       private val fragment: Fragment)
    : RecyclerView.Adapter<ListAlbumAdapter.ListAlbumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAlbumHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_item, parent, false)
        return ListAlbumHolder(itemView)
    }

    override fun getItemCount(): Int {
        return albumList?.size?:0
    }

    override fun onBindViewHolder(holder: ListAlbumHolder, position: Int) {
        val album = albumList?.get(position)
        holder?.bind(album)
        holder.itemView.setOnLongClickListener {
            val fragment: Fragment = AlbumFragment.newInstance()
            var arg: Bundle = Bundle()
            arg.putParcelable(AlbumFragment.ALBUM, album)
            fragment.arguments = arg
            this.fragment.parentFragmentManager
                .beginTransaction()
                .add(R.id.activity_main, fragment)
                .addToBackStack("ALBUM_FRAGMENT")
                .hide(this.fragment)
                .commit()
                //.replace(R.id.activity_main, fragment).commit()
            true
        }
    }

    fun submitAlbumList(data: MutableList<AlbumModel>?) {
        albumList = data
        notifyDataSetChanged()
    }

    class ListAlbumHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val image: ImageView? = itemView?.ai_ava_iv
        val albumName: TextView? = itemView?.ai_album_name_tv
        val artistName: TextView? = itemView?.ai_artist_name_tv
        val genre: TextView? = itemView?.ai_genre_name_date_tv

        fun bind(album: AlbumModel?) {
            Picasso.get().load(album?.artworkUrl100).into(image)

            albumName?.text = album?.collectionName
            artistName?.text = album?.artistName
            genre?.text = album?.primaryGenreName
        }
    }
}