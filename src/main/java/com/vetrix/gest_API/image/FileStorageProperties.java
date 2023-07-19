package com.vetrix.gest_API.image;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "data")
public class FileStorageProperties {
	private String productDir;
}