import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Stats extends JFrame {
	
	private JPanel contentPane;
	private static String userTime;
	JLabel userTimeField;

	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stats frame = new Stats();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
	}

	public Stats() {
		
		try {
			FileInputStream fiss = new FileInputStream("time.dat");
			BufferedInputStream biss = new BufferedInputStream(fiss);
			ObjectInputStream oiss = new ObjectInputStream(biss);
			
			DataStorage ddStorage = (DataStorage)oiss.readObject();
			
			userTime = ddStorage.userTime;
			
			oiss.close();
			
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
		
		setResizable(false);
		setAlwaysOnTop(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 183);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Your Statistics");
		title.setFont(new Font("Segoe UI", Font.BOLD, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 0, 409, 37);
		contentPane.add(title);
		
		JLabel lblNewLabel = new JLabel("You've meditated for...");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 48, 409, 26);
		contentPane.add(lblNewLabel);
		
		userTimeField = new JLabel(userTime);
		userTimeField.setHorizontalAlignment(SwingConstants.CENTER);
		userTimeField.setBounds(0, 85, 409, 26);
		userTimeField.setText(userTime);
		contentPane.add(userTimeField);
	}

}
