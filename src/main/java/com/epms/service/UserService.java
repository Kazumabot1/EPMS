package com.epms.service;

import com.epms.dto.UserDTO;
import com.epms.entity.User;
import com.epms.exception.*;
import com.epms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setEmployeeCode(dto.getEmployeeCode());
        user.setPosition(dto.getPosition());
        user.setManagerId(dto.getManagerId());
        user.setDepartmentId(dto.getDepartmentId());
        user.setActive(dto.getActive() != null ? dto.getActive() : true);
        user.setJoinDate(dto.getJoinDate());
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public User updateUser(Integer id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new DuplicateResourceException("Email already taken");
            }
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null) user.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getFullName() != null) user.setFullName(dto.getFullName());
        if (dto.getEmployeeCode() != null) user.setEmployeeCode(dto.getEmployeeCode());
        if (dto.getPosition() != null) user.setPosition(dto.getPosition());
        if (dto.getManagerId() != null) user.setManagerId(dto.getManagerId());
        if (dto.getDepartmentId() != null) user.setDepartmentId(dto.getDepartmentId());
        if (dto.getActive() != null) user.setActive(dto.getActive());
        if (dto.getJoinDate() != null) user.setJoinDate(dto.getJoinDate());
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}