package src;

import javax.swing.JOptionPane;
public class Settings{
    //red, green and blue represent the RGB values for the background of the PlayFrame
    private int red, green, blue, character;
    
    public Settings (){
        setCharacter(1);
        setRed(255);
        setGreen(255);
        setBlue(255);
    }
    
    public Settings (int r, int g, int b, int character){
        setCharacter(character);
        setRed(r);
        setGreen(g);
        setBlue(b);
    }
    
    public void setRed(int r){
        if (r >= 0 && r <= 255){
            red = r;
        } else {
            JOptionPane.showMessageDialog (null, "You tried setting a value for red not between 0 and 255", "Invalid value", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setGreen(int g){
        if (g >= 0 && g <= 255){
            green = g;
        } else {
            JOptionPane.showMessageDialog (null, "You tried setting a value for green not between 0 and 255", "Invalid value", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setBlue(int b){
        if (b >= 0 && b <= 255){
            blue = b;
        } else {
            JOptionPane.showMessageDialog (null, "You tried setting a value for blue not between 0 and 255", "Invalid value", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setCharacter(int c){
        character = c;
    }
    
    public int getRed(){
        return red;
    }
    
    public int getGreen(){
        return green;
    }
    
    public int getBlue(){
        return blue;
    }
    
    public int getCharacter(){
        return character;
    }
}