package ink.breakpoint.easypay.gateway.config;

import ink.breakpoint.easypay.common.cache.domain.RestResponse;
import ink.breakpoint.easypay.gateway.common.util.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestOAuth2AuthExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        RestResponse restResponse = new RestResponse(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
        HttpUtil.writerError(restResponse,response);
    }
}
