package steps;

import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BankingSteps {

    @Step
    public void loginAsCustomer(WebDriver driver) {
        driver.get("http://www.way2automation.com/angularjs-protractor/banking/#/login");
        WebElement loginButton = driver.findElement(By.xpath(("//button[@ng-click='customer()']")));
        loginButton.click();
    }

    @Step
    public void selectCustomer(WebDriver driver, int customerIndex) {
        Select selectInput = new Select(driver.findElement(By.id(("userSelect"))));
        selectInput.selectByIndex(customerIndex);
        WebElement loginButton = driver.findElement(By.xpath(("//button[@type='submit']")));
        loginButton.click();
    }

    @Step
    public void selectCustomerAccount(WebDriver driver, int accountIndex) {
        Select selectInput = new Select(driver.findElement(By.id(("accountSelect"))));
        selectInput.selectByIndex(accountIndex);
    }

    @Step
    public void doDeposit(WebDriver driver, String depositAmount) {
        WebElement depositButton = driver.findElement(By.xpath("//button[@ng-click='deposit()']"));
        depositButton.click();

        WebElement amountInput = driver.findElement(By.xpath("//input[@type='number']"));
        amountInput.sendKeys(depositAmount);
        WebElement submitDepositButton = driver.findElement(By.xpath(("//button[@type='submit']")));
        submitDepositButton.click();
    }

    @Step
    public void depositIsSuccessful(WebDriver driver) {
        WebElement span = driver.findElement(By.xpath("//span[@ng-show='message']"));
        String message = span.getAttribute("textContent");
        Assert.assertEquals("Deposit Successful", message);
    }

    @Step
    public void logout(WebDriver driver) {
        WebElement logoutButton = driver.findElement(By.xpath("//button[@ng-click='byebye()']"));
        logoutButton.click();
    }

    @Step
    public void doWithdrawal(WebDriver driver, String withdrawalAmount) {
        WebElement activityButton = driver.findElement(By.xpath("//button[@ng-click='withdrawl()']"));
        activityButton.click();

        WebElement amountInput = driver.findElement(By.xpath("//input[@type='number']"));
        amountInput.sendKeys(withdrawalAmount);
        WebElement submitButton = driver.findElement(By.xpath(("//button[@type='submit']")));
        submitButton.click();
    }

    @Step
    public void checkTransactionCreated(WebDriver driver, String transactionAmount, String transactionType) {
        WebElement activityButton = driver.findElement(By.xpath("//button[@ng-click='transactions()']"));
        activityButton.click();

        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        WebElement row = rows.get(rows.size() - 1);
        List<WebElement> cells = row.findElements(By.tagName("td"));

        Assert.assertEquals(transactionAmount, cells.get(1).getAttribute("textContent"));
        Assert.assertEquals(transactionType, cells.get(2).getAttribute("textContent"));
        WebElement backButton = driver.findElement(By.xpath("//button[@ng-click='back()']"));
        backButton.click();
    }

    @Step
    public void clearAllTransactions(WebDriver driver) {
        WebElement activityButton = driver.findElement(By.xpath("//button[@ng-click='transactions()']"));
        activityButton.click();
        WebElement clearButton = driver.findElement(By.xpath("//button[@ng-click='reset()']"));
        if (clearButton != null) {
            clearButton.click();
        }
        WebElement backButton = driver.findElement(By.xpath("//button[@ng-click='back()']"));
        backButton.click();
    }

    @Step
    public void validateBalances(WebDriver driver, String expectedBalance) {
        WebElement balanceStrong = driver.findElements(By.xpath("//div[@class='center']/strong")).get(1);
        Assert.assertEquals(expectedBalance, balanceStrong.getAttribute("textContent"));
    }

    @Step
    public void unsuccessfulWithdrawal(WebDriver driver) {

        WebElement span = driver.findElement(By.xpath("//span[@ng-show='message']"));
        String message = span.getAttribute("textContent");
        Assert.assertEquals("Transaction Failed. You can not withdraw amount more than the balance.", message);

    }

}
