package top.dddclub.payroll.payrollcontext.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    private static final int SCALE = 2;
    private final BigDecimal value;
    private final Currency currency;

    public static Money of(double value) {
        return new Money(value, Currency.RMB);
    }

    public static Money of(double value, Currency currency) {
        return new Money(value, currency);
    }

    private Money(double value, Currency currency) {
        this.value = toBigDecimal(value).setScale(SCALE);
        this.currency = currency;
    }

    private Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Currency currency() {
        return this.currency;
    }

    public static Money zero() {
        return zero(Currency.RMB);
    }

    public static Money zero(Currency currency) {
        return new Money(0d, currency);
    }

    public Money add(Money money) {
        return new Money(value.add(money.value).setScale(SCALE), currency);
    }

    public Money subtract(Money money) {
        return new Money(value.subtract(money.value).setScale(SCALE), currency);
    }

    public Money multiply(double factor) {
        BigDecimal factorDecimal = toBigDecimal(factor);
        return new Money(value.multiply(factorDecimal).setScale(SCALE), currency);
    }

    public Money divide(double multiplicand) {
        BigDecimal divided = toBigDecimal(multiplicand);
        return new Money(value.divide(divided, SCALE, BigDecimal.ROUND_DOWN), currency);
    }

    private BigDecimal toBigDecimal(double factor) {
        return new BigDecimal(Double.toString(factor));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(value, money.value) &&
                currency == money.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    @Override
    public String toString() {
        return "Money {" +
                "face value = " + value +
                ", currency = " + currency +
                '}';
    }
}