package junseong.mvvm.movie_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import junseong.mvvm.movie_mvvm.data.VO.Movie
import junseong.mvvm.movie_mvvm.data.api.TheMovieDBInterface
import junseong.mvvm.movie_mvvm.data.api.post_per_page
import junseong.mvvm.movie_mvvm.data.repository.MovieDataSource
import junseong.mvvm.movie_mvvm.data.repository.MovieDataSourceFactory
import junseong.mvvm.movie_mvvm.data.repository.NetworkState

class MoviePageListRepository (private val apiService:TheMovieDBInterface){
    lateinit var moviePagedList:LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory:MovieDataSourceFactory

    fun FetchLiveMoviePagedList(compositeDisposable: CompositeDisposable):LiveData<PagedList<Movie>>{
        movieDataSourceFactory= MovieDataSourceFactory(apiService, compositeDisposable)
        val config:PagedList.Config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(post_per_page)
            .build()

        moviePagedList=LivePagedListBuilder(movieDataSourceFactory,config).build()
        return moviePagedList
    }
    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource,NetworkState>(
            movieDataSourceFactory.movieLiveDataSource,MovieDataSource::networkState
        )
    }
}