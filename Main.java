import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	
	//////////////////////////// VARIABLES ////////////////////////////
	int windowW, windowH;
	int panelSize = 400;
	int squareSize, colorSize, colorOffset = 12;
	Color selectedColor = Color.WHITE;
	//////////////////////////// VARIABLES ////////////////////////////
	
	//////////////////////////// COLORS ////////////////////////////
	ArrayList<Color> colorArr = new ArrayList<Color>();
	//////////////////////////// COLORS ////////////////////////////
	
	//////////////////////////// UI ////////////////////////////
	private JFrame frame;
	JPanel panel;
	ArrayList<JLabel> squareMap = new ArrayList<JLabel>();
	ArrayList<JLabel> colorLabel = new ArrayList<JLabel>();
	JLabel selectedLabel;
	//////////////////////////// UI ////////////////////////////
	
	public static void main(String[] args) {
		try {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new Main();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	Main() {
		initialize();
	}
	
	//////////////////////////// METHODS ////////////////////////////
	
	void loadColors() {
		colorArr.add(Color.decode("#d61e4a"));
		colorArr.add(Color.decode("#e3355f"));
		colorArr.add(Color.decode("#d00334"));
		
		colorArr.add(Color.decode("#da43a3"));
		colorArr.add(Color.decode("#da2197"));
		colorArr.add(Color.decode("#d40488"));
		
		colorArr.add(Color.decode("#be36d4"));
		colorArr.add(Color.decode("#b11dca"));
		colorArr.add(Color.decode("#a007ba"));
		
		colorArr.add(Color.decode("#463dc9"));
		colorArr.add(Color.decode("#3228cc"));
		colorArr.add(Color.decode("#1b10c5"));
		
		colorArr.add(Color.decode("#4aa9d1"));
		colorArr.add(Color.decode("#259ccd"));
		colorArr.add(Color.decode("#078dc4"));
		
		colorArr.add(Color.decode("#45c078"));
		colorArr.add(Color.decode("#24c165"));
		colorArr.add(Color.decode("#07b850"));
		
		colorArr.add(Color.decode("#c3cc5e"));
		colorArr.add(Color.decode("#becb33"));
		colorArr.add(Color.decode("#b4c406"));
		
		colorArr.add(Color.decode("#c29b4f"));
		colorArr.add(Color.decode("#bd8b2c"));
		colorArr.add(Color.decode("#b77b06"));
	}
	
	void generateSquareMap() {
		int i = 12;
		while(panelSize % i != 0)
			i++;
		squareSize = i;
		
		int x = panel.getBounds().x;
		int y = panel.getBounds().y;
		
		for(i = 0; i < panelSize / squareSize; i++) {
			for(int j = 0; j < panelSize / squareSize; j++) {
				JLabel label = new JLabel("");
				label.setBackground(Color.WHITE);
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
				label.setOpaque(true);
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						label.setBackground(selectedColor);
					}
				});
				label.setBounds(x + squareSize * i, y + squareSize * j, squareSize, squareSize);
				squareMap.add(label);
				frame.getContentPane().add(label);
			}
		}
	}
	
	void initColors() {
		colorSize = panelSize / (colorArr.size() / 3) / 2;
		
		int x = panel.getBounds().x - 30 - 3 * (colorSize + colorOffset);
		int y = panel.getBounds().y;
		
		for(int i = 0; i < colorArr.size() / 3 - 1; i++) {
			for(int j = 0; j < 3; j++) {
				JLabel label = new JLabel();
				label.setBackground(colorArr.get(i * 3 + j));
				label.setOpaque(true);
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectedColor = label.getBackground();
						selectedLabel.setBackground(label.getBackground());
					}
				});
				label.setBounds(x + j * (colorSize + colorOffset), y + i * (colorSize + colorOffset), colorSize, colorSize);
				colorLabel.add(label);
				frame.getContentPane().add(label);
			}
		}
	}
	
	void selectColor() {
		selectedLabel.setBackground(selectedColor);
	}
	
	void print(String message) {
		System.out.println(message);
	}
	
	void resizeUI() {
		if(windowW != frame.getContentPane().getBounds().width || windowH != frame.getContentPane().getBounds().height) {
			windowW = frame.getContentPane().getBounds().width;
			windowH = frame.getContentPane().getBounds().height;
			
			panel.setBounds((windowW - panelSize) / 2, (windowH - panelSize) / 2, panelSize, panelSize);
			
			for(int i = 0; i < panelSize / squareSize; i++)
				for(int j = 0; j < panelSize / squareSize; j++)
					squareMap.get(i * (panelSize / squareSize) + j).setBounds(panel.getBounds().x + squareSize * i, panel.getBounds().y + squareSize * j, squareSize, squareSize);
			
			for(int i = 0; i < colorArr.size() / 3 - 1; i++)
				for(int j = 0; j < 3; j++)
					colorLabel.get(i * 3 + j).setBounds(panel.getBounds().x - 30 - 3 * (colorSize + colorOffset) + j * (colorSize + colorOffset), panel.getBounds().y + i * (colorSize + colorOffset), colorSize, colorSize);
			
			selectedLabel.setBounds(panel.getBounds().x + panelSize + 50, panel.getBounds().y, colorSize * 2, colorSize * 2);
		}
	}
	
	//////////////////////////// METHODS ////////////////////////////
	
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(800, 600));
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		windowW = frame.getContentPane().getBounds().width;
		windowH = frame.getContentPane().getBounds().height;
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		panel.setBounds((windowW - panelSize) / 2, (windowH - panelSize) / 2, panelSize, panelSize);
		//frame.getContentPane().add(panel);
		
		generateSquareMap();
		loadColors();
		initColors();
		
		selectedLabel = new JLabel("");
		selectedLabel.setBackground(Color.WHITE);
		selectedLabel.setOpaque(true);
		selectedLabel.setBounds(panel.getBounds().x + panelSize + 50, panel.getBounds().y, colorSize * 2, colorSize * 2);
		frame.getContentPane().add(selectedLabel);
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				resizeUI();
			}
		}, 500, 500);
	}
}
