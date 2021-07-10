import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MainMenu extends Frame implements ActionListener{
    private MyButton playBtn, newBtn, loadBtn, deleteBtn, helpBtn, settBtn, saveBtn, leadBtn, exitBtn;

    private PlayFrame pf = null;
    private NewPlayerFrame np = null;
    private SettingsFrame sf = null;
    private LeaderboardFrame lf = null;
    private HelpFrame hf = null;

    private BufferedImage titleImg;
    private Player playPlayer;

    private ArrayList <Player> playList;
    private ArrayList <Question> quesList;
    private ArrayList <Level> levelList;

    private boolean exit = true;

    public MainMenu (){
        super("Main Menu");
        setLayout(null);
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground (new Color (50, 205, 50));

        init();
        loadPlayers();
        loadQuestions();
        loadLevels();

        addWindowListener (new WindowAdapter(){
            public void windowClosing (WindowEvent e){
                askToSave();
                if (exit != false){
                    System.exit(0);
                }
            }
        });
    }

    public void init(){
        playBtn = new MyButton ("playButton.png", 535, 235, 300, 90);
        playBtn.addActionListener (this);

        newBtn = new MyButton ("newPlayerButton.png", 535, 335, 300, 90);
        newBtn.addActionListener (this);

        loadBtn = new MyButton ("loadPlayerButton.png", 535, 435, 300, 90);
        loadBtn.addActionListener (this);

        helpBtn = new MyButton ("helpButton.png", 1303, 35, 50, 50);
        helpBtn.addActionListener (this);

        settBtn = new MyButton ("settingsButton.png", 1303, 95, 50, 50);
        settBtn.addActionListener (this);

        deleteBtn = new MyButton ("deleteButton.png", 535, 535, 300, 90);
        deleteBtn.addActionListener(this);

        saveBtn = new MyButton ("saveButton.png", 1303, 155, 50, 50);
        saveBtn.addActionListener(this);

        leadBtn = new MyButton ("leaderboardButton.png", 1303, 215, 50, 50);
        leadBtn.addActionListener(this);

        exitBtn = new MyButton ("exitButton.png", 535, 635, 300, 90);
        exitBtn.addActionListener (this);

        add(playBtn);
        add(newBtn);
        add(loadBtn);
        add(helpBtn);
        add(settBtn);
        add(saveBtn);
        add(deleteBtn);
        add(leadBtn);
        add(exitBtn);
    }

    public void paint (Graphics g){
        titleImg = new BufferedImage (300, 90, BufferedImage.TYPE_INT_ARGB);

        try {
            titleImg = ImageIO.read(new  File("title.png"));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog (null, "Image not found", "Error", JOptionPane.ERROR_MESSAGE);
        }

        g.drawImage(titleImg, 521, 85, null);
    }

    @Override
    public void actionPerformed (ActionEvent e){
        music();
        if (e.getSource()==playBtn){
            if(playList.size()!=0){
                if (playPlayer==null){
                    JOptionPane.showMessageDialog(null, "You must load a player", "No players found", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (playPlayer.getLevel() <= levelList.size()){
                        loadLevels();
                        pf = new PlayFrame ("B Amazed", playPlayer, quesList, levelList, 400, 500);
                        pf.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog (null, "You already completed all the levels!", "Levels finished", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog (null, "No players found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource()==newBtn){
            if (np == null){
                np = new NewPlayerFrame ("New Player", playList, 500, 355);
            }
            np.setVisible(true);
        }
        else if (e.getSource()==loadBtn){
            if (playList.size()!=0){
                setPlayPlayer();
            } else {
                JOptionPane.showMessageDialog (null, "No players found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource()==helpBtn){
            if (hf == null){
                hf = new HelpFrame ();
            }
            hf.setVisible(true);
        }
        else if (e.getSource()==settBtn){
            if (playList.size()!=0){
                if (playPlayer==null){
                    JOptionPane.showMessageDialog(null, "You must load a player", "No players found", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    sf = new SettingsFrame ("Settings", playPlayer, 1366, 768);
                    sf.setVisible(true);
                }
            }
            else {
                JOptionPane.showMessageDialog (null, "No players found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource()==saveBtn){
            rewriteFile();
        }
        else if (e.getSource()==leadBtn){
            lf = new LeaderboardFrame (playList, 600, 768);
            lf.setVisible(true);
        }
        else if (e.getSource()==deleteBtn){
            if (playList.size()!=0){
                delPlayer();
            } else {
                JOptionPane.showMessageDialog (null, "No players found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource()==exitBtn){
            askToSave();
            if (exit != false){
                System.exit(0);
            }
        }
    }
    
    //loads all the players
    public void loadPlayers(){
        try{
            BufferedReader br = new BufferedReader (new FileReader ("playerList.txt"));
            playList = new ArrayList <Player>();
            Player plyr;
            Settings sett;
            String nameL;
            int levelL, scoreL, tempR, tempG, tempB, tempChar;
            while ((nameL = br.readLine()) != null){
                levelL = Integer.parseInt(br.readLine());
                scoreL = Integer.parseInt(br.readLine());
                tempR = Integer.parseInt(br.readLine());
                tempG = Integer.parseInt(br.readLine());
                tempB = Integer.parseInt(br.readLine());
                tempChar = Integer.parseInt(br.readLine());
                plyr = new Player(nameL);
                sett = new Settings(tempR, tempG, tempB, tempChar);
                plyr.setLevel(levelL);
                plyr.setScore(scoreL);
                plyr.setSettings(sett);

                playList.add(plyr);
            }
            br.close();
        } catch (Exception e){
            JOptionPane.showMessageDialog (null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //loads all the questions
    public void loadQuestions(){
        try{
            BufferedReader br = new BufferedReader (new FileReader ("questionList.txt"));
            quesList = new ArrayList <Question>();
            Question ques;
            String lineQues, lineC1, lineC2, lineC3;
            int lineAns;
            while ((lineQues = br.readLine()) != null){
                lineC1 = br.readLine();
                lineC2 = br.readLine();
                lineC3 = br.readLine();
                lineAns = Integer.parseInt(br.readLine());
                ques = new Question();
                ques.setQuestion(lineQues);
                ques.setChoice1(lineC1);
                ques.setChoice2(lineC2);
                ques.setChoice3(lineC3);
                ques.setAnswer(lineAns);

                quesList.add(ques);
            }
            br.close();
        } catch (Exception e){
            JOptionPane.showMessageDialog (null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //loads all the levels
    public void loadLevels(){
        String levelNoLine, line2;
        int [][] m;
        levelList = new ArrayList <Level>();
        Level lvl;
        try{
            BufferedReader br = new BufferedReader (new FileReader ("levelList.txt"));
            while((levelNoLine = br.readLine())!=null){
                lvl = new Level();
                lvl.setLevelNo(Integer.parseInt(levelNoLine));
                m = new int [30][30];
                for (int i = 0; i < m.length; i++) {
                    line2 = br.readLine();
                    String [] splitLine = line2.split(",");
                    for (int j = 0; j < splitLine.length; j++) {
                        m[i][j] = Integer.parseInt(splitLine[j]);
                    }   
                }
                lvl.setMaze(m);
                levelList.add(lvl);
            }
            br.close();
        } catch (Exception e){
            JOptionPane.showMessageDialog (null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //rewrites the playerList file to save any changes
    public void rewriteFile(){
        Player tempPlayer;
        Settings tempSett;
        try{
            BufferedWriter bw = new BufferedWriter (new FileWriter ("playerList.txt"));
            for (int i = 0; i < playList.size(); i++){
                tempPlayer = playList.get(i);
                tempSett = tempPlayer.getSettings();
                bw.write (tempPlayer.getName());
                bw.newLine();
                bw.write (String.valueOf(tempPlayer.getLevel()));
                bw.newLine();
                bw.write (String.valueOf(tempPlayer.getScore()));
                bw.newLine();
                bw.write (String.valueOf(tempSett.getRed()));
                bw.newLine();
                bw.write (String.valueOf(tempSett.getGreen()));
                bw.newLine();
                bw.write (String.valueOf(tempSett.getBlue()));
                bw.newLine();
                bw.write (String.valueOf(tempSett.getCharacter()));
                bw.newLine();
            }   
            bw.close();
            JOptionPane.showMessageDialog (null, "Save successful");
        } catch (Exception e){
            JOptionPane.showMessageDialog (null, "Error " + e.getMessage());
        }
    }

    public void setPlayPlayer(){
        String usernameLoad = JOptionPane.showInputDialog ("Please enter your username;");
        try{
            boolean found = false;
            int i = 0;
            while (i < playList.size()&&!found){
                if (((playList.get(i)).getName()).equalsIgnoreCase(usernameLoad)){
                    playPlayer = playList.get(i);
                    found = true;
                }
                i++;
            }

            if (found){
                JOptionPane.showMessageDialog (null, "Player loaded successfully");
            }
            else JOptionPane.showMessageDialog (null, "Player not found", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e){
            JOptionPane.showMessageDialog (null, "Player specified was not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //asks the user if he/she wants to save before closing
    public boolean askToSave(){
        int choice = JOptionPane.showConfirmDialog (null, "Do you want to save before closing?");

        switch (choice){
            case 0: rewriteFile(); exit = true; break;
            case 1: exit = true; break;
            case -1: 
            case 2: exit = false; break;
        }
        return exit;
    }

    //plays sound upon clicking button
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
    
    public void delPlayer(){
        String usernameDel = JOptionPane.showInputDialog ("Please enter your username;");
        try{
            int i = 0;
            boolean deleted = false;

            if (playPlayer!=null){
                if (playPlayer.getName().equalsIgnoreCase(usernameDel)){
                    playPlayer = null;
                }
            } //sets playPlayer to null if the user requests to remove the player which is currently being used
            do{
                if (((playList.get(i)).getName()).equalsIgnoreCase(usernameDel)){
                    playList.remove(i);
                    deleted = true;
                }
                i++;
            } while (i < playList.size() && !deleted); //removes the requested player from the ArrayList

            if (deleted){
                JOptionPane.showMessageDialog (null, "Player deleted successfully");
            }
            else {
                JOptionPane.showMessageDialog (null, "Player not found", "Error", JOptionPane.ERROR_MESSAGE);
            } //outputs whether or not the deletion was successful
        } catch (Exception e){
            System.err.println ("Error " + e.getMessage());
        }
    }
}
