package com.ody.route.service;

import com.devoops.client.OdsayRouteClient;
import com.devoops.exception.OdsayBadRequestException;
import com.devoops.exception.OdsayClosestPlaceException;
import com.devoops.exception.OdsayUtilException;
import com.devoops.exception.OdsayWrongApiKeyException;
import com.ody.common.exception.OdyBadRequestException;
import com.ody.common.exception.OdyServerErrorException;
import com.ody.meeting.domain.Coordinates;
import com.ody.route.config.RouteClientProperty;
import com.ody.route.domain.ClientType;
import com.ody.route.domain.RouteTime;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
public class OdsayAppRouteClient implements RouteClient {

    private static final AtomicInteger loadBalanceIndex = new AtomicInteger(0);

    private final OdySayProperties odsayProperties;
    private final RouteClientProperty property;
    private final OdsayRouteClient odsayRouteClient;

    public OdsayAppRouteClient(
            OdySayProperties odsayProperties,
            RouteClientProperty property,
            RestClient.Builder restClientBuilder
    ) {
        this.odsayProperties = odsayProperties;
        this.property = property;
        this.odsayRouteClient = new OdsayRouteClient(restClientBuilder);
    }

    @Override
    public RouteTime calculateRouteTime(Coordinates origin, Coordinates target) {
        try {
            com.devoops.vo.Coordinates mappedOrigin = mapCoordinates(origin);
            com.devoops.vo.Coordinates mappedTarget = mapCoordinates(target);

            String apiKey = odsayProperties.getIndexof(loadBalanceIndex.incrementAndGet());
            long minutes = odsayRouteClient.calculateRouteMinutes(apiKey, mappedOrigin, mappedTarget);
            return new RouteTime(minutes);
        } catch (OdsayClosestPlaceException closestPlaceException) {
            return new RouteTime(-1L);
        } catch (OdsayBadRequestException badRequestException) {
            throw new OdyBadRequestException(badRequestException.getMessage());
        } catch (OdsayWrongApiKeyException | OdsayUtilException exception) {
            throw new OdyServerErrorException("오디세이 요청 과정에서 오류가 발생했습니다");
        }
    }

    private com.devoops.vo.Coordinates mapCoordinates(Coordinates coordinates) {
        return new com.devoops.vo.Coordinates(coordinates.getLatitude(), coordinates.getLongitude());
    }

    @Override
    public ClientType getClientType() {
        return ClientType.ODSAY;
    }
}
