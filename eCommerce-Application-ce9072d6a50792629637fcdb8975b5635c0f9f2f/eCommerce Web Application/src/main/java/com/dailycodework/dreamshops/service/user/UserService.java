package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.DTO.OrderDto;
import com.dailycodework.dreamshops.DTO.UserDto;
import com.dailycodework.dreamshops.exceptions.AlreadyExistsException;
import com.dailycodework.dreamshops.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.repository.UserRepository;
import com.dailycodework.dreamshops.requests.CreateUserRequest;
import com.dailycodework.dreamshops.requests.UpdateUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User Not found"));
        return user;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user ->!userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(()-> new AlreadyExistsException("Oops!"+request.getEmail()+ "already exists!"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName((request.getLastName()));
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, ()->{
            throw new ResourceNotFoundException("User not found");
        });
    }

    @Override
    public UserDto convertUserToDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        if(user.getOrders()!=null && !user.getOrders().isEmpty()){
            userDto.setOrders(user.getOrders().stream()
                    .map(order -> modelMapper.map(order, OrderDto.class))
                    .collect(Collectors.toList()));
        }
        else {
            userDto.setOrders(new ArrayList<>());
        }
        return userDto;
    }
}
