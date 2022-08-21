package it.marvel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.marvel.R
import it.marvel.isVisible
import it.marvel.model.CharacterDataContainer
import it.marvel.network.StateObserver

class HomeFragment : Fragment(), StateObserver {

    override val lifecycleOwner: LifecycleOwner
        get() = this

    private lateinit var recyclerCharacters: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var loadingLayout: ConstraintLayout
    private lateinit var errorLayout: ConstraintLayout
    private lateinit var errorTitle: AppCompatTextView
    private lateinit var errorDescription: AppCompatTextView

    private var characterDataContainer: CharacterDataContainer? = null

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCharacters()

        viewModel.state.observe(
            loading = { loadingState() },
            success = {
                characterDataContainer = it.data
                successState()
                characterAdapter.addAll(characterDataContainer?.results.orEmpty())
            },
            error = { errorState(it) }
        )

        setViews(view)
        setSearchView()
        setRecyclerAdapter()
    }

    private fun setViews(view: View) {
        with(view) {
            recyclerCharacters = findViewById(R.id.recyclerCharacters)
            searchView = findViewById(R.id.searchView)
            loadingLayout = findViewById(R.id.loadingLayout)
            nestedScrollView = findViewById(R.id.nestedScrollView)
            errorLayout = findViewById(R.id.errorLayout)
            errorTitle = findViewById(R.id.title)
            errorDescription = findViewById(R.id.description)
        }
    }

    private fun setRecyclerAdapter() {
        characterAdapter =
            CharacterAdapter(requireContext()) { DetailActivity.start(requireContext(), it) }
        recyclerCharacters.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerCharacters.adapter = characterAdapter
    }

    private fun setSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query.isBlank()) {
                    characterAdapter.clearAll()
                    characterAdapter.addAll(characterDataContainer?.results.orEmpty())
                    true
                } else {
                    val filteredCharacters = characterDataContainer?.results?.filter {
                        (it.name?.contains(query, true) ?: false ||
                                it.description?.contains(query, true) ?: false)
                    }
                    characterAdapter.clearAll()
                    characterAdapter.addAll(filteredCharacters.orEmpty())
                    true
                }
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isBlank()) {
                    characterAdapter.clearAll()
                    characterAdapter.addAll(characterDataContainer?.results.orEmpty())
                    return true
                }
                return true
            }
        })
    }

    private fun loadingState() {
        loadingLayout.isVisible(true)
        nestedScrollView.isVisible(false)
        errorLayout.isVisible(false)
    }

    private fun successState() {
        loadingLayout.isVisible(false)
        nestedScrollView.isVisible(true)
        errorLayout.isVisible(false)
    }

    private fun errorState(throwable: Throwable) {
        loadingLayout.isVisible(false)
        nestedScrollView.isVisible(false)
        errorLayout.isVisible(true)
        errorTitle.text = getString(R.string.ops)
        errorDescription.text = throwable.message.orEmpty()
    }
}