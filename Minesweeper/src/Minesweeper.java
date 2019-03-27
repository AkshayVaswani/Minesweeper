import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

public class Minesweeper extends JPanel implements ActionListener, MouseListener{

	/**
	 * 
	 * 
	 * test
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
	JMenuBar menuBar;
	JMenu menu;
	JMenu howTo;
	JMenuItem item1;
	JMenuItem item2;																																												
	JMenuItem item3;
	JMenuItem howToPlay;
	JPanel panel;
	JPanel topbar;
	JPanel toptop;
	Timer timer;
	JToggleButton [][] togglers;
	static String [][] board;
	static boolean[][] boardFlag;
	static boolean[][] isOpened;
	ImageIcon[] imagesArray;
	boolean firstFlagFirst;
	boolean firstClick;
	boolean firstBombClick;
	boolean gameOverClick;
	boolean gameEnd;
	int dimensionx;
	int dimensiony;
	int mineCount;
	int firstX;
	int firstY;
	int flagCounter;
	int timePassed;
	
	
	//images
	ImageIcon mine;
	ImageIcon faceIcon;
	ImageIcon one;
	ImageIcon two;
	ImageIcon three;
	ImageIcon four;
	ImageIcon five;
	ImageIcon six;
	ImageIcon seven;
	ImageIcon eight;
	ImageIcon flag;
	ImageIcon mine_triggered;
	ImageIcon tile;
	ImageIcon zero;	
	JButton smileyFace;
	JLabel flagCountDown;
	JLabel time;
	
	
	
	
	
	public Minesweeper()
	{
		
		
		
		
		frame=new JFrame("Minesweeper");
		frame.add(this);
		menuBar=new JMenuBar();
		menu = 	new JMenu("Game");
		howTo = new JMenu("How To Play");
		topbar = new JPanel();
		toptop = new JPanel();
		item1 = new JMenuItem("Beginner"); 
		item2 = new JMenuItem("Intermediate");
		item3 = new JMenuItem("Expert");
		howToPlay = new JMenuItem("Click Here!");
		firstFlagFirst=	false;
		firstClick = false;
		firstBombClick= false;
		gameOverClick = false;
		gameEnd=true;
		dimensionx = 9;
		dimensiony = 9;
		mineCount =10;
		
		board = new String[dimensionx][dimensiony];
		boardFlag = new boolean[dimensionx][dimensiony];
		isOpened = new boolean[dimensionx][dimensiony];
		
		imageInitializer();
		imagesPlease();
		
		imagesArray = new ImageIcon[] {zero, one, two, three, four, five, six, seven, eight};
		
		smileyFace = new JButton("");
		smileyFace.setIcon(faceIcon);
		smileyFace.setPreferredSize(new Dimension(40, 40)); 
		
		howTo.add(howToPlay);
		menu.add(item1);
		menu.add(item2);
		menu.add(item3);
		menuBar.add(menu);
		menuBar.add(howTo);
		
		howToPlay.addActionListener(this);
		item1.addActionListener(this);		
		item2.addActionListener(this);
		item3.addActionListener(this);
		smileyFace.addActionListener(this);
		
		flagCountDown = new JLabel(10-flagCounter +"");
		flagCountDown.setFont(new Font("TimesRoman", Font.BOLD, 25));
		
		time = new JLabel("Timer");
		time.setFont(new Font("TimesRoman", Font.BOLD, 25));
		
		timer = new Timer();
		timer.schedule(new UpdateTimer(), 0, 1000);
		
		topbar.setLayout(new GridLayout(2,1));
		toptop.add(flagCountDown);
		toptop.add(smileyFace);
		toptop.add(time);
		
		topbar.add(menuBar);
		topbar.add(toptop);
		frame.add(topbar, BorderLayout.NORTH);
		
		nickIsNeon(dimensionx, dimensiony, mineCount); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
	
	//image initializer
	public void imageInitializer(){
		faceIcon = new ImageIcon("smiley.png");
		mine = new ImageIcon("mine.jpg");
		one = new ImageIcon("one.png");
		two = new ImageIcon("2.png");
		three = new ImageIcon("3.png");
		four = new ImageIcon("4-1024x1024.png");
		five = new ImageIcon("5.png");
		six = new ImageIcon("six.png");
		seven = new ImageIcon("seven.png");
		eight = new ImageIcon("eight.png");	
		flag = new ImageIcon("flag.png");
		mine_triggered = new ImageIcon("mine_triggered.jpg");
		tile = new ImageIcon("tile.png");
		zero = new ImageIcon("zero.png");
		
	}
	//listen ok, its called troubleshooting to the extreme... but yes i coulda made it one method
	public void imagesPlease(){
		faceIcon = new ImageIcon(faceIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
		one = new ImageIcon(one.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		two = new ImageIcon(two.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		three = new ImageIcon(three.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		four = new ImageIcon(four.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		five = new ImageIcon(five.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		six = new ImageIcon(six.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		seven = new ImageIcon(seven.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		eight = new ImageIcon(eight.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		zero = new ImageIcon(zero.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		mine = new ImageIcon(mine.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		mine_triggered = new ImageIcon(mine_triggered.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		tile = new ImageIcon(tile.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH));
		flag = new ImageIcon(flag.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		
	}
	//recallable board setter
	public void firstClicker(){
		gameEnd = false;
		boardGenerator(firstX, firstY);
		boardFlagGenerator();
	}
	
	// he really is
	public void nickIsNeon(int dimX, int dimY, int mines){

		frame.setSize(50*dimY,55*dimX);
		
		imagesPlease();
		panel = new JPanel();

		frame.remove(panel);
		togglers = new JToggleButton[dimX][dimY];
		panel.setLayout(new GridLayout(dimX,dimY));
		for(int x = 0; x<dimX; x++){
			for(int y=0; y<dimY; y++){
				togglers[x][y] = new JToggleButton() ;
				togglers[x][y].setIcon(tile);
				togglers[x][y].addMouseListener(this);
				panel.add(togglers[x][y]);
			}
		}
		if (firstClick){
			firstClicker();
		}
		frame.add(panel, BorderLayout.CENTER);
		frame.revalidate();
	}
	
	//board generator
	public void boardGenerator(int firstX, int firstY){
		 int counter = 0 ;
		 for(int i =0; i<dimensionx; i++){
			 for(int j =0 ; j<dimensiony; j++){
				 board[i][j] = "0";
			 	}
		 }
		 board[firstX][firstY] = "F"; //first click
		 while(counter<mineCount){
			 int tempX = (int)(Math.random()*dimensionx);
			 int tempY = (int)(Math.random()*dimensiony);							
			 if(board[tempX][tempY] == "0" && !board[tempX][tempY].equals("F") && surroundFirstChecker(tempX, tempY, firstX, firstY) == true){ //checking to place bombs
				 board[tempX][tempY] = "B";
				 counter++;
			 }
		 }
		 
		 for(int i =0 ; i<dimensionx; i++){				
			 for(int j =0; j<dimensiony; j++){
				if(surroundCheck(i,j) != "0"){
					board[i][j]= surroundCheck(i, j);
				}
			 }	 
		 }
		 for(int i =0; i<dimensionx; i++){
			 for(int j =0; j<dimensiony; j++){
				 System.out.print(board[i][j]+" ");
			 }
			 System.out.println();
		}
	}
	public boolean surroundFirstChecker(int tempx, int tempy, int firx, int firy){
		try {
			if(togglers[firx-1][firy-1] == togglers[tempx][tempy]){ return false;}
		} catch(ArrayIndexOutOfBoundsException ex){}
		try {
			if(togglers[firx][firy-1] == togglers[tempx][tempy]){ return false;}
		} catch(ArrayIndexOutOfBoundsException ex){}
		try{
			if(togglers[firx+1][firy-1] == togglers[tempx][tempy]){ return false;}
		} catch(ArrayIndexOutOfBoundsException ex){}
		try {
			if(togglers[firx-1][firy] == togglers[tempx][tempy]){ return false;}
		} catch(ArrayIndexOutOfBoundsException ex){}
		try{
			if(togglers[firx+1][firy] == togglers[tempx][tempy]){ return false;}
		} catch(ArrayIndexOutOfBoundsException ex){}
		try {
			if(togglers[firx-1][firy+1] == togglers[tempx][tempy]){ return false;}
		} catch(ArrayIndexOutOfBoundsException ex){}
		try{
			if(togglers[firx][firy+1] == togglers[tempx][tempy]){ return false;}
		} catch(ArrayIndexOutOfBoundsException ex){}
		try{
			if(togglers[firx+1][firy+1] == togglers[tempx][tempy]){ return false;}
		} catch(ArrayIndexOutOfBoundsException ex){}
		return true;
	}
	public String surroundCheck(int x, int y){
		int value = 0;
		
		if(board[x][y] != "B" ){
			try {if(board[x-1][y-1] == "B"){value++;}} 
			catch(ArrayIndexOutOfBoundsException ex){}
			try {if(board[x][y-1] == "B"){value++;}} 
			catch(ArrayIndexOutOfBoundsException ex){}
			try{if(board[x+1][y-1] == "B"){value++;}} 
			catch(ArrayIndexOutOfBoundsException ex){}
			try {if(board[x-1][y] == "B"){value++;}} 
			catch(ArrayIndexOutOfBoundsException ex){}
			try{if(board[x+1][y] == "B"){value++;}} 
			catch(ArrayIndexOutOfBoundsException ex){}
			try {if(board[x-1][y+1] == "B"){value++;}} 
			catch(ArrayIndexOutOfBoundsException ex){}
			try{if(board[x][y+1] == "B"){value++;}} 
			catch(ArrayIndexOutOfBoundsException ex){}
			try{if(board[x+1][y+1] == "B"){value++;}} 
			catch(ArrayIndexOutOfBoundsException ex){}
			return value+"";
		}
		return "B";
	}
	
	public void boardFlagGenerator(){
		firstFlagFirst=true;
		for(int i =0; i<dimensionx; i++) {
			for(int j =0; j<dimensiony; j++) {
				boardFlag[i][j] = false;
			}
		}
	}
	public void boardOpenGenerator(){
		for(int i =0; i<dimensionx; i++) {
			for(int j =0; j<dimensiony; j++) {
				isOpened[i][j] = false;
			}
		}
	}
	
	public void mineOpener() {
		for(int x = 0 ; x<dimensionx; x++){
			for(int y=0; y<dimensiony; y++){
				if(board[x][y].equals("B")) {
					togglers[x][y].setEnabled(false);
					togglers[x][y].setDisabledIcon(mine);
				}
			}
		}
	}
	public void helpMenu () {
		JOptionPane.showMessageDialog(null, "How to play: Click on a tile to start the game. Your goal is to\n flag all the bombs and open all other squares. The timer at\n the top will let you know how much time has passed.");
	}
	public void isGameOver () {
		gameEnd = true;
		if(firstBombClick) {
			JOptionPane.showMessageDialog(null, "Game Over, You Lose.");
		}
		else 
		{
			firstBombClick = true;
			JOptionPane.showMessageDialog(null, "Nice Work, You Win!");	
		}
	}
	public void winConditions() {
		int normal = 0;
		for(int x = 0 ; x<dimensionx; x++) {
			for(int y=0; y<dimensiony; y++) {
				if(!togglers[x][y].isEnabled()) { 
					normal++; 
				}
			}
		}
		int notBombs = (dimensionx * dimensiony) - mineCount;
		if (normal >= notBombs) {	
			isGameOver();	
		}
		//System.out.println("not" + notBombs + " nor" + normal );
	}
	
	public void recursion(int x, int y){
        for(int i = x - 1; i <= x + 1 ; i++){ 
            for(int j = y - 1; j <= y + 1; j++){
                try{ 
                    if (togglers[i][j].isEnabled() && !board[i][j].equals("B")){
                        togglers[i][j].setEnabled(false);
                        togglers[i][j].setDisabledIcon(imagesArray[Integer.parseInt(board[i][j])]);
                        if (board[i][j].equals("0")){
                            recursion(i, j); 
                        }
                        winConditions();
                    }  
                } 
                catch(ArrayIndexOutOfBoundsException ex) {}
            }
        }
    }
	
	public static void main(String[] args){
		
		Minesweeper app = new Minesweeper();
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent e){
		if(e.getButton() == MouseEvent.BUTTON1){		
			for(int x = 0 ; x<dimensionx; x++){
				for(int y=0; y<dimensiony; y++){
					if(e.getSource()==togglers[x][y]){
						if(!firstBombClick) {
							if(!firstFlagFirst) {
								boardFlagGenerator();
							}
							if(!boardFlag[x][y]) {
								if(!firstClick) {
									firstClick = true;
									firstX = x;
									firstY = y;
									firstClicker();
								}
								if (togglers[x][y].isEnabled()) {
									togglers[x][y].setEnabled(false);
									winConditions();
									if(board[x][y].equals("0")) {
										togglers[x][y].setDisabledIcon(zero);
										recursion(x, y);
										winConditions();
									}
									if(board[x][y].equals("1")) {togglers[x][y].setDisabledIcon(one);  }
									if(board[x][y].equals("2")) {togglers[x][y].setDisabledIcon(two);  }
									if(board[x][y].equals("3")) {togglers[x][y].setDisabledIcon(three);}
									if(board[x][y].equals("4")) {togglers[x][y].setDisabledIcon(four); }
									if(board[x][y].equals("5")) {togglers[x][y].setDisabledIcon(five); }
									if(board[x][y].equals("6")) {togglers[x][y].setDisabledIcon(six);  }
									if(board[x][y].equals("7")) {togglers[x][y].setDisabledIcon(seven);}
									if(board[x][y].equals("8")) {togglers[x][y].setDisabledIcon(eight);}
									if(board[x][y].equals("B")) {
										firstBombClick = true;
										mineOpener();
										togglers[x][y].setDisabledIcon(mine_triggered);
										isGameOver();
									}
								}
							}
						}
					}
				}
			}
		}
		if(e.getButton() == MouseEvent.BUTTON3){
			if(!firstFlagFirst) {
				boardFlagGenerator();
			}
			for(int x = 0; x<dimensionx; x++){
				for(int y=0; y<dimensiony; y++){
					if(e.getSource()==togglers[x][y]){
						if(!firstBombClick) {
							if(togglers[x][y].isEnabled()) {
								if(!boardFlag[x][y]){
									boardFlag[x][y] = true;
									togglers[x][y].setIcon(flag);
									flagCounter++;
									flagCountDown.setText(""+(10-flagCounter));
									
									System.out.println(flagCounter);
								}
								else
								{
									boardFlag[x][y] = false;
									togglers[x][y].setIcon(tile);
									flagCounter--;
									flagCountDown.setText(""+(10-flagCounter));
									System.out.println(flagCounter);
								}
							}
						}
					}
				}
			}
		}
		frame.revalidate();
		
	}

	public void mouseReleased(MouseEvent e) {}
	public void actionPerformed(ActionEvent e) {
		frame.remove(panel);
		
		if(e.getSource()==item1){
			dimensionx = 9;
			dimensiony = 9;
			mineCount = 10;
			stuff();
		}
		else if (e.getSource()==item2){
			dimensionx = 16;
			dimensiony = 16 ;
			mineCount = 40  ;
			stuff();
		}
		else if (e.getSource()==item3){
			dimensionx = 16 ;
			dimensiony = 30;
			mineCount = 99 ;
			stuff();
		}
		else if(e.getSource() == smileyFace) { stuff(); }
		else if(e.getSource() == howToPlay) { helpMenu(); }
		
	}
	public void stuff() {
		firstClick = false;
		board = new String[dimensionx][dimensiony];
		boardFlag = new boolean[dimensionx][dimensiony];
		isOpened = new boolean[dimensionx][dimensiony];
		timePassed = 0;
		gameEnd = true;
		time.setText("Timer ");
		firstFlagFirst=false;
		flagCounter = 0;
		
		flagCountDown.setText(""+(10-flagCounter));
		firstBombClick = false;
		nickIsNeon(dimensionx, dimensiony, mineCount);	
	}
	class UpdateTimer extends TimerTask {
		public void run() {
			if(!gameEnd){
				timePassed++;
				time.setText("  "+timePassed);
			}
		}
	}
}
