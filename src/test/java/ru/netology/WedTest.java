package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WedTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        String nameOS = System.getProperty("os.name");
        if (nameOS.toLowerCase().contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
            return;
        }
        if (nameOS.toLowerCase().contains("linux")) {
            System.setProperty("webdriver.chrome.driver", "driver/linux/chromedriver");
            return;
        }
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void webTestOk() {
        driver.findElement(By.cssSelector("[type ='text']")).sendKeys("Вася-Ин-Да-Хаус Пупкин");
        driver.findElement(By.cssSelector("[type ='tel']")).sendKeys("+77777777777");
        driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected,actualMessage.trim());
    }

    @Test
    void webTestFailedName() {
        driver.findElement(By.cssSelector("[type ='text']")).sendKeys("Вася-In-Da-House Пупкин");
        driver.findElement(By.cssSelector("[type ='tel']")).sendKeys("+77777777777");
        driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id = 'name'] [class = 'input__sub']")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected,actualMessage.trim());
    }

    @Test
    void webTestFailedTel() {
        driver.findElement(By.cssSelector("[type ='text']")).sendKeys("Вася-Ин-Да-Хаус Пупкин");
        driver.findElement(By.cssSelector("[type ='tel']")).sendKeys("+9");
        driver.findElement(By.cssSelector("[class = 'checkbox__box']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String actualMessage = driver.findElement(By.cssSelector("[data-test-id = 'phone'] [class = 'input__sub']")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected,actualMessage.trim());
    }

    @Test
    void webTestFailedCheckbox() {
        driver.findElement(By.cssSelector("[type ='text']")).sendKeys("Вася-Ин-Да-Хаус Пупкин");
        driver.findElement(By.cssSelector("[type ='tel']")).sendKeys("+99999999999");
        driver.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector("[class = 'checkbox__text']")).getCssValue("Color");
        String expected = "rgb(255, 92, 92)";
        assertEquals(expected,actual);
    }
}
