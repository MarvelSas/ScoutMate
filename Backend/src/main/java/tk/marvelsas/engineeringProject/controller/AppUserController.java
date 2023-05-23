package tk.marvelsas.engineeringProject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import tk.marvelsas.engineeringProject.model.AppUser;
import tk.marvelsas.engineeringProject.model.Response;
import tk.marvelsas.engineeringProject.model.DTO.UserProfileDetailsDTO;
import tk.marvelsas.engineeringProject.service.AppUserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import java.io.IOException;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/appUser")
@Slf4j
public class AppUserController  {

    private final AppUserService appUserService;
    private ServletContext servletContext;

//    @PostMapping()
//    public ResponseEntity<Response> saveAppUser(@Valid @RequestBody AppUser appUser){
//
//        try {
//
//            return ResponseEntity.ok(
//                Response.builder()
//                        .timeStamp(now())
//                        .data(of("appUser", appUserService.createUser(appUser)))
//                        .message("User was created successfully")
//                        .status(HttpStatus.OK)
//                        .statusCode(HttpStatus.OK.value())
//                        .build()
//            );
//
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body(
//                    Response.builder()
//                    .timeStamp(now())
//                    .data(of("appUser", false))
//                    .message(e.getMessage())
//                    .status(HttpStatus.BAD_REQUEST)
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .build());
//        }
//    }

    @GetMapping( "/getMyUserDetails")
    public ResponseEntity<Response> getUserProfailDetails(){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                        .timeStamp(now())
                        .data(of("userProfilDetails",appUserService.getMyUserProfileDetails()))
                        .message("UserProfailDetails returned")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                        .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("userProfilDetails",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @PutMapping("/changeMyUserDetails")
    public ResponseEntity<Response> putAppUser(@RequestBody UserProfileDetailsDTO userProfilDetails){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("userProfilDetails",appUserService.putUserProfileDetails(userProfilDetails)))
                            .message("Updated userProfailDetails")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("userProfilDetails",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }

    @PutMapping("/changeMyUserProfilePhoto")
    public ResponseEntity<Response> putAppUserPhoto(@RequestParam MultipartFile photo){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("userProfileDetailsPhoto",appUserService.putPhoto(photo)))
                            .message("Updated user Photo")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("userProfileDetailsPhoto",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }


    @PutMapping("/changePassword")
    public ResponseEntity<Response> putAppUser(@RequestBody String password){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("ChangePassword",appUserService.changePassword(password)))
                            .message("Updated userProfailDetails")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("ChangePassword",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }


    @PutMapping("/admin/grantAdminPermission")
    public ResponseEntity<Response> grantAdminPermission(@RequestParam String email){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Grant permission",appUserService.addRoleAdmin(email)))
                            .message("Admin permission Granted")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Grant permission",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<Response> getAllUsers(){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllUsers",appUserService.getAllUsers()))
                            .message("Get List of All users")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllUsers",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }


    @GetMapping("/getAllUsersWithOutYou")
    public ResponseEntity<Response> getAllUsersWithOutYou(){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllUsersWithOutYou",appUserService.getAllUsersWithOutYou()))
                            .message("Get List of All Users with out Log User")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllUsersWithOutYou",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }

    @GetMapping("/UserProfailDetails")
    public ResponseEntity<Response>getUserProfailDetails(@Valid @NotBlank @RequestParam String email ) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getUserProfailDetails", appUserService.getUserProfailDetails(email)))
                            .message("Returned User Details")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getUserProfailDetails", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }



    @GetMapping("/getAllUsersWithOutOrganization")
    public ResponseEntity<Response> getAllUsersWithOutOrganization(){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllUsersWithOutOrganization",appUserService.getAllUsersWithOutOrganization()))
                            .message("Get List of All Users with out Organization")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllUsersWithOutOrganization",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }



    @GetMapping("/admin/getAdmins")
    public ResponseEntity<Response> getAdmins(){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAdmins",appUserService.getAdmins()))
                            .message("Get List of All Admis")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAdmins",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }



    @DeleteMapping("/admin/deleteAdminPermission")
    public ResponseEntity<Response> deleteAdminPermission(@RequestParam String email){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteAdminPermission",appUserService.deleteAdminPermission(email)))
                            .message("Admin permission deleted correctly")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteAdminPermission",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }






}






