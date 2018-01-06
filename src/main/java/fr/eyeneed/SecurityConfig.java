package fr.eyeneed;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
public void configureGlobal(AuthenticationManagerBuilder auth,DataSource datasource ) throws Exception{

		auth.jdbcAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())
		.dataSource(datasource)
		.usersByUsernameQuery("select login as principal, password as credentials, 1 from utilisateurs where login = ?")
		// remplacer 1 par active
		.authoritiesByUsernameQuery("select utilisateurs_login as principal, role_nom as role from utilisateurs_role where utilisateurs_login = ?")
		.rolePrefix("ROLE_");


}
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
        .authorizeRequests()
            .antMatchers("/", "/index.html").permitAll()
            .antMatchers("/css/**","/js/**","/img/**","/lib/**","/index.html","/inscription.html","/inscription","/saveUtilisateur","/saveUser","/checkUsername/**",
					"/verifierToken","/checkPassword","/newPassword","/changePassword","/cgu.html","confirmationLogin.html","/majVisite","/test").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/questionnaire")
            .permitAll()
            .and()
        .logout()
            .permitAll();
	}
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
}
