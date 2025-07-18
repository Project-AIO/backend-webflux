package com.idt.aiowebflux.response;


public record DocumentFileResponse(
        DocumentDataResponse documentDataResponse
) {
    public static DocumentFileResponse of(DocumentDataResponse documentDataResponse) {
        return new DocumentFileResponse(documentDataResponse);
    }
}
