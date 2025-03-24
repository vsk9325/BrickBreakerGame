import java.awt.*;

public class MapGenerator {
    public int generatedMap[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col){
        generatedMap = new int[row][col];
        for(int i = 0;i<generatedMap.length;i++){
            for(int j = 0;j<generatedMap[0].length;j++){
                generatedMap[i][j] = 1;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    public void draw(Graphics2D g){
        for(int i = 0;i<generatedMap.length;i++){
            for(int j = 0;j<generatedMap[0].length;j++){
                if(generatedMap[i][j] > 0){
                    g.setColor(Color.white);
                    g.fillRect(j* brickWidth + 80, i * brickHeight + 50, brickWidth,brickHeight);

//                    for space between bricks
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j* brickWidth + 80, i * brickHeight + 50, brickWidth,brickHeight);
                }
            }
        }
    }
    public void setBrickValue(int value, int row, int col){
        generatedMap[row][col] = value;
    }

}
