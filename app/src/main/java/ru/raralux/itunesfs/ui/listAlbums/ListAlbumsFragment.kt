package ru.raralux.itunesfs.ui.listAlbums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.raralux.itunesfs.R
import ru.raralux.itunesfs.service.Event
import ru.raralux.itunesfs.service.Status
import ru.raralux.itunesfs.service.model.AlbumModel
import ru.raralux.itunesfs.ui.detailsAlbum.AlbumFragment

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
                        adapter.submitAlbumList(response.data?.results)
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
