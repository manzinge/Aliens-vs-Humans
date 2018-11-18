import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Map extends JPanel {
	static ArrayList<Integer> map = new ArrayList<Integer>();

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage water = null;
		BufferedImage dirt = null;
		try {
			water = ImageIO.read(new File("waterright.jpg"));
			dirt = ImageIO.read(new File("dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int x=0,y=0,xorigin,yorigin,xdesired,ydesired;
		xorigin = (int)Math.round(Gamewindow.width/Gamewindow.buttonsx);
		yorigin = (int)Math.round(Gamewindow.height/Gamewindow.buttonsy);
		xdesired = xorigin + (int)Math.round(Gamewindow.width/Gamewindow.buttonsx);
		ydesired = yorigin + (int)Math.round(Gamewindow.height/Gamewindow.buttonsy);

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

	public static void readMap() throws IOException {
		Path filePath = Paths.get("generateMap2");
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
	public static JFrame createAndShowGui() throws IOException {
		readMap();
		Map mainPanel = new Map();
		JFrame frame = new JFrame("DrawRect");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(mainPanel);
		frame.setSize(new Dimension((int)Math.round(Gamewindow.width),(int)Math.round(Gamewindow.height)+35));
		frame.setLocationRelativeTo(null);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setResizable(false);
		return frame;
	}
}