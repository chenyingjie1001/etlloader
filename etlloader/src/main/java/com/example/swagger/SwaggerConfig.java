package com.example.swagger;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * SpringBoot默认已经将classpath:/META-INF/resources/和classpath:/META-INF/
	 * resources/webjars/映射 所以该方法不需要重写，如果在SpringMVC中，可能需要重写定义（我没有尝试） 重写该方法需要
	 * extends WebMvcConfigurerAdapter
	 * 
	 */
	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	// registry.addResourceHandler("swagger-ui.html")
	// .addResourceLocations("classpath:/META-INF/resources/");
	//
	// registry.addResourceHandler("/webjars/**")
	// .addResourceLocations("classpath:/META-INF/resources/webjars/");
	// }

	/**
	 * 可以定义多个组，比如本类中定义把test和demo区分开了 （访问页面就可以看到效果了）
	 *
	 */
	/*@SuppressWarnings("unchecked")
	@Bean
	public Docket testApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("test")
				.genericModelSubstitutes(DeferredResult.class)
				// .genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false).forCodeGeneration(true)
				.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select().paths(or(regex("/api/.*")))// 过滤的接口
				.build().apiInfo(testApiInfo());
	}

	@SuppressWarnings("unchecked")
	@Bean
	public Docket demoApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("demo")
				.genericModelSubstitutes(DeferredResult.class)
				// .genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false).forCodeGeneration(false)
				.pathMapping("/").select().paths(or(regex("/demo/.*")))// 过滤的接口
				.build().apiInfo(demoApiInfo());
	}*/

	@Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                Class<?> declaringClass = input.declaringClass();
                if (declaringClass == BasicErrorController.class)// 排除
                    return false;
                if(declaringClass.isAnnotationPresent(RestController.class)) // 被注解的类
                    return true;
                if(input.isAnnotatedWith(ResponseBody.class)) // 被注解的方法
                    return true;
                return false;
            }
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate)
                .build();
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("包含数据，搜索等类型关键接口的服务")//大标题
            .version("1.0")
            .contact(new Contact("陈太大", "http://www.weibo.com/5674120350/profile?rightmod=1&wvr=6&mod=personinfo",
								"77189549@qq.com"))//版本
            .build();
    }
	
	@SuppressWarnings("unused")
	private ApiInfo testApiInfo() {
		return new ApiInfoBuilder()
				.title("Electronic Health Record(EHR) Platform API")
				// 大标题
				.description(
						"EHR Platform's REST API, all the applications could access the Object model data via JSON.")
				// 详细描述
				.version("1.0")
				// 版本
				.termsOfServiceUrl("NO terms of service")
				.contact(
						new Contact("陈太大", "http://www.weibo.com/5674120350/profile?rightmod=1&wvr=6&mod=personinfo",
								"77189549@qq.com"))
				// 作者
				.license("The Apache License, Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.build();
	}

	@SuppressWarnings("unused")
	private ApiInfo demoApiInfo() {
		return new ApiInfoBuilder()
				.title("Electronic Health Record(EHR) Platform API")
				// 大标题
				.description(
						"EHR Platform's REST API, all the applications could access the Object model data via JSON.")
				// 详细描述
				.version("1.0")
				// 版本
				.termsOfServiceUrl("NO terms of service")
				.contact(
						new Contact("陈太大", "http://www.weibo.com/5674120350/profile?rightmod=1&wvr=6&mod=personinfo",
								"77189549@qq.com"))
				// 作者
				.license("The Apache License, Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.build();
	}
}
