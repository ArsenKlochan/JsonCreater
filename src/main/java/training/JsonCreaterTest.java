package training;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonCreaterTest {
    public static void main(String[] args) {
//        Files files = new Files("test");
        writeJsonSimple("test456.json");
        JSONObject jsonObject = (JSONObject) readJsonSimple("test.json");
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("age"));
        JSONObject jsonObject1 = (JSONObject) readJsonSimple("example.json");
        Person ben = new Person(
                (String) jsonObject1.get("name"),
                Integer.valueOf(jsonObject1.get("age").toString()),
                (Boolean) jsonObject1.get("isMarried"),
                (List) jsonObject1.get("hobbies"),
                (List) jsonObject1.get("kids"));
        System.out.println(ben);
        System.out.println(ben.getKids().get(0));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Person ben1 = objectMapper.readValue(new File("example.json"), Person.class);
        System.out.println(ben1);
        System.out.println(ben1.getKids());
        System.out.println(ben1.getKids().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    public static void writeJsonSimple(String filename){
        JSONObject sampleObject = new JSONObject();
        sampleObject.put("name", "Stackbuster");
        sampleObject.put("age", 26);

        JSONArray messages = new JSONArray();
        JSONArray messages1 = new JSONArray();
        messages1.add(messages);
        messages1.add(messages);
        messages.add("Hi!");
        messages.add("How are you!");
        sampleObject.put("messages", messages);
        sampleObject.put("", messages1);
        try {
            Files.write(Paths.get(filename), sampleObject.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readJsonSimple(String fileName){
        try {
            FileReader reader = new FileReader(fileName);
            JSONParser jsonParser = new JSONParser();
            return jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
