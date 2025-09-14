package com.ody.common.ping;

import com.ody.eta.repository.EtaRepository;
import com.ody.mate.repository.MateRepository;
import com.ody.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PingController {

    private final WarmUpRunner warmUpRunner;

    @GetMapping("/coli")
    public String ping() {
        return "ever";
    }

    @GetMapping("/warm-up")
    public String warmUp() {
       warmUpRunner.run();
        return "warmup-done";
    }
}
