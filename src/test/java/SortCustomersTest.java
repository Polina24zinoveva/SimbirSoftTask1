import io.qameta.allure.testng.AllureTestNg;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

@Listeners({AllureTestNg.class})
public class SortCustomersTest extends BaseTest{

    @BeforeMethod
    public void beforeMethod() {
        customersPage = mainPage.openCustomersPage(driver);
    }

    @Test
    @Description("Проверка сортировки по возрастанию")
    public void testSortCustomers() {
        List<String> listBeforeSort = customersPage.getFirstNamesList();
        listBeforeSort = listBeforeSort.stream().sorted().collect(Collectors.toList());
        customersPage.sortByFName("asc");
        List<String> listAfterSort = customersPage.getFirstNamesList();

        Assert.assertEquals(listBeforeSort, listAfterSort);
    }

    @Test
    @Description("Проверка сортировки по убыванию")
    public void testSortCustomersDescending() {
        List<String> listBeforeSort = customersPage.getFirstNamesList();
        listBeforeSort = listBeforeSort.stream().sorted((a, b) -> b.compareTo(a)).collect(Collectors.toList());
        customersPage.sortByFName("desc");
        List<String> listAfterSort = customersPage.getFirstNamesList();

        Assert.assertEquals(listBeforeSort, listAfterSort);
    }


    @Test
    @Description("Проверка повторной сортировки")
    public void testReSortAsc() {
        customersPage.sortByFName("asc");
        List<String> listAfter1Sort = customersPage.getFirstNamesList();

        customersPage.sortByFName("asc");
        List<String> listAfter2Sort = customersPage.getFirstNamesList();

        Assert.assertEquals(listAfter1Sort, listAfter2Sort);
    }

}
