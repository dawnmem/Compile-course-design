package cPreprocessing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
class Expression{
    public boolean t_f;
    public String macro;
    public String macroValue;
    public Vector<String> macros;
    public Vector<Vector<String>> expression;
    public String sExpression;
    public Expression(String macro,Vector<Vector<String>> expression,boolean t_f,Vector<String> macros,String sExpression,String macroValue){
        this.t_f=t_f;
        this.macro=macro;
        this.expression=expression;
        this.macros=macros;
        this.sExpression=sExpression;
        this.macroValue=macroValue;
    }
}
public class Compute {
    public String computeString;
    public String sExpression="";
    public String replaceString="";
    LexicalAnalysis lexical_Analysis;
    Replace replace;
    Operator_Precedence operator_Precedence;
    Vector<Vector<String>> resVector = new Vector<>();
    Vector<Expression> expressVector=new Vector<>();
    ArrayList<String> V = new ArrayList<String>(Arrays.asList(
            ",","?",":","||", "&&", "|", "^", "&", "==", "!=",
            ">", ">=", "<", "<=", "<<", ">>", "+", "-", "*", "/", "%",  "~","!", "(", ")"));   //规定一个顺序
    ArrayList<String> Vt = new ArrayList<String>(Arrays.asList(
            ",", "=", "+=", "-=", "*=", "/=", "%=","?",":","||", "&&", "|", "^", "&", "==", "!=",
            ">", ">=", "<", "<=", "<<", ">>", "+", "-", "*", "/", "%",  "~","!", "[","]",
            "(", ")", "i", "#"));   //规定一个顺序
    String []proRule = {"S->#A#", "A->A,B", "A->B", "B->C=B", "B->C+=B",
            "B->C-=B", "B->C*=B", "B->C/=B", "B->C%=B", "B->C","C->D?D:C","C->D?:C", "C->D","D->D||E", "D->E",
            "E->E&&F", "E->F", "F->F|G", "F->G", "G->G^H", "G->H", "H->H&I",
            "H->I", "I->I==J", "I->I!=J", "I->J", "J->J>K", "J->J>=K", "J->J<K",
            "J->J<=K", "J->K", "K->K<<L", "K->K>>L", "K->L", "L->L+M", "L->L-M",
            "L->M", "M->M*N", "M->M/N", "M->M%N", "M->N", "N->!N", "N->~N","N->O", "O->[A]","O->(A)",
            "O->i"};
    public Compute(String procedure){
        computeString="";
        lexical_Analysis = new LexicalAnalysis();
        lexical_Analysis.initNum();
        lexical_Analysis.mainFunc(procedure);
        replace=new Replace(procedure);
        this.resVector = lexical_Analysis.resVector;
    }
    public Vector<Vector<String>> convert(int k) {
        Vector<String> vector;
        Vector<Vector<String>> wresVector = new Vector<>();
        sExpression="";
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
                    sExpression+=svector.get(0);
                    if(!Vt.contains(vector.get(0))||vector.get(0).equals("i"))
                        svector.add(0,"i");
                    wresVector.add(svector);
                }
            }
        }
        return wresVector;
    }
    public boolean macroCompare(Vector<String> macroVector,String macro){
        if(macroVector.size()==0){
            return true;
        }
        for(int i=0;i<macroVector.size();i++){
            if(macroVector.get(i).equals(macro)){
                return false;
            }
        }
        return true;
    }
    public Vector<String> getExpressMacro(Vector<Vector<String>> wresVector){
        Vector<String> macroVector=new Vector<>();
        if(wresVector.size()==0){
            return macroVector;
        }
        for(int i=0;i<wresVector.size();i++){
            if(wresVector.get(i).size()>2&&wresVector.get(i).get(2).equals("MACRO")&&macroCompare(macroVector, wresVector.get(i).get(1))){
                macroVector.add(wresVector.get(i).get(1));
            }
        }
        return macroVector;
    }
    public void getExpress(){
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
                String macro="";
                Vector<Vector<String>> wresVector=new Vector<>();
                Vector<String> macroVector=new Vector<>();
                macro=resVector.get(k).get(0);
                wresVector = convert(k);
                macroVector=getExpressMacro(wresVector);
                expressVector.add(new Expression(macro, wresVector,true,macroVector,sExpression,""));
            }
        }
    }
    public boolean macroContain(Vector<String> macrostack,Vector<String> macros1){
        for(int i=0;i<macros1.size();i++){
            if(macroCompare(macrostack, macros1.get(i))){
                return false;
            }
        }
        return true;
    }
    public Vector<Expression> compute_pd(){
        Vector<Expression> finalExpressions=new Vector<>();
        Vector<Expression> expressionStack=new Vector<>();
        Vector<String> macroStack=new Vector<>();
        if(expressVector.size()==0){
            return finalExpressions;
        }
        for(int i=0;i<expressVector.size();i++){
            Expression e=expressVector.get(i);
            Vector<Vector<String>> f=e.expression;
            if(f.size()==0){
                computeString+=expressVector.get(i).macro+"：[]\n无法计算，表达式为空\n";
                e.t_f=false;
            }
            else{
                for(int j=0;j<f.size();j++){
                    Vector<String> n=f.get(j);
                    if(!V.contains(n.get(0))&&!n.get(2).equals("number")&&!n.get(2).equals("MACRO")){
                        computeString+=expressVector.get(i).macro+"：["+expressVector.get(i).sExpression+"]\n无法计算，表达式含有数字、运算符之外的词\n";
                        e.t_f=false;
                    }
                }
            }
        }
        for(int i=0;i<expressVector.size();i++){
            Expression e=expressVector.get(i);
            Vector<Vector<String>> f=e.expression;
            if(e.t_f==false){
                continue;
            }
            operator_Precedence=new Operator_Precedence(proRule, Vt, f, false);
            if(operator_Precedence.check()==false){
                computeString+=expressVector.get(i).macro+"：["+expressVector.get(i).sExpression+"]\n无法计算，语法错误\n";
                e.t_f=false;
            }
        }
        while(true){
            int flag=0;
            for(int i=0;i<expressVector.size();i++){
                Expression e=expressVector.get(i);
                Vector<Vector<String>> f=e.expression;
                if(e.t_f==false){
                    continue;
                }
                for(int j=0;j<f.size();j++){
                    Vector<String> n=f.get(j);
                    if(n.size()>2&&n.get(2).equals("MACRO")){
                        for(int k=0;k<expressVector.size();k++){
                            if(expressVector.get(k).macro.equals(n.get(1))){
                                if(expressVector.get(k).t_f==false){
                                    computeString+=expressVector.get(i).macro+"：["+expressVector.get(i).sExpression+"]\n无法计算，表达式含有其他错误的宏\n";
                                    e.t_f=false;
                                    flag=1;
                                }
                                break;
                            }
                        }
                        if(e.t_f==false){
                            break;
                        }
                    }
                }
            }
            if(flag==0){
                break;
            }
        }
        for(int i=0;i<expressVector.size();i++){
            Expression e=expressVector.get(i);
            if(e.t_f==false){
                continue;
            }
            else if(e.macros.size()==0){
                finalExpressions.add(e);
                macroStack.add(e.macro);
            }
            else{
                expressionStack.add(e);
            }
        }
        while(expressionStack.size()!=0){
            int flag=0;
            for(int i=0;i<expressionStack.size();i++){
                Vector<String> macros1=expressionStack.get(i).macros;
                if(macroContain(macroStack, macros1)){
                    macroStack.add(expressionStack.get(i).macro);
                    finalExpressions.add(expressionStack.get(i));
                    expressionStack.remove(i);
                    flag=1;
                }
            }
            if(flag==0){
                break;
            }
        }
        for(int i=0;i<expressionStack.size();i++){
            if(expressionStack.size()==0){
                break;
            }
            computeString+=expressionStack.get(i).macro+"：["+expressionStack.get(i).sExpression+"]\n无法计算，宏循环\n";
        }
        return finalExpressions;
    }
    public boolean findMacro(Vector<Vector<String>> resVector,int i){
        if(i==0){
            return false;
        }
        while(i>0){
            i--;
            if(resVector.get(i).get(0).equals("\n")){
                return false;
            }
            else if(resVector.get(i).get(0).equals("#define")){
                return true;
            }
            else if(!resVector.get(i).get(0).equals(" ")&&!resVector.get(i).get(0).equals("\t")){
                return false;
            }
        }
        return false;
    } 
    public void replacement(Vector<Expression> finalExpressions){
        replace.mainFunc();
        for(int i=0;i<replace.resVector.size();i++){
            Vector<String> r=replace.resVector.get(i);
            Vector<String> v=new Vector<>();
            Vector<String> n=new Vector<>();
            if(r.get(1).equals("MACRO")){
                if(findMacro(replace.resVector, i)){
                    for(int j=0;j<finalExpressions.size();j++){
                        if(r.get(0).equals(finalExpressions.get(j).macro)){
                            v.add(" ");
                            v.add("1");
                            replace.resVector.add(i+1,v);
                            n.add(finalExpressions.get(j).macroValue);
                            n.add("1");
                            replace.resVector.add(i+2,n);
                            break;
                        }
                    }
                }
                else{
                    for(int j=0;j<finalExpressions.size();j++){
                        if(r.get(0).equals(finalExpressions.get(j).macro)){
                            n.add(finalExpressions.get(j).macroValue);
                            replace.resVector.set(i,n);
                            break;
                        }
                    }
                }
            }
        }
        for(int i=0;i<replace.resVector.size();i++){
            replaceString+=replace.resVector.get(i).get(0);
        }
    }
    public boolean finalCompute(){
        int count=0;
        Vector<Expression> finalExpressions;
        getExpress();
        finalExpressions=compute_pd();
        if(finalExpressions.size()<expressVector.size()){
            count=1;
        }
        if(finalExpressions.size()==0){
            computeString+="没有宏要计算\n";
            return false;
        }
        for(int i=0;i<finalExpressions.size();i++){
            if(finalExpressions.get(i).t_f==false){
                continue;
            }
            Vector<Vector<String>> wresVector=finalExpressions.get(i).expression;
            computeString+=finalExpressions.get(i).macro+"：["+finalExpressions.get(i).sExpression+"]\n";
            operator_Precedence=new Operator_Precedence(proRule, Vt, wresVector, true);
            boolean tf=operator_Precedence.check();
            computeString+=operator_Precedence.computeString;
            if(tf==false){
                count=1;
                finalExpressions.get(i).t_f=false;
                while(true){
                    int flag=0;
                    for(int j=0;j<finalExpressions.size();j++){
                        Expression e=finalExpressions.get(j);
                        Vector<Vector<String>> f=e.expression;
                        if(e.t_f==false){
                            continue;
                        }
                        for(int k=0;k<f.size();k++){
                            Vector<String> n=f.get(k);
                            if(n.size()>2&&n.get(2).equals("MACRO")){
                                for(int l=0;l<finalExpressions.size();l++){
                                    if(finalExpressions.get(l).macro.equals(n.get(1))){
                                        if(finalExpressions.get(l).t_f==false){
                                            computeString+=finalExpressions.get(j).macro+"：["+finalExpressions.get(j).sExpression+"]\n无法计算，表达式含有其他错误的宏\n";
                                            e.t_f=false;
                                            flag=1;
                                        }
                                        break;
                                    }
                                }
                                if(e.t_f==false){
                                    break;
                                }
                            }
                        }
                    }
                    if(flag==0){
                        break;
                    }
                }
            }
            else{
                finalExpressions.get(i).macroValue=operator_Precedence.macroValue;
                for(int j=0;j<finalExpressions.size();j++){
                    Expression e=finalExpressions.get(j);
                    Vector<Vector<String>> f=e.expression;
                    if(e.t_f==false){
                        continue;
                    }
                    for(int k=0;k<f.size();k++){
                        Vector<String> n=f.get(k);
                        if(n.size()>2&&n.get(2).equals("MACRO")){
                            if(finalExpressions.get(i).macro.equals(n.get(1))){
                                n.add(1,operator_Precedence.macroValue);
                            }
                        }
                    }
                }
            }
        }
        if(count==1){
            computeString+="\n有宏发生错误，故未替换\n";
            return false;
        }
        else{
            replacement(finalExpressions);
            computeString+="宏替换完成\n";
            return true;
        }
    }
}
