package com.springboot.api.services;

import com.springboot.api.entities.FileUpload;
import com.springboot.api.repositories.FileUploadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    FileUploadRepository fileUploadRepository;

    @Override
    public void save(FileUpload fileUploadObj) throws Exception {
        log.info("Inside save method of TimesheetServiceImpl :: " + fileUploadObj.getTimesheetId());
        fileUploadRepository.save(fileUploadObj);
    }

    @Override
    public FileUpload getTimesheetByTimesheetId(long timesheetId) throws Exception {
        log.info("Inside getTimesheetByTimesheetId method of TimesheetServiceImpl --> timesheetId: " + timesheetId);
        return fileUploadRepository.findById(timesheetId).orElse(null);
    }

    @Override
    public List<FileUpload> getTimesheetsList() throws Exception {
        return fileUploadRepository.findAll();
    }

    @Override
    public List<FileUpload> getTimesheetsListByName(String fileName) throws Exception {
        return fileUploadRepository.findAllByDscFileName(fileName);
    }
}
