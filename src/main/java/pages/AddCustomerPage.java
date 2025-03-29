package pages;

import helpers.WaitHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

public class AddCustomerPage {
    private final WaitHelper waitHelper;

    public AddCustomerPage(WebDriver driver, WaitHelper waitHelper) {
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

    @Step("Ввод данных")
    public AddCustomerPage addCustomer(String fName, String lName, String postCode) {
        waitHelper.waitForVisibility(firstNameInput).sendKeys(fName);
        waitHelper.waitForVisibility(lastNameInput).sendKeys(lName);
        waitHelper.waitForVisibility(postCodeInput).sendKeys(postCode);
        waitHelper.waitForClickability(addCustomerButton).click();
        return this;
    }

    @Step("Очистка данных")
    public AddCustomerPage clearForm() {
        waitHelper.waitForVisibility(firstNameInput).clear();
        waitHelper.waitForVisibility(lastNameInput).clear();
        waitHelper.waitForVisibility(postCodeInput).clear();
        return this;
    }

    @Step("Генерация почтового PostCode")
    public String generatePostCode() {
        Random random = new Random();
        StringBuilder postCode = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            postCode.append(random.nextInt(10));
        }
        return postCode.toString();
    }

    @Step("Генерация почтового FirstName")
    public String generateFirstNameFromPostCode(String postCode) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < postCode.length(); i += 2) {
            if (i + 2 > postCode.length()) break;
            int index = Integer.parseInt(postCode.substring(i, i + 2)) % 26;
            char letter = (char) ('a' + index);
            result.append(letter);
        }
        return result.toString();
    }
}