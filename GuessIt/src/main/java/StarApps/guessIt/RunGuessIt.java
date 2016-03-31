package StarApps.guessIt;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import StarApps.guessIt.R;

public class RunGuessIt {
    private int category;
    private String currScreenText;
    private int team1Score;
    private int team2Score;
    private CategoryManager categoryManager;
    private TextView screen;
    private TextView screenT1;
    private TextView screenT2;
    int variety;
    //##################################################################################startGame()
    RunGuessIt(final Context context, Activity activity) { //starting game session
        category = -1;
        variety = 0;
        categoryManager = new CategoryManager(context);
        team1Score = 0;
        team2Score = 0;
        screen = (TextView)activity.findViewById(R.id.screen);
        screenT1 = (TextView)activity.findViewById(R.id.team1_scoreboard);
        screenT2 = (TextView)activity.findViewById(R.id.team2_scoreboard);
        /*
        screenT1.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v) {
                resetScore();
                return true;
            }
        });
        screenT2.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                resetScore();
                return true;
            }
        });
        */
    }
    //##############################################################################setTeam1Score()
    public void setTeam1Score(int i){
        if(team1Score + i >= 0){
            team1Score+=i;
        }
        screenT1.setText(String.valueOf(team1Score));
    }
    //##############################################################################setTeam2Score()
    public void setTeam2Score(int i){
        if(team2Score + i >= 0){
            team2Score+=i;
        }
        screenT2.setText(String.valueOf(team2Score));
    }
    //#################################################################################nextPhrase()
    public void nextPhrase(){// on click of next button
        if(variety == 0){
            currScreenText = categoryManager.getPhrase();
            screen.setText(currScreenText);
        }
        else{
            nextCategory(variety);
            currScreenText = categoryManager.getPhrase();
            screen.setText(currScreenText);
        }
    }
    //#################################################################################nextPhrase()
    public void nextCategory(int variety) {// on click of next button
        if(variety == 0) {
            currScreenText = categoryManager.next();
            screen.setText(currScreenText);
            this.variety = variety;
        }
        else{
            this.variety = variety;
            categoryManager.next();
        }
    }
    //##############################################################################
    public void resetScore() {
        team1Score = 0;
        team2Score = 0;
        screenT2.setText(String.valueOf(team2Score));
        screenT1.setText(String.valueOf(team1Score));
    }
    public void setDifficulty(int difficulty){
        categoryManager.setDifficulty(difficulty);
    }
    public boolean loadUserCategories(String fileName){
        return categoryManager.loadMore(fileName);
    }
}
