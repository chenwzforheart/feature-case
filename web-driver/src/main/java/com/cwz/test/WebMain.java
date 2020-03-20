package com.cwz.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;


/**
 * @author csh9016
 * @date 2020/3/19
 */
public class WebMain {

    public static void main(String[] args) throws InterruptedException {
        String nam = "";
        String pas = "";
        System.setProperty("webdriver.chrome.driver", "D:\\home\\chromedriver_win32_79\\chromedriver_win32_79\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://ui.easeye.com.cn/EventMail/Login");
        driver.manage().window().setSize(new Dimension(1280, 960));
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("#ctl00_ContentPlaceHolder1_txtEmail")).sendKeys(nam);
        driver.findElement(By.cssSelector("#txtPassword")).sendKeys(pas);
        driver.findElement(By.cssSelector("#btnLogin")).click();
        Thread.sleep(5000);

        //driver.get("https://ui.easeye.com.cn/Eventmail/V1/MailReport/SMTPListReportNew.aspx");
        WebElement tab = driver.findElement(By.cssSelector("#mainmenuItem_3 > a"));
        Actions ac = new Actions(driver);
        ac.moveToElement(tab).perform();

        driver.findElement(By.cssSelector("#mainmenuItem_3 > div > div.nav_level2 > ul > li:nth-child(4)")).click();

        //查询具体的邮件状态
        String[] data = new String[]{
                "amanda.niem@adidas.com",
                "batassa.lum@adidas.com",
                "carman.ip@adidas.com",
                "chibi.chan@adidas.com",
                "derek.lee2@adidas.com",
                "emily.leung@adidas.com",
                "james.wong@adidas.com",
                "Jane.Leung@adidas.com",
                "joan.lee@adidas.com",
                "kate.lau@adidas.com",
                "kenny.chan@adidas.com",
                "kenny.chung@adidas.com",
                "leonard.burges@adidas.com",
                "matthew.tam@adidas.com",
                "nelson.chan@adidas.com",
                "philip.mattner@adidas.com",
                "phoebe.shea@adidas.com",
                "prescilla.lam@adidas.com",
                "ronald.ho@adidas.com",
                "sam.hau@adidas.com",
                "sammy.wong@adidas.com",
                "sheryl.amaranto@adidas.com",
                "tiffany.chu@adidas.com",
                "wing.yan@adidas.com",
                "emily.leung@adidas.com",
                "sam.hau@adidas.com",
                "tiffany.chu@adidas.com"
        };

        for (int i = 0; i < data.length; i++) {
            detail(driver, data[i]);
            Thread.sleep(500);
        }

//                driver.quit();
        while (true) {
            Thread.sleep(500);
        }
    }

    public static void detail(WebDriver driver, String email) {
        String js = "document.querySelector(\"#ctl00_ContentPlaceHolder1_txtEmail\").value=\"\";";
        ((ChromeDriver) driver).executeScript(js);
        driver.findElement(By.cssSelector("#ctl00_ContentPlaceHolder1_txtEmail")).sendKeys(email);

        js = "document.querySelector(\"#ctl00_ContentPlaceHolder1_txtStartDate\").value=\"\";";
        ((ChromeDriver) driver).executeScript(js);
        driver.findElement(By.cssSelector("#ctl00_ContentPlaceHolder1_txtStartDate")).sendKeys("2020-03-19");
        js = "document.querySelector(\"#ctl00_ContentPlaceHolder1_txtEndDate\").value=\"\";";
        ((ChromeDriver) driver).executeScript(js);
        driver.findElement(By.cssSelector("#ctl00_ContentPlaceHolder1_txtEndDate")).sendKeys("2020-03-19");
        driver.findElement(By.cssSelector("#ctl00_ContentPlaceHolder1_btnSearch")).click();


        List<WebElement> td = driver.findElements(By.cssSelector("#ctl00_ContentPlaceHolder1_showReportDetail > table > tbody > tr:nth-child(n+2):nth-child(-n+11)"));
        if (td.size() != 0) {
            WebElement val = td.get(0).findElement(By.cssSelector("td:nth-child(6)"));
            System.out.println(email + "--" + val.getText());
        }

    }
}
