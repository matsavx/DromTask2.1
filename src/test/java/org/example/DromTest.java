package org.example;

import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DromTest {

    public static MainPage mainPage;

    @Test

    public void setup() throws IOException {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

        WebDriver driver = new ChromeDriver();
        mainPage = new MainPage(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("test_page"));

        mainPage.inputData();

        Assert.assertFalse(mainPage.find_unsold(driver.getCurrentUrl()));

        //Иногда на сайте при сортировке с указанием минимального пробега поиск выдает машины без пробега,
        // поэтому тут (строка 34), по очевидным причинам выбрасывается исключение
        Assert.assertFalse(mainPage.find_mileage(driver.getCurrentUrl()));

        mainPage.second_page();

        Assert.assertFalse(mainPage.find_unsold(driver.getCurrentUrl()));
        Assert.assertTrue(mainPage.find_mileage(driver.getCurrentUrl()));

        mainPage.first_page();
        mainPage.year_sort_down();

        Assert.assertTrue(mainPage.find_year(driver.getCurrentUrl()));

        driver.quit();
    }
}
