package StarApps.guessIt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import StarApps.guessIt.R;


public class Options extends Activity {

    private int difficulty;
    private int backPressed;
    private int SoundLength;
    private EditText dictionary;
    private EditText goSound;
    private RadioButton easy, hard, both, half, one, two, custom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        goSound =(EditText)findViewById(R.id.user_input_mus);
        dictionary =(EditText)findViewById(R.id.user_input_dic);
        difficulty = -1;
        easy = (RadioButton)findViewById(R.id.easy);
        hard = (RadioButton)findViewById(R.id.hard);
        both = (RadioButton)findViewById(R.id.both);
        half = (RadioButton)findViewById(R.id.half);
        one = (RadioButton)findViewById(R.id.one);
        two = (RadioButton)findViewById(R.id.two);
        custom = (RadioButton)findViewById(R.id.custom);
        backPressed = 0;
        loadSaveState();
    }
    public void loadSaveState(){

        try {
            InputStream inputStream = openFileInput("saveData.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String currentLine = "";
                int count = 0;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    if (count == 0 && !currentLine.trim().equals("")) {
                        difficulty = Integer.parseInt(currentLine.trim());
                        if (difficulty == 0) {
                            easy.toggle();
                        } else if (difficulty == 1) {
                            hard.toggle();
                        } else if (difficulty == 2) {
                            both.toggle();
                        }
                    }else if (count == 1 && !currentLine.trim().equals("")) {
                            SoundLength = Integer.parseInt(currentLine.trim());
                            if(SoundLength == 0){
                                half.toggle();
                            }
                            else if(SoundLength == 1){
                                one.toggle();
                            }
                            else if(SoundLength == 2){
                                two.toggle();
                            }
                            else if(SoundLength == 3){
                                custom.toggle();
                            }
                    }else if (count == 2 && !currentLine.trim().equals("")) {
                        goSound.setText(currentLine.trim());
                    }
                    else if (count == 3 && !currentLine.trim().equals("")) {
                        dictionary.setText(currentLine.trim());
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
    public void half(View view) {
        SoundLength= 0;
    }

    public void one(View view) {
        SoundLength = 1;
    }

    public void two(View view) {
        SoundLength = 2;
    }
    public void custom(View view) {
        SoundLength = 3;
    }
    public void easy(View view) {
        difficulty= 0;
    }

    public void hard(View view) {
        difficulty = 1;
    }

    public void both(View view) {
        difficulty = 2;
    }
    public void close(){
        Intent goingBack = new Intent();
        goingBack.putExtra("Difficulty",difficulty);
        goingBack.putExtra("SoundLength",SoundLength);
        goingBack.putExtra("GoSound",goSound.getText().toString());
        goingBack.putExtra("Dictionary",dictionary.getText().toString());
        setResult(RESULT_OK,goingBack);
        finish();
    }
    @Override
    public void onBackPressed() {
        if(backPressed == 0){
            backPressed++;
            String text = "Press back again to return to the main screen!";
            Toast info = new Toast(this);
            info.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        else{
            close();
        }
    }
    public void finish(View view) {
        close();
    }

    public void displayInfo(View view) {
        String text = "Create a folder \"catchphrase\" in root and place mp3's and txt files inside";
        Toast info = new Toast(this);
        info.makeText(this, text, Toast.LENGTH_LONG).show();
        text = "Type the name of the file without the extensions in the text field";
        info = new Toast(this);
        info.makeText(this, text, Toast.LENGTH_LONG).show();
        text = "If you don't have any hard phrases, easy phrases will be used in hard mode and vice-versa";
        info = new Toast(this);
        info.makeText(this, text, Toast.LENGTH_LONG).show();

    }
}
