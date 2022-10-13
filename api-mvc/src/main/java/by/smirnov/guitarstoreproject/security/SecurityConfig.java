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
//change to SecurityFilterChain interface?
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
                /*For swagger access only*/
                .authorizeRequests() //настраиваем авторизацию
                .antMatchers("/v2/api-docs", "/configuration/ui/**", "/swagger-resources/**",
                        "/configuration/security/**", "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html#").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers(LOGIN, REGISTRATION, ERROR).permitAll() //всех пользователей пускаем на эти страницы
                /*.anyRequest().authenticated() //на всех остальных пользователь д.б. аутентифицирован*/
                .anyRequest().hasAnyRole("CUSTOMER", "SALES_CLERC", "ADMIN")
                .and()//переходим к настройке своей страницы аутентификации
                .formLogin().loginPage(LOGIN) //определяем свою страницу
                .loginProcessingUrl(PROCESS_LOGIN)
                .defaultSuccessUrl(DEFAULT_SUCCESS, true) //прошедший перенаправляется сюда
                .failureUrl(FAILURE) //не прошедший сюда
                .and()
                .logout()
                .logoutUrl(LOGOUT)
                .logoutSuccessUrl(LOGIN)
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
