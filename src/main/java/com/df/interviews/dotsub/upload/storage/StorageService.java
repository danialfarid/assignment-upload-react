package com.df.interviews.dotsub.upload.storage;

import com.df.interviews.dotsub.upload.model.FileMetadata;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.Collection;

public interface StorageService {
    void save(FileMetadata fileInfo, InputStream inputStream);

    Collection<FileMetadata> getAll();

    FileMetadata get(String id);

    Resource getAsResource(String id);
}
