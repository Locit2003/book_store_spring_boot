package com.locShop.securityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.locShop.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private  JwtAuthFilter jwtAuthFilter;
	
	@Bean
	public UserDetailsService userDetailsService () {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
//    @Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//			.csrf().disable()
//			.authorizeHttpRequests()
//					.requestMatchers("/*").permitAll()
////					.requestMatchers("/api/**").authenticated()
//					.and()
//				.formLogin().loginProcessingUrl("/login")
//				 	.defaultSuccessUrl("/api/show-acc")
//				 	.failureUrl("/login?error")
//				 	.and()
//				 .logout()
//				 	.logoutUrl("/logout")
//				 	.logoutSuccessUrl("/login")
//				 	.deleteCookies("JSESSIONID");	
//		return http.build();
//	}
    
		@Bean
		SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
			http.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/api/auth/authenticate").permitAll()
			.anyRequest().authenticated()
			.and()
			.authenticationProvider(authenticationProvider())
			.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
			return http.build();
		}

		@Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
			return config.getAuthenticationManager();
		}
    
//    private CorsConfigurationSource crosConfigurationSource() {
//    	  CorsConfiguration configuration = new CorsConfiguration();
//    	  configuration.setAllowedOrigins(Arrays.asList("*"));
//    	  configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
//    	  configuration.setAllowedHeaders(Arrays.asList("*"));
//    	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    	  source.registerCorsConfiguration("/**", configuration);
//    	  return source;
//    }

}
