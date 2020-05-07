package com.daily.english.app.enums;

/**
 * 兑换码状态<br/>
 *
 */
public enum CodeStateEnum {

    REDEEM_SUCCESS(1, "兑换成功"),
    REDEEM_USED(2, "兑换码已使用"),
    REDEEM_EXPIRE(3, "兑换码过期"),
    REDEEM_ERROR(4, "兑换码错误");

    private int value;
    private String name;

    CodeStateEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CodeStateEnum valueOf(int value) {
        for (CodeStateEnum i : CodeStateEnum.values()) {
            if (i.getValue() == value) {
                return i;
            }
        }
        throw new IllegalArgumentException("兑换码状态不正常");
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
