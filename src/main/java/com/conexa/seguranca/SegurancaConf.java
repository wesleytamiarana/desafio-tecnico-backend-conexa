package com.conexa.seguranca;

import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Optional;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Repository;

import com.conexa.seguranca.credenciais.Credenciais;

import lombok.NoArgsConstructor;


@Configuration
@EnableCaching
@EnableWebSecurity
@NoArgsConstructor(access = PROTECTED)
public class SegurancaConf {

	@Bean
	PasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	CacheManager cacheManagerBean() {
		return new ConcurrentMapCacheManager("tokens");
	}


	@Bean
	UserDetailsService userDetailsServiceBean(final UserDetailsRepository repository) {
		return email -> repository
				.findByEmail(email)
				.map(credenciais -> User.builder()
						.username(credenciais.email())
						.password(credenciais.senha())
						.build())
				.orElseThrow(() -> new UsernameNotFoundException("seguranca.credenciais.nao.encontrada"));
	}


	@Bean
	AuthenticationProvider authenticationProviderBean(final UserDetailsService userDetailsService, final PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setUserDetailsService(userDetailsService);

		provider.setPasswordEncoder(passwordEncoder);

		return provider;
	}

	@Bean
	SecurityFilterChain securityFilterChainBean(final HttpSecurity builder, final AuthenticationProvider provider, final SegurancaFiltro filter) throws Exception {
		return builder
				.csrf(CsrfConfigurer::disable)
				//.httpBasic(withDefaults())
				.authenticationProvider(provider)
				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(POST, "/api/v1/login").permitAll()
						.requestMatchers(POST, "/api/v1/signup").permitAll()
						.requestMatchers(POST, "/api/v1/logoff").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}


	@Bean
	AuthenticationManager authenticationManagerBean(final AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}


@Repository
interface UserDetailsRepository extends JpaRepository<Credenciais, String> {
	Optional<Credenciais> findByEmail(String email);
}
