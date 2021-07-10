import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.JOptionPane;

public class PlayFrame extends Frame implements ActionListener{
    private Settings sett;
    private MyButton closeBtn;
    private Player player;
    private MyLabel wasdLbl;
    private Label scoreLbl;
    
    private GamePanel gamePanel;

    public PlayFrame (String name, Player p, ArrayList <Question> qList, ArrayList <Level> lList, int w, int h){
        super(name);
        setLayout(null);
        setSize(1366, 808);
        setUndecorated(true);
        setLocationRelativeTo(null);
        
        player = p;
        
        sett = player.getSettings();
        setBackground (new Color (sett.getRed(), sett.getGreen(), sett.getBlue()));
        
        gamePanel = new GamePanel(this, p, qList, lList);
        gamePanel.setBounds (245, 24, 750, 750);
        gamePanel.setForeground(new Color (255-sett.getRed(), 255-sett.getGreen(), 255-sett.getBlue()));
        add(gamePanel);
        
        greet();
        init();
    }

    public void paint (Graphics g){
        g.setColor (new Color (255-sett.getRed(), 255-sett.getGreen(), 255-sett.getBlue()));

        g.drawLine (243, 22, 997, 22);
        g.drawLine (997, 22, 997, 776);
        g.drawLine (997, 776, 243, 776);
        g.drawLine (243, 776, 243, 22);
    }

    public void init(){
        closeBtn = new MyButton ("closeButton.png", 1, 1, 50, 50);
        closeBtn.addActionListener(this);

        wasdLbl = new MyLabel ("WASDInstruction.png", 1081, 306, 200, 155);
        
        scoreLbl = new Label ("Score: " + player.getScore());
        scoreLbl.setBounds (1081, 50, 200, 20);

        add(closeBtn);
        add(wasdLbl);
        add(scoreLbl);
    }

    @Override
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==closeBtn){
            setVisible(false);
            player = gamePanel.getPlayer();
        }
    }
    
    public void greet(){
        JOptionPane.showMessageDialog (null, "Hello " + player.getName() + "; Level " + player.getLevel());
    }
    
    public void clearLabel(){
        wasdLbl.setVisible(false);
    }
    
    public void updateScore(int s){
        scoreLbl.setText("Score: " + s);
        repaint();
    }
}
