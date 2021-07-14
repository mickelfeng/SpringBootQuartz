package com.godfunc.enums;

public enum JobStatusEnum {

    PAUSE(0),
    NORMAL(1);

    private final int value;

    JobStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
