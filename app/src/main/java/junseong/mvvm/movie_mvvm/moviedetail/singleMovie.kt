package junseong.mvvm.movie_mvvm.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import junseong.mvvm.movie_mvvm.R
import junseong.mvvm.movie_mvvm.data.VO.MovieDetails
import junseong.mvvm.movie_mvvm.data.api.Client
import junseong.mvvm.movie_mvvm.data.api.POSTER_BASE_URL
import junseong.mvvm.movie_mvvm.data.api.TheMovieDBInterface
import junseong.mvvm.movie_mvvm.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class singleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        val movieId:Int=intent.getIntExtra("id",1)
        val apiService:TheMovieDBInterface= Client.getClient()
        movieRepository= MovieDetailsRepository(apiService)

        viewModel=getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer { bindUI(it) })

        viewModel.networkState.observe(this, Observer {
            progress_Bar.visibility= if (it== NetworkState.LOADING) View.VISIBLE else View.GONE
            text_error.visibility= if(it== NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }
    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("uncheck")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
    fun bindUI(it:MovieDetails){
        movie_title.text=it.title
        movie_tagline.text=it.tagline
        movie_release_date.text=it.releaseDate
        movie_rating.text=it.rating.toString()
        if(it.overview.isEmpty())
            movie_overview.text="영화 개요가 등록되어 있지 않음"
        else
        movie_overview.text=it.overview
        movie_runtime.text=it.runtime.toString()+"분"
        val formatCurrency=NumberFormat.getCurrencyInstance(Locale.KOREA)
        movie_budget.text="예산"+formatCurrency.format(it.budget)+"원"
        movie_revenue.text="수익"+formatCurrency.format(it.revenue)+"원"
        val movieposter = POSTER_BASE_URL+it.posterPath
        Glide.with(this)
            .load(movieposter)
            .into(movie_poster)
    }
}
