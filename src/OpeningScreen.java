package src;

import javax.swing.JProgressBar;
import java.awt.*;
import javax.swing.event.*;

public class OpeningScreen extends Frame implements ChangeListener, Runnable{
    private MyLabel flashScreen;
    private JProgressBar jp;
    private Thread thrd;
    private boolean stopped = false;
    
    public OpeningScreen (){
        setLayout(null);
        setSize (1366, 768);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        init();
    }
    
    public void init(){
        flashScreen = new MyLabel ("images/flashScreen.png", 0, 0, 1366, 768);
        
        jp = new JProgressBar (0, 100);
        jp.setBounds(533, 600, 300, 30);
        jp.addChangeListener(this);
        jp.setStringPainted(true);
        
        add(jp);
        add(flashScreen);
        
        thrd = new Thread (this);
        thrd.start();
    }
    
    public void run (){
        while (!stopped) {
            logic();
            try {
                Thread.sleep(55);
            }
            catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }
    }
    
    @Override
    public void stateChanged (ChangeEvent e){
        if (jp.getValue()==100){
            MainMenu mm = new MainMenu ();
            mm.setVisible(true);
            this.dispose();
        }
    }
    
    public void logic(){
        jp.setValue (jp.getValue()+1); //adds 1 percent to the progress bar every 55ms
        if (jp.getValue()==100){
            stopped = true;
        }
        jp.setString(jp.getValue() + "%");
    }
}