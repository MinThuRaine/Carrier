package io.github.minthurein.carrier.portfolio.way.Data;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.github.minthurein.carrier.portfolio.way.domain.Way;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MultipleWayModel {

    private List<WayModel.WayModelNested> ways;
    private int wayCount;

    public List<WayModel.WayModelNested> getWays() {
        return ways;
    }

    public int getWayCount() {
        return wayCount;
    }

    public void setWays(List<WayModel.WayModelNested> ways) {
        this.ways = ways;
    }

    public void setWayCount(int wayCount) {
        this.wayCount = wayCount;
    }

    public MultipleWayModel(List<WayModel.WayModelNested> waysCollected, int size) {
    this.ways = waysCollected;
    this.wayCount = size;
    }

    public static MultipleWayModel fromWays(Page<Way> way) {
        final var waysCollected = way.map(WayModel.WayModelNested::fromWay)
                .stream().collect(toList());
        return new MultipleWayModel(waysCollected, waysCollected.size());
    }
}
