package tk.marvelsas.engineeringProject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_STATUS;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_TYPE;
import tk.marvelsas.engineeringProject.model.*;
import tk.marvelsas.engineeringProject.model.DTO.AppUserDetailsDTO;
import tk.marvelsas.engineeringProject.model.DTO.AttemptDTO;
import tk.marvelsas.engineeringProject.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class AttemptService {

    private final MeritBadgeRepository meritBadgeRepository;
    private final CategoryMeritBadgeRepository categoryMeritBadgeRepository;
    private final ScoutRankRepository scoutRankRepository ;
    private final ScoutInstructorRankRepository scoutInstructorRankRepository;
    private final AppUserRepository appUserRepository;
    private final OrganizationRepository organizationRepository;

    private final AttemptRepository attemptRepository;

    private final ActionQueueLineRepository actionQueueLineRepository;
    public AttemptDTO createAttempt(Attempt attempt ,String emailScout,Integer organizationId){

        AppUser creatorAttempt=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
                "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));
        AppUser applicantAttempt=appUserRepository.findByEmail(emailScout).orElseThrow(()->new IllegalStateException(
                "applicantsUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));
        Organization attemptOrganization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Organization don't exist"));


        Attempt attemptNew;
        if(attempt.getAttemptTasks().isEmpty()){
            throw  new IllegalArgumentException("You can't create attempt without tasks");
        }
        if (attempt.getTYPE()==ATTEMPT_TYPE.SCOUT_RANK){
            if (attempt.getScoutRankId()==null){
                throw  new IllegalArgumentException("SCOUT_RANK can't be null");
            }
             attemptNew=new Attempt(null,attempt.getName(),creatorAttempt,applicantAttempt,attempt.getAttemptTasks(),attempt.getScoutRankId());
        }
        else if (attempt.getTYPE()==ATTEMPT_TYPE.SCOUT_INSTRUCTOR_RANK) {
            if (attempt.getScoutInstructorRankId()==null){
                throw  new IllegalArgumentException("SCOUT_INSTRUCTOR_RANK can't be null");
            }
             attemptNew=new Attempt(null,attempt.getName(),creatorAttempt,applicantAttempt,attempt.getAttemptTasks(),attempt.getScoutInstructorRankId());
        }
        else if (attempt.getTYPE()==ATTEMPT_TYPE.MERITBADGE) {
            if (attempt.getMeritBadgeId()==null){
                throw  new IllegalArgumentException("MERITBADGE can't be null");
            }
            attemptNew=new Attempt(null,attempt.getName(),creatorAttempt,applicantAttempt,attempt.getAttemptTasks(),attempt.getMeritBadgeId());
        }
        else {throw  new IllegalArgumentException("ATTEMPT_TYPE ERROR");}


        attemptNew.setOrganization(attemptOrganization);
        attemptNew = attemptRepository.save(attemptNew);


        return new AttemptDTO(
                attemptNew.getId(),
                attemptNew.getName(),
                attemptNew.getAttemptTasks(),
                new AppUserDetailsDTO(attemptNew.getCreatorId().getEmail(),attemptNew.getCreatorId().getName(),attemptNew.getCreatorId().getSurname()),
                new AppUserDetailsDTO(attemptNew.getApplicantId().getEmail(),attemptNew.getApplicantId().getName(),attemptNew.getApplicantId().getSurname()),
                attemptNew.getOrganization(),
                attemptNew.getScoutRankId(),
                attemptNew.getScoutInstructorRankId(),
                attemptNew.getMeritBadgeId(),
                attemptNew.getSTATUS(),
                attemptNew.getTYPE(),
                attemptNew.isArchived()
                );

    }


    public boolean closeAttempt(boolean ifAttemptResultIsPositive, int atemptId){

       Attempt attempt =  attemptRepository.findById(atemptId).orElseThrow(() -> new IllegalArgumentException("Attempt with this id :"+ atemptId + " doesn't exist"));

       if(attempt.getSTATUS() == ATTEMPT_STATUS.CLOSE_POSITIVELY || attempt.getSTATUS() == ATTEMPT_STATUS.CLOSE_NEGATIVELY){
           throw new IllegalArgumentException("Attempt with id :"+ atemptId + " is already closed");
       }

       AppUser aplicantAppUser = attempt.getApplicantId();


        if (ifAttemptResultIsPositive) {
            attempt.setSTATUS(ATTEMPT_STATUS.CLOSE_POSITIVELY);


            if(attempt.getTYPE()==ATTEMPT_TYPE.MERITBADGE){


                aplicantAppUser.getMeritBadges().add(attempt.getMeritBadgeId());

                actionQueueLineRepository.save(new ActionQueueLine(aplicantAppUser,attempt.getOrganization(),attempt.getMeritBadgeId(),attempt));
                System.out.println();

            } else if (attempt.getTYPE()==ATTEMPT_TYPE.SCOUT_RANK) {

                actionQueueLineRepository.save(new ActionQueueLine(aplicantAppUser,attempt.getOrganization(),attempt.getScoutRankId(),attempt));

                aplicantAppUser.setScoutRankId(attempt.getScoutRankId());



                
            } else if (attempt.getTYPE()==ATTEMPT_TYPE.SCOUT_INSTRUCTOR_RANK) {




                actionQueueLineRepository.save(new ActionQueueLine(aplicantAppUser,attempt.getOrganization(),attempt.getScoutInstructorRankId(),attempt,aplicantAppUser.getScoutInstructorRankId()));
                aplicantAppUser.setScoutInstructorRankId(attempt.getScoutInstructorRankId());


            }


        } else {
            attempt.setSTATUS(ATTEMPT_STATUS.CLOSE_NEGATIVELY);
            if(attempt.getTYPE()==ATTEMPT_TYPE.MERITBADGE){
                actionQueueLineRepository.save(new ActionQueueLine(aplicantAppUser,attempt.getOrganization(),attempt.getMeritBadgeId(),attempt));
            } else if (attempt.getTYPE()==ATTEMPT_TYPE.SCOUT_RANK) {
                actionQueueLineRepository.save(new ActionQueueLine(aplicantAppUser,attempt.getOrganization(),attempt.getScoutRankId(),attempt));
            } else if (attempt.getTYPE()==ATTEMPT_TYPE.SCOUT_INSTRUCTOR_RANK) {
                actionQueueLineRepository.save(new ActionQueueLine(aplicantAppUser,attempt.getOrganization(),attempt.getScoutInstructorRankId(),attempt,null));
            }
        }

        attempt.setArchived(true);

        attemptRepository.save(attempt);

        return true;


    }



    public List<MeritBadge> getAllMeritBadge(){
        return meritBadgeRepository.findAll();
    }

    public List<CategoryMeritBadge> getAllCategoryMeritBadge(){
        return categoryMeritBadgeRepository.findAll();
    }


    public List<ScoutRank>getAllScoutRank(){return scoutRankRepository.findAll();}

    public List<ScoutInstructorRank>getAllScoutInstructorRank(){return scoutInstructorRankRepository.findAll();}

    public List<ScoutRank>getAllScoutRankAvailableForAppUserAttempt(String emailScout){
        AppUser creatorAttempt=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
                "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));
        AppUser applicantAttempt=appUserRepository.findByEmail(emailScout).orElseThrow(()->new IllegalStateException(
                "applicantsUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));



        if (creatorAttempt.getScoutRankId()==null){
            throw new IllegalStateException("creatorAttempt dont have Scout Rank");
        }
        List<ScoutRank> filtredScoutRanks=scoutRankRepository.findAll();
        List<ScoutRank> newlist;
        if (applicantAttempt.getScoutRankId()==null){

             newlist=filtredScoutRanks.stream().filter(fi->fi.getLevel()<=creatorAttempt.getScoutRankId().getLevel()).collect(Collectors.toList());
        }
        else {


            if (creatorAttempt.getScoutRankId().getLevel()<applicantAttempt.getScoutRankId().getLevel()){
                throw new IllegalStateException("You can't create Attempt to  Scout with higher rank's then yours");
            }

            if (creatorAttempt.getScoutRankId().getLevel()==applicantAttempt.getScoutRankId().getLevel()){
                throw new IllegalStateException("You can't create Attempt to  Scout with equal rank's to yours");
            }

            else {
                newlist=filtredScoutRanks.stream().filter(f->{
                    if (f.getLevel()<=creatorAttempt.getScoutRankId().getLevel() && f.getLevel()>applicantAttempt.getScoutRankId().getLevel()){
                        return true;
                    }
                    return false;

                }).collect(Collectors.toList());
            }
        }




        return newlist;


    }




    public List<ScoutInstructorRank>getAllScoutInstructorRankAvailableForAppUserAttempt(String emailScout){
        AppUser creatorAttempt=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
                "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));
        AppUser applicantAttempt=appUserRepository.findByEmail(emailScout).orElseThrow(()->new IllegalStateException(
                "applicantsUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));



        if (creatorAttempt.getScoutInstructorRankId()==null){
            throw new IllegalStateException("creatorAttempt dont have Scout Instructor Rank");
        }
        List<ScoutInstructorRank> filtredScoutInstructorRanks=scoutInstructorRankRepository.findAll();
        List<ScoutInstructorRank> newlist;
        if (applicantAttempt.getScoutInstructorRankId()==null){

            newlist=filtredScoutInstructorRanks.stream().filter(fi->fi.getLevel()<=creatorAttempt.getScoutInstructorRankId().getLevel()).collect(Collectors.toList());
        }
        else {

            if (creatorAttempt.getScoutInstructorRankId().getLevel()<applicantAttempt.getScoutInstructorRankId().getLevel()){
                throw new IllegalStateException("You can't create Attempt to  Scout with higher rank's then yours");
            }
            if (creatorAttempt.getScoutInstructorRankId().getLevel()==applicantAttempt.getScoutInstructorRankId().getLevel()){
                throw new IllegalStateException("You can't create Attempt to  Scout with equal rank's to yours");
            }
            else {
                newlist=filtredScoutInstructorRanks.stream().filter(f->{
                    if (f.getLevel()<=creatorAttempt.getScoutInstructorRankId().getLevel() && f.getLevel()>applicantAttempt.getScoutInstructorRankId().getLevel()){
                        return true;
                    }
                    return false;

                }).collect(Collectors.toList());
            }
        }




        return newlist;


    }

    public List<AttemptDTO> getMyAllCreatedAttempt(){

        AppUser creatorAttempt=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
                "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));


      return attemptRepository.findAllByCreatorId(creatorAttempt).get().
              stream().
              map(attempt -> new AttemptDTO(
                attempt.getId(),
                attempt.getName(),
                attempt.getAttemptTasks(),
                new AppUserDetailsDTO(attempt.getCreatorId().getEmail(),attempt.getCreatorId().getName(),attempt.getCreatorId().getSurname()),
                new AppUserDetailsDTO(attempt.getApplicantId().getEmail(),attempt.getApplicantId().getName(),attempt.getApplicantId().getSurname()),
                attempt.getOrganization(),
                attempt.getScoutRankId(),
                attempt.getScoutInstructorRankId(),
                attempt.getMeritBadgeId(),
                attempt.getSTATUS(),
                attempt.getTYPE(),
                attempt.isArchived()
            )).collect(Collectors.toList());

    }
    public List<AttemptDTO> getMyAllAppliedAttempt(){

        AppUser creatorAttempt=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
                "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));


      return attemptRepository.findAllByApplicantId(creatorAttempt).get().
              stream().
              map(attempt -> new AttemptDTO(
                attempt.getId(),
                attempt.getName(),
                attempt.getAttemptTasks(),
                new AppUserDetailsDTO(attempt.getCreatorId().getEmail(),attempt.getCreatorId().getName(),attempt.getCreatorId().getSurname()),
                new AppUserDetailsDTO(attempt.getApplicantId().getEmail(),attempt.getApplicantId().getName(),attempt.getApplicantId().getSurname()),
                attempt.getOrganization(),
                attempt.getScoutRankId(),
                attempt.getScoutInstructorRankId(),
                attempt.getMeritBadgeId(),
                attempt.getSTATUS(),
                attempt.getTYPE(),
                attempt.isArchived()
            )).collect(Collectors.toList());

    }





  public List<MeritBadge>  getAllMeritBadgeForAppUserAttempt(String emailScout){
      AppUser applicantAttempt=appUserRepository.findByEmail(emailScout).orElseThrow(()->new IllegalStateException(
              "applicantsUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
      ));
        List<MeritBadge> availableMeritBadge=new ArrayList<MeritBadge>();
        List<MeritBadge> allMeritBadge=meritBadgeRepository.findAll();
        if (applicantAttempt.getMeritBadges()==null){
            return allMeritBadge;
        }
        else {
            availableMeritBadge=allMeritBadge.stream().filter((fi)->!applicantAttempt.getMeritBadges().contains(fi)).collect(Collectors.toList());
            return availableMeritBadge;
        }

  }

    public List<AttemptDTO> getMyAllAppliedAttemptOpen(){

        AppUser creatorAttempt=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
                "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));


        return attemptRepository.findAllByApplicantIdAndSTATUS(creatorAttempt,ATTEMPT_STATUS.OPEN).get().
                stream().
                map(attempt -> new AttemptDTO(
                        attempt.getId(),
                        attempt.getName(),
                        attempt.getAttemptTasks(),
                        new AppUserDetailsDTO(attempt.getCreatorId().getEmail(),attempt.getCreatorId().getName(),attempt.getCreatorId().getSurname()),
                        new AppUserDetailsDTO(attempt.getApplicantId().getEmail(),attempt.getApplicantId().getName(),attempt.getApplicantId().getSurname()),
                        attempt.getOrganization(),
                        attempt.getScoutRankId(),
                        attempt.getScoutInstructorRankId(),
                        attempt.getMeritBadgeId(),
                        attempt.getSTATUS(),
                        attempt.getTYPE(),
                        attempt.isArchived()
                )).collect(Collectors.toList());

    }
















}
