package StarApps.guessIt;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CategoryManager {
    private Category head;
    private Category tale;
    private Category currCategory;//used for setting user input
    private Category curr;// used for traversal
    private Context context;
    private int numNodes;
    private int difficulty;
    CategoryManager(Context con){
        head = null;
        tale = null;
        currCategory = null;
        context = con;
        difficulty = 2;
        loadCategories(null);
    }
    private void addCategory(String name){
        if(head == null){
            head = new Category(name);
            tale = head;
        }
        else{
            Category temp;
            temp = head;
            head = new Category(name);
            temp.setNext(head);
            head.setPrev(temp);
        }
        numNodes++;
    }
    private boolean loadCategories(String file){
        boolean retVal = true;
        if(file == null) {
            BufferedReader reader = null;
            try {
                String currentLine;
                AssetManager assetManager = context.getAssets();
                InputStream categories = assetManager.open("categories.txt");
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open("categories.txt")));
                while ((currentLine = reader.readLine()) != null) {
                    processLine(currentLine.trim());
                }
                reader.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        else{
            File sdcard = Environment.getExternalStorageDirectory();
            File userCategories = new File(sdcard,"catchphrase/"+file);
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(userCategories));
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    processLine(currentLine.trim());
                }
                br.close();
            }
            catch (IOException exception) {
                Toast error = new Toast(context);
                error.makeText(context, "Text file was not found", Toast.LENGTH_SHORT).show();
                retVal = false;
            }
        }
        return retVal;
    }
    private void processLine(final String input){
        String str;
        if (input.charAt(0) == '#') {
            str = (input.split("#")[1]).trim();
            currCategory = exists(str);
            if(currCategory== null){
                addCategory(str);
                currCategory = head;
            }
            System.out.println(currCategory.getCategoryName() );
        } else if (input.charAt(0) == 'h') {
            currCategory.addItemHard(input.split("#")[1].trim());
            //System.out.println(currCategory.getCategoryName());
        }
        else if (input.charAt(0) == 'e') {
            currCategory.addItemEasy(input.split("#")[1].trim());
            //System.out.println(currCategory.getCategoryName());
        }
    }
    private Category exists(String name){
        Category trav = head;
        while(trav != null){
            if(trav.getCategoryName().toLowerCase().trim().equals(name.toLowerCase().trim())){
                break;
            }
            trav = trav.getPrev();
        }
        return trav;
    }
    public String next(){
        if(curr == null){
            curr = tale;
        }
        else if(curr.getNext() == null){
            curr = tale;
        }
        else{
            curr = curr.getNext();
        }
        return curr.getCategoryName();
    }
    public String getPhrase(){
        String phrase;
        if(difficulty == 0){
            phrase = (String)curr.randSelectEasy();
        }
        else if(difficulty == 1){
            phrase = (String)curr.randSelectHard();
        }
        else{
            phrase = (String)curr.randSelect();
        }

        return phrase;
    }
    public boolean loadMore(String file){
        return loadCategories(file);
    }
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }
}
