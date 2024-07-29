package com.example.usermanagement.service;

import com.example.usermanagement.exception.UserNotFoundException;
import com.example.usermanagement.model.dto.UserDto;
import com.example.usermanagement.model.entity.User;
import com.example.usermanagement.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAllUsersSorted();
        return users.stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Integer id) {
        Optional<User> foundUser = this.userRepo.findById(id);
        return foundUser.filter(user -> foundUser.isPresent())
                .map(user -> foundUser.get())
                .map(user -> UserDto.toDto(user))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserDto createUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        User userEntity = User.toEntity(userDto); // Convert the UserDto to a User entity
        User savedUserEntity = userRepo.save(userEntity); // Save the User entity to the repository
        UserDto savedUserDto = UserDto.toDto(savedUserEntity); // Convert the saved User entity back to a UserDto

        return savedUserDto;
    }

    public UserDto updateUser(Integer id, UserDto updatedUserDto) {
        return userRepo.findById(id)
                .map(user -> {
                    user.setName(updatedUserDto.getName());
                    user.setEmail(updatedUserDto.getEmail());
                    user.setMobileNumber(updatedUserDto.getMobileNumber());
                    User savedUser = userRepo.save(user);
                    return UserDto.toDto(savedUser);
                }).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

}
