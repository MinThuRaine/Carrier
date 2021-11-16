package io.github.minthurein.carrier.portfolio.way.comment.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("comment")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class CommentPostRequestDTO {

    @NotBlank
    private final String body;

    public String getBody() {
        return body;
    }

    @JsonCreator
    public CommentPostRequestDTO(String body) {
        this.body = body;
    }
}
