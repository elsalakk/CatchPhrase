package StarApps.guessIt;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import StarApps.guessIt.R;

public class StartScreen extends Activity{
    private Button next;
    private Button skip;
    private String[] instructions= {"To begin a round, press the " +
            "GO! button. A word from the chosen category will " +
            "appear on the screen. You can press the Next button to show the new word. " +
            "Now you need to get your team to guess that word by giving them clues. " +
            "You can make any physical gesture and give almost any verbal clue to " +
            "get your team to say the phrase. But you CANNOT:\n" +
            "• Say a word that RHYMES with any part of the phrase\n" +
            "• Give the FIRST LETTER of the word\n" +
            "• SAY A PART OF THE WORD in the clue (i.e. “shoe” for “shoe horn”)\n",
            "If the other team catches you committing any of these no-no’s you " +
            "must press the STOP! button to shut the timer off. The other team automatically " +
            "gets one point for that round. " +
            "As soon as your team has guessed the word, hand the phone to a " +
            "member of the other team. Play continues with the new team pressing " +
            "the Next button to reveal the next word. The round ends when the music " +
            "stops and the timer runs out","Time is up. The team NOT holding the game unit " +
            "gets 1 point. That team also has one turn to guess the word that the losing " +
            "team did not get. If they guess correctly, they get 1 BONUS point (scoring a " +
            "total of 2 points for the round).","You must wait until the timer has run out, or is stopped, before you can " +
            "enter points(Unenforced in the case that you want to score a point for each successful guess). "+
            "First team to 7 points wins. You can tap the scoreboard to decrement scores, or hold to reset. Holding the category button will "+
            "set variety mode on. Enjoy!!"};
    private int instr;
    private TextView instructionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.start_screen);
        skip = (Button)findViewById(R.id.skip_instructions);
        next = (Button)findViewById(R.id.next_instruction);
        instructionText = (TextView)findViewById(R.id.instructions);
        instr = 0;
    }

    public void skipButton(View view) {
        Intent gameMode = new Intent(this, GameMode.class);
        startActivity(gameMode);
        finish();
    }
    public void nextButton(View view) {
        if(instr<=3){
            instructionText.setText(instructions[instr]);
            instr++;
        }
        else{
            Intent gameMode = new Intent(this, GameMode.class);
            startActivity(gameMode);
            finish();
        }
    }
}