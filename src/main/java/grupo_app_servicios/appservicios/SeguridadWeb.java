package grupo_app_servicios.appservicios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadWeb {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                (authorize) -> authorize
                    .requestMatchers("/dashboard").hasAnyRole("ADMIN","SUPER")
                    .requestMatchers("/css/", "/js/", "/img/", "/**").permitAll()
                    .anyRequest().permitAll()
            )
            //Login usuario
            .formLogin(
                (form) -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/logincheck")
                    .usernameParameter("email")
                    .passwordParameter("contrasena")
                    .defaultSuccessUrl("/perfil", true)
                    .permitAll()
            )
            // //Login Proveedor
            // .formLogin(
            //     (form) -> form
            //         .loginPage("/loginProveedor")
            //         .loginProcessingUrl("/logincheckProveedor")
            //         .usernameParameter("email")
            //         .passwordParameter("contrasena")
            //         .defaultSuccessUrl("/perfilProveedor", true)
            //         .permitAll()
            // )

            .logout(
                (logout) -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            )
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
