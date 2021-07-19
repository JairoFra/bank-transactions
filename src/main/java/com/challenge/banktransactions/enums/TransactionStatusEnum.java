package com.challenge.banktransactions.enums;

/**
 * Transaction Status enum.
 */
public enum TransactionStatusEnum {
    PENDING, SETTLED, FUTURE, INVALID;

    @Override
    public String toString() {
        return this.name();
    }

    public static TransactionStatusEnum fromString(final String stringValue) {
        return TransactionStatusEnum.valueOf(stringValue);
    }
}
