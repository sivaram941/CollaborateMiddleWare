package Com.collaborate.middleware;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("Com.collaborate.collabaration")
public class WebResolver {
	
	@Bean
	public InternalResourceViewReslover.getViewResolver(){
		InternalResourceViewResolver.internalResourceViewResolver = new InternalResourceViewResolver();
		InternalResourceViewResolver.setPrefix("/WEB-INF");
		InternalResourceViewResolver.setSuffix(".jsp");
		System.out.println("Resolver Created");
		return InternalResourceViewReslover;
	}
}
