package StarApps.guessIt;

import android.os.Bundle;

import StarApps.guessIt.R;

/**
 * Created by khaled on 10/31/2015.
 */
public class TopScore extends GuessIt{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_guess_it);
        super.onCreate(savedInstanceState);
        gameMode = 2;
        teamNumber = 1;
    }
}
