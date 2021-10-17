/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import java.util.*;

/**
 *
 * @author aniketkumar
 */
public class Finder {
    
    int wordlength;
    int textlength;
    String toFind;
    String findFrom;
    ArrayList<Integer> indexList;
    
    ArrayList<Integer> Finder(String toFind, String findFrom)
    {
        this.toFind = toFind;
        this.findFrom = findFrom;
        indexList = new ArrayList<>();
        this.StringSearch();
        return indexList;
    }
    
    
    private void StringSearch()
    {
        wordlength = this.toFind.length();
        textlength = this.findFrom.length();
        long hashWord = 0;
        long hashText = 0;        
        if(textlength < wordlength)
        {
            return;
        }
        
        for(int i = 0; i < wordlength; i++)
        {
            hashWord = hashWord + (toFind.charAt(i));
        }
        
        for(int i = 0; i < wordlength; i++)
        {
            hashText = hashText + (findFrom.charAt(i));
        }
        
        for(int i = 1; i<=(textlength - wordlength);i++)
        {
            if(hashWord == hashText)
            {
                matchstring(i);
            }
            hashText = hashText - findFrom.charAt(i - 1);
            hashText = hashText + findFrom.charAt(i + wordlength - 1);
        }
        if(hashWord == hashText)
        {
            matchstring(textlength - wordlength);
        }
    }
    
    private void matchstring(int i)
    {
        i--;
        if(toFind.equals(findFrom.substring(i, i + wordlength)));
        {
            indexList.add(i);
        }
    }
}
