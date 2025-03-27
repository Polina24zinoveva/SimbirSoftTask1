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

@Listeners({AllureTestNg.class})
public class AddCustomerPageTest extends BaseTest {

    @BeforeMethod
    public void beforeMethod() {
        addCustomerPage = mainPage.openAddCustomerPage(driver);
    }

    @Test
    @Description("Проверка успешного добавления клиента")
    public void testSuccessfulCustomerCreation() {
        String postCode = generatePostCode();
        String firstName = generateFirstNameFromPostCode(postCode);

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
        String postCode = generatePostCode();
        String firstName = "";

        addCustomerPage.addCustomer(firstName, firstName, postCode);

        Assert.assertThrows(TimeoutException.class, () -> {
            waitHelper.waitForAlertPresent();
        });
    }

    @Test(threadPoolSize = 3, invocationCount = 1)
    @Description("Проверка неудачного добавления клиента без фамилии")
    public void testWithoutLastNameCustomerCreation() {
        String postCode = generatePostCode();
        String firstName = generateFirstNameFromPostCode(postCode);

        addCustomerPage.addCustomer(firstName, "", postCode);

        Assert.assertThrows(TimeoutException.class, () -> {
            waitHelper.waitForAlertPresent();
        });
    }

    @Test(threadPoolSize = 3, invocationCount = 1)
    @Description("Проверка неудачного добавления клиента без почтового индекса")
    public void testWithoutFirstNameAndPostCodeCustomerCreation() {
        addCustomerPage.addCustomer("", "aaa", "");

        Assert.assertThrows(TimeoutException.class, () -> {
            waitHelper.waitForAlertPresent();
        });
    }

    @Step("Генерация почтового PostCode")
    private String generatePostCode() {
        Random random = new Random();
        StringBuilder postCode = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            postCode.append(random.nextInt(10));
        }
        return postCode.toString();
    }

    @Step("Генерация почтового FirstName")
    private String generateFirstNameFromPostCode(String postCode) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < postCode.length(); i += 2) {
            if (i + 2 > postCode.length()) break;
            int index = Integer.parseInt(postCode.substring(i, i + 2)) % 26;
            char letter = (char) ('a' + index);
            result.append(letter);
        }
        return result.toString();
    }

    @AfterMethod
    public void afterMethod() {
        addCustomerPage.clearForm();
    }

}