package com.ody.eta.event;

import com.ody.meeting.domain.Coordinates;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdateRouteTimeEvent extends ApplicationEvent {

    private final long etaId;
    private final Coordinates origin;
    private final Coordinates target;

    public UpdateRouteTimeEvent(Object source, long etaId, Coordinates origin, Coordinates target) {
        super(source);
        this.etaId = etaId;
        this.origin = origin;
        this.target = target;
    }
}
