package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Document;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.repository.custom.CustomDocumentRepository;
import com.idt.aiowebflux.request.DocumentFileDataRequest;
import com.idt.aiowebflux.response.DocStateResponse;
import com.idt.aiowebflux.response.SimilarityDocFileDataResponse;
import com.idt.aiowebflux.response.core.CoreDocFileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String>, CustomDocumentRepository {

    @Query("""
            SELECT new com.idt.aiowebflux.response.SimilarityDocFileDataResponse(sd.similarityDocId, d.docId, sd.score, df.extension, df.path, df.fileName, df.totalPage, df.revision)
             FROM Answer a,
                SimilarityDoc sd,
                            Document d,
                    DocumentFile df
             WHERE a.answerId = sd.answer.answerId
               AND sd.document.docId = d.docId
               AND d.docId = df.document.docId
               AND a.answerId = :answerId
            """)
    List<SimilarityDocFileDataResponse> findSimDocFileDataByAnswerId(@Param("answerId") final Long answerId);


    @Query("""
                        SELECT new com.idt.aiowebflux.response.core.CoreDocFileData(d.docId, df.extension, df.path, df.fileName, df.totalPage, df.revision)
                         FROM Document d,
                                DocumentFile df
                         WHERE d.docId = df.document.docId
                           AND d.docId IN :docIds
            """)
    List<CoreDocFileData> findFileDataByDocIdsIn(@Param("docIds") final List<String> docIds);

    @Modifying
    @Query("update Document d set d.state = :state, d.progress = :progress where d.docId in :docIds")
    void updateProgress(@Param("docIds") final String docIds, @Param("state") final State state,
                        @Param("progress") final Integer progress);

    @Query("SELECT new com.idt.aiowebflux.response.DocStateResponse(d.docId, d.state) FROM Document d WHERE d.docId = :docId")
    Optional<DocStateResponse> findDocStateByDocId(@Param("docId") final String docId);

    @Query(value = "SELECT d.doc_id, d.project_folder_id, d.state, d.progress, d.upload_dt, " +
            "df.extension, df.path, df.file_name, df.total_page, df.revision, df.original_file_name " +
            "FROM tb_doc d " +
            "INNER JOIN tb_doc_file df ON d.doc_id = :docId", nativeQuery = true)
    Map<String, Object> selDocumentInfomationByDocId(@Param("docId") final String docId);

  /*  @Query("""
            SELECT new com.idt.aiowebflux.dto.KnowledgeFolderReferenceDto(k.knowledgeId, f.folderId)
             FROM Document d 
             JOIN d.folder f 
             JOIN f.knowledge k
            WHERE d.docId = :docId
            GROUP BY k.knowledgeId, f.folderId
            """)
    Optional<KnowledgeFolderReferenceDto> findKnowledgeReferenceByDocId(@Param("docId") final String docId);*/

    List<Document> getDocumentsByDocIdIn(final List<String> docIds);

    @Query("""
            SELECT new com.idt.aiowebflux.request.DocumentFileDataRequest(d.docId, df.fileName, df.extension, df.revision)
             FROM Document d,
                  DocumentFile df
             WHERE d.docId = df.document.docId
               AND d.docId IN :docIds
            """)
    List<DocumentFileDataRequest> getDocumentFileDataByDocIdIn(@Param("docIds") final List<String> docIds);

}
