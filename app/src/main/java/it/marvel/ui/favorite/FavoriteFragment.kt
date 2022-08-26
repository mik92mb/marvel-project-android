package it.marvel.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.marvel.R
import it.marvel.ui.adapter.CharacterAdapter
import it.marvel.ui.home.detail.DetailActivity
import it.marvel.utils.ItemOffsetDecoration
import it.marvel.utils.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val viewModel by viewModel<FavoriteViewModel>()

    private lateinit var textNoFavorite: AppCompatTextView
    private lateinit var recyclerFavoriteCharacters: RecyclerView
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textNoFavorite = view.findViewById(R.id.textNoFavorite)

        viewModel.favoriteCharacters.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                textNoFavorite.isVisible(true)
                recyclerFavoriteCharacters.isVisible(false)
            } else {
                textNoFavorite.isVisible(false)
                recyclerFavoriteCharacters.isVisible(true)
                characterAdapter.addAll(it)
            }
        }

        setRecyclerAdapter(view)
    }

    private fun setRecyclerAdapter(view: View) {
        characterAdapter =
            CharacterAdapter(requireContext()) { DetailActivity.start(requireContext(), it) }

        recyclerFavoriteCharacters = view.findViewById(R.id.recyclerFavoriteCharacters)
        recyclerFavoriteCharacters.apply {
            addItemDecoration(ItemOffsetDecoration(resources.getDimensionPixelSize(R.dimen.margin_10)))
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = characterAdapter
        }
    }
}