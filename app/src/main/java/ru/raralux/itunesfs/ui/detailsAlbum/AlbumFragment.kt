package ru.raralux.itunesfs.ui.detailsAlbum

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.raralux.itunesfs.R
import ru.raralux.itunesfs.service.Status
import ru.raralux.itunesfs.service.model.AlbumModel
import ru.raralux.itunesfs.service.model.TrackModel
import ru.raralux.itunesfs.ui.listAlbums.ListAlbumAdapter
import ru.raralux.itunesfs.ui.listAlbums.ListAlbumsViewModel

class AlbumFragment : Fragment() {
    companion object {
        const val ALBUM = "ALBUM"
        fun newInstance() = AlbumFragment()
    }

    private lateinit var viewModel: AlbumViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrackAdapter

    private var albumModel: AlbumModel? = null
    private var trackList: MutableList<TrackModel>? = null

    //view
    private lateinit var backBtn :Button
    private lateinit var image :ImageView
    private lateinit var albumName :TextView
    private lateinit var artisteName :TextView
    private lateinit var genre :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            albumModel = it.getParcelable<AlbumModel?>(ALBUM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        findViews(view)
        bind()
    }

    private fun findViews(view: View) {
        recyclerView = view.findViewById(R.id.album_rv)
        backBtn = view.findViewById(R.id.album_back_btn)
        image = view.findViewById(R.id.album_iv)
        albumName = view.findViewById(R.id.album_album_name_tv)
        artisteName = view.findViewById(R.id.album_artist_name_tv)
        genre = view.findViewById(R.id.album_genre_date_tv)
    }

    private fun bind() {
        adapter = TrackAdapter(trackList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        backBtn.setOnClickListener {
            parentFragmentManager
                .popBackStack()
        }

        albumName.text = albumModel?.collectionName?:"N/A"
        artisteName.text = albumModel?.artistName?:"N/A"
        genre.text = albumModel?.primaryGenreName?:"N/A"
        Picasso.get().load(albumModel?.artworkUrl100).into(image)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AlbumViewModel::class.java)

        viewModel.getTracks(albumModel?.collectionId.toString())
        viewModel.tracksLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    adapter.submitTrackList(response.data?.results)
                }
            }
        })


    }
}