package items.filters;

import items.LoanRequest;

public interface LoanRequestFilter<T> {
    public boolean test(LoanRequest t, T argument);

}
