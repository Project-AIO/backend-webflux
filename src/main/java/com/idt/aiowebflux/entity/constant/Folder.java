package com.idt.aiowebflux.entity.constant;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Deprecated
@Getter
@AllArgsConstructor
public enum Folder {
    KNOWLEDGE("knowledge_"),
    KNOWLEDGE_FOLDER("knowledge__folder_"),
    DOCUMENT("document_");
    private final String ROOT = System.getProperty("user.dir");
    private final String folderName;

    public String getFolderName(final Long id) {
        return KNOWLEDGE.folderName + id;
    }

    public String getKnowledgePath(final Long knowledgeId) {
        return KNOWLEDGE.folderName + knowledgeId;
    }

    public String getKnowledgeFolderPath(final Long knowledgeId, final Long folderId) {
        return getKnowledgePath(knowledgeId) + File.separator + KNOWLEDGE_FOLDER.folderName + folderId;
    }

    public String getDocumentFolderPath(final Long knowledgeId, final Long folderId, final Integer documentId) {
        return getKnowledgeFolderPath(knowledgeId, folderId) + File.separator + DOCUMENT.folderName + documentId;
    }

}
