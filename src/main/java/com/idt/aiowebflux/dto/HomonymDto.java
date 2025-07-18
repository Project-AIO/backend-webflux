package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.Homonym;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomonymDto {
    private Long homonymId;
    private Long knowledgeId;
    private String source;
    private String match;

    public static HomonymDto from(Homonym homonym) {
        return new HomonymDto(
                homonym.getHomonymId(),
                homonym.getKnowledge().getKnowledgeId(),
                homonym.getSource(),
                homonym.getMatch()
        );
    }

    public static List<HomonymDto> from(List<Homonym> homonym) {
        return homonym.stream().map(HomonymDto::from).toList();
    }
}
