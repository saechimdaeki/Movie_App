package junseong.mvvm.movie_mvvm.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import junseong.mvvm.movie_mvvm.data.VO.MovieDetails
import junseong.mvvm.movie_mvvm.data.repository.NetworkState

class SingleMovieViewModel(private val movieDetailsRepository: MovieDetailsRepository,movieId:Int):ViewModel() {

    private val compositeDisposable=CompositeDisposable()

    val movieDetails:LiveData<MovieDetails> by lazy{
        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState: LiveData<NetworkState> by lazy{
        movieDetailsRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}