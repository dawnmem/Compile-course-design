package cPreprocessing;

import java.util.ArrayList;
import java.util.Vector;

public class Operator_Precedence {
    String[] proRule;
    String tab = ""; 
    public String processString = "";
    public boolean t_f;
    public String computeString="";
    public String macroValue="";
    ArrayList<String> Vt = new ArrayList<String>();
    Vector<Vector<String>> wresVector;
    StepCalculate stepCalculate;
    private int[][] priorityTab = {
        /*      ,   =   +=  -=  *=  /=  %=   ?   :   ||  &&  |   ^   &   ==   !=  >   >=   <  <=  <<  >>   +   -   *   /   %   ~   !   [   ]   (   )   i   #
/*  ,   */  {   1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  =   */  {   1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  +=  */  {   1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  -=  */  {   1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  *=  */  {   1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  /=  */  {   1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  %=  */  {   1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  ?   */  {   1,  1,  1,   1,  1,  1,  1,  2,  0, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1,  1, -1,  1 },
/*  :   */  {   1,  1,  1,   1,  1,  1,  1, -1,  2, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  ||  */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  &&  */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  |   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  ^   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  &   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  ==  */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  !=  */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  >   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  >=  */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  <   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  <=  */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  <<  */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  >>  */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  +   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  -   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  *   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  /   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  %   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  ~   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  !   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1, -1, -1, -1,  1, -1,  1, -1,  1 },
/*  [   */  {  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1,  2, -1,  2 },
/*  ]   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  2,  1,  2,  1 },
/*  (   */  {  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1,  0, -1,  2 },
/*  )   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  2,  1,  2,  1,  2,  1 },
/*  i   */  {   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,   1,  1,  1,   1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  2,  1,  2,  1,  2,  1 },
/*  #   */  {  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1,  2, -1,  0 },
};

    public Operator_Precedence(String[] proRule, ArrayList<String> Vt,Vector<Vector<String>> wresVector,boolean t_f) {
        this.proRule = proRule;
        this.Vt = Vt;
        this.t_f=t_f;
        this.wresVector=wresVector;
        this.tab=" ";
        if(t_f){
            this.stepCalculate=new StepCalculate();
        }
    }

    public String createTab(int i) {
        String res = "";
        for (int k = 0; k < i; k++) {
            res += tab;
        }
        return res;
    }

    public boolean check() {
        String priorString = "";
        String waitInput = "";
        String restString = ""; 
        String actionString = ""; 
        Vector<String> wvector=new Vector<>();
        Vector<Vector<String>> symbolStack = new Vector<Vector<String>>();
        Vector<Vector<String>> vectorStack=new Vector<Vector<String>>(wresVector);
        Vector<String> c1=new Vector<String>();
        Vector<String> c2=new Vector<String>();
        int index=0;
        int index1;
        int index2;
        c1.add("#");
        c2.add("#");
        symbolStack.add(c1);
        vectorStack.add(c2);
        processString += ("step" + createTab(2) + "symbolStack" + createTab(8) +  "prior" + createTab(2)
                + "waitInput" + createTab(2) + "restString" + createTab(vectorStack.size())
                + "action" + "\n");
        if(vectorStack.size()==1){
            priorString="=";
            waitInput="#";
            actionString="拒绝";
            showProcess(index, symbolStack,priorString , waitInput, restString, actionString);
            return false;
        }
        waitInput = vectorStack.get(0).get(0);
        wvector=new Vector<>(vectorStack.get(0));
        vectorStack.remove(0);
        for(int i=0;i<vectorStack.size();i++)
            restString+=vectorStack.get(i).get(0);
        while(true) {
            String char1 = "";
            String char2="";
            int flag0=1;
            for(int i=symbolStack.size()-1;i>=0;i--){
                if(!symbolStack.get(i).get(0).equals("z")){
                    char1=symbolStack.get(i).get(0);
                    flag0=i;
                    break;
                }
            }
            index1=Vt.indexOf(char1);
            index2=Vt.indexOf(waitInput);
            if(priorityTab[index1][index2]==1){
                priorString=">";
            }
            else if(priorityTab[index1][index2]==-1){
                priorString="<";
            }
            else if(priorityTab[index1][index2]==0){
                priorString="=";
            }
            else{
                priorString=" ";
            }
            if(priorString.equals("<") || priorString.equals("=")) {
                if(symbolStack.get(0).get(0).equals("#") && waitInput.equals("#")&& symbolStack.get(1).get(0).equals("z")) {
                    actionString = "接受";
                    if(t_f==true){
                        macroValue=stepCalculate.convertString;
                        computeString+="计算完成\n";
                    }
                    showProcess(index, symbolStack, priorString, waitInput, restString, actionString);
                    return true;
                }
                actionString = "移进";
                showProcess(index, symbolStack, priorString, waitInput, restString, actionString);
                symbolStack.add(wvector);
                if(vectorStack.size()>0){
                    waitInput=vectorStack.get(0).get(0);
                    wvector=new Vector<>(vectorStack.get(0));
                    vectorStack.remove(0);
                    if(vectorStack.size()>0)
                        for(int i=0;i<vectorStack.size();i++)
                            char2+=vectorStack.get(i).get(0);
                }
                restString=char2;
            }
            else if(priorString.equals(" ")){
                actionString = "拒绝";
                showProcess(index, symbolStack, priorString, waitInput, restString, actionString);
                return false;
            }
            else {
                int flag1;
                String gcut="";
                actionString = "规约";
                showProcess(index, symbolStack, priorString, waitInput, restString, actionString);
                for(flag1=flag0-1;flag1>=0;flag1--){
                    String ch=symbolStack.get(flag1).get(0);
                    if(ch.equals("z"))
                        continue;
                    index2=Vt.indexOf(ch);
                    if(priorityTab[index2][index1]==-1||flag1==0)
                        break;
                    index1=index2;
                }
                for(int i=flag1+1;i<symbolStack.size();i++)
                    gcut+=symbolStack.get(i).get(0);
                int i = 0;
                for(i = 0; i < proRule.length; i++) {
                    String tempString = proRule[i].substring(3, proRule[i].length());
                    boolean flag = false;
                    if(isMatch(tempString, gcut)) {
                        flag = true;
                    }
                    if(flag) {
                        Vector<Vector<String>> dcomputeVector=new Vector<>();
                        Vector<Vector<String>> computeVector=new Vector<>();
                        for(int j=symbolStack.size()-1;j>flag1;j--){
                            dcomputeVector.add(symbolStack.get(j));
                            symbolStack.remove(j);
                        }
                        Vector<String> zvector=new Vector<>();
                        zvector.add("z");
                        if(t_f){
                            for(int j=dcomputeVector.size()-1;j>=0;j--){
                                computeVector.add(dcomputeVector.get(j));
                            }
                            boolean tf=stepCalculate.getStep(computeVector);
                            computeString+=stepCalculate.resultString;
                            if(tf==false){
                                return false;
                            }
                            zvector.add(stepCalculate.convertString);
                        }
                        symbolStack.add(zvector);
                        break;
                    }else {
                        continue;
                    }
                }
                if(i == proRule.length) {
                    actionString = "拒绝"; 
                    showProcess(index, symbolStack, priorString, waitInput, restString, actionString);
                    return false;
                }
         
            }
            index++;
        }
    }
            
    public void showProcess(int index, Vector<Vector<String>> symbolStack, String priorString, String waitInput, String restString, String actionString) {
        processString += (tab + index);
        String s="";
        for(int i=0;i<symbolStack.size();i++){
            Vector<String> vector=symbolStack.get(i);
            s+=vector.get(0);
        }
        if(index < 10) {
             processString += (createTab(4) + s);
        }else {
            processString += (createTab(3) + s);
        }
        for(int i = 0; i < 20 - s.length(); i++) {
            processString += (tab);
        }
        processString += (createTab(2) + priorString + createTab(4));
        processString += (createTab(4) + waitInput + createTab(8-waitInput.length()));
        processString += restString;
        for(int i = 0; i < wresVector.size() - restString.length()+10; i++) {
            processString += (tab);
        }
        processString += (tab + actionString + "\n");
    }

    public  boolean isMatch(String rightString, String reducedString) {
        String  tempString = "";
        for(int i = 0; i < rightString.length(); i++) {
            if(Character.isUpperCase(rightString.charAt(i))) {
                tempString += "z";
            }else {
                tempString += String.valueOf(rightString.charAt(i));
             }
        }
        if(reducedString.trim().equals(tempString)) {
            return true;
        }else {
            return false;
        }
    }
}

