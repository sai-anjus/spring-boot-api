package com.springboot.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Maintains FileUpload Information.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TIMESHEET_ID", unique = true, nullable = false)
    private long timesheetId;
    //File Upload Information
    @Transient
    private MultipartFile uploadFile; //Just to get the uploaded file.
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(length = 1048576)
    private byte[] blobContent; //Byte stream of the file saved in the database.
    private String dscFileName;
    private long fileSize;
    private String dscComments;
    private String reviewerComments;
    //Audit Information
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime dateCreated;
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime dateLastModified;
    private String nameCreated; //Name of the user Uploaded the timesheet.
    private String nameLastModified;


}
