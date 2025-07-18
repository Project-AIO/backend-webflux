package com.idt.aiowebflux.request;

import com.idt.aiowebflux.entity.constant.State;

public record DocumentDeleteRequest (
    String subscribeKey,
    String docId,
    State state
) {

}
