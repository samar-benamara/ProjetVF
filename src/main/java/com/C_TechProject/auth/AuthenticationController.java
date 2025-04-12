package com.C_TechProject.auth;

import com.C_TechProject.Password.*;
import com.C_TechProject.user.User;
import com.C_TechProject.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;


@GetMapping("/users")
public ResponseEntity<List<User>> getAllusers() {
  List<User> user =service.findAllUsers();
  return new ResponseEntity<>(user,HttpStatus.OK);
}
  @GetMapping("/find/{id}")
  public ResponseEntity<User> getUserById (@PathVariable("id") Long id) {
    try {
      User user = service.findUserById(id);
      return ResponseEntity.ok(user);
    } catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
}

  @PutMapping("/update/{id}")
  public void updateUser(@PathVariable Long id, @RequestBody User user) {
    try {
      service.updateUser(id, user);
    } catch (UserNotFoundException e) {

      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }



  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    try {
    service.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.OK);  }
    catch (UserNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }



  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
      RegisterResponse response = service.register(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("/changepass")
  public ResponseEntity<changepasswordresponse> changePassword(
          @RequestBody changepasswordrequest request) throws Exception {
    return ResponseEntity.ok(service.changePassword(request));
  }
  @PostMapping("/forgetpass2")
  public ResponseEntity<forgetpasswordresponse2> forgetPassword2(
          @RequestBody forgetpasswordrequest2 request) throws Exception {
    return ResponseEntity.ok(service.forgetPassword2(request));
  }
  @PostMapping("/forgetpass")
  public ResponseEntity<forgetpasswordresponse> forgetPassword(
          @RequestBody forgetpasswordrequest request) {
    try {
      return ResponseEntity.ok(service.forgetPassword(request));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new forgetpasswordresponse("Error occurred while processing request"));
    }
  }



}

