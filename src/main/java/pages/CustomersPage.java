package pages;

import helpers.WaitHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomersPage {
    private final WaitHelper waitHelper;

    public CustomersPage(WebDriver driver, WaitHelper waitHelper) {
        this.waitHelper = waitHelper;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(), 'First Name')]")
    private WebElement sortByFName;


    @FindBy(xpath = "//table//tbody//tr//td[1]")
    private List<WebElement> firstNameColumn;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped']//tbody/tr")
    private List<WebElement> customerRows;


    public CustomersPage sortByFName(String direction) {
        switch (direction) {
            case "asc":
                waitHelper.waitForVisibility(sortByFName).click();
                waitHelper.waitForVisibility(sortByFName).click();
                break;
            case "desc":
                waitHelper.waitForVisibility(sortByFName).click();
                break;
        }
        return this;

    }

    @Step("Получение списка имен")
    public List<String> getFirstNamesList() {
        waitHelper.waitForVisibility(firstNameColumn);
        return firstNameColumn.stream().map(WebElement::getText).collect(Collectors.toList());
    }


    /**
     * Удаляет customer по указанному имени.
     *
     * <p>Поиск осуществляется по точному совпадению имени в первой колонке таблицы.
     * При успешном нахождении кликает кнопку удаления в соответствующей строке.</p>
     *
     * @param customerName имя customer для удаления (должно точно соответствовать значению в таблице)
     * @return текущий экземпляр CustomersPage для поддержки паттерна Chain Of Invocations
     * @throws RuntimeException если customer с указанным именем не найден
     */
    @Step("Удаление customer")
    public CustomersPage deleteCustomer(String customerName) {
        if (customerName != null) {
            WebElement customerRow = customerRows.stream()
                    .filter(row -> {
                        WebElement nameCell = row.findElement(By.xpath(".//td[1]"));
                        return nameCell.getText().equals(customerName);
                    })
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + customerName));

            WebElement deleteButton = customerRow.findElement(By.xpath(".//button[contains(@ng-click, 'deleteCust')]"));
            waitHelper.waitForClickability(deleteButton).click();
        }
        return this;
    }

    @Step("Выбор имени для удаления")
    public String selectName(List<String> listNames) {
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
