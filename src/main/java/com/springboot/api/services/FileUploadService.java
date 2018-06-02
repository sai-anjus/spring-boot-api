package com.springboot.api.services;

import com.springboot.api.entities.FileUpload;

import java.util.List;


public interface FileUploadService {

    /**
     * Save FileUpload information along with file upload information.
     *
     * @param fileUploadObj
     */
    public void save(FileUpload fileUploadObj) throws Exception;

    /**
     * Returns the FileUpload information from the FileUpload table based on the given timesheetId.
     *
     * @param timesheetId
     * @return
     */
    public FileUpload getTimesheetByTimesheetId(long timesheetId) throws Exception;

    /**
     * Returns list of Timesheets.
     *
     * @return
     * @throws Exception
     */
    public List<FileUpload> getTimesheetsList() throws Exception;

    /**
     * Returns list of Timesheets for a given File Name.
     *
     * @return
     * @throws Exception
     */
    public List<FileUpload> getTimesheetsListByName(String fileName) throws Exception;


}
