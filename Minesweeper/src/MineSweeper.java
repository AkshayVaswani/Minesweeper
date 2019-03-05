import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
public class MineSweeper extends JPanel implements ActionListener, MouseListener {

	JFrame frame;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem item1;
	JMenuItem item2;
	JMenuItem item3;
	JPanel panel;
	JToggleButton[][] togglers;
	String[][] board;
	int dimensionx = 9;
	int dimensiony = 9;
	ImageIcon mine;
	ImageIcon faceIcon;
	JButton smileyFace;
	int mineCount = 10;

	public MineSweeper()
	{
		frame=new JFrame("Minesweeper");
		frame.add(this);
		menuBar=new JMenuBar();
		menu = new JMenu("Game");
		item1 = new JMenuItem("Beginner"); 
		item2 = new JMenuItem("Intermediate");
		item3 = new JMenuItem("Expert");
		
		faceIcon = new ImageIcon("smiley.png");
		faceIcon = new ImageIcon(faceIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
		smileyFace = new JButton("");
		smileyFace.setIcon(faceIcon);
		
		menu.add(item1);
		menu.add(item2);
		menu.add(item3);
		menuBar.add(menu);
		
		item1.addActionListener(this);
		item2.addActionListener(this);
		item3.addActionListener(this);		
		mine = new ImageIcon("mine.png");
		menuBar.add(smileyFace, BorderLayout.CENTER);
		smileyFace.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
		frame.add(menuBar, BorderLayout.NORTH);
		
		nickIsNeon(dimensionx, dimensiony, mineCount); 
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
	
	
	public void nickIsNeon(int dimX, int dimY, int mines){

		frame.setSize(50*dimY,50*dimX);
		mine = new ImageIcon( mine.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		panel = new JPanel();

		frame.remove(panel);
		togglers = new JToggleButton[dimX][dimY];
		panel.setLayout(new GridLayout(dimX,dimY));
		for(int x = 0; x<dimX; x++){
			for(int y=0; y<dimY; y++){
				togglers[x][y] = new JToggleButton();
				togglers[x][y].addMouseListener(this);
				panel.add(togglers[x][y]);
			}
		}
		frame.add(panel, BorderLayout.CENTER);
		frame.revalidate();
	}
	
	public void boardGenerator(int mineCounter, int firstX, int firstY){
		 
	}
	
	
	
	public static void main(String[] args) {
		MineSweeper app = new MineSweeper();

	}



	public void mouseClicked(MouseEvent e) {
		
		
		
	}



	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){		
			for(int x = 0; x<dimensionx; x++){
				for(int y=0; y<dimensiony; y++){
					if(e.getSource()==togglers[x][y]){
						togglers[x][y].setSelected(true);
						togglers[x][y].setIcon(mine);
					}
				}
			}
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			for(int x = 0; x<dimensionx; x++){
				for(int y=0; y<dimensiony; y++){
					if(e.getSource()==togglers[x][y]){
						if(!togglers[x][y].isSelected()){
							togglers[x][y].setSelected(true);	
						}
						else
						{
							togglers[x][y].setSelected(false);
						}
					}
				}
			}
		}
		frame.revalidate();
		
	}

	public void mouseReleased(MouseEvent e) {
		
		
	}
	public void actionPerformed(ActionEvent e) {
		frame.remove(panel);
		
		if(e.getSource()==item1){
			dimensionx = 9;
			dimensiony = 9;
			mineCount = 10;
		}
		else if (e.getSource()==item2){
			dimensionx = 16;
			dimensiony = 16;
			mineCount = 40;
		}
		else if (e.getSource()==item3){
			dimensionx = 16;
			dimensiony = 30;
			mineCount = 99;
		}
		board = new String[dimensionx][dimensiony];
		nickIsNeon(dimensionx, dimensiony, mineCount);
		
	}

}
