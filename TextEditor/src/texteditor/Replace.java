/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 *
 * @author aniketkumar
 */
public class Replace extends JFrame implements ActionListener{
    
    String title;
    JTextArea workspace;
    JLabel lbl_find;
    JTextField tf_find;
    JLabel lbl_replace;
    JTextField tf_replace;
    JButton findbtn;
    JButton cancelbtn;
    JButton nextbtn;
    JButton replacebtn;
    JButton replaceallbtn;
    String findFrom;
    String toFind;
    String toChange;
    ArrayList<Integer> indexList = new ArrayList<>();;
    int arrayindex;
    int wordlength;
    int textlength;
    int newWordLength;
    Font font = new Font("Helvetica", Font.PLAIN, 16);
    Font btnfont = new Font("Helvetica", Font.PLAIN, 15);
    Insets insets = new Insets(10, 10, 7, 10);
    
    Replace(JTextArea workspace)
    {
        this.workspace = workspace;
        this.createWindow();
    }
    
    private void createWindow()
    {
        this.title = new String("Find and Replace");
        this.setLayout(null);
        this.setBounds(400, 200, 600, 350);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        this.setTitle(title);
        this.create_findlabel();
        this.create_findTextField();
        this.create_replacelabel();
        this.create_replaceTextField();
        this.create_findbtn();
        this.create_nextbtn();
        this.create_cancelbtn();
        this.create_replacebtn();
        this.create_replaceallbtn();
    }

    private void create_findlabel()
    {
        this.lbl_find = new JLabel("Find: ");
        this.lbl_find.setBounds(70, 50, 50, 20);
        this.lbl_find.setFont(font);
        this.add(this.lbl_find);
    }
    
    private void create_replacelabel()
    {
        this.lbl_replace = new JLabel("Replace: ");
        this.lbl_replace.setBounds(70, 130, 70, 20);
        this.lbl_replace.setFont(font);
        this.add(this.lbl_replace);
    }
    
    private void create_findTextField()
    {
        this.tf_find = new JTextField();
        this.tf_find.setBounds(150, 50, 230, 20);
        this.tf_find.setFont(font);
        this.tf_find.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                findbtn.setEnabled(true);
                nextbtn.setEnabled(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                findbtn.setEnabled(true);
                nextbtn.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        this.add(this.tf_find);
    }
    
    private void create_replaceTextField()
    {
        this.tf_replace = new JTextField();
        this.tf_replace.setBounds(150, 130, 230, 20);
        this.tf_replace.setFont(font);
        this.add(this.tf_replace);
    }
    
    private void create_findbtn()
    {
        this.findbtn = new JButton("Find");
        this.findbtn.setBounds(420, 50, 100, 25);
        this.findbtn.setFont(btnfont);
        this.findbtn.setActionCommand("findbtn");
        this.findbtn.addActionListener(this);
        this.findbtn.setMargin(insets);
        this.add(findbtn);
    }
    
    private void create_nextbtn()
    {
        this.nextbtn = new JButton("Next");
        this.nextbtn.setBounds(420, 130, 100, 25);
        this.nextbtn.setFont(btnfont);
        this.nextbtn.setActionCommand("nextbtn");
        this.nextbtn.addActionListener(this);
        this.nextbtn.setMargin(insets);
        this.nextbtn.setEnabled(false);
        this.add(nextbtn);
    }
    
    private void create_cancelbtn()
    {
        this.cancelbtn = new JButton("Cancel");
        this.cancelbtn.setBounds(70, 220, 100, 25);
        this.cancelbtn.setFont(btnfont);
        this.cancelbtn.setActionCommand("cancelbtn");
        this.cancelbtn.addActionListener(this);
        this.cancelbtn.setMargin(insets);
        this.add(cancelbtn);
    }
    
    private void create_replacebtn()
    {
        this.replacebtn = new JButton("Replace");
        this.replacebtn.setBounds(235, 220, 120, 25);
        this.replacebtn.setFont(font);
        this.replacebtn.setActionCommand("replacebtn");
        this.replacebtn.addActionListener(this);
        this.replacebtn.setMargin(insets);
        this.replacebtn.setEnabled(false);
        this.add(replacebtn);
    }
    
    private void create_replaceallbtn()
    {
        this.replaceallbtn = new JButton("Replace All");
        this.replaceallbtn.setBounds(400, 220, 120, 25);
        this.replaceallbtn.setFont(font);
        this.replaceallbtn.setActionCommand("replaceallbtn");
        this.replaceallbtn.addActionListener(this);
        this.replaceallbtn.setMargin(insets);
        this.replaceallbtn.setEnabled(false);
        this.add(replaceallbtn);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command)
        {
            case "findbtn": find(); break;
            case "nextbtn": next(); break;
            case "replacebtn": replace(); break;
            case "replaceallbtn": replaceall(); break;
            case "cancelbtn": cancel(); break;
        }
    }
    
    
    private void find()
    {
        arrayindex = 0;
        indexList.clear();
        this.findFrom = this.workspace.getText().toLowerCase();
        this.toFind = this.tf_find.getText().trim().toLowerCase();
        if(this.toFind.isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "Enter word!");
        }
        else
        {
            wordlength = this.toFind.length();
            textlength = this.findFrom.length();
            indexList = new Finder().Finder(toFind, findFrom);
        }
        if(indexList.isEmpty())
        {
            
        }
        else
        {
            this.setTitle(title + indexList.size());
            workspace.setCaretPosition(indexList.get(arrayindex) + wordlength);
            workspace.setSelectionStart(indexList.get(arrayindex));
            workspace.setSelectionEnd(indexList.get(arrayindex) + wordlength);
            this.replacebtn.setEnabled(true);
        }
        if(indexList.size()>1)
        {
            this.arrayindex++;
            this.nextbtn.setEnabled(true);
            this.replaceallbtn.setEnabled(true);
        }
        this.findbtn.setEnabled(false);
    }
    
    private void next()
    {
        workspace.setCaretPosition(indexList.get(arrayindex) + wordlength);
        workspace.setSelectionStart(indexList.get(arrayindex));
        workspace.setSelectionEnd(indexList.get(arrayindex) + wordlength);
        
        if(this.arrayindex == this.indexList.size()-1)
        {
            this.arrayindex = 0;
        }
        else
        {
            this.arrayindex++;
        }
    }
    
    private void cancel()
    {
        dispose();
    }
    
    private void replace()
    {
        this.toChange = this.tf_replace.getText();
        if(this.toChange.isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "Enter new word!");
        }
        else
        {
            this.workspace.replaceRange(toChange, this.workspace.getSelectionStart(), this.workspace.getSelectionEnd());
            this.find();
        }
    }
    
    private void replaceall()
    {
        this.toChange = this.tf_replace.getText();
        if(this.toChange.isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "Enter new word!");
        }
        else
        {
            while(!indexList.isEmpty())
            {
                this.workspace.replaceRange(toChange, indexList.get(0), indexList.get(0) + wordlength);
                this.find();
            }
        }
    }
    
}
