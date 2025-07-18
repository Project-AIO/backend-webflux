package com.idt.aiowebflux.request.core;

public record CoreDataRequest<T>(
        T data,
        CoreErrorRequest error
) {
    public static <T> CoreDataRequest<T> of(final T data, final CoreErrorRequest error) {
        return new CoreDataRequest<>(data, error);
    }

    public static <T> CoreDataRequest<T> from(final T data) {
        return new CoreDataRequest<>(data, null);
    }
}
