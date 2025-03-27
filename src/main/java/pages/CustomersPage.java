package pages;

import helpers.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CustomersPage {
    private WebDriver driver;
    private final WaitHelper waitHelper;

    public CustomersPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(text(), 'First Name')]")
    private WebElement sortByFName;


    @FindBy(xpath = "//table//tbody//tr//td[1]")
    private List<WebElement> firstNameColumn;

    @FindBy(xpath = "//table[@class='table table-bordered table-striped']//tbody/tr")
    private List<WebElement> customerRows;


    public void sortByFName(String direction) {
        switch (direction){
            case "asc":
                waitHelper.waitForVisibility(sortByFName).click();
                waitHelper.waitForVisibility(sortByFName).click();
                break;
            case "desc":
                waitHelper.waitForVisibility(sortByFName).click();
                break;
        }

    }

    public List<String> getFirstNamesList() {
        waitHelper.waitForVisibility(firstNameColumn);
        return firstNameColumn.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void deleteCustomer(String customerName) {
        if (customerName != null) {
            // Находим строку с нужным именем
            WebElement customerRow = customerRows.stream()
                    .filter(row -> {
                        WebElement nameCell = row.findElement(By.xpath(".//td[1]"));
                        return nameCell.getText().equals(customerName);
                    })
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Customer not found: " + customerName));

            // Находим кнопку удаления в этой строке
            WebElement deleteButton = customerRow.findElement(By.xpath(".//button[contains(@ng-click, 'deleteCust')]"));
            waitHelper.waitForClickability(deleteButton).click();
        }
    }
}
