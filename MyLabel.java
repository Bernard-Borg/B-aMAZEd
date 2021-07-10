import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class MyLabel extends Label{
    private BufferedImage img;
    private String fileName;
    
    public MyLabel (String fN, int x, int y, int w, int h){
        fileName = fN;
        setBounds (x, y, w, h);
    }
    
    public void setImage (String fN){
        fileName = fN;
        repaint();
    }
    
    public void paint(Graphics g){
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        
        img = new BufferedImage (1336, 638, BufferedImage.TYPE_INT_ARGB);
        
        try {
            img = ImageIO.read(new File(fileName));
        }
        catch (Exception e) {
            System.err.println("Image not found " + e.getMessage());
        }
        
        g.drawImage(img, 0, 0, null);
    }
}