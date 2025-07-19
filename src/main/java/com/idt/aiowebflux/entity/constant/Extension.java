package com.idt.aiowebflux.entity.constant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Extension {

    PDF("pdf", "application/pdf"),
    DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOC("doc", "application/msword"),
    PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    HWP("hwp", "application/hwp"),
    ;

    private final String extension;
    private final String type;

    //값을 다 set에 담아서 반환
    public static Set<String> getExtensions() {
        return Arrays.stream(Extension.values())
                .map(e -> e.extension)
                .collect(Collectors.toSet());
    }

    public static Set<String> getTypes() {
        return Arrays.stream(Extension.values())
                .map(e -> e.type)
                .collect(Collectors.toSet());
    }
}
