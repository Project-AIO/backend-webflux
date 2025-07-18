package com.idt.aiowebflux.response;

import java.util.List;

public record KnowledgeModelConfigResponse(
        List<AiModelResponse> aiModelResponses,
        KnowledgeConfigResponse knowledgeConfigResponse

) {

}
