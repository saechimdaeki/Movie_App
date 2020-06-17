package junseong.mvvm.movie_mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import junseong.mvvm.movie_mvvm.moviedetail.singleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //TODO target url example1:   https://developers.themoviedb.org/3/movies/get-movie-details
    //TODO target url example2: https://developers.themoviedb.org/3/movies/get-popular-movies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener{
            val intent= Intent(this,singleMovie::class.java)
            intent.putExtra("id",299534)
            //id 값 419704 옆집누나의맛 (개요가 없는 id값 이기에 memo) 다른의도 x
            this.startActivity(intent)
        }
    }
}
