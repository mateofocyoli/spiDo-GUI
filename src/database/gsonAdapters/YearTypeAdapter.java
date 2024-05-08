package database.gsonAdapters;

/**
 * This code was found on this link:
 * https://howtodoinjava.com/gson/gson-typeadapter-for-inaccessibleobjectexception/
 */

import java.lang.reflect.Type;
import java.time.Year;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class YearTypeAdapter implements JsonSerializer<Year>, JsonDeserializer<Year> {


    @Override
    public JsonElement serialize(final Year date, final Type typeOfSrc,
            final JsonSerializationContext context) {
        return new JsonPrimitive(date.getValue());
    }

    @Override
    public Year deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) throws JsonParseException {
        return Year.of(json.getAsInt());
    }
}
