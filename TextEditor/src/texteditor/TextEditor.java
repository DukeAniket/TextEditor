/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.*;

/**
 *
 * @author aniketkumar
 */

public class TextEditor extends JFrame implements ActionListener{
    
    JMenuBar menubar;
    
    //MenuBar Items
    JMenu file;
    JMenu edit;
    JMenu tools;
    JMenu format;
    JMenu themes;
    JMenu help;
    
    //File Menu Items
    JMenuItem newfile;
    JMenuItem openfile;
    JMenuItem savefile;
    JMenuItem saveasfile;
    JMenuItem printfile;
    
    //Edit Menu Items
    JMenuItem exit;
    JMenuItem copy;
    JMenuItem cut;
    JMenuItem paste;
    JMenuItem selectall;

    //Tools Menu Items
    
    //Format Menu Items
    
    //Themes Menu Items
    JMenuItem light;
    JMenuItem dark;
    
    //Help Menu Items
    JMenuItem about;
    
    //Text Area
    JTextArea workspace;
    JScrollPane scrollpane;
    
    String text;
    String fileName;
    
    TextEditor()
    {
        //Menu Bar
        menubar = new JMenuBar();
        
        //Menu
        
        //File Menu
        file = new JMenu("File");
        
        menubar.add(file);
        
        //File Menu Items.
        newfile = new JMenuItem("New");
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        newfile.addActionListener(this);
        file.add(newfile);
        
        openfile = new JMenuItem("Open");
        openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        openfile.addActionListener(this);
        file.add(openfile);
        
        savefile = new JMenuItem("Save");
        savefile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        savefile.addActionListener(this);
        file.add(savefile);
        
        saveasfile = new JMenuItem("Save As");
        saveasfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        saveasfile.addActionListener(this);
        file.add(saveasfile);
        
        printfile = new JMenuItem("Print");
        printfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        printfile.addActionListener(this);
        file.add(printfile);
        
        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        exit.addActionListener(this);
        file.add(exit);
        
        
        //Edit Menu
        edit = new JMenu("Edit");
        menubar.add(edit);
        
        //Edit Menu Items
        copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        copy.addActionListener(this);
        edit.add(copy);
        
        cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        cut.addActionListener(this);
        edit.add(cut);
        
        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        paste.addActionListener(this);
        edit.add(paste);
        
        selectall = new JMenuItem("Select All");
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        selectall.addActionListener(this);
        edit.add(selectall);
        
        
        //Tools Menu
        tools = new JMenu("Tools");
        menubar.add(tools);
        
        //Tools Menu Items
        
        //Format Menu
        format = new JMenu("Format");
        menubar.add(format);
        
        //Format Menu Items
        
        
        //Themes Menu
        themes = new JMenu("Themes");
        menubar.add(themes);
        
        //Themes Menu Items
        light = new JMenuItem("Light");
        light.addActionListener(this);
        themes.add(light);
        
        dark = new JMenuItem("Dark");
        dark.addActionListener(this);
        themes.add(dark);
        
        
        //Help Menu
        help = new JMenu("Help");
        menubar.add(help);
        
        //Help Menu Items
        about = new JMenuItem("About Editor");
        about.addActionListener(this);
        help.add(about);
        
        setJMenuBar(menubar);
        
        workspace = new JTextArea();
        workspace.setLineWrap(true);
        workspace.setWrapStyleWord(true);
        
        scrollpane = new JScrollPane(workspace);
        scrollpane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollpane, BorderLayout.CENTER);
        
        setBounds(50,50,1300,700);
        setVisible(true);
    }
            
    public static void main(String[] args) {
        new TextEditor().setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("New"))
        {
            workspace.setText("");
            this.setTitle("");
            fileName = null;
        }
        else if(e.getActionCommand().equals("Open"))
        {
            open();
        }
        else if(e.getActionCommand().equals("Save"))
        {
            if(fileName==null)
            {
                savefile();
            }
            else
            {
                save();
            }
        }
        else if(e.getActionCommand().equals("Save As"))
        {
            savefile();
        }
        else if(e.getActionCommand().equals("Print"))
        {
            try{
                workspace.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }
        else if(e.getActionCommand().equals("Copy"))
        {
            text = workspace.getSelectedText();
        }
        else if(e.getActionCommand().equals("Cut"))
        {
            text = workspace.getSelectedText();
            workspace.replaceRange("", workspace.getSelectionStart(), workspace.getSelectionEnd());
        }
        else if(e.getActionCommand().equals("Paste"))
        {
            workspace.insert(text, workspace.getCaretPosition());
        }
        else if(e.getActionCommand().equals("Select All"))
        {
            workspace.selectAll();
        }
        else if(e.getActionCommand().equals("Light"))
        {
            setBackground(Color.WHITE);
            menubar.setBackground(Color.WHITE);
            menubar.setForeground(Color.BLACK);
            UIManager.put("MenuItem.background", Color.WHITE);
            UIManager.put("MenuItem.opaque", true);
            workspace.setBackground(Color.WHITE);
            workspace.setForeground(Color.BLACK);
            workspace.setCaretColor(Color.BLACK);
        }
        else if(e.getActionCommand().equals("Dark"))
        {
            setBackground(Color.BLACK);
            menubar.setBackground(Color.BLACK);
            menubar.setOpaque(true);
            menubar.setForeground(Color.WHITE);
            UIManager.put("Menu.background", Color.BLACK);
            UIManager.put("Menu.foreground", Color.WHITE);
            UIManager.put("MenuItem.background", Color.BLACK);
            UIManager.put("MenuItem.opaque", true);
            workspace.setBackground(Color.BLACK);
            workspace.setForeground(Color.WHITE);
            workspace.setCaretColor(Color.WHITE);
        }
        else if(e.getActionCommand().equals("About"))
        {
            new About().setVisible(true);
        }
    }
    
    private void open()
    {
        JFileChooser open = new JFileChooser();
        open.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt Files","txt");
        open.addChoosableFileFilter(restrict);
        open.setApproveButtonText("Open");

        int action = open.showOpenDialog(open);
        if(action!=JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        fileName = open.getSelectedFile().toString();
        this.setTitle(fileName);
        File file = open.getSelectedFile();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            workspace.read(reader, null);
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    private void savefile()
    {
        JFileChooser saveas = new JFileChooser();
        saveas.setApproveButtonText("Save As");
        int action = saveas.showSaveDialog(this);
        if(action != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        fileName = new String(saveas.getSelectedFile() + ".txt");
        this.setTitle(fileName);
        File filename = new File(fileName);
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(filename));
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    private void save()
    {
        this.setTitle(fileName);
        File filename = new File(fileName);
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(filename));
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
}
