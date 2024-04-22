package com.mysite.csJpa;

import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CsJpaApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void save() {
		Question q1 = new Question();
		q1.setSubject("무슨 홈페이지인가요?");
		q1.setContent("어떤 건지 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("아무 글이나 올려도 되나요?");
		q2.setContent("답변 부탁드립니다.");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);
	}

	@Test
	void findAll() {
		List<Question> all = questionRepository.findAll();
		assertThat(2).isEqualTo(all.size());

		Question q = all.get(0);
		assertThat("무슨 홈페이지인가요?").isEqualTo(q.getSubject());

		Question q1 = questionRepository.findBySubject("아무 글이나 올려도 되나요?");
		assertThat("아무 글이나 올려도 되나요?").isEqualTo(q1.getSubject());
	}

	@Test
	void subject_like() {
		List<Question> qList = questionRepository.findBySubjectLike("무%");
		Question q = qList.get(0);
		assertThat("무슨 홈페이지인가요?").isEqualTo(q.getSubject());
	}
}
