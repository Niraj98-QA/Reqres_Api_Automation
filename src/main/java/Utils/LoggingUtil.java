package Utils;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoggingUtil
{
    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void logRequest(ExtentTest test, String method,String endpint,Object requestBody)
    {
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("Method",method);
        logMap.put("Endpoint",endpint);

        if(requestBody == null)
        {
            logMap.put("Request Body","A method without request body (GET or similar request)");
        }
        else
        {
            logMap.put("Request Body",requestBody);
        }

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap);
            test.info("<pre>"+json+"</pre>");
        }
        catch (Exception e) {
            test.warning("Failed to log a request "+e.getMessage());
        }
    }

    public static void logResponse(ExtentTest test, Response response)
    {
        Map<String,Object> logMap = new HashMap<>();
        logMap.put("Status Code",response.getStatusCode());
        logMap.put("Headers",response.getHeaders());
        logMap.put("Execution time",response.timeIn(TimeUnit.SECONDS)+" seconds");

       Object responseBody;
       try
       {
           responseBody = response.getBody().as(Map.class);

       }catch (Exception e)
       {
           responseBody = response.getBody().asString();
       }

       logMap.put("Response Body",responseBody);


        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap);
            test.info("<pre>"+json+"</pre>");
        }
        catch (Exception e) {
            test.warning("Failed to log a response "+e.getMessage());
        }
    }
}