package Utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class FileLoggingFilter implements Filter {

    private final String logFilePath;

    public FileLoggingFilter(String logFilePath) {
        this.logFilePath = logFilePath;
        File dir = new File(logFilePath).getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);

        try (PrintWriter logWriter = new PrintWriter(new FileWriter(logFilePath, true))) {
            logWriter.println("========= API CALL =========");
            logWriter.println("Timestamp: " + LocalDateTime.now());
            logWriter.println("\nRequest:");
            logWriter.println(requestSpec.getMethod() + " " + requestSpec.getURI());
            requestSpec.getHeaders().forEach(header ->
                    logWriter.println(header.getName() + ": " + header.getValue()));
            if (requestSpec.getBody() != null) {
                logWriter.println("Body: " + requestSpec.getBody());
            }
            logWriter.println("\nResponse:");
            logWriter.println("Status Code: " + response.getStatusCode());
            logWriter.println("Body: " + response.getBody().asPrettyString());
            logWriter.println("============================\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
