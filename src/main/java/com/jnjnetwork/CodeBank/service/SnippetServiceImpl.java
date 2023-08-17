package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.repository.SnippetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class SnippetServiceImpl implements SnippetService{
    @Value("${app.upload.path}")
    private String uploadDir;
    private SnippetRepository snippetRepository;

    @Autowired
    public void setSnippetRepository(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    @Override
    public int save(Snippet snippet, MultipartFile file) {
        return upload(snippet, file);
    }

    @Override
    public List<Snippet> findAll() {
        return snippetRepository.findAll();
    }

    @Override
    public List<Snippet> findPublic() {
        return snippetRepository.findByIsPublic(true, Sort.by(Sort.Order.desc("reg_date")));
    }

    private int upload(Snippet snippet, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null || originalFileName.length() == 0) {
            try {
                snippet.setImg(null);
                snippetRepository.save(snippet);
                return 1;
            } catch(Exception e) {
                return 0;
            }
        }

        String sourceName = StringUtils.cleanPath(originalFileName);
        String fileName = sourceName;
        File file1 = new File(uploadDir + File.separator + sourceName);
        if (file1.exists()) {
            int pos = fileName.lastIndexOf(".");
            if (pos > -1) {
                String name = fileName.substring(0, pos);
                String ext = fileName.substring(pos + 1);

                fileName = name + "_" + System.currentTimeMillis() + "." + ext;
            } else {
                fileName += "_" + System.currentTimeMillis();
            }
        }
        snippet.setImg(fileName);
        Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());
        try {
            Files.copy(
                    file.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            snippetRepository.save(snippet);
            return 1;
        } catch(Exception e) {
            return 0;
        }
    }
}
