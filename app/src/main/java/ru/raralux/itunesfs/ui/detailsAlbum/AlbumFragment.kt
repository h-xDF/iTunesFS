package ru.raralux.itunesfs.ui.detailsAlbum

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.raralux.itunesfs.R
import ru.raralux.itunesfs.service.Status
import ru.raralux.itunesfs.model.AlbumModel
import ru.raralux.itunesfs.model.TrackModel

class AlbumFragment : Fragment() {
    companion object {
        const val ALBUM = "ALBUM"
        fun newInstance() = AlbumFragment()
    }
    private val TAG = "IMAGE"

    private lateinit var viewModel: AlbumViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrackAdapter

    private var albumModel: AlbumModel? = null
    private var trackList: MutableList<TrackModel>? = null

    //view
    private lateinit var backBtn :ImageButton
    private lateinit var image :ImageView
    private lateinit var albumName :TextView
    private lateinit var artisteName :TextView
    private lateinit var genre :TextView
    private lateinit var progressBar: ProgressBar

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
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        findViews(view)
        bind()

        viewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
        viewModel.getTracks(albumModel?.collectionId.toString())
        viewModel.tracksLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    adapter.submitTrackList(response.data)
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun findViews(view: View) {
        recyclerView = view.findViewById(R.id.album_rv)
        backBtn = view.findViewById(R.id.album_back_btn)
        image = view.findViewById(R.id.album_iv)
        albumName = view.findViewById(R.id.album_album_name_tv)
        artisteName = view.findViewById(R.id.album_artist_name_tv)
        genre = view.findViewById(R.id.album_genre_date_tv)
        progressBar = view.findViewById(R.id.album_progress_bar)
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
        genre.text = mergeGenreYear(albumModel)
        val picsUrl = resizePictureUrl(200, 200, albumModel?.artworkUrl100)
        Log.d(TAG, "original image url: ${albumModel?.artworkUrl100}")
        Log.d(TAG, "image url: $picsUrl")
        Picasso.get()
            .load(picsUrl)
            .placeholder(R.drawable.ic_placholder_image)
            .error(R.drawable.ic_error_load_image)
            .into(image)
    }

    private fun mergeGenreYear(album : AlbumModel?): String {
        var year: String? = null
        if (album?.releaseDate != null) {
            year = album.releaseDate!!.split('-').first()
        }
        return "${album?.primaryGenreName?.toUpperCase()} · $year"
    }

    private fun resizePictureUrl(height: Int, width: Int, originUrl: String?): String? {
        val bufResString = StringBuffer()
        val urlSplit = originUrl?.split('/')
        val resizeRes = "${height}x${width}bb.${urlSplit?.last()?.split('.')?.last()}"
        for (i in 0..(urlSplit?.size?.minus(2)!!)) {
            bufResString.append(urlSplit[i]).append('/')
        }
        return bufResString.append(resizeRes).toString()
    }
}