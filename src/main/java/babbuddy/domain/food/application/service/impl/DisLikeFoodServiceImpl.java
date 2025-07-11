package babbuddy.domain.food.application.service.impl;

import babbuddy.domain.food.application.service.DisLikeFoodService;
import babbuddy.domain.food.domain.entity.DislikeFood;
import babbuddy.domain.food.domain.entity.Food;
import babbuddy.domain.food.domain.repository.DisLikeFoodRepository;
import babbuddy.domain.food.presentation.dto.dislike.req.PostDislikeReq;
import babbuddy.domain.food.presentation.dto.dislike.res.GetDisLikeRes;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DisLikeFoodServiceImpl implements DisLikeFoodService {

    private final DisLikeFoodRepository disLikeFoodRepository;
    private final UserRepository userRepository;

    @Override
    public List<GetDisLikeRes> getDisLikeFood(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);
        List<GetDisLikeRes> result = new ArrayList<>();
        List<DislikeFood> allByFood = disLikeFoodRepository.findAllByUser(user);

        for (DislikeFood food : allByFood) {
            result.add(GetDisLikeRes.of(food.getId(), food.getFoodName()));
        }

        return result;
    }

    @Override
    public void postDisLikeFood(PostDislikeReq req, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        DislikeFood food = DislikeFood.builder().foodName(req.foodName()).user(user).build();
        disLikeFoodRepository.save(food);
    }

    @Override
    public void deleteDisLikeFood(Long foodId, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        DislikeFood food = disLikeFoodRepository.findById(foodId).orElse(null);
        if (food == null) throw new BabbuddyException(ErrorCode.FOOD_NOT_EXIST);

        disLikeFoodRepository.delete(food);
    }

}
