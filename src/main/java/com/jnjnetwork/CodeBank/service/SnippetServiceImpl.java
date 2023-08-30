package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.repository.SnippetRepository;
import com.jnjnetwork.CodeBank.util.U;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
    @Value("${app.pagination.write_pages}")
    private int WRITE_PAGES;
    @Value("${app.pagination.page_rows}")
    private int PAGE_ROWS;
    @Value("${app.pagination.profile_write_pages}")
    private int PROFILE_WRITE_PAGES;
    @Value("${app.pagination.profile_page_rows}")
    private int PROFILE_PAGE_ROWS;
    private SnippetRepository snippetRepository;

    @Autowired
    public void setSnippetRepository(SnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    @Override
    public void save(Snippet snippet) {
        snippetRepository.save(snippet);
    }

    @Override
    public int save(Snippet snippet, MultipartFile file) {
        return upload(snippet, file);
    }

    @Override
    public void deleteById(Long id) {
        snippetRepository.deleteById(id);
    }

    @Override
    public List<Snippet> findAll() {
        return snippetRepository.findAll();
    }

    @Override
    public List<Snippet> findPublic(List<Long> following_users, String sortMethod, Integer page, Model model) {
        Sort sort;
        Page<Snippet> pageWrites;
        // default page is 1
        if(page == null) page = 1;
        if(page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = WRITE_PAGES;
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PAGE_ROWS;
        // set current page in session
        session.setAttribute("page", page);

        if ("newest".equals(sortMethod)) {
            sort = Sort.by(
                    Sort.Order.desc("regDate")
            );
            pageWrites = snippetRepository.findByIsPublic(true, PageRequest.of(page - 1, pageRows, sort));
        } else if ("following".equals(sortMethod)) {
            sort = Sort.by(
                    Sort.Order.desc("regDate")
            );
            pageWrites = snippetRepository.findByUserIdInAndIsPublic(following_users, true, PageRequest.of(page - 1, pageRows, sort));
        } else {
            sort = Sort.by(
                    Sort.Order.desc("likeCount"),
                    Sort.Order.desc("regDate")
            );
            pageWrites = snippetRepository.findByIsPublic(true, PageRequest.of(page - 1, pageRows, sort));
        }

        long cnt = pageWrites.getTotalElements();
        int totalPage =  pageWrites.getTotalPages();

        if(page > totalPage) page = totalPage;
        int fromRow = (page - 1) * pageRows;
        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // total snippets
        model.addAttribute("page", page); // current page
        model.addAttribute("totalPage", totalPage);  // total pages
        model.addAttribute("pageRows", pageRows);  // number of snippets in 1 page

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("writePages", writePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return pageWrites.getContent();
    }

    @Override
    public List<Snippet> findByUserId(Long id, Boolean isPublic, Integer page, Model model) {
        Sort sort = Sort.by(Sort.Order.desc("regDate"));
        Page<Snippet> pageWrites;
        // default page is 1
        if(page == null) page = 1;
        if(page < 1) page = 1;

        HttpSession session = U.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = PROFILE_WRITE_PAGES;
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = PROFILE_PAGE_ROWS;
        // set current page in session
        session.setAttribute("page", page);

        if(isPublic) {
            pageWrites = snippetRepository.findByUserIdAndIsPublic(id, isPublic, PageRequest.of(page - 1, pageRows, sort));
        } else {
            pageWrites = snippetRepository.findByUserId(id, PageRequest.of(page - 1, pageRows, sort));
        }

        long cnt = pageWrites.getTotalElements();
        int totalPage =  pageWrites.getTotalPages();

        if(page > totalPage) page = totalPage;
        int fromRow = (page - 1) * pageRows;
        int startPage = (((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // total snippets
        model.addAttribute("page", page); // current page
        model.addAttribute("totalPage", totalPage);  // total pages
        model.addAttribute("pageRows", pageRows);  // number of snippets in 1 page

        model.addAttribute("url", U.getRequest().getRequestURI());
        model.addAttribute("writePages", writePages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return pageWrites.getContent();
    }

    @Override
    public Snippet findById(Long id) {
        return snippetRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public long countPublicSnippets(Boolean isPublic) {
        return snippetRepository.countByIsPublic(isPublic);
    }

    private int upload(Snippet snippet, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null || originalFileName.isEmpty()) {
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
