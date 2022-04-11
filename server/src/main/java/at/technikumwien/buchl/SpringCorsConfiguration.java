package at.technikumwien.buchl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;


@Configuration
public class SpringCorsConfiguration {

    private static final String ALLOWED_HEADERS = "*";//"x-requested-with, authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN";
    private static final String ALLOWED_METHODS = "*"; //"DELETE, GET, HEAD, OPTIONS, PATCH, POST, PUT, TRACE";
    private static final String ALLOWED_ORIGIN = "*";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();

            ServerHttpResponse response = ctx.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
            headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
            headers.add("Access-Control-Allow-Headers",ALLOWED_HEADERS);

            return chain.filter(ctx);
        };
    }

}