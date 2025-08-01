package com.idt.aiowebflux.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public enum DomainExceptionCode {
    FOLDER(1000, "정의되지 않은 에러입니다."),
    FOLDER_NOT_FOUND(FOLDER.code + 1, "프로젝트 폴더를 찾을 수 없습니다."),
    FOLDER_DELETE_FAILED(FOLDER.code + 2, "폴더 삭제에 실패했습니다. 지식 아카이브에 사용되고 있는 문서가 존재합니다. %s \n"),
    FOLDER_ALREADY_EXISTS(FOLDER.code + 3, "이미 존재하는 폴더명입니다."),


    KNOWLEDGE(1500, "정의되지 않은 에러입니다."),
    KNOWLEDGE_NOT_FOUND(KNOWLEDGE.code + 1, "프로젝트를 찾을 수 없습니다."),
    KNOWLEDGE_NAME_DUPLICATED(KNOWLEDGE.code + 2, "이미 존재하는 프로젝트명입니다."),

    DOCUMENT(2000, "정의되지 않은 에러입니다."),
    DOCUMENT_TYPE_INVALID(DOCUMENT.code + 1, "MIME Type이 올바르지 않습니다."),
    DOCUMENT_NOT_FOUND(DOCUMENT.code + 2, "문서를 찾을 수 없습니다."),
    DOCUMENT_IDS_NOT_FOUND(DOCUMENT.code + 2, "문서를 찾을 수 없습니다. 문서 ID: %s"),

    KNOWLEDGE_REFERENCE_NOT_FOUND(DOCUMENT.code + 3, "프로젝트 참조를 찾을 수 없습니다."),

    FILE(2500, "정의되지 않은 에러입니다."),
    FILE_READ_FAILED(FILE.code + 1, "파일을 읽는데 실패했습니다."),
    FILE_SIZE_EXCEEDED(FILE.code + 2, "파일 크기가 %dMB를 초과했습니다."),
    FOLDER_CREATION_FAILED(FILE.code + 3, "폴더를 찾을 수 없습니다."),
    FOLDER_EXISTS(FILE.code + 4, "폴더가 이미 존재합니다."),
    FILE_NAME_DUPLICATED(FILE.code + 5, "중복된 파일명이 있습니다."),
    FILE_NOT_FOUND(FILE.code + 6, "파일을 찾을 수 없습니다."),
    FILE_EXTENSION_INVALID(FILE.code + 7, "파일 확장자가 올바르지 않습니다 -> %s"),
    FILE_EXTENSION_NOT_ALLOWED(FILE.code + 8, "허용되지 않는 파일 확장자입니다."),
    FILE_NAME_IS_NULL(FILE.code + 9, "파일명이 null입니다."),
    FILE_CONTENT_TYPE_INVALID(FILE.code + 10, "파일의 Content-Type이 올바르지 않습니다."),
    ZIP_FILE_NOT_SUPPORTED(FILE.code + 11, "ZIP 파일은 지원하지 않습니다."),
    SERVER_FILE_NOT_FOUND(FILE.code + 12, "서버에서 파일을 찾을 수 없습니다."),
    UPLOAD_OFFSET_MISMATCH(FILE.code + 13, "업로드 오프셋이 일치하지 않습니다."),
    //악성 파일
    FILE_VIRUS_DETECTED(FILE.code + 12, "파일에 바이러스가 감지되었습니다."),
    BULK_FILE_UPLOAD_NOT_COMPLETE(FILE.code + 13, "대량 파일 업로드가 완료되지 않았습니다."),
    FILE_VALIDATION_FAILED(FILE.code + 14, "파일 유효성 검사에 실패했습니다. %s"),
    FILE_SIZE_MISMATCH(FILE.code + 15, "파일의 신고 크기와 실제 크기가 일치하지 않습니다. 신고 크기: %d, 실제 크기: %d"),
    DOCUMENT_DELETE_FAILED(FILE.code + 16, "문서 삭제에 실패했습니다."),


    CONFIGURATION_KNOWLEDGE(3000, "정의되지 않은 에러입니다."),
    CONFIGURATION_KNOWLEDGE_NOT_FOUND(CONFIGURATION_KNOWLEDGE.code + 1, "설정 지식을 찾을 수 없습니다."),
    CONFIGURATION_KNOWLEDGE_MODEL_NOT_FOUND(CONFIGURATION_KNOWLEDGE.code + 2, "설정 지식 모델을 찾을 수 없습니다."),

    AI_MODEL(3500, "정의되지 않은 에러입니다."),
    AI_MODEL_NOT_FOUND(AI_MODEL.code + 1, "언어 모델을 찾을 수 없습니다."),
    DUPLICATE_AI_MODEL(AI_MODEL.code + 2, "똑같은 Feature의 모델이 한 개 이상 있습니다."),
    MODEL_API_KEY_NOT_FOUND(AI_MODEL.code + 3, "API Key를 찾을 수 없습니다."),
    GEN_AI_MODEL_NOT_FOUND(AI_MODEL.code + 4, "Gen AI 모델을 찾을 수 없습니다."),
    RERK_AND_EMB_MODEL_MORE_THAN_ONE(AI_MODEL.code + 5, "RERK 모델과 Embedding 모델은 각각 하나만 설정할 수 있습니다."),
    RERK_OR_EMB_AI_MODEL_ALREADY_EXISTS(AI_MODEL.code + 5, "Rerank 또는 Embedding 모델이 이미 존재합니다."),

    ANSWER(4000, "정의되지 않은 에러입니다."),
    ANSWER_NOT_FOUND(ANSWER.code + 1, "답변을 찾을 수 없습니다."),
    CONVERSATION(4500, "정의되지 않은 에러입니다."),
    CONVERSATION_NOT_FOUND(CONVERSATION.code + 1, "대화를 찾을 수 없습니다."),

    ACCOUNT(5000, "정의되지 않은 에러입니다."),
    ACCOUNT_NOT_FOUND(ACCOUNT.code + 1, "계정을 찾을 수 없습니다."),
    KNOWLEDGE_ACCOUNT_NOT_FOUND(ACCOUNT.code + 2, "프로젝트 계정을 찾을 수 없습니다."),
    KNOWLEDGE_ACCOUNT_ALREADY_EXISTS(ACCOUNT.code + 3, "이미 존재하는 프로젝트 계정입니다. %s"),
    //문서에 대한 권한이 없습니다 에 대한 에러 코드
    ACCOUNT_DOCUMENT_NOT_AUTHORIZED(ACCOUNT.code + 4, "해당 계정은 이 문서에 대한 권한이 없습니다."),
    ACCOUNT_KNOWLEDGE_NOT_AUTHORIZED(ACCOUNT.code + 5, "해당 계정은 이 프로젝트에 대한 권한이 없습니다."),


    SEARCH(5500, "정의되지 않은 에러입니다."),
    INVALID_SEARCH_FIELD(SEARCH.code + 1, "검색 필드가 올바르지 않습니다."),
    SYNONYM(6000, "정의되지 않은 에러입니다."),
    SYNONYM_ALREADY_EXISTS(SYNONYM.code + 1, "이미 존재하는 동의어입니다."),
    SYNONYM_NOT_FOUND(SYNONYM.code + 2, "동의어를 찾을 수 없습니다."),
    HOMONYM(6500, "정의되지 않은 에러입니다."),
    HOMONYM_ALREADY_EXISTS(HOMONYM.code + 1, "이미 존재하는 동의어입니다."),
    HOMONYM_NOT_FOUND(HOMONYM.code + 2, "동의어를 찾을 수 없습니다."),
    DICTIONARY(6700, "정의되지 않은 에러입니다."),
    DICTIONARY_ALREADY_EXISTS(DICTIONARY.code+1, "%s"),
    DICTIONARY_NOT_FOUND(DICTIONARY.code + 2, "%s"),

    KNOWLEDGE_AI_MODEL(7000, "정의되지 않은 에러입니다."),
    KNOWLEDGE_AI_MODEL_NOT_FOUND(KNOWLEDGE_AI_MODEL.code + 1, "모델 프리셋을 찾을 수 없습니다."),
    USER_ACT(7500, "정의되지 않은 에러"),
    SELECT_EMB_MODEL_NAME(USER_ACT.code + 1, "Embedding Model 이름을 먼저 설정해 주세요."),

    SIMILARITY(8000, "정의되지 않은 에러입니다."),
    SIMILARITY_NOT_FOUND(SIMILARITY.code + 1, "유사도를 찾을 수 없습니다."),

    QUESTION(8500, "정의되지 않은 에러입니다."),
    QUESTION_NOT_FOUND(QUESTION.code + 1, "질문을 찾을 수 없습니다."),

    DOCUMENT_FILE(9000, "정의되지 않은 에러입니다."),
    DOCUMENT_FILE_NOT_FOUND(DOCUMENT_FILE.code + 1, "문서 파일을 찾을 수 없습니다."),
    DOCUMENT_FILE_EXTENSION_NOT_EQUAL(DOCUMENT_FILE.code + 2, "문서 파일 확장자가 일치하지 않습니다."),
    DOCUMENT_FILE_NAME_DOES_NOT_HAVE_EXTENSION(DOCUMENT_FILE.code + 3, "문서 파일명이 확장자를 포함하지 않습니다."),

    CLIENT(9500, "정의되지 않은 에러입니다."),
    CLIENT_NOT_FOUND(CLIENT.code + 1, "클라이언트를 찾을 수 없습니다."),
    ADMIN(10000, "정의되지 않은 에러입니다."),
    ADMIN_NOT_FOUND(ADMIN.code + 1, "관리자를 찾을 수 없습니다."),

    KNOWLEDGE_CFG(10500, "정의되지 않은 에러입니다."),
    KNOWLEDGE_CFG_NOT_FOUND(KNOWLEDGE_CFG.code + 1, "프로젝트 설정을 찾을 수 없습니다."),

    SORT_PROPERTY(11000, "정의되지 않은 에러입니다."),
    SORT_PROPERTY_NOT_FOUND(SORT_PROPERTY.code + 1, "정렬 속성을 찾을 수 없습니다."),

    JWT(11500, "정의되지 않은 에러입니다."),
    JWT_TOKEN_NOT_FOUND(JWT.code + 1, "JWT 토큰을 찾을 수 없습니다. %s"),
    JWT_TOKEN_EXPIRED(JWT.code + 2, "JWT 토큰이 만료되었습니다. %s"),
    JWT_TOKEN_CLAIM_NOT_FOUND(JWT.code + 3, "JWT 토큰의 클레임을 찾을 수 없습니다."),
    JWT_MAL_FORMED(JWT.code + 4, "잘못된 형식의 JWT 토큰입니다. %s"),
    JWT_UNSUPPORTED(JWT.code + 5, "지원되지 않는 JWT 토큰입니다."),
    ILLEGAL_JWT_TOKEN(JWT.code + 6, "잘못된 JWT 토큰입니다."),
    NO_JWT_COOKIE(JWT.code + 7, "JWT 쿠키 정보가 없습니다."),
    JWT_TAMPERED(JWT.code + 8, "JWT 토큰이 변조되었습니다. %s"),
    JWT_BLACKLISTED(JWT.code + 9, "JWT 토큰이 블랙리스트에 등록되었습니다. %s"),

    USER(12000, "정의되지 않은 에러입니다."),
    USER_NOT_FOUND(USER.code + 1, "사용자를 찾을 수 없습니다."),
    USER_PASSWORD_MISMATCH(USER.code + 2, "사용자 비밀번호가 일치하지 않습니다."),
    USER_LICENSE_KEY_VALIDATION_FAILED(USER.code + 3, "라이선스 키가 유효하지 않습니다."),
    USER_LICENSE_KEY_EXPIRED(USER.code + 4, "라이선스 키가 만료되었습니다."),

    SORTING(12500, "정의되지 않은 에러입니다."),
    SORTING_NOT_FOUND(SORTING.code + 1, "정렬 조건을 찾을 수 없습니다. %s"),

    ROLE(13000, "정의되지 않은 에러입니다."),
    ROLE_NOT_FOUND(ROLE.code + 1, "역할을 찾을 수 없습니다."),
    ACCOUNT_ROLE_NOT_FOUND(ROLE.code + 2, "계정 역할을 찾을 수 없습니다."),
    ACCOUNT_ROLE_ALREADY_EXISTS(ROLE.code + 3, "이미 존재하는 계정 역할입니다."),
    ROLE_NAME_DUPLICATED(ROLE.code + 4, "이미 존재하는 역할명입니다."),
    ROLE_NAME_INVALID(ROLE.code + 5, "역할 이름이 올바르지 않습니다. %s"),

    DATE_SEARCH(13500, "정의되지 않은 에러입니다."),
    DATE_SEARCH_INVALID(DATE_SEARCH.code + 1, "날짜 검색 형식이 올바르지 않습니다."),
    PERMISSION(14000, "정의되지 않은 에러입니다."),
    PERMISSION_NOT_FOUND(PERMISSION.code + 1, "권한을 찾을 수 없습니다."),
    ROLE_PERMISSION(14500, "정의되지 않은 에러입니다."),
    ROLE_PERMISSION_NOT_FOUND(ROLE_PERMISSION.code + 1, "역할 권한을 찾을 수 없습니다."),
    ROLE_PERMISSION_USED(ROLE_PERMISSION.code + 2, "역할 권한이 사용 중입니다. 삭제할 수 없습니다. %s"),

    ACL_ENTRY(15000, "정의되지 않은 에러입니다."),
    ACL_ENTRY_NOT_FOUND(ACL_ENTRY.code + 1, "ACL 엔트리를 찾을 수 없습니다."),
    ACL_RESOURCE_NOT_FOUND(ACL_ENTRY.code + 2, "ACL 리소스를 찾을 수 없습니다."),


    MODEL_CREDENTIAL(15500, "정의되지 않은 에러입니다."),
    MODEL_CREDENTIAL_NOT_FOUND(MODEL_CREDENTIAL.code + 1, "모델 자격 증명을 찾을 수 없습니다."),

    ACCESS_DENIED(16000, "정의되지 않은 에러입니다."),
    ACCESS_DENIED_NOT_AUTHORIZED(ACCESS_DENIED.code + 1, "접근이 거부되었습니다. 권한이 없습니다. -> %s"),

    ;



    private final int code;
    private final String message;

    public DomainException newInstance() {
        return new DomainException(code, message);
    }

    public DomainException newInstance(Throwable ex) {
        return new DomainException(code, message, ex);
    }

    public DomainException newInstance(Object... args) {
        return new DomainException(code, String.format(message, args), args);
    }

    public DomainException newInstance(Throwable ex, Object... args) {
        return new DomainException(code, String.format(message, args), ex, args);
    }

    public void invokeBySupplierCondition(Supplier<Boolean> condition) {
        if (condition.get()) {
            throw new DomainException(code, message);
        }
    }

    public void invokeByCondition(boolean condition) {
        if (condition) {
            throw new DomainException(code, message);
        }
    }
}