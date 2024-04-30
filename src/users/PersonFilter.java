package users;

public interface PersonFilter<T> {
    public boolean test(Person t, T argument);
}