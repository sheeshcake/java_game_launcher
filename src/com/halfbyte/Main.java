package com.halfbyte;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.*;
import me.marnic.jiconextract.extractor.IconSize;
import me.marnic.jiconextract.extractor.JIconExtractor;



public class Main extends JFrame{
	
	JLabel versionLabel = new JLabel("Game Launcher v1.1c");
	Process iconsextProc;
	
	private static final long serialVersionUID = 1L;
	public Main(){
		versionLabel.setBounds(10,690,150,30);
		
		Path currentRelativePath = Paths.get("");
        final JPanel gamePanel = new JPanel();
        gamePanel.setBounds(300,10,970, 700);
        File folder = new File(currentRelativePath.toAbsolutePath().toString() + "\\Games");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
        	System.out.println(file.getName().toString());
            if ((file.isFile()) && !(file.getName().toString().equals("desktop.ini"))) {
                JButton pan = new JButton();        
                pan.setSize(100, 100);
                pan.setEnabled(true);
                pan.setBackground(Color.WHITE);
                pan.setToolTipText(file.getName());      
                pan.setVerticalTextPosition(SwingConstants.CENTER);
                pan.setHorizontalTextPosition(SwingConstants.CENTER);
                pan.setName(file.getName());

                final File get_i = new File(currentRelativePath.toAbsolutePath().toString() + "\\Games\\" + file.getName());
                System.out.println(get_i);
                pan.addMouseListener(new MouseAdapter() {
                    @SuppressWarnings("unused")
					@Override
                    public void mouseClicked(MouseEvent  e) {
                    	System.out.print("Clicked" + get_i.toString());
		                    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start \"\" \"" + get_i.toString() + "\"");
								try {
									Process process = pb.start();
									System.out.print("\n" + process.getInputStream() + "\n" + process.getErrorStream());

								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

                            }
                });
                
                
                // Extract icons and wait for completion.
//                File iconDir = new File(currentRelativePath.toAbsolutePath().toString() + "\\tempIcons");
//                System.out.println(iconDir);
//                String[] iconsextCmd = { "iconsext.exe", "/save", currentRelativePath.toAbsolutePath().toString() + "\\" + file.getName(), iconDir.getPath(), "-icons" };
//                
//				try {
//					iconsextProc = Runtime.getRuntime().exec(iconsextCmd);
//				} catch (IOException e1) {
//					System.out.println(e1);
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//                try {
//					iconsextProc.waitFor();
//				} catch (InterruptedException e1) {
//					System.out.println(e1);
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}


                try{
                	BufferedImage image = JIconExtractor.getJIconExtractor().extractIconFromFile(get_i,IconSize.EXTRALARGE);
                    pan.setIcon(new ImageIcon(image));
                    pan.setText(file.getName());
                }catch(Exception e){
                	BufferedImage image = JIconExtractor.getJIconExtractor().extractIconFromFile(get_i,IconSize.EXTRALARGE);
                    pan.setIcon(new ImageIcon(image));
                    pan.setText(file.getName());
                }
                gamePanel.add(pan);            	
                System.out.println(file.getName());
            }
        }
        
        
        gamePanel.setLayout(new GridLayout(5,2));

		final JPanel panel = new JPanel();
    	panel.setLayout(null);
		
		final JLabel InfoLabel = new JLabel("");
		InfoLabel.setBounds(10,380,260, 40);
		
		final JLabel passL = new JLabel("Password");
		passL.setBounds(10,330,100,30);
		
		final JPasswordField pass = new JPasswordField();
		pass.setBounds(80,330,190,30);
		
		JButton button = new JButton("Interet Explorer");
		button.setBounds(15,430,150, 40);
		
		JButton buttonExit = new JButton("Exit");
		buttonExit.setBounds(180,430,80, 40);
		
		pass.addActionListener(new AbstractAction()
		{
			private static final long serialVersionUID = -7613390104708677312L;

			@SuppressWarnings("deprecation")
			@Override
		    public void actionPerformed(ActionEvent e)
		    {
		    	if(pass.getText().equals("admin")){
		    		panel.remove(passL);
		    		panel.add(gamePanel);
		    		panel.remove(pass);
		    		panel.revalidate();
		    		panel.repaint();
			        Thread thread = new Thread(new Runnable () {
			            @Override
			            public void run() {
			            	for(int i = 30; i >= 0 ; i--){
			            		InfoLabel.setText("This Launcher Will Expire on: " + i + " seconds");
			            		InfoLabel.setForeground(new Color(50,255,50));
			            		try{
			            			Thread.sleep(1000);
			            		}catch(Exception e){}
			                }
			            	panel.add(pass);
			            	panel.remove(gamePanel);
			            	pass.setText("");
			            	panel.add(passL);
				    		panel.revalidate();
				    		panel.repaint();
				    		setLocationRelativeTo(null);
			            	InfoLabel.setText("Launcher Expired Please Login Again!");			            	
			            	InfoLabel.setForeground(new Color(255,0,0));
			            }
			        });
			        thread.start();
		    	}else{
		    		InfoLabel.setText("Wrong Password!");
		    		InfoLabel.setForeground(new Color(255,0,0));
		    	}

		    }
		});
		
		buttonExit.addActionListener(new ActionListener() {
			 public void actionPerformed (ActionEvent e) {
				  System.exit(0);
				 }
				});
		
		  
		panel.add(button);
		panel.add(pass);
		panel.add(passL);
		panel.add(InfoLabel);
		panel.add(buttonExit);
		panel.add(versionLabel);
		add(panel);
        setSize(1280,720);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public static void main(String[] args) {
        new Main();
    }

	
	
	
	

}
