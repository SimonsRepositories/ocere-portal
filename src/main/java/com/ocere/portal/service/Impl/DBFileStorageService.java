package com.ocere.portal.service.Impl;

import com.ocere.portal.exception.FileStorageException;
import com.ocere.portal.exception.MyFileNotFoundException;
import com.ocere.portal.model.DBFile;
import com.ocere.portal.repository.DBFileRepository;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    @Autowired
    private UserService userService;

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            dbFile.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile storeFileWithPrincipal(MultipartFile file, Principal principal) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            dbFile.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            dbFile.setAuthor(userService.findByEmail(principal.getName()));

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
