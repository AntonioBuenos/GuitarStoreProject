package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.service.UserDetailsSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsSecurityService service;
    private final JWTFilter jwtFilter;

    public static final String REGISTRATION = "/auth/registration";
    public static final String LOGIN = "/auth/login";
    public static final String ERROR = "/error";
    public static final String LOGOUT = "/logout";
    public static final String PROCESS_LOGIN = "/process_login";
    public static final String DEFAULT_SUCCESS = "/";
    public static final String FAILURE = "/auth/login?error";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//отключаем при обращении не через браузер, н-р, чере Postman
                .authorizeRequests() //настраиваем авторизацию
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                //For swagger access
                .antMatchers("/v3/api-docs", "/configuration/ui/**", "/swagger-resources/**",
                        "/configuration/security/**", "/swagger-ui/**", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.index").permitAll()
                .antMatchers(LOGIN, REGISTRATION, ERROR).permitAll()
                .antMatchers(HttpMethod.GET, "/rest/guitars/**",
                        "/rest/genres/**", "/rest/manufacturers/**", "/rest/instocks/**").permitAll()
                .antMatchers("/rest/orders/**", "/rest/users/**").authenticated()
                .antMatchers("/rest/admin/**").hasRole("ADMIN")
                .anyRequest().hasAnyRole("MANAGER", "ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //теперь никакаяя сесси на сервере не хранится

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); //внедряем фильтр
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
