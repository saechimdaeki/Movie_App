package junseong.mvvm.movie_mvvm.data.repository

enum class Status{
    Running,
    SUCCESS,
    Failed
}
class NetworkState(val status: Status, val message:String) {
    companion object{
        val LOADED:NetworkState
        val LOADING:NetworkState
        val ERROR:NetworkState
        init{
            LOADED = NetworkState(Status.SUCCESS ,"Success")
            LOADING = NetworkState(Status.Running,"Running")
            ERROR = NetworkState(Status.Failed,"ERROR")
        }
    }
}