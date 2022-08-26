package it.marvel.ui.home.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import de.hdodenhof.circleimageview.CircleImageView
import it.marvel.R
import it.marvel.network.entities.Character
import it.marvel.utils.Costants
import it.marvel.utils.loadImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val favoriteIcon: AppCompatImageView by lazy { findViewById(R.id.favoriteIcon) }
    private val shareIcon: AppCompatImageView by lazy { findViewById(R.id.shareIcon) }

    private val image: CircleImageView by lazy { findViewById(R.id.circleImage) }
    private val name: AppCompatTextView by lazy { findViewById(R.id.name) }

    private val infoBoxLayout: LinearLayout by lazy { findViewById(R.id.infoBoxLayout) }

    private val comicsLayout: ConstraintLayout by lazy { infoBoxLayout.findViewById(R.id.infoComicsLayout) }
    private val eventsLayout: ConstraintLayout by lazy { infoBoxLayout.findViewById(R.id.infoEventsLayout) }
    private val storiesLayout: ConstraintLayout by lazy { infoBoxLayout.findViewById(R.id.infoStoriesLayout) }
    private val seriesLayout: ConstraintLayout by lazy { infoBoxLayout.findViewById(R.id.infoSeriesLayout) }

    private val infoComics: AppCompatTextView by lazy { comicsLayout.findViewById(R.id.info) }
    private val infoEvents: AppCompatTextView by lazy { eventsLayout.findViewById(R.id.info) }
    private val infoStories: AppCompatTextView by lazy { storiesLayout.findViewById(R.id.info) }
    private val infoSeries: AppCompatTextView by lazy { seriesLayout.findViewById(R.id.info) }

    private val sizeComics: AppCompatTextView by lazy { comicsLayout.findViewById(R.id.size) }
    private val sizeSeries: AppCompatTextView by lazy { seriesLayout.findViewById(R.id.size) }
    private val sizeEvents: AppCompatTextView by lazy { eventsLayout.findViewById(R.id.size) }
    private val sizeStories: AppCompatTextView by lazy { storiesLayout.findViewById(R.id.size) }

    private val descriptionLabel: AppCompatTextView by lazy { findViewById(R.id.descriptionLabel) }
    private val description: AppCompatTextView by lazy { findViewById(R.id.description) }

    private val backButton: AppCompatButton by lazy { findViewById(R.id.backButton) }

    private val viewModel by viewModel<DetailViewModel>()

    companion object {
        fun start(context: Context, character: Character) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(Costants.CHARACTER_SELECTED, character)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val character = intent.extras?.get(Costants.CHARACTER_SELECTED) as Character
        viewModel.setCharacter(character)
        setValues()

        viewModel.character.observe(this) { setFavouriteIcon() }
    }

    private fun setFavouriteIcon() {
        favoriteIcon.apply {
            val background = if (viewModel.character.value?.isFavorite == true) {
                R.drawable.ic_favorite_white
            } else {
                R.drawable.ic_favorite
            }
            setImageDrawable(AppCompatResources.getDrawable(this@DetailActivity, background))
            setOnClickListener {
                val isFavorite = viewModel.getCurrentFavoriteValue()
                viewModel.updateFavorite(isFavorite)
                val textToShow = if (!isFavorite) "CHARACTER SET AS FAVORITE! \uD83D\uDE0E"
                else "CHARACTER REMOVED AS FAVORITE! \uD83D\uDE13"
                Toast.makeText(this@DetailActivity, textToShow, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setValues() {
        viewModel.character.value?.imageUrl()?.let {
            image.loadImage(this, it, R.drawable.ic_placeholder)
        }

        shareIcon.setOnClickListener {
            Toast.makeText(this@DetailActivity, "CLICK ON SHARE ICON!", Toast.LENGTH_SHORT)
                .show()
        }

        backButton.setOnClickListener { finish() }

        name.text = viewModel.character.value?.name.orEmpty()
        infoComics.text = getString(R.string.comics)
        sizeComics.text = viewModel.character.value?.comics?.items?.size.toString()

        infoSeries.text = getString(R.string.series)
        sizeSeries.text = viewModel.character.value?.series?.items?.size.toString()

        infoEvents.text = getString(R.string.events)
        sizeEvents.text = viewModel.character.value?.events?.items?.size.toString()

        infoStories.text = getString(R.string.stories)
        sizeStories.text = viewModel.character.value?.stories?.items?.size.toString()

        if (!viewModel.character.value?.description.isNullOrEmpty()) {
            description.text = viewModel.character.value?.description
        } else {
            descriptionLabel.text = getString(R.string.no_description_available)
        }
    }
}