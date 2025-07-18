package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.Knowledge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter

@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeDto {
    private Long knowledgeId;
    private String name;
    private String description;
    private String accountId;
    private String accountName;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    public static KnowledgeDto from(final Knowledge knowledge) {
        return new KnowledgeDto(
                knowledge.getKnowledgeId(),
                knowledge.getName(),
                knowledge.getDescription(),
                knowledge.getAccount().getAccountId(),
                knowledge.getAccount().getName(),
                knowledge.getCreateDt(),
                knowledge.getUpdateDt()
        );
    }

    public static List<KnowledgeDto> from(List<Knowledge> knowledge) {
        return knowledge.stream()
                .map(KnowledgeDto::from)
                .toList();
    }
}
