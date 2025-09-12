package com.ody.common.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/coli")
    public String ping() {
        return "ever";
    }
}
