package StarApps.guessIt;

import java.util.Random;
import java.util.Date;
import java.util.ArrayList;
final class Category{
    private String category;
    private  Category next;
    private  Category prev;
    //Hard List
    private Node headHard;
    private Node taleHard;
    private int numNodesHard;
    private ArrayList positionsHard;
    private int posHard;
    private short firstRunHard;
    //Easy List
    private Node headEasy;
    private Node taleEasy;
    private int numNodesEasy;
    private ArrayList positionsEasy;
    private int posEasy;
    private short firstRunEasy;
    Category(String name){
        headHard = null;
        taleHard = null;
        next = null;
        prev = null;
        numNodesHard = 0;
        headEasy = null;
        taleEasy = null;
        numNodesEasy = 0;
        posEasy = 0;
        posHard = 0;
        firstRunEasy = 0;
        firstRunHard = 0;
        category = name;
    }
    void addItemEasy(final Object item){
        if(headEasy == null){
            headEasy = new Node(item);
            taleEasy = headEasy;
        }
        else{
            Node temp;
            temp = headEasy;
            headEasy = new Node(item);
            temp.setNext(headEasy);
            headEasy.setPrev(temp);
        }
        numNodesEasy++;
    }
    void addItemHard(final Object item){
        if(headHard == null){
            headHard = new Node(item);
            taleHard = headHard;
        }
        else{
            Node temp;
            temp = headHard;
            headHard = new Node(item);
            temp.setNext(headEasy);
            headHard.setPrev(temp);
        }
        numNodesHard++;
    }
    private void shuffleEasy(){
        positionsEasy = new ArrayList<Integer>();
        Node trav = headEasy;
        Random randGen = new Random();
        randGen.setSeed(new Date().getTime());
        int number = randGen.nextInt(numNodesEasy);
        while(positionsEasy.size()<numNodesEasy){
            if(!positionsEasy.contains(number)){
                positionsEasy.add(number);
            }
            number = randGen.nextInt(numNodesEasy);
        }
    }
    private void shuffleHard(){
        positionsHard = new ArrayList<Integer>();
        Node trav = headHard;
        Random randGen = new Random();
        randGen.setSeed(new Date().getTime());
        int number = randGen.nextInt(numNodesHard);
        while(positionsHard.size()<numNodesHard){
            if(!positionsHard.contains(number)){
                positionsHard.add(number);
            }
            number = randGen.nextInt(numNodesHard);
        }
    }
    Object randSelectEasy(){
        Object ret;
        if(headEasy != null) {
            Node trav = headEasy;
            if (posEasy >= numNodesEasy || firstRunEasy == 0) {
                firstRunEasy = 1;
                shuffleEasy();
                posEasy = 0;
            }
            if(posEasy >=  positionsEasy.size()){
                shuffleEasy();
                posEasy = 0;
            }
            for (int i = 0; i < (int) positionsEasy.get(posEasy); i++) {
                trav = trav.getPrev();
            }
            posEasy++;
            ret = trav.getObj();
        }
        else if(headHard != null){
            ret = randSelectHard();
        }
        else{
            ret = "This list is empty";
        }
        return ret;
    }
    Object randSelectHard(){
        Object ret;
        if(headHard != null) {
            Node trav = headHard;
            if (posHard >= numNodesHard || firstRunHard == 0) {
                assert(numNodesHard == positionsHard.size());
                firstRunHard = 1;
                shuffleHard();
                posHard = 0;
            }
            if(posHard >=  positionsHard.size()){
                shuffleEasy();
                posHard = 0;
            }
            for (int i = 0; i < (int) positionsHard.get(posHard); i++) {
                trav = trav.getPrev();
            }
            posHard++;
            ret = trav.getObj();
        }
        else if(headEasy != null){
            ret = randSelectEasy();
        }
        else{
            ret = "This list is empty";
        }
        return ret;
    }
    Object randSelect(){
        Object ret;
        if(headHard != null && headEasy != null) {
            Random randGen = new Random();
            int number = randGen.nextInt(2);
            if(number == 1){
                ret = randSelectHard();
            }
            else{
                ret = randSelectEasy();
            }
        }
        else if(headHard != null && headEasy == null) {
            ret = randSelectHard();
        }
        else if(headHard == null && headEasy != null) {
            ret = randSelectEasy();
        }
        else{
            ret = "The List Empty";
        }
        return ret;
    }
    Category getNext(){
        return next;
    }
    Category getPrev(){
        return prev;
    }
    void setNext(Category next) {
        this.next = next;
    }
    void setPrev(Category prev){
        this.prev = prev;
    }
    String getCategoryName(){
        return category;
    }
}