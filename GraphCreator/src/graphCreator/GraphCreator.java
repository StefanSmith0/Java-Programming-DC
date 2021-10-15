package graphCreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GraphCreator implements ActionListener, MouseListener {

	JFrame frame = new JFrame();
	GraphPanel panel = new GraphPanel();
	JButton nodeB = new JButton("Node");
	JButton edgeB = new JButton("Edge");
	JButton connectB = new JButton("Check Connection");
	String label = "<html>" + "Travelling Salesman" + "<br>" + "(Doesn't work)" + "</html>"; //Multiline JButton label code from java2s.com
	JButton salesmanB = new JButton(label);
	JTextField labelsTF = new JTextField("A");
	JTextField connect1TF = new JTextField("A");
	JTextField connect2TF = new JTextField("A");
	Container east = new Container();
	Container west = new Container();
	Node first = null;

	final int NODE_CREATE = 0;
	final int EDGE_FIRST = 1;
	final int EDGE_SECOND = 2;
	int state = NODE_CREATE;

	public GraphCreator() { 
		//UI setup
		frame.setSize(800, 400);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		east.setLayout(new GridLayout(4, 1));
		east.add(connect1TF);
		east.add(connectB);
		connectB.setOpaque(true);
		connectB.setBorderPainted(false);
		connectB.addActionListener(this);
		connectB.setBackground(Color.LIGHT_GRAY);
		east.add(connect2TF);
		east.add(salesmanB);
		salesmanB.setOpaque(true);
		salesmanB.setBorderPainted(false);
		salesmanB.addActionListener(this);
		salesmanB.setBackground(Color.LIGHT_GRAY);
		frame.add(east, BorderLayout.EAST);
		west.setLayout(new GridLayout(3, 1));
		west.add(nodeB);
		nodeB.setOpaque(true);
		nodeB.setBorderPainted(false);
		nodeB.addActionListener(this);
		nodeB.setBackground(Color.GREEN);
		west.add(edgeB);
		edgeB.setOpaque(true);
		edgeB.setBorderPainted(false);
		edgeB.addActionListener(this);
		edgeB.setBackground(Color.LIGHT_GRAY);
		west.add(labelsTF);
		frame.add(west, BorderLayout.WEST);
		panel.addMouseListener(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	//Constructor for GraphCreator
	public static void main(String args[]) {
		new GraphCreator();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	//Tracks clicks inside panel.
	public void mouseReleased(MouseEvent e) {
		if (state == NODE_CREATE) { //Create node 
			panel.addNode(e.getX(), e.getY(), labelsTF.getText());
		} else if (state == EDGE_FIRST) { //Set edge starting node
			Node n = panel.getNode(e.getX(), e.getY());
			if (n != null) {
				first = n;
				state = EDGE_SECOND;
				n.setHighlighted(true);
			}
		} else if (state == EDGE_SECOND) { //Set edge ending node
			Node n = panel.getNode(e.getX(), e.getY());
			if (n != null && !first.equals(n)) {
				first.setHighlighted(false);
				panel.addEdge(first, n, labelsTF.getText());
				first = null;
				state = EDGE_FIRST;
			}
		}

		frame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Runs Check Connection button.
		if (e.getSource().equals(connectB)) {
			panel.checkConnection(connect1TF.getText(), connect2TF.getText(), frame);
		}
		//Runs Create Node button.
		if (e.getSource().equals(nodeB)) {
			nodeB.setBackground(Color.GREEN);
			edgeB.setBackground(Color.LIGHT_GRAY);
			state = NODE_CREATE;
		}
		//Runs Create Edge button.
		if (e.getSource().equals(edgeB)) {
			edgeB.setBackground(Color.GREEN);
			nodeB.setBackground(Color.LIGHT_GRAY);
			state = EDGE_FIRST;
			panel.stopHighlighting();
			frame.repaint();
		}
	}
}
