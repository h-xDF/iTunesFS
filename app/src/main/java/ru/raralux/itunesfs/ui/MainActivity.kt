package ru.raralux.itunesfs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.raralux.itunesfs.R
import ru.raralux.itunesfs.ui.listAlbums.ListAlbumsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val listAlbumFragment: Fragment = ListAlbumsFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main, listAlbumFragment)
                .addToBackStack("LIST_ALBUM")
                .commit()
        }
    }
}