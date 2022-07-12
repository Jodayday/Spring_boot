package com.example.jumptostrping.question;


import com.example.jumptostrping.controller.DataNotFoundException;
import com.example.jumptostrping.answer.Answer;
import com.example.jumptostrping.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec, pageable);

    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()){
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setAuthor(user);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save((question));
    }
    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }

    private Specification<Question> search(String kw){
        return new Specification<Question>() {

            private static final long serialVersionUID  =1L;

            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true);
                Join<Question, SiteUser> u1 = root.join("author",JoinType.LEFT);
                Join<Question, Answer> a = root.join("answerList",JoinType.LEFT);
                Join<Answer, SiteUser> u2 = root.join("author",JoinType.LEFT);
                return criteriaBuilder.or(criteriaBuilder.like(root.get("subject"), "%" + kw + "%"),
                        criteriaBuilder.like(root.get("content"), "%" + kw + "%"),
                        criteriaBuilder.like(u1.get("username"), "%" + kw + "%"),
                        criteriaBuilder.like(a.get("content"), "%" + kw + "%"),
                        criteriaBuilder.like(u2.get("username"), "%" + kw + "%"));

            }
        };
    }


}
