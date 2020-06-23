import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serializer.MyGson;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyGsonTest {
    Gson gson;
    MyGson myGson;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
        myGson = new MyGson();
    }

    @Test
    public void checkNull() {
        assertEquals(myGson.toJson((null)), gson.toJson((null)));
    }

    @Test
    public void checkEqualsByteType() {
        assertEquals(myGson.toJson((byte) 1), gson.toJson((byte) 1));
    }

    @Test
    public void checkEqualsShortType() {
        assertEquals(myGson.toJson((short) 1f), gson.toJson((short) 1f));
    }

    @Test
    public void checkEqualsIntType() {
        assertEquals(myGson.toJson(1), gson.toJson(1));
    }

    @Test
    public void checkEqualsLongType() {
        assertEquals(myGson.toJson(1L), gson.toJson(1L));
    }

    @Test
    public void checkEqualsFloatType() {
        assertEquals(myGson.toJson(1f), gson.toJson(1f));
    }

    @Test
    public void checkEqualsDoubleType() {
        assertEquals(myGson.toJson(1d), gson.toJson(1d));
    }

    @Test
    public void checkEqualsStringType() {
        assertEquals(myGson.toJson("aaa"), gson.toJson("aaa"));
    }

    @Test
    public void checkEqualsCharType() {
        assertEquals(myGson.toJson('a'), gson.toJson("a"));
    }

    @Test
    public void checkEqualsPrimitiveArrType() {
        assertEquals(myGson.toJson(new int[]{1, 2, 3}), gson.toJson(new int[]{1, 2, 3}));
    }

    @Test
    public void checkEqualsListType() {
        assertEquals(myGson.toJson(List.of(1, 2, 3)), gson.toJson(List.of(1, 2, 3)));
    }

    @Test
    public void checkEqualsCollectionsType() {
        assertEquals(myGson.toJson(Collections.singletonList(1)), gson.toJson(Collections.singletonList(1)));
    }
}
