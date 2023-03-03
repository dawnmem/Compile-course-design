package cPreprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class GrammarAnalysis {
    public Map<String, String> expMap = new LinkedHashMap<String, String>();
    public String resString = "";
    public ArrayList<String> mainExpArrayList = new ArrayList<>();
    LexicalAnalysis lexical_Analysis;
    Operator_Precedence operator_Precedence;
    Vector<Vector<String>> resVector = new Vector<>();
    ArrayList<String> Vt = new ArrayList<String>(Arrays.asList(
            ",", "=", "+=", "-=", "*=", "/=", "%=","?",":","||", "&&", "|", "^", "&", "==", "!=",
            ">", ">=", "<", "<=", "<<", ">>", "+", "-", "*", "/", "%",  "~","!", "[","]",
            "(", ")", "i", "#"));
    String []proRule = {"S->#A#", "A->A,B", "A->B", "B->C=B", "B->C+=B",
            "B->C-=B", "B->C*=B", "B->C/=B", "B->C%=B", "B->C","C->D?D:C","C->D?:C", "C->D","D->D||E", "D->E",
            "E->E&&F", "E->F", "F->F|G", "F->G", "G->G^H", "G->H", "H->H&I",
            "H->I", "I->I==J", "I->I!=J", "I->J", "J->J>K", "J->J>=K", "J->J<K",
            "J->J<=K", "J->K", "K->K<<L", "K->K>>L", "K->L", "L->L+M", "L->L-M",
            "L->M", "M->M*N", "M->M/N", "M->M%N", "M->N", "N->!N", "N->~N","N->O", "O->[A]","O->(A)",
            "O->i"};
    public GrammarAnalysis(String procedure) {
        lexical_Analysis = new LexicalAnalysis();
        lexical_Analysis.initNum();
        lexical_Analysis.mainFunc(procedure);
        this.resVector = lexical_Analysis.resVector;
    }

    public Vector<Vector<String>> convert(int k) {
        Vector<String> vector;
        Vector<Vector<String>> wresVector = new Vector<>();
        if(++k<resVector.size()){
            for(int i = k; i < resVector.size(); i++) {
                vector = resVector.get(i);
                if(vector.get(0).equals("#define")||
                vector.get(0).equals("#include")||
                vector.get(0).equals("void")||
                vector.get(0).equals("\n")){
                    break;
                }
                else{
                    Vector<String> svector=new Vector<>(vector);
                    if(!Vt.contains(vector.get(0))||vector.get(0).equals("i"))
                        svector.add(0,"i");
                    wresVector.add(svector);
                }
            }
        }
        return wresVector;
    }

    public boolean checkMacro() {
        boolean resFlag = true;
        Vector<String> vector;
        for(int k = 0; k < resVector.size(); k++) {
            vector = resVector.get(k);
            if(vector.get(0).equals("main")){
                    break;
            }
            if(!vector.get(1).equals("MACRO")){
                continue;
            }
            if(resVector.get(k-1).get(0).equals("#define")) {
                String waitString = vector.get(2); 
                String cwaitString="";
                Vector<Vector<String>> wresVector;
                resString += ("待分析的宏常量:" + vector.get(0) + "\n");
                resString += ("待分析的表达式:" + waitString + "\n");
                System.out.println("待分析的宏常量:" + vector.get(0));
                System.out.println("待分析的表达式:" + waitString);
                expMap.put(vector.get(0), waitString);
                wresVector = convert(k);
                for(int i=0;i<wresVector.size();i++)
                    cwaitString+=wresVector.get(i).get(0);
                resString += ("转换后:" + cwaitString + "\n");
                System.out.println("转换后:" + cwaitString);
                operator_Precedence = new Operator_Precedence(proRule,Vt,wresVector,false);
                operator_Precedence.processString = "";
                boolean flag = operator_Precedence.check();
                resString += operator_Precedence.processString;
                operator_Precedence.processString = "";
                if(flag) {
                    resString += ("Successful!" + "\n" + "\n");
                    System.out.println("Successful!");
                    System.out.println();
                }else {
                    resFlag = false;
                    resString += ("failed!" + "\n" + "\n");
                    System.out.println("failed!");
                    System.out.println();
                }
            }
        }
        return resFlag;
    }
}