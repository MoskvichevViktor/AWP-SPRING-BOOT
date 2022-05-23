package application.config.security.v1;

import application.config.security.JwcRequestFilter;
import application.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity()
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwcRequestFilter jwcRequestFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private static final String[] AUTH_WHITELIST_HTTP = {
            //доступ к любой точке
            "/**"
            //для доступа к swagger-ui
            , "/v3/api-docs/**"
            , "/swagger-ui/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST_HTTP)
                .permitAll()
                // httpBasic() НЕ ОТКЛЮЧАТЬ, НЕ БУДЕТ РАБОТАТЬ @PreAuthorize("hasAuthority('ROLE')")
                .and().httpBasic()
                //TODO Указать URLS login and logout
//                .and()
//                .formLogin()
//                .loginPage("/auth")
                .and()
                .logout();
//                .logoutSuccessUrl("/logout");
//                .and()
//                    .exceptionHandling()
//                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.addFilterBefore(jwcRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Открывает доступ к консоли H2
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}