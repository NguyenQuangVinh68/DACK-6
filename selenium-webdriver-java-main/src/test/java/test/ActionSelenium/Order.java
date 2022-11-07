package test.ActionSelenium;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
public class Order {
    @Test
    public static void checkOrder(){
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            driver.get("https://thanhhungfutsal.com/");
            Thread.sleep(2000);

//            1 click chọn 1 sản phẩm trong home
            driver.findElement(By.cssSelector("#ega-fashion > div.mustbuy > div > div.mustbuy__product.ega-row > div:nth-child(3) > a")).click();
            Thread.sleep(2000);

//            2 click add to cart
            driver.findElement(By.cssSelector("#addcart-btn")).click();
            Thread.sleep(2000);

//            3 click view cart
            driver.findElement(By.cssSelector("#cart-popup > div > div > div > div.pd-cart__action > a")).click();
            Thread.sleep(3000);

//            4 click thanh toán
            driver.findElement(By.cssSelector("#checkout")).click();
            Thread.sleep(3000);

//            5 điền thông tin khách hàng mua hàng
            String fullname = "Nguyễn Quang Vịnh";
            String email = "truongvoky00123@gmail.com";
            String phone = "08173210298";
            String address = "Trường Chinh, Hồ Chí Minh";
            driver.findElement(By.cssSelector("#billing_address_full_name")).sendKeys(fullname);
            driver.findElement(By.cssSelector("#checkout_user_email")).sendKeys(email);
            driver.findElement(By.cssSelector("#billing_address_phone")).sendKeys(phone);
            driver.findElement(By.cssSelector("#billing_address_address1")).sendKeys(address);
            Thread.sleep(3000);

            Select tinh = new Select(driver.findElement(By.id("customer_shipping_province")));
            tinh.selectByVisibleText("Hồ Chí Minh");
            Thread.sleep(1000);
            Select huyen = new Select(driver.findElement(By.id("customer_shipping_district")));
            huyen.selectByVisibleText("Quận 12");
            Thread.sleep(1000);
            Select ap =  new Select(driver.findElement(By.id("customer_shipping_ward")));
            ap.selectByVisibleText("Phường Hiệp Thành");
            Thread.sleep(3000);

//            6 click tiếp tục thanh toán
            driver.findElement(By.cssSelector("#form_next_step > button")).click();
            Thread.sleep(2000);

//            7 check xem có thêm tiền phí vận chuyển chưa
            String total = driver.findElement(By.cssSelector("body > div:nth-child(7) > div > div.sidebar > div > div > div > div.order-summary-section.order-summary-section-total-lines.payment-lines > table > tbody > tr.total-line.total-line-subtotal > td.total-line-price > span")).getText();
            String grandTatol = driver.findElement(By.cssSelector("body > div:nth-child(7) > div > div.sidebar > div > div > div > div.order-summary-section.order-summary-section-total-lines.payment-lines > table > tfoot > tr > td.total-line-name.payment-due > span.payment-due-price")).getText();
            String ship = driver.findElement(By.cssSelector("body > div:nth-child(7) > div > div.sidebar > div > div > div > div.order-summary-section.order-summary-section-total-lines.payment-lines > table > tbody > tr.total-line.total-line-shipping > td.total-line-price > span")).getText();
            if(total.equals(grandTatol)){
                System.out.println("số tiền chưa được  cộng thêm phí vận chuyển");
                System.out.println("số tiền sản phẩm " + total);
                System.out.println("số tiền tổng cộng cả phí vận chuyển "+grandTatol);
                System.out.println("tiền phí vận chuyển là "+ ship);
                Assert.assertEquals(total ,grandTatol);
            }else{
                System.out.println("đã cập nhật tiền ship");
                System.out.println("số tiền sản phẩm " + total);
                System.out.println("số tiền tổng cộng cả phí vận chuyển "+grandTatol);
                System.out.println("tiền phí vận chuyển là "+ ship);
            }
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        //7. Quit browser session
        driver.quit();
    }

}
