import javax.swing.JButton;
import javax.swing.JFrame;

import objects.Objects.Human;

public class TestBoard {
	static JButton[][] buttons = new JButton[10][10];			
	static JFrame board = new JFrame("Testwindow");
	static int buttonsx = 8;
	static int buttonsy = 8;
	static Human hmn = null;
	
	public static void main(String[] args) {
		setupwindow();
		createbuttons(buttonsx,buttonsy);
	}
	public static void setupwindow() {
		board.setDefaultCloseOperation(board.EXIT_ON_CLOSE);
		board.setResizable(false);
		board.setVisible(true);
		board.setSize(buttonsx*100,buttonsy*100 + 29);
		board.setLocationRelativeTo(null);
	}
	public static void createbuttons(int length, int width) {
		board.setLayout(null);
		int x = 0;
		int y = 0;
		for(int i=0;i<length;i++) {
			x=0;
			for(int j=0;j<width;j++) {
				buttons[i][j] = hmn;
				buttons[i][j].setLocation(x, y);
				buttons[i][j].setSize(100,100);
				
				board.add(buttons[i][j]);
				x = x + 100;
			}
			y=y+100;
		}
		board.repaint();
	}
}