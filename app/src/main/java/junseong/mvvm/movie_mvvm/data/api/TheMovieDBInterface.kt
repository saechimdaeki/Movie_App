package junseong.mvvm.movie_mvvm.data.api

import io.reactivex.Single
import junseong.mvvm.movie_mvvm.data.VO.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBInterface {
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}