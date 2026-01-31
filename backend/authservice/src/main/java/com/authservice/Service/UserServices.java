package com.authservice.Service;



import com.authservice.JWT.CustomerUserDetailsSErvice;
import com.authservice.JWT.JwtUtil;
import com.authservice.Repositories.UserRepository;
import com.authservice.modules.User;
import com.authservice.utils.apputils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServices {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private CustomerUserDetailsSErvice customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;
    public ResponseEntity<String> singup(Map<String, String> requestMap) {
        try {
            if(validateSingUpMap(requestMap)){
                if(isValidEmail(requestMap.get("email"))){
                    User user = userRepo.findByEmailId(requestMap.get("email"));
                    if(Objects.isNull(user)){
                        userRepo.save(getUserFromMap(requestMap));
                        return apputils.getResponseEntity("Successfully registred", HttpStatus.CREATED);

                    }else {
                        return apputils.getResponseEntity("Email already exist", HttpStatus.CONFLICT);
                    }
                } else {
                    return apputils.getResponseEntity("Invalid email format", HttpStatus.BAD_REQUEST);
                }
            }
            else {
                return apputils.getResponseEntity("Invalid data: name, email, password and contactNumber are required", HttpStatus.BAD_REQUEST);
            }
        } catch(Exception ex){
            ex.printStackTrace();
            return apputils.getResponseEntity("An unexpected error ocuured", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private boolean validateSingUpMap(Map<String, String> requestMap) {
        if (requestMap != null && requestMap.containsKey("name") && !requestMap.get("name").trim().isEmpty() && requestMap.containsKey("email") && !requestMap.get("email").trim().isEmpty() &&
                requestMap.containsKey("contactNumber") && !requestMap.get("contactNumber").trim().isEmpty() && requestMap.containsKey("password")  && !requestMap.get("password").trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isValidEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setRole("user");
        user.setStatus("true");
        return user;

    }

    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {

            if(requestMap == null && !requestMap.containsKey("email") && !requestMap.containsKey("password")){
                return apputils.getResponseEntity("Email and password are required", HttpStatus.BAD_REQUEST);
            }
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                if("true".equalsIgnoreCase(customerUserDetailsService.getUserDetail().getStatus())){
                    String token = jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                            customerUserDetailsService.getUserDetail().getRole(), customerUserDetailsService.getUserDetail().getId());
                    User user = customerUserDetailsService.getUserDetail();
                    User userresponse = new User();
                    userresponse.setId(user.getId());
                    userresponse.setEmail(user.getEmail());
                    userresponse.setName(user.getName());
                    userresponse.setRole(user.getRole());
                    Map<String, Object> object = new HashMap<>();
                    object.put("token",token);
                    object.put("user",userresponse);
                    ObjectMapper mapper = new ObjectMapper();

                    return new ResponseEntity<String>(mapper.writeValueAsString(object), HttpStatus.OK);

                }else {
                    return apputils.getResponseEntity("Account pending admin approval", HttpStatus.FORBIDDEN);
                }

            } else {
                return apputils.getResponseEntity("Authentication failed", HttpStatus.UNAUTHORIZED);

            }
        }
        catch(Exception ex){
            System.out.println("Exception: "+ex);
            return apputils.getResponseEntity("Something went wrong", HttpStatus.FORBIDDEN);
        }
    }

    public boolean validateToken(String token) {
        return jwtUtil.validate(token);
    }

    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }
}
