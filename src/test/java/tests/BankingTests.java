package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import steps.BankingSteps;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

@RunWith(SerenityRunner.class)

public class BankingTests {

    @Managed
    WebDriver driver;

    @Steps
    BankingSteps bankingSteps;

    @Test
    public void makeSingleDeposit(){
        bankingSteps.loginAsCustomer(driver);
        bankingSteps.selectCustomer(driver, 1);
        bankingSteps.doDeposit(driver, "1500");
        bankingSteps.depositIsSuccessful(driver);
        bankingSteps.logout(driver);
    }

    @Test
    public void makeMultipleDeposits(){
        bankingSteps.loginAsCustomer(driver);
        bankingSteps.selectCustomer(driver, 1);
        for (int i = 0; i < 3; ++i){
            bankingSteps.selectCustomerAccount(driver, i);
            bankingSteps.doDeposit(driver, "1500");
            bankingSteps.depositIsSuccessful(driver);
        }
        bankingSteps.logout(driver);
    }

    @Test
    public void performMultipleTransactions(){
        bankingSteps.loginAsCustomer(driver);
        bankingSteps.selectCustomer(driver, 1);
        bankingSteps.clearAllTransactions(driver);
        bankingSteps.doDeposit(driver, "31459");
        bankingSteps.depositIsSuccessful(driver);
        bankingSteps.checkTransactionCreated(driver, "31459", "Credit");
        bankingSteps.doWithdrawal(driver, "31459");
        bankingSteps.validateBalances(driver, "0");
        bankingSteps.checkTransactionCreated(driver, "31459", "Debit");
        bankingSteps.logout(driver);
    }

    @Test
    public void testWithJSONData() throws Throwable{
        FileReader reader = new FileReader("src/main/resources/support/scenarioData.json");
        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(reader);

        bankingSteps.loginAsCustomer(driver);
        bankingSteps.selectCustomer(driver, Integer.parseInt((String)data.get("customerIndex")));
        bankingSteps.clearAllTransactions(driver);
        bankingSteps.doDeposit(driver, (String)data.get("depositAmount"));
        bankingSteps.depositIsSuccessful(driver);
        bankingSteps.checkTransactionCreated(driver, (String)data.get("depositAmount"), "Credit");
        bankingSteps.doWithdrawal(driver, (String)data.get("withdrawalAmount"));
        bankingSteps.validateBalances(driver, "0");
        bankingSteps.checkTransactionCreated(driver, (String)data.get("withdrawalAmount"), "Debit");
        bankingSteps.logout(driver);
    }

    @Test
    public void unsuccessfulWithdrawal(){
        bankingSteps.loginAsCustomer(driver);
        bankingSteps.selectCustomer(driver, 1);
        bankingSteps.clearAllTransactions(driver);
        bankingSteps.doWithdrawal(driver, "1500");
        bankingSteps.unsuccessfulWithdrawal(driver);
        bankingSteps.logout(driver);

    }
}
