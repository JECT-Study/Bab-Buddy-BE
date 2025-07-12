package babbuddy.domain.recommend.application.service.impl;

import babbuddy.domain.allergy.domain.entity.Allergy;
import babbuddy.domain.allergy.domain.repository.AllergyRepository;
import babbuddy.domain.dislikefood.domain.entity.DislikeFood;
import babbuddy.domain.dislikefood.domain.repository.DisLikeFoodRepository;
import babbuddy.domain.openai.application.service.OpenAITextService;
import babbuddy.domain.recommend.application.service.RecommendFoodService;
import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;
import babbuddy.domain.recommend.domain.repository.RecommendFoodRepository;
import babbuddy.domain.recommend.domain.repository.RecommendRestaurantRepository;
import babbuddy.domain.recommend.presentation.dto.req.RecommendFoodReq;
import babbuddy.domain.recommend.presentation.dto.res.RecommendFoodRes;
import babbuddy.domain.recommend.presentation.dto.res.RestaurantRes;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RecommendFoodServiceImpl implements RecommendFoodService {
    private final OpenAITextService openAITextService;
    private final AllergyRepository allergyRepository;
    private final UserRepository userRepository;
    private final DisLikeFoodRepository disLikeFoodRepository;
    private final RecommendFoodRepository recommendFoodRepository;
    private final RecommendRestaurantAsyncService restaurantAsyncService;
    private final RecommendRestaurantRepository recommendRestaurantRepository;


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
        String dislike;
        if (!dislikeFoods.isEmpty()) dislike = dislikeFoodFor(dislikeFoods);
        else dislike = "없음";


        // 알러지 + 싫어하는 음식 + 설문조사 3개
        String prompt = createTextPromptV2(req, allergy, dislike);

        String[] resultParts = openAITextService.recommendFood(prompt).split(",", 2);
        String foodName = resultParts[0].trim();  // 음식 이름
        //String city = resultParts.length > 1 ? resultParts[1].trim() : "Seoul"; // 주소 영어로 된 값 없으면 기본 서울
        String category = resultParts[1].trim(); // 음식 타입

        String foodIntroduce = "오늘 너를 위해 추천한 메뉴는 바로 " + foodName + "이야! 🍽️ 나만 알고 있기 아까운 맛인데, 너도 한 번 받아볼래?";

        String foodImageUrl = getFoodImageUrl(foodName);

        RecommendFood recommendFood = RecommendFood.builder()
                .foodName(foodName)
                .foodIntroduce(foodIntroduce)
                .user(user)
                .isFavorite(false)
                .build();
        RecommendFood saved = recommendFoodRepository.save(recommendFood);


        return RecommendFoodRes.of(saved.getId(), foodName, foodIntroduce, foodImageUrl, category);
    }

    @Override
    public void doRestaurantAsync(String address, RecommendFoodRes res, String category) {
        /**
         * @Async는 프록시 기반으로 동작
         * 따라서 @Async는 같은 클래스 안에서 직접 호출하면 비동기로 실행되지 않기 때문에,
         * 다른 클래스(프록시 빈)로 분리해서 호출하는 구조로 만듬.
         * 여기서 호출만 위임
         */
        // restaurantAsyncService.recommendRestaurantsAsyncV1(address, res, city); // openai용
         restaurantAsyncService.recommendRestaurantsAsyncV2(address, res, category); // 네이버 지역 검색 api
    }

    @Override
    public List<RestaurantRes> restaurantAll(Long foodId) {
        RecommendFood recommendFood = recommendFoodRepository.findById(foodId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.FOOD_NOT_EXIST));

        List<RecommendRestaurant> allRecommendFood = recommendRestaurantRepository.findByRecommendFood(recommendFood);

        if (allRecommendFood.isEmpty()) throw new BabbuddyException(ErrorCode.FOOD_NOT_EXIST);

        List<RestaurantRes> result = new ArrayList<>();

        for (RecommendRestaurant recommendRestaurant : allRecommendFood) {
            result.add(RestaurantRes.of(recommendRestaurant));
        }

        return result;

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
            throw new BabbuddyException(ErrorCode.IMAGE_MAPPING_FAIL);
        }
    }


    private String createTextPromptV1(RecommendFoodReq req, String allergy, String dislike) {
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

        prompt.append("\n📍 사용자의 주소는 다음과 같습니다: ").append(req.address()).append("\n");
        prompt.append("주소를 참고하여 해당 지역을 영어로 표현해 주세요. 예를 들어 서울특별시는 Seoul, 인천광역시는 Incheon, 경기도는 Gyeonggi 등으로 변환합니다.\n");

        prompt.append("\n✨ 최종 출력은 음식 이름과 지역(영문)을 쉼표(,)로 구분한 한 줄로 출력해 주세요.\n");

        return prompt.toString();
    }

    private String createTextPromptV2(RecommendFoodReq req, String allergy, String dislike) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("다음 조건을 참고하여 사용자에게 추천할 수 있는 음식 이름 하나만 알려주세요.\n");
        prompt.append("5개 후보를 생각해 본 뒤, 5개 중에서 랜덤으로 하나를 선택해 주세요.\n");
        prompt.append("선택한 음식이 한식, 중식, 일식, 양식, 분식, 아시안 등 어떤 종류인지 분류해 주세요.\n");
        prompt.append("단, 음식 이름과 음식 종류(대분류)만 한 줄로 출력해 주세요.\n");
        prompt.append("출력 형식은 '음식 이름,종류' 형식으로 간단하게 작성해 주세요. (예: 비빔밥,한식)\n\n");

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
