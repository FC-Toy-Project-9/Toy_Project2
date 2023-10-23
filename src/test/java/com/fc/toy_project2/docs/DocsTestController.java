package com.fc.toy_project2.docs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * DocsTestController는 Spring REST Docs를 테스트 하기 위한 컨트롤러로, 실제로 작동하지 않습니다.
 */
@RestController
public class DocsTestController {

    @PostMapping("/docs")
    public ResponseEntity<Map<String, Object>> sample(
        @RequestBody @Valid SampleRequestDTO sampleRequestDTO) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 201);
        response.put("message", "성공!");
        response.put("data", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SampleRequestDTO {

        @NotEmpty(message = "이름을 입력하세요.")
        @JsonProperty("name")
        private String name;
    }
}
