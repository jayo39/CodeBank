package com.jnjnetwork.CodeBank.service;

import com.jnjnetwork.CodeBank.domain.Role;
import com.jnjnetwork.CodeBank.domain.User;
import com.jnjnetwork.CodeBank.repository.RoleRepository;
import com.jnjnetwork.CodeBank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
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
}
