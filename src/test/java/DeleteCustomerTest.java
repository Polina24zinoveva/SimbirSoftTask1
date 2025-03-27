import io.qameta.allure.testng.AllureTestNg;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        String nameCustomer = selectName(listBeforeDelete);
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


    private String selectName(List<String> listNames) {
        List<Integer> nameLengths = listNames.stream()
                .map(String::length)
                .collect(Collectors.toList());

        double averageLength = nameLengths.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        Optional<String> name = listNames.stream()
                .min((name1, name2) -> {
                    int diff1 = Math.abs(name1.length() - (int) averageLength);
                    int diff2 = Math.abs(name2.length() - (int) averageLength);
                    return Integer.compare(diff1, diff2);
                });
        return name.orElse(null);

    }
}
