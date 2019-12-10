package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import object.Client;

@Controller
public class FileUploadController {
	private Client client;
	public static String uploadDirectory = System.getProperty("user.dir") + "/upload";
	private String res;

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

		try {
			client = new Client("localhost", 8000);
			res = client.send(uploadDirectory + "/" + fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(res);
		model.addAttribute("msg", "Result is " + res);
		return "uploadstatusview";
	}

}