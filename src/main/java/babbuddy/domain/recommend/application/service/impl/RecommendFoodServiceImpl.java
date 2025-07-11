package babbuddy.domain.recommend.application.service.impl;

import babbuddy.domain.allergy.domain.entity.Allergy;
import babbuddy.domain.allergy.domain.repository.AllergyRepository;
import babbuddy.domain.dislikefood.domain.entity.DislikeFood;
import babbuddy.domain.dislikefood.domain.repository.DisLikeFoodRepository;
import babbuddy.domain.openai.application.service.OpenAITextService;
import babbuddy.domain.recommend.application.service.RecommendFoodService;
import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.recommend.domain.repository.RecommendFoodRepository;
import babbuddy.domain.recommend.presentation.dto.req.RecommendFoodReq;
import babbuddy.domain.recommend.presentation.dto.res.RecommendFoodRes;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendFoodServiceImpl implements RecommendFoodService {
    private final OpenAITextService openAITextService;
    private final AllergyRepository allergyRepository;
    private final UserRepository userRepository;
    private final DisLikeFoodRepository disLikeFoodRepository;
    private final RecommendFoodRepository recommendFoodRepository;

    @Override
    public RecommendFoodRes recommendFood(RecommendFoodReq req, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        // 알러지 조회
        List<Allergy> allergies = allergyRepository.findByUser(user);
        String allergy;
        if (!allergies.isEmpty()) allergy = allergyFor(allergies);
        else allergy = "없음";

        // 싫어하는 음식 조회
        List<DislikeFood> dislikeFoods = disLikeFoodRepository.findAllByUser(user);
        log.info(String.valueOf(dislikeFoods.size()));
        String dislike;
        if (!dislikeFoods.isEmpty()) dislike = dislikeFoodFor(dislikeFoods);
        else dislike = "없음";


        // 알러지 + 싫어하는 음식 + 설문조사 3개
        String prompt = createTextPrompt(req, allergy, dislike);

        String foodName = openAITextService.recommendFood(prompt);

        String foodIntroduce = "오늘 너를 위해 추천한 메뉴는 바로 " + foodName + "이야! 🍽️ 나만 알고 있기 아까운 맛인데, 너도 한 번 받아볼래?";

        String foodImageUrl = getFoodImageUrl(foodName);

        RecommendFood recommendFood = RecommendFood.builder()
                .foodName(foodName)
                .foodIntroduce(foodIntroduce)
                .user(user)
                .isFavorite(false)
                .build();
        RecommendFood saved = recommendFoodRepository.save(recommendFood);


        return RecommendFoodRes.of(saved.getId(), foodName, foodIntroduce, foodImageUrl);
    }

    private String getFoodImageUrl(String foodName) {

        try {
            String encoded = URLEncoder.encode(foodName, "UTF-8");
            String url = "https://www.google.com/search?tbm=isch&q=" + encoded;

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            // 첫 번째 이미지 가져오기 (0번은 로고일 수 있으므로 1번)
            Element img = doc.select("img").get(1);
            return img.attr("src");

        } catch (Exception e) {
            e.printStackTrace();
            return "https://via.placeholder.com/300?text=Image+Not+Found";
        }
    }


    private String createTextPrompt(RecommendFoodReq req, String allergy, String dislike) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("다음 조건을 참고하여 사용자에게 추천할 수 있는 음식 이름 하나만 알려주세요.\n");
        prompt.append("5개 후보를 생각해 본 뒤, 5개중에서 랜덤으로 음식을 추천해 주세요.\n");
        prompt.append("단, 음식 이름만 간결하게 출력해 주세요. (예: 비빔밥)\n\n");

        prompt.append("✅ 사용자 선호:\n");
        prompt.append("1. 지금 가장 떠오르는 맛: ").append(req.survey1()).append("\n");
        prompt.append("2. 오늘 끌리는 음식점 분위기: ").append(req.survey2()).append("\n");
        prompt.append("3. 피하고 싶은 음식 종류: ").append(req.survey3()).append("\n\n");

        prompt.append("❌ 알레르기 및 기피 음식:\n");
        if (!allergy.isBlank()) {
            prompt.append("- 알레르기: ").append(allergy).append("\n");
        }
        if (!dislike.isBlank()) {
            prompt.append("- 기피 음식: ").append(dislike).append("\n");
        }

        prompt.append("\n위 조건을 모두 고려하여 가장 적절한 음식을 추천해 주세요.");

        return prompt.toString();
    }


    private String dislikeFoodFor(List<DislikeFood> dislikeFoods) {
        StringBuilder sb = new StringBuilder();
        for (DislikeFood dislikeFood : dislikeFoods) {
            sb.append(dislikeFood.getFoodName()).append(", ");
        }
        StringBuilder info = sb.deleteCharAt(sb.length() - 1);
        return info.toString();
    }

    private String allergyFor(List<Allergy> allergies) {
        StringBuilder sb = new StringBuilder();
        for (Allergy allergy : allergies) {
            sb.append(allergy.getAllergyType()).append(", ");
        }
        StringBuilder info = sb.deleteCharAt(sb.length() - 1);
        return info.toString();
    }


}
