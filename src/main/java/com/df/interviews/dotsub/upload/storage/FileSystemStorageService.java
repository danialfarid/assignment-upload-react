package com.df.interviews.dotsub.upload.storage;

import com.df.interviews.dotsub.upload.model.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileSystemStorageService implements StorageService {
    private Path rootLocation;
    private static Map<String, FileMetadata> filesMap = new ConcurrentHashMap<>();

    @Override
    public void save(FileMetadata fileInfo, InputStream inputStream) {
        try {
            fileInfo.id = UUID.randomUUID().toString();
            Files.copy(inputStream, this.rootLocation.resolve(fileInfo.id));
            filesMap.put(fileInfo.id, fileInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Collection<FileMetadata> getAll() {
        return filesMap.values();
    }

    @Override
    public FileMetadata get(String id) {
        return filesMap.get(id);
    }

    @Override
    public Resource getAsResource(String id) {
        Path file = rootLocation.resolve(id);
        try {
            return new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        try {
            rootLocation = Paths.get("uploaded-files-storage");
            if (!rootLocation.toFile().exists()) {
                Files.createDirectory(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
