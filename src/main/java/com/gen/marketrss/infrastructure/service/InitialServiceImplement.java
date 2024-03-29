package com.gen.marketrss.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitialServiceImplement implements InitialService{

    @Value("${img.upload}")
    private String uploadPath;
    @Override
    public void removeImage(int period) {
        log.info("removeImage >>> ");
        LocalDate now = LocalDate.now();
        String dirString = System.getProperty("user.dir") + uploadPath;
        File file = new File(dirString);
        if (file.isDirectory()) {
            Path dir = Path.of(dirString);
            try {
                Stream<Path> files = Files.list(dir);
                files.forEach(path -> {
                    try {
                        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

                        LocalDate fileDate = attrs.creationTime()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();

                        long daysBetween = ChronoUnit.DAYS.between(fileDate, now);
                        log.info("daysBetween", daysBetween);
//                        if (daysBetween >= period) {
                        if (true) {
                            Files.delete(path);
                            log.info("file delete : {} " , path);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("forEach error : {} ", e.getMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                log.info("method error : {} " , e.getMessage());
            }
        }
    }
}
