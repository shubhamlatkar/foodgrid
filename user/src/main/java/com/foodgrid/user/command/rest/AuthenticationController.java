package com.foodgrid.user.command.rest;

import com.foodgrid.common.payload.dto.request.LogIn;
import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.service.AuthenticationService;
import com.foodgrid.common.security.utility.UserTypes;
import com.foodgrid.user.command.payload.dto.UserSignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PutMapping("/{endpoint.authentication.signup}")
    public ResponseEntity<String> signupUser(@Valid @RequestBody UserSignUp signupRequest, BindingResult result) {
        try {
            return authenticationService.signup(
                    new SignUp(
                            signupRequest.getUsername(),
                            signupRequest.getEmail(),
                            signupRequest.getRoles(),
                            signupRequest.getPassword(),
                            signupRequest.getPhone(),
                            UserTypes.USER
                    ), result);
        } catch (Exception e) {
            return new ResponseEntity<>("Some exception caught", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{endpoint.authentication.autoLogin}")
    public ResponseEntity<Object> tryAutoLogin() {
        return ResponseEntity.ok().body("Authenticated");
    }

    @GetMapping("/{endpoint.authentication.logout}")
    public ResponseEntity<String> logout() {
        return authenticationService.logOut();
    }

    @GetMapping("/{endpoint.authentication.logoutAll}")
    public ResponseEntity<String> logoutAll() {
        return authenticationService.logoutAll();
    }

    @PostMapping("/{endpoint.authentication.login}")
    public ResponseEntity<Object> getJwtToken(@RequestBody LogIn request) {
        try {
            return authenticationService.login(request);
        } catch (Exception e) {
            return new ResponseEntity<>("Some exception caught", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
