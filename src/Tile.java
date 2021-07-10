package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Tile{
    protected BufferedImage image;
    protected Rectangle rect;
    protected int col, row; //col and row represent the x and y values of the 2d array
    
    public Tile (String fileName){
        try {
            image = ImageIO.read(new  File(fileName));
        }
        catch (Exception e) {
            System.err.println("Error loading" + fileName);
        }
        rect =  new  Rectangle((25 * col),(25 * row), 25, 25); //col and row are multiplied by 25 since each square in the maze is 25px by 25px
    }
    
    public Tile(String fileName, int row, int col){
        setCol(col);
        setRow(row);
        try {
            image = ImageIO.read(new  File(fileName));
        }
        catch (Exception e) {
            System.err.println("Error loading" + fileName);
        }
        rect =  new  Rectangle((25 * col),(25 * row), 25, 25);
    }

    public BufferedImage getImage(){
        return image;
    }

    public void setImage(BufferedImage img){
        image = img;
    }
    
    public void setX(int nx){
        rect.setLocation (nx, (int)rect.getY());
    }
    
    public void setY(int ny){
        rect.setLocation((int)rect.getX(), ny);
    }
    
    public int getX(){
        return (int)rect.getX();
    }

    public int getY(){
        return (int)rect.getY();
    }
    
    public void setRow (int ry){
        row = ry;
    }
    
    public void setCol (int cx){
        col = cx;
    }
    
    public int getCol(){
        return col;
    }

    public int getRow(){
        return row;
    }
    
    public void draw(Graphics g){
        g.drawImage(image, (int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight(), null);
    }
    
    public void setCoords (int row, int col){
        setX(col*25);
        setY(row*25);
    }
}
