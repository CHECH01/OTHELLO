package othello;

import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;

public class Othello {
	private Board mainBoard;
	private BoardGui guiBoard;
	
	private int boardSize;
	
	private ArrayList<Integer> validMoves 	 	  = new ArrayList<Integer>();
	private ArrayList<Integer> lastDiscRows 	  = new ArrayList<Integer>();
	private ArrayList<Integer> lastDiscColumns 	  = new ArrayList<Integer>();
	private ArrayList<Integer> discsToFlipArray   = new ArrayList<Integer>();
	private ArrayList<Integer> playerMovePosition = new ArrayList<Integer>();
	
	private static final String WHITE = "W";
	private static final String BLACK = "B";
	
	private String board[][];
	private String currentPlayer;
	
	private boolean showValidMoves = false ;
	
	private int inputRow 	 	  = 0;
	private int inputColumn  	  = 0;
	private int directionsToFlip  = 0; 
	private	int whiteDiscs = 2, blackDiscs = 2;
	
	public Othello(int boardSize) {
		this.boardSize = boardSize;
		mainBoard = new Board(boardSize);
		guiBoard  = new BoardGui(this,boardSize);
		board = mainBoard.getBoard();
	}
	
	public void play() {
		currentPlayer = BLACK;
		mainBoard.initializeArrayPositions();
		mainBoard.generateEmptyBoard();
		guiBoard.generateEmpytGuiBoard();
		getValidMoves();
		
		if(showValidMoves) {
			showValidMoves();
		}
		showGuiBoard();
	}
	
	public void onClickMove(int i2,int j2) {
		if(isValidMove(board[i2][j2])) {
			validMoves.clear();
			lastDiscRows.clear();
			lastDiscColumns.clear();
			playerMovePosition.clear();
			discsToFlipArray.clear();
			if(showValidMoves) {
				hideValidMoves();
			}
			discCount();
			guiBoard.setDiscsValues(whiteDiscs, blackDiscs);
			if(gameOver()) {
				getWinner();
			}else {
				switchPlayer();
				getValidMoves();
				if(validMoves.size() == 0 ) {
					JOptionPane.showMessageDialog(null,"No valid moves.");
					switchPlayer();
					getValidMoves();
					if(validMoves.size() == 0 ) {
						getWinner();
					}
				}
				if(showValidMoves) {
					showValidMoves();
				}
			}
			directionsToFlip = 0;
		}
	}
	
	public boolean isValidMove(String position) {
		boolean validMove = true;
		for(int i = 0; i < validMoves.size() ; i++ ) {
			if(position.contentEquals(String.valueOf(validMoves.get(i)))) {
				playerMovePosition.add(i);
				directionsToFlip++;
			}
		}
		if(playerMovePosition.size() == 0) {
			validMove = false;
			JOptionPane.showMessageDialog(null, "Invalid move, try again: ");
		}else {
			getCoordinates(position);
			mainBoard.removeArrayPosition(position);
			flipDiscs();
			validMove = true;
		}
		return validMove;
	}
	
	public void flipDiscs() {
		int cont = 0;
		do {
			flipDiscs(playerMovePosition.get(cont));
			cont++;
		}while(cont < directionsToFlip);
	}
	
	public void flipDiscs(int position) {
		int targetRow    = lastDiscRows.get(position);
		int targetColumn = lastDiscColumns.get(position);
		int majorRow 	 = compare(inputRow, targetRow,true);
		int minorRow 	 = compare(inputRow, targetRow,false);
		int majorColumn  = compare(inputColumn, targetColumn,true);
		int minorColumn  = compare(inputColumn, targetColumn,false);
		
		if(majorColumn != minorColumn && majorRow != minorRow) {
			int i = minorRow, j = minorColumn;
			if((targetColumn < inputColumn && targetRow > inputRow) || (targetColumn > inputColumn && targetRow < inputRow))
				j = majorColumn;
			do {
				mainBoard.flip(currentPlayer,i,j);
				if(currentPlayer.contentEquals(BLACK)) {
					guiBoard.flip(i,j, guiBoard.getBlackIcon(),"",Color.BLACK);
				}else {
					guiBoard.flip(i,j, guiBoard.getWhiteIcon(),"",Color.BLACK);
				}
				i++;
				if((targetColumn < inputColumn && targetRow > inputRow) || (targetColumn > inputColumn && targetRow < inputRow))
					j--;
				else
					j++;
			}while(i <= majorRow);
		}else {
			for(int i = minorRow ; i <= majorRow; i++) 
				for (int j = minorColumn; j <= majorColumn; j++) {
					mainBoard.flip(currentPlayer,i,j);
					if(currentPlayer.contentEquals(BLACK)) {
						guiBoard.flip(i,j, guiBoard.getBlackIcon(),"",Color.BLACK);
					}else {
						guiBoard.flip(i,j, guiBoard.getWhiteIcon(),"",Color.BLACK);
					}
				}
		}
	}
	
