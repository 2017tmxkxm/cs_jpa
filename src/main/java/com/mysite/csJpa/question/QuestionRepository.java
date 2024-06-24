package com.mysite.csJpa.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "left outer join Category c on q.category=c "
            + "where "
            + "  q.subject like %:kw% and c.id =:categoryId "
            )
            //+ "   or (:kw is null or q.content like %:kw%) "
            //+ "   or (:kw is null or u1.username like %:kw%) "
            //+ "   or (:kw is null or a.content like %:kw%) "
            //+ "   or (:categoryId is null or c.id =:categoryId) " )
            //+ "   or (:kw is null or u2.username like %:kw%) ")
    Page<Question> findAll(@Param("kw") String kw, @Param("categoryId") int categoryId, Pageable pageable);
}
