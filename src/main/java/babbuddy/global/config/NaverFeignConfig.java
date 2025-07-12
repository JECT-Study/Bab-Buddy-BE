package babbuddy.global.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NaverFeignConfig implements RequestInterceptor {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Override
    public void apply(RequestTemplate template) {
        template.header("X-Naver-Client-Id", clientId);
        template.header("X-Naver-Client-Secret", clientSecret);
    }
}
