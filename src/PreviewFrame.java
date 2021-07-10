package src;

import java.awt.*;
import java.awt.event.*;
public class PreviewFrame extends Frame{
    public PreviewFrame (String n, int r, int g, int b){
        super (n);
        setSize(341, 155);
        setBackground (new Color (r, g, b));
        setLocationRelativeTo(null);
        addWindowListener (new WindowAdapter (){
            public void windowClosing (WindowEvent e){
                dispose();
            }
        });
    }
}