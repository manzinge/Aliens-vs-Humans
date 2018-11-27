import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Font;
import javax.swing.*;
import javax.imageio.ImageIO;


public class Map extends JPanel {
	static ArrayList<Integer> map = new ArrayList<Integer>();
	static JLabel zonelabel = new JLabel("Try to gather some resources");

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage water = null;
		BufferedImage dirt = null;
		try {
			water = ImageIO.read(new File("GameIcons\\\\waterright.jpg"));
			dirt = ImageIO.read(new File("GameIcons\\dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int x=0,y=0,xorigin,yorigin;
		xorigin = (int)Math.round(Gamewindow.width/Gamewindow.buttonsx);
		yorigin = (int)Math.round(Gamewindow.height/Gamewindow.buttonsy);

		for(int i=0;i<Gamewindow.buttonsx;i++) {
			for(int j=0;j<Gamewindow.buttonsy;j++) {
				switch(map.get(i+(j*8))){
				case 0: g.drawImage(water, i*xorigin, j*yorigin, (i*xorigin)+xorigin, (j*yorigin)+yorigin, 0, 0, 557, 555, this); break;
				case 1: g.drawImage(dirt, i*xorigin, j*yorigin, (i*xorigin)+xorigin, (j*yorigin)+yorigin, 0, 0, 390, 390, this); break;
				default: System.out.println("Error occured while drawing a Tile!");break;
				}
				x=x+(int)Math.round(Gamewindow.width/Gamewindow.buttonsx);
			}
			y=y+(int)Math.round(Gamewindow.height/Gamewindow.buttonsy);
			x=0;
		}
	}

	public static void readMap(int mapchoice) throws IOException {
		Path filePath = Paths.get("generateMap1");
		switch(mapchoice) {
		case 1: filePath = Paths.get("Maps\\generateMap1");break;
		case 2: filePath = Paths.get("Maps\\generateMap2");break;
		case 3: filePath = Paths.get("Maps\\generateMap3");break;
		default: System.out.println("There was an error while creating the map!");break;
		}
		Scanner scanner = new Scanner(filePath);
		while (scanner.hasNext()) {
			if (scanner.hasNextInt()) {
				map.add(scanner.nextInt());
			} else {
				scanner.next();
			}
		}
	}

	// create the GUI explicitly on the Swing event thread
	public static JFrame createAndShowGui(int map) throws IOException {
		readMap(map);
		Map mainPanel = new Map();
		JFrame frame = new JFrame("Aliens-vs-Humans");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(mainPanel);
		frame.setSize(new Dimension((int)Math.round(Gamewindow.width),(int)Math.round(Gamewindow.height)+35));
		frame.setLocationRelativeTo(null);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setResizable(false);
		zonelabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		zonelabel.setForeground(Color.BLACK);
		zonelabel.setVisible(true);
		frame.add(zonelabel);
		return frame;
	}
}