package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant
{
    public static final String URL = "http://api.zenpay.online/api/";
    public static String TOTAL_BALANCE = "";
    public static String MEMBER_ID = "";


    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
