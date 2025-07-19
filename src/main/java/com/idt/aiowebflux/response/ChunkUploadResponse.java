package com.idt.aiowebflux.response;

public record ChunkUploadResponse(String serverFileId,
                                  long receivedBytes,
                                  long totalBytes,

                                  boolean complete) {
}
