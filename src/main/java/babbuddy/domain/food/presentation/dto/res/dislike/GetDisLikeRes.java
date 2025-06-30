package babbuddy.domain.food.presentation.dto.res.dislike;

public record GetDisLikeRes(

        Long id,
        String foodName
) {
    public static GetDisLikeRes of(Long id, String foodName) {
        return new GetDisLikeRes(id,foodName);
    }
}
