package graphCreator;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {

	ArrayList<Node> nodeList = new ArrayList<Node>();
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	int circleRadius = 20;

	ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();

	public GraphPanel() {
		super();
	}

	// Gets nodes.
	public Node getNode(int x, int y) {
		for (int a = 0; a < nodeList.size(); a++) {
			Node node = nodeList.get(a);
			// a squared plus b squared equals c squared
			double radius = Math.sqrt(Math.pow(x - node.getX(), 2) + Math.pow(y - node.getY(), 2));
			if (radius <= circleRadius) {
				return node;
			}
		}
		return null;
	}

	// Prints adjacency matrix to console.
	public void printAdjacency() {
		for (int a = 0; a < adjacency.size(); a++) {
			for (int b = 0; b < adjacency.get(0).size(); b++) {
				System.out.print(adjacency.get(a).get(b) + "\t");
			}
			System.out.println();
		}
	}

	// Adds new nodes to nodeList.
	public void addNode(int newx, int newy, String newlabel) {
		nodeList.add(new Node(newx, newy, newlabel));
		adjacency.add(new ArrayList<Boolean>());
		for (int a = 0; a < adjacency.size() - 1; a++) {
			adjacency.get(a).add(false);
		}

		for (int a = 0; a < adjacency.size(); a++) {
			adjacency.get(adjacency.size() - 1).add(false);
		}
		printAdjacency();
	}

	// Adds new edges to edgeList.
	public void addEdge(Node first, Node second, String newlabel) {
		edgeList.add(new Edge(first, second, newlabel));
		int firstIndex = 0;
		int secondIndex = 0;
		for (int a = 0; a < nodeList.size(); a++) {
			if (first.equals(nodeList.get(a))) {
				firstIndex = a;
			}
			if (second.equals(nodeList.get(a))) {
				secondIndex = a;
			}
		}
		// Update the adjacency matrix
		adjacency.get(firstIndex).set(secondIndex, true);
		adjacency.get(secondIndex).set(firstIndex, true);

		printAdjacency();
	}

	// Draws everything in panel.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int a = 0; a < nodeList.size(); a++) { // draw nodes
			if (nodeList.get(a).getHighlighted() == true) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.black);
			}
			g.drawOval(nodeList.get(a).getX() - circleRadius, nodeList.get(a).getY() - circleRadius, circleRadius * 2,
					circleRadius * 2);
			g.drawString(nodeList.get(a).getLabel(), nodeList.get(a).getX(), nodeList.get(a).getY());
		}
		for (int a = 0; a < edgeList.size(); a++) { // draw edges
			g.setColor(Color.black);
			g.drawLine(edgeList.get(a).getFirst().getX(), edgeList.get(a).getFirst().getY(),
					edgeList.get(a).getSecond().getX(), edgeList.get(a).getSecond().getY());
			int fx = edgeList.get(a).getFirst().getX();
			int fy = edgeList.get(a).getFirst().getY();
			int sx = edgeList.get(a).getSecond().getX();
			int sy = edgeList.get(a).getSecond().getY();
			g.drawString(edgeList.get(a).getLabel(), Math.min(fx, sx) + (Math.abs(sx - fx) / 2),
					Math.min(fy, sy) + (Math.abs(sy - fy) / 2));
		}
	}

	// Un-highlights everything.
	public void stopHighlighting() {
		for (int a = 0; a < nodeList.size(); a++) {
			nodeList.get(a).setHighlighted(false);
		}
	}

	// Checks connection between two nodes.
	public void checkConnection(String connect1TF, String connect2TF, JFrame frame) {
		int firstIndex = 999999;
		int secondIndex = 999999;
		for (int a = 0; a < nodeList.size(); a++) { // search for nodes
			if (connect1TF.equals(nodeList.get(a).getLabel())) {
				firstIndex = a;
			}
			if (connect2TF.equals(nodeList.get(a).getLabel())) {
				secondIndex = a;
			}
		}
		if (firstIndex != 999999 && secondIndex != 999999) { // found both nodes?
			if (adjacency.get(firstIndex).get(secondIndex).equals(true)) { //nodes are connected?
				JOptionPane.showMessageDialog(frame, "Nodes are connected.");
			} else {
				JOptionPane.showMessageDialog(frame, "Nodes are not connected.");
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Nodes not found.");
		}
	}
}
