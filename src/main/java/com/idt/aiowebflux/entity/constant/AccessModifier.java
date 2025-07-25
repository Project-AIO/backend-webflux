package com.idt.aiowebflux.entity.constant;

import lombok.Getter;

@Getter
public enum AccessModifier {
    PRIVATE, PUBLIC;
    public static AccessModifier fromString(String value) {
        for (AccessModifier modifier : AccessModifier.values()) {
            if (modifier.name().equalsIgnoreCase(value)) {
                return modifier;
            }
        }
        throw new IllegalArgumentException("Unknown access modifier: " + value);
    }
}
