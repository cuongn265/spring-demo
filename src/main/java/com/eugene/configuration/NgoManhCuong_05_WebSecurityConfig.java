package com.eugene.configuration;

import com.eugene.service.NgoManhCuong_05_CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Created by Ngô Mạnh Cường on 28/11/2016.
 */
/*Hàm cấu hình việc xác thực người dùng*/
@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = NgoManhCuong_05_CustomUserDetailsService.class)
public class NgoManhCuong_05_WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final NgoManhCuong_05_CustomAuthenticationSuccessHandler ngoManhCuong05CustomAuthenticationSuccessHandler;

  /*Gọi các bean*/
  @Autowired
  public NgoManhCuong_05_WebSecurityConfig(UserDetailsService userDetailsService, NgoManhCuong_05_CustomAuthenticationSuccessHandler ngoManhCuong05CustomAuthenticationSuccessHandler) {
    this.userDetailsService = userDetailsService;
    this.ngoManhCuong05CustomAuthenticationSuccessHandler = ngoManhCuong05CustomAuthenticationSuccessHandler;
  }

  /*Thực hiện đăng nhập với username và password đã được encode*/
  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
  }

  /**Cấu hình việc xác thực người dùng, cấp độ truy cập cho các file static
   * các URL được truy cập không cần đăng nhập
   * các URL được truy cập cần đăng nhập
   * handler cho việc đăng nhập
   * cho các request không có quyền
   * ...
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/login", "/css/**", "/js/**", "/built/**", "/images/**", "/layout").permitAll()
      .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//      .antMatchers("/**").access("hasRole('ROLE_USER')")
      .antMatchers("/profile", "/password").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .loginPage("/login")
      .usernameParameter("username").passwordParameter("password")
      .successHandler(ngoManhCuong05CustomAuthenticationSuccessHandler)
      .and()
      .httpBasic()
      .and()
      .logout()
      .logoutUrl("/logout")
      .logoutSuccessUrl("/login?logout")
      .and()
      .exceptionHandling().accessDeniedPage("/403")
      .and()
      .csrf().disable();
  }

  /*Tạo bean để encode password*/
  @Bean(name = "passwordEncoder")
  public PasswordEncoder passwordencoder() {
    return new BCryptPasswordEncoder(4);
  }
}
