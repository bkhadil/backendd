package com.system.core.config;


import com.system.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final PasswordEncoder passenc = new PasswordEncoder();
    private  final UserRepository userrep ;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userrep.findByEmail(username)
                .map(user -> {
                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            user.isEnabled(),
                            true,
                            true,
                            true,
                            user.getAuthorities()
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'existe pas"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider =new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passenc.bCryptPasswordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();

    }
}
