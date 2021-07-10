package src;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LeaderboardFrame extends Frame implements ActionListener, ItemListener{
    private MyButton backBtn;
    private List l;
    private MyLabel title;
    
    private ArrayList <Player> arrList;
    private Player [] pArr;
    
    public LeaderboardFrame (ArrayList <Player> aL, int w, int h){
        super ("Score Leaderboard");
        setLayout(null);
        setSize(w, h);
        setLocationRelativeTo(null);
        setBackground(new Color (210, 150, 20));
        
        arrList = aL;
        
        init();
        sortArrayList();
        
        addWindowListener(new WindowAdapter(){
            public void windowClosing (WindowEvent e){
                setVisible(false);
            }
        });
    }
    
    public void init(){
        backBtn = new MyButton ("images/backButton.png", 150, 580, 300, 90);
        backBtn.addActionListener(this);
        
        l = new List (10);
        l.setBounds (150, 225, 300, 300);
        l.addItemListener(this);
        
        title = new MyLabel ("images/leaderboardTitle.png", 150, 80, 300, 100);
        
        add(backBtn);
        add(l);
        add(title);
    }
    
    @Override
    public void actionPerformed (ActionEvent e){
        setVisible(false);
    }
    
    //simply deselects anything the user selects to show the user that selecting the list is useless
    @Override
    public void itemStateChanged (ItemEvent e){
        l.deselect(l.getSelectedIndex());
    }
    
    //uses a Bubble sort algorithm to sort the array in descending order of score
    public void sortArrayList (){
        Player tempPlayer;
        pArr = (Player[])arrList.toArray(new Player [arrList.size()]);
        
        for (int i = 1; i <= pArr.length; i++){
            for (int j = 0; j < pArr.length-i; j++){
                if (pArr[j].getScore() < pArr[j + 1].getScore()){
                    tempPlayer = pArr[j];
                    pArr[j] = pArr[j+1];
                    pArr[j+1] = tempPlayer;
                }
            }
        }
        
        fillList();
    }
    
    //fills the awt list with the details
    public void fillList(){
        for (int i = 0; i < pArr.length; i++){
            l.add("(" + (i + 1) + ") " + pArr[i].getName() + " with " + pArr[i].getScore() + " points!");
        }
    }
}