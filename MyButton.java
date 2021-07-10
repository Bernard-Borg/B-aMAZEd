import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
public class MyButton extends Button{
    private BufferedImage img;
    private String fileName;
    
    public MyButton (String fN, int x, int y, int w, int h){
        fileName = fN;
        setBounds (x, y, w, h);
    }
    
    @Override
    public void paint(Graphics g){
        img = new BufferedImage (640, 480, BufferedImage.TYPE_INT_ARGB);
        
        try {
            img = ImageIO.read(new  File(fileName));
        }
        catch (Exception e) {
            System.err.println("Image not found");
        }
        
        g.drawImage(img, 0, 0, null);
    }
}