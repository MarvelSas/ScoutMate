package tk.marvelsas.engineeringProject.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.marvelsas.engineeringProject.model.AppUser;
import tk.marvelsas.engineeringProject.model.RegistrationRequest;
import tk.marvelsas.engineeringProject.model.Response;
import tk.marvelsas.engineeringProject.service.RegistrationService;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

private RegistrationService registrationService;


    @PostMapping
    public ResponseEntity<Response> register(@RequestBody RegistrationRequest request ){


        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Confirmation token :",registrationService.register(request)))
                            .message("User created")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Confirmation token :",false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmatinToken(token);
    }


}
