package ink.breakpoint.easypay.uaa.config;


import ink.breakpoint.easypay.uaa.integration.IntegrationUserDetailsAuthenticationHandler;
import ink.breakpoint.easypay.uaa.integration.IntegrationUserDetailsAuthenticationProvider;
import ink.breakpoint.easypay.user.api.AuthorizationService;
import ink.breakpoint.easypay.user.api.TenantService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 * Spring Security配置
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Reference
	private TenantService tenantService;
	@Reference
	private AuthorizationService authorizationService;

	public SecurityConfig() {
	}

	@Bean
	public IntegrationUserDetailsAuthenticationHandler integrationUserDetailsAuthenticationHandler(){
		IntegrationUserDetailsAuthenticationHandler authenticationHandler = new IntegrationUserDetailsAuthenticationHandler();
		authenticationHandler.setTenantService(tenantService);
		return authenticationHandler;
	}

	@Bean
	public IntegrationUserDetailsAuthenticationProvider integrationUserDetailsAuthenticationProvider(){
		IntegrationUserDetailsAuthenticationProvider provider = new IntegrationUserDetailsAuthenticationProvider(integrationUserDetailsAuthenticationHandler());
		return provider;
	}


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(integrationUserDetailsAuthenticationProvider());
    }
    

    //不定义没有password grant_type
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/public/**", "/webjars/**", "/v2/**", "/swagger**", "/static/**", "/resources/**");
		//web.httpFirewall(new DefaultHttpFirewall());//StrictHttpFirewall 去除验url非法验证防火墙
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/login*").permitAll()
			.antMatchers("/logout*").permitAll()
			.antMatchers("/druid/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin()
				.loginPage("/login") // 登录页面
				.loginProcessingUrl("/login.do") // 登录处理url
				.failureUrl("/login?authentication_error=1")
				.defaultSuccessUrl("/oauth/authorize")
				.usernameParameter("username")
				.passwordParameter("password")
			.and().logout()
				.logoutUrl("/logout.do")
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/")
			.and().csrf().disable()
			.exceptionHandling()
			.accessDeniedPage("/login?authorization_error=2");

    }
    
    


}
