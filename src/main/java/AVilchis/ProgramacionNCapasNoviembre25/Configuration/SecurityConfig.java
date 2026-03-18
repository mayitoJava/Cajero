package AVilchis.ProgramacionNCapasNoviembre25.Configuration;

import AVilchis.ProgramacionNCapasNoviembre25.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login")
                .permitAll()
                .requestMatchers("/cajero").permitAll()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/cajero", true)
                .permitAll()
                ).userDetailsService(userDetailService)
                .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
