package com.idt.aiowebflux.entity.constant;

import java.util.List;
import lombok.Getter;

@Getter
public enum PermissionMask {
    READ(1), WRITE(2), EXECUTE(3);

    private final int mask;

    PermissionMask(int mask) {
        this.mask = mask;
    }

    public static List<PermissionMask> fromMask(final Integer mask) {
        return switch (mask) {
            case 1 -> List.of(READ);
            case 2 -> List.of(WRITE);
            case 3 -> List.of(READ, WRITE);
            case 4 -> List.of(EXECUTE);
            case 5 -> List.of(WRITE, EXECUTE);
            case 6 -> List.of(READ, EXECUTE);
            case 7 -> List.of(READ, WRITE, EXECUTE);
            default -> throw new IllegalArgumentException("Invalid permission mask: " + mask);
        };
    }
}
