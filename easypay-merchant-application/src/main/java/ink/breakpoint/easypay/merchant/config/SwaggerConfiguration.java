package ink.breakpoint.easypay.merchant.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket buildDocket() {

        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build();
        pars.add(tokenPar.build());
        //添加head参数end

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                // 要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.basePackage("ink.breakpoint.easypay.merchant.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @param
     * @return springfox.documentation.service.ApiInfo
     * @Title: 构建API基本信息
     * @methodName: buildApiInfo
     */
    private ApiInfo buildApiInfo() {
        Contact contact = new Contact("开发者", "", "");
        return new ApiInfoBuilder()
                .title("惠民支付-商户应用API文档")
                .description("")
                .contact(contact)
                .version("1.0.0").build();
    }
}
