package junseong.mvvm.movie_mvvm.data.api

import io.reactivex.Single
import junseong.mvvm.movie_mvvm.data.VO.MovieDetails
import junseong.mvvm.movie_mvvm.data.VO.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page :Int) :Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}