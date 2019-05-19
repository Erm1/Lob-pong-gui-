/*
Ermiyas Liyeh
30711570
Lab Section - MW 2:00PM to 3:15PM 
I did collaborate on this assignment.

Group member, Beakal Lemeneh
*/

//I imported JFrame from java library
import javax.swing.JFrame;

public class LobPong {
	public static void main(String [] args) {
		
		//here, I created an object of type JFrame called frame
		JFrame frame = new JFrame("Lob Pong");
		
		//here, I created an instance of Playgame class called playGame
		Playgame playGame = new Playgame();
		
		//I set a size, title and resizablity properties
		frame.setSize(1400, 1200);
		frame.setTitle("Lob Pong");
		frame.setResizable(true);
		
		//I set a visibility and default closing operations
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//I finally added playGame to my frame
		frame.add(playGame);
		
		
	}
}
