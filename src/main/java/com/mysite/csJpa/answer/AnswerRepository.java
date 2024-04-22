package com.mysite.csJpa.answer;

import com.mysite.csJpa.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
