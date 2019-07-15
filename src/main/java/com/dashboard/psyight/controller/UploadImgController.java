package com.dashboard.psyight.controller;

import com.dashboard.model.UploadModel;
import com.dashboard.response.readusergroup.Image;
import com.dashboard.service.ImportService;
import com.dashboard.service.ProductImageService;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
public class UploadImgController {

	@Autowired
	ImportService ims;
	@Autowired
	ProductImageService pis;
	
    private final Logger logger = LoggerFactory.getLogger(UploadImgController.class);

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "img/";

    //Single file upload
    @PostMapping("/img/upload")

    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile, @RequestParam("userid") String userid , @RequestParam("pid") int pid) {

        logger.debug("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
        	 String generatedString = RandomStringUtils.randomAlphabetic(10).replaceAll("[^a-zA-Z0-9]", "") ;
        	 File dir = new File(UPLOADED_FOLDER + userid + "/");
        	    if (!dir.exists()) dir.mkdirs();        	    
            saveUploadedFiles(Arrays.asList(uploadfile),UPLOADED_FOLDER + userid + "/" + generatedString + ".jpg");    
            pis.addimage("http://psyightdash.boxspace.in:8081/viewimage/"+userid+"/"+generatedString + ".jpg" , pid);

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
    
    
    @RequestMapping(value = "/viewimage/{userid}/{image}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] readimage(@PathVariable("userid") String userid,@PathVariable("image") String image ) throws IOException {    	
    	byte[] b =Files.readAllBytes(Paths.get(UPLOADED_FOLDER+userid+"/"+image));
        return b;
    }
    
}
