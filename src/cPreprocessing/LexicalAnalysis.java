package cPreprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class LexicalAnalysis {
    public Vector<Vector<String>> resVector = new Vector<>();
    public ArrayList<String> special = new ArrayList<>(Arrays.asList("main", "#include", "#define", "#", "\"", "'"));
    public ArrayList<Integer> specialNum = new ArrayList<>();
    public ArrayList<String> ope = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "%", ">", "<", ">=", "<=", "==",
            "!=", "!", "&&", "||", "<<", ">>", "~", "|", "^", "&", "=", "+=", "-=", "*=", "/=", "?",":" ,",", "[","]"));
    public ArrayList<Integer> opeNum = new ArrayList<>();
    public ArrayList<String> keyWord = new ArrayList<>(Arrays.asList("auto", "short", "int", "case", "long", "float",
            "double", "char", "struct", "union", "enum",
            "typedef", "const", "unsigned", "signed", "extern", "register", "static", "volatile", "void", "if",
            "else", "switch", "for", "do", "while", "goto", "continue", "break", "default", "sizeof", "return"));
    public ArrayList<Integer> keyWordNum = new ArrayList<>();
    public ArrayList<String> function = new ArrayList<String>(Arrays.asList("printf", "scanf", "memset"));
    public ArrayList<Integer> functionNum = new ArrayList<Integer>();
    public ArrayList<String> delimiter = new ArrayList<>(Arrays.asList(";", "(", ")", "{", "}"));
    public ArrayList<Integer> delimiterNum = new ArrayList<>();
    private Num_Var num_var;

    public LexicalAnalysis() {
        num_var = new Num_Var();
    }

    public void initNum() {
        for (int i = 0; i <= 5; i++) {
            specialNum.add(i);
        }
        for (int i = 6; i <= 35; i++) {
            opeNum.add(i);
        }
        for (int i = 36; i <= 67; i++) {
            keyWordNum.add(i);
        }
        for (int i = 68; i <= 70; i++) {
            functionNum.add(i);
        }
        for (int i = 71; i <= 75; i++) {
            delimiterNum.add(i);
        }
    }

    public void Process(String x) {
        if(x.equals("\n")){
            Vector<String> tempVector0 = new Vector<String>();
            tempVector0.add(x);
            tempVector0.add("-1");
            resVector.add(tempVector0);
        }
        else if (special.contains(x)) {
            int temp = specialNum.get(special.indexOf(x));
            Vector<String> tempVector1 = new Vector<String>();
            tempVector1.add(x);
            tempVector1.add(String.valueOf(temp));
            resVector.add(tempVector1);
        } else if (ope.contains(x)) {
            int temp = opeNum.get(ope.indexOf(x));
            Vector<String> tempVector2 = new Vector<String>();
            tempVector2.add(x);
            tempVector2.add(String.valueOf(temp));
            resVector.add(tempVector2);
        } else if (keyWord.contains(x)) { 
            int temp = keyWordNum.get(keyWord.indexOf(x));
            Vector<String> tempVector3 = new Vector<String>();
            tempVector3.add(x);
            tempVector3.add(String.valueOf(temp));
            resVector.add(tempVector3);
        } else if (function.contains(x)) { 
            int temp = functionNum.get(function.indexOf(x));
            Vector<String> tempVector4 = new Vector<String>();
            tempVector4.add(x);
            tempVector4.add(String.valueOf(temp));
            resVector.add(tempVector4);
        } else if (delimiter.contains(x)) { 
            int temp = delimiterNum.get(delimiter.indexOf(x));
            Vector<String> tempVector5 = new Vector<String>();
            tempVector5.add(x);
            tempVector5.add(String.valueOf(temp));
            resVector.add(tempVector5);
        } else if (num_var.isNum(x)) { 
            Vector<String> tempVector6 = new Vector<String>();
            tempVector6.add(x);
            tempVector6.add("number");
            resVector.add(tempVector6);
        } else if (num_var.isVar(x)) { 
            Vector<String> tempVector7 = new Vector<String>();
            tempVector7.add(x);
            tempVector7.add("var");
            resVector.add(tempVector7);
        } else {
            Vector<String> tempVector8 = new Vector<String>();
            tempVector8.add(x);
            tempVector8.add("uncertain!");
            resVector.add(tempVector8);
        }
    }

    public void mainFunc(String procedure) { 
        for (int i = 0; i < procedure.length(); i++) {
            char temp = procedure.charAt(i);
            String subString = "";
            int j = i;
            if ((temp == '"' || temp == '\'' || temp == '#' || temp == '>' || temp == '<' || temp == '='
                    || temp == '!' || temp == '&' || temp == '|' || temp == '+' || temp == '-' || temp == '*'
                    || temp == '/') && i < procedure.length() - 1) {
                temp = procedure.charAt(i + 1);
                switch (procedure.charAt(j)) {
                    case '"': {
                        j++;
                        while (j < procedure.length()&&procedure.charAt(j) != '"')
                            j++;
                        if (j == procedure.length()) {
                            j = i;
                            temp=procedure.charAt(i);
                            System.out.print("0");
                        } else {
                            Process("\"");
                            Vector<String> tempVector1 = new Vector<String>();
                            tempVector1.add("\""+procedure.substring(i + 1, j)+"\"");
                            tempVector1.add("\"chars\"");
                            resVector.add(tempVector1);
                            Process("\"");
                            i = j;
                            continue;
                        }
                        break;
                    }
                    case '\'': {
                        j++;
                        while (j < procedure.length()&&procedure.charAt(j) != '\'')
                            j++;
                        if (j == procedure.length()) {
                            j = i;
                            temp=procedure.charAt(i);
                        } else {
                            Process("\'");
                            Vector<String> tempVector1 = new Vector<String>();
                            tempVector1.add("'"+procedure.substring(i + 1, j)+"'");
                            tempVector1.add("\'chars\'");
                            resVector.add(tempVector1);
                            Process("\'");
                            i = j;
                            continue;
                        }
                        break;
                    }
                    case '>':
                        if (temp == '=') {
                            subString = ">=";
                            i++;
                            Process(subString);
                            continue;
                        } else if (temp == '>') {
                            subString = ">>";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '<':
                        if (temp == '=') {
                            subString = "<=";
                            i++;
                            Process(subString);
                            continue;
                        } else if (temp == '<') {
                            subString = "<<";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '=':
                        if (temp == '=') {
                            subString = "==";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '!':
                        if (temp == '=') {
                            subString = "!=";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '&':
                        if (temp == '&') {
                            subString = "&&";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '|':
                        if (temp == '|') {
                            subString = "||";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '+':
                        if (temp == '=') {
                            subString = "+=";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '-':
                        if (temp == '=') {
                            subString = "-=";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '*':
                        if (temp == '=') {
                            subString = "*=";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '/':
                        if (temp == '=') {
                            subString = "/=";
                            i++;
                            Process(subString);
                            continue;
                        } else {
                            temp = procedure.charAt(i);
                            break;
                        }
                    case '#':
                        break;
                }
            }
            while (temp >= 'a' && temp <= 'z' || temp >= 'A' && temp <= 'Z' || temp >= '0' && temp <= '9'
                    || temp == '_' || temp == '.') {
                if (++i < procedure.length())
                    temp = procedure.charAt(i);
                else
                    break;
            } // 当前i位置为一个空格符号
            if (procedure.charAt(j) == '#') {
                if (procedure.substring(j, i).equals("#define") || procedure.substring(j, i).equals("#include")) {
                    subString = procedure.substring(j, i);
                    i--;
                } else {
                    i = j;
                    subString = "#";
                }
            } else if (i == j) {
                if (temp == ' ' || temp == '\t' || temp == 13)
                    continue; // 一开始遇到空格等跳过
                subString = Character.toString(temp);
            } else {
                subString = procedure.substring(j, i);
                i--;
            }
            Process(subString);
        }
        for (int i = 0; i < resVector.size()-1; i++) {
            Vector<String> vector1 = resVector.get(i);
            Vector<String> vector2 = resVector.get(i+1);
            if (vector1.get(0).equals("main")) {
                break;
            }
            if (vector1.get(0).equals("#define")) {
                String varString = vector2.get(0).toString();
                String temp = "";
                if(!num_var.isVar(varString)){
                    continue;
                }
                if(i<resVector.size()-2){
                    i += 2; 
                    vector1=resVector.get(i);
                    while (!vector1.get(0).equals("#define") && !vector1.get(0).equals("void")&&!vector1.get(0).equals("#include")&&!vector1.get(0).equals("\n") ) {
                        temp += vector1.get(0);
                        i++;
                        if(i==resVector.size()) break;
                        vector1=resVector.get(i);
                    } 
                    i--;
                }
                for (int j = 0; j < resVector.size(); j++) {
                    Vector<String> vector = resVector.get(j);
                    if (vector.get(0).equals(varString)) {
                        resVector.get(j).set(1, "MACRO");
                        resVector.get(j).add(temp);
                    }
                }
            }
        }
        for (int i = 0; i < resVector.size()-3; i++) {
            Vector<String> vector1 = resVector.get(i);
            Vector<String> vector2 = resVector.get(i+1);
            Vector<String> vector3 = resVector.get(i+2);
            Vector<String> vector4 = resVector.get(i+3);
            if (vector1.get(0).equals("main")) {
                break;
            }
            String s = vector3.get(0).toString();
            int l = s.length();
            if (l>2&&
                vector1.get(0).equals("#include") &&
                vector2.get(0).equals("<") &&
                vector4.get(0).equals(">") &&
                s.substring(l - 2, l).equals(".h") &&
                num_var.isVar(s.substring(0, l - 2))) {
                resVector.get(i + 2).set(1, "headfile");
            }
            if (l>2&&
                vector1.get(0).equals("#include") &&
                vector2.get(0).equals("\"") &&
                vector4.get(0).equals("\"") &&
                s.substring(l - 2, l).equals(".h") &&
                num_var.isVar(s.substring(0, l - 2))) {
                resVector.get(i + 2).set(1, "headfile");
            }
        }
        if (resVector.size() > 1) {
            Vector<String> vector11 = resVector.get(1);
            Vector<String> vector22 = resVector.get(0);
            Vector<String> vector33;
            Vector<String> vector44;
            if (vector11.get(1).equals("number") && (vector22.get(0).equals("+") || vector22.get(0).equals("-"))) {
                vector11.set(0, vector22.get(0).toString() + vector11.get(0).toString());
                resVector.remove(0);
            }
            if(resVector.size()>2){
                for (int i = 2; i < resVector.size(); i++) {
                    vector11 = resVector.get(i);
                    vector22 = resVector.get(i - 1);
                    vector33 = resVector.get(i - 2);
                    if (vector11.get(1).equals("number") && vector22.get(0).equals("+")) {
                        if (!vector33.get(1).equals("var") && !vector33.get(1).equals("number")&& !vector33.get(0).equals("+")&&!vector33.get(0).equals(")")&&!vector33.get(0).equals("]")) {
                            if(vector33.get(1).equals("MACRO")){
                                vector44=resVector.get(i-3);
                                if(!vector44.get(0).equals("#define"))
                                    continue;
                            }
                            vector11.set(0, vector22.get(0).toString() + vector11.get(0).toString());
                            resVector.remove(i - 1);
                        }
                    }
                    if (vector11.get(1).equals("number") &&vector22.get(0).equals("-")) {
                        if (!vector33.get(1).equals("var") && !vector33.get(1).equals("number")&& !vector33.get(0).equals("-")&&!vector33.get(0).equals(")")&&!vector33.get(0).equals("]")) {
                            if(vector33.get(1).equals("MACRO")){
                                vector44=resVector.get(i-3);
                                if(!vector44.get(0).equals("#define"))
                                    continue;
                            }
                            vector11.set(0, vector22.get(0).toString() + vector11.get(0).toString());
                            resVector.remove(i - 1);
                        }
                    }
                }
            }
        }
    }
}
