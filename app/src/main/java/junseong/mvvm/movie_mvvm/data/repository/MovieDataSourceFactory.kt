package junseong.mvvm.movie_mvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import junseong.mvvm.movie_mvvm.data.VO.Movie
import junseong.mvvm.movie_mvvm.data.api.TheMovieDBInterface

class MovieDataSourceFactory(private val apiService:TheMovieDBInterface,private val compositeDisposable: CompositeDisposable) :DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource=MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource=MovieDataSource(apiService,compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}