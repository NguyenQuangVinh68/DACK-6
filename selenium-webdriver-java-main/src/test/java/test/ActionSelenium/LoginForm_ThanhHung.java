package test.ActionSelenium;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/*
 *   1. Step 1 Truy cập https://thanhhungfutsal.com/
 *   2. Step 2 Đưa chuột đến icon account
 *   3. Step 3 Bấm "Đăng nhập" và nhập vào tất cả các trường
 *   4. Step 4 Bấm nút "Đăng nhập"
 *   5. Step 5 Xác minh đăng nhập thành công
 */

@Test
public class LoginForm_ThanhHung {
    public static void testLogin() {
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            // 1. Step 1 Truy cập https://thanhhungfutsal.com/
            driver.get("https://thanhhungfutsal.com/");
            Thread.sleep(1000);

            // 2. Step 2 Đưa chuột đến icon account
            WebElement buttonAccount = driver
                    .findElement(By.cssSelector(".ega-color--inherit.header-icon.icon-account"));
            Actions action = new Actions(driver);
            action.moveToElement(buttonAccount).perform();

            // 3. Step 3 Bấm "Đăng nhập" và nhập vào tất cả các trường
            WebElement signIn = driver.findElement(By.cssSelector("a[href='/account/login']"));
            signIn.click();

            String emailValue = "truongvoky001@gmail.com";

            WebElement email = driver.findElement(By.cssSelector("#customer_email"));
            email.sendKeys(emailValue);
            Thread.sleep(2000);

            WebElement password = driver.findElement(By.cssSelector("#customer_password"));
            password.sendKeys("123456");
            Thread.sleep(2000);

            // 4. Step 4 Bấm nút "Đăng nhập"
            WebElement loginButton = driver.findElement(By.cssSelector("input[value='Đăng nhập']"));
            loginButton.click();

            // 5. Step 5. Xác minh đăng nhập thành công
            WebElement titleMyAccount = driver.findElement(By.cssSelector(".account-title"));
            assertEquals(titleMyAccount.getText(), "Tài khoản của bạn");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
