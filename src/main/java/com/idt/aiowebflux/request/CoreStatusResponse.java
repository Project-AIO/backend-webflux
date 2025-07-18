package com.idt.aiowebflux.request;

import com.idt.aiowebflux.dto.KnowledgeFolderReferenceDto;
import com.idt.aiowebflux.entity.constant.State;

public record CoreStatusResponse(
        State state,
        Long knowledgeId,
        Long folderId,
        Integer queueSize,
        String docId,
        Integer progress
) {


    public static CoreStatusResponse of(final DocumentStatusResponse response, final KnowledgeFolderReferenceDto reference) {
        return new CoreStatusResponse(
                response.state(),
                reference.knowledgeId(),
                reference.folderId(),
                response.queueSize(),
                response.docId(),
                response.progress()
        );
    }
}
