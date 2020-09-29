package com.lhstack.microservice.minio.controller;

import com.lhstack.microservice.common.resp.Result;
import com.lhstack.microservice.minio.entity.FileObject;
import com.lhstack.microservice.minio.service.MinioUploadService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @author lh
 * @date 2020/9/29
 * @description
 * @name microservice
 * @sine 11
 */
@RestController
@RequestMapping("upload")
@AllArgsConstructor
public class UploadController {

    private final MinioUploadService minioUploadService;

    @PostMapping("file")
    public List<Result<Map<String,Object>>>  upload(@RequestPart("file") FilePart filePart) throws InterruptedException {
        List<FileObject> fileObjects = new CopyOnWriteArrayList<>();
        filePart.content().reduce(DataBuffer::write)
                .subscribe(item ->{
                    byte[] bytes = new byte[item.readableByteCount()];
                    item.read(bytes);
                    FileObject fileObject = new FileObject();
                    fileObject.setFileName(filePart.filename());
                    fileObject.setContent(bytes);
                    fileObject.setContentType(filePart.headers().getFirst("Content-Type"));
                    fileObjects.add(fileObject);
                }).dispose();
        return minioUploadService.uploads(fileObjects,"picture");
    }
}
