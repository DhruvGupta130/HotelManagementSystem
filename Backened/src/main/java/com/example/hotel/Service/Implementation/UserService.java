package com.example.hotel.Service.Implementation;

import com.example.hotel.DTO.LoginRequest;
import com.example.hotel.DTO.Response;
import com.example.hotel.DTO.UserDTO;
import com.example.hotel.Entity.UserEntity;
import com.example.hotel.Exception.HotelException;
import com.example.hotel.Repository.UserRepo;
import com.example.hotel.Service.Interface.IUserService;
import com.example.hotel.Service.Utils.JWTUtils;
import com.example.hotel.Service.Utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));
            UserEntity user = userRepo.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new HotelException("User Not Found"));
            String token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("1 day");
            response.setMessage("Successfully logged in");
        } catch (Exception e) {
            response.setStatusCode(404);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response register(UserEntity userEntity) {
        Response response = new Response();
        try {
            if (userEntity.getRole()==null || userEntity.getRole().isBlank()) {
                userEntity.setRole("USER");
            }
            if (userRepo.existsByEmail(userEntity.getEmail())) {
                throw new HotelException("Email Already Exists");
            }
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            UserEntity user = userRepo.save(userEntity);
            UserDTO userDTO = Utils.mapUserEntityToDTO(user);
            response.setStatusCode(200);
            response.setUser(userDTO);
        }catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<UserEntity> users = userRepo.findAll();
            List<UserDTO> userDTOS = Utils.mapUserEntityToDTOList(users);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved all users");
            response.setUserList(userDTOS);
        }catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(long userId) {
        Response response = new Response();
        try {
            UserEntity user = userRepo.findById(userId)
                    .orElseThrow(() -> new HotelException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToDTOPlusBookings(user);
            response.setStatusCode(200);
            response.setUser(userDTO);
            response.setMessage("Successfully retrieved user booking history");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(long userId) {
        Response response = new Response();
        try {
            userRepo.findById(userId)
                    .orElseThrow(() -> new HotelException("User Not Found"));
            userRepo.deleteById(userId);
            response.setStatusCode(200);
            response.setMessage("Successfully deleted user");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateUser(Long id, UserEntity userEntity) {
        Response response = new Response();
        try {
            UserEntity user = userRepo.findById(id)
                    .orElseThrow(() -> new HotelException("User Not Found"));
            if(!user.getEmail().equalsIgnoreCase(userEntity.getEmail())){
                response.setStatusCode(400);
                response.setMessage("Email ID cannot be changed");
                return response;
            }
            if (userEntity.getRole()==null || userEntity.getRole().isBlank()) {
                user.setRole("USER");
            }
            user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            user.setName(userEntity.getName());
            user.setEmail(userEntity.getEmail());
            user.setPhoneNumber(userEntity.getPhoneNumber());
            UserEntity savedUser = userRepo.save(user);
            UserDTO userDTO = Utils.mapUserEntityToDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);
        }catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(long userId) {
        Response response = new Response();
        try {
            UserEntity user = userRepo.findById(userId)
                    .orElseThrow(() -> new HotelException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToDTO(user);
            response.setStatusCode(200);
            response.setUser(userDTO);
            response.setMessage("Successfully retrieved user");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserInfo(String email) {
        Response response = new Response();
        try {
            UserEntity user = userRepo.findByEmail(email)
                    .orElseThrow(() -> new HotelException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToDTO(user);
            response.setStatusCode(200);
            response.setUser(userDTO);
            response.setMessage("Successfully retrieved user");
        } catch (Exception e) {
            response.setStatusCode(400);
            response.setMessage("Something went wrong"+e.getMessage());
        }
        return response;
    }
}
