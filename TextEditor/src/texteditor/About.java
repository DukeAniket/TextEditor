/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author aniketkumar
 */
public class About extends JFrame {
    
    boolean darktheme;
    Image i;
    ImageIcon icon;
    JLabel icon_lbl;
    JLabel name;
    JLabel emailID;
    JLabel github;
    
    
    About(boolean darktheme)
    {
        this.darktheme = darktheme;
        this.createWindow();
    }
    
    private void createWindow()
    {
        setBounds(400,100,500,600);
        setVisible(true);
        this.setLayout(null);
        this.setAlwaysOnTop(true);
        this.setTitle("About");
        this.createIcon();
        addINFO();
        this.darktheme();
    }
    
    private void addINFO()
    {
        this.name = new JLabel();
        this.name.setBounds(120, 350, 300, 60);
        this.name.setText("<HTML><CENTER><P style=\"font-size:15px\">This project is created by <B>Aniket Kumar</B></P></CENTER></HTML>");
        this.add(name);
        
        this.emailID = new JLabel();
        this.emailID.setBounds(120, 420, 300, 50);
        this.emailID.setText("<HTML><CENTER><P style=\"font-size:12px\"><B>Email ID: </B>aniketk.28@gmail.com</P></CENTER></HTML>");
        this.add(emailID);
        
        this.github = new JLabel();
        this.github.setBounds(120, 470, 350, 50);
        this.github.setText("<HTML><CENTER><P style=\"font-size:12px\"><B>GitHub: </B>https://github.com/DukeAniket</P></CENTER></HTML>");
        this.add(github);
    }
    
    private void darktheme()
    {
        if(this.darktheme)
        {
            this.getContentPane().setBackground(Color.DARK_GRAY);
            this.getContentPane().setForeground(Color.WHITE);
            this.name.setBackground(Color.DARK_GRAY);
            this.name.setForeground(Color.WHITE);
            this.emailID.setBackground(Color.DARK_GRAY);
            this.emailID.setForeground(Color.WHITE);
            this.github.setBackground(Color.DARK_GRAY);
            this.github.setForeground(Color.WHITE);
            
        }
        else
        {
            this.getContentPane().setBackground(Color.GRAY);
            this.getContentPane().setForeground(Color.BLACK);
            this.name.setBackground(Color.WHITE);
            this.name.setForeground(Color.BLACK);
            this.emailID.setBackground(Color.WHITE);
            this.emailID.setForeground(Color.BLACK);
            this.github.setBackground(Color.WHITE);
            this.github.setForeground(Color.BLACK);
        }

    }
    
    private void createIcon()
        {
            ImageIcon i = new ImageIcon(ClassLoader.getSystemResource("texteditor/icons/icon.png"));
            Image i1 = i.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
            icon = new ImageIcon(i1);
            icon_lbl = new JLabel(icon);
            icon_lbl.setBounds(125, 80, 250, 250);
            this.add(icon_lbl);
        }
}
