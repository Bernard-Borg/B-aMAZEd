package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class NewPlayerFrame extends Frame implements ActionListener{
    private Label nameLbl;
    private TextField nameTxt;
    private MyButton createBtn, backBtn;
    private ArrayList <Player> pList;
    
    public NewPlayerFrame (String s, ArrayList <Player> pL, int w, int h){
        super(s);
        setLayout(null);
        setSize (w, h);
        setLocationRelativeTo (null);
        setBackground(Color.CYAN);
        pList = pL;
        init();
        
        addWindowListener (new WindowAdapter(){
            public void windowClosing (WindowEvent e){
                setVisible (false);
            }
        });
    }
    
    public void init(){
        setFont(new Font ("Courier", Font.PLAIN, 20));
        
        nameLbl = new Label ("Enter your username; ");
        nameLbl.setBounds(100, 45, 300, 30);
        
        nameTxt = new TextField ();
        nameTxt.setBounds(100, 75, 300, 30);
        nameTxt.addActionListener(this);
        
        createBtn = new MyButton ("images/createButton.png", 100, 125, 300, 90);
        createBtn.addActionListener(this);
        
        backBtn = new MyButton ("images/backButton.png", 100, 235, 300, 90);
        backBtn.addActionListener(this);
        
        add(nameLbl);
        add(nameTxt);
        add(createBtn);
        add(backBtn);
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
    
    @Override
    public void actionPerformed (ActionEvent e){
        music();
        if (e.getSource()==createBtn||e.getSource()==nameTxt){
            Player p;
            if (!((nameTxt.getText()).trim().equals(""))){
                p = new Player (nameTxt.getText());
                pList.add(p);
                JOptionPane.showMessageDialog (null, "Player created successfully!");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog (null, "Empty textfield not accepted", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
        else if (e.getSource()==backBtn){
            setVisible(false);
        }
    }
}
