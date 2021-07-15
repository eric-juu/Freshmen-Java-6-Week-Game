//Eric Ju
//5/22/2020
//Game5.java
///The point of the game is to let people practice for their Java final exams 
//Uses all the concepts learned throughout the school year. Loops, If-Else, GUI, Inheritence, methods.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.awt.event.KeyEvent;        
import java.awt.event.KeyListener;

import javax.swing.JFrame;	
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game5
{
	public static void main(String[] args) 
	{
		Game5 ga = new Game5();
		ga.run();
	}
	
	public void run()
	{
		System.out.println("\n\n\n");
		JFrame frame = new JFrame("Game");
		GameContent panel = new GameContent();
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(960, 600);
		frame.setLocation(200, 140);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("\n\n\n");
	}
}

class GameContent extends JPanel
{
	private CardLayout cardHolder;//CardLayout Object
	private boolean userInGame;//Checks if the user is inside the Game
	private boolean firstTimeCheck; //Checks if the user is playing for the first time
	private int redNum, blueNum, greenNum;//The RGB values
	
	private String[] player;//Names of the Images
	private Image[] playerImages;//Array for the images
	private String category;
	
	private Scanner arrayReader, javaReader, stringReader,ieReader, bcReader;//All the readers for all of the files
	
	private String question, correctAnswer, npcAdvice;//The Question and the Answer
	private String[] options;//The four options
	
	private Font font; //The font
	private Color color;//Text Color
	private boolean clickMeCheck; //Checks if the menu button is supposed to be just clicked
	
	private JTextArea questionArea; //Where the text will show up
	public GameContent ( )
	{
		font = new Font("Serif", Font.BOLD, 20);
		//Initialize the readers
		arrayReader = null;
		javaReader = null;
		stringReader = null;
		ieReader = null;
		bcReader = null;
		//Calls the methods to read the Files
		readFiles();
		
		npcAdvice = new String("");
		redNum = 173;
		blueNum = 205;
		greenNum = 255;
		color = new Color(redNum, blueNum, greenNum);
		setBackground(Color.BLACK);
		userInGame = false;
		
		player = new String[] {"Forward1.png","Forward2.png","Forward3.png", "Back1.png", "Back2.png", "Back3.png","Left1.png","Left2.png","Left3.png","Right1.png","Right2.png","Right3.png","NPC1Forward.png","Boss1Right.png","Boss1Left.png","BottemRightSettings.png","TopLeftMap.png","TheMap.jpg"};
		playerImages = new Image[player.length];
		category = new String("Methods");
		
		for (int i = 0; i < player.length; i++)
		{
			playerImages[i] = loadImages(player[i]);	
		}
		
		cardHolder = new CardLayout();
		setLayout(cardHolder);
		
		StartPanel first = new StartPanel(this);
		add(first, "1");
		
		MapPanel second = new MapPanel(this);
		add(second, "2");
		
		Settings third = new Settings(this);
		add(third, "3");
		
		Help forth = new Help(this);
		add(forth, "4");
	}
	
	//Assigns the images to the Array that stores images
	public Image loadImages(String name)
	{
		Image picture = null;
		try
		{
			picture = ImageIO.read(new File(name));
		}
		catch(IOException e)
		{
			System.out.println(name+" Can't be found.\n");
		}
		return picture;
	}
	
