package com.isaactai.selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

/**
 * @author tisaac
 */
public class SampleTest {

    // 	1.	Opens Google (http://www.google.com/) in a Chrome browser.
    //	2.	Finds the search box (<input name="q">).
    //	3.	Enters “ChromeDriver” into the search box.
    //	4.	Submits the search query.
    //	5.	Waits for 5 seconds (so the user can see the results).
    //	6.	Closes the browser.
    @Test
    public void testGoogleSearch() throws InterruptedException {
        // Direct initialization, no need for System.setProperty.
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.google.com/");
        Thread.sleep(5000);

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();

        Thread.sleep(5000);
        driver.quit();
    }

}
