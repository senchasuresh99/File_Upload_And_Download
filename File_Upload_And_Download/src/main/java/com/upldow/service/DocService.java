package com.upldow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upldow.model.DocFile;
import com.upldow.repository.DocRepository;

@Service
public class DocService {

	@Autowired
	private DocRepository docRepository;
	
	public DocFile saveFile(MultipartFile file) {
		String docName = file.getOriginalFilename();
		try {
			DocFile doc = new DocFile(docName, file.getContentType(), file.getBytes());
			return docRepository.save(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<DocFile> getFile(Integer fileId) {
		return docRepository.findById(fileId);
	}
	
	public List<DocFile> getFiles(){
		return docRepository.findAll();
	}

	public void deleteFile(Integer fileId) {
		this.docRepository.deleteById(fileId);
	}
}
