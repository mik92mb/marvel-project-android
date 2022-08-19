package it.marvel.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import it.cincin.R
import it.cincin.extensions.onSafeClick
import it.cincin.extensions.openCustomChromeTab
import it.marvel.R

class CharacterAdapter(
    private val context: Context
) : RecyclerView.Adapter<CharacterAdapter.ItemViewHolder>() {

    private val libraries: ArrayList<Library> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.card_character, parent, false))

    override fun getItemCount() = libraries.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(libraries[position])
    }

    fun addAll(list: List<Library>) {
        libraries.addAll(list)
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val circleImage: CircleImageView = view.findViewById(R.id.circleImage)
        private val name: AppCompatTextView = view.findViewById(R.id.name)
        private val descrption: AppCompatTextView = view.findViewById(R.id.description)

        fun bind(library: Library) {
            with(view) {
                name.text = library.name
                descrption.text = library.description
                view.onSafeClick {
                    context.openCustomChromeTab(library.url)
                }
            }
        }
    }
}
