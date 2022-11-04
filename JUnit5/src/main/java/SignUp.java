import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp {
    public int getSpecialCharacterCount(String s) {
        if (s == null || s.trim().isEmpty()) {
            System.out.println("Incorrect format of string");
            return 0;
        }
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(s);
        // boolean b = m.matches();
        boolean b = m.find();
        if (b)
            return 1;
        else
            return 0;
    }
    public String validateName(String firstName) {
        if("" == firstName) {
            return "Bạn chưa nhập thông tin này";
        }
        if(getSpecialCharacterCount(firstName) == 1) {
            return "Không được chứa ký tự đặc biệt";
        }
        return "OK";
    }
    public static boolean checkEmail(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public String validatePassWord(String password) {
        if(password == "")
            return "Bạn chưa nhập thông tin này";
        if(password.length() < 6)
            return "Mật khẩu có độ dài 6 kí tự";
        return "OK";
    }
    public String validateRePassWord(String password, String rePassword) {
        if(rePassword == "")
            return "Bạn chưa nhập thông tin này";
        if(!Objects.equals(password, rePassword))
            return "Mật khẩu nhập lại không chính xác";
        return "OK";
    }
}
