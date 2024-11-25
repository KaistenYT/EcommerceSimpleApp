package ks.dev.ShoppingCartApp.controller;

import ks.dev.ShoppingCartApp.dto.ImageDto;
import ks.dev.ShoppingCartApp.exceptions.ResourceNotFoundException;
import ks.dev.ShoppingCartApp.model.Image;
import ks.dev.ShoppingCartApp.response.ApiResponse;
import ks.dev.ShoppingCartApp.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private IImageService imageService;

@PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files ,@RequestParam Long productId){
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files , productId);
            return  ResponseEntity.ok(new ApiResponse("Upload Successful" , imageDtos));
        } catch (Exception e) {
           return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!" , e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
          public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = convertToByteArrayResource(image);
        return buildResponseEntity(image, resource);
    }

            private ByteArrayResource convertToByteArrayResource(Image image) throws SQLException {
            return new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
    }

             private ResponseEntity<ByteArrayResource> buildResponseEntity(Image image, ByteArrayResource resource) {
                return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));
    }


    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImageById( imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));
    }

}
