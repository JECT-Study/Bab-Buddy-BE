package babbuddy.domain.recommend.application.service.impl;

import babbuddy.domain.openai.application.service.OpenAITextService;
import babbuddy.domain.openai.dto.naver.LocalSearchResponse;
import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;
import babbuddy.domain.recommend.domain.repository.RecommendFoodRepository;
import babbuddy.domain.recommend.domain.repository.RecommendRestaurantRepository;
import babbuddy.domain.recommend.presentation.dto.res.RecommendFoodRes;
import babbuddy.domain.recommend.presentation.dto.res.RestaurantRes;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import babbuddy.global.infra.feignclient.NaverLocalClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendRestaurantAsyncService {
    private final RecommendRestaurantRepository recommendRestaurantRepository;
    private final OpenAITextService openAITextService;
    private final NaverLocalClient naverLocalClient;
    private final RecommendFoodRepository recommendFoodRepository;

    @Async
    public void recommendRestaurantsAsyncV1(String address, RecommendFoodRes res, String city) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            /* 1) GPT 응답 JSON 문자열 */
            String jsonRestaurant = openAITextService.recommendRestaurant(address, res.foodName(), city);

            /* 2) JSON → List<RestaurantDto>  (record 버전) */
            List<RestaurantRes> restaurants = mapper.readValue(
                    jsonRestaurant,
                    new TypeReference<List<RestaurantRes>>() {
                    }
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

    @Async
    public void recommendRestaurantsAsyncV2(String address, RecommendFoodRes res, String city) {
        try {
            // 1. 검색어 구성
            String query = address + " " + res.foodName();
            log.info("🔍 [음식점 검색 요청] query='{}'", query);

            // 2. 네이버 지역 검색 API 호출
            LocalSearchResponse apiResp = naverLocalClient.searchLocal(
                    query,  // 그대로
                    3,
                    1,
                    "comment"
            );

            if (apiResp == null || apiResp.items() == null || apiResp.items().isEmpty()) {
                log.warn("⚠️ [검색 결과 없음] query='{}'", query);
                return;
            }

            // 3. 응답 변환
            List<RestaurantRes> restaurants = apiResp.items().stream()
                    .map(item -> {
                        log.debug("📌 검색 결과 → title={}, category={}, address={}, mapx={}, mapy={}",
                                item.title(), item.category(), item.roadAddress(), item.mapx(), item.mapy());

                        if (item.mapx() == null || item.mapx().isBlank() ||
                                item.mapy() == null || item.mapy().isBlank()) {
                            log.warn("⛔ 위치 정보 없음 → 스킵: title='{}'", item.title());
                            return null;
                        }

                        return new RestaurantRes(
                                item.title().replaceAll("<.*?>", ""),
                           item.category(),
                                item.roadAddress(),
                                "4.5",
                                convertNaverCoord(item.mapy()),   // 위도
                                convertNaverCoord(item.mapx())    // 경도
                        );
                    })

                    .filter(Objects::nonNull)
                    .toList();

            log.info("✅ 변환된 음식점 개수: {}", restaurants.size());

            // 4. 음식 추천 정보 조회
            RecommendFood recommendFood = recommendFoodRepository.findById(res.id())
                    .orElseThrow(() -> new BabbuddyException(ErrorCode.FOOD_NOT_EXIST));

            // 5. 저장
            for (RestaurantRes dto : restaurants) {
                log.info("💾 저장 대상: name='{}', type='{}', address='{}'",
                        dto.name(), dto.restaurantType(), dto.address());
                String category = dto.restaurantType();
                String type = category.split(">")[0];  // "중식"만 추출
                RecommendRestaurant entity = RecommendRestaurant.builder()
                        .recommendFood(recommendFood)
                        .restaurantName(dto.name())
                        .restaurantType(type)
                        .address(dto.address())
                        .rate(dto.rating())
                        .latitude(dto.latitude())
                        .longitude(dto.longitude())
                        .build();

                recommendRestaurantRepository.save(entity);
            }

            log.info("🎉 음식점 저장 성공 (총 {}개)", restaurants.size());

        } catch (Exception e) {
            log.error("❌ 음식점 추천 저장 실패", e);
            throw new BabbuddyException(ErrorCode.JSON_MAPPING_FAIL);
        }

    }
    // 문자열을 실수로 바꾸면서 소수점 7자리 삽입
    private double convertNaverCoord(String coordStr) {
        if (coordStr.length() <= 7) return 0.0;

        String integerPart = coordStr.substring(0, coordStr.length() - 7);
        String fractionalPart = coordStr.substring(coordStr.length() - 7);

        return Double.parseDouble(integerPart + "." + fractionalPart);
    }



}
