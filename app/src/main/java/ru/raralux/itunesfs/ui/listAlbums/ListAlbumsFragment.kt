package ru.raralux.itunesfs.ui.listAlbums

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.raralux.itunesfs.R

class ListAlbumsFragment : Fragment() {

    companion object {
        fun newInstance() =
            ListAlbumsFragment()
    }

    private lateinit var viewModel: ListAlbumsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_albums_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListAlbumsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}