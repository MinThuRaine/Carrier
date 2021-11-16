package io.github.minthurein.carrier.portfolio.way.domain;

import io.github.minthurein.carrier.portfolio.tag.domain.Tag;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;

@Embeddable
public class WayContents {

    @Embedded
    private WayHeader title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String body;

    @JoinTable(name = "ways_tags",
            joinColumns = @JoinColumn(name = "way_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false))
    @ManyToMany(fetch = EAGER, cascade = PERSIST)
    private Set<Tag> tags = new HashSet<>();

    public WayContents(String description, WayHeader title, String body, Set<Tag> tags) {
        this.description = description;
        this.title = title;
        this.body = body;
        this.tags = tags;
    }

    protected WayContents() {
    }

    public WayHeader getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBody() {
        return body;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    void updateWayContentsIfPresent(WayUpdateRequest updateRequest) {
        updateRequest.getTitleToUpdate().ifPresent(titleToUpdate -> title = titleToUpdate);
        updateRequest.getDescriptionToUpdate().ifPresent(descriptionToUpdate -> description = descriptionToUpdate);
        updateRequest.getBodyToUpdate().ifPresent(bodyToUpdate -> body = bodyToUpdate);
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }


}
