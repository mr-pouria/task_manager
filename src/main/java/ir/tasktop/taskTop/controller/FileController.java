package ir.tasktop.taskTop.controller;

import ir.tasktop.taskTop.dto.SaveFileDto;
import ir.tasktop.taskTop.service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> saveFile(@ModelAttribute SaveFileDto file, @CurrentSecurityContext(expression = "authentication?.name") String username) throws IOException {
        return fileService.saveFile(file , username);
    }
}
