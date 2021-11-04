package io.github.thurein.carrier.domain.article;

import java.util.Optional;

public interface ArticleFindService {

    Optional<Article> getArticleBySlug(String slug);
}
