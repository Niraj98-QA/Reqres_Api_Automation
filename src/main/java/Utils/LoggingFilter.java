package Utils;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class LoggingFilter implements Filter {

    private final ExtentTest test;

    public LoggingFilter(ExtentTest test) {
        this.test = test;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        String method = requestSpec.getMethod();
        String url = requestSpec.getURI();
        Object requestBody = requestSpec.getBody();

        LoggingUtil.logRequest(test, method, url, requestBody);

        Response response = ctx.next(requestSpec, responseSpec);

        LoggingUtil.logResponse(test, response);

        return response;
    }
}