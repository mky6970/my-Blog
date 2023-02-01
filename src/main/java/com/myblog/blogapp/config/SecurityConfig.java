package com.myblog.blogapp.config;




import com.myblog.blogapp.security.CustomUserDetailService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private CustomUserDetailService userDetailService;

    public SecurityConfig(CustomUserDetailService userDetailService){

        this.userDetailService=userDetailService;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //hcd4ah
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails mukesh = User.builder().username("mukesh").password("password").roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password("admin").roles("ADMIN").build();
//         return new InMemoryUserDetailsManager(mukesh,admin);
//
//    }
}
