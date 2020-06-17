package junseong.mvvm.movie_mvvm.moviedetail

import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable
import junseong.mvvm.movie_mvvm.data.VO.MovieDetails
import junseong.mvvm.movie_mvvm.data.api.TheMovieDBInterface
import junseong.mvvm.movie_mvvm.data.repository.MovieDetailNetworkDataSource
import junseong.mvvm.movie_mvvm.data.repository.NetworkState

class MovieDetailsRepository(private val apiService:TheMovieDBInterface) {
    lateinit var movieDetailNetworkDataSource: MovieDetailNetworkDataSource
    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable,movieId:Int):LiveData<MovieDetails>{
        movieDetailNetworkDataSource= MovieDetailNetworkDataSource(apiService, compositeDisposable)
        movieDetailNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailNetworkDataSource.downloadedMovieResponse
    }
    fun getMovieDetailNetworkState():LiveData<NetworkState>{
        return movieDetailNetworkDataSource.networkState
    }

}