package com.idt.aiowebflux.dto;

import java.util.Set;

public record AccountSecret(
        String accountId,
        String pw,
        Set<String> roleNames
) {

    public static AccountSecret from(final String accountId, final String pw, final Set<String> roleNames) {
        return new AccountSecret(accountId, pw, roleNames);
    }
}
