package com.challenge.banktransactions.enums;

/**
 * Channel enum.
 */
public enum ChannelEnum {
    CLIENT, ATM, INTERNAL;

    @Override
    public String toString() {
        return this.name();
    }

    public static ChannelEnum fromString(final String stringValue) {
        return ChannelEnum.valueOf(stringValue);
    }
}
