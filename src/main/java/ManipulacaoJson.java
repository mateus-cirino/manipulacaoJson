import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;

public class ManipulacaoJson {
    public static Map<String, Object> toJson1(final Object objeto) {
        final Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("objectClassName", objeto.getClass().getName());

        final List<Field> fields = Arrays.asList(objeto.getClass().getDeclaredFields());
        fields.forEach(field -> {
            try {
                field.setAccessible(true);
                jsonMap.put(field.getName(), field.get(objeto));
            } catch (final IllegalAccessException e) {
                System.out.println("Erro: " + e.getMessage());
            }});

        return jsonMap;
    }

    public static Object jsonToObject1(final Map<String, Object> jsonMap) {
        try {
            final Class<?> classe = Class.forName(jsonMap.get("objectClassName").toString());

            jsonMap.remove("objectClassName");

            final Object objeto = classe.getConstructor().newInstance();
            jsonMap.forEach((s, o) -> {
                try {
                    final Field field = objeto.getClass().getDeclaredField(s);
                    field.setAccessible(true);
                    field.set(objeto, o);
                } catch (final NoSuchFieldException | IllegalAccessException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            });
            return objeto;
        }catch (final Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }

    public static JSONObject toJson2(final Object objeto) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("objectClassName", objeto.getClass().getName());

        final List<Field> fields = Arrays.asList(objeto.getClass().getDeclaredFields());
        fields.forEach(field -> {
            try {
                field.setAccessible(true);
                jsonObject.put(field.getName(), field.get(objeto));
            } catch (final IllegalAccessException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        });

        return jsonObject;
    }

    public static Object jsonToObject2(final JSONObject jsonObject) {
        try {
            final Class<?> classe = Class.forName(jsonObject.get("objectClassName").toString());

            jsonObject.remove("objectClassName");

            final Object objeto = classe.getConstructor().newInstance();
            jsonObject.toMap().forEach((s, o) -> {
                try {
                    final Field field = objeto.getClass().getDeclaredField(s);
                    field.setAccessible(true);
                    field.set(objeto, o);
                } catch (final NoSuchFieldException | IllegalAccessException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            });
            return objeto;
        }catch (final Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }

    public static String toJson3(final Object objeto) {
        final Gson gson = new Gson();
        return gson.toJson(objeto);
    }

    public static Object jsonToObject3(final String json, final Class<?> classe) {
        final Gson gson = new Gson();
        return gson.fromJson(json, classe);
    }
}
