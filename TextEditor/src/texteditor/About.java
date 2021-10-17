/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import javax.swing.*;

/**
 *
 * @author aniketkumar
 */
public class About extends JFrame{
    
    About()
    {
        setBounds(400,100,500,600);
        setVisible(true);
        this.setAlwaysOnTop(true);
        this.setTitle("About");
    }
    
    public static void main(String[] args)
    {
        new About();
    }
}
