package serializer;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

public class MyGson {
    public String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        StringBuilder json = new StringBuilder();
        serialize(obj, json, null);
        return json.toString();
    }

    private void serialize(Object obj, StringBuilder json, String title) {
        if (obj == null) {
            return;
        }

        if (title != null) {
            json.append("\"").append(title).append("\":");
        }

        if (isPrimitive(obj)) {
            json.append(obj);
        } else if (isCharOrString(obj)) {
            json.append("\"").append(obj).append("\"");
        } else if (obj.getClass().isArray()) {
            json.append("[");
            for (int i = 0; i < Array.getLength(obj); i++) {
                serialize(Array.get(obj, i), json, null);
                if (i != Array.getLength(obj) - 1) {
                    json.append(",");
                }
            }
            json.append("]");
        } else if (obj instanceof Collection) {
            json.append("[");
            Iterator iterator = ((Collection) obj).iterator();
            while (iterator.hasNext()) {
                serialize(iterator.next(), json, null);
                if (iterator.hasNext()) {
                    json.append(",");
                }
            }
            json.append("]");
        } else {
            json.append("{");
            Field[] fields = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field field = fields[i];
                    field.setAccessible(true);
                    if (field.get(obj) != null) {
                        serialize(field.get(obj), json, field.getName());
                    }
                    if (i != fields.length - 1) {
                        json.append(",");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            json.append("}");
        }
    }

    private Boolean isPrimitive(Object obj) {
        return obj instanceof Double || obj instanceof Short
                || obj instanceof Byte || obj instanceof Integer
                || obj instanceof Float || obj instanceof Long
                || obj instanceof Boolean;
    }

    private Boolean isCharOrString(Object obj) {
        return obj instanceof Character || obj instanceof String;
    }
}
