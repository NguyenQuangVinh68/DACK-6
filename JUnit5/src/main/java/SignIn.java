import java.util.regex.Pattern;

public class SignIn {
    public static boolean checkEmail(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public boolean validatePassWord(String password) {
        if(password == "")
            return false;
        if(password.length() < 6)
            return false;
        return true;
    }
    public boolean checkSignIn(String email, String password) {
        boolean checkMail = checkEmail(email, "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}");
        boolean checkPass = validatePassWord(password);
        if(checkMail && checkPass) {
            return true;
        }
        return false;
    }
}
