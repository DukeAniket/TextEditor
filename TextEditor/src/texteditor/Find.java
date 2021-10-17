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
public class Find extends JFrame implements ActionListener{
    
    JTextArea workspace;
    JLabel lbl_find;
    JTextField tf_find;
    JButton findbtn;
    JButton cancelbtn;
    JButton nextbtn;
    String findFrom;
    String toFind;
    ArrayList<Integer> indexList = new ArrayList<>();;
    int arrayindex;
    int wordlength;
    int textlength;
    Font font = new Font("Helvetica", Font.PLAIN, 16);
    Font btnfont = new Font("Helvetica", Font.PLAIN, 15);
    Insets insets = new Insets(10, 10, 7, 10);
    
    Find(JTextArea workspace)
    {
        this.workspace = workspace;
        create_findlabel();
        create_findTextField();
        create_findbtn();
        create_nextbtn();
        create_cancelbtn();
        createWindow();
    }
    
    private void create_findlabel()
    {
        this.lbl_find = new JLabel("Find: ");
        this.lbl_find.setBounds(100, 50, 50, 20);
        this.lbl_find.setFont(font);
        this.add(this.lbl_find);
    }
    
    private void create_findTextField()
    {
        this.tf_find = new JTextField();
        this.tf_find.setBounds(170, 50, 250, 20);
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
    
    private void create_findbtn()
    {
        this.findbtn = new JButton("Find");
        this.findbtn.setBounds(110, 130, 100, 25);
        this.findbtn.setFont(btnfont);
        this.findbtn.setActionCommand("findbtn");
        this.findbtn.addActionListener(this);
        this.findbtn.setMargin(insets);
        this.add(findbtn);
    }
    
    private void create_nextbtn()
    {
        this.nextbtn = new JButton("Next");
        this.nextbtn.setBounds(220, 130, 100, 25);
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
        this.cancelbtn.setBounds(330, 130, 100, 25);
        this.cancelbtn.setFont(btnfont);
        this.cancelbtn.setActionCommand("cancelbtn");
        this.cancelbtn.addActionListener(this);
        this.cancelbtn.setMargin(insets);
        this.add(cancelbtn);
    }
    
    private void createWindow()
    {
        this.setLayout(null);
        this.setBounds(420, 220, 550, 250);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
    }
    
    private void find()
    {
        arrayindex = 0;
        indexList.clear();
        this.findFrom = this.workspace.getText().trim();
        this.toFind = this.tf_find.getText().trim();
        if(this.toFind.isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "Enter word!");
        }
        else
        {
            StringSearch();
        }
        if(indexList.isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "Word Not Found!");
        }
        else
        {
            workspace.setCaretPosition(indexList.get(arrayindex) + wordlength - 1);
            workspace.setSelectionStart(indexList.get(arrayindex) - 1);
            workspace.setSelectionEnd(indexList.get(arrayindex) + wordlength - 1);
        }
        if(indexList.size()>1)
        {
            this.arrayindex++;
            this.nextbtn.setEnabled(true);
        }
        this.findbtn.setEnabled(false);
    }
    
    private void next()
    {
        workspace.setCaretPosition(indexList.get(arrayindex) + wordlength - 1);
        workspace.setSelectionStart(indexList.get(arrayindex) - 1);
        workspace.setSelectionEnd(indexList.get(arrayindex) + wordlength - 1);
        
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
    
    private void StringSearch()
    {
        wordlength = this.toFind.length();
        textlength = this.findFrom.length();
        long hashWord = 0;
        long hashText = 0;
        int p = 13;
        
        if(textlength < wordlength)
        {
            return;
        }
        
        for(int i = 0; i < wordlength; i++)
        {
            hashWord = (hashWord + (toFind.charAt(i)))%p;
        }
        
        for(int i = 0; i < wordlength; i++)
        {
            hashText = (hashText + (findFrom.charAt(i)))%p;
        }
        
        for(int i = 1; i<=(textlength - wordlength);i++)
        {
            if(hashWord == hashText)
            {
                matchstring(i);
            }
            hashText = (hashText - findFrom.charAt(i - 1))%p;
            hashText = (hashText + findFrom.charAt(i + wordlength - 1))%p;
        }
        if(hashWord == hashText)
        {
            matchstring(textlength - wordlength);
        }
    }
    
    private void matchstring(int i)
    {
        if(toFind.equals(findFrom.substring(i, i+wordlength-1)));
        {
            indexList.add(i);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch(cmd)
        {
            case "findbtn": find(); break;
            case "nextbtn": next(); break;
            case "cancelbtn": cancel(); break;
        }
    }
    
}
