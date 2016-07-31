package com.careermate.CORE_LIBS.HELPER;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by TuAnh on 6/28/2016.
 */
public class MathHelper {
    public final static Double WRONG_FUNCTION_VALUE= Double.valueOf(Integer.MAX_VALUE);
    public final static Double DIV_0_VALUE= Double.valueOf(Integer.MAX_VALUE)+1;
    public final static Double WRONG_WITH_GIAI_THUA= Double.valueOf(Integer.MAX_VALUE)+1;
    public final static double LIMIT_EE=0.000001;

    public static double LIMIT_EE_Round(double xx){
        double x=xx;
        if(Math.abs(Math.ceil(x)-xx)<LIMIT_EE){
            return Math.ceil(x);
        }
        if(Math.abs(Math.floor(x)-xx)<LIMIT_EE){
            return Math.floor(x);
        }
        return xx;
    }
    public static boolean isNumber(String st) {
        if ((st.charAt(st.length()-1) < '0') || (st.charAt(st.length()-1) > '9')) return false;
        return true;
    }
    public static boolean isNumber(char st) {
        if ((st < '0') || (st > '9')) return false;
        return true;
    }

    public static int Priority(String st) {
        if ((st.charAt(0) == '+') || (st.charAt(0) == '-')) return 1;
        if ((st.charAt(0) == 'x') || (st.charAt(0) == '/') || (st.charAt(0) == '%')) return 2;
        if ((st.charAt(0) == '^')) return 3;
        if ((st.charAt(0) == '!')) return 4;
        return 0;
    }

    public static double Giaithua(double x) {
        if(Math.round(x)==x){
            double r = 1;
            for (int i = 1; i <= x; i++) r *= i;
            return r;
        }else{
            double r=1;
            r=Math.sqrt(2*x*Math.PI)*Math.pow(x/Math.E,x);
            return r;
        }
    }

