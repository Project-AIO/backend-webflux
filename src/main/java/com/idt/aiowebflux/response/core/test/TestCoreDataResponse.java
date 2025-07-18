package com.idt.aiowebflux.response.core.test;

import com.idt.aiowebflux.response.core.CoreErrorResponse;


public record TestCoreDataResponse<T>(
        T data,
        CoreErrorResponse error
) {
    public static <T> TestCoreDataResponse<T> of(final T data, final CoreErrorResponse error) {
        return new TestCoreDataResponse<>(data, error);
    }

    public static <T> TestCoreDataResponse<T> from(final T data) {
        return new TestCoreDataResponse<>(data, null);
    }
}
