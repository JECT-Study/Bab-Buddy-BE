package babbuddy.domain.allergy.presentation.controller;

import babbuddy.domain.allergy.application.service.AllergyService;
import babbuddy.domain.allergy.presentation.dto.req.AllergyPostReq;
import babbuddy.domain.allergy.presentation.dto.res.AllergyGetRes;
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
public class AllergyController {

    private final AllergyService allergyService;

    @GetMapping
    public ResponseEntity<List<AllergyGetRes>> getAllergy(@AuthenticationPrincipal String userId){
        return ResponseEntity.ok(allergyService.getAllergy(userId));
    }

    @PostMapping
    public void postAllergy(@RequestBody @Valid AllergyPostReq allergyPostReq,
                            @AuthenticationPrincipal String userId) {
        allergyService.postAllergy(allergyPostReq, userId);
    }
}
