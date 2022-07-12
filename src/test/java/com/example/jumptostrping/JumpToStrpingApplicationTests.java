package com.example.jumptostrping;

import com.example.jumptostrping.answer.AnswerRepository;
import com.example.jumptostrping.question.QuestionRepository;
import com.example.jumptostrping.answer.Answer;
import com.example.jumptostrping.question.Question;
import com.example.jumptostrping.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebAppConfiguration
@SpringBootTest
class JumpToStrpingApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;


    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 뭡니까?");
        q1.setContent("sbb 약어 설명 불친절");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("JPA?");
        q2.setContent("이게 지금 JPA 리포지토리 설정해서 쓰는것이다.");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);
    }
    @Test
    void testQuesry() {
        // 모든 쿼리 조회 List로 반환된다.
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 뭡니까?", q.getSubject());
    }
    @Test
    void testFindById(){
        // Id로 조회 Optional로 반환된다.(null 처리를 위함)
        Optional<Question> oq = this.questionRepository.findById(2);
        if(oq.isPresent()){
            Question q = oq.get();
            assertEquals("JPA?",q.getSubject());
        }
    }

    @Test
    void testFindBySubject(){
        Question q = this.questionRepository.findBySubject("sbb가 뭡니까?");
        assertEquals(1,q.getId());
    }

    @Test
    void testFindBySubjectAndContent(){
        Question q = this.questionRepository.findBySubjectAndContent("sbb가 뭡니까?","sbb 약어 설명 불친절");
        assertEquals(1,q.getId());
    }

    @Test
    void testFindBySubjectLike(){
        List<Question> questionList = this.questionRepository.findBySubjectLike("%?%");
        Question q1 = questionList.get(0);
        Question q2 = questionList.get(1);
        assertEquals("sbb가 뭡니까?",q1.getSubject());
        assertEquals("JPA?",q2.getSubject());
    }

    @Test
    void testDataChange(){
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("ssb?");
        this.questionRepository.save(q);
    }

    @Test
    void testDelete() {
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

// Answer Test

    @Test
    void testCreate(){
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("그렇군요!");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }
    @Test
    void testAnswer(){
        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue((oa.isPresent()));
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

    @Test
    @Transactional
    void testQuestionAnswer(){
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        // 여기서 세션 종료되서 에러가 발생함 test코드 한정 에러
        // @Transactional 통해 해결하거나( 종료때까지 세션유지)
        // fetch= FetchType를 통해 가져오는 방식 설정
        List<Answer> answerList = q.getAnswerList();

        assertEquals(1,answerList.size());
        assertEquals("그렇군요!",answerList.get(0).getContent());

    }
    @Test
    void testQuestion(){
        for (int i =0;i<=300;i++){
            String subject = String.format("테스트 데이터입니다: [%03d]",i);
            String content = "내용 무";
            this.questionService.create(subject,content,null);
        }
    }
}


