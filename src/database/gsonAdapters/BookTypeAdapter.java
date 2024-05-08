package database.gsonAdapters;

/**
 * This code was found on this link:
 * https://howtodoinjava.com/gson/gson-typeadapter-for-inaccessibleobjectexception/
 */

import java.lang.reflect.Type;

import items.Book;
import items.managers.ArchiveManager;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class BookTypeAdapter implements JsonSerializer<Book>, JsonDeserializer<Book> {


    @Override
    public JsonElement serialize(final Book book, final Type typeOfSrc,
            final JsonSerializationContext context) {
        return new JsonPrimitive(book.getID());
    }

    @Override
    public Book deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) throws JsonParseException {
        ArchiveManager am = ArchiveManager.getInstance();
        try {
            Book book = am.searchBook(json.getAsString()).get(0);
            return book;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}