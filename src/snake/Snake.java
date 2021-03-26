package snake;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Snake extends JPanel implements KeyListener {
  
  private final ArrayList segment;
  private static int direction = 1;
  private final SnakePart apple1;
  private final SnakePart apple2;
  private final SnakePart apple3;
  private static boolean moving = false;
  private static int playingState = 0;
  private static int choice = 1;
  private static boolean enter = false;
  private static int col = 1;
  private static int highscore = 0;
  private final Color wall;
  private final Color snake;
  private final Color alt;
  private final Color apple;
  
  public Snake() {
    wall = new Color(0, 178, 255);
    snake = Color.green;
    alt = Color.white;
    apple = Color.red;
    addKeyListener(this);
    segment = new ArrayList();
    for (int i = 0; i < 20; i++) {
      SnakePart snakepart = new SnakePart(i + 15, 30, 1);
      segment.add(snakepart);
    } 
    apple1 = new SnakePart(25, 25, 0);
    apple2 = new SnakePart(30, 25, 0);
    apple3 = new SnakePart(20, 25, 0);
  }
  
  public void moveSnake() {
    for (int i = 0; i < this.segment.size() - 1; i++) {
      SnakePart Temp = (SnakePart) segment.get(i);
      SnakePart tEmp = (SnakePart) segment.get(i + 1);
      Temp.setD(tEmp.getD());
    } 
    SnakePart temp = (SnakePart) segment.get(segment.size() - 1);
    temp.setD(direction);
    for (int j = 0; j < segment.size(); j++) {
      SnakePart Temp = (SnakePart) segment.get(j);
      switch (Temp.getD()) {
        case 0:
          if (Temp.getY() + 1 != 49) {
            Temp.setY(Temp.getY() + 1);
            break;
          } 
          Temp.setY(1);
          break;
        case 1:
          if (Temp.getX() + 1 != 49) {
            Temp.setX(Temp.getX() + 1);
            break;
          } 
          Temp.setX(1);
          break;
        case 2:
          if (Temp.getY() - 1 != 0) {
            Temp.setY(Temp.getY() - 1);
            break;
          } 
          Temp.setY(48);
          break;
        case 3:
          if (Temp.getX() - 1 != 0) {
            Temp.setX(Temp.getX() - 1);
            break;
          } 
          Temp.setX(48);
          break;
      } 
    } 
  }
  
  public int collision(int alt) {
    SnakePart first = (SnakePart) segment.get(this.segment.size() - 1);
    for (int i = 0; i < this.segment.size() - 3; i++) {
      SnakePart temp = (SnakePart) segment.get(i);
      if (first.getX() == temp.getX() && first.getY() == temp.getY())
        return 1; 
    } 
    int mult = 1;
    if (first.getX() == this.apple1.getX() && first.getY() == this.apple1.getY()) {
      addSegment(mult);
      newApple(1);
    } else if (first.getX() == this.apple2.getX() && first.getY() == this.apple2.getY()) {
      addSegment(mult);
      newApple(2);
    } else if (first.getX() == this.apple3.getX() && first.getY() == this.apple3.getY()) {
      addSegment(mult);
      newApple(3);
    } 
    if (alt != 1 && (first.getX() == 1 || first.getX() == 48 || first.getY() == 1 || first.getY() == 48))
      return 1; 
    return 0;
  }
  
  public void addSegment(int mult) {
    for (int i = 0; i < mult; i++) {
      SnakePart temp = (SnakePart) segment.get(0);
      int x = temp.getX();
      int y = temp.getY();
      switch (temp.getD()) {
        case 0:
          y--;
          break;
        case 1:
          x--;
          break;
        case 2:
          y++;
          break;
        case 3:
          x++;
          break;
      } 
      SnakePart snakepart = new SnakePart(x, y, temp.getD());
      segment.add(0, snakepart);
    } 
  }
  
  public void newApple(int apple) {
    Random r = new Random();
    switch (apple) {
      case 1:
        apple1.setX(r.nextInt(40) + 9);
        apple1.setY(r.nextInt(44) + 5);
        break;
      case 2:
        apple2.setX(r.nextInt(40) + 9);
        apple2.setY(r.nextInt(44) + 5);
        break;
      case 3:
        apple3.setX(r.nextInt(40) + 9);
        apple3.setY(r.nextInt(44) + 5);
        break;
    } 
  }
  
  public void paint(Graphics g1) {
    super.paint(g1);
    Graphics2D g = (Graphics2D)g1;
    g.setStroke(new BasicStroke(1.0F));
    Font f = new Font("SansSerif Bold", 1, 20);
    g.setFont(f);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(wall);
    g.fillRect(0, 0, 500, 500);
    g.setColor(Color.black);
    g.fillRect(10, 10, 480, 480);
    for (int i = 0; i < segment.size(); i++) {
      SnakePart temp = (SnakePart) segment.get(i);
      g.setColor(snake);
      g.fillOval(temp.getX() * 10, temp.getY() * 10, 10, 10);
    } 
    moving = false;
    switch (playingState) {
      case 1:
        g.setColor(apple);
        g.fillOval(apple1.getX() * 10, apple1.getY() * 10, 10, 10);
        g.fillOval(apple2.getX() * 10, apple2.getY() * 10, 10, 10);
        g.fillOval(apple3.getX() * 10, apple3.getY() * 10, 10, 10);
        g.setColor(alt);
        g.drawString("Current Length: " + segment.size(), 10, 30);
        break;
      case 0:
        if (choice == 1) {
          g.setColor(alt);
          g.fillRect(170, 20, 160, 60);
          if (enter)
            playingState = 1; 
        } else if (choice == 2) {
          g.setColor(Color.white);
          g.fillRect(170, 80, 160, 60);
          g.setStroke(new BasicStroke(3.0F));
          g.fillRect(230, 200, 40, 40);
          g.fillRect(230, 250, 40, 40);
          g.fillRect(180, 225, 40, 40);
          g.fillRect(280, 225, 40, 40);
          g.drawString("Control the direction of your", 110, 170);
          g.drawString("snake using the arrow keys", 115, 190);
          g.setColor(Color.black);
          g.drawLine(250, 210, 250, 230);
          g.drawLine(250, 210, 240, 220);
          g.drawLine(250, 210, 260, 220);
          g.drawLine(250, 260, 250, 280);
          g.drawLine(250, 280, 240, 270);
          g.drawLine(250, 280, 260, 270);
          g.drawLine(190, 245, 210, 245);
          g.drawLine(190, 245, 200, 255);
          g.drawLine(190, 245, 200, 235);
          g.drawLine(290, 245, 310, 245);
          g.drawLine(310, 245, 300, 235);
          g.drawLine(310, 245, 300, 255);
        } 
        g.setColor(wall);
        g.fillRect(180, 30, 140, 40);
        g.fillRect(180, 90, 140, 40);
        g.setColor(alt);
        g.drawString("Play", 230, 50);
        g.drawString("How to Play", 190, 110);
        break;
      case 2:
        if (choice == 1) {
          g.setColor(alt);
          g.fillRect(170, 20, 160, 60);
          if (enter)
            playingState = 1; 
        } else if (choice == 2) {
          g.setColor(alt);
          g.fillRect(170, 80, 160, 60);
          if (enter)
            System.exit(0); 
        } 
        g.setColor(wall);
        g.fillRect(180, 30, 140, 40);
        g.fillRect(180, 90, 140, 40);
        g.setColor(alt);
        g.drawString("Play Again", 190, 50);
        g.drawString("Exit Game", 190, 110);
        g.drawString("Final Score: " + segment.size(), 170, 170);
        g.drawString("High Score: " + highscore, 170, 190);
        break;
    } 
    enter = false;
  }
  
  public void reset() {
    direction = 1;
    moving = false;
    playingState = 1;
    choice = 1;
    enter = false;
    int i;
    for (i = 0; i < segment.size();)
      segment.remove(i); 
    for (i = 0; i < 20; i++) {
      SnakePart snakepart = new SnakePart(i + 15, 30, 1);
      segment.add(i, snakepart);
    } 
    apple1.setX(25);
    apple1.setY(25);
    apple2.setX(30);
    apple2.setY(25);
    apple3.setX(20);
    apple3.setY(25);
  }
  
  public void keyPressed(KeyEvent e) {
    int dir = e.getKeyCode();
    if (!moving && playingState == 1) {
      if (dir == 40 && direction != 2) {
        direction = 0;
        moving = true;
      } else if (dir == 39 && direction != 3) {
        direction = 1;
        moving = true;
      } else if (dir == 38 && direction != 0) {
        direction = 2;
        moving = true;
      } else if (dir == 37 && direction != 1) {
        direction = 3;
        moving = true;
      } 
    } else if (playingState == 0 || playingState == 2) {
      if (dir == 40 && choice + 1 == 2) {
        choice++;
      } else if (dir == 38 && choice - 1 == 1) {
        choice--;
      } 
      if (dir == 10)
        enter = true; 
    } 
  }
  
  static class SnakePart {
      
    private int x;
    private int y;
    private int d;
    
    public SnakePart(int a_x, int a_y, int a_d) {
      x = a_x;
      y = a_y;
      d = a_d;
    }
    
    public void setX(int ax) {
      x = ax;
    }
    
    public void setY(int ay) {
      y = ay;
    }
    
    public void setD(int ad) {
      d = ad;
    }
    
    public int getX() {
      return x;
    }
    
    public int getY() {
      return y;
    }
    
    public int getD() {
      return d;
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    JFrame frame = new JFrame("Snake!!");
    Snake s = new Snake();
    s.setFocusable(true);
    s.requestFocusInWindow();
    frame.add(s);
    frame.setSize(515, 535);
    frame.setLocation(520, 185);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(3);
    int g = 1;
    while (true) {
      while (playingState == 0)
        s.repaint(); 
      while (s.collision(col) != 1) {
        Thread.sleep(65L);
        s.moveSnake();
        s.repaint();
      } 
      if (s.segment.size() > highscore)
        highscore = s.segment.size(); 
      playingState = 2;
      while (playingState == 2)
        s.repaint(); 
      s.reset();
    } 
  }
  
  public void keyTyped(KeyEvent e) {}
  
  public void keyReleased(KeyEvent e) {}
}