	//Tries to read to all of the files
	public void readFiles()
	{
		try
		{
			arrayReader = new Scanner(new File("ArraysQuestions.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open ArraysQuestions.txt\n");
			System.exit(1);
		}
		try
		{
			javaReader = new Scanner(new File("JavaBasics.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open JavaBasics.txt\n");
			System.exit(1);
		}
		try
		{
			stringReader = new Scanner(new File("StringQuestions.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open StringQuestions.txt\n");
			System.exit(1);
		}
		try
		{
			ieReader = new Scanner(new File("IfElseQuestions.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open IfElseQuestions.txt\n");
			System.exit(1);
		}
		try
		{
			bcReader = new Scanner(new File("BooleanControl.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open BooleanControl.txt\n");
			System.exit(1);
		}
	}
	
	//StartPanel
	class StartPanel extends JPanel implements ActionListener
	{
		private JButton playButton, helpButton,settingsButton, firstTime, quitButton; //The Buttons that should take you to a new panel
		private GameContent gc;
		
		public StartPanel(GameContent gcIn)
		{
			gc = gcIn;
			
			setBackground(color);
			firstTimeCheck = false;
			setLayout(new BorderLayout(100,10));
			
			JLabel empty1 = new JLabel("                    			         ");
			JLabel empty2 = new JLabel("                    			         ");
			empty1.setBackground(color);
			empty2.setBackground(color);
			
			add(empty1, BorderLayout.EAST);
			add(empty2, BorderLayout.WEST);
			JPanel buttons = new JPanel();
			buttons.setLayout(new GridLayout(5,1,100,10));
			buttons.setBackground(color);
			
			JLabel title = new JLabel("Java Masters");
			title.setFont(new Font("Serif", Font.BOLD, 50));
			buttons.add(title);
			
			JPanel playPanel = new JPanel();
			playButton = new JButton("Play");
			
			JPanel helpPanel = new JPanel();
			helpButton = new JButton("Help");
			
			JPanel settingsPanel = new JPanel();
			settingsButton = new JButton("Settings");
			
			JPanel firstTimePanel = new JPanel();
			firstTime = new JButton("This is my first time playing!");
			firstTimePanel.setBackground(color);
			
			JPanel quitPanel = new JPanel();
			quitButton = new JButton("Quit");

			
			playButton.setBorder(BorderFactory.createLoweredBevelBorder());
			playButton.addActionListener(this);
			playPanel.add(playButton);
			buttons.add(playButton);
			
			helpButton.setBorder(BorderFactory.createLoweredBevelBorder());
			helpButton.addActionListener(this);
			helpPanel.add(helpButton);
			buttons.add(helpButton);
			
			settingsButton.setBorder(BorderFactory.createLoweredBevelBorder());
			settingsButton.addActionListener(this);
			settingsPanel.add(settingsButton);
			buttons.add(settingsButton);
			
			firstTime.setBorder(BorderFactory.createLoweredBevelBorder());
			firstTime.addActionListener(this);
			firstTimePanel.add(firstTime);
			
			quitButton.setBorder(BorderFactory.createLoweredBevelBorder());
			quitButton.addActionListener(this);
			quitPanel.add(quitButton);
			buttons.add(quitButton);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BorderLayout(0,0));
            buttonPanel.add(buttons, BorderLayout.CENTER);
			
			add(buttonPanel, BorderLayout.CENTER);
			add(firstTimePanel, BorderLayout.SOUTH);
		}
		
		//Checks if whch button you click to redirect the user
		public void actionPerformed(ActionEvent evt) 
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Settings"))
			{
				cardHolder.show(gc, "3");
			}
			else if (command.equals("Help"))
			{
				cardHolder.show(gc, "4");
			}
			else if (command.equals("Play"))
			{
				cardHolder.show(gc, "2");
				userInGame = true;
			}
			else if (command.equals("This is my first time playing!"))
			{
				cardHolder.show(gc, "4");
				userInGame = true;
				firstTimeCheck = true;
			}
			else if (command.equals("Quit"))
			{
				System.exit(1);
			}
		}
	}

	//MapPanel
	class MapPanel extends JPanel implements ActionListener
	{
		private JRadioButton [] answer; //The options for the questions
		private JPanel picturePan; //Way for the handler classes to accsess the Panel
		private int pictureVal, xCoord, yCoord; //The x and y value of the picture
		private GameContent gc;
		private JFrame questionFrame;//A panel that will show the question and the answer
		private boolean bossPicture;//Determines if the user is facing the boss
		
		private JMenu option; //The JMenu button
		//Constructor
		public MapPanel(GameContent gcIn)
		{
			gc = gcIn;
			pictureVal = 1;
			xCoord = 116;
			yCoord = 100;
			bossPicture = false;
			
			question = new String("");
			options = new String[4];
			correctAnswer = new String("");;
			
			setBackground(Color.WHITE);
			setLayout(new BorderLayout(0,0));
			
			JPanel mapPanel = new JPanel();
			
			mapPanel.setBackground(Color.WHITE);
			mapPanel.setLayout(new GridLayout(2, 1));
			
			picturePan = new PicturePanel(); //Creates a new instance of the class that displays the pictures
			 
			MovementHandler mh = new MovementHandler();
			picturePan.addKeyListener(mh);//Adds the keyListener to the pciture pan
			
			questionArea = new JTextArea("Start by moving around for a bit and intercat with someone",1,80);
			questionArea.setBorder(BorderFactory.createLoweredBevelBorder());
			questionArea.setLineWrap(true);
			questionArea.setWrapStyleWord(true);
			questionArea.setEditable(false);
			
			JPanel optionsHolder = new JPanel();
			
			JMenuBar optionsBar = optionBarMaker();
			optionsHolder.add(optionsBar);
			
			mapPanel.add(picturePan);
			mapPanel.add(questionArea);
			
			add(mapPanel, BorderLayout.EAST);
			add(optionsHolder, BorderLayout.WEST);
		}
		
		//Makes the Question Frame
		public void makeQuestionFrame() 
		{
			questionFrame = new JFrame("FlowLayout");	// Create the JFrame
			
			// notice this has DISPOSE.  What is the effect of that?
			questionFrame.setDefaultCloseOperation(questionFrame.DISPOSE_ON_CLOSE);
			questionFrame.setSize(1000,600);
			questionFrame.setLocation(310, 220);

			// Initialize panel
			JPanel questionPanel = new JPanel();

			questionPanel.setPreferredSize(new Dimension(100, 300));//Set prefered size
			questionPanel.setLayout(new BorderLayout());
			
			JPanel questionOptions = new JPanel();
			questionOptions.setLayout(new GridLayout(2,2,5,5));//Sets the gridlayout
			
			JTextArea questionBox = new JTextArea(question);
			questionBox.setFont(font);
			questionBox.setLineWrap(true);
			questionBox.setWrapStyleWord(true);
			RButtonHandler rbh = new RButtonHandler();
			
			JRadioButton choice1 = new JRadioButton(options[0]);	// construct button  
			choice1.setFont(font);
			choice1.addActionListener(rbh); 	// add listener to JRadioButton
			questionOptions.add(choice1);
			
			JRadioButton choice2 = new JRadioButton(options[1]);	 
			choice2.setFont(font);
			choice2.addActionListener(rbh); 	
			questionOptions.add(choice2);
			
			JRadioButton choice3 = new JRadioButton(options[2]);	
			choice3.setFont(font);
			choice3.addActionListener(rbh); 	
			questionOptions.add(choice3);
			
			JRadioButton choice4 = new JRadioButton(options[3]);
			choice4.setFont(font);
			choice4.addActionListener(rbh);
			questionOptions.add(choice4);
			
			questionPanel.add(questionBox, BorderLayout.NORTH);
			questionPanel.add(questionOptions, BorderLayout.CENTER);
			// Add panels to the frame
			questionFrame.getContentPane().add(questionPanel);
			
			// Make the JFrame visible
			questionFrame.setVisible(true);
		}
		
		//Checks if you selected the right answer
		class RButtonHandler implements ActionListener 
		{
			public void actionPerformed( ActionEvent evt ) 
			{
				String phrase = "";
				String command = evt.getActionCommand();
				if ( command.equals(correctAnswer.substring(8)) )
				{
					questionFrame.dispose(); //Destroy the JFrame object
					questionArea.setText("That is the correct answer! If you like, you can go back to the settings and pick another topic.");
				}
				else
				{
					questionFrame.setVisible(false); //you can't see me
					questionFrame.dispose(); //Destroy the JFrame object
					questionArea.setText("That is not the right answer. Refer to the other NPC for instructions");
					xCoord = 356;
					yCoord = 200;
				}
			}
		}
		//Makes the Menu that will take the user to another frame
		public JMenuBar optionBarMaker()
		{
			clickMeCheck = false;
			JMenuBar bar = new JMenuBar();
			option = new JMenu("Options");
			
			JMenuItem exit = new JMenuItem("Exit");
			JMenuItem setting = new JMenuItem("Settings");
				
			exit.addActionListener(this);
			setting.addActionListener(this);	
				
			option.add( exit );
			option.add( setting );
			
			bar.add( option);
		
			return bar;
		}
		
		//Redirects the user to a new frame
		public void actionPerformed( ActionEvent evt ) 
		{
			String command = evt.getActionCommand();
			
			if ( command.equals( "Exit" ) )
			{
				cardHolder.show(gc, "1");
				firstTimeCheck = false;
				userInGame = false;
			}
			else if ( command.equals( "Settings" ) )
				cardHolder.show(gc, "3");	
			
		}
		
		//the paintPanel that will show the pictures
		class PicturePanel extends JPanel
		{
			public PicturePanel()
			{
				setBackground(Color.WHITE);
			}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				requestFocusInWindow();
				g.drawImage(playerImages[playerImages.length-1],0,0,picturePan.getWidth(), picturePan.getHeight(),32,32,picturePan.getWidth()+32, picturePan.getHeight()+128,this);//Map
				
				g.drawImage(playerImages[pictureVal],xCoord,yCoord,48,48,this); //Prints the player
				g.drawImage(playerImages[12],356,150,48,48,this);//NPC
				
				if (!bossPicture)
					g.drawImage(playerImages[13],700,200,48,48,this);//Boss
				else
					g.drawImage(playerImages[14],700,200,48,48,this);//Boss
			}
		}
		
		//Performs Checks the Movemment of the user to make sure it is not out of bound
		class MovementHandler implements KeyListener
		{
			public void keyPressed(KeyEvent evt)
			{
				requestFocusInWindow();
				int keyCode = evt.getKeyCode();
				
				if(keyCode == KeyEvent.VK_DOWN) //If going down
				{
					if(pictureVal < 3)
					{
						pictureVal++;
						if(!(pictureVal < 3))
						{
							pictureVal = 0;
						}
						if(yCoord <= 200 - 10)
							yCoord = yCoord + 10;
					}
					else
					{
						pictureVal = 0;
					}
					repaint();
				}
					
				else if(keyCode == KeyEvent.VK_UP) // If going up
				{
					if(pictureVal >= 3 && pictureVal <6)
					{
						pictureVal++;
						if(!(pictureVal < 6))
						{
							pictureVal = 3;
						}
						if(yCoord >= 10)
							yCoord = yCoord - 10;
					}
					else
					{
						pictureVal = 3;
					}
					repaint();
				}
					
				else if(keyCode == KeyEvent.VK_LEFT) // If going left
				{
					if(pictureVal >= 6 && pictureVal < 9)
					{
						pictureVal++;
						if(!(pictureVal < 9))
						{
							pictureVal = 6;
						}
						if(xCoord >= 120)
							xCoord = xCoord - 10;
					}
					else
					{
						pictureVal = 6;
					}
					repaint();
				}
					
				else if(keyCode == KeyEvent.VK_RIGHT) //If going right
				{
					if(pictureVal >= 9 && pictureVal <= 11)
					{
						pictureVal++;
						if(!(pictureVal <= 10))
						{
							pictureVal = 9;
						}
						if(xCoord <= picturePan.getWidth()-48 - 10)
							xCoord = xCoord + 10;
					}
					else
					{
						pictureVal = 9;
					}
					repaint();
				}
				
				if (xCoord >=324 && xCoord <= 396 && yCoord >= 102 && yCoord <= 198)//If around a certain NPC
				{
					if(keyCode == KeyEvent.VK_RIGHT)
					{
						xCoord -= 10;
					}
					if(keyCode == KeyEvent.VK_LEFT)
					{
						xCoord += 10;
					}
					if(keyCode == KeyEvent.VK_DOWN)
					{
						yCoord -= 10;
					}
					if(keyCode == KeyEvent.VK_UP)
					{
						yCoord += 10;
					}
				}

				if (xCoord >= 652 && yCoord >= 152 )//If around the boss
				{
					if(keyCode == KeyEvent.VK_RIGHT)
					{
						xCoord -= 10;
					}
					if(keyCode == KeyEvent.VK_LEFT)
					{
						xCoord += 10;
					}
					if(keyCode == KeyEvent.VK_DOWN)
					{
						yCoord -= 10;
					}
				}
				else if(xCoord == 646) //Way to activate the question screen
				{
					questionArea.setText("Press Q");
					if (keyCode == KeyEvent.VK_Q)
					{
						makeQuestionFrame();
						bossPicture = true;
						repaint();
					}
				}
				if(xCoord == 356 && yCoord == 200)
				{
					questionArea.setText("Press Q");
					if (keyCode == KeyEvent.VK_Q)
					{
						questionArea.setText(npcAdvice+"Click the button on the top left corner to continue.");
						option.setText("Click Me"); 
					}
					requestFocusInWindow();
				}
				else 
				{
					questionArea.setText("Keep Moving to random people. They want to talk.");
					option.setText("Options"); 
				}
			}
			
			public void keyReleased(KeyEvent evt){}
			public void keyTyped(KeyEvent evt){}
		}
	}
	
	//Settings Panel
	class Settings extends JPanel implements ActionListener
	{
		private JSlider redValue, blueValue, greenValue;//Slider that will determine the number of questions
		private JCheckBox array, javaBasics, strings, ifElse, booleanAlgebra;//The Check box Options
		private JLabel settngLabel, empty;//The Label 
		private JButton exit;//Button to exit
		private GameContent gc;
		
		public Settings(GameContent gcIn)
		{
			gc = gcIn;
			
			JPanel settingsPanel = new JPanel();
			settingsPanel.setLayout(new BorderLayout(100,100));
				
			//Sliders
			JPanel sliderHolder = new JPanel();
			sliderHolder.setLayout(new GridLayout(4,1));
			JLabel sliderLabel = new JLabel("Game Text Color");
			sliderLabel.setFont(font);
			empty = new JLabel("Your text will look like this");
			empty.setFont(font);
			JLabel redLabel = new JLabel("Red");
			JLabel blueLabel = new JLabel("Blue");
			JLabel greenLabel = new JLabel("Green");
			NumberOfQuestions sb = new NumberOfQuestions();
			
			redValue = new JSlider(0, 255,0);
			redValue.setMajorTickSpacing(51);
			redValue.setLabelTable( redValue.createStandardLabels(51));
			redValue.setPaintLabels(true);
			redValue.setPaintTicks(true);
			redValue.addChangeListener(sb);
			
			blueValue = new JSlider(0, 255,0 );
			blueValue.setMajorTickSpacing(51);
			blueValue.setLabelTable( blueValue.createStandardLabels(51));
			blueValue.setPaintLabels(true);
			blueValue.setPaintTicks(true);
			blueValue.addChangeListener(sb);
			
			greenValue = new JSlider(0, 255,0 );
			greenValue.setMajorTickSpacing(51);
			greenValue.setLabelTable( greenValue.createStandardLabels(51));
			greenValue.setPaintLabels(true);
			greenValue.setPaintTicks(true);
			greenValue.addChangeListener(sb);
			
			sliderHolder.add(sliderLabel);
			sliderHolder.add(empty);
			
			sliderHolder.add(redLabel);
			sliderHolder.add(redValue);

			sliderHolder.add(blueLabel);
			sliderHolder.add(blueValue);

			sliderHolder.add(greenLabel);
			sliderHolder.add(greenValue);
				
			settingsPanel.add(sliderHolder, BorderLayout.WEST);
				
			//Checkboxes 
			JPanel contentHolder = new JPanel();
			contentHolder.setLayout(new GridLayout(6,1));
				
			settngLabel = new JLabel("Content");
			settngLabel.setFont(font);
				
			array = new JCheckBox("Arrays");
			CheckBoxListener cblistener = new CheckBoxListener();
			array.addActionListener(cblistener);
			
			javaBasics = new JCheckBox("Java Basics");
			javaBasics.addActionListener(cblistener);
			
			strings = new JCheckBox("Strings");
			strings.addActionListener(cblistener);
			
			ifElse = new JCheckBox("Control Structures");
			ifElse.addActionListener(cblistener);
			
			booleanAlgebra = new JCheckBox("Boolean Algebra");
			booleanAlgebra.addActionListener(cblistener);
				
			contentHolder.add(settngLabel);
			contentHolder.add(array);
			contentHolder.add(javaBasics);
			contentHolder.add(strings);
			contentHolder.add(ifElse);
			contentHolder.add(booleanAlgebra);
				
			settingsPanel.add(contentHolder,BorderLayout.SOUTH);
				
			JPanel exitHelp = new JPanel();
			exit = new JButton("Exit");
			exit.addActionListener(this);
			exitHelp.add(exit);
			settingsPanel.add(exitHelp, BorderLayout.EAST);
			add(settingsPanel);
		}
		
		//Checks where to go
		public void actionPerformed(ActionEvent evt) 
		{
			String command = evt.getActionCommand();
			
				if(command.equals("Exit"))
				{
					if (!userInGame)
					{
						cardHolder.show(gc, "1");
					}
					else if (userInGame)
					{
						cardHolder.show(gc, "2");
					}
				}
		}
		
		//Checks which options are chosen
		class CheckBoxListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				String cmd = evt.getActionCommand();
				int questionValue = -1;
				String line = new String("");
				String num = new String("");
				
				if (cmd.equals("Arrays")) 
				{
					questionValue = (int)(Math.random()*6 + 1);
					
					num = ""+questionValue;
					boolean bFindingNumber = true;
					boolean bFindingAnswer = true;
					int optionNumber = 0;
					while(arrayReader.hasNext() && bFindingNumber)
					{
						line = arrayReader.nextLine();
						if (line.equals(num))//Checks that the problem number matches the randomly generated number
						{
							while(arrayReader.hasNext() && bFindingAnswer) // Checks if you have arrived the line with the answer
							{
								line = arrayReader.nextLine();
								if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
								{
									correctAnswer = line;
									bFindingAnswer = false;
								}
								else
								{
									if (line.lastIndexOf("Question:") != -1)//Checks if the line you are on has question on it 
									{
										line = arrayReader.nextLine();
										while(arrayReader.hasNext() && line.lastIndexOf("Options:") == -1)
										{
											question = question + line;
											line = arrayReader.nextLine();
										}
										if (line.lastIndexOf("Options:") != -1)
										{
											line = arrayReader.nextLine();
											while(arrayReader.hasNext() && line.lastIndexOf("Answer:") == -1)
											{
												options[optionNumber] = line;
												optionNumber++;
												line = arrayReader.nextLine();
											}
											if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
											{
												correctAnswer = line;
												bFindingAnswer = false;
											}
										}
									}	
								}
							}
							bFindingNumber = false;
						}
					}
					npcAdvice = new String("Hey there, you want to learn about arrays. The most importrant thing to remember is that indexes start at 0. You can declare arrays by <data type><name>=new<data type>[<size>]. ");
				}
				if (cmd.equals("Java Basics"))
				{
					questionValue = (int)(Math.random()*6 + 1);
					num = ""+questionValue;
					boolean bFindingNumber = true;
					boolean bFindingAnswer = true;
					int optionNumber = 0;
					while(javaReader.hasNext() && bFindingNumber)
					{
						line = javaReader.nextLine();
						if (line.equals(num))//Checks that the problem number matches the randomly generated number
						{
							while(javaReader.hasNext() && bFindingAnswer) // Checks if you have arrived the line with the answer
							{
								line = javaReader.nextLine();
								if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
								{
									correctAnswer = line;
									bFindingAnswer = false;
								}
								else
								{
									if (line.lastIndexOf("Question:") != -1)//Checks if the line you are on has question on it 
									{
										line = javaReader.nextLine();
										while(javaReader.hasNext() && line.lastIndexOf("Options:") == -1)
										{
											question = question + line;
											line = javaReader.nextLine();
										}
										if (line.lastIndexOf("Options:") != -1)
										{
											line = javaReader.nextLine();
											while(javaReader.hasNext() && line.lastIndexOf("Answer:") == -1)
											{
												options[optionNumber] = line;
												optionNumber++;
												line = javaReader.nextLine();
											}
											if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
											{
												correctAnswer = line;
												bFindingAnswer = false;
											}
										}
									}	
								}
							}
							bFindingNumber = false;
						}
					}
					npcAdvice = new String("Hey so you want to learn about java basics. You must remember that java is a object oriented language, while C is a sturctured language. "+
						"In java there are two types of code: source code and byte code. Binary is in base2. In hardware, RAM stands for Random Access Memory and ROM stands for "+
						"Read Only Memory. RAM is used for memory while the computer is running. ROM is used for start up. ");
				}
				if (cmd.equals("Strings"))
				{
					questionValue = (int)(Math.random()*6 + 1);
					num = ""+questionValue;
					boolean bFindingNumber = true;
					boolean bFindingAnswer = true;
					int optionNumber = 0;
					while(stringReader.hasNext() && bFindingNumber)
					{
						line = stringReader.nextLine();
						if (line.equals(num))//Checks that the problem number matches the randomly generated number
						{
							while(stringReader.hasNext() && bFindingAnswer) // Checks if you have arrived the line with the answer
							{
								line = stringReader.nextLine();
								if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
								{
									correctAnswer = line;
									bFindingAnswer = false;
								}
								else
								{
									if (line.lastIndexOf("Question:") != -1)//Checks if the line you are on has question on it 
									{
										line = stringReader.nextLine();
										while(stringReader.hasNext() && line.lastIndexOf("Options:") == -1)
										{
											question = question + line;
											line = stringReader.nextLine();
										}
										if (line.lastIndexOf("Options:") != -1)
										{
											line = stringReader.nextLine();
											while(stringReader.hasNext() && line.lastIndexOf("Answer:") == -1)
											{
												options[optionNumber] = line;
												optionNumber++;
												line = stringReader.nextLine();
											}
											if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
											{
												correctAnswer = line;
												bFindingAnswer = false;
											}
										}
									}	
								}
							}
							bFindingNumber = false;
						}
					}
					npcAdvice = new String("So you want to learn about strings. It is important to remember that Strings are objects, so they have their own methods. Indexes for stings start at 0. ");
				}
				if (cmd.equals("Control Structures"))
				{
					questionValue = (int)(Math.random()*6 + 1);
					num = ""+questionValue;
					boolean bFindingNumber = true;
					boolean bFindingAnswer = true;
					int optionNumber = 0;
					while(ieReader.hasNext() && bFindingNumber)
					{
						line = ieReader.nextLine();
						if (line.equals(num))//Checks that the problem number matches the randomly generated number
						{
							while(ieReader.hasNext() && bFindingAnswer) // Checks if you have arrived the line with the answer
							{
								line = ieReader.nextLine();
								if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
								{
									correctAnswer = line;
									bFindingAnswer = false;
								}
								else
								{
									if (line.lastIndexOf("Question:") != -1)//Checks if the line you are on has question on it 
									{
										line = ieReader.nextLine();
										while(ieReader.hasNext() && line.lastIndexOf("Options:") == -1)
										{
											question = question + line;
											line = ieReader.nextLine();
										}
										if (line.lastIndexOf("Options:") != -1)
										{
											line = ieReader.nextLine();
											while(ieReader.hasNext() && line.lastIndexOf("Answer:") == -1)
											{
												options[optionNumber] = line;
												optionNumber++;
												line = ieReader.nextLine();
											}
											if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
											{
												correctAnswer = line;
												bFindingAnswer = false;
											}
										}
									}	
								}
							}
							bFindingNumber = false;
						}
					}
					npcAdvice = new String("So you want to learn about control sturctures. For if-else blocks, a certain condition has to be met. If the condition is not met, the action in the else block will be executed OR it exits the if block and continues running to code. If the if condition is met, then the action inside of the block will be run, and once that is done running, the program will continue to run. ");
				}
				if (cmd.equals("Boolean Algebra"))
				{
					questionValue = (int)(Math.random()*6 + 1);
					num = ""+questionValue;
					boolean bFindingNumber = true;
					boolean bFindingAnswer = true;
					int optionNumber = 0;
					while(bcReader.hasNext() && bFindingNumber)
					{
						line = bcReader.nextLine();
						if (line.equals(num))//Checks that the problem number matches the randomly generated number
						{
							while(bcReader.hasNext() && bFindingAnswer) // Checks if you have arrived the line with the answer
							{
								line = bcReader.nextLine();
								if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
								{
									correctAnswer = line;
									bFindingAnswer = false;
								}
								else
								{
									if (line.lastIndexOf("Question:") != -1)//Checks if the line you are on has question on it 
									{
										line = bcReader.nextLine();
										while(bcReader.hasNext() && line.lastIndexOf("Options:") == -1)
										{
											question = question + line;
											line = bcReader.nextLine();
										}
										if (line.lastIndexOf("Options:") != -1)
										{
											line = bcReader.nextLine();
											while(bcReader.hasNext() && line.lastIndexOf("Answer:") == -1)
											{
												options[optionNumber] = line;
												optionNumber++;
												line = bcReader.nextLine();
											}
											if (line.lastIndexOf("Answer:") != -1)//Checks if you have arrived on the line with the answer
											{
												correctAnswer = line;
												bFindingAnswer = false;
											}
										}
									}	
								}
							}
							bFindingNumber = false;
						}
					}
					npcAdvice = new String("So you want to learn about boolean algerbra, one of the most useless things in Java. == means if the values on each end are equal. != is the opposite of ==. && means both consitions on each end must be true in order for the whole condition to be true. || means only one condition has to be true in order for the whole thing to be true. && and || are opposites. >= and < are opposites, and <= and > are oppsites. ! means that the conditionals are switched. ");
				}
			}
		}
		
		class NumberOfQuestions implements ChangeListener 
		{
			public void stateChanged (ChangeEvent evt) 
			{
				redNum = redValue.getValue();
				greenNum = greenValue.getValue();
				blueNum = blueValue.getValue();
				color = new Color(redNum, greenNum, blueNum);
				empty.setForeground(color);
				questionArea.setForeground(color);	
			}
		}
	}
	//Help Panel
	class Help extends JPanel implements ActionListener 
	{
		private GameContent gc;
		public Help(GameContent gcIn)
		{
			gc = gcIn;
			
			setLayout(cardHolder);
			
			JPanel exitHolder = new JPanel();
			JPanel helpPictures = new HelpContent();
			helpPictures.setBounds(10,60,940, 480);
			exitHolder.setLayout(null);
			
			JTextArea topLeftText = new JTextArea("Welcome to JavaMasters! Use the up, down left, right arrow keys to move. In order to interact with the players, press the Q key.");
			JTextArea bottemRightText = new JTextArea("Before you proceed with the game, please go to the settings to select the topics. These topics are not pre-set and you will need to select them before every game. You can also change the game text color by changing the values of the sliders.");
			topLeftText.setEditable(false);
			bottemRightText.setEditable(false);
			topLeftText.setFont(font);
			bottemRightText.setFont(font);
			topLeftText.setLineWrap(true);
			bottemRightText.setLineWrap(true);
			topLeftText.setLineWrap(true);
			bottemRightText.setLineWrap(true);
			
			JButton exit = new JButton("Exit");
			exit.setBounds(840,10,100,50);
			topLeftText.setBounds(10,60,470,240);
			bottemRightText.setBounds(480, 300, 450, 240);
			exit.addActionListener(this);
			
			exitHolder.add(bottemRightText);
			exitHolder.add(topLeftText);
			exitHolder.add(helpPictures);
			exitHolder.add(exit);
			
			add(exitHolder);
			
			revalidate();
		}
		
		public void actionPerformed(ActionEvent evt) 
		{
			String command = evt.getActionCommand();
			
			if(command.equals("Exit"))
			{
				if (firstTimeCheck)
				{
					cardHolder.show(gc, "3");
				}
				else
					cardHolder.show(gc, "1");
			}
		}
		class HelpContent extends JPanel
		{
			public HelpContent()
			{}
			
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				requestFocusInWindow();
				
				g.drawImage(playerImages[16],470,0,470,240,this);//Map
				g.drawImage(playerImages[15],0,240,470,240,this); //Prints the player
			}
		}
	}
}


