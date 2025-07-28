package com.alex.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class DataBaseWebSecurity {

    @Bean
    public UserDetailsManager usersCustom(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select username,password,estatus from usuarios u where username=?");
        users.setAuthoritiesByUsernameQuery("select u.username,p.perfil from usuarioperfil up " +
                "inner join usuarios u on u.id = up.id_usuario " +
                "inner join perfiles p on p.id = up.id_perfil " +
                "where u.username=?");
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authoriize -> authoriize 
        // los rtecursos estatiocos no requieren autenticacion
        .requestMatchers("/bootstrap/**", "/images/**", "/tinymce/**", "/logos/**").permitAll()

        //las vistas publicas no requieren autenticacion
        .requestMatchers("/", "/signup", "/search", "/bcrypt/**","/vacantes/view/**").permitAll()

        //Quien tiene acceso
        .requestMatchers("/solicitudes/create/**", "/solicitudes/save/**").hasAnyAuthority("USUARIO")
        .requestMatchers("/solicitudes/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADIOR")
        .requestMatchers("/vacantes/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADIOR")
        .requestMatchers("/categorias/**").hasAnyAuthority("SUPERVISOR", "ADMINISTRADIOR")
        .requestMatchers("/usuarios/**").hasAnyAuthority("ADMINISTRADIOR")

        //Todas las demas urls de la aplicacion requerida autenticacion
        .anyRequest().authenticated());

        http.formLogin(form -> form.loginPage("/login").permitAll());
        return http.build();
        

    }


}
