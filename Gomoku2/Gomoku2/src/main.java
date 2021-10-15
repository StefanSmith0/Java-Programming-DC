import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/*this program would allow you to play the game gomoku
 * Author:Cadmiel Cruz
 * Date:1/26
 */




public class main implements ActionListener, MouseListener {

	String name;
	String secondname;
	
	final int player1 = 1;
	final int player2 = 2;
	final int BLANK = 0;
	int playerOnesScore = 0;
	int playerTwoScore = 0;
	
	JFrame board = new JFrame("Gomoku");
	JTextField name1 = new JTextField(5);
	JLabel playerone = new JLabel("Player 1"  );
	JLabel playertwo = new JLabel("Player 2");
	
	JButton reset = new JButton("Reset");
	JButton newGame = new JButton("New Game");
	JButton changeName1 = new JButton("Change Player one's name ");
	JButton changeName2 = new JButton("Change Player twos name");
	JRadioButton white = new JRadioButton();
	JRadioButton black = new JRadioButton();
	
	board Board = new board();

	Container north = new Container();
	Container south = new Container();
	Container center = new Container();
	
		public main() {
			board.setSize(300,300);
			board.setLayout(new BorderLayout()); //sets the layout
			board.add(north, BorderLayout.NORTH); //add the other class panel to the frame
			board.add(south, BorderLayout.SOUTH);//adds the container to the frame
			board.add(Board, BorderLayout.CENTER);
			
					
			//north container
			north.setLayout(new GridLayout(1,3));
			north.add(playerone);
			north.add(playertwo);
			north.add(name1);
			north.add(changeName1);
			north.add(changeName2);
			north.add(white);
			north.add(black);
			
			//south container
			south.setLayout(new GridLayout(1,2));
			south.add(reset);
			south.add(newGame);


			/*ButtonGroup group = new ButtonGroup();
			group.add(white);
			group.add(black);
			*/
			
			reset.addActionListener(this);
			newGame.addActionListener(this);
			changeName1.addActionListener(this);
			changeName2.addActionListener(this);
			white.addActionListener(this);
			black.addActionListener(this);

			board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//stops running the program when the user hits quit
			board.setVisible(true);//makes the frame visible
			
		
		
		
		}

		
	public static void main(String[] args) {
		new main();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(changeName1)) {
			name = name1.getText();
			playerone.setText(name + " win's: " + playerOnesScore);
		}
		if (e.getSource().equals(changeName2)) {
			secondname = name1.getText();
			playertwo.setText(secondname + " win's: " + playerTwoScore);
		}
		if(e.getSource().equals(reset)) {
			
		}
	}

}
