package com.idt.aiowebflux.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idt.aiowebflux.entity.constant.State;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class DocumentInfomationDto {

    @JsonProperty(value = "doc_id")
    private String docId;
    @JsonProperty(value = "folder_id")
    private Long folderId;
    private State state;
    private Integer progress;
    @JsonProperty(value = "upload_dt")
    private String uploadDt;
    private String extension;
    private String path;
    @JsonProperty(value = "file_name")
    private String fileName;
    @JsonProperty(value = "total_page")
    private int totalPage;
    private String revision;
    @JsonProperty(value = "original_file_name")
    private String originalFileName;

    public DocumentInfomationDto(String docId, Long folderId, State state, Integer progress, String uploadDt,
                                 String extension, String path, String fileName, int totalPage, String revision,
                                 String originalFileName) {
        this.docId = docId;
        this.folderId = folderId;
        this.state = state;
        this.progress = progress;
        this.uploadDt = uploadDt;
        this.extension = extension;
        this.path = path;
        this.fileName = fileName;
        this.totalPage = totalPage;
        this.revision = revision;
        this.originalFileName = originalFileName;
    }

    public static DocumentInfomationDto from(Map<String, Object> data) {
        return new DocumentInfomationDto(
                (String) data.get("doc_id"),                          // tb_doc.doc_id
                data.get("folder_id") != null ? ((Number) data.get("folder_id")).longValue() : null,
                // tb_doc.project_folder_id (Integer)
                data.get("state") != null ? State.valueOf((String) data.get("state")) : null, // tb_doc.state (Enum)
                data.get("progress") != null ? ((Number) data.get("progress")).intValue() : null,
                // tb_doc.progress (Integer)
                data.get("upload_dt") != null ? data.get("upload_dt").toString() : null, // tb_doc.upload_dt
                (String) data.get("extension"),                       // tb_doc_file.extension
                (String) data.get("path"),                            // tb_doc_file.path
                (String) data.get("file_name"),                       // tb_doc_file.file_name
                data.get("total_page") != null ? ((Number) data.get("total_page")).intValue() : 0,
                // tb_doc_file.total_page (int)
                (String) data.get("revision"),                        // tb_doc_file.revision
                (String) data.get("original_file_name")               // tb_doc_file.original_file_name
        );
    }

    public static List<DocumentInfomationDto> from(List<Map<String, Object>> data) {
        return data.stream().map(DocumentInfomationDto::from).toList();
    }

}
