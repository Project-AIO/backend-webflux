package com.idt.aiowebflux.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Getter
public class DocumentPathDto {
    private String docId;
    private String path;
}
