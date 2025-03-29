package pages;

import helpers.WaitHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    private final WaitHelper waitHelper;

    @FindBy(xpath = "//button[@ng-class='btnClass1']")
    private WebElement addCustomerButton;

    @FindBy(xpath = "//button[@ng-class='btnClass3']")
    WebElement customersButton;

    public MainPage(WebDriver driver, WaitHelper waitHelper) {
        this.waitHelper = waitHelper;
        PageFactory.initElements(driver, this);
    }

    @Step("Открыть страницу AddCustomer")
    public AddCustomerPage openAddCustomerPage(WebDriver driver) {
        waitHelper.waitForClickability(addCustomerButton).click();
        return new AddCustomerPage(driver, waitHelper);
    }

    @Step("Открыть страницу Customers")
    public final CustomersPage openCustomersPage(WebDriver driver) {
        waitHelper.waitForClickability(customersButton).click();
        return new CustomersPage(driver, waitHelper);
    }
}
