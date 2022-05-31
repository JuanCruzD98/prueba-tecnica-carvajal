package com.example.carvajaltechnicaltest.controller;


import com.example.carvajaltechnicaltest.dto.DnaDto;
import com.example.carvajaltechnicaltest.dto.UserDto;
import com.example.carvajaltechnicaltest.repository.UserRepository;
import com.example.carvajaltechnicaltest.service.DnaServiceImpl;
import com.example.carvajaltechnicaltest.service.UserServiceImpl;
import com.example.carvajaltechnicaltest.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/api/")
@CrossOrigin (origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private DnaServiceImpl dnaServiceImpl;
    @Autowired
    private UserRepository userRepository;



    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto){
            UserDto userSaved = userService.register(userDto);

            String  jwt = jwtUtils.generateToken(userService.loadUserByUsername(userDto.getEmail()));
            return new ResponseEntity<>(jwt,HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.getUser(id),HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.update(userDto,id),HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.login(userDto),HttpStatus.OK);
    }

    @PostMapping(value = "/results")
    public ResponseEntity<DnaDto> getResults(@RequestBody UserDto userDto){
        return new ResponseEntity<>(dnaServiceImpl.dnaResults(userDto),HttpStatus.OK);
    }

}
