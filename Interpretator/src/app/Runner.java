package app;

import gsa.interpretator.Calculator;

import java.util.HashMap;

import javax.security.auth.callback.LanguageCallback;


public class Runner extends Calculator {

  private Runner(String str, HashMap<String, String> hm) {
    super(str, hm);
    //        System.out.println("myInter started");
   }

  public static Runner createmyInterpretator(String str, HashMap<String, String> hm) {
    return new Runner(str, hm);
  }

  public static void main(String[] args) {
    HashMap<String,String> hm = new HashMap<>();
    hm.put("ku123", "(7+abc)");
    hm.put("abc", "7+dcb");
    hm.put("dcb", "5");
          
    String str1 = "(2+((8+10)*5))/(2*(3+2)-5) + (3*19)";
    String str2 = "(2+((8+10)*5))/(2*(3+2)-5) + (3*ku123)";

    Runner i1 = Runner.createmyInterpretator(str1, null);
    Calculator i2 = new Calculator(str2, hm);
    
//    System.out.println("-----> " + i2.toString()+" " + hm);
    System.out.println("-----> Calculated 1 = " + i1.calculate());
    System.out.println("-----> Calculated 2 = " + i2.calculate());
}
  


}
