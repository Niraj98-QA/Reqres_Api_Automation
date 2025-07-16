package Utils;

import org.testng.annotations.DataProvider;

public class DataProviders
{
    @DataProvider(name = "createUserData")
    public static Object[][] createUserData() {
        return new Object[][]{
                {"Sam","QA"},
                {"Peter","Devops Engineer"},
                {"paul","Developer"}
        };
    }
}
