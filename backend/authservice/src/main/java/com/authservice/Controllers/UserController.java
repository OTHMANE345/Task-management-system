package com.authservice.Controllers;

import com.authservice.JWT.JwtUtil;
import com.authservice.Service.UserServices;
import com.authservice.modules.User;
import com.authservice.utils.apputils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
@RestController
public class UserController {

    @Autowired
    UserServices userservice;

    @Autowired
    JwtUtil jwtUtil;
    @PostMapping(path = "/singup")
    public ResponseEntity<String> singUp(@RequestBody(required = true) Map<String,String> requestMap){
        try {
            return userservice.singup(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return apputils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String,String> requestMap){
        try {
            return userservice.login(requestMap);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return apputils.getResponseEntity("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/validate-token")
    public ResponseEntity<Map<String, String>> validateToken(@RequestBody Map<String, String> requestBody){
        String token = requestBody.get("token");
        if(userservice.validateToken(token)){
            Integer id = jwtUtil.extractUserID(token);
            String role = jwtUtil.extractUserRole(token);
            Map<String, String> response = new HashMap<>();
            response.put("id", id.toString());
            response.put("role", role);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Internal server error");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("USER-ROLE") String role){
        if(!"admin".equals(role)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userservice.getAllUsers());
    }
}
