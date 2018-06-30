/**
 * Created by thanos-imac on 30/6/18.
 */

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class screenshotFailedTC {

    private static WebDriver driver;

    @Test
    public void openBrowser() throws Exception {

        System.setProperty("webdriver.chrome.driver", "/IdeaProjects/takescreenshotapp/src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.linkedin.com");

        //TC01 verify home page
        driver.findElement(By.xpath("//*[@id='regForm']"));
        assertTrue(isElementPresent(By.xpath(("//*[@id='regForm']/h2"))));
        driver.findElement(By.xpath("//*[@id='layout-main']/div/div[1]/div/form/a")).click();

        //TC02 correct Xpath with false assertion
        assertFalse(isElementPresent(By.xpath(("//*[@id='app__container']/div[2]/header"))));

    }

    @AfterMethod
    public void screenShot(ITestResult result) {
        //using ITestResult.FAILURE is equals to result.getStatus then it enter into if condition
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                // To create reference of TakesScreenshot
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                // Call method to capture screenshot
                File src = screenshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(src, new File("/IdeaProjects/takescreenshotapp/src/main/resources" + result.getName() + ".png"));
                System.out.println("Successfully captured a screenshot");
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
        driver.quit();
    }
    public Boolean isElementPresent(By by) {
        if (driver.findElements(by).size() > 0)
            return true;
        else
            return false;
    }
}
