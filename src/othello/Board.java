package othello;

import java.util.ArrayList;

public class Board {
	private int boardSize;
	private int arraySize;
	private String board[][];
	private ArrayList<String> positions = new ArrayList<String>();
	
	public Board(int boardSize) {
		this.boardSize = boardSize;
		arraySize  	   = boardSize*boardSize;
		board  		   = new String[boardSize][boardSize];
	}
	
	public void generateEmptyBoard() {
		int k = 0;
		for(int i = 0; i < boardSize; i++) 
			for(int j = 0; j < boardSize; j++) {
				board[i][j] = positions.get(k);
				k++;
			}
		board[3][3] = "W";
		board[3][4] = "B";
		
		board[4][3] = "B";
		board[4][4] = "W";
		removeArrayPosition("28");
		removeArrayPosition("29");
		removeArrayPosition("36");
		removeArrayPosition("37");
		
	}
	public void initializeArrayPositions() {
		for (int i = 1; i <= arraySize; i++)
			positions.add(String.valueOf(i));
	}
	public void removeArrayPosition(String pos) {
		positions.remove(positions.indexOf(pos));
	}
	public void flip(String playerColor,int i, int j) {
		board[i][j] = playerColor;
	}
	public String[][] getBoard() {
		return board;
	}
	public void clearArrayPosition() {
		positions.clear();
	}
	public void setBoard(String[][] board) {
		this.board = board;
	}
	public ArrayList<String> getPositions() {
		return positions;
	}
	public void setPositions(ArrayList<String> positions) {
		this.positions = positions;
	}
	
}
