package com.springboot.api.controllers;

import com.springboot.api.constants.ApplicationConstants;
import com.springboot.api.entities.FileUpload;
import com.springboot.api.models.FileUtils;
import com.springboot.api.models.RestResponse;
import com.springboot.api.services.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class FileUploadRestController {

    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/searchFiles")
    public List<FileUpload> getUploadedFileSummary(@RequestParam(name = "dscFileName", required = false) String dscFileName) {
        log.info("Inside getUploadedFileSummary method of FileUploadRestController:: dscFileName: " + dscFileName);
        HttpHeaders header = null;
        List<FileUpload> timesheetsList = null;
        try {
            if (StringUtils.isBlank(dscFileName)) {
                timesheetsList = fileUploadService.getTimesheetsList();
            } else {
                timesheetsList = fileUploadService.getTimesheetsListByName(dscFileName);
            }
            for (FileUpload fileUpload : timesheetsList) {
                fileUpload.setBlobContent(null);
            }
            log.info("After getting the timesheetObj");
        } catch (Exception ex) {
            log.error("Exception while retrieving document " + ex);
        }
        return timesheetsList;
    }

    /**
     * Get the file from the database TIMESHEET table and then
     * covert it to a stream and display it to the user.
     *
     * @param fileId
     * @return
     */
    @GetMapping("/getUploadedFile")
    public ResponseEntity getUploadedFile(@RequestParam("fileId") long fileId) {
        log.info("Inside getContractDocument method of FileUploadRestController:: fileId: " + fileId);
        byte[] doc = null;
        HttpHeaders header = null;
        try {
            FileUpload fileUploadObj = fileUploadService.getTimesheetByTimesheetId(fileId);
            log.info("After getting the fileUploadObj");
            if (fileUploadObj != null) {
                doc = fileUploadObj.getBlobContent();
                String extn = FilenameUtils.getExtension(fileUploadObj.getDscFileName()).toLowerCase();
                log.info("File Extension: " + extn);
                String mimeType = ApplicationConstants.TIMESHEET_FILE_EXTENSION_MAP.get(extn);
                if (StringUtils.isEmpty(mimeType) || StringUtils.isBlank(mimeType)) {
                    mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;    //Unknown file type - defaulting to stream
                }
                log.info("Mime type detected is : " + mimeType + " for the file extn: " + extn);
                header = new HttpHeaders();
                header.setContentType(MediaType.valueOf(mimeType));
                header.set("Content-Disposition", "inline; filename = " + fileUploadObj.getDscFileName());
                header.setContentLength(doc.length);
            } else {
                log.error("No FileUpload found for the given timesheetId.");
                return new ResponseEntity(doc, header, HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            log.error("Exception while retrieving document " + ex);
        }
        if (doc == null || doc.length == 0) {
            return new ResponseEntity(doc, header, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(doc, header, HttpStatus.OK);
    }

    @GetMapping("/getFileMetaData")
    public FileUpload getUploadedFileMetadata(@RequestParam("fileId") long fileId) {
        log.info("Inside getUploadedFileMetadata method of FileUploadRestController:: fileId: " + fileId);
        HttpHeaders header = null;
        FileUpload fileUploadObj = null;
        try {
            fileUploadObj = fileUploadService.getTimesheetByTimesheetId(fileId);
            log.info("After getting the fileUploadObj");
            if (fileUploadObj != null) {
                fileUploadObj.setBlobContent(null);
            } else {
                log.error("No FileUpload found for the given timesheetId.");
            }
        } catch (Exception ex) {
            log.error("Exception while retrieving document " + ex);
        }

        return fileUploadObj;
    }

    /**
     * Uploads the file and also save corresponding file
     * information.
     *
     * @param uploadedFile
     * @param description
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<RestResponse> uploadFile(
            @RequestParam("file") MultipartFile uploadedFile,
            @RequestParam(name = "description", required = false) String description) {
        log.info("Inside uploadFile method ");
        RestResponse ecasRestResponse = new RestResponse();

        if (uploadedFile.isEmpty()) {
            ecasRestResponse.setResponseType("ERROR");
            ecasRestResponse.setResponseMessage("Please select a file to upload!");
            return new ResponseEntity(ecasRestResponse, HttpStatus.OK);
        }

        try {
            String uploadedFileName = uploadedFile.getOriginalFilename().replaceAll("\\s+", StringUtils.EMPTY);
            log.debug("Original File Name: " + uploadedFileName
                    + " Name: " + uploadedFile.getName() + " Content Type: " + uploadedFile.getContentType() + " uploadedFile size: " + uploadedFile.getSize());
            StringBuilder message = new StringBuilder("");
            FileUtils fileUtils = new FileUtils();

            if (!fileUtils.validFileName(uploadedFileName)) {
                message.append(" Not a valid file name. ");
            }

            if (!fileUtils.validFileType(uploadedFileName)) {
                message.append(" Not a valid file type. ");
            }

            if (!validateFileSize(uploadedFile)) {
                message.append(" The uploaded file should be less than 5MB. ");
            }

            //Check if there is any message associated
            if (StringUtils.isBlank(message.toString())) {
                FileUpload fileUpload = new FileUpload();
                fileUpload.setDscFileName(uploadedFileName);
                fileUpload.setFileSize(uploadedFile.getSize());
                fileUpload.setBlobContent(uploadedFile.getBytes());
                fileUpload.setDscComments(description);
                fileUpload.setDateCreated(LocalDateTime.now());
                fileUploadService.save(fileUpload);

                ecasRestResponse.setResponseType("SUCCESS");
                ecasRestResponse.setResponseMessage("File - " + uploadedFileName + " uploaded successfully");
                return new ResponseEntity(ecasRestResponse, new HttpHeaders(), HttpStatus.OK);
            } else {
                ecasRestResponse.setResponseType("SUCCESS");
                ecasRestResponse.setResponseMessage("Errors: " + message);
                return new ResponseEntity(ecasRestResponse, new HttpHeaders(), HttpStatus.OK);
            }

        } catch (Exception ex) {
            log.error("Exception while retrieving the file: " + ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /*
     *   Perform all the required validations on the Uploaded file.
     *    File Name, File Type/Extension, File Size
     */
    private boolean validateFileSize(MultipartFile uploadedFile) throws Exception {
        boolean errorFlag = true;
        long FILE_SIZE_LIMIT = Long.parseLong("1000000");
        //File Size validations
        if (uploadedFile.getSize() >= FILE_SIZE_LIMIT) {
            errorFlag = false;
        }
        return errorFlag;
    }
}
