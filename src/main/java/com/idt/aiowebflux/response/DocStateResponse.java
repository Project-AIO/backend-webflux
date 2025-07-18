package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.State;

public record DocStateResponse(
        String docId,
        State state
) {

    public DocStateResponse(String docId, State state) {
        this.docId = docId;
        this.state = state;
    }

}
