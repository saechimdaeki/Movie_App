package junseong.mvvm.movie_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    //TODO target url example1:   https://developers.themoviedb.org/3/movies/get-movie-details
    //TODO target url example2: https://developers.themoviedb.org/3/movies/get-popular-movies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
