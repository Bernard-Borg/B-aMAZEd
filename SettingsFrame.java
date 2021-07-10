import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SettingsFrame extends Frame implements ActionListener{
    private MyButton selectBtn, setBtn, prevBtn, doneBtn, backBtn;
    private TextField redTxt, blueTxt, greenTxt;
    private Label redLbl, greenLbl, blueLbl, subT1Lbl, subT2Lbl;
    private Checkbox yellowBox, purpleBox, orangeBox, brownBox;
    private CheckboxGroup radioButtons;
    
    private Player p;
    private Settings sett;
    private PreviewFrame pf = null;
    private Font f, f2;
    
    public SettingsFrame (String s, Player playPlayer, int w, int h){
        super(s);
        setLayout(null);
        setSize(w, h);
        setLocationRelativeTo (null);
        setBackground (Color.CYAN);
        p = playPlayer;
        
        init();
        
        addWindowListener (new WindowAdapter(){
            public void windowClosing (WindowEvent e){
                setVisible (false);
            }
        });
    }
    
    public void init(){
        f = new Font ("Courier", Font.PLAIN, 20);
        f2 = new Font ("Courier", Font.BOLD, 22);
        
        sett = p.getSettings();
        
        selectBtn = new MyButton ("selectButton.png", 300, 243, 300, 90);
        selectBtn.addActionListener(this);
        
        setBtn = new MyButton ("setButton.png", 975, 200, 300, 90);
        setBtn.addActionListener(this);
        
        prevBtn = new MyButton ("previewButton.png", 975, 310, 300, 90);
        prevBtn.addActionListener(this);
        
        doneBtn = new MyButton ("doneButton.png", 374, 600, 300, 90);
        doneBtn.addActionListener(this);
        
        backBtn = new MyButton ("backButton.png", 694, 600, 300, 90);
        backBtn.addActionListener(this);
        
        redLbl = new Label ("Red;");
        redLbl.setBounds (800, 188, 100, 30);
        redLbl.setFont (f);
        
        greenLbl = new Label("Green;");
        greenLbl.setBounds (800, 258, 100, 30);
        greenLbl.setFont (f);
        
        blueLbl = new Label("Blue;");
        blueLbl.setBounds (800, 328, 100, 30);
        blueLbl.setFont (f);
        
        subT1Lbl = new Label ("Select Pawn Colour;");
        subT1Lbl.setBounds (15, 26, 250, 60);
        subT1Lbl.setFont (f2);
        
        subT2Lbl = new Label ("Background Color;");
        subT2Lbl.setBounds (700, 26, 250, 60);
        subT2Lbl.setFont (f2);
        
        radioButtons = new CheckboxGroup ();
        
        yellowBox = new Checkbox ("Yellow", radioButtons, false);
        yellowBox.setBounds(125, 208, 100, 40);
        yellowBox.setFont (f);
        
        purpleBox = new Checkbox ("Purple", radioButtons, false);
        purpleBox.setBounds(125, 248, 100, 40);
        purpleBox.setFont (f);
        
        orangeBox = new Checkbox ("Orange", radioButtons, false);
        orangeBox.setBounds(125, 288, 100, 40);
        orangeBox.setFont (f);
        
        brownBox = new Checkbox ("Brown", radioButtons, false);
        brownBox.setBounds(125, 328, 100, 40);
        brownBox.setFont (f);
        
        redTxt = new TextField ();
        redTxt.setBounds(800, 218, 100, 30);
        redTxt.setFont (f);
        
        greenTxt = new TextField ();
        greenTxt.setBounds(800, 288, 100, 30);
        greenTxt.setFont (f);
        
        blueTxt = new TextField ();
        blueTxt.setBounds(800, 358, 100, 30);
        blueTxt.setFont (f);
        
        add(selectBtn);
        add(setBtn);
        add(prevBtn);
        add(doneBtn);
        add(backBtn);
        add(redLbl);
        add(greenLbl);
        add(blueLbl);
        add(subT1Lbl);
        add(subT2Lbl);
        add(yellowBox);
        add(purpleBox);
        add(orangeBox);
        add(brownBox);
        add(redTxt);
        add(greenTxt);
        add(blueTxt);
    }
    
    @Override
    public void actionPerformed (ActionEvent e){
        music();
        if (e.getSource()==selectBtn){
            if (radioButtons.getSelectedCheckbox()==yellowBox){
                sett.setCharacter(1);
            }
            else if (radioButtons.getSelectedCheckbox()==purpleBox){
                sett.setCharacter(2);
            }
            else if (radioButtons.getSelectedCheckbox()==orangeBox){
                sett.setCharacter(3);
            }
            else if (radioButtons.getSelectedCheckbox()==brownBox){
                sett.setCharacter(4);
            }
            else {
                JOptionPane.showMessageDialog (null, "No character selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource()==setBtn){
            try{    
                sett.setRed(Integer.parseInt(redTxt.getText()));
                sett.setGreen(Integer.parseInt(greenTxt.getText()));
                sett.setBlue(Integer.parseInt(blueTxt.getText()));
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog (null, "Strings are not allowed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource()==prevBtn){
            pf = new PreviewFrame("Preview", Integer.parseInt(redTxt.getText()), Integer.parseInt(greenTxt.getText()), Integer.parseInt(blueTxt.getText()));
            pf.setVisible(true);
        }
        else if (e.getSource()==doneBtn){
            p.setSettings(sett);
        }
        else if (e.getSource()==backBtn){
            setVisible(false);
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
    
    //paints the two divider lines
    public void paint (Graphics g){
        g.drawLine (684, 25, 684, 550);
        g.drawLine (1, 550, 1366, 550);
    }
}