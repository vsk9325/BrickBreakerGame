import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

     private boolean play = false;
     private int score = 0;

     private int totalBricks = 21;

     private Timer timer;
     private int delay = 6;

     private int playerX = 310;

     private int ballposX = 120;
     private int ballposY = 350;

//     direction values
     private int ballXdir = -3;
     private int ballYdir = -6;

//     mapgenerator
    private MapGenerator map;

    public GamePlay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();

        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){
        super.paintComponent(g);
//        background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

//        calling draw from mapGenerator
        map.draw((Graphics2D) g);

//        scores
        g.setColor(Color.white);
        g.setFont(new Font("Dialog",Font.BOLD, 25));
        g.drawString(""+score,640,40);

//        borders
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(683,0,3,592);
//
////        the paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

//        the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks <=0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Dialog",Font.BOLD,30));
            g.drawString("You Won!!",190,300);

            g.setFont(new Font("Dialog",Font.BOLD,20));
            g.drawString("Please Enter to Restart",230,350);

        }

        if(ballposY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("Dialog",Font.BOLD,30));
            g.drawString("Game Over, Score: "+score,190,300);

            g.setFont(new Font("Dialog",Font.BOLD,20));
            g.drawString("Please Enter to Restart",230,350);

        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
//            intersection with paddle
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }

//            ball and brick intersection logic
            A: for(int i = 0;i<map.generatedMap.length;i++){
                for(int j= 0;j<map.generatedMap[0].length;j++){
                    if(map.generatedMap[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;
                        if(brickRect.intersects(ballRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score += 5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }
                            else{
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }

                    }
                }
            }

             ballposX += ballXdir;
             ballposY += ballYdir;
             if(ballposX < 0){
                 ballXdir = - ballXdir;
             }
             if(ballposY < 0){
                 ballYdir = - ballYdir;
             }
             if(ballposX > 670){
                 ballXdir = - ballXdir;
             }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 580) {
                playerX = 580;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX<=0){
                playerX = 0;
            }else{
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -3;
                ballYdir = -6;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);
                repaint();
            }
        }
    }

    public void moveRight(){
        play = true;
        playerX += 20;
        repaint();
    }
    public void moveLeft(){
        play = true;
        playerX -= 20;
        repaint();
    }


}
