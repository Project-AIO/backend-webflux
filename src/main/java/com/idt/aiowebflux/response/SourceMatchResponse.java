package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.Homonym;
import com.idt.aiowebflux.entity.Synonym;

public record SourceMatchResponse(
        Long knowledgeId,
        Long id,
        String source,
        String match
) {
    public static SourceMatchResponse from(final Synonym synonym) {
        return new SourceMatchResponse(
                synonym.getKnowledge().getKnowledgeId(),
                synonym.getSynonymId(),
                synonym.getSource(),
                synonym.getMatch()
        );
    }

    public static SourceMatchResponse from(final Homonym homonym) {
        return new SourceMatchResponse(
                homonym.getKnowledge().getKnowledgeId(),
                homonym.getHomonymId(),
                homonym.getSource(),
                homonym.getMatch()
        );
    }
}
