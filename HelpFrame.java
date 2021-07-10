import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class HelpFrame extends Frame implements ActionListener{
    private MyLabel imgLbl;
    private MyButton goBtn, backBtn;
    private Choice imgSelect;
    private int selection;
    
    public HelpFrame (){
        setLayout(null);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(Color.GREEN);
        init();
    }
    
    public void init(){
        imgLbl = new MyLabel ("defaultImg.png", 10, 10, 1346, 623);
        
        imgSelect = new Choice();
        imgSelect.add("Introduction");
        imgSelect.add("Objectives");
        imgSelect.add("Chests");
        imgSelect.add("Settings");
        imgSelect.setBounds(116, 668, 300, 90);
        
        goBtn = new MyButton ("goButton.png", 533, 643, 300, 90);
        goBtn.addActionListener(this);
        
        backBtn = new MyButton ("backButton.png", 950, 643, 300, 90);
        backBtn.addActionListener(this);
        
        add(imgLbl);
        add(imgSelect);
        add(goBtn);
        add(backBtn);
    }
    
    @Override
    public void actionPerformed (ActionEvent e){
        music();
        if (e.getSource()==goBtn){
            selection = imgSelect.getSelectedIndex();
            switch(selection){
                case 0: imgLbl.setImage("introHelp.png"); break;
                case 1: imgLbl.setImage("objectivesHelp.png"); break;
                case 2: imgLbl.setImage("chestsHelp.png"); break;
                case 3: imgLbl.setImage("settingsHelp.png"); break;
            }
        }
        else if (e.getSource()==backBtn){
            this.setVisible(false);
        }
    }
    
    public void music (){
        try{
            AudioInputStream aIS;
            Clip clip;
            
            aIS = AudioSystem.getAudioInputStream(new File("buttonSound.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(aIS);
            clip.start();
        } catch (Exception e){
            JOptionPane.showMessageDialog (null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}