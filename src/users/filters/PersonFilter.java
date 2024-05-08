package users.filters;

import users.Person;

public interface PersonFilter<T> {
    public boolean test(Person t, T argument);
}