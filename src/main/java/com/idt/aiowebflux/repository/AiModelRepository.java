package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.AiModel;
import com.idt.aiowebflux.repository.custom.CustomAiModelRepository;
import com.idt.aiowebflux.response.AiModelResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AiModelRepository extends JpaRepository<AiModel, Long>, CustomAiModelRepository {

    Optional<AiModel> getAiModelByAiModelId(final Long aiModelId);

    void deleteAiModelsByAiModelId(final Long aiModelId);

    @Query("""
                SELECT new com.idt.aiowebflux.response.AiModelResponse(
                    a.aiModelId,
                    a.name,
                    a.provider,
                    a.description,
                    a.baseUrl
                )
                FROM AiModel a
            """)
    List<AiModelResponse> findAllAiModels();
}
