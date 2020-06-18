package junseong.mvvm.movie_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import junseong.mvvm.movie_mvvm.data.VO.Movie
import junseong.mvvm.movie_mvvm.data.repository.NetworkState

class MainViewModel (private val movieRepository: MoviePageListRepository):ViewModel() {
    private val compositeDisposable=CompositeDisposable()
    val moviePageList:LiveData<PagedList<Movie>> by lazy{
        movieRepository.FetchLiveMoviePagedList(compositeDisposable)
    }
    val networkState:LiveData<NetworkState> by lazy{
        movieRepository.getNetworkState()
    }
    fun listisempty():Boolean{
      return moviePageList.value?.isEmpty() ?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}