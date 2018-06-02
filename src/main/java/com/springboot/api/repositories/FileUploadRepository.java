package com.springboot.api.repositories;

import com.springboot.api.entities.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

    List<FileUpload> findAllByDscFileName(String fileName);

}
