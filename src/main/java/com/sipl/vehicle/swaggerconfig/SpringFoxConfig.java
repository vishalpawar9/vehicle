package com.sipl.vehicle.swaggerconfig;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
		public static final String AUTHORIZATION_HEADER="Authorization";
		
		@Bean
		public Docket api() {
		    return new Docket(DocumentationType.SWAGGER_2)
		    	.securityContexts(securityContexts())
		    	.securitySchemes(Arrays.asList(apiKeys()))
		    	.select()
		    	.apis(RequestHandlerSelectors.any())
		    	.paths(PathSelectors.any())
		    	.build();
		}
		private List<SecurityContext> securityContexts()
		{
			return Arrays.asList(SecurityContext.builder().securityReferences(securityRef()).build());
		}
		
	 private List<SecurityReference> securityRef()
	{
	    AuthorizationScope scope=new AuthorizationScope("global", "accessEveryThing");
		return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[] {scope}));
	}
	 
	 
	private ApiKey apiKeys() {
	        return new ApiKey("JWT",AUTHORIZATION_HEADER, "header");
	    }
}