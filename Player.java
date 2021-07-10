import javax.swing.JOptionPane;
public class Player{
    private String name;
    private int level, score;
    private Settings sett;
    
    public Player (){
        setName("Guest");
        setLevel(1);
        setScore(0);
        setSettings(new Settings ());
    }
    
    public Player (String n){
        setName(n);
        setLevel(1);
        setScore(0);
        setSettings(new Settings ());
    }
    
    public void setName (String n){
        name = n;
    }
    
    public void setLevel (int l){
        if (l >= 1){
            level = l;
        }
        else {
            JOptionPane.showMessageDialog (null, "Error - tried setting level less than 1", "Error", JOptionPane.ERROR_MESSAGE);   
        }
    }
    
    public void setSettings (Settings s){
        sett = s;
    }
    
    public void setScore (int s){
        if (s >= 0){    
            score = s;
        }
        else{
            JOptionPane.showMessageDialog (null, "Error - tried setting score less than 0", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getName (){
        return name;
    }
    
    public int getLevel (){
        return level;
    }
    
    public int getScore(){
        return score;
    }
    
    public Settings getSettings (){
        return sett;
    }
}