package com.example.server.global.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class StorageConfig {

	@Value("${ncp.object-storage.bucket-base-url}")
	private String baseUrl;

	@Value("${ncp.object-storage.profile-url}")
	private String profilePath;

	@Value("${ncp.object-storage.character-url}")
	private String characterPath;

	public String getProfileUrl(String fileName) {
		if (fileName == null) {
			return null;
		}
		return baseUrl + profilePath + fileName;
	}

	// 파일 여러개
	public String getCharacterUrl(String folderName) {
		if (folderName == null) {
			return null;
		}
		return baseUrl + characterPath + folderName + "/";
	}
}
