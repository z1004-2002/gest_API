package com.vetrix.gest_API.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Images")
@CrossOrigin("*")
@RequestMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

	@Autowired
	private ImageService imageService;

	@PutMapping(path = "/image")
	@Operation(summary = "Submit new image and projet Id")
	public List<ImageDao> submitImage(
			@RequestParam("file") MultipartFile[] file,
			@RequestParam("numero") Integer numero,
			@RequestParam("type") String type
	) {
		List<ImageDao> images = new ArrayList<>();
		for (MultipartFile image: file){
			log.info("Controller save Image ({})", image);
			images.add(imageService.submitImage(image, numero,type));
		}
		return images;
	}

	@GetMapping(path = "/image/id")
	@Operation(summary = "Get image by image id")
	public ImageDao fetchingImageById(@RequestParam(name = "imageId") Integer imageId) {
		log.info("Controller: Fetching Image by Id {}", imageId);
		return imageService.getImageById(imageId);
	}

	@GetMapping(path = "/projet/images")
	@Operation(summary = "Fetching images by product")
	public List<ImageDao> FetchingImageByProduct(@RequestParam(name = "numero") Integer numero) {
		log.info("Controller: Product Id {}", numero);
		return imageService.fetchingImagesByProduct(numero);
	}

	@GetMapping("/image/{projetImageName:.+}")
	@Operation(summary = "Get profile pic")
	public ResponseEntity<Resource> downloadProfileImage(@PathVariable String projetImageName,
			HttpServletRequest request) {
		Resource resource = imageService.loadProfileImage(projetImageName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Could Not Determine file ");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
}
