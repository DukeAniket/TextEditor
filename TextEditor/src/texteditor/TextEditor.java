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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    
    //Tools Menu Items
    JMenuItem find, replace;
    
    //Format Menu Items
    JMenu font, size, style, color;
    JMenuItem size12, size14, size16, size18, size20, size22, size24, size30, size36, size42, size48, size66, size72; 
    JMenuItem fonttype1, fonttype2, fonttype3, fonttype4, fonttype5;
    JMenuItem fontstyle_plain, fontstyle_bold, fontstyle_italic;
    JMenuItem color_red, color_blue, color_green, color_black, color_white, color_orange, color_yellow;
    
    //Themes Menu Items
    JMenuItem light, dark;
    
    //Help Menu Items
    JMenuItem about;
    
    //Text Area
    JTextArea workspace;
    JScrollPane scrollpane;
    Insets insets;
    
    String text;
    String fileName;
    String fonttype = "Helvetica";
    int fontstyle = Font.PLAIN;
    int fontsize = 12;
    Color fontcolor = Color.BLACK;
    
    
    //Undo Redo
    private Stack<UndoableEdit> editHistory;
    private Stack<UndoableEdit> undoHistory;
    
    TextEditor()
    {   
        //Menu Bar
        menubar = new JMenuBar();
        create_menubar();
        setJMenuBar(menubar);
        
        
        //Work Space
        create_workspace();
        
        editHistory = new Stack<>();
        undoHistory = new Stack<>();
        
        //Window Properties
        window_properties();
        
    }
            
    public static void main(String[] args) {
        
        String OS = System.getProperty("os.name");
        if(OS.toLowerCase().indexOf("mac")>=0)
        {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        new TextEditor();
        
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("New"))
            newfile();
        
        else if(e.getActionCommand().equals("Open"))
            open();
        
        else if(e.getActionCommand().equals("Save"))
            savefile();
            
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
        
        else if(e.getActionCommand().equals("find"))
            find();
        
        else if(e.getActionCommand().equals("replace"))
            replace();
        
        else if(e.getActionCommand().startsWith("fonttype"))
            font(e.getActionCommand());
        
        else if(e.getActionCommand().startsWith("size"))
            fontsize(e.getActionCommand());
        
        else if(e.getActionCommand().startsWith("fontstyle"))
            fontstyle(e.getActionCommand());
        
        else if(e.getActionCommand().startsWith("color"))
            fontcolor(e.getActionCommand());
        
        else if(e.getActionCommand().equals("Light"))
            light();
            
        else if(e.getActionCommand().equals("Dark"))
            dark();
            
        else if(e.getActionCommand().equals("about"))
            about();
    }
    
    
    private void window_properties()
     {
        try {
            Image i = ImageIO.read(getClass().getResource("/texteditor/icons/icon.png"));
            this.setIconImage(i);
            if(System.getProperty("os.name").toLowerCase().indexOf("mac")>=0)
            {
                final Taskbar taskbar = Taskbar.getTaskbar();
                taskbar.setIconImage(i);
            }
        } catch (IOException ex) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        updatefont();
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
        
        
        //Tools Menu
        tools = new JMenu("Tools");
        create_tools_menu();
        menubar.add(tools);
        
        
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
    
    private void create_tools_menu()
    {
        find = new JMenuItem("Find");
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        find.addActionListener(this);
        find.setActionCommand("find");
        tools.add(find);
        
        replace = new JMenuItem("Find and Replace");
        replace.addActionListener(this);
        replace.setActionCommand("replace");
        tools.add(replace);
    }
    
    private void create_format_menu()
    {
        //Format Menu Items
        font = new JMenu("Font");
        create_font_submenu();
        format.add(font);
        
        size = new JMenu("Size");
        create_size_submenu();
        format.add(size);
        
        style = new JMenu("Style");
        create_style_submenu();
        format.add(style);
        
        color = new JMenu("Color");
        create_color_submenu();
        format.add(color);
        
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
        about.setActionCommand("about");
        help.add(about);
    }
    
    private void create_workspace()
    {
        insets = new Insets(5,5,5,5);
        workspace = new JTextArea();
        workspace.setMargin(insets);
        workspace.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                if(e.getEdit().isSignificant())
                {
                    editHistory.push(e.getEdit());
                    undoHistory.clear();
                }
            }
        });
        workspace.setLineWrap(true);
        workspace.setWrapStyleWord(true);
        
        scrollpane = new JScrollPane(workspace);
        scrollpane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollpane, BorderLayout.CENTER);
    }
    
    private void create_font_submenu()
    {
        fonttype1 = new JMenuItem("Helvetica");
        fonttype1.setActionCommand("fonttype1");
        fonttype1.addActionListener(this);
        font.add(fonttype1);
        
        fonttype2 = new JMenuItem("Dialog");
        fonttype2.setActionCommand("fonttype2");
        fonttype2.addActionListener(this);
        font.add(fonttype2);
        
        fonttype3 = new JMenuItem("Monospaced");
        fonttype3.setActionCommand("fonttype3");
        fonttype3.addActionListener(this);
        font.add(fonttype3);
        
        fonttype4 = new JMenuItem("Sans Serif");
        fonttype4.setActionCommand("fonttype4");
        fonttype4.addActionListener(this);
        font.add(fonttype4);
        
        fonttype5 = new JMenuItem("Serif");
        fonttype5.setActionCommand("fonttype5");
        fonttype5.addActionListener(this);
        font.add(fonttype5);
    }
    
    private void create_size_submenu()
    {
        //size18, size22
        size12 = new JMenuItem("12");
        size12.setActionCommand("size12");
        size12.addActionListener(this);
        size.add(size12);
        
        size14 = new JMenuItem("14");
        size14.setActionCommand("size14");
        size14.addActionListener(this);
        size.add(size14);
        
        size16 = new JMenuItem("16");
        size16.setActionCommand("size16");
        size16.addActionListener(this);
        size.add(size16);
        
        size18 = new JMenuItem("18");
        size18.setActionCommand("size18");
        size18.addActionListener(this);
        size.add(size18);
        
        size20 = new JMenuItem("20");
        size20.setActionCommand("size20");
        size20.addActionListener(this);
        size.add(size20);
        
        size22 = new JMenuItem("22");
        size22.setActionCommand("size22");
        size22.addActionListener(this);
        size.add(size22);
        
        size24 = new JMenuItem("24");
        size24.setActionCommand("size24");
        size24.addActionListener(this);
        size.add(size24);
        
        size30 = new JMenuItem("30");
        size30.setActionCommand("size30");
        size30.addActionListener(this);
        size.add(size30);
        
        size36 = new JMenuItem("36");
        size36.setActionCommand("size36");
        size36.addActionListener(this);
        size.add(size36);
        
        size42 = new JMenuItem("42");
        size42.setActionCommand("size42");
        size42.addActionListener(this);
        size.add(size42);
        
        size48 = new JMenuItem("48");
        size48.setActionCommand("size48");
        size48.addActionListener(this);
        size.add(size48);
        
        size66 = new JMenuItem("66");
        size66.setActionCommand("size66");
        size66.addActionListener(this);
        size.add(size66);
        
        size72 = new JMenuItem("72");
        size72.setActionCommand("size72");
        size72.addActionListener(this);
        size.add(size72);
    }
    
    private void create_style_submenu()
    {
        //JMenuItem fontstyle_plain, fontstyle_bold, fontstyle_italic;
        fontstyle_plain = new JMenuItem("Plain");
        fontstyle_plain.addActionListener(this);
        fontstyle_plain.setActionCommand("fontstyle_plain");
        style.add(fontstyle_plain);
        
        fontstyle_bold = new JMenuItem("Bold");
        fontstyle_bold.addActionListener(this);
        fontstyle_bold.setActionCommand("fontstyle_bold");
        style.add(fontstyle_bold);
        
        fontstyle_italic = new JMenuItem("Italic");
        fontstyle_italic.addActionListener(this);
        fontstyle_italic.setActionCommand("fontstyle_italic");
        style.add(fontstyle_italic);
    }
    
    private void create_color_submenu()
    {
        color_red = new JMenuItem("Red");
        color_red.addActionListener(this);
        color_red.setActionCommand("color_red");
        color.add(color_red);
        
        color_blue = new JMenuItem("Blue");
        color_blue.addActionListener(this);
        color_blue.setActionCommand("color_blue");
        color.add(color_blue);
        
        color_green = new JMenuItem("Green");
        color_green.addActionListener(this);
        color_green.setActionCommand("color_green");
        color.add(color_green);
        
        color_black = new JMenuItem("Black");
        color_black.addActionListener(this);
        color_black.setActionCommand("color_black");
        color.add(color_black);
        
        color_white = new JMenuItem("White");
        color_white.addActionListener(this);
        color_white.setActionCommand("color_white");
        color.add(color_white);
        
        color_orange = new JMenuItem("Orange");
        color_orange.addActionListener(this);
        color_orange.setActionCommand("color_orange");
        color.add(color_orange);
        
        color_yellow = new JMenuItem("Yellow");
        color_yellow.addActionListener(this);
        color_yellow.setActionCommand("color_yellow");
        color.add(color_yellow);
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
        FileNameExtensionFilter restrict1 = new FileNameExtensionFilter(".txt Files","txt");
        FileNameExtensionFilter restrict2 = new FileNameExtensionFilter("C Files","c");
        FileNameExtensionFilter restrict3 = new FileNameExtensionFilter("Java Files","java");
        FileNameExtensionFilter restrict4 = new FileNameExtensionFilter("Python Files","py");
        FileNameExtensionFilter restrict5 = new FileNameExtensionFilter("HTML Files","html");
        FileNameExtensionFilter restrict6 = new FileNameExtensionFilter("Shell Script Files","sh");
        open.addChoosableFileFilter(restrict1);
        open.addChoosableFileFilter(restrict2);
        open.addChoosableFileFilter(restrict3);
        open.addChoosableFileFilter(restrict4);
        open.addChoosableFileFilter(restrict5);
        open.addChoosableFileFilter(restrict6);
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
            writer.write(this.workspace.getText());
            writer.flush();
            writer.close();
        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    private void savefile()
    {
        if(fileName==null)
            saveasfile();
        else
            save();
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
        fileName = new String(saveas.getSelectedFile()+"");
        if(!fileName.contains("."))
        {
            fileName = new String(fileName + ".txt");
        }
        this.setTitle(fileName);
        File filename = new File(fileName);
        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(this.workspace.getText());
            writer.flush();
            writer.close();
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
        UndoableEdit edit = editHistory.pop();
        undoHistory.push(edit);
        if(!editHistory.isEmpty())
        {
            edit.undo();
        }
    }
    
    private void redo()
    {
        UndoableEdit edit = undoHistory.pop();
        if(!undoHistory.isEmpty())
        {
            edit.redo();
        }
    }
    
    private void clearall()
    {
        workspace.setText("");
    }
    
    private void find()
    {
        new Find(this.workspace);
    }
    
    private void replace()
    {
        new Replace(this.workspace);
    }
    
    private void font(String type)
    {
        int fontindex = Integer.parseInt(type.substring("fonttype".length()));
        
        switch(fontindex)
        {
            case 1: fonttype = "Helvetica"; break;
            case 2: fonttype = Font.DIALOG; break;
            case 3: fonttype = Font.MONOSPACED; break;
            case 4: fonttype = Font.SANS_SERIF; break;
            case 5: fonttype = Font.SERIF; break;
        }
        
        updatefont();
    }
    
    private void fontsize(String size)
    {
        fontsize = Integer.parseInt(size.substring("size".length()));
        updatefont();
    }
    
    private void fontstyle(String style)
    {
        String stylecase = style.substring("fontstyle_".length());
        switch(stylecase)
        {
            case "plain": fontstyle = Font.PLAIN; break;
            case "bold": fontstyle = Font.BOLD; break;
            case "italic": fontstyle = Font.ITALIC; break;
        }
        updatefont();
    }
    
    private void fontcolor(String color)
    {
        String colorname = color.substring("color_".length());
        switch(colorname)
        {
//            color_red, color_blue, color_green, color_black, color_white, color_orange, color_yellow
            case "red": fontcolor = Color.RED; break;
            case "blue": fontcolor = Color.BLUE; break;
            case "green": fontcolor = Color.GREEN; break;
            case "black": fontcolor = Color.BLACK; break;
            case "white": fontcolor = Color.WHITE; break;
            case "orange": fontcolor = Color.ORANGE; break;
            case "yellow": fontcolor = Color.YELLOW; break;
        }
        updatefont();
    }
    
    private void light()
    {
        fontcolor = Color.BLACK;
        updatefont();
        setBackground(Color.WHITE);
        workspace.setBackground(Color.WHITE);
        workspace.setCaretColor(Color.BLACK);
    }
    
    private void dark()
    {
        fontcolor = Color.WHITE;
        updatefont();
        setBackground(Color.BLACK);
        workspace.setBackground(Color.BLACK);
        workspace.setCaretColor(Color.WHITE);
    }
    
    private void about()
    {
        new About();
    }
    
    private void updatefont()
    {
        this.workspace.setFont(new Font(fonttype, fontstyle, fontsize));
        this.workspace.setForeground(fontcolor);
    }
    
}
