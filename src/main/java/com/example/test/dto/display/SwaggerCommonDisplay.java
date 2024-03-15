package com.example.test.dto.display;

import com.example.test.dto.request.AddFriendRequest;
import com.example.test.dto.request.TestRequest;
import com.example.test.dto.response.AddFriendResponse;
import com.example.test.dto.response.TestResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Req/Res를 클라이언트에 전달하기 위해 Swagger Display용
 */
@RestController
@Tag(name = "Common")
public class SwaggerCommonDisplay {
    @GetMapping("/test")
    void test(TestRequest testRequest,
              TestResponse testResponse) throws IllegalAccessException {
        throw new IllegalAccessException("절대 직접 호출하지 않는다. (Swagger용)");
    }
}
