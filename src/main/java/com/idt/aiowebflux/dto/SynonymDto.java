package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.Synonym;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SynonymDto {
    private Long synonymId;
    private Long knowledgeId;
    private String source;
    private String match;

    public static SynonymDto from(Synonym synonym) {
        return new SynonymDto(
                synonym.getSynonymId(),
                synonym.getKnowledge().getKnowledgeId(),
                synonym.getSource(),
                synonym.getMatch()
        );
    }

    public static List<SynonymDto> from(List<Synonym> synonym) {
        return synonym.stream().map(SynonymDto::from).toList();
    }
}
