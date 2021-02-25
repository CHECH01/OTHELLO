package othello;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BoardGui extends JFrame{
	private static final long serialVersionUID = 1L;
	private static final Color MY_GREEN = new Color (0,102,0);
	private static final Color MY_BLUE = new Color (0,51,51);
	private static final Color MY_WHITE = new Color (224,224,224);
	
	private JPanel contentPane;
	private JButton guiBoard [][] = new JButton [8][8];
	private JLabel lbl[][] 		  = new JLabel[8][8];
	
	private JLabel lblBlackDiscs = new JLabel("x2");
	private JLabel lblWhiteDiscs = new JLabel("x2");
	
	private ImageIcon whiteIcon  		= new ImageIcon("white.png");
	private ImageIcon blackIcon  		= new ImageIcon("black.png");
	private ImageIcon blackOptionsIcon  = new ImageIcon("blackOptions.png");
	private ImageIcon whiteOptionsIcon  = new ImageIcon("whiteOptions.png");
	private ImageIcon showIcon  		= new ImageIcon("showIcon.png");
	private ImageIcon hideIcon  		= new ImageIcon("hideIcon.png");

	private ImageIcon newGameIcon  = new ImageIcon("newGameBtn.png");

	public BoardGui(Othello oth){
		setResizable(false);
		setTitle("OTHELLO GAME");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 60, 570, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(MY_BLUE);
		contentPane.setLayout(null);
		
		JLayeredPane panel = new JLayeredPane();
		panel.setBounds(30, 80, 500, 500);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(8,8));
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(69, 580, 150, 70);
		panel2.setLayout(new GridLayout(1,2));
		panel2.setBackground(MY_BLUE);
		contentPane.add(panel2);
		
		JPanel panel3 = new JPanel();
		panel3.setBounds(69, 10, 150, 70);
		panel3.setLayout(new GridLayout(1,2));
		panel3.setBackground(MY_BLUE);
		contentPane.add(panel3);
		
		JLabel lblBlack = new JLabel();
		lblBlack.setBounds(69, 580, 70, 70);
		lblBlack.setIcon(blackIcon);
		panel2.add(lblBlack);
		
		
		lblBlackDiscs.setFont(new Font("Tahoma",Font.BOLD, 40));
		lblBlackDiscs.setForeground(Color.BLACK);
		lblBlackDiscs.setBounds(139, 580, 70, 70);
		panel2.add(lblBlackDiscs);
		
		JLabel lblWhite = new JLabel();
		lblWhite.setBounds(69,10, 60, 60);
		lblWhite.setIcon(whiteIcon);
		panel3.add(lblWhite);
		
		
		lblWhiteDiscs.setFont(new Font("Tahoma",Font.BOLD, 40));
		lblWhiteDiscs.setForeground(MY_WHITE);
		lblWhiteDiscs.setBounds(69, 10, 140, 70);
		panel3.add(lblWhiteDiscs);
		
		JButton newGameBtn = new JButton("");
		newGameBtn.setBounds(335, 605, 173, 30);
		newGameBtn.setBackground(MY_BLUE);
		newGameBtn.setIcon(newGameIcon);
		contentPane.add(newGameBtn);
		
		JButton showHide = new JButton("");
		showHide.setBorderPainted(false);
		showHide.setBounds(501, 39, 30, 30);
		showHide.setIcon(hideIcon);
		showHide.setOpaque(false);
		showHide.setBackground(new Color(0,0,0,0));
		showHide.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(oth.isShowValidMoves()) {
					oth.setShowValidMoves(false);
					oth.hideValidMoves();
					showHide.setIcon(hideIcon);
				}else if(!oth.isShowValidMoves()) {
					oth.setShowValidMoves(true);
					oth.showValidMoves();
					showHide.setIcon(showIcon);
				}
			}
			
		});
		contentPane.add(showHide);
		
		
		newGameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				oth.replay();
			}
			
		});
		
		for(int i = 0 ; i < 8;i++) {
			for(int j = 0 ; j < 8;j++) {
				guiBoard[i][j] = new JButton();
				lbl[i][j] = new JLabel("");
				guiBoard[i][j].add(lbl[i][j]);
				panel.add(guiBoard[i][j]);
				
				final int i2 = i;
				final int j2 = j;
				guiBoard[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						oth.onClickMove(i2, j2);
					}
				});
				
			}
		}
		guiBoard[3][3].setIcon(whiteIcon);
		guiBoard[3][4].setIcon(blackIcon);
		
		guiBoard[4][3].setIcon(blackIcon);
		guiBoard[4][4].setIcon(whiteIcon);
	}
	public void generateEmpytGuiBoard() {
		for(int i = 0 ; i < 8;i++) {
			for(int j = 0 ; j < 8;j++) {
				guiBoard[i][j].setSize(10, 10);
				guiBoard[i][j].setBackground(MY_GREEN);
				guiBoard[i][j].setIcon(null);
				
				lbl[i][j].setFont(new Font("Tahoma", Font.BOLD, 20));
				lbl[i][j].setText("");
				lbl[i][j].setAlignmentX((float)0.54);
				lbl[i][j].setAlignmentY(CENTER_ALIGNMENT);
			}
		}
		guiBoard[3][3].setIcon(whiteIcon);
		guiBoard[3][4].setIcon(blackIcon);
		
		guiBoard[4][3].setIcon(blackIcon);
		guiBoard[4][4].setIcon(whiteIcon);
	}
	
	public ImageIcon getWhiteIcon() {
		return whiteIcon;
	}
	
	public ImageIcon getBlackIcon() {
		return blackIcon;
	}
	public void setDiscsValues(int whiteDiscs, int blackDiscs) {
		lblBlackDiscs.setText("x"+blackDiscs);
		lblWhiteDiscs.setText("x"+whiteDiscs);
	}
	
	public ImageIcon getBlackOptionsIcon() {
		return blackOptionsIcon;
	}
	public ImageIcon getWhiteOptionsIcon() {
		return whiteOptionsIcon;
	}
	public void flip(int i, int j, ImageIcon icon,String txt,Color c) {
		guiBoard[i][j].setIcon(icon);
		lbl[i][j].setText(txt);
		lbl[i][j].setForeground(c);
	}

	public Object getIcon(int i, int j) {
		return guiBoard[i][j].getIcon();
	}
}