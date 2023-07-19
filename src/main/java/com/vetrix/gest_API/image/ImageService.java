package com.vetrix.gest_API.image;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.vetrix.gest_API.projet.Projet;
import com.vetrix.gest_API.projet.ProjetRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@NoArgsConstructor
public class ImageService {

	private ImageRepository imageRepository;
	private ProjetRepository projetRepository;

	private Path fileStorageLocationProduct;

	@Autowired
	public ImageService(
			ImageRepository imageRepository,
			ProjetRepository projetRepository,
			FileStorageProperties fileStorageProperties
	) {
		super();
		this.imageRepository = imageRepository;
		this.projetRepository = projetRepository;
		this.fileStorageLocationProduct = Paths
				.get(System.getProperty("user.dir") + fileStorageProperties.getProductDir()).toAbsolutePath()
				.normalize();
		log.info("========>Image Path = {}<========", fileStorageLocationProduct);

		try {
			Files.createDirectories(this.fileStorageLocationProduct);
		} catch (Exception ex) {
			throw new RuntimeException("Could not create the directory to upload.");
		}
	}

	public ImageDao getImageById(Integer imageId) {
		log.info("Service: Fetching Image {}", imageId);
		return imageRepository.findById(imageId).get();
	}

	public List<ImageDao> fetchingImagesByProduct(Integer projectId) {
		log.info("Service: Fetching product by Id({})", projectId);
		Projet projet = projetRepository.findById(projectId).get();
		log.info("Service: Product {}", projet);
		return imageRepository.findByProjet(projet);
	}

	public ImageDao submitImage(MultipartFile file, Integer idProjet,String type) {

		//GENERATION OF VARCHAR

		String completeName = "abe";
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "abcdefghijklmnopqrstuvxyz";

		StringBuilder s = new StringBuilder(50);
		for (int i = 0; i < 50; i++) {
			int index = (int)(str.length() * Math.random());
			s.append(str.charAt(index));
		}
		completeName = String.valueOf(s);

		//END OF GENERATION

		log.info("Image Name = {}", file.getOriginalFilename());
		Projet projet = projetRepository.findById(idProjet).get();
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		try {
			Path targetLocation = this.fileStorageLocationProduct.resolve(completeName+fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path(System.getProperty("user.dir") + "/src/main/resources/static/image/").path(completeName + fileName)
					.toUriString();
			return imageRepository.save(
					new ImageDao(null, completeName+fileName, fileDownloadUri, file.getSize(), file.getContentType(), type ,projet));
		} catch (IOException e) {
			throw new RuntimeException("Could not store file " + completeName+fileName + ". Please try again!", e);
		}
	}

	public Resource loadProfileImage(String fileName) {
		log.info("Load File = {} Successfully", fileName);
		try {
			Path filePath = this.fileStorageLocationProduct.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new RuntimeException("Le fichier: " + fileName + " est introuvable");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Le fichier: " + fileName + " est introuvable");
		}
	}

	public void deleteById(Integer id){
		imageRepository.deleteById(id);
	}
}
