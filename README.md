# spring-boot-api
Spring Boot Application 

//GET file stream for the uploaded file
http://localhost:9005/springBootApi/api?getUploadedFile?fileId=<File ID>

//Search for uploaded files
http://localhost:9005/springBootApi/api/searchFiles

//Search for uploaded files based on the file name
http://localhost:9005/springBootApi/api/searchFiles?dscFileName=<File Name>
//Get File Metadata by file Id.
http://localhost:9005/springBootApi/api/getFileMetaData?fileId=<File ID>

//Upload file -- POST/GET accepts mutlipart requests as parameters
http://localhost:9005/springBootApi/api/uploadFile
http://localhost:9005/springBootApi/api/uploadFile?description=<Description>
