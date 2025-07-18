package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.Homonym;
import com.idt.aiowebflux.entity.Synonym;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode
@Getter
public class SourceMatchDto {
    @NotNull
    @Size(min = 1, max = 25)
    private String source;
    @NotNull
    @Size(min = 1, max = 25)
    private String match;

    public static SourceMatchDto from(final Synonym synonym) {
        return new SourceMatchDto(
                synonym.getSource(),
                synonym.getMatch()
        );
    }

    public static SourceMatchDto from(final Homonym homonym) {
        return new SourceMatchDto(
                homonym.getSource(),
                homonym.getMatch()
        );
    }

    public static List<SourceMatchDto> from(List<Homonym> homonym) {
        return homonym.stream().map(SourceMatchDto::from).toList();
    }
}
