package com.idt.aiowebflux.grpc;

import com.idt.aio.grpc.DeleteDocumentsRequest;
import com.idt.aio.grpc.DeleteDocumentsResponse;
import com.idt.aio.grpc.DocumentServiceGrpc;
import com.idt.aio.grpc.UpdateDocumentNameRequest;
import com.idt.aio.grpc.UpdateDocumentNameResponse;
import com.idt.aiowebflux.service.DocumentService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
@Slf4j
@GrpcService
public class DocumentGrpcService extends DocumentServiceGrpc.DocumentServiceImplBase{
    private final DocumentService documentService;
    @Override
    public void deleteDocuments(DeleteDocumentsRequest request,
                                StreamObserver<DeleteDocumentsResponse> responseObserver) {

        final int deletedFilesCount = documentService.deleteDocumentFiles(request.getPathsList());

        DeleteDocumentsResponse reply = DeleteDocumentsResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Deleted " + deletedFilesCount + " files")
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void updateDocumentName(UpdateDocumentNameRequest request, StreamObserver<UpdateDocumentNameResponse> responseObserver) {
        documentService.updateDocumentName(request.getId(), request.getNewName(), request.getPath());

        UpdateDocumentNameResponse reply = UpdateDocumentNameResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Updated document name to " + request.getNewName())
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
