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
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.undo.*;

/**
 *
 * @author aniketkumar
 */

public final class TextEditor extends JFrame implements ActionListener{
    
    JMenuBar menubar;
    
    //MenuBar Items
    JMenu file, edit, tools, format, themes, help;
    
    //File Menu Items
    JMenuItem newfile, openfile, savefile, saveasfile, printfile, exit;
    
    //Edit Menu Items
    JMenuItem copy, cut, paste, selectall, undo, redo, clearall;
    
    //Format Menu Items
    JMenuItem font, size, color;
    
    //Themes Menu Items
    JMenuItem light, dark;
    
    //Help Menu Items
    JMenuItem about;
    
    //Text Area
    JTextArea workspace;
    JScrollPane scrollpane;
    
    String text;
    String fileName;
    
    UndoManager undoManager;
    
    TextEditor()
    {   
        //Menu Bar
        menubar = new JMenuBar();
        create_menubar();
        setJMenuBar(menubar);
        
        undoManager = new UndoManager();
        
        //Work Space
        create_workspace();
        
        //Window Properties
        window_properties();
        
    }
            
    public static void main(String[] args) {
        new TextEditor().setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("New"))
            newfile();
        
        else if(e.getActionCommand().equals("Open"))
            open();
        
        else if(e.getActionCommand().equals("Save"))
        {
            if(fileName==null)
                saveasfile();
            else
                save();
        }
        
        else if(e.getActionCommand().equals("Save As"))
            saveasfile();
        
        else if(e.getActionCommand().equals("Print"))
            printfile();
        
        else if(e.getActionCommand().equals("Exit"))
            quit();
        
        else if(e.getActionCommand().equals("Copy"))
            copy();
        
        else if(e.getActionCommand().equals("Cut"))
            cut();
        
        else if(e.getActionCommand().equals("Paste"))
            paste();
        
        else if(e.getActionCommand().equals("Select All"))
            selectall();
           
        else if(e.getActionCommand().equals("Undo"))
            undo();
        
        else if(e.getActionCommand().equals("Redo"))
            redo();
        
        else if(e.getActionCommand().equals("Clear All"))
            clearall();
        
        else if(e.getActionCommand().equals("Light"))
            light();
            
        else if(e.getActionCommand().equals("Dark"))
            dark();
            
        else if(e.getActionCommand().equals("About"))
            about();
    }
    
    private void window_properties()
    {
        this.setTitle("Untitled Document");
        this.setBounds(50,50,1300,700);
        this.setVisible(true);
        
    }
    
    public void create_menubar()
    {
        //File Menu
        file = new JMenu("File");
        create_file_menu();
        menubar.add(file);
        
        
        //Edit Menu
        edit = new JMenu("Edit");
        create_edit_menu();
        menubar.add(edit);
        
        
        //Format Menu
        format = new JMenu("Format");
        create_format_menu();
        menubar.add(format);
        
        
        //Themes Menu
        themes = new JMenu("Themes");
        create_themes_menu();
        menubar.add(themes);
        
        
        //Help Menu
        help = new JMenu("Help");
        create_help_menu();
        menubar.add(help);
    }
    
    private void create_file_menu()
    {
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
    }
    
    private void create_edit_menu()
    {
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
        
        undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        undo.addActionListener(this);
        edit.add(undo);
        
        redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        redo.addActionListener(this);
        edit.add(redo);
        
        clearall = new JMenuItem("Clear All");
        clearall.addActionListener(this);
        edit.add(clearall);
        
    }
    
    private void create_format_menu()
    {
        //Format Menu Items
        
    }
    
    private void create_themes_menu()
    {
        //Themes Menu Items
        light = new JMenuItem("Light");
        light.addActionListener(this);
        themes.add(light);
        
        dark = new JMenuItem("Dark");
        dark.addActionListener(this);
        themes.add(dark);
    }
    
    private void create_help_menu()
    {
        //Help Menu Items
        about = new JMenuItem("About Editor");
        about.addActionListener(this);
        help.add(about);
    }
    
    private void create_workspace()
    {
        workspace = new JTextArea();
        workspace.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
        workspace.setLineWrap(true);
        workspace.setWrapStyleWord(true);
        
        scrollpane = new JScrollPane(workspace);
        scrollpane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollpane, BorderLayout.CENTER);
    }
    
    private void newfile()
    {
        workspace.setText("");
        this.setTitle("Untitled Document");
        fileName = null;
    }
    
    private void open()
    {
        JFileChooser open = new JFileChooser();
        open.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter(".txt Files","txt");
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
    
    private void saveasfile()
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
    
    private void printfile()
    {
        try{
            workspace.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }
    
    private void quit()
    {
        System.exit(0);
    }
    
    private void copy()
    {
        text = workspace.getSelectedText();
    }
    
    private void cut()
    {
        text = workspace.getSelectedText();
        workspace.replaceRange("", workspace.getSelectionStart(), workspace.getSelectionEnd());
    }
    
    private void paste()
    {
        workspace.insert(text, workspace.getCaretPosition());
    }
    
    private void selectall()
    {
        workspace.selectAll();
    }
    
    private void undo()
    {
        this.undoManager.undo();
    }
    
    private void redo()
    {
        this.undoManager.redo();
    }
    
    private void clearall()
    {
        workspace.setText("");
    }
    
    private void light()
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
    
    private void dark()
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
    
    private void about()
    {
        new About().setVisible(true);
    }
    
}
