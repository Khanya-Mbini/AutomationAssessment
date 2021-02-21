package steps;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import static org.hamcrest.Matchers.is;

public class UserSteps {
    private String UsersEndpoint="https://jsonplaceholder.typicode.com/users";
    //private Response response;

    @Step public void GetUsers(){

       Response response = SerenityRest.when().get(UsersEndpoint);
    }


}
