package com.C_TechProject.auth;

import com.C_TechProject.Password.*;
import com.C_TechProject.email.EmailSender;
import com.C_TechProject.config.JwtService;
import com.C_TechProject.user.User;
import com.C_TechProject.user.UserNotFoundException;
import com.C_TechProject.user.UserRepository;
import com.C_TechProject.token.Token;
import com.C_TechProject.token.TokenRepository;
import com.C_TechProject.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public RegisterResponse register(RegisterRequest request) throws Exception  {
    if (repository.findByEmail(request.getEmail()).isPresent()) {
      throw new Exception("User with this email already exists");
    }
    var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return RegisterResponse.builder()
            .token(jwtToken)
            .build();
  }

  public changepasswordresponse changePassword(changepasswordrequest request) throws Exception {
    User user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new Exception("User not found"));

    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
      throw new Exception("Invalid current password");
    }
    if (!isValidPassword(request.getNewPassword())) {
      throw new Exception("Invalid new password");
    }


    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    repository.save(user);

    EmailSender emailSender = new EmailSender();
    String subject = "Password Update";
    String body = "Your Password Has Been Changed Successfully ";
    emailSender.sendEmail(user.getEmail(), subject, body);

    return changepasswordresponse.builder()
            .msg("password changed successfully")
            .email(user.getEmail())
            .currentPassword(request.getCurrentPassword())
            .newPassword(request.getNewPassword())
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .role(user.getRole())
            .email(user.getEmail())
            .Id(user.getId())
            .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public forgetpasswordresponse forgetPassword(forgetpasswordrequest request) throws Exception {
    User user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new Exception("User not found"));

    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);


    String resetPasswordLink = "http://localhost:4200/forgetpass2?token=" + jwtToken;


    EmailSender emailSender = new EmailSender();
    String subject = "Reset Password";
    String body = "Click the link below to reset your password: " + resetPasswordLink;
    emailSender.sendEmail(user.getEmail(), subject, body);
    var msg1 = "Here is the link";
    return forgetpasswordresponse.builder().msg(msg1).build();
  }


  public forgetpasswordresponse2 forgetPassword2(forgetpasswordrequest2 request) throws Exception {
    User user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new Exception("User not found"));


    if (!isValidPassword(request.getNewPassword())) {
      throw new Exception("Invalid password");
    }

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    repository.save(user);

    EmailSender emailSender = new EmailSender();
    String subject = "Password Reset";
    String body = "Your password has been reset successfully. Please use the new password to login.";
    emailSender.sendEmail(user.getEmail(), subject, body);

    return forgetpasswordresponse2.builder()
            .msg("Password reset successful")
            .email(user.getEmail())
            .newPassword(request.getNewPassword())
            .build();
  }


  private boolean isValidPassword(String Newpassword) {
    if (Newpassword == null || Newpassword.isEmpty()) {
      return false;
    }


    return true;
  }

  public List<User> findAllUsers() {
    return repository.findAll();
  }


  public User findUserById(Long id) {
    return repository.findUserById(id)
            .orElseThrow(() -> new UserNotFoundException
                    ("User by id " + id + " was not found"));
  }


  public User updateUser(Long id, User newUser) {
    User user = repository.findUserById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

    if (newUser.getFirstname() != null) {
      user.setFirstname(newUser.getFirstname());
    }
    if (newUser.getLastname() != null) {
      user.setLastname(newUser.getLastname());
    }
    if (newUser.getEmail() != null && !newUser.getEmail().equals(user.getEmail())) {
      Optional<User> userWithSameEmail = repository.findByEmail(newUser.getEmail());
      if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
        throw new UserNotFoundException("Email already exists for another user");
      }
      user.setEmail(newUser.getEmail());
    }

    if (newUser.getRole() != null) {
      user.setRole(newUser.getRole());
    }

    repository.save(user);
    return user;
  }

  public void deleteUser(Long id) {
    User user = repository.findUserById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    user.getTokens().clear();

    repository.delete(user);
  }

}



