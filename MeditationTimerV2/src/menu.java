import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;
import javax.swing.JMenuItem;

public class menu extends JFrame implements ActionListener, KeyListener {

	int minSet;
	private JPanel contentPane;
	private JTextField medMinutes;
	private JButton start;
	private JLabel timer;
	private JButton stop;
	private JLabel lblNewLabel;
	private Timer time;
	private int second;
	private int minute;
	private String ddSecond, ddMinute;	
	private DecimalFormat dFormat = new DecimalFormat("00");
	private String endMessage;
	private File bell;
	private AudioInputStream bellStream;
	private Clip bellClip;
	private JMenuBar menuBar;
	private static int uhour;
	private static int uminute;
	private static int usecond;
	private static int udays;
	private Timer utime;
	private static String userTime, uddSecond, uddMinute, uddHours, uddDays;
	JMenuItem statItem;
	JMenuItem prefItem;
	JMenuItem aboutItem;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menu frame = new menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			FileInputStream fis = new FileInputStream("time.dat");
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);
			
			DataStorage dStorage = (DataStorage)ois.readObject();
			
			usecond = dStorage.second;
			uminute = dStorage.minute;
			uhour = dStorage.hour;
			udays = dStorage.days;
			userTime = dStorage.userTime;
			
			ois.close();
			
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
	}

	public menu() {
		
		endMessage = "Have a peaceful day!";
		
		try {
			bell = new File("bell.wav");
			bellStream = AudioSystem.getAudioInputStream(bell);
			bellClip = AudioSystem.getClip();
			bellClip.open(bellStream);
			
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ImageIcon image = new ImageIcon("background.png");
		setResizable(false);
		setSize(new Dimension(699, 615));
		setTitle("Meditation Timer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 529);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu prefeMenu = new JMenu("Settings");
		menuBar.add(prefeMenu);
		
		statItem = new JMenuItem("Stats");
		prefeMenu.add(statItem);
		statItem.addActionListener(this);
		
		prefItem = new JMenuItem("Preferences");
		prefeMenu.add(prefItem);
		
		aboutItem = new JMenuItem("About");
		prefeMenu.add(aboutItem);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		prefeMenu.add(exitItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Meditation Timer");
		title.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 40));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(10, 61, 663, 54);
		contentPane.add(title);
		
		JLabel instruct = new JLabel("Please type in how many minutes you would like to meditate for.");
		instruct.setHorizontalAlignment(SwingConstants.CENTER);
		instruct.setBounds(10, 126, 663, 14);
		contentPane.add(instruct);
		
		medMinutes = new JTextField();
		medMinutes.setHorizontalAlignment(SwingConstants.CENTER);
		medMinutes.setBounds(300, 151, 42, 20);
		contentPane.add(medMinutes);
		medMinutes.setColumns(10);
		medMinutes.addKeyListener(this);
		
		JLabel minlabel = new JLabel("minutes");
		minlabel.setBounds(352, 154, 46, 14);
		contentPane.add(minlabel);
		
		start = new JButton("Start Meditating");
		start.setBounds(273, 182, 135, 23);
		start.addActionListener(this);
		contentPane.add(start);
		start.addKeyListener(this);
		
		timer = new JLabel("25");
		timer.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 60));
		timer.setHorizontalAlignment(SwingConstants.CENTER);
		timer.setBounds(0, 253, 683, 80);
		contentPane.add(timer);
		timer.setVisible(false);
		timer.addKeyListener(this);
		
		stop = new JButton("Stop Meditation");
		stop.setBounds(273, 354, 135, 23);
		contentPane.add(stop);
		stop.addActionListener(this);
		stop.setVisible(false);
		stop.addKeyListener(this);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("bkgrnd.png"));
		lblNewLabel.setBounds(0, 0, 683, 468);
		contentPane.add(lblNewLabel);
		
	}
	public void upTimer() {
		
		utime = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				usecond++;
				uddSecond = dFormat.format(usecond);
				uddMinute = dFormat.format(uminute);
				uddHours = dFormat.format(uhour);
				uddDays = dFormat.format(udays);
				
//				ddMinute = dFormat.format(minute);
//				second = 0;
//				ddSecond = dFormat.format(second);
				
				if (uminute == 60 && usecond == 60) {
					uminute = 0;
					usecond = 0;
					uhour++;
					uddSecond = dFormat.format(usecond);
					uddMinute = dFormat.format(uminute);
					uddHours = dFormat.format(uhour);
					uddDays = dFormat.format(udays);
				}
				if (usecond == 60) {
					usecond = 0;
					uminute++;
					uddSecond = dFormat.format(usecond);
					uddMinute = dFormat.format(uminute);
					uddHours = dFormat.format(uhour);
					uddDays = dFormat.format(udays);
				}

				if (uhour == 24) {
					uhour = 0;
					uminute = 0;
					usecond = 0;
					udays++;
					uddSecond = dFormat.format(usecond);
					uddMinute = dFormat.format(uminute);
					uddHours = dFormat.format(uhour);
					uddDays = dFormat.format(udays);
				}
				userTime = uddDays + " Days : " + uddHours + " Hours : " + uddMinute + " Minutes : " + uddSecond + " Seconds";
				try {
					FileOutputStream fos = new FileOutputStream("time.dat");
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					ObjectOutputStream oos = new ObjectOutputStream(bos);
					
					DataStorage dStorage = new DataStorage();
					
					dStorage.hour = uhour;
					dStorage.minute = uminute;
					dStorage.second = usecond;
					dStorage.days = udays;
					dStorage.userTime = userTime;
					
					oos.writeObject(dStorage);
					oos.close();
					
				}catch(IOException ex) {
					ex.printStackTrace();
				}
				//USE IN DEVELOPMENT ONLY
				System.out.println(uddDays + " Days : " + uddHours + " Hours : " + uddMinute + " Minutes : " + uddSecond + " Seconds");
				System.out.println(userTime);
			}
			
		});
			
	}
	
	public void downTimer() {
		
		endMessage = "";
		time = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				second--;
				ddSecond = dFormat.format(second);
//				ddMinute = dFormat.format(minute);
//				second = 0;
//				ddSecond = dFormat.format(second);
				timer.setText(minute + ":" + ddSecond);
				
				if (second == -1) {
					second = 59;
					minute--;
					ddSecond = dFormat.format(second);
//					ddMinute = dFormat.format(minute);
					timer.setText(minute + ":" + ddSecond);
				}
				
				if (minute == 0 && second == 1) {
//					try {
//						bellStream.reset();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					bellClip.setMicrosecondPosition(0);
					bellClip.start();
				}
				
				if (minute == 0 && second == 0) {
					time.stop();
					utime.stop();
					endMessage = "Have a peaceful day!";
					timer.setText(endMessage);
					stop.setVisible(false);
				}
				
			}
			
		});
			
	}
	
	public void Start() {
		
		minute = Integer.parseInt(medMinutes.getText());
		if (minute < 1) {
			timer.setVisible(true);
			timer.setText("Try Again");
		}
		else {
			bellClip.setMicrosecondPosition(0);
			bellClip.start();
			timer.setVisible(true);
			stop.setVisible(true);
			second = 0;
			ddSecond = dFormat.format(second);
			ddMinute = dFormat.format(minute);
			timer.setText(ddMinute + ":" + ddSecond);
			downTimer();
			upTimer();
			time.start();
			utime.start();
		}
	}
	
	public void Stop() {
		utime.stop();
		time.stop();
		timer.setText("1");
		timer.setVisible(false);
		stop.setVisible(false);
		bellClip.stop();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//When Start is Clicked
		if (e.getSource()==start) {
			
			Start();
		}
	
		if (e.getSource()==stop) {
			
			Stop();
		}
		
		if (e.getSource()==statItem) {
			
			Stats sframe = new Stats();
			sframe.setVisible(true);
			
		}
	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if  (e.getKeyCode() == 10) {
			Start();
		}
		if (e.getKeyCode() == 32) {
			Stop();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}