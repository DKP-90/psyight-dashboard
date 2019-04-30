package com.dashboard.psyight.controller;

import com.dashboard.model.UploadModel;
import com.dashboard.service.ImportService;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UploadImgController {

	@Autowired
	ImportService ims;
	
    private final Logger logger = LoggerFactory.getLogger(UploadImgController.class);

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C:\\Users\\Backend\\Desktop\\psyight\\img\\";

    //Single file upload
    @PostMapping("/img/upload")
    // If not @RestController, uncomment this
    //@ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile, @RequestParam("userid") String userid) {

        logger.debug("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
        	 String generatedString = RandomStringUtils.randomAlphabetic(10).replaceAll("[^a-zA-Z0-9]", "") ;
        	 File dir = new File("C:\\Users\\Backend\\Desktop\\psyight\\img\\" + userid + "\\");
        	    if (!dir.exists()) dir.mkdirs();        	    
            saveUploadedFiles(Arrays.asList(uploadfile),"C:\\Users\\Backend\\Desktop\\psyight\\img\\" + userid + "\\" + generatedString + ".jpg");          

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }


    //save file
    private void saveUploadedFiles(List<MultipartFile> files,String pathfolder) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathfolder);
            Files.write(path, bytes);

        }

    }
}
