package tk.marvelsas.engineeringProject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.marvelsas.engineeringProject.model.AppUser;


import java.time.LocalDate;
import java.util.ArrayList;

import tk.marvelsas.engineeringProject.model.Role;
import tk.marvelsas.engineeringProject.model.ScoutInstructorRank;
import tk.marvelsas.engineeringProject.model.ScoutRank;
import tk.marvelsas.engineeringProject.repository.AppUserRepository;
import tk.marvelsas.engineeringProject.repository.ScoutInstructorRankRepository;
import tk.marvelsas.engineeringProject.repository.ScoutRankRepository;
import tk.marvelsas.engineeringProject.service.AppUserService;
import tk.marvelsas.engineeringProject.service.OrganizationService;


@SpringBootApplication
@ServletComponentScan
public class EngineeringProjectApplication extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EngineeringProjectApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(EngineeringProjectApplication.class, args);
	}



	@Bean
	CommandLineRunner run(AppUserService appUserService, AppUserRepository appUserRepository, OrganizationService organizationService, ScoutRankRepository scoutRankRepository, ScoutInstructorRankRepository scoutInstructorRankRepository){
		return  args -> {
			ScoutRank rekrutRank=scoutRankRepository.findById(1).orElseThrow(()->new IllegalStateException("Rank Recrut dont exist"));
			ScoutRank HRRank=scoutRankRepository.findById(7).orElseThrow(()->new IllegalStateException("Rank HR dont exist"));

			ScoutInstructorRank PHMScoutInstructorRank = scoutInstructorRankRepository.findById(3).orElseThrow(()->new IllegalStateException("Rank PHM dont exist"));


			//Creating first Admin account
			if(!appUserRepository.findByEmail("admin@gmail.com").isPresent()){

				AppUser appUser = new AppUser("admin@gmail.com","admin123", "Marvel","Sas", LocalDate.now(),"adminNickName",new ArrayList<Role>());
				appUser.setEnabled(true);
				appUserService.createUser(appUser);
				appUserService.addRoleAdmin(appUser.getEmail());

			}
			//Create Temporary User 1
			if(!appUserRepository.findByEmail("zdzislaw1@gmail.com").isPresent()){

				AppUser appUser1 = new AppUser("zdzislaw1@gmail.com","zdzislaw1", "zdzislaw","Sas", LocalDate.now(),"adminNickName",new ArrayList<Role>());
				appUser1.setEnabled(true);
				appUser1.setScoutRankId(HRRank);
				appUser1.setScoutInstructorRankId(PHMScoutInstructorRank);
				appUserService.createUser(appUser1);



			}


			//Create Temporary User2
			if(!appUserRepository.findByEmail("adam2@gmail.com").isPresent()){

				AppUser appUser1 = new AppUser("adam2@gmail.com","adam2", "adam","Sas", LocalDate.now(),"adminNickName",new ArrayList<Role>());
				appUser1.setEnabled(true);
				appUser1.setScoutRankId(rekrutRank);
				appUserService.createUser(appUser1);


			}


			//Create Temporary User3
			if(!appUserRepository.findByEmail("ala3@gmail.com").isPresent()){

				AppUser appUser1 = new AppUser("ala3@gmail.com","ala3", "ala","Sas", LocalDate.now(),"adminNickName",new ArrayList<Role>());
				appUser1.setEnabled(true);
				appUser1.setScoutRankId(rekrutRank);
				appUserService.createUser(appUser1);


			}




			//Creating first Organization
			organizationService.createBasicOrganization();
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncode(){
		return new  BCryptPasswordEncoder();
	}







}



