package tk.marvelsas.engineeringProject.service;


import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.marvelsas.engineeringProject.ENUMS.ACTION_TYPE;
import tk.marvelsas.engineeringProject.ENUMS.ORGANIZATION_TYPE;
import tk.marvelsas.engineeringProject.model.*;
import tk.marvelsas.engineeringProject.model.DTO.*;
import tk.marvelsas.engineeringProject.repository.ActionQueueLineRepository;
import tk.marvelsas.engineeringProject.repository.AppUserRepository;
import tk.marvelsas.engineeringProject.repository.OrganizationRepository;
import tk.marvelsas.engineeringProject.repository.RoleRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrganizationService {



    private final OrganizationRepository organizationRepository;
    private final AppUserRepository appUserRepository;

    private final RoleRepository roleRepository;

    private final ActionQueueLineRepository actionQueueLineRepository;



    public Organization createBasicOrganization(){
        Organization organization=new Organization(null,"GŁÓWNA KWATERA", ORGANIZATION_TYPE.KWATERA_GLOWNA,null,null,null,null,null,null,null,null);
        organizationRepository.save(organization);
        return organization;

    }


    public boolean createSubOrganization(Integer superiorOrganizationId, String email, String name, MultipartFile photo) throws IOException {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String photoName = LocalDateTime.now().format(formatter)+"-"+UUID.randomUUID()+"-"+photo.getOriginalFilename();


        String newPath= new FileSystemResource("src/main/resources/Images/organizationPhoto").getFile().getAbsolutePath();

        photo.transferTo(new File(newPath+"/"+ photoName));

        Organization superiorOrganization=organizationRepository.findById(superiorOrganizationId).orElseThrow(()->new IllegalStateException("Superior organization don't exist"));
        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));

        ORGANIZATION_TYPE organization_type=ORGANIZATION_TYPE.KWATERA_GLOWNA;
        String roleName = "";


        if (superiorOrganization.getOrganizationType()==ORGANIZATION_TYPE.KWATERA_GLOWNA){
            organization_type=ORGANIZATION_TYPE.CHORAGIEW;
            roleName="KOMENDANT CHORAGWI";}
        else if (superiorOrganization.getOrganizationType()==ORGANIZATION_TYPE.CHORAGIEW){
            organization_type=ORGANIZATION_TYPE.HUFIEC;
            roleName="KOMENDANT HUFCA";}
        else if (superiorOrganization.getOrganizationType()==ORGANIZATION_TYPE.HUFIEC){
            organization_type=ORGANIZATION_TYPE.DRUZYNA;
            roleName="DRUŻYNOWY";}
        else if (superiorOrganization.getOrganizationType()==ORGANIZATION_TYPE.DRUZYNA){
            organization_type=ORGANIZATION_TYPE.ZASTEP;
            roleName="ZASTĘPOWY";}
        else{
            throw new IllegalStateException("Your Organization can't have subOrganization");
        }



        Organization organization= new Organization(null,name,organization_type,null, new ArrayList<AppUser>(),null,null,null,null,null,photoName);
        organization.getOwners().add(appUser);
        superiorOrganization.getSubOrganizations().add(organization);


        Role role = new Role(null,roleName,appUser,organization);

        Role role1 = roleRepository.save(role);



        Organization organization1 = organizationRepository.save(organization);

        organizationRepository.save(organization);


        actionQueueLineRepository.save(new ActionQueueLine(superiorOrganization,appUser,role1, ACTION_TYPE.GRANTING_FUNCTION));

        actionQueueLineRepository.save(new ActionQueueLine(superiorOrganization,organization1));

        return true;

    }





    public boolean addAppUserToOrganization(String email,Integer idOrganization,String roleName){

        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));
        Organization organization=organizationRepository.findById(idOrganization).orElseThrow(()->new IllegalStateException("Organization don't exist"));
        if (roleRepository.findByAppUserRoleAndOrganizationRole(appUser,organization).isPresent()){
            throw new IllegalStateException("User with email: "+appUser.getEmail()+" already exist in Organization: "+organization.getName());
        }




        Role role=new Role(null,roleName,appUser,organization);

        roleRepository.save(role);
        return true;

    }
    public boolean deleteAppUserFromOrganization(String email,Integer idOrganization){

        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));
        Organization organization=organizationRepository.findById(idOrganization).orElseThrow(()->new IllegalStateException("Organization don't exist"));
        Role role = roleRepository.findByAppUserRoleAndOrganizationRole(appUser,organization).orElseThrow(()->new IllegalStateException("Role doesn't exist"));
        appUser.getRoles().remove(role);
        if (organization.getOwners().contains(appUser)==true){
           organization.getOwners().remove(appUser);
           organizationRepository.save(organization);

        }
        roleRepository.deleteById(role.getId());
        return true;

    }



    public boolean grantPermissionToChiefOfScout(String email){

        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));
        Organization organization=organizationRepository.findById(1).orElseThrow(()->new IllegalStateException("Main Organization didn't exist. Please rebuild server one more time"));

        if(roleRepository.findByName("NACZELNIK").isPresent()){
            throw new IllegalStateException("Chief of the scout already exist");
        }


        organization.getOwners().add(appUser);


        Role role=new Role(null,"NACZELNIK",appUser,organization);

        roleRepository.save(role);
        return true;

    }
    public AppUserDetailsDTO getChiefOfScout(){


        if(!roleRepository.findByName("NACZELNIK").isPresent()){
            throw new IllegalStateException("Chief of the scout already don't exist");
        }

        Role role=roleRepository.findByName("NACZELNIK").get();
        AppUserDetailsDTO appUserDetailsDTO= new AppUserDetailsDTO(role.getAppUserRole().getEmail(),role.getAppUserRole().getName(),role.getAppUserRole().getSurname());
        return appUserDetailsDTO;


    }

















    public boolean addOwnerToOrganization(String email,Integer idOrganization){
        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));
        Organization organization=organizationRepository.findById(idOrganization).orElseThrow(()->new IllegalStateException("Organization don't exist"));

        if (organization.getOwners().contains(appUser)==true){
            throw new IllegalStateException("This user is already the owner of the organization ");
        }
        //find user role in organization
        Role role = roleRepository.findByAppUserRoleAndOrganizationRole(appUser,organization).orElseThrow(()->new IllegalStateException("Role doesn't exist"));
        // chceck typ organization
        String roleName="";
        if (organization.getOrganizationType()==ORGANIZATION_TYPE.CHORAGIEW){
            roleName="KOMENDANT CHORAGWI";}
        else if (organization.getOrganizationType()==ORGANIZATION_TYPE.HUFIEC){
            roleName="KOMENDANT HUFCA";}
        else if (organization.getOrganizationType()==ORGANIZATION_TYPE.DRUZYNA){
            roleName="DRUŻYNOWY";}
        else if (organization.getOrganizationType()==ORGANIZATION_TYPE.ZASTEP){
            roleName="ZASTĘPOWY";}
        else if (organization.getOrganizationType()==ORGANIZATION_TYPE.KWATERA_GLOWNA){
            throw new IllegalStateException("You don't have authority to add new owner to KWATERA GŁOWNA");
        }
        else{
            throw new IllegalStateException("This TYPE of organization not exist");
        }
        role.setName(roleName);
        roleRepository.save(role);

        actionQueueLineRepository.save(new ActionQueueLine(organization,appUser,role,ACTION_TYPE.GRANTING_FUNCTION));

        organization.getOwners().add(appUser);
        organizationRepository.save(organization);
        return true;
    }



    public ArrayList<AppUserOrganizationDTO> getAllUsersFromOrganization(Integer idOrganization){

        ArrayList<AppUserOrganizationDTO> listOfAppUserDetails = new ArrayList<>();
        Organization organization=organizationRepository.findById(idOrganization).orElseThrow(()->new IllegalStateException("Organization don't exist"));

        roleRepository.findAllByOrganizationRole(organization).get().forEach((Role role)->{
        //    System.out.println(role.getAppUserRole().toString());

            listOfAppUserDetails.add(
                    new AppUserOrganizationDTO(
                            role.getAppUserRole().getName(),
                            role.getAppUserRole().getSurname(),
                            role.getAppUserRole().getScoutRankId().getName(),
                            role.getAppUserRole().getEmail(),
                            role.getName(),
                            role.getAppUserRole().getNickName()
                    ));
        });

        return listOfAppUserDetails;

    }   public ArrayList<AppUserOrganizationDTO> getAllUsersFromOrganizationWithOutYou(Integer idOrganization){

        ArrayList<AppUserOrganizationDTO> listOfAppUserDetails = new ArrayList<>();
        Organization organization=organizationRepository.findById(idOrganization).orElseThrow(()->new IllegalStateException("Organization don't exist"));
        AppUser appUser=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException("User not found"));

        roleRepository.findAllByOrganizationRole(organization).get().forEach((Role role)->{

            if (appUser.getId()!=role.getAppUserRole().getId()){

                listOfAppUserDetails.add(
                        new AppUserOrganizationDTO(
                                role.getAppUserRole().getName(),
                                role.getAppUserRole().getSurname(),
                                role.getAppUserRole().getScoutRankId().getName(),
                                role.getAppUserRole().getEmail(),
                                role.getName(),
                                role.getAppUserRole().getNickName()
                        ));

            }



        });

        return listOfAppUserDetails;
    }













    public boolean removeOwnerPermission(String email,Integer idOrganization){
        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));
        Organization organization=organizationRepository.findById(idOrganization).orElseThrow(()->new IllegalStateException("Organization don't exist"));
        Role role = roleRepository.findByAppUserRoleAndOrganizationRole(appUser,organization).orElseThrow(()->new IllegalStateException("Role doesn't exist"));
        if (!organization.getOwners().contains(appUser)){
            throw new IllegalStateException("No find user in owners grup");
        }
        actionQueueLineRepository.save(new ActionQueueLine(organization,appUser,role,ACTION_TYPE.REVOKING_FUNCTION));
        organization.getOwners().remove(appUser);
        role.setName("Harcerz");
        roleRepository.save(role);
        organizationRepository.save(organization);
        return true;


    }
    public List<Organization> getUserOwnedOrganizations(){

        ArrayList<Organization> organizationsOwned=new ArrayList<Organization>();
        AppUser appUser=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException("User not found"));

        appUser.getOwnedOrganization().forEach((Organization organization)->{
            organizationsOwned.add(organization);

        });
        return organizationsOwned;

    }


    public List<Organization> getUserOrganizations(){



        ArrayList<Organization> organizations=new ArrayList<Organization>();
        AppUser appUser=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException("User not found"));


        appUser.getRoles().forEach((Role role)->{
            organizations.add(role.getOrganizationRole());
        });
        return organizations;

    }

    public List<Organization> getAllSubOrganizations(){
        AppUser appUser=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException("User not found"));


        ArrayList subOrganizationList=new ArrayList<Organization>();

        appUser.getOwnedOrganization().forEach((Organization organization)->{
            organization.getSubOrganizations().forEach((Organization subOrganization)->{

               subOrganizationList.add(subOrganization);

            });
        });
        return subOrganizationList;

    }


    public UserProfileDetailsDTO setScoutDetails(UserProfileDetailsDTO userProfilDetails){


        AppUser appUser = appUserRepository.findByEmail(userProfilDetails.getEmail()).orElseThrow(() -> new IllegalStateException(
                "user with e-mail " + userProfilDetails.getEmail() + " does not exist")
        );

       appUser.setNickName(userProfilDetails.getNickName());
       appUser.setMeritBadges(userProfilDetails.getMeritBadges());
       appUser.setScoutRankId(userProfilDetails.getScoutRank());
       appUser.setScoutInstructorRankId(userProfilDetails.getScoutInstructorRank());
       appUserRepository.save(appUser);



        return userProfilDetails;

    }




    public List<ActiveQueueLineDTO> getAllActiveLines(Integer organizationId){


        Organization organization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Organization don't exist"));

        return actionQueueLineRepository.findAllByOrganizationAndArchived(organization,false).get().stream().map(actionQueueLine->
            new ActiveQueueLineDTO(
                    actionQueueLine.getId(),
                    actionQueueLine.getTYPE(),
                    actionQueueLine.getArchived(),
                    actionQueueLine.getAppUser()==null? null :new AppUserDetailsDTO(actionQueueLine.getAppUser().getEmail(),actionQueueLine.getAppUser().getName(),actionQueueLine.getAppUser().getSurname()),
                    actionQueueLine.getOrganization(),
                    actionQueueLine.getMeritBadge(),
                    actionQueueLine.getScoutRank(),
                    actionQueueLine.getScoutInstructorRank(),
                    actionQueueLine.getRole(),
                    actionQueueLine.getSubOrganization()

            )
        ).collect(Collectors.toList());

    }


    public Organization getOrganization(Integer organizationId){

        Organization organization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Organization don't exist"));

        return organization;

    }


    public ArrayList<UserProfileDetailsForAttemptDTO> getAllOrganizationUsersAttempt(Integer idOrganization){

        Organization organization=organizationRepository.findById(idOrganization).orElseThrow(()->new IllegalStateException("Organization don't exist"));
        AppUser creatorAttempt=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException(
                "mainUser with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"
        ));

        ArrayList<UserProfileDetailsForAttemptDTO> listOfAppUserDetails = new ArrayList<>();


        roleRepository.findAllByOrganizationRole(organization).get().forEach((Role role)->{
            //    System.out.println(role.getAppUserRole().toString());

           if (creatorAttempt!=role.getAppUserRole()){
                listOfAppUserDetails.add(new UserProfileDetailsForAttemptDTO(
                        role.getAppUserRole().getEmail(),
                        role.getAppUserRole().getName(),
                        role.getAppUserRole().getSurname(),
                        role.getAppUserRole().getScoutRankId(),
                        role.getAppUserRole().getScoutInstructorRankId()));
            }
        });
        return listOfAppUserDetails;
    }

    public List<Organization>getSubOrganizationBelongToMainOrganization(Integer idOrganization){

        Organization organization=organizationRepository.findById(idOrganization).orElseThrow(()->new IllegalStateException("Organization don't exist"));
        List subOrganization=new ArrayList(organization.getSubOrganizations());
        return subOrganization;
    }


    public boolean putPhoto(int organizationId,MultipartFile photo) throws IOException {




        Organization organization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Organization don't exist"));

        String newPath= new FileSystemResource("src/main/resources/Images/organizationPhoto").getFile().getAbsolutePath();

        if (organization.getImage()!=null){
            boolean fileDeleted= Files.deleteIfExists(Paths.get(newPath+"/"+organization.getImage()));
        }



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String photoName = LocalDateTime.now().format(formatter)+"-"+UUID.randomUUID()+"-"+photo.getOriginalFilename();


        photo.transferTo(new File(newPath+"/"+ photoName));

        organization.setImage(photoName);


        organizationRepository.save(organization);

        return true;

    }
    public boolean putChangeOrganizationName(int organizationId,String organizationName){

        Organization organization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Organization don't exist"));


        if(organizationName.length()<4){return false;}
        if(organizationName==null){return false;}


        organization.setName(organizationName);
        organizationRepository.save(organization);
        return true;
    }


    public List<AppUserDetailsDTO> getAllOwnerFromOrganization(int organizationId){

        Organization organization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Organization don't exist"));



       return organization.getOwners().stream().map( appUser -> {
        return new AppUserDetailsDTO(
                   appUser.getEmail(),
                   appUser.getName(),
                   appUser.getSurname()
           );
       }).collect(Collectors.toList());


    }

    public List<AppUserDetailsDTO> getUsersFromOutSideOrganization(int organizationId){
        Organization organization=organizationRepository.findById(organizationId).orElseThrow(()->new IllegalStateException("Organization don't exist"));

        ArrayList users=new ArrayList<AppUserDetailsDTO>();

        ArrayList<String> excludedEmail= new ArrayList<>();
        roleRepository.findAllByOrganizationRole(organization).get().forEach((Role role)->{
            excludedEmail.add(role.getAppUserRole().getEmail());
        });
        excludedEmail.add("admin@gmail.com");



        appUserRepository.findAllAppUserDetailsEnabled().get().forEach(appUserDetailsDTO -> {

            if (!excludedEmail.contains(appUserDetailsDTO.getEmail())){
                users.add(appUserDetailsDTO);
            }

        });
        return users;
    }

    public boolean deleteChiefPermission(String email){



        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));
        Organization organization = organizationRepository.findById(1).orElseThrow(()->new IllegalStateException("Organization 'GŁÓWNA KWATERA' doesn't exsit. Please reset server."));;
        appUser.getOwnedOrganization().remove(organization);


        List<Role> rolesToRemove = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            if(role.getName().equals("NACZELNIK")){
                rolesToRemove.add(role);
            }
        });

        rolesToRemove.forEach(role -> {
            appUser.getRoles().remove(role);

        });

        appUserRepository.save(appUser);


        return true;



    }





}
