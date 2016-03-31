package StarApps.guessIt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import StarApps.guessIt.R;


public class GuessIt extends Activity {
    InterstitialAd mInterstitialAd;
    protected RunGuessIt game;
    protected SoundPool sp;
    protected int nextSound;
    protected int t1Sound;
    protected int t2Sound;
    protected int catSound;
    protected int skipSound;
    protected int buzzerSound;
    protected TextView timerScreen;
    protected TextView screen;
    protected MediaPlayer goSound;
    protected int categorySelected;
    protected CountDownTimer timer;
    protected Uri goMusic;
    int variety = 0;
    int backPressed;
    int teamNumber = 0;
    int gameMode;
    protected int skipsAllowed = 2;
    protected int skips = 0;
    protected Button nextButton, goButton,team1Button, team2Button, categoryButton,optButton, skipButton, restButton;
    @Override
    //###################################################################################onCreate()
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(game == null){
            File saveData = new File(Environment.getExternalStorageDirectory().getPath() + "/guessIt/saveData.txt");
            File guessItFolder = new File(Environment.getExternalStorageDirectory().getPath(), "guessIt");
            if(!guessItFolder.exists()){
                guessItFolder.mkdir();
            }
            screen = (TextView) findViewById(R.id.screen);
            variety = 0;
            backPressed= 0;
            nextButton = (Button) findViewById(R.id.next_button);
            skipButton = (Button) findViewById(R.id.skip_button);
            goButton = (Button) findViewById(R.id.go_button);
            team1Button = (Button) findViewById(R.id.team1_button);
            team2Button = (Button) findViewById(R.id.team2_button);
            optButton = (Button) findViewById(R.id.opt_button);
            restButton = (Button) findViewById(R.id.reset_button);
            timerScreen = (TextView)findViewById(R.id.timer);
            categoryButton = (Button) findViewById(R.id.category_button);
            categorySelected = 0;
            goMusic = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.one);
            goSound = MediaPlayer.create(this, goMusic);
            categoryButton.setOnLongClickListener(new View.OnLongClickListener(){
                public boolean onLongClick(View v) {
                    if(!goSound.isPlaying()) {
                        categorySelected = 1;
                        variety = 1;
                        categorySelected=1;
                        screen.setText("Variety");
                        game.nextCategory(variety);
                        variety = 0;
                        sp.play(catSound, 1, 1, 0, 0, 1);
                    }
                    return true;
                }
            });
            game = new RunGuessIt(getApplicationContext(), this );
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            nextSound = sp.load(this,R.raw.next, 1);
            t1Sound = sp.load(this,R.raw.t1, 1);
            t2Sound = sp.load(this, R.raw.t2, 1);
            catSound = sp.load(this, R.raw.categ, 1);
            buzzerSound = sp.load(this, R.raw.airhorn, 1);
            skipSound = sp.load(this, R.raw.skip, 1);
            goSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mp) {
                    onGoSoundComplete();
                }
            });
            loadSaveState();
        }
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(" ca-app-pub-5782977969817126/7261798896");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                settings();
            }
        });

        requestNewInterstitial();

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2C1257A26062F16E")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(goSound.isPlaying()){
            goSound.stop();
            goSound = MediaPlayer.create(this,goMusic);
            timer.cancel();
            goButton.setText("GO!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void team2Button(View view) {
        game.setTeam2Score(1);
        sp.play(t2Sound, 1, 1, 0, 0, 1);
    }
    public void team1Button(View view) {
        game. setTeam1Score(1);
        sp.play(t1Sound, 1, 1, 0, 0, 1);
    }
    public void nextButton(View view) {
        if(categorySelected == 1 && goSound.isPlaying()) {
            game.nextPhrase();
            sp.play(nextSound, 1, 1, 0, 0, 1);
            if(gameMode == 2){
                if(teamNumber ==1){
                    game.setTeam1Score(1);
                }
                else if(teamNumber == 2 ){
                    game.setTeam2Score(1);
                }
            }
        }
        else if(categorySelected == 0){
            infoCategory();
        }
        else{
            infoGo();
        }
        if(gameMode == 1) {
            if (teamNumber == 1) {
                teamNumber = 2;
            } else {
                teamNumber = 1;
            }
        }
    }
    public void goButton(View view) {
        skips = 0;
        if(categorySelected == 1) {
            if (goSound.isPlaying()) {
                goButton.setText("GO!");
                screen.setTextColor(Color.parseColor("#3db2f5"));
                goSound.stop();
                goSound = MediaPlayer.create(this,goMusic);
                goSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        onGoSoundComplete();
                    }
                });
                timer.cancel();
                if(teamNumber == 1){
                    teamNumber = 2;
                }
                else{
                    teamNumber = 1;
                }
            } else {
                goSound.start();
                game.nextPhrase();
                screen.setTextColor(Color.parseColor("#f6c02e"));
                goButton.setText("STOP!");
                System.out.println(goSound.getDuration());
                timer = new CountDownTimer(goSound.getDuration()+1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timerScreen.setGravity(Gravity.CENTER);
                        timerScreen.setText("Time: " + (millisUntilFinished-1000)/1000);
                        if((millisUntilFinished-1000)/1000 == 0){
                            sp.play(buzzerSound, 1, 1, 0, 0, 1);
                        }
                    }

                    @Override
                    public void onFinish() {
                        goButton.setText("GO!");
                        if(gameMode == 1){
                            if(teamNumber == 1){
                                game.setTeam2Score(1);
                            }
                            else if(teamNumber == 2){
                                game.setTeam1Score(1);
                            }

                        }
                        else if(gameMode == 2){
                            if(teamNumber ==1){
                                teamNumber = 2;
                            }
                            else if(teamNumber == 2 ){
                                teamNumber = 1;
                            }
                        }
                        skips = 0;
                    }
                }.start();
            }
        }
        else{
            infoCategory();
        }
    }
    public void infoGo(){
        String text = "Please press the go button first.";
        Toast info = new Toast(this);
        info.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void infoCategory(){
        String text = "Please select a category first.";
        Toast info = new Toast(this);
        info.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void categoryButton(View view) {
        if(!goSound.isPlaying()){
            screen.setTextColor(Color.parseColor("#ff7575"));
            categorySelected = 1;
            game.nextCategory(variety);// variety is always 0 here
            sp.play(catSound, 1, 1, 0, 0, 1);
        }
        else{
            String text = "Can not change the category while the game is running.";
            Toast info = new Toast(this);
            info.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }
    public void reset(View view) {
        goButton.setText("GO!");
        screen.setTextColor(Color.parseColor("#3db2f5"));
        screen.setText("Hello!");
        goSound.stop();
        goSound = MediaPlayer.create(this,goMusic);
        goSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onGoSoundComplete();
            }
        });
        timer.cancel();
        game.resetScore();
        timerScreen.setText("Time: 000");
        teamNumber =1;
    }
    public void skipButton(View view) {
        if(categorySelected == 1 && goSound.isPlaying()){
            sp.play(skipSound, 1, 1, 0, 0, 1);
            game.nextPhrase();
            if(skips >= skipsAllowed){
                if(teamNumber == 1){
                    decT1(view);
                }
                else if(teamNumber == 2){
                    decT2(view);
                }
            }
            else{
                skips++;
            }
        }
        else if(categorySelected == 0){
            infoCategory();
        }
        else{
            infoGo();
        }

    }
    public void decT1(View view) {
        game. setTeam1Score(-1);
    }
    public void decT2(View view) {
        game. setTeam2Score(-1);
    }

    public void options(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            settings();
        }
    }
    public void settings(){
        Intent getOptions = new Intent(this,Options.class);
        final int result = 1;
        backPressed = 0;
        startActivityForResult(getOptions, result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int difficulty;
        int soundLength;
        String dictionaryDir;
        String goSoundDir;
        if(resultCode == RESULT_OK) {

            try {
                difficulty = data.getIntExtra("Difficulty", -1);
                soundLength = data.getIntExtra("SoundLength", 1);
                dictionaryDir = data.getStringExtra("Dictionary");
                goSoundDir = data.getStringExtra("GoSound");

                OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("saveData.txt", Context.MODE_PRIVATE));

                if(difficulty != -1){
                    game.setDifficulty(difficulty);
                    writer.append(difficulty + "");
                }
                writer.write("\n");
                if(soundLength == 0){
                    goMusic = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.half);
                    goSound = MediaPlayer.create(this, goMusic);
                    writer.append(soundLength + "");
                    writer.write("\n");
                }
                else if(soundLength == 1){
                    goMusic = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.one);
                    goSound = MediaPlayer.create(this, goMusic);
                    writer.append(soundLength + "");
                    writer.write("\n");
                }
                else if(soundLength == 2){
                    goMusic = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.two);
                    goSound = MediaPlayer.create(this, goMusic);
                    writer.append(soundLength + "");
                    writer.write("\n");
                }
                else if(soundLength == 3) {
                    writer.append(soundLength + "");
                    writer.write("\n");
                }
                if(!goSoundDir.trim().equals("")) {
                    File music = new File(Environment.getExternalStorageDirectory().getPath() + "/guessIt/" + goSoundDir + ".mp3");
                    if (!music.exists()) {
                        Toast error = new Toast(this);
                        error.makeText(this, "Audio file was not found", Toast.LENGTH_SHORT).show();
                        goMusic = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.one);
                        goSound = MediaPlayer.create(this, goMusic);

                    } else {
                        writer.append(goSoundDir);
                        if(soundLength == 3) {
                            goMusic = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/guessIt/" + goSoundDir + ".mp3");
                            goSound = MediaPlayer.create(this, goMusic);
                        }
                    }
                }

                writer.write("\n");
                if(!dictionaryDir.trim().equals("")){
                    if(game.loadUserCategories(dictionaryDir + ".txt")){
                        writer.append(dictionaryDir);
                    }
                }
                writer.flush();
                writer.close();

            } catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
            goButton.setText("GO!");
        }

    }

    public void startInfo(View view) {
        Toast info = new Toast(this);
        info.makeText(this, "Tap a scoreboard to decrease score.", Toast.LENGTH_LONG).show();
        info.makeText(this, "Hold a scoreboard to clear scoreboards.", Toast.LENGTH_LONG).show();
        info.makeText(this, "Hold \"Category\" to go into variety mode.", Toast.LENGTH_LONG).show();
    }
    public void loadSaveState(){

        try {
            InputStream inputStream = openFileInput("saveData.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String currentLine = "";
                int count = 0;
                int soundLength = 1;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    if (count == 0 && !currentLine.trim().equals("")) {
                        game.setDifficulty(Integer.parseInt(currentLine.trim()));
                    }
                    else if (count == 1 && !currentLine.trim().equals("")) {
                        soundLength = Integer.parseInt(currentLine.trim());
                        if(soundLength == 0){
                            goMusic = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.half);
                            goSound = MediaPlayer.create(this, goMusic);
                        }
                        else if(soundLength == 1){
                            goMusic = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.one);
                            goSound = MediaPlayer.create(this, goMusic);
                        }
                        else if(soundLength == 2){
                            goMusic = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.two);
                            goSound = MediaPlayer.create(this, goMusic);
                        }
                    }
                    else if (count == 2 && !currentLine.trim().equals("") && soundLength == 3) {
                        goMusic = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/guessIt/" + currentLine.trim() +".mp3");
                        goSound = MediaPlayer.create(this, goMusic);
                    } else if (count == 3 && !currentLine.trim().equals("")) {
                        game.loadUserCategories(currentLine.trim() + ".txt");
                    }
                    count++;
                }
                bufferedReader.close();
            }
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public void onBackPressed() {
        if (backPressed == 0) {
            backPressed++;
            String text = "Press back again to change Mode!";
            Toast info = new Toast(this);
            info.makeText(this, text, Toast.LENGTH_LONG).show();
        } else {
            Intent gameMode = new Intent(this, GameMode.class);
            startActivity(gameMode);
            finish();
        }
    }
    public void onGoSoundComplete(){
        goButton.setText("GO!");
        screen.setTextColor(Color.parseColor("#3db2f5"));
    }
}