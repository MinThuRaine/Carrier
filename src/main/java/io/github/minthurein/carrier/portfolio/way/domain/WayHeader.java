package io.github.minthurein.carrier.portfolio.way.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class WayHeader {

    @Column(nullable = false)
    private String  wayid;

    @Column(nullable = false)
    private String slug;

    public static WayHeader of(String wayid) {
        return new WayHeader(wayid, slugFromTitle(wayid));
    }

    private WayHeader(String wayid, String slug) {
        this.wayid = wayid;
        this.slug = slug;
    }

    protected WayHeader() {
    }

    private static String slugFromTitle(String title) {
        return title.toLowerCase()
                .replaceAll("\\$,'\"|\\s|\\.|\\?", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("(^-)|(-$)", "");
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return wayid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WayHeader that = (WayHeader) o;
        return slug.equals(that.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }
}
