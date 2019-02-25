package gsa.interpretator;

public abstract class ParserParameters extends keyParameters {
    String resultAnalizing;

    public ParserParameters() {
    }

    public String getResultAnalizing() {
        return resultAnalizing;
    }

    public String trim_result(String istr) {
        String str = "";
        for (int i = 0; istr != null && i < istr.length(); i++) {
            if (istr.charAt(i) != ' ')
                str = str + istr.charAt(i);
        }
        return str;
    }
    public void analyzing(String str) {
        String name_variable, Result, v_str;
        int counter = 0;
        v_str = str;
        Result = trim_result(v_str);

        name_variable = extract_name_variable(v_str);
        while (name_variable != null && !name_variable.isEmpty() && counter++ < getMapParameters().size()) {
            name_variable = extract_name_variable(Result);
            v_str = getMapParameters().get(name_variable);
            Result = substitutionParameters(Result, name_variable);
        }
        resultAnalizing=Result;
//        System.out.println("-----> ParserParameters analyzing String: " + Result + " : " + name_variable);
    }
}
