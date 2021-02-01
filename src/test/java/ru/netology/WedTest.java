package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
            System.setProperty("webdriver.chrome.driver", "driver/linux/chromedriver.exe");
            return;
        }
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void webTest() {
        driver.get("http://localhost:9999");
        driver.findElement(By.name("name")).sendKeys("Ваня");
        driver.findElement(By.name("phone")).sendKeys("+77777777777");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualMessage = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",actualMessage.trim());
    }
}
