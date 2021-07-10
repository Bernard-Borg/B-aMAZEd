public class Level{
    private int levelNo;
    private int [][] maze;
    
    public void setMaze (int [][] m){
        maze = m;
    }
    
    public void setLevelNo(int lN){
        levelNo = lN;
    }
    
    public int getLevelNo(){
        return levelNo;
    }
    
    public int [][] getMaze(){
        return maze;
    }
}