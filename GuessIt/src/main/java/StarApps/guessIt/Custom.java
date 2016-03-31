package StarApps.guessIt;

import android.os.Bundle;

import StarApps.guessIt.R;

/**
 * Created by khale on 10/31/2015.
 */
public class Custom extends GuessIt{
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_guess_it_custom);
        super.onCreate(savedInstanceState);
        gameMode = 0;
    }
}
