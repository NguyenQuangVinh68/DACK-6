package test.ActionSelenium;


import driver.driverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/*
 *   1. Step 1 Truy cập https://thanhhungfutsal.com/
 *   2. Step 2 Đưa chuột đến icon account
 *   3. Step 3 Bấm Đăng ký và điền vào tất cả các trường
 *   4. Step 4 Bấm nút Đăng ký
 *   5. Step 5 Xác minh đăng ký thành công
 */
@Test
public class RegisterForm_ThanhHung {
    public static void testRegister() {
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            // 1. Step 1 Truy cập https://thanhhungfutsal.com/
            driver.get("https://thanhhungfutsal.com/");
            Thread.sleep(1000);

            // 2. Step 2 Đưa chuột đến icon account
            WebElement buttonAccount = driver.findElement(By.cssSelector(".ega-color--inherit.header-icon.icon-account"));
            Actions action = new Actions(driver);
            action.moveToElement(buttonAccount).perform();

            // 3. Step 3 Bấm Đăng ký và điền vào tất cả các trường
            WebElement signUp = driver.findElement(By.cssSelector("a[href='/account/register']"));
            signUp.click();

            WebElement firstName = driver.findElement(By.cssSelector("#last_name"));
            firstName.sendKeys("Nguyen");

            WebElement lastName = driver.findElement(By.cssSelector("#first_name"));
            lastName.sendKeys("Binh");

            WebElement email = driver.findElement(By.cssSelector("#email"));
            email.sendKeys("nguyenquangvinhnn031@gmail.com");

            WebElement password = driver.findElement(By.cssSelector("#password"));
            password.sendKeys("123456");

            WebElement repassword = driver.findElement(By.cssSelector("#repassword"));
            repassword.sendKeys("123456");

            // 4. Step 4 Bấm nút Đăng ký
            WebElement buttonSubmitSignUp = driver.findElement(By.cssSelector(".register-action input.ega-btn.ega-btn--outline"));
            buttonSubmitSignUp.click();

            Thread.sleep(1000);

            // 5. Step 5 Xác minh đăng ký thành công
            Alert alert = driver.switchTo().alert();
            String textAlert = alert.getText();
            assertEquals(textAlert, "Đăng ký thành công!");
            alert.accept();



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
