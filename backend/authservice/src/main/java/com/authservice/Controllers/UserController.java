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
       try {
           if(requestBody == null || !requestBody.containsKey("token") || requestBody.get("token").trim().isEmpty()){
               Map<String, String> response = new HashMap<>();
               response.put("error", "Token is required");
               return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
           }
           String token = requestBody.get("token");
           if(userservice.validateToken(token)){
               Integer id = jwtUtil.extractUserID(token);
               String role = jwtUtil.extractUserRole(token);
               String email = jwtUtil.extractUsername(token);
               Map<String, String> response = new HashMap<>();
               response.put("id", id.toString());
               response.put("role", role);
               response.put("email", email);
               return new ResponseEntity<>(response, HttpStatus.OK);
           } else {
               Map<String, String> response = new HashMap<>();
               response.put("error", "Invalid or expired token");
               return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
           }
       }catch(Exception ex){
           Map<String, String> response = new HashMap<>();
           response.put("error", "Token validation failed");
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllUsers(@RequestHeader("USER-ROLE") String role){
       try {
           if(role == null || role.trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body(Map.of("error", "USER-ROLE header is required"));
           }
           if(!"admin".equals(role)){
               return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Admin access required"));
           }
           List<User> users = userservice.getAllUsers();
           users.forEach(user -> user.setPassword(null));
           return ResponseEntity.ok(users);
       }catch(Exception ex){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(Map.of("error", "failed to fetch users"));
       }
    }
}