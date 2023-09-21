package com.upldow.controller;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.upldow.model.DocFile;
import com.upldow.service.DocService;


@Controller
public class DocFileController {
	
	@Autowired
	private DocService docService;
	
	@GetMapping("/")
	public String get(Model model) {
		List<DocFile> docs = docService.getFiles();
		model.addAttribute("docs", docs);
		return "doc";
	}
	
	@PostMapping("/uploadFiles")
	public String uploadFile(@RequestParam("files") MultipartFile[] files) {
		for(MultipartFile file : files) {
			docService.saveFile(file);
		}
		return "redirect:/";
	}
	
	@GetMapping("/download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId){
		DocFile doc = docService.getFile(fileId).get();
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(doc.getDocType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\""+doc.getDocName()+"\"")
				.body(new ByteArrayResource(doc.getData()));
	}
	
	@RequestMapping("/delete/{fileId}")
	public String delete(@PathVariable("fileId") Integer fileId) {
		this.docService.deleteFile(fileId);
		return "redirect:/";
	}
}
