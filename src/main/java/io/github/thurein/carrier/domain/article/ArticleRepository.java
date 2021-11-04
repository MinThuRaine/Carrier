package io.github.thurein.carrier.domain.article;

import io.github.thurein.carrier.domain.article.tag.Tag;
import io.github.thurein.carrier.domain.user.User;
import io.github.thurein.carrier.domain.user.UserName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ArticleRepository extends Repository<Article, Long> {

    Article save(Article article);

    Page<Article> findAll(Pageable pageable);
    Page<Article> findAllByUserFavoritedContains(User user, Pageable pageable);
    Page<Article> findAllByAuthorProfileUserName(UserName authorName, Pageable pageable);
    Page<Article> findAllByContentsTagsContains(Tag tag, Pageable pageable);
    Optional<Article> findFirstByContentsTitleSlug(String slug);

    void deleteArticleByAuthorAndContentsTitleSlug(User author, String slug);

}
