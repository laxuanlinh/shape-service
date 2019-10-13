package com.linhlx.shapeservice.service;

import com.linhlx.shapeservice.dto.PostedUserDTO;
import com.linhlx.shapeservice.dto.UserDTO;
import com.linhlx.shapeservice.exception.UserException;
import com.linhlx.shapeservice.model.Role;
import com.linhlx.shapeservice.model.User;
import com.linhlx.shapeservice.repository.RoleRepository;
import com.linhlx.shapeservice.repository.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
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
        if (userRepository.findById(postedUserDTO.getUsername()).isPresent()){
            throw new UserException("Username already exists");
        }
        User user = new User(postedUserDTO.getUsername(), encoder.encode(postedUserDTO.getPassword()), true, null);
        Role role = new Role(postedUserDTO.getRole(), user);
        user.setRole(role);

        return saveUser(user);
    }

    @Override
    public UserDTO updateUser(PostedUserDTO postedUserDTO) {
        User user = userRepository.findById(postedUserDTO.getUsername())
                .orElseThrow(()->new UserException("User not found"));
        if (Strings.isNotEmpty(postedUserDTO.getPassword()))
            user.setPassword(encoder.encode(postedUserDTO.getPassword()));
        if (Strings.isNotEmpty(postedUserDTO.getRole()))
            user.getRole().setAuthority(postedUserDTO.getRole());
        return saveUser(user);
    }

    private UserDTO saveUser(User user){
        user = userRepository.save(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO deleteUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(()->new UserException("User not found"));
        user.setEnabled(false);
        userRepository.save(user);

        return new UserDTO(user);
    }
}
