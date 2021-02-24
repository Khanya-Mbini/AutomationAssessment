package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
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

    @Before
    public void initBankingStepsDriver(){
        bankingSteps.setWebDriver(driver);
    }

    @Test
    public void makeSingleDeposit(){
        bankingSteps.loginAndSelectCustomer(2);
        bankingSteps.doDeposit("1500");
        bankingSteps.depositIsSuccessful();
        bankingSteps.logout();
    }

    @Test
    public void makeMultipleDeposits(){
        bankingSteps.loginAndSelectCustomer(2);
        for (int i = 0; i < 3; ++i){
            bankingSteps.selectCustomerAccount(i);
            bankingSteps.doDeposit("1500");
            bankingSteps.depositIsSuccessful();
        }
        bankingSteps.logout();
    }

    @Test
    public void performMultipleTransactions() throws Throwable {
        bankingSteps.loginAndSelectCustomer(2);
        bankingSteps.doDeposit("31459");
        bankingSteps.depositIsSuccessful();
        bankingSteps.checkTransactionCreated("31459", "Credit");
        bankingSteps.doWithdrawal("31459");
        bankingSteps.validateBalances("0");
        bankingSteps.checkTransactionCreated("31459", "Debit");
        bankingSteps.logout();
    }

    @Test
    public void testWithJSONData() throws Throwable {
        FileReader reader = new FileReader("src/main/resources/support/scenarioData.json");
        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(reader);

        bankingSteps.loginAndSelectCustomer(Integer.parseInt((String)data.get("customerIndex")));
        bankingSteps.doDeposit((String)data.get("depositAmount"));
        bankingSteps.depositIsSuccessful();
        bankingSteps.checkTransactionCreated((String)data.get("depositAmount"), "Credit");
        bankingSteps.doWithdrawal((String)data.get("withdrawalAmount"));
        bankingSteps.validateBalances("0");
        bankingSteps.checkTransactionCreated((String)data.get("withdrawalAmount"), "Debit");
        bankingSteps.logout();
    }

    @Test
    public void unsuccessfulWithdrawal(){
        bankingSteps.loginAndSelectCustomer(2);
        bankingSteps.doWithdrawal("1500");
        bankingSteps.unsuccessfulWithdrawal();
        bankingSteps.logout();
    }
}
