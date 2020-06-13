import com.google.gson.Gson;
import model.AnyObject;
import serializer.MyGson;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        MyGson myGson = new MyGson();

        List<String> education = new ArrayList<>();
        education.add("33");
        education.add("temp");

        AnyObject obj = new AnyObject("Dan", 22, "Boston", "swimming", true, education, new Object[]{"one", "two", "three"});

        String gsonJson = gson.toJson(obj);
        String myJson = myGson.toJson(obj);

        System.out.println("Gson------>>>  " + gsonJson);
        System.out.println("MyJson---->>>  " + myJson);
        System.out.println("_______________________");
        AnyObject obj2 = gson.fromJson(myJson, AnyObject.class);
        System.out.println("obj equals obj2 ---->>>  " + obj.equals(obj2));
    }
}
