package ks.dev.ShoppingCartApp.service.user;

import ks.dev.ShoppingCartApp.dto.UserDto;
import ks.dev.ShoppingCartApp.model.User;
import ks.dev.ShoppingCartApp.request.CreateUserRequest;
import ks.dev.ShoppingCartApp.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request , Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);
}
