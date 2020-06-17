package junseong.mvvm.movie_mvvm.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import junseong.mvvm.movie_mvvm.data.VO.Movie
import junseong.mvvm.movie_mvvm.data.api.First_page
import junseong.mvvm.movie_mvvm.data.api.TheMovieDBInterface

class MovieDataSource(private val apiService :TheMovieDBInterface , private val compositeDisposable: CompositeDisposable) :PageKeyedDataSource<Int, Movie>(){

    private var page= First_page
    val networkState:MutableLiveData<NetworkState> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList,null,page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.d("테스트 initial",it.message)
                    }
                )

        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                       if(it.totalPages>= params.key){
                            callback.onResult(it.movieList,params.key+1)
                           networkState.postValue(NetworkState.LOADED)
                       }else{
                            networkState.postValue(NetworkState.End)
                       }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.d("테스트 loadAfter",it.message)
                    }
                )

        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //
    }

}