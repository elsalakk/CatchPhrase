package StarApps.guessIt;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import StarApps.guessIt.R;

public class GameMode extends Activity{
    private Button hotPotatoButton;
    private Button topScoreButton;
    private Button customButton;
    int backPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.game_mode);
        hotPotatoButton = (Button)findViewById(R.id.hotPotato);
        topScoreButton = (Button)findViewById(R.id.topScore);
        customButton = (Button)findViewById(R.id.custom);
    }

    public void custButton(View view) {
        Intent Custom = new Intent(this, Custom.class);
        startActivity(Custom);
        finish();
    }
    public void topScButton(View view) {
        Intent TopScore = new Intent(this, TopScore.class);
        startActivity(TopScore);
        finish();
    }
    public void hotPotatButton(View view) {
        Intent HotPotato = new Intent(this, HotPotato.class);
        startActivity(HotPotato);
        finish();
    }
    public void onBackPressed() {
        if (backPressed == 0) {
            backPressed++;
            String text = "Press back again to quit game!";
            Toast info = new Toast(this);
            info.makeText(this, text, Toast.LENGTH_LONG).show();
        } else {
            finish();
        }
    }

}