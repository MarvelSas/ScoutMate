package tk.marvelsas.engineeringProject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_TYPE;
import tk.marvelsas.engineeringProject.model.Attempt;
import tk.marvelsas.engineeringProject.model.Response;
import tk.marvelsas.engineeringProject.service.AttemptService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/attempt")
@Slf4j
public class AttemptController {

   private final AttemptService attemptService;



    @PostMapping("/addAttemptScoutRank")
    public ResponseEntity<Response> addAttemptScoutRank(@RequestBody Attempt attempt,@Valid @NotBlank @RequestParam String  emailScout,@Valid @NotBlank @RequestParam Integer organizationId){
        try {
            attempt.setTYPE(ATTEMPT_TYPE.SCOUT_RANK);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addAttemptScoutRank",attemptService.createAttempt(attempt,emailScout,organizationId)))
                            .message("addition Attempt ScoutRank was successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addAttemptScoutRank", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }    @PostMapping("/addAttemptScoutInstructorRank")
    public ResponseEntity<Response> addAttemptScoutInstructorRank(@RequestBody Attempt attempt,@Valid @NotBlank @RequestParam String  emailScout,@Valid @NotBlank @RequestParam Integer organizationId){
        try {
            attempt.setTYPE(ATTEMPT_TYPE.SCOUT_INSTRUCTOR_RANK);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addAttemptScoutInstructorRank",attemptService.createAttempt(attempt,emailScout,organizationId)))
                            .message("addition Attempt Scout Instructor Rank was successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addAttemptScoutInstructorRank", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @PostMapping("/addAttemptMeritbadge")
    public ResponseEntity<Response> closeAttempt(@RequestBody Attempt attempt, @Valid @NotBlank @RequestParam String  emailScout, @Valid @NotBlank @RequestParam Integer organizationId){
        try {
            attempt.setTYPE(ATTEMPT_TYPE.MERITBADGE);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addAttemptMeritbadge",attemptService.createAttempt(attempt,emailScout,organizationId)))
                            .message("addition Attempt Meritbadge was successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("addAttemptMeritbadge", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }








    @GetMapping("/meritBadges")
    public ResponseEntity<Response>getAllMeritBadges(){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllMeritBadge",attemptService.getAllMeritBadge()))
                            .message("Returned all Meritbadges")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllMeritBadge", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }



    }



    @GetMapping("/meritBadgesCategories")
    public ResponseEntity<Response>getAllMeritBadgesCategories(){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("MeritBadgesCategories",attemptService.getAllCategoryMeritBadge()))
                            .message("Returned all categories of Meritbadges")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("MeritBadgesCategories", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @GetMapping("/scoutranks")
    public ResponseEntity<Response>getAllScoutRank(){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("ScoutRanks",attemptService.getAllScoutRank()))
                            .message("Returned all ScoutRanks")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("ScoutRanks", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @GetMapping("/scoutinstructorranks")
    public ResponseEntity<Response>getAllScoutInstructorRank(){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("ScoutInstruktorRanks",attemptService.getAllScoutInstructorRank()))
                            .message("Returned all Scout Instruktor Ranks")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("ScoutInstruktorRanks", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @GetMapping("/allScoutRankAvailableForAppUserAttempt")
    public ResponseEntity<Response>getAllScoutRankAvailableForAppUserAttempt(@Valid @NotBlank @RequestParam String emailScout ){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllScoutRankAvailableForAppUserAttempt", attemptService.getAllScoutRankAvailableForAppUserAttempt(emailScout)))
                            .message("Returned Scout Ranks Available for applicant")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllScoutRankAvailableForAppUserAttempt", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }



    @GetMapping("/allScoutInstructorRankAvailableForAppUserAttempt")
    public ResponseEntity<Response>getAllScoutInstructorRankAvailableForAppUserAttempt(@Valid @NotBlank @RequestParam String emailScout ){
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllScoutRankAvailableForAppUserAttempt", attemptService.getAllScoutInstructorRankAvailableForAppUserAttempt(emailScout)))
                            .message("Returned Scout Ranks Available for applicant")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllScoutRankAvailableForAppUserAttempt", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }


    @PutMapping("/closeAttempt")
    public ResponseEntity<Response> closeAttempt(@Valid @NotBlank @RequestParam boolean ifAttemptResultIsPositive , int attemptId){
        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("endAttempt",attemptService.closeAttempt(ifAttemptResultIsPositive,attemptId)))
                            .message("Closing attempt is completed successfully")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("endAttempt", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }



    @GetMapping("/getAllMyCreatedAttempt")
    public ResponseEntity<Response>getAllCreatedAttempt(){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllCreatedAttempt", attemptService.getMyAllCreatedAttempt()))
                            .message("Returned All My Created Attempt")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllCreatedAttempt", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }
    @GetMapping("/getAllAppliedAttempt")
    public ResponseEntity<Response>getMyAllAppliedAttempt(){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getMyAllAppliedAttempt", attemptService.getMyAllAppliedAttempt()))
                            .message("Returned All My Applied Attempt")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getMyAllAppliedAttempt", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }

    @GetMapping("/getMyAllAppliedAttemptOpen")
    public ResponseEntity<Response>getMyAllAppliedAttemptOpen(){
        try{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getMyAllAppliedAttemptOpen", attemptService.getMyAllAppliedAttemptOpen()))
                            .message("Returned All My Applied Attempt Open")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getMyAllAppliedAttemptOpen", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

    }



    @GetMapping("/getAllMeritBadgeForAppUserAttempt")
    public ResponseEntity<Response>getAllMeritBadgeForAppUserAttempt(@Valid @NotBlank @RequestParam String emailScout ) {
        try {

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllMeritBadgeAvailableForAppUserAttempt", attemptService.getAllMeritBadgeForAppUserAttempt(emailScout)))
                            .message("Returned available MeritBadge For AppUser")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("getAllMeritBadgeAvailableForAppUserAttempt", false))
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
    }









    }
