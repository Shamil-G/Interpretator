package gsa.interpretator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class keyParameters {

    HashMap<String, String> mapParameters = new HashMap<>();
    List<String> keys = new ArrayList<>();

    public keyParameters() {
        super();
    }

    public HashMap<String, String> getMapParameters() {
        return mapParameters;
    }

    public void setMapParameters(HashMap<String, String> mapVariable) {
        if (mapParameters.size() > 0)
            mapParameters.clear();
//this.mapParameters = (HashMap<String, String>)mapVariable.clone();
        this.mapParameters = mapVariable;
        mapParameters.forEach((p1, p2) -> keys.add(p1));
        resolveParameters();
    }

    public boolean isValueNumber(String value) {
        for (int i = 0; i < value.length(); i++) {
            if ((value.charAt(i) < '0' || value.charAt(i) > '9') && value.charAt(i) != '.')
                return false;
        }
        return true;
    }

    public boolean isVariableSignature(char c1) {
        return (c1 >= 'a' && c1 <= 'z') || (c1 >= 'A' && c1 <= 'Z');
    }

    public boolean outOfVariable(char c1) {
        return c1 == '+' || c1 == '-' || c1 == '*' || c1 == '/' || c1 == '(' || c1 == ')' || c1 == ' ';
    }

    public void resolveParameters() {
        String value;
        String internal_nv;
        for (String key : keys) {
            value = mapParameters.get(key);
            internal_nv = extract_name_variable(value);
            while (internal_nv != null && !internal_nv.isEmpty()) {
                value = substitutionParameters(value, internal_nv);
                mapParameters.put(key, value);
                internal_nv = extract_name_variable(value);
            }
        }
//        System.out.println("------>5. Resolve MAP: "+mapParameters);
    }

    public String substitutionParameters(String expression, String name_variable) {
        String Result = expression;
        if (name_variable != null && !name_variable.isEmpty())
            Result =
                expression.substring(0, expression.indexOf(name_variable)) + getMapParameters().get(name_variable) +
                expression.substring(expression.indexOf(name_variable) + name_variable.length());
        return Result;
    }

    public String extract_name_variable(String expression) {
        char c1;
        boolean variable_found = false;
        String result_var = "";
        for (int i = 0; i < expression.length(); i++) {
            c1 = expression.charAt(i);
            if (!variable_found)
                variable_found = isVariableSignature(c1);

            if (variable_found) {
                if (outOfVariable(c1))
                    return result_var;
                result_var = result_var + c1;
            }
        }
        return result_var;
    }

    @Override
    public String toString() {
        mapParameters.forEach((key, value) -> System.out.println("List variable: " + key + "=" +
                                                                 getMapParameters().get(key)));
        return "";
    }
}
