package junseong.mvvm.movie_mvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import junseong.mvvm.movie_mvvm.data.VO.MovieDetails
import junseong.mvvm.movie_mvvm.data.api.TheMovieDBInterface
import java.lang.Exception

class MovieDetailNetworkDataSource(private val apiService: TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState:LiveData<NetworkState>
         get() = _networkState

    private val _downloadMovieDetailResponse=MutableLiveData<MovieDetails>()
    val downloadedMovieResponse : LiveData<MovieDetails>
        get() = _downloadMovieDetailResponse

    fun fetchMovieDetails(movieId:Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadMovieDetailResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("로그;;", it.message)
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("에러",e.message)
        }
    }


}