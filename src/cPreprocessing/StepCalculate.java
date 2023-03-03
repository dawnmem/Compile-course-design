package cPreprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class StepCalculate {
    public String convertString;
    public String resultString;
    public String expressionString;
    ArrayList<String> V = new ArrayList<String>(Arrays.asList(
            ",", "?", ":", "||", "&&", "|", "^", "&", "==", "!=",
            ">", ">=", "<", "<=", "<<", ">>", "+", "-", "*", "/", "%", "~", "!",
            "(", ")", "i"));

    public String getOpe(Vector<Vector<String>> stepVector) {
        for (int i = 0; i < stepVector.size(); i++) {
            Vector<String> s = stepVector.get(i);
            if (V.contains(s.get(0))) {
                return s.get(0);
            }
        }
        return null;
    }

    public String getNumString(Vector<Vector<String>> stepVector, int index) {
        return stepVector.get(index).get(1);
    }

    public String to8_16(String s) {
        String ss = "";
        if (s.length() > 2 && (s.substring(0, 2).equals("0x") || s.substring(0, 2).equals("0X"))) {
            try {
                ss = Integer.toString(Integer.parseInt(s.substring(2, s.length()), 16));
            } catch (Exception e) {
                ss = "";
            }
        } else if (s.length() > 1 && s.charAt(0) == '0' && s.charAt(1) != '.') {
            try {
                ss = Integer.toString(Integer.parseInt(s.substring(1, s.length()), 8));
            } catch (Exception e) {
                ss = "";
            }
        } else {
            ss = s;
        }
        return ss;
    }

    public boolean getStep(Vector<Vector<String>> computeVector) {
        convertString = "";
        resultString = "";
        expressionString = "";
        Vector<Vector<String>> stepVector = new Vector<>(computeVector);
        for (int i = 0; i < stepVector.size(); i++) {
            if (V.contains(stepVector.get(i).get(0))) {
                expressionString += stepVector.get(i).get(0);
            } else {
                expressionString += stepVector.get(i).get(1);
            }
        }
        switch (getOpe(stepVector)) {
            case "i": {
                convertString = stepVector.get(0).get(1);
                String s1 = to8_16(getNumString(stepVector, 0));
                try {
                    Integer.parseInt(s1);
                } catch (Exception e) {
                    try {
                        Double.parseDouble(s1);
                    } catch (Exception f) {
                        resultString += "数值错误，停止计算\n";
                        return false;
                    }
                }
                break;
            }
            case "+": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) + Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    try {
                        Double a = (Double) (Integer.parseInt(s1) + Double.parseDouble(s2));
                        convertString = Double.toString(a);
                    } catch (Exception f) {
                        try {
                            Double a = (Double) (Double.parseDouble(s1) + Integer.parseInt(s2));
                            convertString = Double.toString(a);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1) + Double.parseDouble(s2);
                                convertString = Double.toString(a);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "-": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) - Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    try {
                        Double a = (Double) (Integer.parseInt(s1) - Double.parseDouble(s2));
                        convertString = Double.toString(a);
                    } catch (Exception f) {
                        try {
                            Double a = (Double) (Double.parseDouble(s1) - Integer.parseInt(s2));
                            convertString = Double.toString(a);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1) - Double.parseDouble(s2);
                                convertString = Double.toString(a);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "*": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) * Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    try {
                        Double a = (Double) (Integer.parseInt(s1) * Double.parseDouble(s2));
                        convertString = Double.toString(a);
                    } catch (Exception f) {
                        try {
                            Double a = (Double) (Double.parseDouble(s1) * Integer.parseInt(s2));
                            convertString = Double.toString(a);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1) * Double.parseDouble(s2);
                                convertString = Double.toString(a);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "/": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) / Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    try {
                        Double a = (Double) (Integer.parseInt(s1) / Double.parseDouble(s2));
                        convertString = Double.toString(a);
                    } catch (Exception f) {
                        try {
                            Double a = (Double) (Double.parseDouble(s1) / Integer.parseInt(s2));
                            convertString = Double.toString(a);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1) / Double.parseDouble(s2);
                                convertString = Double.toString(a);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "%": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) % Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    try {
                        Double a = (Double) (Integer.parseInt(s1) % Double.parseDouble(s2));
                        convertString = Double.toString(a);
                    } catch (Exception f) {
                        try {
                            Double a = (Double) (Double.parseDouble(s1) % Integer.parseInt(s2));
                            convertString = Double.toString(a);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1) % Double.parseDouble(s2);
                                convertString = Double.toString(a);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "(": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 1));
                try {
                    Integer a = Integer.parseInt(s1);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        convertString = Double.toString(a);
                    } catch (Exception f) {
                        resultString += "发生错误，停止计算\n";
                        return false;
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "!": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 1));
                try {
                    Integer a = Integer.parseInt(s1);
                    int count=0;
                    if(a!=0){
                        count=1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        int count=0;
                        if(a!=0){
                            count=1;
                        }
                        convertString = Double.toString(count);
                    } catch (Exception f) {
                        resultString += "发生错误，停止计算\n";
                        return false;
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "~": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 1));
                try {
                    Integer a = ~Integer.parseInt(s1);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    resultString += "发生错误，停止计算\n";
                    return false;
                }
                resultString += convertString + "\n";
                break;
            }
            case ",": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Double b = Double.parseDouble(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Double b = Double.parseDouble(s2);
                        convertString = Double.toString(a);
                    } catch (Exception f) {
                        resultString += "发生错误，停止计算\n";
                        return false;
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "&&": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Integer b = Integer.parseInt(s2);
                    int count = 0;
                    if (a != 0 && b != 0) {
                        count = 1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Integer b = Integer.parseInt(s2);
                        int count = 0;
                        if (a != 0 && b != 0) {
                            count = 1;
                        }
                        convertString = Integer.toString(count);
                    } catch (Exception f) {
                        try {
                            Integer a = Integer.parseInt(s1);
                            Double b = Double.parseDouble(s2);
                            int count = 0;
                            if (a != 0 && b != 0) {
                                count = 1;
                            }
                            convertString = Integer.toString(count);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1);
                                Double b = Double.parseDouble(s2);
                                int count = 0;
                                if (a != 0 && b != 0) {
                                    count = 1;
                                }
                                convertString = Integer.toString(count);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "||": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Integer b = Integer.parseInt(s2);
                    int count = 0;
                    if (a != 0 || b != 0) {
                        count = 1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Integer b = Integer.parseInt(s2);
                        int count = 0;
                        if (a != 0 || b != 0) {
                            count = 1;
                        }
                        convertString = Integer.toString(count);
                    } catch (Exception f) {
                        try {
                            Integer a = Integer.parseInt(s1);
                            Double b = Double.parseDouble(s2);
                            int count = 0;
                            if (a != 0 || b != 0) {
                                count = 1;
                            }
                            convertString = Integer.toString(count);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1);
                                Double b = Double.parseDouble(s2);
                                int count = 0;
                                if (a != 0 || b != 0) {
                                    count = 1;
                                }
                                convertString = Integer.toString(count);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "==": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Integer b = Integer.parseInt(s2);
                    int count = 0;
                    if (a-b==0 ) {
                        count = 1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Integer b = Integer.parseInt(s2);
                        int count = 0;
                        if (a-b==0) {
                            count = 1;
                        }
                        convertString = Integer.toString(count);
                    } catch (Exception f) {
                        try {
                            Integer a = Integer.parseInt(s1);
                            Double b = Double.parseDouble(s2);
                            int count = 0;
                            if (a-b==0) {
                                count = 1;
                            }
                            convertString = Integer.toString(count);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1);
                                Double b = Double.parseDouble(s2);
                                int count = 0;
                                if (a-b==0) {
                                    count = 1;
                                }
                                convertString = Integer.toString(count);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "!=": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Integer b = Integer.parseInt(s2);
                    int count = 0;
                    if (a-b!=0 ) {
                        count = 1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Integer b = Integer.parseInt(s2);
                        int count = 0;
                        if (a-b!=0) {
                            count = 1;
                        }
                        convertString = Integer.toString(count);
                    } catch (Exception f) {
                        try {
                            Integer a = Integer.parseInt(s1);
                            Double b = Double.parseDouble(s2);
                            int count = 0;
                            if (a-b!=0) {
                                count = 1;
                            }
                            convertString = Integer.toString(count);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1);
                                Double b = Double.parseDouble(s2);
                                int count = 0;
                                if (a-b!=0) {
                                    count = 1;
                                }
                                convertString = Integer.toString(count);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case ">": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Integer b = Integer.parseInt(s2);
                    int count = 0;
                    if (a-b>0 ) {
                        count = 1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Integer b = Integer.parseInt(s2);
                        int count = 0;
                        if (a-b>0) {
                            count = 1;
                        }
                        convertString = Integer.toString(count);
                    } catch (Exception f) {
                        try {
                            Integer a = Integer.parseInt(s1);
                            Double b = Double.parseDouble(s2);
                            int count = 0;
                            if (a-b>0) {
                                count = 1;
                            }
                            convertString = Integer.toString(count);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1);
                                Double b = Double.parseDouble(s2);
                                int count = 0;
                                if (a-b>0) {
                                    count = 1;
                                }
                                convertString = Integer.toString(count);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case ">=": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Integer b = Integer.parseInt(s2);
                    int count = 0;
                    if (a-b>=0 ) {
                        count = 1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Integer b = Integer.parseInt(s2);
                        int count = 0;
                        if (a-b>=0) {
                            count = 1;
                        }
                        convertString = Integer.toString(count);
                    } catch (Exception f) {
                        try {
                            Integer a = Integer.parseInt(s1);
                            Double b = Double.parseDouble(s2);
                            int count = 0;
                            if (a-b>=0) {
                                count = 1;
                            }
                            convertString = Integer.toString(count);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1);
                                Double b = Double.parseDouble(s2);
                                int count = 0;
                                if (a-b>=0) {
                                    count = 1;
                                }
                                convertString = Integer.toString(count);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "<": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Integer b = Integer.parseInt(s2);
                    int count = 0;
                    if (a-b<0 ) {
                        count = 1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Integer b = Integer.parseInt(s2);
                        int count = 0;
                        if (a-b<0) {
                            count = 1;
                        }
                        convertString = Integer.toString(count);
                    } catch (Exception f) {
                        try {
                            Integer a = Integer.parseInt(s1);
                            Double b = Double.parseDouble(s2);
                            int count = 0;
                            if (a-b<0) {
                                count = 1;
                            }
                            convertString = Integer.toString(count);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1);
                                Double b = Double.parseDouble(s2);
                                int count = 0;
                                if (a-b<0) {
                                    count = 1;
                                }
                                convertString = Integer.toString(count);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "<=": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1);
                    Integer b = Integer.parseInt(s2);
                    int count = 0;
                    if (a-b<=0 ) {
                        count = 1;
                    }
                    convertString = Integer.toString(count);
                } catch (Exception e) {
                    try {
                        Double a = Double.parseDouble(s1);
                        Integer b = Integer.parseInt(s2);
                        int count = 0;
                        if (a-b<=0) {
                            count = 1;
                        }
                        convertString = Integer.toString(count);
                    } catch (Exception f) {
                        try {
                            Integer a = Integer.parseInt(s1);
                            Double b = Double.parseDouble(s2);
                            int count = 0;
                            if (a-b<=0) {
                                count = 1;
                            }
                            convertString = Integer.toString(count);
                        } catch (Exception g) {
                            try {
                                Double a = Double.parseDouble(s1);
                                Double b = Double.parseDouble(s2);
                                int count = 0;
                                if (a-b<=0) {
                                    count = 1;
                                }
                                convertString = Integer.toString(count);
                            } catch (Exception h) {
                                resultString += "发生错误，停止计算\n";
                                return false;
                            }
                        }
                    }
                }
                resultString += convertString + "\n";
                break;
            }
            case "<<": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) << Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    resultString += "发生错误，停止计算\n";
                    return false;
                }
                resultString += convertString + "\n";
                break;
            }
            case ">>": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) >> Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    resultString += "发生错误，停止计算\n";
                    return false;
                }
                resultString += convertString + "\n";
                break;
            }
            case "&": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) & Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    resultString += "发生错误，停止计算\n";
                    return false;
                }
                resultString += convertString + "\n";
                break;
            }
            case "|": {
                resultString += "正在计算：" + expressionString + "->";
                String s1 = to8_16(getNumString(stepVector, 0));
                String s2 = to8_16(getNumString(stepVector, 2));
                try {
                    Integer a = Integer.parseInt(s1) | Integer.parseInt(s2);
                    convertString = Integer.toString(a);
                } catch (Exception e) {
                    resultString += "发生错误，停止计算\n";
                    return false;
                }
                resultString += convertString + "\n";
                break;
            }
            case "?":{
                resultString += "正在计算：" + expressionString + "->";
                if(stepVector.size()==4){
                    String s1 = to8_16(getNumString(stepVector, 0));
                    String s2 = to8_16(getNumString(stepVector, 3));
                    try {
                        Integer a = Integer.parseInt(s1);
                        Integer b = Integer.parseInt(s2);
                        if(a!=0){
                            convertString = Integer.toString(a);
                        }
                        else{
                            convertString = Integer.toString(b);
                        }
                    } catch (Exception e) {
                        try {
                            Double a = Double.parseDouble(s2);
                            Integer b = Integer.parseInt(s2);
                            if(a!=0){
                                convertString = Double.toString(a);
                            }
                            else{
                                convertString = Integer.toString(b);
                            }
                        } catch (Exception f) {
                            try {
                                Integer a = Integer.parseInt(s1);
                                Double b = Double.parseDouble(s2);
                                if(a!=0){
                                    convertString = Integer.toString(a);
                                }
                                else{
                                    convertString = Double.toString(b);
                                }
                            } catch (Exception g) {
                                try {
                                    Double a = Double.parseDouble(s2);
                                    Double b = Double.parseDouble(s2);
                                    if(a!=0){
                                        convertString = Double.toString(a);
                                    }
                                    else{
                                        convertString = Double.toString(b);
                                    }
                                } catch (Exception h) {
                                    resultString += "发生错误，停止计算\n";
                                    return false;
                                }
                            }
                        }
                    }
                }
                if(stepVector.size()==5){
                    String s1 = to8_16(getNumString(stepVector, 0));
                    String s2 = to8_16(getNumString(stepVector, 2));
                    String s3 = to8_16(getNumString(stepVector, 4));
                    try{
                        Double a = Double.parseDouble(s1);
                        try {
                            Integer c = Integer.parseInt(s2);
                            Integer d = Integer.parseInt(s3);
                            if(a!=0){
                                convertString = Integer.toString(c);
                            }
                            else{
                                convertString = Integer.toString(d);
                            }
                        } catch (Exception e) {
                            try {
                                Double c = Double.parseDouble(s2);
                                Integer d = Integer.parseInt(s3);
                                if(a!=0){
                                    convertString = Double.toString(c);
                                }
                                else{
                                    convertString = Integer.toString(d);
                                }
                            } catch (Exception f) {
                                try {
                                    Integer c = Integer.parseInt(s1);
                                    Double d = Double.parseDouble(s2);
                                    if(a!=0){
                                        convertString = Integer.toString(c);
                                    }
                                    else{
                                        convertString = Double.toString(d);
                                    }
                                } catch (Exception g) {
                                    try {
                                        Double c = Double.parseDouble(s2);
                                        Double d = Double.parseDouble(s3);
                                        if(a!=0){
                                            convertString = Double.toString(c);
                                        }
                                        else{
                                            convertString = Double.toString(d);
                                        }
                                    } catch (Exception h) {
                                        resultString += "发生错误，停止计算\n";
                                        return false;
                                    }
                                }
                            }
                        }
                    }catch(Exception e){
                        resultString += "发生错误，停止计算\n";
                        return false;
                    }
                }
                resultString += convertString + "\n";
                break;
            }
        }
        return true;
    }
}
