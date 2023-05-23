package tk.marvelsas.engineeringProject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.marvelsas.engineeringProject.model.Response;
import tk.marvelsas.engineeringProject.model.DTO.UserProfileDetailsDTO;
import tk.marvelsas.engineeringProject.service.OrganizationService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/organization")
@Slf4j
public class OrganizationController {

    private final OrganizationService organizationService;



    @PostMapping("/suborganization")
    public ResponseEntity<Response> saveSubOrganization(@Valid @NotNull @RequestParam Integer superiorOrganizationId,
                                                        @Valid @NotBlank @RequestParam String email,
                                                        @Valid @NotBlank  @RequestParam String name,
                                                        @RequestParam("myFile") MultipartFile photo){

        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("organization", organizationService.createSubOrganization(
                                    superiorOrganizationId,
                                    email,
                                    name,
                                    photo)))
                            .message("Organization was created successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("organization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PostMapping("/addusertoorganization")
    public ResponseEntity<Response> addToOrganization(@Valid @NotBlank @RequestParam String email,
                                                      @Valid @NotNull @RequestParam Integer idOrganization,
                                                      @Valid @NotBlank  @RequestParam String nameRole){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addAppUserToOrganization",organizationService.addAppUserToOrganization(email,idOrganization, nameRole)))
                            .message("addition Organization was successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addAppUserToOrganization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }


    @Transactional
    @DeleteMapping("/delateFromOrganization")
    public ResponseEntity<Response>delateFromOrganization(@Valid @NotBlank @RequestParam String email,
                                                          @Valid @NotNull @RequestParam Integer idOrganization){


        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteAppUserFromOrganization",organizationService.deleteAppUserFromOrganization(email,idOrganization)))
                            .message("User deleted from Organization")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteAppUserFromOrganization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }








    @PostMapping("/admin/grantPermissionToChiefOfScout")
    public ResponseEntity<Response> grantPermissionToChiefOfScout(@Valid @NotBlank @RequestParam String email){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("grantPermissionToChiefOfScout",organizationService.grantPermissionToChiefOfScout(email)))
                            .message("The chief permission granted successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("grantAuthorityToChiefOfScout", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/grantOwnerPermission")
    public ResponseEntity<Response>grantPermission(@Valid @NotBlank @RequestParam String email,
                                                   @Valid @NotNull @RequestParam Integer idOrganization){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("grantOwnerPermission",organizationService.addOwnerToOrganization(email,idOrganization)))
                            .message("The user is now owner")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("grantOwnerPermission", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }



    }


    @GetMapping("/allUsersFromOrganization")
    public ResponseEntity<Response>allUsersFromOrganization(@Valid @NotNull @RequestParam Integer idOrganization) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("allUsersFromOrganization", organizationService.getAllUsersFromOrganization(idOrganization)))
                            .message("All users list from Organization  ")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("allUsersFromOrganization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
    }
    @GetMapping("/getAllUsersFromOrganizationWithOutYou")
    public ResponseEntity<Response>getAllUsersFromOrganizationWithOutYou(@Valid @NotNull @RequestParam Integer idOrganization){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllUsersFromOrganizationWithOutYou",organizationService.getAllUsersFromOrganizationWithOutYou(idOrganization)))
                            .message("All users list from Organization without log user ")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllUsersFromOrganizationWithOutYou", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @PatchMapping("/removeOwnerPermission")
    public ResponseEntity<Response>removeOwnerPermission(@Valid @NotBlank @RequestParam String email,
                                                   @Valid @NotNull @RequestParam Integer idOrganization) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("removeOwnerPermission", organizationService.removeOwnerPermission(email, idOrganization)))
                            .message("Owner Permission was remove")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("removeOwnerPermission", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }
    @GetMapping("/getUserOwnedOrganizations")
    public ResponseEntity<Response>getUserOwnedOrganizations() {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("ownedOrganization", organizationService.getUserOwnedOrganizations()))
                            .message("All your owned organization returned")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("ownedOrganization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }



    @GetMapping("/getUserOrganizations")//NotUse
    public ResponseEntity<Response>getUserOrganizations() {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("organization", organizationService.getUserOrganizations()))
                            .message("All your organization returned")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("organization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }
    @GetMapping("/getAllSubOrganizations") //NotUse
    public ResponseEntity<Response>getAllSubOrganizations() {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllSubOrganizations", organizationService.getAllSubOrganizations()))
                            .message("All your sub organization returned")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllSubOrganizations", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }

    @PutMapping("/UserProfailDetails")
    public ResponseEntity<Response>setScoutDetails(@RequestBody UserProfileDetailsDTO userProfilDetails){
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("setScoutDetails", organizationService.setScoutDetails(userProfilDetails)))
                            .message("Set Scout details Success")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("setScoutDetails", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }


    @GetMapping("/allActiveLines")
    public ResponseEntity<Response>getAllActiveLines(@Valid @NotNull @RequestParam Integer idOrganization){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getActiveTask", organizationService.getAllActiveLines(idOrganization)))
                            .message("Returned User Details")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getActiveTask", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }


    @GetMapping("/getOrganization")
    public ResponseEntity<Response>getOrganization(@Valid @NotNull @RequestParam Integer idOrganization) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("organization", organizationService.getOrganization(idOrganization)))
                            .message("Organization details returned")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("organization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }
    @GetMapping("/getAllOrganizationUsersAttempt")
    public ResponseEntity<Response>getAllOrganizationUsersAttempt(@Valid @NotNull @RequestParam Integer idOrganization) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllOrganizationUsersAttempt", organizationService.getAllOrganizationUsersAttempt(idOrganization)))
                            .message("All Organization Users for Attempt")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllOrganizationUsersAttempt", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @GetMapping("/getSubOrganizationBelongToMainOrganization")
    public ResponseEntity<Response>getSubOrganizationBelongToMainOrganization(@Valid @NotNull @RequestParam Integer idOrganization) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getSubOrganizationBelongToMainOrganization", organizationService.getSubOrganizationBelongToMainOrganization(idOrganization)))
                            .message("All SubOrganization belong to MainOrganization")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getSubOrganizationBelongToMainOrganization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }


    @PutMapping("/changeOrganizationPhoto")
    public ResponseEntity<Response> putOrganizationImage(@RequestParam Integer organizationId, @RequestBody MultipartFile photo){


        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("organizationPhoto",organizationService.putPhoto(organizationId,photo)))
                            .message("Updated organization Photo")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("organizationPhoto",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }
    @PutMapping("/changeOrganizationName")
    public ResponseEntity<Response> putOrganizationName(@RequestParam Integer organizationId, @RequestParam String organizationName){

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("putOrganizationName",organizationService.putChangeOrganizationName(organizationId,organizationName)))
                            .message("Updated organization Name")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("putOrganizationName",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


    }


    @GetMapping("/getAllOwnerFromOrganization")
    public ResponseEntity<Response> getAllOwnerFromOrganization(@Valid @NotNull @RequestParam Integer idOrganization) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllOwnerFromOrganization", organizationService.getAllOwnerFromOrganization(idOrganization)))
                            .message("Returned all owners from organization")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllOwnerFromOrganization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @GetMapping("/getUsersFromOutSideOrganization")
    public ResponseEntity<Response> getUsersFromOutSideOrganization(@Valid @NotNull @RequestParam Integer organizationId) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getUsersFromOutSideOrganization", organizationService.getUsersFromOutSideOrganization(organizationId)))
                            .message("Get Users From OutSide Organization")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getUsersFromOutSideOrganization", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }


    @GetMapping("/getChiefOfScout")
    public ResponseEntity<Response> getChiefOfScout() {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getChiefOfScout", organizationService.getChiefOfScout()))
                            .message("Get Chief of Scout")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getChiefOfScout", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }


    @DeleteMapping("/admin/deleteChiefPermission")
    public ResponseEntity<Response> deleteChiefPermission(@RequestParam String email) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteChiefPermission", organizationService.deleteChiefPermission(email)))
                            .message("Chief permission deleted correctly")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("deleteChiefPermission", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }





}
