import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.testng.AllureTestNg;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Epic("Customer Management")
@Feature("Delete Customer Functionality")
@Listeners({AllureTestNg.class})
public class DeleteCustomerTest extends BaseTest {

    @BeforeMethod
    public void beforeMethod() {
        customersPage = mainPage.openCustomersPage(driver);
    }

    @Test
    @Description("Проверка успешного удаления")
    public void testDeleteSuccessful() {
        List<String> listBeforeDelete = customersPage.getFirstNamesList();

        String nameCustomer = customersPage.selectName(listBeforeDelete);
        listBeforeDelete.remove(nameCustomer);
        customersPage.deleteCustomer(nameCustomer);

        List<String> listAfterDelete = customersPage.getFirstNamesList();

        Assert.assertEquals(listBeforeDelete, listAfterDelete);
    }

    @Test(expectedExceptions = RuntimeException.class)
    @Description("Проверка удаления несуществующего пользователя")
    public void testDeleteNonExistentCustomer() {
        customersPage.deleteCustomer("Nonexistent_Customer_123");
    }

}
