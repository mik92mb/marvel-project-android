package it.marvel.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.marvel.R
import timber.log.Timber
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {

    private lateinit var recyclerWineSearched: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews(view)
        setSearchView()
        setRecyclerAdapter()
    }

    private fun setViews(view: View) {
        with(view) {
            recyclerWineSearched = findViewById(R.id.recyclerCharacters)
            searchView = findViewById(R.id.searchView)
        }
    }

    private fun setRecyclerAdapter() {
        recyclerWineSearched.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        // recyclerWineSearched.adapter = wineAdapter
    }

    private fun setSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ada
            }

        })
    }
}