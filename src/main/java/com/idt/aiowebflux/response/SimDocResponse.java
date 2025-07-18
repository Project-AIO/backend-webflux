package com.idt.aiowebflux.response;

import com.idt.aiowebflux.response.core.Pages;

import java.util.List;

public record SimDocResponse(
        SimilarityDocFileDataResponse docResponse,
        List<Pages> pages
) {
}
