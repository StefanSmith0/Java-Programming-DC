package com.stefan.farkle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Farkle implements ActionListener {
	
	//Constructor
	public static void main(String[] args) {
		new Farkle();
	}
		
	JFrame frame = new JFrame();
	Container diceContainer = new Container();	
	JButton[] diceButtons = new JButton[6];
	ImageIcon[] imageIcons = new ImageIcon[6];
	ImageIcon[] selectedImageIcons = new ImageIcon[6];
	int[] buttonState = new int[6];
	int[] dieValue = new int[6];
	final int HOT_DIE = 0;
	final int SCORE_DIE = 1;
	final int LOCKED_DIE = 2;
	Container buttonContainer = new Container();
	JButton rollButton = new JButton("Roll");
	JButton scoreButton = new JButton("Score");
	JButton stopButton = new JButton("Stop");
	Container labelContainer = new Container();
	JLabel currentScoreLBL = new JLabel("Current Score: 0");
	JLabel totalScoreLBL = new JLabel("Total Score: 0");
	JLabel currentRoundLBL = new JLabel("Current Round: 0");
	int currentScore = 0;
	int totalScore = 0;
	int currentRound = 1;
	Font customFont;
	
	//UI and game setup.
	public Farkle() {
		frame.setSize(600, 600);
		//Imports and registers custom font.
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("./fonts/BebasNeue-Regular.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);
			customFont = customFont.deriveFont(18f);
		}
		catch (Exception e) {
			System.out.println("Couldn't load font");
			customFont = new Font("serif", Font.PLAIN, 24);
		}
		imageIcons[0] = new ImageIcon(getScaledImage("./png/one.png", 180, 180));
		imageIcons[1] = new ImageIcon(getScaledImage("./png/two.png", 180, 180));
		imageIcons[2] = new ImageIcon(getScaledImage("./png/three.png", 180, 180));
		imageIcons[3] = new ImageIcon(getScaledImage("./png/four.png", 180, 180));
		imageIcons[4] = new ImageIcon(getScaledImage("./png/five.png", 180, 180));
		imageIcons[5] = new ImageIcon(getScaledImage("./png/six.png", 180, 180));
		selectedImageIcons[0] = new ImageIcon(getScaledImage("./png/oneSelected.png", 180, 180));
		selectedImageIcons[1] = new ImageIcon(getScaledImage("./png/twoSelected.png", 180, 180));
		selectedImageIcons[2] = new ImageIcon(getScaledImage("./png/threeSelected.png", 180, 180));
		selectedImageIcons[3] = new ImageIcon(getScaledImage("./png/fourSelected.png", 180, 180));
		selectedImageIcons[4] = new ImageIcon(getScaledImage("./png/fiveSelected.png", 180, 180));
		selectedImageIcons[5] = new ImageIcon(getScaledImage("./png/sixSelected.png", 180, 180));
		diceContainer.setLayout(new GridLayout(2, 3));
		for (int a = 0; a < diceButtons.length; a++) {
			diceButtons[a] = new JButton();
			diceButtons[a].setIcon(imageIcons[a]);
			diceButtons[a].setBorderPainted(false);
			diceButtons[a].setEnabled(false);
			diceButtons[a].addActionListener(this);
			diceContainer.add(diceButtons[a]);
		}
		buttonContainer.setLayout(new GridLayout(1, 3));
		rollButton.setFont(customFont);
		rollButton.addActionListener(this);
		buttonContainer.add(rollButton);
		scoreButton.setFont(customFont);
		scoreButton.setEnabled(false);
		scoreButton.addActionListener(this);
		buttonContainer.add(scoreButton);
		stopButton.setFont(customFont);
		stopButton.setEnabled(false);
		stopButton.addActionListener(this);
		buttonContainer.add(stopButton);
		labelContainer.setLayout(new GridLayout(1, 3));
		currentScoreLBL.setFont(customFont);
		labelContainer.add(currentScoreLBL);
		totalScoreLBL.setFont(customFont);
		labelContainer.add(totalScoreLBL);
		currentRoundLBL.setFont(customFont);
		currentRoundLBL.setText("Current Round: " + currentRound);
		labelContainer.add(currentRoundLBL);
		
		
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.WHITE);
		frame.add(diceContainer, BorderLayout.CENTER);
		frame.add(buttonContainer, BorderLayout.SOUTH);
		frame.add(labelContainer, BorderLayout.NORTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//Image resizing method written by Suken Shah - https://stackoverflow.com/users/845849/suken-shah
	public Image getScaledImage(String imgLocation, int w, int h){
		Image srcImg = new ImageIcon(imgLocation).getImage();
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}

	//Manages player actions.
	public void actionPerformed(ActionEvent event) {
		//Manages the roll button.
		if(event.getSource().equals(rollButton)) {
			for (int a = 0; a < diceButtons.length; a++) {
				if (buttonState[a] == HOT_DIE) {
					int choice = (int)(Math.random() * 6);
					dieValue[a] = choice;
					diceButtons[a].setIcon(imageIcons[dieValue[a]]);
					diceButtons[a].setEnabled(true);
				}
			}
			rollButton.setEnabled(false);
			scoreButton.setEnabled(true);
			stopButton.setEnabled(false);
		}
		//Manages the score button.
		else if (event.getSource().equals(scoreButton)) {
			int [] valueCount = new int[7];
			for (int a = 0; a < diceButtons.length; a++) {
				if (buttonState[a] == SCORE_DIE) {
					valueCount[dieValue[a] + 1]++;
				}
			}
			if (valueCount[2] > 0 && valueCount[2] < 3 || valueCount[3] > 0 && valueCount[3] < 3
					|| valueCount[4] > 0 && valueCount[4] < 3 || valueCount[6] > 0 && valueCount[6] < 3){
				//Invalid die selection
				JOptionPane.showMessageDialog(frame, "Invalid die selected.");
			}
			else if (valueCount[1] == 0 && valueCount[2] == 0 && valueCount[3] == 0 &&
					 valueCount[4] == 0 && valueCount[5] == 0 && valueCount[6] == 0) {
					Object[] options = {"Yes", "No"};
					int dialogChoice = JOptionPane.showOptionDialog(frame, "Forfeit Score?", "Forfeit Score?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if (dialogChoice == JOptionPane.YES_OPTION) {
						currentScore = 0;
						currentRound++;
						currentScoreLBL.setText("Current Score: " + currentScore);
						currentRoundLBL.setText("Current Round: " + currentRound);
						resetDice();
					}
				}
			else {
				//Tallies up score.
				if (valueCount[1] >= 3) {
					currentScore += (valueCount[1] - 2) * 1000;
				}
				if (valueCount[2] >= 3) {
					currentScore += (valueCount[2] - 2) * 200;
				}
				if (valueCount[3] >= 3) {
					currentScore += (valueCount[3] - 2) * 300;
				}
				if (valueCount[4] >= 3) {
					currentScore += (valueCount[4] - 2) * 400;
				}
				if (valueCount[5] >= 3) {
					currentScore += (valueCount[5] - 2) * 500;
				}
				if (valueCount[6] >= 3) {
					currentScore += (valueCount[6] - 2) * 600;
				}
				if (valueCount[1] < 3) {
					currentScore += valueCount[1] * 100;
				}
				if (valueCount[5] < 3) {
					currentScore += valueCount[5] * 50;
				}
				currentScoreLBL.setText("Current Score: " + currentScore);
				for (int a = 0; a < diceButtons.length; a++) {
					if (buttonState[a] == SCORE_DIE) {
						buttonState[a] = LOCKED_DIE;
					}
					diceButtons[a].setEnabled(false);
				}
				int lockedCount = 0;
				for (int a = 0; a < diceButtons.length; a++) {
					if (buttonState[a] == LOCKED_DIE) {
						lockedCount++;
					}
				}
				if (lockedCount == 6) {
					for (int a = 0; a < valueCount.length; a++) {
						buttonState[a] = HOT_DIE;
						diceButtons[a].setIcon(imageIcons[dieValue[a]]);
					}
				}
				rollButton.setEnabled(true);
				scoreButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
		}
		//Manages the stop button.
		else if (event.getSource().equals(stopButton)) {
			totalScore += currentScore;
			currentScore = 0;
			currentScoreLBL.setText("Current Score: " + currentScore);
			totalScoreLBL.setText("Total Score: " + totalScore);
			currentRound++;
			currentRoundLBL.setText("Current Round: " + currentRound);
			resetDice();
		}
		else for (int a = 0; a < diceButtons.length; a++) {
			if (event.getSource().equals(diceButtons[a])) {
				if (buttonState[a] == HOT_DIE) {
					diceButtons[a].setIcon(selectedImageIcons[dieValue[a]]);
					buttonState[a] = SCORE_DIE;
				}
				else {
					diceButtons[a].setIcon(imageIcons[dieValue[a]]);
					buttonState[a] = HOT_DIE;
				}
			}
		}
	}
	//Resets dice and buttons.
	public void resetDice() {
		for (int a = 0; a < diceButtons.length; a++) {
			diceButtons[a].setEnabled(false);
			buttonState[a] = HOT_DIE;
			diceButtons[a].setIcon(imageIcons[dieValue[a]]);
		}
		rollButton.setEnabled(true);
		scoreButton.setEnabled(false);
		stopButton.setEnabled(false);
	}
}
