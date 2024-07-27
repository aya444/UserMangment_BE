package com.example.usermanagement.service;

import com.example.usermanagement.model.dto.UserDto;
import com.example.usermanagement.model.entity.User;
import com.example.usermanagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserDto.toDto(user));
        }
        return userDtos;
    }

    public UserDto getUserById(Integer id) {
        Optional<User> user = this.userRepo.findById(id);
        if(user.isPresent())
            return UserDto.toDto(user.get());
        else
            return null;
    }

    public UserDto createUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        // Convert the UserDto to a User entity
        User userEntity = User.toEntity(userDto);

        // Save the User entity to the repository
        User savedUserEntity = userRepo.save(userEntity);

        // Convert the saved User entity back to a UserDto
        UserDto savedUserDto = UserDto.toDto(savedUserEntity);

        // Return the UserDto
        return savedUserDto;
    }

    public UserDto updateUser(Integer id, UserDto updatedUserDto) {
        return userRepo.findById(id).map(user -> {
            user.setName(updatedUserDto.getName());
            user.setEmail(updatedUserDto.getEmail());
            user.setMobileNumber(updatedUserDto.getMobileNumber());
            User savedUser = userRepo.save(user);
            return UserDto.toDto(savedUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

}
