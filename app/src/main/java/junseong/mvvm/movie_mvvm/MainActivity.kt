package junseong.mvvm.movie_mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import junseong.mvvm.movie_mvvm.adpter.MoviePageListAdapter
import junseong.mvvm.movie_mvvm.data.api.Client
import junseong.mvvm.movie_mvvm.data.api.TheMovieDBInterface
import junseong.mvvm.movie_mvvm.data.repository.NetworkState
import junseong.mvvm.movie_mvvm.moviedetail.singleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var movieRepository:MoviePageListRepository
    //TODO target url example1:   https://developers.themoviedb.org/3/movies/get-movie-details
    //TODO target url example2: https://developers.themoviedb.org/3/movies/get-popular-movies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService:TheMovieDBInterface= Client.getClient()
        movieRepository= MoviePageListRepository(apiService)

        viewModel=getViewModel()

        val movieAdapter=MoviePageListAdapter(this)

        val gridLayoutManager=GridLayoutManager(this,2)
        gridLayoutManager.spanSizeLookup=object :GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType:Int = movieAdapter.getItemViewType(position)
                return if(viewType==movieAdapter.MOVIE_VIEW_TYPE)
                    1
                else
                    3
            }
        }
        movie_list.layoutManager=gridLayoutManager
        movie_list.setHasFixedSize(true)
        movie_list.adapter=movieAdapter

        viewModel.moviePageList.observe(this, Observer {
            movieAdapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility=if(viewModel.listisempty()&& it== NetworkState.LOADING)
                View.VISIBLE
            else
                View.GONE
            text_error_popular.visibility=if(viewModel.listisempty()&& it==NetworkState.ERROR)
                View.VISIBLE
            else
                View.GONE
            if(!viewModel.listisempty())
            {
                movieAdapter.setNetworkState(it)
            }

        })
    }
    private fun getViewModel():MainViewModel{
        return ViewModelProviders.of(this,object:ViewModelProvider.Factory{
            override fun <T:ViewModel?> create(modelClass: Class<T>):T{
                @Suppress("Uncheck")
                return MainViewModel(movieRepository) as T
            }
        })[MainViewModel::class.java]
    }
}
