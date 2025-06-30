package babbuddy.domain.food.presentation.controller;

import babbuddy.domain.food.application.service.DisLikeFoodService;
import babbuddy.domain.food.presentation.dto.req.dislike.PostDislikeReq;
import babbuddy.domain.food.presentation.dto.res.dislike.GetDisLikeRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
@Slf4j
public class DisLikeFoodController {

    private final DisLikeFoodService foodService;
    @GetMapping
    public ResponseEntity<List<GetDisLikeRes>> getDisLikeFood(@AuthenticationPrincipal String userId){
       return ResponseEntity.ok(foodService.getDisLikeFood(userId));
    }

    @PostMapping
    public void postDisLikeFood(@RequestBody @Valid PostDislikeReq req,
                                @AuthenticationPrincipal String userId){
        foodService.postDisLikeFood(req, userId);
    }

    @DeleteMapping("/{foodId}")
    public void deleteDisLikeFood(@PathVariable Long foodId,
                                  @AuthenticationPrincipal String userId){
        foodService.deleteDisLikeFood(foodId, userId);
    }
}
