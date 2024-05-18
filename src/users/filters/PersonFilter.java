package users.filters;

import users.Person;

/**
 * Functional interface used to filter a List of {@code Person} objects.
 */
public interface PersonFilter<T> {
    /**
     * The only method of this class. Used to check if the Person object validates the condition.
     * @param t The {@code Person} object to test
     * @param argument The condition that must be met.
     * @return {@code true} If the object met the condition.
     */
    public boolean test(Person t, T argument);
}