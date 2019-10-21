package com.ocere.portal.controller;

import com.ocere.portal.model.DBFile;
import com.ocere.portal.service.Impl.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/froala")
public class FroalaController {

    private DBFileStorageService dbFileStorageService;

    @Autowired
    public FroalaController(DBFileStorageService dbFileStorageService) {
        this.dbFileStorageService = dbFileStorageService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        return "{ \"link\": \"/file/" + dbFile.getId() + "\" }";
    }
}
