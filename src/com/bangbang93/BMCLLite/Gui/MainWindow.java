package com.bangbang93.BMCLLite.Gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.bangbang93.BMCLLite.BMCLLite;
import com.bangbang93.BMCLLite.GameVersion.Version;

public class MainWindow {

	private JFrame frameMain;
	private JComboBox<String> comboVersion;
	private JTextField txtUserName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameMain = new JFrame();
		frameMain.setTitle("BMCLLite");
		frameMain.setBounds(100, 100, 450, 300);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		comboVersion = new JComboBox<String>();
		comboVersion.setBounds(0, 241, 434, 21);
		comboVersion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BMCLLite.selectedVersion = Version.readVersion(Version.getVersionJsonFile(comboVersion.getSelectedItem().toString()));
			}
		});
		String[] versions;
		try {
			versions = Version.refreshVersion();
			for (String ver : versions){
				comboVersion.addItem(ver);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			comboVersion.addItem("未找到任何版本");
			System.out.println(BMCLLite.getCurrectDirectory());
		}
		frameMain.getContentPane().setLayout(null);
		frameMain.getContentPane().add(comboVersion);
		
		JButton btnStart = new JButton("开始游戏");
		btnStart.setBounds(311, 172, 123, 61);
		frameMain.getContentPane().add(btnStart);
		
		JButton button = new JButton("设置");
		button.setBounds(360, 125, 74, 37);
		frameMain.getContentPane().add(button);
		
		JLabel labsUsername = new JLabel("用户名");
		labsUsername.setBounds(10, 25, 55, 15);
		frameMain.getContentPane().add(labsUsername);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(75, 22, 179, 21);
		frameMain.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
	}
}
