package com.example.concalendar.user.config;

import com.example.concalendar.user.exception.JwtCustomExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Security config.
 */
@RequiredArgsConstructor
// 기본적인 web 보안 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    private final JwtCustomExceptionFilter jwtCustomExceptionFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    private final UserLoginFailHandler userLoginFailHandler;

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    // passwordencoder bean 등록
    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().
//                    withUser("user").password("{noop}password1").roles("USER")
//                .and()
//                    .withUser("admin").password("{noop}password2").roles("ADMIN");
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        //http.httpBasic().disable(); // 일반적인 루트가 아닌 다른 방식으로 요청시 거절,
        // header에 id, pw가 아닌 token(jwt)을 달고 간다.
        // 그래서 basic이 아닌 bearer를 사용한다.
        http.httpBasic().disable()
                .authorizeRequests()// 요청에 대한 사용권한 체크
                .antMatchers("/test").authenticated() // 해당 url에 대한 요청 시 설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .formLogin() //
//                .loginPage("/users/login") // 인증이 필요할 때 이동하는 페이지 설정
//                .defaultSuccessUrl("/users/login") // 인증 성공시
//                .failureUrl("/users/login")
//                .failureHandler(userLoginFailHandler)
//                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider,redisTemplate),
                        UsernamePasswordAuthenticationFilter.class)
                // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
                // 필터를 등록, 왼쪽은 커스텀한 필터링, 오른쪽 필터링 전에 왼쪽이 먼저 진행됨
                .addFilterBefore(jwtCustomExceptionFilter,jwtAuthenticationFilter.getClass());

        // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성합니다.
        // 세션을 사용하지 않는다고 설정 -> STATELESS
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }

}
