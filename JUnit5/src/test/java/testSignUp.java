import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class testSignUp {
    @Test
    public void testSignUp(){
        SignUp signup = new SignUp();
        String result = signup.validateName("");
        assertEquals("Bạn chưa nhập thông tin này" , result);
    }
    @Test
    public void testSignUp1(){
        SignUp signup = new SignUp();
        String result = signup.validateName("Nguyen");
        assertEquals("OK" , result);
    }
    @Test
    public void testSignUp2(){
        SignUp signup = new SignUp();
        String result = signup.validateName("Nguyen@@");
        assertEquals("Không được chứa ký tự đặc biệt" , result);
    }
    @Test
    public void testSignUp3(){
        SignUp signup = new SignUp();
        String result = signup.validateName("Binh@@");
        assertEquals("Không được chứa ký tự đặc biệt" , result);
    }
    @Test
    public void testSignUp4(){
        SignUp signup = new SignUp();
        String result = signup.validateName("Binh");
        assertEquals("OK" , result);
    }
    @Test
    public void testSignUp5(){
        SignUp signup = new SignUp();
        String result = signup.validateName("");
        assertEquals("Bạn chưa nhập thông tin này" , result);
    }
    @Test
    public void testSignUp6() {
        String emailAddress = "domain@gmail.com";
        String regexPattern = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";

//        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        assertTrue(SignUp.checkEmail(emailAddress, regexPattern));
    }
    @Test
    public void testSignUp7() {
        String emailAddress = "domain.com";
        String regexPattern = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";

//        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
//                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        assertFalse(SignUp.checkEmail(emailAddress, regexPattern));
    }
    @Test
    public void testSignUp9(){
        SignUp signup = new SignUp();
        String result = signup.validatePassWord("");
        assertEquals("Bạn chưa nhập thông tin này" , result);
    }
    @Test
    public void testSignUp10(){
        SignUp signup = new SignUp();
        String result = signup.validatePassWord("1234");
        assertEquals("Mật khẩu có độ dài 6 kí tự" , result);
    }
    @Test
    public void testSignUp11(){
        SignUp signup = new SignUp();
        String result = signup.validatePassWord("123456");
        assertEquals("OK" , result);
    }
    @Test
    public void testSignUp12(){
        SignUp signup = new SignUp();
        String result = signup.validateRePassWord("123456", "654321");
        assertEquals("Mật khẩu nhập lại không chính xác" , result);
    }
    @Test
    public void testSignUp13(){
        SignUp signup = new SignUp();
        String result = signup.validateRePassWord("123456", "123456");
        assertEquals("OK" , result);
    }
    @Test
    public void testSignUp14(){
        SignUp signup = new SignUp();
        String result = signup.validateRePassWord("123456", "");
        assertEquals("Bạn chưa nhập thông tin này" , result);
    }
}
