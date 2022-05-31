package com.example.carvajaltechnicaltest.service;


import com.example.carvajaltechnicaltest.dto.UserDto;
import com.example.carvajaltechnicaltest.entity.Role;
import com.example.carvajaltechnicaltest.entity.User;
import com.example.carvajaltechnicaltest.exception.EmailExistsException;
import com.example.carvajaltechnicaltest.exception.ResourceNotFoundException;
import com.example.carvajaltechnicaltest.mapper.UserMapper;
import com.example.carvajaltechnicaltest.repository.RoleRepository;
import com.example.carvajaltechnicaltest.repository.UserRepository;
import com.example.carvajaltechnicaltest.utils.JwtUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private DnaServiceImpl dnaServiceImpl;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RoleRepository roleRepository, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roleRepository= roleRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public UserDto register(UserDto userDto){
        User user = userMapper.userDtoToUser(userDto);
        if (emailExist(user.getEmail())) {
            throw new EmailExistsException("An account with the email address "
                    + user.getEmail() + " already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleId(roleRepository.findByName("ADMIN"));
        userRepository.save(user);

            return userMapper.userToUserDto(user);



    }

    @Override
    public boolean emailExist(String email){
        Optional<User> userFound = userRepository.findByEmail(email);
                return userFound.isPresent();
    }

    @Override
    public String login(UserDto userDto) {
        Authentication authentication = authenticate(userDto);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = loadUserByUsername(userDto.getEmail());

        return jwtUtils.generateToken(userDetails);
    }



    @Override
    public String delete(Long id){
       User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "Deleted successfully";
    }

    @Override
    public UserDto update(UserDto userDto,Long id){
        User existingUser = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "ID",id));

        existingUser.setUserName(userDto.getUserName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

            return userMapper.userToUserDto(existingUser);

    }

    @Override
    public UserDto getUser(Long id){
        return userMapper.userToUserDto(userRepository.findById(id).orElseThrow(()-> new RuntimeException("User doesn´t exist")));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = user.getRoleId();
        List<GrantedAuthority> authority = Stream.of(role).map(role1 -> new SimpleGrantedAuthority(role1.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authority);

    }

    private Authentication authenticate(UserDto user) {

        Authentication authentication;

        try {

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        } catch (AuthenticationException e) {

            throw new BadCredentialsException("Wrong credentials");

        }

        return authentication;

    }



}

