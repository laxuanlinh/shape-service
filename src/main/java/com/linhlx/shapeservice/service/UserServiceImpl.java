package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.dto.PostedUserDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.model.Role;
import com.linhlx.shapeservice.model.User;
import com.linhlx.shapeservice.repository.RoleRepository;
import com.linhlx.shapeservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return this.convertToUserDTOList(users);
    }

    private List<UserDTO> convertToUserDTOList(List<User> users) {
        return users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(PostedUserDTO postedUserDTO) {
        User user = this.saveUser(postedUserDTO);
        Role role = this.saveRole(postedUserDTO, user);
        return this.convertToUserDTO(user, role);
    }

    private User saveUser(PostedUserDTO postedUserDTO){
        User user = new User(postedUserDTO.getUsername(), postedUserDTO.getPassword(), true);
        return userRepository.save(user);
    }

    private Role saveRole(PostedUserDTO postedUserDTO, User user){
        Role role = new Role(postedUserDTO.getRole(), user);
        return roleRepository.save(role);
    }

    private UserDTO convertToUserDTO(User user, Role role){
        user.setRole(role);
        return new UserDTO(user);
    }

    @Override
    public UserDTO updateUser(PostedUserDTO postedUserDTO) {
        return null;
    }

    @Override
    public UserDTO deleteUser(UserDTO user) {
        return null;
    }
}
