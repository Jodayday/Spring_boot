package com.example.jumptostrping.question;

import com.example.jumptostrping.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // subject값으로 데이터 조회
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);
    Page<Question> findAll(Specification<Question> spec,Pageable pageable);
}
