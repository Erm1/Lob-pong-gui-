/*
Ermiyas Liyeh
30711570
Lab Section - MW 2:00PM to 3:15PM 
I did collaborate on this assignment.

Group member, Beakal Lemeneh
*/

//I imported different classes from the java library 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")

//I created a class called Playgame that extends JPanel and implements KeyListener and ActionListener
public class Playgame extends JPanel implements KeyListener, ActionListener{

	//I created a boolean field play and set it false 
	private boolean play = false;
	
	
	private int score = 0;
	private int highScore ;
	
	private int level = 1;
	private int lives = 3;
	
	//I created an ArrayList to keep track of high score
	private ArrayList <Integer> scoreList = new ArrayList<>();
	
	//I have a constant G for gravity and THETA
	private final double G = 9.81;
	private final int THETA = 83;

	//I have set a delay time for the animation
	private int delay = 2;
	private Timer timer;
	
	//I have set an initial position for the paddle as well X and Y axis for the initial center of the ball
	private int playerX = 310;
	private int X = 10;
	private int Y = 1000;
	
	//I have set an initial speed, timex and timey values
	private int initVelo = 60;
	private double timex =0.1,timey =0.1;
	
	@SuppressWarnings("unused")
	//The following three lines of codes are used when analyzing a projectile motion
	private final double hmax = (Math.pow(initVelo, 2))*Math.pow(Math.sin(Math.toRadians(THETA)), 2)/(2*G);
	private int velx = (int) Math.ceil((initVelo * Math.cos(Math.toRadians(THETA)) * timex)) ;
	private int vely = (int)(initVelo * Math.sin(Math.toRadians(THETA)) * timey - G * timey * timey / 2); 
	
	
	//the constructor adds a keylistener to allow the paddle 
	//move back and forth as the user presses the left and right arrow keys
	public Playgame() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		//I added delay to my timer
		timer = new Timer(delay, this);
		//Animation starts as the timer is set to start
		timer.start();
	}
	
	
	@Override
	//Here is my paintComponent method, I have created paddle, ball, a string drawing and many more
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//set background color
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		//set borders
		//I used a randomly generated color to change the color of the border and the game name continuously
		int gr = (int) (Math.random()*256);
		int bl = (int) (Math.random()*256);
		int re = (int) (Math.random()*256);
		Color col = new Color(re, gr, bl);
		g.setColor(col);
		
		//the next three lines of codes are used to draw the borders
		//It's resizable
		g.fillRect(0, 0, 4, getHeight());
		g.fillRect(0, 0, getWidth(), 3);
		g.fillRect(getWidth()-3, 0, 3, getHeight());
		
		
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, getHeight()-20, 150, 10);
		
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(X, Y, 20, 20);
		
		
		//this code is used for a different randomly generated color
		int green = (int) (Math.random()*256);
		int blue = (int) (Math.random()*256);
		int red = (int) (Math.random()*256);
		Color color = new Color(red, green, blue);
		
		g.setColor(color);
		//the next four lines are used to make lines to make 
		//three equal boxes to enter lives, level and so on
		g.drawRect(0, 0, getWidth(), 80);
		g.drawRect(0, 80, getWidth(), 150);
		g.drawLine(getWidth()/3, 80, getWidth()/3, 230);
		g.drawLine(2*getWidth()/3, 80, 2*getWidth()/3, 230);
		
		//used to draw a string "Lob Pong" at the top center of the screen
		g.setColor(color);
		g.setFont(new Font("serif", Font.BOLD, 30));
		g.drawString("Lob Pong", (getWidth()/2)-100, 50);
		
		//setting a color
		g.setColor(Color.WHITE);
				
		//drawing a string "Lives" and keep track of number of lives
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Lives : "+lives , 20, 200);
		
		//drawing a string "Level" and keep track of the level
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Level : "+level, 20, 150);
		
		//drawing the high score of all time
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("High score : "+highScore, (getWidth()/3) + 20, 150);
		
		//drawing the score for each life
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score : "+score, (2*getWidth()/3) + 20, 150);
		
		
		if(Y > getHeight()) {
			//if the ball passes the paddle, the game ends, 
			//velx and vely become 0 and level is set back to default which is 0
			play = false;
			velx = 0;
			vely = 0;
			level = 1;
			
			//every time the player fails, the game outputs the score 
			g.setColor(Color.WHITE);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over! Score: "+score, getWidth()/3, 100+getHeight()/2);
			
			//tells the user to press enter to restart the game
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press \"Enter\" to Restart", getWidth()/3, 150+getHeight()/2);
			
			if(lives==1) {
				//if the user runs out of life, the following blocks of statements are drawn on the screen
				g.setColor(Color.WHITE);
				g.setFont(new Font("serif", Font.BOLD, 30));
				g.drawString("You are out of life!", getWidth()/3, 50+getHeight()/2);
				g.drawString("High Score: "+highScore, getWidth()/3, getHeight()/2);
			}
		}
		
		if((score+2)%10 == 0) {
			//each bounce has a score of 2
			//if a player gets 10 points, the level increases by one 
			//and the motion of the ball also changes accordingly
			g.setColor(Color.WHITE);
			g.setFont(new Font("serif", Font.BOLD, 60));
			g.drawString("YOU ARE ON LEVEL "+level, getWidth()/4, getHeight()/2);
		}

		g.dispose();
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		
//		int hmax = (int)(initVelo*initVelo*Math.sin(Math.toRadians(THETA)) *Math.sin(Math.toRadians(THETA))/(2*G));

		if(play) {
			//System.out.println(velx+"   "+vely);
			//to bounce back the ball when it hits the paddle
			
			//if the paddle touches the ball, 
			//the ball bounces back in the y-direction while continuing the same motion in the x-direction
			if(new Rectangle(X, Y, 20, 20).intersects(new Rectangle(playerX, getHeight()-20, 150, 10))) {
				vely = -vely;
				
				//score increases by two for each effective bounce back by the paddle
				score += 2;
				
				if((score+2)%10 == 0) {
					//the following couple of lines increase difficulty of 
					//the game as the game progresses to the next level
					level += 1;
					velx *= 2;
					vely += level;
					X = 20;
					Y = 1000;
					playerX = 310;
				}
				
				if(lives>=1) {
				//these lines of codes are used to select the highest score from the collection
				scoreList.add(score);
				Integer i = Collections.max(scoreList);
				highScore = i;
				
				}
				
				
			}
			
			//this are the formulas for updating X and Y
			X += velx;
			Y -= vely;
			
			if(Y<230) {
				//if y is less than 230, the ball changes its Y-direction motion
				vely = -vely;
			}
			
			if(X>=getWidth()-20) {
				//bouncing the ball off the right side of the wall
				velx = -velx;
			}
			
			if(X<0) {
				//bouncing the ball off the left side of the wall
				velx = -velx;
			}
			
			
		}
		//Calling repaint so that previous drawing of 
		//the ball doesn't appear while the new X and Y component of the ball chages with time
		repaint();
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//user can use right arrow or "F" on the keyboard to move to right
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_F) {
			//if the paddle approaches the right side of the wall, it can't go further to the right
			if(playerX>getWidth()-140) {
				playerX = getWidth()-140;
			}
			else {
				//calling a method for the right forward movement of the paddle
				moveRight();
			}
		}
		
		//user can use left arrow or "S" on the keyboard to move to right
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_S) {
			//if the paddle approaches the left side of the wall, it can't go further to the left
			if(playerX<=0) {
				playerX = 0;
			}
			else {
				//calling a method for the left forward movement of the paddle
				moveLeft();
			}
		}
		//user can use "Enter" key to restart the game
		//if the user restarts everything except high score and number of lives goes to default value
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				X = 10;
				Y = 1000;
				
				timex = 0.1;
				timey = 0.1;
				velx = (int) Math.ceil((initVelo * Math.cos(Math.toRadians(THETA)) * timex)) ;//velx = 0;
				vely = (int)Math.ceil(initVelo * Math.sin(Math.toRadians(THETA)) * timey - G * timey * timey / 2); ;//vely = 0;
				highScore = score;
				playerX = 310;
				score = 0;
				lives -= 1;
				if(lives==0) {
					lives = 3;
				}
				
				repaint();
			}
		}
		
	}
	//a method that moves the paddle to the right
	public void moveRight() {
		play = true;
		playerX += 20;
		
	}
	//a method that moves the paddle to the left
	public void moveLeft() {
		play = true;
		playerX -= 20;
		
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}

}
