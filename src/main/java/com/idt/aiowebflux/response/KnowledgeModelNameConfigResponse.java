package com.idt.aiowebflux.response;

import java.util.List;

public record KnowledgeModelNameConfigResponse(
        List<AiModelNameResponse> aiModelNameResponses,
        KnowledgeConfigResponse knowledgeConfigResponse

) {

}
