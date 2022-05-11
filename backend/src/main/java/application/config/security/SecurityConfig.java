package application.config.security;

import application.constants.RolesNameEnum;
import application.utils.jwtsecuriru.JwtConfigurer;
import application.utils.jwtsecuriru.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends  WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    //TODO разрешено всем
    private static final String ALL_ENDPOINT = "";
    //TODO разрешено ROLE_ADMIN
    private static final String ADMIN_ENDPOINT = "";
    //TODO разрешено ROLE_MANAGER
    private static final String MANAGER_ENDPOINT = "";
    //TODO разрешено ROLE_MAIN_MANAGER
    private static final String MAIN_MANAGER_ENDPOINT = "";


    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ALL_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasAnyRole(RolesNameEnum.ROLE_ADMIN.toString())
                .antMatchers(MANAGER_ENDPOINT).hasAnyRole(RolesNameEnum.ROLE_MANAGER.toString())
                .antMatchers(MAIN_MANAGER_ENDPOINT).hasAnyRole(RolesNameEnum.ROLE_MAIN_MANAGER.toString())
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
