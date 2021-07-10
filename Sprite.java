public final class Sprite extends Tile{
    public Sprite (String fN, int x, int y){
        super (fN, x, y);
    }
    
    public void moveLeft(int px){
        int x = (int)rect.getX();
        int y = (int)rect.getY();
        rect.setLocation(x - px, y);
        setCol(getCol() - 1);
    }

    public void moveRight(int px){
        int x = (int)rect.getX();
        int y = (int)rect.getY();
        rect.setLocation(x + px, y);
        setCol(getCol() + 1);
    }

    public void moveUp(int px){
        int x = (int)rect.getX();
        int y = (int)rect.getY();
        rect.setLocation(x, y - px);
        setRow(getRow() - 1);
    }

    public void moveDown(int px){
        int x = (int)rect.getX();
        int y = (int)rect.getY();
        rect.setLocation(x, y + px);
        setRow(getRow() + 1);
    }
}
 