package com.idt.aiowebflux.controller;

import com.idt.aiowebflux.dto.FileProgress;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.registry.ProgressRegistry;
import com.idt.aiowebflux.request.CreateUploadSessionRequest;
import com.idt.aiowebflux.response.CreateUploadSessionResponse;
import com.idt.aiowebflux.service.ProgressService;
import com.idt.aiowebflux.session.UploadSession;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UploadSessionController {

    private final ProgressRegistry registry;
    @Operation(summary = """
            벌크 업로드 세션 생성 API
            """, description = """
               [벌크 업로드 세션 생성]
               
               전송하려는 file들의 파일 이름과 사이즈를 바이트로 보낼 것
               client_file_id는 클라이언트가 관리하는 파일 ID로 UUID를 사용해서 생성해도 됨 이건 Indexed Db에 관리하지 않아도됨
               client_file_id는 추후 백엔드 서버에서 server_file_id를 생성하는 데 사용됨
               이 api의 response로 오는 각 파일별 server_file_id는 Indexed Db에 저장해서 사용해야 함
              
            """)
    @PostMapping(
            path = "/upload-sessions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<CreateUploadSessionResponse> createSession(
            @Validated @RequestBody final CreateUploadSessionRequest req) {

        // 벌크 업로드 세션에 대한 ID 생성
        final String uploadId = UUID.randomUUID().toString();

        UploadSession session = new UploadSession(uploadId, req.folderId(), State.NEW);

        // 요청 파일 목록 → RegisteredFile 목록 생성
        final List<CreateUploadSessionResponse.RegisteredFile> registered =
                req.files().stream()
                        .map(fm -> {
                            // 클라이언트가 보내는 각 파일에 대한 고유 ID 생성
                            final String serverFileId = UUID.randomUUID().toString();
                            // 세션에 파일 등록
                            session.addFile(new FileProgress(
                                    serverFileId,
                                    fm.clientFileId(),
                                    fm.filename(),
                                    fm.size()
                            ));
                            return new CreateUploadSessionResponse.RegisteredFile(
                                    fm.clientFileId(),
                                    serverFileId,
                                    fm.filename(),
                                    fm.size()
                            );
                        })
                        .toList();

        registry.create(session);   // emitter 포함 세션 등록

        return Mono.just(new CreateUploadSessionResponse(uploadId, registered));
    }
}