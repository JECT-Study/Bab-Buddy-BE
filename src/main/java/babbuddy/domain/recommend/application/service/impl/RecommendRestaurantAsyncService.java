package babbuddy.domain.recommend.application.service.impl;

import babbuddy.domain.allergy.domain.repository.AllergyRepository;
import babbuddy.domain.dislikefood.domain.repository.DisLikeFoodRepository;
import babbuddy.domain.openai.application.service.OpenAITextService;
import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;
import babbuddy.domain.recommend.domain.repository.RecommendFoodRepository;
import babbuddy.domain.recommend.domain.repository.RecommendRestaurantRepository;
import babbuddy.domain.recommend.presentation.dto.res.RecommendFoodRes;
import babbuddy.domain.recommend.presentation.dto.res.RestaurantRes;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendRestaurantAsyncService {
    private final RecommendRestaurantRepository recommendRestaurantRepository;
    private final OpenAITextService openAITextService;

    private final RecommendFoodRepository recommendFoodRepository;

    @Async
    public void recommendRestaurantsAsync(String address, RecommendFoodRes res) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            /* 1) GPT 응답 JSON 문자열 */
            String jsonRestaurant = openAITextService.recommendRestaurant(address, res.foodName());

            /* 2) JSON → List<RestaurantDto>  (record 버전) */
            List<RestaurantRes> restaurants = mapper.readValue(
                    jsonRestaurant,
                    new TypeReference<List<RestaurantRes>>() {}
            );

            /* 3) 추천 음식 FK 조회 */
            RecommendFood recommendFood = recommendFoodRepository.findById(res.id())
                    .orElseThrow(() -> new BabbuddyException(ErrorCode.FOOD_NOT_EXIST));

            /* 4) 엔티티 변환·저장 */
            for (RestaurantRes dto : restaurants) {
                RecommendRestaurant entity = RecommendRestaurant.builder()
                        .recommendFood(recommendFood)
                        .restaurantName(dto.name())
                        .restaurantType(dto.restaurantType())
                        .address(dto.address())
                        .rate(dto.rating())
                        .latitude(dto.latitude())
                        .longitude(dto.longitude())
                        .build();

                recommendRestaurantRepository.save(entity);
            }


        } catch (Exception e) {
            log.error("❌ 음식점 추천 저장 실패", e);
            throw new BabbuddyException(ErrorCode.JSON_MAPPING_FAIL);
        }
    }
}
