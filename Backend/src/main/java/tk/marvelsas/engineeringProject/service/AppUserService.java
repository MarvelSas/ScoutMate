package tk.marvelsas.engineeringProject.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import tk.marvelsas.engineeringProject.model.*;
import tk.marvelsas.engineeringProject.model.DTO.AppUserDetailsDTO;
import tk.marvelsas.engineeringProject.model.DTO.UserProfileDetailsDTO;
import tk.marvelsas.engineeringProject.repository.AppUserRepository;
import tk.marvelsas.engineeringProject.repository.RoleRepository;
import tk.marvelsas.engineeringProject.repository.ScoutRankRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ConfirmationTokenService confirmationTokenService;

    private final ScoutRankRepository scoutRankRepository;




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("load user");
        return  appUserRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException(
                        "User with email :" + email + " not found"
                ));

    }


    public String createUser(AppUser appUser){
        log.info("Saving new User {}",appUser.getEmail());
         boolean userExist=appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExist){
            throw  new IllegalStateException("User exist in DB");
        }
        else {

            //:TODO Create validation password before b-crypt
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));


            ScoutRank rekrutRank=scoutRankRepository.findById(1).orElseThrow(()->new IllegalStateException("Rank Recrut dont exist"));
            appUser.setScoutRankId(rekrutRank);
            appUserRepository.save(appUser);
            //Generate Token for User
            String token= UUID.randomUUID().toString();
            ConfirmationToken confirmationToken =new ConfirmationToken(token,LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),appUser);
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            //TO DO: SEND EMAIL
            return  token;
        }



    }

    public UserProfileDetailsDTO getMyUserProfileDetails(){
        log.info("Return user with e-mail");

        AppUser appUser = appUserRepository.findByEmail(
                SecurityContextHolder.
                        getContext().
                        getAuthentication().
                        getName()).orElseThrow(() ->
                new IllegalStateException(
                "user with e-mail" +
                        SecurityContextHolder.
                                getContext().
                                getAuthentication().
                                getName() + " does not exist")
        );
        UserProfileDetailsDTO userProfilDetails =new UserProfileDetailsDTO(
                appUser.getEmail(),
                appUser.getName(),
                appUser.getSurname(),
                appUser.getBirthday(),
                appUser.getNickName(),
                appUser.getRoles(),
                appUser.getMeritBadges(),
                appUser.getScoutRankId(),
                appUser.getScoutInstructorRankId(),
                appUser.getPhoto()

                );
        return userProfilDetails;
    }

    public UserProfileDetailsDTO putUserProfileDetails(UserProfileDetailsDTO userProfilDetails){


        AppUser appUser = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new IllegalStateException(
                "user with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"));


        appUser.setName(userProfilDetails.getName());
        appUser.setSurname(userProfilDetails.getSurname());
        appUser.setNickName(userProfilDetails.getNickName());


        appUserRepository.save(appUser);
        userProfilDetails.setRoles(appUser.getRoles());
        userProfilDetails.setPhoto(appUser.getPhoto());
        return userProfilDetails;

    }


    public boolean putPhoto(MultipartFile photo) throws IOException {


        AppUser appUser = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new IllegalStateException(
                "user with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist"));

        String newPath= new FileSystemResource("src/main/resources/Images/appUserPhoto").getFile().getAbsolutePath();

        if (appUser.getPhoto()!=null){
            boolean fileDeleted= Files.deleteIfExists(Paths.get(newPath+"/"+appUser.getPhoto()));
        }



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String photoName = LocalDateTime.now().format(formatter)+"-"+UUID.randomUUID()+"-"+photo.getOriginalFilename();


        photo.transferTo(new File(newPath+"/"+ photoName));

        appUser.setPhoto(photoName);

        appUserRepository.save(appUser);



        return true;

    }



    public AppUser changePassword(String email,String newPassword){ //User Reset External


        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()-> new IllegalStateException(
                "user with e-mail" + email + " does not exist")
        );
        //:TODO Create validation password before b-crypt
        appUser.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(appUser);
        return appUser;
    }



    public Boolean changePassword(String newPassword){ //User Reset Internal


        //checking if SecutiryContext contains user name
        String email;

        if(SecurityContextHolder.getContext().getAuthentication().getName().isEmpty()){
            throw new IllegalStateException("User doesn't exist in secuitrycontext");
        }{
            email = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()-> new IllegalStateException(
                "user with e-mail" + email + " does not exist")
        );
        //:TODO Create validation password before b-crypt
        appUser.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(appUser);
        return true;
    }


    public Role createRole(Role role){
        return roleRepository.save(role);
    }






    public boolean addRoleAdmin(String email){


            //Checking if user exist in database before grant permission
            AppUser appUser = appUserRepository.findByEmail(email).get();

            //Checking if user has already Admin role
            appUser.getRoles().forEach(role -> {
                if(role.getName().equals("ADMIN")){
                    throw new IllegalStateException("This user has already role Admin");
                }
            });


            Role role = new Role(null, "ADMIN", null, null);
            role = createRole(role);
            addRoleToUser(appUser.getEmail(), role.getId());
            return  true;


    }



    public void addRoleToUser(String email, Integer roleId){
        AppUser user=appUserRepository.findByEmail(email).get();
        Role role=roleRepository.findById(roleId).get();
        role.setAppUserRole(user);
        roleRepository.save(role);


    }




    public int enableAppUser(String email){//Check User acond is enabled
        return appUserRepository.enableAppUser(email);
    }

    public List<AppUserDetailsDTO> getAllUsers(){
        //Mapping on AppUserDetailsDTO in repository SQL
        return appUserRepository.findAllAppUserDetails().get();

    }

    public List<AppUserDetailsDTO> getAllUsersWithOutYou(){
        //Mapping on AppUserDetailsDTO in repository SQL
        AppUser appUser=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException("User not found"));

        ArrayList banEmail=new ArrayList();
        ArrayList users=new ArrayList<AppUserDetailsDTO>();
        banEmail.add(appUser.getEmail());
        roleRepository.allAdmins().get().forEach(role -> {
                    banEmail.add(role.getAppUserRole().getEmail());
                }
        );



        appUserRepository.findAllAppUserDetailsEnabled().get().forEach(appUserDetailsDTO -> {

            if (!banEmail.contains(appUserDetailsDTO.getEmail())){
                users.add(appUserDetailsDTO);
            }

        });
        return users;



    }


    public UserProfileDetailsDTO getUserProfailDetails(String email){

        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException(
                "user with e-mail" + SecurityContextHolder.getContext().getAuthentication().getName() + " does not exist")
        );
        UserProfileDetailsDTO userProfilDetails =new UserProfileDetailsDTO(
                appUser.getEmail(),
                appUser.getName(),
                appUser.getSurname(),
                appUser.getBirthday(),
                appUser.getNickName(),
                appUser.getRoles(),
                appUser.getMeritBadges(),
                appUser.getScoutRankId(),
                appUser.getScoutInstructorRankId(),
                appUser.getPhoto()

        );
        return userProfilDetails;

    }

    public List<AppUserDetailsDTO> getAllUsersWithOutOrganization(){
        //Mapping on AppUserDetailsDTO in repository SQL


        AppUser appUser=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException("User not found"));
        ArrayList banEmail=new ArrayList();
        ArrayList users=new ArrayList<AppUserDetailsDTO>();
        banEmail.add(appUser.getEmail());
        banEmail.add("admin@gmail.com");
        roleRepository.allAdmins().get().forEach(role -> {
            banEmail.add(role.getAppUserRole().getEmail());
                }
        );


       appUserRepository.findAll().forEach(appUserRole-> {
           if (appUserRole.getRoles().isEmpty()){

               if (!banEmail.contains(appUserRole.getEmail())){
                   users.add(new AppUserDetailsDTO(
                           appUserRole.getEmail(),
                           appUserRole.getName(),
                           appUserRole.getSurname()
                           ));
               }
           }


       });
       return users;
    }




    public List<AppUserDetailsDTO> getAdmins(){
        //Mapping on AppUserDetailsDTO in repository SQL


        AppUser appUser=appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new IllegalStateException("User not found"));

        ArrayList banEmail=new ArrayList();
        ArrayList admins=new ArrayList<AppUserDetailsDTO>();
        banEmail.add(appUser.getEmail());
        banEmail.add("admin@gmail.com");


        roleRepository.allAdmins().get().forEach(role -> {

           if (!banEmail.contains(role.getAppUserRole().getEmail())){
               admins.add(new AppUserDetailsDTO(
                       role.getAppUserRole().getEmail(),
                       role.getAppUserRole().getName(),
                       role.getAppUserRole().getSurname()
               ));
           }
        }
       );


        return admins;

    }


    public boolean deleteAdminPermission(String email){

        AppUser appUser=appUserRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));


        List<Role> rolesToRemove = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            if(role.getName().equals("ADMIN")){
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















