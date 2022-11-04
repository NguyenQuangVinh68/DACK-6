import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class testSignIn {
    @Test
    public void testSignIn() {
        SignIn signin = new SignIn();
        boolean result = signin.checkSignIn("binh", "12345");
        assertFalse(result);
    }
    @Test
    public void testSignIn1() {
        SignIn signin = new SignIn();
        boolean result = signin.checkSignIn("binh@", "123456");
        assertFalse(result);
    }
    @Test
    public void testSignIn2() {
        SignIn signin = new SignIn();
        boolean result = signin.checkSignIn("binh@gmail.com", "12356");
        assertFalse(result);
    }
    @Test
    public void testSignIn3() {
        SignIn signin = new SignIn();
        boolean result = signin.checkSignIn("binh@gmail.com", "123456");
        assertTrue(result);
    }
}
