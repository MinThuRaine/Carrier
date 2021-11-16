package io.github.minthurein.carrier.portfolio.way.Data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.minthurein.carrier.portfolio.way.domain.WayHeader;
import io.github.minthurein.carrier.portfolio.way.domain.WayUpdateRequest;


import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Optional.ofNullable;

@JsonTypeName("way")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
 public class WayPutRequestDTO {

    String title;
    String description;
    String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public WayUpdateRequest toUpdateRequest() {
        return WayUpdateRequest.builder().titleToUpdate(ofNullable(title).map(WayHeader::of).orElse(null))
                .descriptionToUpdate(description)
                .bodyToUpdate(body)
                .build();
    }
}
