package test.ActionSelenium;

import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@Test
public class Sort_ThanhHung {
    public static void testSort() {
        WebDriver driver = driverFactory.getChromeDriver();

        try {
            // 1. Step 1 Truy cập https://thanhhungfutsal.com/
            driver.get("https://thanhhungfutsal.com/");
            Thread.sleep(1000);

            // 2. Step 2 Bấm vào "Tất cả sản phẩm" trên thanh menu
            WebElement navAllProducts = driver.findElement(By.cssSelector("li.ega-menu__item.menupos-2"));
            navAllProducts.click();
            Thread.sleep(1000);

            // 3. Step 3 Xác minh vào trang tất cả sản phẩm thành công
            WebElement categoryText = driver.findElement(By.cssSelector("li[class='ega-menu__item'] h1"));
            assertEquals(categoryText.getText(), "TẤT CẢ SẢN PHẨM");
            Thread.sleep(3000);

            // 4. Step 4 Kiểm tra bộ lọc theo giá
            WebElement sortByPrice = driver.findElement(By.xpath("//span[contains(text(),'3,000,000₫- 4,000,000₫')]"));
            sortByPrice.click();
            Thread.sleep(3000);

            By priceProduct = By.cssSelector(".price ins");
            List<WebElement> priceProducts = driver.findElements(priceProduct);

            for (int i = 0; i < priceProducts.size(); i++) {
                String priceItem = priceProducts.get(i).getText();
                priceItem = priceItem.replace(priceItem.substring(priceItem.length() - 1), "");
                priceItem = priceItem.replaceAll(",", "");
                int price = Integer.parseInt(priceItem);
                assertTrue(price >= 3000000 && price <= 4000000);
            }

            // 5. Step 5 Kiểm tra bộ lọc theo thương hiệu
            WebElement all = driver.findElement(By.xpath("//span[contains(text(),'Tất cả')]"));
            all.click();
            Thread.sleep(3000);

            WebElement sortByBrand = driver
                    .findElement(By.xpath("//ul[@class='ega-ul ega-m--0']//span[contains(text(),'ADIDAS')]"));
            sortByBrand.click();
            String brand = sortByBrand.getText();
            Thread.sleep(3000);

            By nameProduct = By.cssSelector(".pd-item__wrapper h3");
            List<WebElement> nameProducts = driver.findElements(nameProduct);

            for (int i = 0; i < nameProducts.size(); i++) {
                assertTrue(nameProducts.get(i).getText().toLowerCase().contains(brand.toLowerCase()));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
