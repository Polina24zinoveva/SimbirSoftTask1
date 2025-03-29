import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import jdk.jfr.Description;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Random;

@Epic("Customer Management")
@Feature("Add Customer Functionality")
@Listeners({AllureTestNg.class})
public class AddCustomerPageTest extends BaseTest {

    @BeforeMethod
    public void beforeMethod() {
        addCustomerPage = mainPage.openAddCustomerPage(driver);
    }

    @Test
    @Description("Проверка успешного добавления клиента")
    public void testSuccessfulCustomerCreation() {
        String postCode = addCustomerPage.generatePostCode();
        String firstName = addCustomerPage.generateFirstNameFromPostCode(postCode);

        addCustomerPage.addCustomer(firstName, firstName, postCode);

        waitHelper.waitForAlertPresent();
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();

        Assert.assertTrue(alertText.contains("Customer added successfully"));
    }

    @Test
    @Description("Проверка неудачного добавления клиента с пустыми полями")
    public void testUnSuccessfulCustomerCreation() {
        String postCode = "";
        String firstName = "";

        addCustomerPage.addCustomer(firstName, firstName, postCode);

        Assert.assertThrows(TimeoutException.class, () -> {
            waitHelper.waitForAlertPresent();
        });
        addCustomerPage.addCustomer("", "", "");
    }

    @Test
    @Description("Проверка неудачного добавления клиента без имени")
    public void testWithoutFirstNameCustomerCreation() {
        String postCode = addCustomerPage.generatePostCode();
        String firstName = "";

        addCustomerPage.addCustomer(firstName, firstName, postCode);

        Assert.assertThrows(TimeoutException.class, () -> {
            waitHelper.waitForAlertPresent();
        });
    }

    @Test
    @Description("Проверка неудачного добавления клиента без фамилии")
    public void testWithoutLastNameCustomerCreation() {
        String postCode = addCustomerPage.generatePostCode();
        String firstName = addCustomerPage.generateFirstNameFromPostCode(postCode);

        addCustomerPage.addCustomer(firstName, "", postCode);

        Assert.assertThrows(TimeoutException.class, () -> {
            waitHelper.waitForAlertPresent();
        });
    }

    @Test
    @Description("Проверка неудачного добавления клиента без почтового индекса")
    public void testWithoutFirstNameAndPostCodeCustomerCreation() {
        addCustomerPage.addCustomer("", "aaa", "");

        Assert.assertThrows(TimeoutException.class, () -> {
            waitHelper.waitForAlertPresent();
        });
    }


    @AfterMethod
    public void afterMethod() {
        addCustomerPage.clearForm();
    }

}