    public static double getResult(List<String> list) {
        double a, b;
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < list.size(); i++) {
            if (isNumber(list.get(i))) {
                stack.push(list.get(i));
                continue;
            }
            switch (list.get(i).charAt(0)) {
                case '+':
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    a = Double.parseDouble(stack.pop());
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    b = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(a + b));
                    break;
                case '-':
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    a = Double.parseDouble(stack.pop());
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    b = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(b - a));
                    break;
                case 'x':
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    a = Double.parseDouble(stack.pop());
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    b = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(a * b));
                    break;
                case '/':
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    a = Double.parseDouble(stack.pop());
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    b = Double.parseDouble(stack.pop());

                    if(a==0) return DIV_0_VALUE;
                    stack.push(String.valueOf(b / a));
                    break;
                case '%':
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    a = Double.parseDouble(stack.pop());
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    b = Double.parseDouble(stack.pop());

                    if(a==0) return DIV_0_VALUE;
                    stack.push(String.valueOf(b % a));
                    break;
                case '!':
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    a = Double.parseDouble(stack.pop());
                    if(a<0) return WRONG_FUNCTION_VALUE;
                    stack.push(String.valueOf(Giaithua(a)));
                    break;
                case '^':
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    a = Double.parseDouble(stack.pop());
                    if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
                    b = Double.parseDouble(stack.pop());
                    stack.push(String.valueOf(Math.pow(b, a)));
                    break;
            }
        }
        if(stack.isEmpty()) return WRONG_FUNCTION_VALUE;
        String result= stack.pop();
        if(!stack.isEmpty()) return WRONG_FUNCTION_VALUE;
        return Double.parseDouble(result);
    }
    public static boolean checkGiaiThua(String st) {
        boolean ok = false;
        for (int i = 0; i < st.length()-1; i++) {
            if (st.charAt(i) == '!') {
                if(isNumber(st.charAt(i+1))) return false;
                if(st.charAt(i+1)=='(') return false;
            }
        }
        return true;
    }
    public static boolean checkSymbols(String st) {
        boolean ok = false;
        // + - x / % ^ !
        String[] invalid={
                "+x","+/","+%","+^","+!",
                "-x","-/","-%","-^","-!",
                "//","/x","/+","/-","/!","/%","/^",
                "x/","xx","x+","x-","x!","x%","x^",
                "%/","%x","%+","%-","%!","%%","%^",
                "^/","^x","^+","^-","^!","^%","^^",
        };
        for(int i=0;i<invalid.length;i++)
            if(st.contains(invalid[i])) return false;
        return true;
    }
    public static boolean checkDauNgoac(String st) {
        boolean ok = false;
        int c = 0;
        for (int i = 0; i < st.length(); i++) {
            if (st.charAt(i) == ')') {
                if (c <= 0) return false;
                c--;
                continue;
            }
            if (st.charAt(i) == '(') {
                c++;
                continue;
            }
        }
        return (c == 0);
    }
    public static String xulisoAm(String st){
        String res="";
        for(int i=0;i<st.length();i++){
            if (i==0){
                if ((st.charAt(i)=='-')||(st.charAt(i)=='+')) {
                    res+="0"+st.charAt(i);
                }else{
                    res+=st.charAt(i);
                }
            }else{
                if(((st.charAt(i)=='-')||(st.charAt(i)=='+'))){
                    if(st.charAt(i-1)=='('){
                        res+="0"+st.charAt(i);
                    }else{
                        res+=st.charAt(i);
                    }
                }else{
                    res+=st.charAt(i);
                }
            }
        }
        return res;
    }
    public static String validString(String st){
        boolean ok=true;
        String res=st;
        while(ok){
            ok=false;
            if(res.indexOf("--")>=0){
                res=res.replace("--","+");ok=true;
            }
            if(res.indexOf("++")>=0){
                res=res.replace("++","+");ok=true;
            }
            if(res.indexOf("+-")>=0){
                res=res.replace("+-","-");ok=true;
            }
            if(res.indexOf("-+")>=0){
                res=res.replace("-+","-");ok=true;
            }
        }
        return res;
    }
    public static List<String> getListThanhPhan(String st) {
        st=validString(st);///cat bo dau - + thua ra
        st=xulisoAm(st);
        List<String> list = new ArrayList<>();
        boolean so = false;
        st += ".";
        String sSo = "";
        for (int i = 0; i < st.length(); i++) {
            if (isNumber(st.charAt(i))) {
                if (!so) {
                    sSo = String.valueOf(st.charAt(i));
                    so = true;
                } else {
                    sSo += String.valueOf(st.charAt(i));
                }
            } else {
                if (so) {
                    list.add(sSo);
                    so = false;
                }
                if (st.charAt(i) == '.') break;
                list.add(String.valueOf(st.charAt(i)));

            }
        }
        return list;
    }

    public static List<String> getHauTo(List<String> list) {
        List<String> res = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < list.size(); i++) {
            if (isNumber(list.get(i))) {
                res.add(list.get(i));
                continue;
            }
            if (list.get(i).charAt(0) == '(') {
                stack.push(list.get(i));
                continue;
            }

            if (list.get(i).charAt(0) == ')') {
                String tmp = stack.pop();
                while (tmp.charAt(0) != '(') {
                    res.add(tmp);
                    tmp = stack.pop();
                }
                continue;
            }

            if (!stack.isEmpty()) {
                while (!stack.isEmpty() && (Priority(stack.peek()) >= Priority(list.get(i)))) {
                    res.add(stack.pop());
                }
            }
            stack.push(list.get(i));
        }
        while (!stack.isEmpty()) res.add(stack.pop());
        return res;
    }
    public static double getResultFromString(String st){
        if(!checkDauNgoac(st)) return WRONG_FUNCTION_VALUE;
        if(!checkGiaiThua(st)) return WRONG_FUNCTION_VALUE;
        if(!checkSymbols(st)) return WRONG_FUNCTION_VALUE;
        return getResult(getHauTo(getListThanhPhan(st)));
    }
}
