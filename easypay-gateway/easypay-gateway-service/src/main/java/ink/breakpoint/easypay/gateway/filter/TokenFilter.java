package ink.breakpoint.easypay.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import ink.breakpoint.easypay.merchant.common.util.LoginUser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenFilter extends ZuulFilter {
    /**
     * 过滤器是否生效
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * 过滤器类型
     *
     * @return
     */
    @Override
    public String filterType() {
        return "route";
    }

    /**
     * 过滤器执行顺序
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 添加信息在request域对象里
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //获取Zuul提供的上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        //获取请求对象
        HttpServletRequest request = context.getRequest();
        String tenantId = request.getParameter("tenantId");
        System.out.println("====================================================================");
        System.out.println(tenantId);
        System.out.println("====================================================================");
        LoginUser loginUser = new LoginUser();
        loginUser.setTenantId(Long.parseLong(tenantId));
        request.setAttribute("jsonToken", loginUser);
        return null;
    }
}
