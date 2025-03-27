package pages;

import helpers.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    private final WebDriver driver;
    private final WaitHelper waitHelper;

    @FindBy(xpath = "//button[@ng-class='btnClass1']")
    private WebElement addCustomerButton;

    @FindBy(xpath = "//button[@ng-class='btnClass3']")
    WebElement customersButton;

    public MainPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
        PageFactory.initElements(driver, this);
    }

    public AddCustomerPage openAddCustomerPage(WebDriver driver) {
        waitHelper.waitForClickability(addCustomerButton).click();
        return new AddCustomerPage(driver, waitHelper);
    }

    public final CustomersPage openCustomersPage(WebDriver driver) {
        waitHelper.waitForClickability(customersButton).click();
        return new CustomersPage(driver, waitHelper);
    }
}
