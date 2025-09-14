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

    private final MeetingRepository meetingRepository;
    private final EtaRepository etaRepository;
    private final MateRepository mateRepository;

    @GetMapping("/coli")
    public String ping() {
        return "ever";
    }

    @GetMapping("/warm-up")
    public String warmUp() {
        for (int i = 1; i < 41; i++) {
            meetingRepository.findById((long) i);
        }

        for (int i = 1; i < 101; i++) {
            mateRepository.findById((long) i);
            etaRepository.findById((long) i);
        }

        return "warmup-done";
    }
}
