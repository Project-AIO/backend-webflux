package com.idt.aiowebflux.repository.custom.condition;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;

import static com.idt.aiowebflux.entity.QDocument.document;
import static com.idt.aiowebflux.entity.QDocumentFile.documentFile;

@Getter
public enum DocumentSearchField {
    SOURCE_NAME("sourceName") {
        @Override
        public BooleanExpression buildExpression(String input) {
            return documentFile.path.like("%" + input + "%");
        }
    },
    FILE_NAME("fileName") {
        @Override
        public BooleanExpression buildExpression(String input) {
            return documentFile.fileName.like("%" + input + "%");
        }
    },
    TOTAL_PAGES("totalPages") {
        @Override
        public BooleanExpression buildExpression(String input) {
            return documentFile.totalPage.like("%" + input + "%");
        }
    },
    FILE_SIZE("fileSize") {
        @Override
        public BooleanExpression buildExpression(String input) {
            return documentFile.fileSize.like("%" + input + "%");
        }
    },
    STATE("state") {
        @Override
        public BooleanExpression buildExpression(String input) {
            return document.state.stringValue().contains(input);
        }
    },
    UPLOAD_DATE("uploadDate") {
        @Override
        public BooleanExpression buildExpression(String input) {
            return document.uploadDt.stringValue().contains(input);
        }
    };

    private final String fieldName;

    DocumentSearchField(String fieldName) {
        this.fieldName = fieldName;
    }

    //일치하는 상수 반환
    public static DocumentSearchField fromString(String searchField) {
        for (DocumentSearchField field : values()) {
            if (field.getFieldName().equalsIgnoreCase(searchField)) {
                return field;
            }
        }
        throw new IllegalArgumentException("Invalid search field: " + searchField);
    }

    public abstract BooleanExpression buildExpression(String input);
}
