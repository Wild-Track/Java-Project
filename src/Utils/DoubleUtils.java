package Utils;

import java.text.NumberFormat;

public class DoubleUtils {
  public static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
  
  public static NumberFormat percentageFormat = NumberFormat.getPercentInstance();
  
  public static String toCurrency(double prix) {
    return currencyFormat.format(prix);
  }
  
  public static String toPercentage(double reduction) {
    return percentageFormat.format(reduction);
  }
  
  public static boolean isParsable(String str) {
    if (str.isEmpty())
      return false; 
    if (str.charAt(str.length() - 1) == '.')
      return false; 
    if (str.charAt(0) == '-') {
      if (str.length() == 1)
        return false; 
      return withDecimalsParsing(str, 1);
    } 
    return withDecimalsParsing(str, 0);
  }
  
  private static boolean withDecimalsParsing(String str, int beginIdx) {
    int decimalPoints = 0;
    for (int i = beginIdx; i < str.length(); i++) {
      boolean isDecimalPoint = (str.charAt(i) == '.');
      if (isDecimalPoint)
        decimalPoints++; 
      if (decimalPoints > 1)
        return false; 
      if (!isDecimalPoint && !Character.isDigit(str.charAt(i)))
        return false; 
    } 
    return true;
  }
}
