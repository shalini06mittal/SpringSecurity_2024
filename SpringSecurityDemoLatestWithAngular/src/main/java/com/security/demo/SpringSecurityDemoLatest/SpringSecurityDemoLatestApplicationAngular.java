package com.security.demo.SpringSecurityDemoLatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?response_type=code&access_type=offline&client_id=849883241272-ed6lnodi1grnoomiuknqkq2rbvd2udku.apps.googleusercontent.com&prompt=select_account&scope=profile%20email&redirect_uri=https%3A%2F%2Fzoom.us%2Fgoogle%2Foauth&state=MGRhZF9iNWZSSlNnNnE5QlphT2dQZyxnb29nbGVfc2lnbmlu&_x_zm_rtaid=4sNHOo0VRSm_SA0d5V_ORw.1729753003691.f5d051efe7996d0bc5be94e9c89adb48&_x_zm_rhtaid=834&service=lso&o2v=2&ddm=0&flowName=GeneralOAuthFlow
@SpringBootApplication
@EnableWebSecurity//(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SpringSecurityDemoLatestApplicationAngular {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoLatestApplicationAngular.class, args);
	}

}
