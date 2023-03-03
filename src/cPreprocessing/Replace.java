package cPreprocessing;

import java.util.ArrayList;
import java.util.Vector;

public class Replace {
    public Vector<Vector<String>> resVector = new Vector<>();
    public ArrayList<Integer> delimiterNum = new ArrayList<>();
    private Num_Var num_var;
    public String procedure;
    public Replace(String procedure) {
        num_var = new Num_Var();
        this.procedure=procedure;
    }
    public void Process(String x) {
        if(x.equals("#define")){
            Vector<String> tempVector0 = new Vector<String>();
            tempVector0.add(x);
            tempVector0.add("0");
            resVector.add(tempVector0);
        }
        else if (num_var.isVar(x)) {
            Vector<String> tempVector1 = new Vector<String>();
            tempVector1.add(x);
            tempVector1.add("var");
            resVector.add(tempVector1);
        } else {
            Vector<String> tempVector8 = new Vector<String>();
            tempVector8.add(x);
            tempVector8.add("1");
            resVector.add(tempVector8);
        }
    }

    public void mainFunc() { 
        for (int i = 0; i < procedure.length(); i++) {
            char temp = procedure.charAt(i);
            String subString = "";
            int j = i;
            if (temp == '#'&& i < procedure.length() - 1) {
                temp = procedure.charAt(i + 1);
            }
            while (temp >= 'a' && temp <= 'z' || temp >= 'A' && temp <= 'Z' || temp >= '0' && temp <= '9'
                    || temp == '_') {
                if (++i < procedure.length())
                    temp = procedure.charAt(i);
                else
                    break;
            }
            if (procedure.charAt(j) == '#') {
                if (procedure.substring(j, i).equals("#define")||procedure.substring(j, i).equals("#include")) {
                    subString = procedure.substring(j, i);
                    i--;
                } else {
                    i = j;
                    subString = "#";
                }
            } else if (i == j) {
                subString = Character.toString(temp);
            } else {
                subString = procedure.substring(j, i);
                i--;
            }
            Process(subString);
        }
        for (int i = 0; i < resVector.size(); i++) {
            Vector<String> vector1 = resVector.get(i);
            if (vector1.get(0).equals("main")) {
                break;
            }
            if (i < resVector.size()-1&&vector1.get(0).equals("#define")) {
                String varString = "";
                String temp = "";
                i++;
                while(i<resVector.size()&&resVector.get(i).get(0).equals(" ")||resVector.get(i).get(0).equals("\t")){
                    i++;
                }
                if(i<resVector.size()){
                    varString=resVector.get(i).get(0);
                }
                i++;
                if(!num_var.isVar(varString)){
                    continue;
                }
                if(i<resVector.size()){
                    vector1=resVector.get(i);
                    while (!vector1.get(0).equals("#define") && !vector1.get(0).equals("void")&&!vector1.get(0).equals("#include")&&!vector1.get(0).equals("\n") ) {
                        temp += vector1.get(0);
                        resVector.remove(i);
                        if(i==resVector.size()) break;
                        vector1=resVector.get(i);
                    }
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
    }
}
