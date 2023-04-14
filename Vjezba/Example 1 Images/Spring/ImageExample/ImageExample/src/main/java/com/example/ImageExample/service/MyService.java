package com.example.ImageExample.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StreamUtils;

@Service
public class MyService {
	//myservice because service conflicts with the annotation
	private static final String FILE_SYSTEM_IMAGES = System.getProperty("user.dir") + File.separator + "images";
	
	public boolean saveFile(MultipartFile f) {
		boolean flag = false;
		
		try {
			InputStream initialStream = f.getInputStream();
			
			byte[] buffer = new byte[initialStream.available()];
			initialStream.read(buffer);
			
			File targetFile = new File(FILE_SYSTEM_IMAGES + File.separator + f.getOriginalFilename());
			
			targetFile.createNewFile();
			try(OutputStream out = new FileOutputStream(targetFile)){
				out.write(buffer);
				flag = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public byte[] getFile(String fileName) {
		File f = new File(FILE_SYSTEM_IMAGES + File.separator + fileName);
		
		if (f.exists()) {
			try {
				InputStream tStream = new FileInputStream(f);
				byte[] bytes = StreamUtils.copyToByteArray(tStream);
				
				return bytes;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
