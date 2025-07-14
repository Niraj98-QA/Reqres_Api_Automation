package Utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.IOException;

public class JsonReader
{
    public static String getJsonData(String key) throws IOException, ParseException
    {
        return (String) getJsonData().get(key);
    }

    public static JSONObject getJsonData() throws IOException, ParseException
    {
        File file = new File("Resources/TestData/testdata.json");
        String json = FileUtils.readFileToString(file, "UTF-8");
        Object object = new JSONParser().parse(json);
        JSONObject jsonObject = (JSONObject) object;
        return jsonObject;
    }
}
