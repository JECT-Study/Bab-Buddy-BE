package babbuddy.domain.openai.application.service.impl;

import babbuddy.domain.openai.application.service.OpenAITextService;
import babbuddy.domain.openai.dto.OpenAITextRequest;
import babbuddy.domain.openai.dto.OpenAITextResponse;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import babbuddy.global.infra.feignclient.OpenAITextFeignClient;
import ch.qos.logback.core.spi.ErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenAITextServiceImpl implements OpenAITextService {

    private final OpenAITextFeignClient openAITextFeignClient;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.text.model}")
    private String textModel;

    @Override
    public String recommendFood(String prompt) {
        try {
            log.info(prompt);
            List<OpenAITextRequest.Message> messages = new ArrayList<>();
            messages.add(OpenAITextRequest.Message.builder()
                    .role("system")
                    .content("너는 사용자의 취향과 상황에 맞춰 가장 적합한 음식을 추천해주는 전문가야. 친절하고 구체적으로 추천해줘.")
                    .build());
            messages.add(OpenAITextRequest.Message.builder()
                    .role("user")
                    .content(prompt)
                    .build());

            OpenAITextRequest request = OpenAITextRequest.builder()
                    .model(textModel)
                    .messages(messages)
                    .temperature(0.7)
                    .maxTokens(500)
                    .build();

            OpenAITextResponse response = openAITextFeignClient.generateText(
                    "Bearer " + apiKey,
                    request
            );

            if (response.getChoices() != null && !response.getChoices().isEmpty()) {
               return response.getChoices().get(0).getMessage().getContent();

            } else {
                throw new BabbuddyException(ErrorCode.OPENAI_NOT_EXIST);
            }
        } catch (Exception e) {
            log.error("에러 내용: {}", e.getMessage(), e);
            throw new BabbuddyException(ErrorCode.OPENAI_NOT_EXIST);
        }
    }
}
