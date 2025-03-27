package pages;

import helpers.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerPage {
    private final WebDriver driver;
    private final WaitHelper waitHelper;

    public AddCustomerPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
        PageFactory.initElements(driver, this);
        waitHelper.waitForVisibility(firstNameInput);
    }


    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder='Post Code']")
    private WebElement postCodeInput;

    @FindBy(xpath = "//button[text()='Add Customer']")
    private WebElement addCustomerButton;

    public String getFirstNameError() {
        return firstNameInput.getAttribute("validationMessage");
    }

    public String getLastNameError() {
        return lastNameInput.getAttribute("validationMessage");
    }

    public String getPostCodeError() {
        return postCodeInput.getAttribute("validationMessage");
    }


    public void addCustomer(String fName, String lName, String postCode) {
        waitHelper.waitForVisibility(firstNameInput).sendKeys(fName);
        waitHelper.waitForVisibility(lastNameInput).sendKeys(lName);
        waitHelper.waitForVisibility(postCodeInput).sendKeys(postCode);
        waitHelper.waitForClickability(addCustomerButton).click();
    }

    public void clearForm() {
        waitHelper.waitForVisibility(firstNameInput).clear();
        waitHelper.waitForVisibility(lastNameInput).clear();
        waitHelper.waitForVisibility(postCodeInput).clear();
    }
}