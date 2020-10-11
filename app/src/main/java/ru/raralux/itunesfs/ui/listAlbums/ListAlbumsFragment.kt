package ru.raralux.itunesfs.ui.listAlbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.raralux.itunesfs.R
import ru.raralux.itunesfs.service.Status
import ru.raralux.itunesfs.model.AlbumModel

class ListAlbumsFragment : Fragment() {

    companion object {
        fun newInstance() = ListAlbumsFragment()
    }

    private lateinit var viewModel: ListAlbumsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAlbumAdapter

    private var albumList: MutableList<AlbumModel>? = null

    // view
    private lateinit var searchBtn: Button
    private lateinit var searchString: EditText
    private lateinit var infoText: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_albums_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchBtn = view!!.findViewById(R.id.search_btn)
        viewModel = ViewModelProviders.of(this).get(ListAlbumsViewModel::class.java)

        searchBtn.setOnClickListener {
            val search = searchString.text.toString()
            viewModel.getAlbum(search)
            viewModel.albumsLiveData.observe(viewLifecycleOwner, Observer {response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        infoText.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        adapter.submitAlbumList(response.data?.results)
                    }
                    Status.EMPTY_DATA -> {
                        progressBar.visibility = View.GONE
                        adapter.submitAlbumList(response.data?.results)
                        infoText.text = "Nothing found..."
                        infoText.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        infoText.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        findViews(view)
        bind()
    }

    private fun findViews(view: View) {
        searchString = view.findViewById(R.id.search_et)
        recyclerView = view.findViewById(R.id.albums_rv)
        searchBtn = view.findViewById(R.id.search_btn)
        infoText = view.findViewById(R.id.la_info_tv)
        progressBar = view.findViewById(R.id.la_progress_bar)
    }

    private fun bind() {
        adapter = ListAlbumAdapter(albumList, this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter


//            .setOnClickListener(this)
//        if (albumList.isEmpty()) com.example.itunesforasoft.ui.ListFragment.setSearchInfo(com.example.itunesforasoft.ui.ListFragment.InfoText.START) else com.example.itunesforasoft.ui.ListFragment.setSearchInfo(
//            com.example.itunesforasoft.ui.ListFragment.InfoText.EMPTY
//        )
    }
//
//    fun onAlbumClickListener(album: AlbumModel): Boolean {
//        val fragment: Fragment = AlbumFragment.newInstance()
//        this.parentFragmentManager.beginTransaction().replace(R.id.activity_main, fragment).commit()
//        return true
//    }


}
