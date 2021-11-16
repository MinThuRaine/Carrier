package io.github.minthurein.carrier.portfolio.way.domain;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class WayUpdateRequest {

    private final WayHeader titleToUpdate;
    private final String descriptionToUpdate;
    private final String bodyToUpdate;

    public static WayUpdateRequestBuilder builder() {
        return new WayUpdateRequestBuilder();
    }

    Optional<WayHeader> getTitleToUpdate() {
        return ofNullable(titleToUpdate);
    }

    Optional<String> getDescriptionToUpdate() {
        return ofNullable(descriptionToUpdate);
    }

    Optional<String> getBodyToUpdate() {
        return ofNullable(bodyToUpdate);
    }

    private WayUpdateRequest(WayUpdateRequestBuilder builder) {
        this(builder.titleToUpdate, builder.descriptionToUpdate, builder.bodyToUpdate);
    }

    private WayUpdateRequest(WayHeader titleToUpdate, String descriptionToUpdate, String bodyToUpdate) {
        this.titleToUpdate = titleToUpdate;
        this.descriptionToUpdate = descriptionToUpdate;
        this.bodyToUpdate = bodyToUpdate;
    }

    public static class WayUpdateRequestBuilder {
        private WayHeader titleToUpdate;
        private String descriptionToUpdate;
        private String bodyToUpdate;

        public WayUpdateRequestBuilder titleToUpdate(WayHeader titleToUpdate) {
            this.titleToUpdate = titleToUpdate;
            return this;
        }
        public WayUpdateRequestBuilder descriptionToUpdate(String descriptionToUpdate) {
            this.descriptionToUpdate = descriptionToUpdate;
            return this;
        }
        public WayUpdateRequestBuilder bodyToUpdate(String bodyToUpdate) {
            this.bodyToUpdate = bodyToUpdate;
            return this;
        }

        public WayUpdateRequest build() {
            return new WayUpdateRequest(this);
        }
    }
}
