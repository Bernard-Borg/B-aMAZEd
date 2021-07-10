import java.awt.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class GamePanel extends Panel implements KeyListener{
    private int level, score, character;
    private int [][] mazeArr;
    private String chosenCharacter;
    
    private Player plyr;
    private Settings sett;
    
    private BufferedImage screen;
    private Graphics g;
    private boolean chestVisited, finished;
    
    private ArrayList <Question> quesList;
    private ArrayList <Level> lvlList;
    
    private Sprite pawn;
    private Tile tempEmpty, tempChest, tempWall, tempEnd;
    
    private final int EMPTY = 0, WALL = 1, PAWN = 2, CHEST = 3, END = 9;
    private PlayFrame owner;

    public GamePanel (PlayFrame o, Player p, ArrayList <Question> qList, ArrayList <Level> lList){
        setFocusable(true);
        addKeyListener(this);

        plyr = p;
        owner = o;
        sett = p.getSettings();
        quesList = qList;
        lvlList = lList;

        screen = new BufferedImage (1366, 780, BufferedImage.TYPE_INT_ARGB);
        tempEmpty = new Tile ("emptyNode.png");
        tempEnd = new Tile ("endPoint.png");
        tempChest = new Tile ("chest.png");
        tempWall = new Tile ("wall.png");
        
        level = p.getLevel();
        score = p.getScore();
        finished = false;
        
        character = sett.getCharacter();

        switch (character){
            case 1: chosenCharacter = ("yellow.png"); break;
            case 2: chosenCharacter = ("purple.png"); break;
            case 3: chosenCharacter = ("orange.png"); break;
            case 4: chosenCharacter = ("brown.png"); break;
        }

        getCurrentLevel();
    }

    @Override
    public void keyPressed(KeyEvent e){
        owner.clearLabel();

        int x = pawn.getCol();
        int y = pawn.getRow();
        if (!finished){
            try{
                if (e.getKeyCode() == KeyEvent.VK_D){
                    if (mazeArr[x+1][y]==END){
                        updateLevel();
                    }
                    else if (mazeArr[x+1][y]==EMPTY){
                        pawn.moveRight(25);
                        mazeArr[x][y] = EMPTY;
                        mazeArr[x+1][y] = PAWN;
                        render();
                    }
                    else if (mazeArr[x+1][y]==CHEST){
                        if (!chestVisited){
                            chestLogic();
                        } else {
                            JOptionPane.showMessageDialog (null, "Chest already done this level", "Oops", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_A) {
                    if (mazeArr[x-1][y]==END){
                        updateLevel();
                    }
                    else if (mazeArr[x-1][y]==EMPTY){
                        pawn.moveLeft(25);
                        mazeArr[x][y] = EMPTY;
                        mazeArr[x-1][y] = PAWN;
                        render();
                    }
                    else if (mazeArr[x-1][y]==CHEST){
                        if (!chestVisited){
                            chestLogic();
                        } else {
                            JOptionPane.showMessageDialog (null, "Chest already done this level", "Oops", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_W){
                    if (mazeArr[x][y-1]==END){
                        updateLevel();
                    }
                    else if (mazeArr[x][y-1]==EMPTY){
                        pawn.moveUp(25);
                        mazeArr[x][y] = EMPTY;
                        mazeArr[x][y-1] = PAWN;
                        render();
                    }
                    else if (mazeArr[x][y-1]==CHEST){
                        if (!chestVisited){
                            chestLogic();
                        } else {
                            JOptionPane.showMessageDialog (null, "Chest already done this level", "Oops", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_S){
                    if (mazeArr[x][y+1]==END){
                        updateLevel();
                    }
                    else if (mazeArr[x][y+1]==EMPTY){
                        pawn.moveDown(25);
                        mazeArr[x][y] = EMPTY;
                        mazeArr[x][y+1] = PAWN;
                        render();
                    }
                    else if (mazeArr[x][y+1]==CHEST){
                        if (!chestVisited){
                            chestLogic();
                        } else {
                            JOptionPane.showMessageDialog (null, "Chest already done this level", "Oops", JOptionPane.WARNING_MESSAGE);
                        }
                    }  
                }
            } catch (ArrayIndexOutOfBoundsException ex){
                
            }
        }
    }
    
    public void update (Graphics g){
        paint (g);
    }
    
    public void paint(Graphics g){
        if (screen != null){
            g.drawImage(screen, 1, 1, null);
        }
    }

    public void render(){        
        g = screen.getGraphics();
        
        for (int i = 0; i < mazeArr.length; i++){
            for (int j = 0; j < mazeArr.length; j++){
                if (mazeArr[i][j]==EMPTY){
                    tempEmpty.setCoords(j, i);
                    tempEmpty.draw(g);
                } else if (mazeArr[i][j]==WALL){
                    tempWall.setCoords(j, i);
                    tempWall.draw(g);
                } else if (mazeArr[i][j]==PAWN){
                    pawn = new Sprite (chosenCharacter, j, i);
                    pawn.draw(g);
                } else if (mazeArr[i][j]==CHEST){
                    tempChest.setCoords(j, i);
                    tempChest.draw(g);
                } else if (mazeArr[i][j]==END){
                    tempEnd.setCoords(j, i);
                    tempEnd.draw(g);
                }
            }
        }

        repaint();
    }
    
    public void getCurrentLevel(){
        if (level <= lvlList.size()){
            Level lvl;
            lvl = lvlList.get(level-1);
            chestVisited = false;
            mazeArr = lvl.getMaze();
        
            render();
        }
        else {
            JOptionPane.showMessageDialog (null, "No more levels - you finished!");
            finished = true;
        }
    }
    
    //updates the level whenever the player finishes the level
    public void updateLevel(){
        level++;
        plyr.setLevel(level);
        JOptionPane.showMessageDialog (null, "You completed level " + (level - 1));
        getCurrentLevel();
    }
    
    //displays a multiple choice question whenever a player touches a chest and gives a point if the player answers correctly
    public void chestLogic(){
        Question question2Ask;
        int random = (int)(Math.random() * quesList.size());
        question2Ask = quesList.get(random);
        String quest = question2Ask.getQuestion();
        String choice1 = question2Ask.getChoice1();
        String choice2 = question2Ask.getChoice2();
        String choice3 = question2Ask.getChoice3();
        int ans = question2Ask.getAnswer();
       
        String [] options = {choice1, choice2, choice3};
        int response = JOptionPane.showOptionDialog(null, quest ,"Chest Question",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, options,options[2]);
        
        if ((response+1)==ans){
            JOptionPane.showMessageDialog (null, "Correct!");
            score++;
            plyr.setScore(score);
            owner.updateScore(score);
        } else {
            JOptionPane.showMessageDialog (null, "Wrong :(");
        }
        chestVisited = true;
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    
    public Player getPlayer(){
        return plyr;
    }
}
