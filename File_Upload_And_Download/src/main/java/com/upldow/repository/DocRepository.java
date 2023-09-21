package com.upldow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upldow.model.DocFile;

public interface DocRepository extends JpaRepository<DocFile, Integer>{

}
