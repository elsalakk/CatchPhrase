package StarApps.guessIt;

import android.os.Bundle;

import StarApps.guessIt.R;

/**
 * Created by khaled on 10/31/2015.
 */
public class HotPotato extends GuessIt{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_guess_it_hot);
        super.onCreate(savedInstanceState);
        gameMode = 1;
        teamNumber = 1;

    }

}
