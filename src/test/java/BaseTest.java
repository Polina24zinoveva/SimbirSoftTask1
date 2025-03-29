import helpers.Config;
import helpers.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.AddCustomerPage;
import pages.CustomersPage;
import pages.MainPage;

public class BaseTest {
    protected WebDriver driver;
    protected WaitHelper waitHelper;
    protected MainPage mainPage;
    protected AddCustomerPage addCustomerPage;
    protected CustomersPage customersPage;

    @BeforeMethod
    public void init() {
        System.setProperty("webdriver.chrome.driver", "selenium/chromedriver.exe");
        this.driver = new ChromeDriver();
        this.waitHelper = new WaitHelper(driver);
        driver.manage().window().maximize();

        this.driver.get(Config.getFullUrl() + "/#/manager");
        this.waitHelper.waitForPageLoadComplete(driver);
        this.mainPage = new MainPage(driver, waitHelper);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}