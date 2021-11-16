package io.github.minthurein.carrier.portfolio.way.Data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.minthurein.carrier.portfolio.tag.domain.Tag;
import io.github.minthurein.carrier.portfolio.way.domain.WayContents;
import io.github.minthurein.carrier.portfolio.way.domain.WayHeader;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("way")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class WayPostRequestDTO {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String body;
    @NotNull
    private Set<Tag> tagList;

    public WayContents towayContents() {
        return new WayContents(description, WayHeader.of(title), body, tagList);
    }

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

    public Set<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(Set<Tag> tagList) {
        this.tagList = tagList;
    }
}
