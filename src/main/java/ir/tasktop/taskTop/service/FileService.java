package ir.tasktop.taskTop.service;

import ir.tasktop.taskTop.dto.SaveFileDto;
import ir.tasktop.taskTop.model.File;
import ir.tasktop.taskTop.repo.FileRepo;
import ir.tasktop.taskTop.repo.UserRepo;
import ir.tasktop.taskTop.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileService {

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    UserRepo userRepo;

    @Autowired
    FileRepo fileRepo;

    @Value("${file.upload.dir}")
    String uploadDir;

    public ResponseEntity<?> saveFile(SaveFileDto saveFileDto, String username) throws IOException {
//        if (bindingResult.hasErrors()) {
//            return responseHandler.responseBack(null, null, bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
//        }
        String filename = String.valueOf(new Date().getTime());
        File file = new ir.tasktop.taskTop.model.File();
        file.setFileName(filename);
        file.setFileExtension(saveFileDto.getFile().getOriginalFilename().substring(saveFileDto.getFile().getOriginalFilename().lastIndexOf(".") + 1));
        file.setFileSize(saveFileDto.getFile().getSize());
        file.setCreatedBy(userRepo.findByUsername(username).get().getId());
        file.setDocName(saveFileDto.getDocName());
        file.setDocIdentifier(saveFileDto.getDocIdentifier());
        Path path = Paths.get(uploadDir + filename + "." + file.getFileExtension());
        file.setPath(path.toString());
        Files.write(path, saveFileDto.getFile().getBytes());
        fileRepo.save(file);
        return responseHandler.responseBack(null, "فایل با موفقیت ذخیره شد", null, HttpStatus.OK);
    }

}
