package com.example.registrationform.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUserCRUD implements UserJson{
    private static final String JSON_FILE_PATH = "src/main/java/com/example/registrationform/users.json";
    private List<User> readFile() {
        List<User> userList = new ArrayList<>();

        try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject userJson = (JSONObject) obj;
                String username = (String) userJson.get("username");
                String email = (String) userJson.get("email");
                String password = (String) userJson.get("password");

                User user = new User(username, email, password);
                userList.add(user);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return userList;
    }

    private void writeToFile(List<User> userList) {
        JSONArray jsonArray = new JSONArray();

        for (User user : userList) {
            JSONObject userJson = new JSONObject();
            userJson.put("username", user.getName());
            userJson.put("email", user.getEmail());
            userJson.put("password", user.getPassword());
            jsonArray.add(userJson);
        }

        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            writer.write(jsonArray.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return readFile();
    }

    @Override
    public User getUserByUsername(String email) {
        List<User> userList = readFile();

        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        List<User> userList = readFile();
        userList.add(user);
        writeToFile(userList);
    }

    @Override
    public void updateUser(User user) {
        List<User> userList = readFile();

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getName().equals(user.getName())) {
                userList.set(i, user);
                break;
            }
        }
        writeToFile(userList);
    }

    @Override
    public void deleteUser(User user) {
        List<User> userList = readFile();
        userList.remove(user);
        writeToFile(userList);
    }
}
