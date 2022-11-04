package test.ActionSelenium;


import driver.driverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.*;

@Test
public class Sale_ThanhHung {
    public static void testSale() {
        WebDriver driver = driverFactory.getChromeDriver();
        try {
            driver.get("https://thanhhungfutsal.com/");

            WebElement navHotSale = driver.findElement(By.cssSelector("#primary-menu > ul > li.ega-menu__item.menupos-7 > a"));
            navHotSale.click();

            By saleItem = By.cssSelector(".label-sale");

            List<WebElement> saleList = driver.findElements(saleItem);

            for(int i = 0; i < saleList.size(); i++) {
                if (saleList.get(i).getText().equals("")) {
                    assertFalse("Bug sp " + i, saleList.get(i).getText().equals(""));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
