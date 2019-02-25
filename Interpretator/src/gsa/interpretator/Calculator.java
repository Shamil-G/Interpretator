package gsa.interpretator;

import java.util.HashMap;

import javax.xml.transform.Result;

public class Calculator extends ParserParameters {

    protected Calculator() {
        super();
    }

    public Calculator(String str, HashMap<String, String> hm) {
        if (hm != null) {
            setMapParameters(hm);
        }
        analyzing(str);
    }

    private boolean isBinaryOperation(char c1) {
        return c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/';
    }

    private boolean is_lexem_start(String expression, int first_pos) {
        return expression.charAt(first_pos) == '(';
    }

    private boolean is_lexem_end(String expression, int last_pos) {
        return expression.charAt(last_pos) == ')';
    }

    public String calculate() {
        calculate_params();
        return calculateString(resultAnalizing);
    }

    private void calculate_params() {
        String value_param;
        for (String key : keys) {
            value_param = mapParameters.get(key);
            value_param = calculateString(value_param);
            mapParameters.put(key, value_param);
        }
    }

    private String calculateString(String Result) {
        String lexem = "";
        do {
            lexem = lexem_extract(Result);
            if (lexem != null && lexem.isEmpty())
                break;
            Result =
                Result.substring(0, Result.indexOf(lexem) - 1) + calc_simple_operations(lexem) +
                Result.substring(Result.indexOf(lexem) + 1 + lexem.length());
        } while (Result.indexOf('(') != -1);
        return calc_simple_operations(Result);
    }

    private String lexem_extract(String expression) {
        for (int last_pos = 0; last_pos < expression.length(); last_pos++) {
            if (is_lexem_end(expression, last_pos)) {
                for (int first_pos = last_pos; first_pos >= 0; first_pos--) {
                    if (is_lexem_start(expression, first_pos)) {
                        return expression.substring(first_pos + 1, last_pos);
                    }
                }
            }
        }
        return "";
    }

    private String calc_simple_operations(String lexem) {
        boolean calculating = false;
        String expr = "";
        if (isValueNumber(lexem))
            return lexem;

        for (int i = 0; i < lexem.length(); i++) {
            if (i == (lexem.length() - 1)) { //last symbol
                return calc_binary_operation(expr + lexem.charAt(i));
            }
            if (isBinaryOperation(lexem.charAt(i))) {
                if (calculating) {
                    expr = calc_binary_operation(expr);
                }
                calculating = true;
            }
            expr = expr + lexem.charAt(i);
        }
        return expr;
    }

    public String calc_binary_operation(String str) {
        String sv1, sv2;
        char op;
        double val1, val2, res = 0;
        str.trim();
        for (int i = 0; i < str.length(); i++) {
            op = str.charAt(i);
            switch (op) {
            case '+':
            case '-':
            case '/':
            case '*':
                sv1 = str.substring(0, i).trim();
                sv2 = str.substring(i + 1, str.length()).trim();
                val1 = Double.parseDouble(sv1);
                if (sv2 != null) {
                    val2 = Double.parseDouble(sv2);
                } else
                    val2 = 0;
                switch (op) {
                case '+':
                    res = val1 + val2;
                    break;
                case '-':
                    res = val1 - val2;
                    break;
                case '/':
                    if (val2 != 0)
                        res = val1 / val2;
                    else
                        res = 0;
                    break;
                case '*':
                    res = val1 * val2;
                    break;
                }
            }
        }
        return Double.toString(res);
    }


}
