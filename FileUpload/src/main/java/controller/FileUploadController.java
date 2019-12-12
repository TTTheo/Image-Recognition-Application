package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import object.Client;

@Controller
public class FileUploadController {
	private Client client;
	public static String uploadDirectory = "/users/zhjiang/tomcat/apache-tomcat-9.0.29/webapps/Image-App";
	private String res = "hehe_1_1";

	@RequestMapping("/")
	public String UploadPage(Model model) {
		return "uploadview";
	}

	@RequestMapping("/upload")
	public String upload(Model model, @RequestParam("file") MultipartFile file) {
		String fileName = "";
		Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
		fileName = file.getOriginalFilename();
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		long start = System.currentTimeMillis() ;
		try {
			client = new Client("204.102.228.186", 8888);
			System.out.println("connected");
			res = client.send(uploadDirectory + "/" + fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis() ;
		double ttot = end - start ;
		String[] ss = res.split("_") ;
		String display = ss[0] ;
		double tnet = Double.parseDouble(ss[1]) ;
		double tfiles = Double.parseDouble(ss[2]) ;
		double tsocket = ttot - tnet - tfiles ;

		model.addAttribute("tsocket", "The network time is " + tsocket);
		model.addAttribute("tnet", "The inference time is " + tnet);
		model.addAttribute("tfile", "The file processing time is " + tfiles);
		model.addAttribute("prediction",display) ;
		System.out.println("------------------");
		System.out.println(tnet);
		System.out.println(tfiles);
		System.out.println(ttot - tnet);
		System.out.println(tsocket);
		System.out.println("------------------");
		return "uploadstatusview";
	}
	
	@RequestMapping("/result")
	public String showResult(Model model) {
		return "waiting";
	}
	

}