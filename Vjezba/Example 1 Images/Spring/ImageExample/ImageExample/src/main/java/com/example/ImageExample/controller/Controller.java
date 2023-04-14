package com.example.ImageExample.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ImageExample.entity.MyEntity;
import com.example.ImageExample.service.MyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "image")
public class Controller {
	private static final String FILE_SYSTEM_IMAGES = System.getProperty("user.dir") + File.separator + "images";
	private final MyService myService;
	
	@Autowired
	public Controller(MyService myService) {
		this.myService = myService;
	}
	
	static {
		File f = new File(FILE_SYSTEM_IMAGES);
		if (!f.exists())
			f.mkdir();
	}
	
	@PostMapping("/upload")
	public ResponseEntity<Boolean> uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		boolean flag = myService.saveFile(file);
		
		return new ResponseEntity<>(flag, HttpStatus.OK);
	}
	
	@GetMapping(path = { "/get/{imageName}" })
	public ResponseEntity<MyEntity> getImage(@PathVariable("imageName") String imageName) throws IOException {
		byte []b = myService.getFile(imageName);
		MyEntity e = new MyEntity(b);
		
		return new ResponseEntity<>(e, HttpStatus.OK);
	}
}
