package com.dailycodework.dreamshops.service.user;

import com.dailycodework.dreamshops.DTO.UserDto;
import com.dailycodework.dreamshops.model.User;
import com.dailycodework.dreamshops.requests.CreateUserRequest;
import com.dailycodework.dreamshops.requests.UpdateUserRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
