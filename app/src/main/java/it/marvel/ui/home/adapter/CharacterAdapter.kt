package it.marvel.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import it.marvel.R
import it.marvel.utils.loadImage
import it.marvel.network.entities.Character

typealias OnItemClick = (Character) -> Unit

class CharacterAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<CharacterAdapter.ItemViewHolder>() {

    private val characters: ArrayList<Character> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_character, parent, false),
            onItemClick
        )

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    fun addAll(chars: List<Character>) {
        clearAll()
        characters.addAll(chars)
        for (i in chars.indices) {
            notifyItemChanged(i)
        }
    }

    private fun clearAll() {
        characters.clear()
        notifyDataSetChanged()
    }

    class ItemViewHolder(
        private val view: View,
        private val onItemClick: OnItemClick
    ) : RecyclerView.ViewHolder(view) {

        private val cardView: CardView = view.findViewById(R.id.cardView)
        private val circleImage: CircleImageView = view.findViewById(R.id.circleImage)
        private val name: AppCompatTextView = view.findViewById(R.id.name)
        private val description: AppCompatTextView = view.findViewById(R.id.description)

        fun bind(character: Character) {

            character.imageUrl()?.let {
                circleImage.loadImage(view.context, it, R.drawable.ic_placeholder)
            }

            name.text = character.name
            description.text = character.description
            cardView.setOnClickListener { onItemClick.invoke(character) }
        }
    }
}
