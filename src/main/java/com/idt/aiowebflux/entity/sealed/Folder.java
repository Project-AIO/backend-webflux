package com.idt.aiowebflux.entity.sealed;

import java.io.File;

public sealed interface Folder permits Folder.Knowledge, Folder.KnowledgeFolder, Folder.Document {

    String getFolderPrefix();

    @Deprecated
    // PROJECT 타입: 프로젝트 경로 생성 (한 개의 파라미터)
    final class Knowledge implements Folder {
        private static final Knowledge INSTANCE = new Knowledge();
        private static final String PREFIX = "folder_id_";

        private Knowledge() {
        }

        public static Knowledge getInstance() {
            return INSTANCE;
        }

        @Override
        public String getFolderPrefix() {
            return PREFIX;
        }

        public String getPath(Integer projectId) {
            if (projectId == null) {
                throw new IllegalArgumentException("projectId must not be null");
            }
            return PREFIX + projectId;
        }
    }

    // PROJECT_FOLDER 타입: 프로젝트 폴더 경로 생성 (두 개의 파라미터)
    final class KnowledgeFolder implements Folder {
        private static final KnowledgeFolder INSTANCE = new KnowledgeFolder();
        private static final String PREFIX = "folder_id_";

        private KnowledgeFolder() {
        }

        public static KnowledgeFolder getInstance() {
            return INSTANCE;
        }

        @Override
        public String getFolderPrefix() {
            return PREFIX;
        }

        public String getPath(final Long folderId) {
            if (folderId == null) {
                throw new IllegalArgumentException("knowledgeId and folderId must not be null");
            }
            return PREFIX + folderId + "_";
        }
    }

    @Deprecated
    // DOCUMENT 타입: 문서 경로 생성 (세 개의 파라미터)
    final class Document implements Folder {
        private static final Document INSTANCE = new Document();
        private static final String PREFIX = "document_";

        private Document() {
        }

        public static Document getInstance() {
            return INSTANCE;
        }

        @Override
        public String getFolderPrefix() {
            return PREFIX;
        }

        public String getPath(Long knowledgeId, Long folderId, String documentId) {
            if (knowledgeId == null || folderId == null || documentId == null) {
                throw new IllegalArgumentException("knowledgeId, folderId and documentId must not be null");
            }
            // PROJECT_FOLDER 타입의 getPath를 재사용하여 경로 생성
            String projectFolderPath = KnowledgeFolder.getInstance().getPath(/*projectId, */folderId);
            return projectFolderPath + File.separator + PREFIX + documentId;
        }
    }
}
