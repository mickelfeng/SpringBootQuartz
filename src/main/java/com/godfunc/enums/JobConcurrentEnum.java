package com.godfunc.enums;

public enum JobConcurrentEnum {

    DISALLOW_CONCURRENT(0),
    CONCURRENT(1);

    private final int value;

    JobConcurrentEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