	public void getValidMoves() {
		for (int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				if(board[i][j].contentEquals(currentPlayer)) {
					getValidMoves(i,j,1,0);					
					getValidMoves(i,j,-1,0);
					getValidMoves(i,j,0,1);
					getValidMoves(i,j,0,-1);
					getValidMoves(i,j,1,1);
					getValidMoves(i,j,1,-1);
					getValidMoves(i,j,-1,1) ;
					getValidMoves(i,j,-1,-1);
				}
			}
		}
	}
	
	public void getValidMoves(int i, int j, int jIncrement,int iIncrement) {
		int row 		  = 0; 
		int column 	 	  = 0; 
		int discsToFlip   = 0; 
		int iCont 		  = iIncrement;
		int jCont 		  = jIncrement; 
		boolean continuee 	= false; 
		boolean invalidMove = false;
		
		do {
			if(j+jCont > 7 || j+jCont < 0 || i+iCont > 7 || i+iCont < 0) {//si está en el borde
				invalidMove = true;
				continuee    = false;
				discsToFlip = 0;
			}else if(!board[i+iCont][j + jCont].contentEquals(BLACK) && !board[i+iCont][j + jCont].contentEquals(WHITE) && discsToFlip == 0) { // si la casilla de al lado está desocupada
				invalidMove = true;
				continuee    = false;
			}else if( discsToFlip == 0 && board[i+iCont][j+jCont] == currentPlayer) { // si la casilla de al lado es del mismo color(1er ciclo)
				invalidMove = true;
				continuee 	  = false;
			}else if(!board[i+iCont][j+jCont].contentEquals(currentPlayer) && 
					(board[i+iCont][j+jCont].contentEquals(BLACK) | board[i+iCont][j+jCont].contentEquals(WHITE))) {//si la casilla  es de otro color: mov valido
				continuee = true;
				discsToFlip++;
			}else if(board[i+iCont][j+jCont] == currentPlayer){ //si la casilla es del mismo color
				invalidMove = true;
				continuee = false;
				discsToFlip = 0;
			}else if (!board[i+iCont][j + jCont].contentEquals(BLACK) && !board[i+iCont][j + jCont].contentEquals(WHITE) ) { // si la casilla está desocupada
				continuee = false;
				row 	 = i + iCont;
				column   = j + jCont;
			}
			jCont  += jIncrement;
			iCont  += iIncrement;
		}while(continuee);
		if (!invalidMove) {
			lastDiscRows.add(i);
			lastDiscColumns.add(j);
			validMoves.add(Integer.parseInt(board[row][column]));
			discsToFlipArray.add(discsToFlip);
		}
	}
	
	public void replay() {
		directionsToFlip = 0;
		whiteDiscs = 2;
		blackDiscs = 2;
		playerMovePosition.removeAll(playerMovePosition);
		validMoves.clear();
		lastDiscRows.clear();
		lastDiscColumns.clear();
		playerMovePosition.clear();
		mainBoard.clearArrayPosition();
		guiBoard.setDiscsValues(whiteDiscs, blackDiscs);
		play();
	}
	
	public void showValidMoves() {
		int k = 0;
		int aux = 0;
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize;j++) {
				while(k < validMoves.size()) {										
					if(board[i][j].contentEquals(String.valueOf(validMoves.get(k)))) {
						aux += discsToFlipArray.get(k);
						if(currentPlayer.contentEquals(BLACK)) {
							guiBoard.flip(i,j, guiBoard.getBlackOptionsIcon(),String.valueOf(aux),Color.BLACK);
						}else {
							guiBoard.flip(i,j, guiBoard.getWhiteOptionsIcon(),String.valueOf(aux),Color.WHITE);
						}
					}
					k++;
				}
				aux = 0;
				k=0;
			}
		}
	}
	
	public void hideValidMoves() {
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize;j++) {
				try {
					if(guiBoard.getIcon(i,j).toString().contentEquals("whiteOptions.png") 
							|| guiBoard.getIcon(i,j).toString().contentEquals("blackOptions.png")) {
						guiBoard.flip(i,j, null,"",Color.BLACK);
					}
				}catch(Exception ex) {}	
			}
		}
	}
	
	public void showGuiBoard() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiBoard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public boolean gameOver() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++){
				if(!board[i][j].contentEquals(BLACK) && !board[i][j].contentEquals(WHITE)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void getWinner() {
		if(blackDiscs > whiteDiscs) {
			JOptionPane.showMessageDialog(null, "BLACK WINS!");
		}else if( blackDiscs < whiteDiscs) {
			JOptionPane.showMessageDialog(null, "WHITE WINS!");
		}else
			JOptionPane.showMessageDialog(null, "It's a Draw.");
	}
	
	public int compare(int firstValue, int secondValue, boolean type) {
		int major = 0;
		int minor = 0;
		if(firstValue >= secondValue) {
			major = firstValue;
			minor = secondValue;
		}else {
			major = secondValue;
			minor = firstValue;
		}
		if(type)
			return major;
		else
			return minor;
	}
	
	public void discCount() {
		whiteDiscs = 0;
		blackDiscs = 0;
		for(int i  = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (board[i][j].contentEquals(WHITE))
					whiteDiscs ++;
				else if (board[i][j].contentEquals(BLACK)) 
					blackDiscs ++;
			}
		}
	}
	
	public void getCoordinates(String currentPos) {
		first:
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				if (board[i][j].contentEquals(currentPos)) {
					inputRow 	= i;
					inputColumn  = j;
					break first;
				}
	}
	
	public void switchPlayer() {
		if(currentPlayer.contentEquals(WHITE) ) {
			currentPlayer = BLACK;
		}else {
			currentPlayer = WHITE;
		}
	}
	
	public void setShowValidMoves(boolean showValidMoves) {
		this.showValidMoves = showValidMoves;
	}
	
	public boolean isShowValidMoves() {
		return showValidMoves;
	}
}