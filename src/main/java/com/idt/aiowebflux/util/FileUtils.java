package com.idt.aiowebflux.util;

import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.exception.InvalidFileNameException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Slf4j
@Service
public class FileUtils {
    public static final String DOC_ROOT = File.separator + "aio";
    public static final String ROOT_PATH = System.getProperty("user.dir");
    public static final Path TEMP_PATH = Paths.get(File.separator + "aio" + File.separator + "temp").toAbsolutePath()
            .normalize();

    public FileUtils() {
        // 임시 저장소는 고정 경로로 사용
        try {
            Files.createDirectories(TEMP_PATH);
        } catch (IOException e) {
            throw new RuntimeException("임시 저장소 생성 실패", e);
        }
    }


    /**
     * MultipartFile을 고유한 staging 디렉토리에 저장
     */
    public Path storeTempFile(final MultipartFile file, final String fileName) {
        try {
            Path stagingDir = Files.createDirectories(TEMP_PATH.resolve(fileName));

            try (InputStream is = file.getInputStream()) {
                Files.copy(is, stagingDir, StandardCopyOption.REPLACE_EXISTING);
            }
            return stagingDir;
        } catch (FileAlreadyExistsException f) {
            throw new RuntimeException("파일이 이미 존재합니다 -> " + f.getMessage(), f);
        } catch (IOException e) {
            throw new RuntimeException("임시 파일 저장 실패 -> " + e.getMessage(), e);
        }
    }

    /**
     * staging 디렉토리에서 지정된 경로로 파일 이동
     */
    public void moveToPermanent(Path stagingDir,
                                String originalFileName,
                                Path permanentDir,
                                String newFileName) {

        // 1. 경로 준비
        // 1. 원본‧대상 경로 계산
        Path source = stagingDir.resolve(originalFileName);
        if (!Files.isRegularFile(source)) {
            throw new IllegalArgumentException("스테이징에 "
                    + originalFileName + " 파일이 없습니다.");
        }

        try {

            try {

                // 2-1. 동일 파티션이면 rename 수준으로 원자 이동
                Files.move(source, permanentDir,
                        StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                // 2-2. 파티션이 다를 때는 copy → delete 폴백
                Files.copy(source, permanentDir,
                        StandardCopyOption.REPLACE_EXISTING);
                Files.delete(source);
            }

        } catch (IOException ioe) {
            throw new UncheckedIOException("파일 이동 실패", ioe);
        }
    }

    private void deleteDirectoryRecursively(Path path) {
        try {
            if (Files.isDirectory(path)) {
                Files.list(path).forEach(this::deleteDirectoryRecursively);
            }
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }


    @Transactional
    public String createFolder(final String folderName) {
        // 루트에 폴더 생성
        // 루트 디렉토리 경로 설정
        final String rootPath = ROOT_PATH + DOC_ROOT;

        // 새 폴더 경로 설정
        final File newFolder = new File(rootPath, folderName);

        // 폴더 생성
        if (!newFolder.exists()) {
            if (newFolder.mkdirs()) {
                log.info("폴더 생성 성공: " + newFolder.getAbsolutePath());
            }
        }
        return newFolder.getAbsolutePath();
    }

    public String buildFilePath(final String path, final String fileName, final String extension) {
        return path + File.separator + fileName + "." + extension;
    }

    public FileSystemResource getFileSystemResource(final String filePath) {
        // 특정 폴더 내의 파일 경로 구성
        final Path path = Paths.get(filePath);

        // Resource 생성 (파일 시스템 기반)
        final FileSystemResource resource = new FileSystemResource(path);

        // 파일 존재 여부 체크
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다: " + path);
        }

        return resource;
    }

    public Resource getFileFromPath(String fullPath) {
        // 특정 폴더 내의 파일 경로 구성
        Path filePath = Paths.get(fullPath);

        // Resource 생성 (파일 시스템 기반)
        Resource resource = new FileSystemResource(filePath);

        // 파일 존재 여부 체크
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다: " + filePath);
        }

        return resource;
    }


    public void saveFile(final MultipartFile file, final String filePath, final String fileName,
                         final String extension) {

        try {
            // 대상 경로 (폴더가 이미 존재한다고 가정)
            Path targetDir = Paths.get(filePath);

            if (fileName == null || fileName.isEmpty()) {
                throw new InvalidFileNameException("파일 이름이 비어있거나 null입니다.");
            }

            // 대상 파일 경로 생성
            final Path targetFile = targetDir.resolve(String.format(fileName + ".%s", extension));

            // 파일이 이미 존재하는지 체크
            if (Files.exists(targetFile)) {
                throw DomainExceptionCode.FILE_NAME_DUPLICATED.newInstance();
            }

            // 파일 복사
            Files.copy(file.getInputStream(), targetFile);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }


    public byte[] convertResourceToBytes(final Resource resource) {
        try (InputStream inputStream = resource.getInputStream();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            String fileName = resource.getFilename();
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            return buffer.toByteArray();
        } catch (Exception e) {
            log.error("파일을 읽는 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("파일을 읽는 중 오류 발생", e);
        }
    }


    public long getFileSize(final MultipartFile file) {
        return file.getSize();
    }

    public Resource loadFileAsResource(final String fullPath) {
        Path filePath = Paths.get(fullPath);
        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다: " + fullPath);
        }
        return resource;
    }
}
