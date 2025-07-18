package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.State;

public record ChunkUploadResponse(String serverFileId,
                                  long receivedBytes,
                                  long totalBytes,

                                  boolean complete) {}
