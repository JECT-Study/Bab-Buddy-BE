package babbuddy.domain.allergy.presentation.controller;

import babbuddy.domain.allergy.application.service.AllergyService;
import babbuddy.domain.allergy.presentation.dto.req.AllergyPostReq;
import babbuddy.domain.allergy.presentation.dto.res.AllergyGetRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allergy")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Allergy", description = "알레르기 관련 API")
public class AllergyController {

    private final AllergyService allergyService;

    @Operation(summary = "알레르기 조회", description = "사용자의 알레르기 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알레르기 정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @GetMapping
    public ResponseEntity<List<AllergyGetRes>> getAllergy(@AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(allergyService.getAllergy(userId));
    }

    @Operation(summary = "알레르기 등록 및 수정",
            description = "사용자의 알레르기 정보를 등록하거나 수정합니다. " +
                    "알레르기 선택된 목록 전체를 전달해주세요. 이전에 선택된거라해도 다 보내주세요.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알레르기 등록 성공"),
            @ApiResponse(responseCode = "400", description = "알레르기 정보 없음(잘 못 입력 했을때) -212") ,
            @ApiResponse(responseCode = "404", description = "유저 없음 -211")
    })
    @PostMapping
    public void postAllergy(@RequestBody @Valid AllergyPostReq allergyPostReq,
                            @AuthenticationPrincipal String userId) {
        allergyService.postAllergy(allergyPostReq, userId);
    }
}
