package com.itmuch.cloud.microservice.uploadfile;

import java.io.File;
import java.io.IOException;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	@PostMapping("/upload")
	public String handleFileUpload(
			@RequestParam(value="file",required=true)MultipartFile file) throws IOException {
		
		byte[] bytes = file.getBytes();
		File fileToSave = new File(file.getOriginalFilename());
		FileCopyUtils.copy(bytes,fileToSave);
		return fileToSave.getAbsolutePath();
	}
}
