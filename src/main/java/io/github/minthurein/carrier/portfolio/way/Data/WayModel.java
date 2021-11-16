package io.github.minthurein.carrier.portfolio.way.Data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.github.minthurein.carrier.portfolio.tag.domain.Tag;
import io.github.minthurein.carrier.portfolio.user.data.ProfileModel.ProfileModelNested;
import io.github.minthurein.carrier.portfolio.way.domain.Way;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class WayModel {

    private WayModelNested way;


    public WayModel(WayModelNested fromWay) {
        this.way = fromWay;
    }

    public static WayModel fromWay(Way wayCreated) {
        return new WayModel(WayModelNested.fromWay(wayCreated));
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class WayModelNested {
        String slug;
        String title;
        String description;
        String body;
        Set<String> tagList;
        ZonedDateTime createdAt;
        ZonedDateTime updatedAt;
        ProfileModelNested owner;

        private WayModelNested(String slug, String title, String description, String body, Set<String> tagList, ZonedDateTime createdAt, ZonedDateTime updatedAt, ProfileModelNested fromProfile) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = tagList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.owner = fromProfile;
        }

        public static WayModelNested fromWay(Way way) {
            final var contents = way.getContents();
            final var titleFromWay = contents.getTitle();
            return new WayModelNested(
                    titleFromWay.getSlug(), titleFromWay.getTitle(),
                    contents.getDescription(), contents.getBody(),
                    contents.getTags().stream().map(Tag::toString).collect(toSet()),
                    way.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    way.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    ProfileModelNested.fromProfile(way.getOwner().getProfile())
            );
        }

    }
}
