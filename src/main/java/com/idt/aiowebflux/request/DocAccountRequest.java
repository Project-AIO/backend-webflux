package com.idt.aiowebflux.request;

import java.util.List;

public record DocAccountRequest(
        List<String> accountIds
) {
}
