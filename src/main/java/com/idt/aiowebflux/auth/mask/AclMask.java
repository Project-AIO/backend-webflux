package com.idt.aiowebflux.auth.mask;

public record AclMask(
        boolean canRead
) {
    private static final AclMask EMPTY_MASK = new AclMask(false);

    public static AclMask getEmptyMask() {
        return EMPTY_MASK;
    }
}
