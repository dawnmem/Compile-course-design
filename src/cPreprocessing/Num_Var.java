package cPreprocessing;

import java.util.ArrayList;
import java.util.Arrays;

public class Num_Var {
    ArrayList<Character> Vn = new ArrayList<Character>(Arrays.asList('N', 'D', 'S'));
    ArrayList<Character> Vt = new ArrayList<Character>(Arrays.asList('0', '1', '3', '2', '4', '5', '6', '7', '8', '9'));
    ArrayList<Character> XVt = new ArrayList<Character>(Arrays.asList('0', '1', '3', '2', '4', '5', '6', '7', '8', '9',
                                                                        'A','B','C','D','E','F','a','b','c','d','e','f'));                                                                   
    public boolean isNum(String x) {
        String temp = "DS";
        if(x.length()>=3&&x.charAt(0)=='0'&&(x.charAt(1)=='x'||x.charAt(1)=='X')){
            for(int i = 2; i < x.length(); i++) {
                if(XVt.contains(x.charAt(i))) {
                    temp = temp.replace('D', x.charAt(i));
                    temp = temp.replace("S", "DS");
                }else {
                    return false;
                }
            } 
            return true;
        }
        for(int i = 0,count=0; i < x.length(); i++) {
            if(x.charAt(i)=='.')
                count++;
            if(Vt.contains(x.charAt(i))||count==1) {
                temp = temp.replace('D', x.charAt(i));
                temp = temp.replace("S", "DS");
            }else {
                return false;
            }
        }
        return true;
    }
    public boolean isVar(String x) {
        String temp = "LM";
        if(Character.isLetter(x.charAt(0)) || x.charAt(0) == '_') {
            temp = temp.replace('L', x.charAt(0));
        }else {
            return false;
        }
        for(int i = 1; i < x.length(); i++) {
            char tempChar = x.charAt(i);
            if(Character.isDigit(tempChar)) {
                temp = temp.replace("M", "DM");
                temp = temp.replace('D', tempChar);
            }else if(Character.isLetter(tempChar) || tempChar == '_'){
                temp = temp.replace("M", "LM"); 
                temp = temp.replace('L', tempChar);
            }else {
                return false;
            }
        }
        temp = temp.replace("M", "");
        if(temp.equals(x)) {
            return true;
        }
        return false;
    }
}
