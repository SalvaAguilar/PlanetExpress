package silverhillapps.com.planetexpress.utils;

import java.math.BigDecimal;

/**
 * This class provide the generic methods for checking the validity of strings with numbers
 */
public class DigitUtils {

    /**
     * Method used for assessing whether a String object has a number kind in its value or not.
     * @param text
     * @return
     */
    public static boolean isDoubleNumber(String text){
        boolean out = false;

        if(text != null){
            out = text.matches("\\d+(\\.\\d+)*");
        }

        return out;
    }

    /**
     * This method is used for parsing a truncated double number from a string
     * @param text
     * @return
     */
    public static double getDoubleFromString(String text){

            double out = Double.valueOf(text);

            out = truncateDecima(out, 3);

            return out;
    }

    /**
     * This method truncates a double number to the number of decimals determined by parameter.
     * @param d
     * @param decimals
     * @return
     */
    public static double truncateDecima(double d, int decimals){
        BigDecimal bd =  new BigDecimal(d).setScale(decimals, BigDecimal.ROUND_FLOOR);
        return bd.doubleValue();
    }
}
