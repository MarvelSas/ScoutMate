package tk.marvelsas.engineeringProject.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tk.marvelsas.engineeringProject.filter.CustomAuthenticationFilter;
import tk.marvelsas.engineeringProject.filter.CustomAuthorizationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors(Customizer.withDefaults());
        http.authorizeRequests().antMatchers("/api/v1/login").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/registration").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/registration/**").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/forgetPassword").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/forgetPassword/**").permitAll();


        //After verify
        http.authorizeRequests().antMatchers("/api/v1/appUser/admin/grantAdminPermission").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/v1/appUser/getAllUsersWithOutOrganization").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/v1/appUser/admin/getAdmins").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/v1/appUser/admin/deleteAdminPermissions").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/v1/organization/admin/deleteChiefPermission").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/api/v1/organization/admin/grantPermissionToChiefOfScout").hasAnyAuthority("ADMIN");



        //After verify
        http.authorizeRequests().antMatchers("/api/v1/appUser/getAllUsersWithOutYou").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/attempt/addAttemptScoutRank").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/attempt/addAttemptScoutInstructorRank").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/attempt/addAttemptMeritbadge").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/attempt/allScoutRankAvailableForAppUserAttempt").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/attempt/allScoutInstructorRankAvailableForAppUserAttempt").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/attempt/closeAttempt").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/attempt/getAllMeritBadgeForAppUserAttempt").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        //Order
        http.authorizeRequests().antMatchers("/api/v1/Order/addOrder").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/Order/getOrganizationOrders").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/Order/pdf/generate").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/Order/view/pdf/generate").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        //Organization
        http.authorizeRequests().antMatchers("/api/v1/organization/suborganization").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/addusertoorganization").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/delateFromOrganization").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/grantOwnerPermission").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/allUsersFromOrganization").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/getAllUsersFromOrganizationWithOutYou").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/removeOwnerPermission").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/getUserOwnedOrganizations").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/UserProfailDetails").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/allActiveLines").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/getOrganization").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/getAllOrganizationUsersAttempt").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/getSubOrganizationBelongToMainOrganization").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/changeOrganizationPhoto").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/changeOrganizationName").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/getAllOwnerFromOrganization").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/getUsersFromOutSideOrganization").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY");
        http.authorizeRequests().antMatchers("/api/v1/organization/getChiefOfScout").hasAnyAuthority("NACZELNIK","KOMENDANT CHORAGWI","KOMENDANT HUFCA","DRUŻYNOWY","ZASTĘPOWY","ADMIN");






        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT","PATCH","DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

}
