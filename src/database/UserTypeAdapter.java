package database;

/**
 * This code was found on this link:
 * https://howtodoinjava.com/gson/gson-typeadapter-for-inaccessibleobjectexception/
 */

import java.lang.reflect.Type;

import users.PersonManager;
import users.User;
import users.PersonManager;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UserTypeAdapter implements JsonSerializer<User>, JsonDeserializer<User> {


    @Override
    public JsonElement serialize(final User User, final Type typeOfSrc,
            final JsonSerializationContext context) {
        return new JsonPrimitive(User.getCredentials().getUsername());
    }

    @Override
    public User deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) throws JsonParseException {
        PersonManager pm = PersonManager.getInstance();
        return pm.filterBy(pm.getUsers(), "Username", json.getAsString()).get(0);
    }
}