package kz.com.aven.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/images")
public class ImageController {
    String fileBasePath = "C:/Users/ASUS/Downloads/images";

    @PostMapping("/upload")
    public ResponseEntity uploadToLocalFileSystem(@RequestParam("image") MultipartFile file) {
        String fileName = StringUtils.cleanPath (file.getOriginalFilename ());
        Path path = Paths.get (fileBasePath + fileName);
        try {
            Files.copy (file.getInputStream (), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace ();
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath ()
                .path ("/images/download/")
                .path (fileName)
                .toUriString ();
        return ResponseEntity.ok (fileDownloadUri);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
        Path path = Paths.get (fileBasePath + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource (path.toUri ());
        } catch (MalformedURLException e) {
            e.printStackTrace ();
        }
        return ResponseEntity.ok ()
                .contentType (MediaType.parseMediaType ("application/octet-stream"))
                .header (HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename () + "\"")
                .body (resource);
    }

}