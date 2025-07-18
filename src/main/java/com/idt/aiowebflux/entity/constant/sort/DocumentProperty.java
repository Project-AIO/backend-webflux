package com.idt.aiowebflux.entity.constant.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DocumentProperty implements SortProperty {
    DOC_ID("doc_id", "docId"),
    PROJECT_FOLDER_ID("project_folder_id", "projectFolder.projectFolderId"),
    STATE("state", "state"),
    PROGRESS("progress", "progress"),
    UPLOAD_DT("upload_dt", "uploadDt");
    private final String key;
    private final String value;

    @Override
    public String key() {
        return key;
    }

    @Override
    public String path() {
        return value;
    }
}
