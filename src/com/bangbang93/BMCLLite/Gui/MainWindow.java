package com.bangbang93.BMCLLite.Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;

import com.bangbang93.BMCLLite.BMCLLite;
import com.bangbang93.BMCLLite.GameVersion.Version;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;
	private JComboBox<String> comboVersion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		comboVersion = new JComboBox<String>();
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
		frame.getContentPane().add(comboVersion, BorderLayout.SOUTH);
	}

}
