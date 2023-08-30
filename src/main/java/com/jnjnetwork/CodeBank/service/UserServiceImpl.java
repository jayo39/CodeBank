package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Role;
import com.jnjnetwork.CodeBank.domain.Snippet;
import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.repository.RoleRepository;
import com.jnjnetwork.CodeBank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserServiceImpl implements UserService{

    @Value("${app.upload.path}")
    private String uploadDir;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isExist(String email) {
        User user = findByEmail(email);
        return user != null;
    }

    @Override
    public int register(User user){
        try {
            user.setEmail(user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user); // insert

            Role role = roleRepository.findByName("ROLE_MEMBER");

            user.addRole(role);
            userRepository.save(user); // update
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long countUserTotal() {
        return userRepository.count();
    }

    @Override
    public List<Role> selectRolesById(Long id) {
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        return user.getRoles();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void save(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public int save(User user, MultipartFile file) {
        return upload(user, file);
    }
    private int upload(User user, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null || originalFileName.isEmpty()) {
            try {
                user.setP_img(null);
                userRepository.saveAndFlush(user);
                return 1;
            } catch(Exception e) {
                return 0;
            }
        }
        if (!user.getP_img().isEmpty()) {
            String deleteFilePath = uploadDir + File.separator + user.getP_img();
            File deleteFile = new File(deleteFilePath);
            if (deleteFile.exists()) {
                deleteFile.delete();
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
        user.setP_img(fileName);
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
            userRepository.saveAndFlush(user);
            return 1;
        } catch(Exception e) {
            return 0;
        }
    }
}
