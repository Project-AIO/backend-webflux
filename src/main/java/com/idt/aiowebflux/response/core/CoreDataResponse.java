package com.idt.aiowebflux.response.core;

public record CoreDataResponse<T>(
        T data,
        CoreErrorResponse error
) {
    public static <T> CoreDataResponse<T> of(final T data, final CoreErrorResponse error) {
        return new CoreDataResponse<>(data, error);
    }

    public static <T> CoreDataResponse<T> from(final T data) {
        return new CoreDataResponse<>(data, null);
    }
}
