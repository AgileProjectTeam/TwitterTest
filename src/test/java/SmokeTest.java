import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.*;

/**
 * Created by hazel on 30.03.2017.
 */
public class SmokeTest {

    private static WebDriver driver = null;
    private static String baseUrl = null;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HazelTuğba\\Desktop\\Agile\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://agile123.000webhostapp.com/login/login.php";
        driver.get(baseUrl);
    }

    @Test
    public void shouldLogin() throws Exception {

        driver.findElement(By.id("username")).sendKeys("hazel");
        driver.findElement(By.id("password")).sendKeys("asdfgh");
        driver.findElement(By.id("login")).click();
        assertTrue(isElementPresent(By.xpath("//a[@href = '/profile/profile.php?name=hazel']")));

    }

    @Test
    public void shouldNotLogin() throws Exception {

        driver.findElement(By.id("username")).sendKeys("hazel");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("login")).click();
        String textLogin = driver.findElement(By.cssSelector("body > center")).getText();
        assertTrue(textLogin.contains("Credentials were not valid."));

    }

    @Test
    public void shouldLogout() throws Exception {

        driver.findElement(By.id("username")).sendKeys("hazel");
        driver.findElement(By.id("password")).sendKeys("asdfgh");
        driver.findElement(By.id("login")).click();
        driver.get("https://agile123.000webhostapp.com/login/logout.php");
        String textLogout = driver.findElement(By.cssSelector("body > div > center")).getText();
        System.out.print(textLogout);
        assertTrue(textLogout.contains("You have successfully logged out."));

    }

    @Test
    public void shouldRegister() throws Exception {

        driver.get("https://agile123.000webhostapp.com/login/register.php");
        driver.findElement(By.id("username")).sendKeys("oooo");
        driver.findElement(By.id("password")).sendKeys("1246");
        driver.findElement(By.id("age")).sendKeys("35");
        driver.findElement(By.id("email")).sendKeys("oo@oo.com");
        driver.findElement(By.id("register")).click();
        String textReg = driver.findElement(By.cssSelector("body > div > center")).getText();
        assertTrue(textReg.contains("You are registered. Click here to login."));


    }

    @Test
    public void shouldFollow() throws Exception {

        driver.findElement(By.id("username")).sendKeys("hazel");
        driver.findElement(By.id("password")).sendKeys("asdfgh");
        driver.findElement(By.id("login")).click();
        driver.get("https://agile123.000webhostapp.com/profile/profile.php?name=selin");
        driver.findElement(By.xpath("//a[@href = '/follow/follow.php?target=20']")).click();
        driver.get("https://agile123.000webhostapp.com/follow/followings.php");
        assertTrue(isElementPresent(By.xpath("//a[@href = '/follow/unfollow.php?target=20']")));

    }
    @Test
    public void shouldUnfollow() throws Exception {

        driver.findElement(By.id("username")).sendKeys("hazel");
        driver.findElement(By.id("password")).sendKeys("asdfgh");
        driver.findElement(By.id("login")).click();
        driver.get("https://agile123.000webhostapp.com/follow/followings.php");
        driver.findElement(By.xpath("//a[@href = '/follow/unfollow.php?target=20']")).click();
        assertTrue(!isElementPresent(By.xpath("//a[@href = '/follow/unfollow.php?target=20']")));


    }

    @Test
    public void shouldGetAgeInfo() throws Exception {

        driver.findElement(By.id("username")).sendKeys("yuyu");
        driver.findElement(By.id("password")).sendKeys("yuyu");
        driver.findElement(By.id("login")).click();
        driver.get("https://agile123.000webhostapp.com/profile/profile.php?name=yuyu");
        String textReg = driver.findElement(By.xpath("/html/body/div/center")).getText();
        assertTrue(textReg.contains("Age of"));
        assertTrue(textReg.contains("25"));

    }

    @Test
    public void shouldTweet() throws Exception {

        driver.findElement(By.id("username")).sendKeys("hazel");
        driver.findElement(By.id("password")).sendKeys("asdfgh");
        driver.findElement(By.id("login")).click();
        driver.get("https://agile123.000webhostapp.com/");
        driver.findElement(By.id("text")).sendKeys("agile");
        driver.findElement(By.id("login")).click();
        String textReg = driver.findElement(By.xpath("/html/body/div/center")).getText();
        assertTrue(textReg.contains("Your message is successfully shared."));

    }

    @Test
    public void shouldListTweets() throws Exception {

        driver.findElement(By.id("username")).sendKeys("yuyu");
        driver.findElement(By.id("password")).sendKeys("yuyu");
        driver.findElement(By.id("login")).click();
        driver.get("https://agile123.000webhostapp.com/");
        String textReg = driver.findElement(By.xpath("/html/body/div/center")).getText();
        assertTrue(textReg.contains("asas"));
        assertTrue(textReg.contains("yuyuyuyu"));
        assertTrue(textReg.contains("Message"));
        assertTrue(textReg.contains("deneme"));

    }
    
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }
}
