package items.manager;

import items.LoanRequest;

public interface LoanRequestFilter<T> {
    public boolean test(LoanRequest t, T argument);

}
