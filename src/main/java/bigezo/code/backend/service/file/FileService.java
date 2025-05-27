package bigezo.code.backend.service.file;

import bigezo.code.backend.model.Student;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    private final AmazonS3 amazonS3Client;
    private final String bucketName;
    private final String endpoint;

    @Autowired
    public FileService(AmazonS3 amazonS3Client, 
                       @Value("${application.bucket.name}") String bucketName,
                       @Value("${cloud.aws.endpoint.static}") String endpoint) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
        this.endpoint = endpoint;
    }

    /**
     * Upload a file to S3 and return the URL
     */
    public String uploadFile(MultipartFile file, String fileType, Student student) throws IOException {
        if (student == null || student.getSchoolAdmin() == null) {
            throw new IllegalArgumentException("Student or school admin information is missing");
        }
        
        Long schoolId = student.getSchoolAdmin().getId();
        if (schoolId == null) {
            throw new IllegalArgumentException("School admin ID is missing");
        }
        
        // Create unique file name with timestamp
        String fileName = String.format("%s_%s_%s.%s", 
            fileType, 
            schoolId, 
            System.currentTimeMillis(),
            file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1));
        
        // Create full path with school ID and file type
        String filePath = String.format("school-%s/students/%s/%s", schoolId, fileType, fileName);
        
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        
        // Upload file and get the response
        PutObjectResult result = amazonS3Client.putObject(new PutObjectRequest(bucketName, filePath, file.getInputStream(), metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));
        
        // Return the friendly URL format
        return String.format("https://f005.backblazeb2.com/file/%s/%s", bucketName, filePath);
    }

    /**
     * Delete a file from S3
     */
    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(bucketName, fileName);
    }


}
