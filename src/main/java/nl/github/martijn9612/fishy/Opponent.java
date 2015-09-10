package nl.github.martijn9612.fishy;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Opponent extends Entity {
  private String left = Main.OPPONENT_CHARACTER + "left";
  private String right = Main.OPPONENT_CHARACTER + "right";
  private boolean isleft;
  private int size;
  public OpponentHandler opponentHandler;

  /**
   * constructor for fishes.
   * @param isleft whether the fish spawns left or right.
   * @param xpos the x position.
   * @param ypos the y position.
   * @param size the size of the fish.
   * @param speed the speed of the fish.
   * @param opponentHandler the handler of all the opponents
   */
  public Opponent(boolean isleft, int xpos, int ypos, int size, int speed, OpponentHandler opponentHandler) {
    this.isleft = isleft;
    this.size = 50 * size;
    this.opponentHandler = opponentHandler;
    if (isleft) {
      this.loadImage(right);
    } else {
      this.loadImage(left);
    }
    this.setPosition(xpos, ypos);
    this.setDimensions(this.size, this.size);
    this.setSpeed(speed);
    this.createRectangle();
  }
  /**
   * logic.
   */
  public void objectLogic(GameContainer gc, int deltaTime) {
    if (this.isleft) {
      goright();
    } else {
      goleft();
    }
  }
  
  @Override
  public void renderObject(Graphics g) {
    g.drawImage(this.getImage(), this.getX(), this.getY());
  }
  /**
   * make the fish go left.
   */
  public void goright() {
    this.x += speed;
    this.objectRect.x += speed;
    if (this.x > (615 + this.size)) {
      destroy();
    }
  }
  /**
   * make the fish go right.
   */
  public void goleft() {
    this.x -= speed;
    this.objectRect.x -= speed;
    if (this.x < (0 - this.size)) {
      destroy();
    }
  }
  
  public void destroy() {
    opponentHandler.destroy(this);
  }
}