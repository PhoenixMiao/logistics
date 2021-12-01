//package undestiny.stell.config;
//
//import com.google.gson.Gson;
//import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RequestMethod;
//import undestiny.stell.common.Result;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * 重写shiro的RolesAuthorizationFilter，实现放行OPTIONS请求
// * @author MDY
// */
//public class MyRoleFilter extends RolesAuthorizationFilter {
//
//    @Override
//    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            setHeader(httpRequest, httpResponse);
//            return true;
//        }
//        return super.isAccessAllowed(request, response, mappedValue);
//    }
//    /**
//     * 在访问过来的时候检测是否为OPTIONS请求，如果是就直接返回true
//     */
//    @Override
//    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            setHeader(httpRequest,httpResponse);
//            return true;
//        }
//        return super.preHandle(request,response);
//    }
//
//    /**
//     * 该方法会在验证失败后调用，这里由于是前后端分离，后台不控制页面跳转
//     * 因此重写改成传输JSON数据
//     */
//    @Override
//    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//        saveRequest(request);
//        setHeader((HttpServletRequest) request,(HttpServletResponse) response);
//        PrintWriter out = response.getWriter();
//        //自己控制返回的json数据
//        out.println(new Gson().toJson(Result.fail("login failed")));
//        out.flush();
//        out.close();
//    }
//
//    /**
//     * 为response设置header，实现跨域
//     */
//    private void setHeader(HttpServletRequest request, HttpServletResponse response){
//        //跨域的header设置
//        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
//        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
//        //防止乱码，适用于传输JSON数据
//        response.setHeader("Content-Type","application/json;charset=UTF-8");
//        response.setStatus(HttpStatus.OK.value());
//    }
//
//}
//
