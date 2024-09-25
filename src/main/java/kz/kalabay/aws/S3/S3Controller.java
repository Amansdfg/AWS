package kz.kalabay.aws.S3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    @Autowired
    private S3Service s3Service;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String key = file.getOriginalFilename();
        try {
            Path tempFile = Files.createTempFile("upload-", key);
            Files.write(tempFile, file.getBytes());
            s3Service.uploadFile(key, tempFile);
            Files.deleteIfExists(tempFile);
            return ResponseEntity.ok("File uploaded successfully to S3.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("File upload failed.");
        }
    }
    @GetMapping("/files")
    public ResponseEntity<List<String>> listFiles() {
        List<String> fileList = s3Service.listFiles();
        return ResponseEntity.ok(fileList);
    }
}
