package manage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBase {
    protected WebDriver driver = new ChromeDriver();

    public TestBase() {
    }

    @BeforeClass
    public void setUp() {
        driver.navigate().to("https://www.google.com/");
        driver.manage().window().maximize();
    }

    public void switchToMainWindow() {
        String mainWindowHandle = driver.getWindowHandle();
        driver.switchTo().window(mainWindowHandle);
    }

    public void switchToMyWindow() {
        String myWindowHandle = driver.getWindowHandle();
        driver.switchTo().window(myWindowHandle);
    }

    public void signInAccount() {
        driver.findElement(By.id("gb_70")).click();
        driver.findElement(By.name("identifier")).sendKeys("juliatest2510@gmail.com");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        WebElement password = driver.findElement(By.xpath("//*[@id='password']/div[1]/div/div[1]/input"));
        wait.until(ExpectedConditions.elementToBeClickable(password));
        password.sendKeys("qwerty2019");
        driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();
    }

    public void goToGmail() {
        driver.findElement(By.xpath("//a[contains(text(),'Gmail')]")).click();
    }

    public void goToDraftFolder() {
        driver.findElement(By.xpath("//div[@class='T-I J-J5-Ji T-I-KE L3']")).click();
    }

    public void createDraft() {
        switchToMyWindow();
        WebElement email = driver.findElement(By.xpath("//textarea[@class='vO']"));
        email.sendKeys("qa@gmail.com");
        WebElement text = driver.findElement(By.className("aoT"));
        String draftTopic = "New Message";
        text.sendKeys(draftTopic);
        driver.findElement(By.xpath("//img[@class='Ha']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        switchToMainWindow();
        try {
            WebElement ell = driver.findElement(By.xpath("//a[@href='https://mail.google.com/mail/u/0/?ogbl#drafts']"));
            ell.click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            WebElement ell = driver.findElement(By.xpath("//a[@href='https://mail.google.com/mail/u/0/?ogbl#drafts']"));
            ell.click();
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        validateTopicInDraft(draftTopic);
        System.out.println("The draft was created");
    }

    private void validateTopicInDraft(String expectedToopic) {
        List<WebElement> list = driver.findElements(By.cssSelector("[jsmodel='nXDxbd']"));
        list.get(0).click();
        switchToMyWindow();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        //getting actual draft header - expected to be equal to provided topic
        WebElement draftHeader = driver.findElement(By.xpath("//div[@class='aYF']"));
        String topic = draftHeader.getText();
        System.out.println("ExpectedToopic: " + expectedToopic);
        System.out.println("Topic: " + topic);
        Assert.assertEquals(expectedToopic, topic);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("[aria-label='Save & close']")).click();
        switchToMainWindow();
    }

    public void updateDraft() {
        List<WebElement> list = driver.findElements(By.cssSelector("[jsmodel='nXDxbd']"));
        list.get(0).click();
        switchToMyWindow();
        WebElement input = driver.findElement(By.className("aoT"));
        input.click();
        input.sendKeys(Keys.CONTROL + "a");
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        input.sendKeys(Keys.DELETE);
        String newTopic = "Automation";
        input.sendKeys(newTopic);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        validateTopicInDraft(newTopic);
        System.out.println("The draft was updated");
    }

    public void singOut() {
        switchToMainWindow();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//span[@class='gb_xa gbii']"))).click().perform();
        switchToMyWindow();
        driver.findElement(By.xpath("//a[@id='gb_71']")).click();
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}


