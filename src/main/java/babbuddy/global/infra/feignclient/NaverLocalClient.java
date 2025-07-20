package babbuddy.global.infra.feignclient;

import babbuddy.domain.openai.dto.naver.LocalSearchResponse;
import babbuddy.global.config.NaverFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "naverLocalClient",
        url = "https://openapi.naver.com",
        configuration = NaverFeignConfig.class
)
public interface NaverLocalClient {

    @GetMapping("/v1/search/local.json")
    LocalSearchResponse searchLocal(
            @RequestParam("query") String query,
            @RequestParam("display") int display,
            @RequestParam("start") int start,
            @RequestParam("sort") String sort);
}

