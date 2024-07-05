package com.mysite.csJpa.question.mapper;

import com.mysite.csJpa.common.paging.RequestList;
import com.mysite.csJpa.common.paging.SearchDto;
import com.mysite.csJpa.question.Question;
import com.mysite.csJpa.question.dto.QuestionResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuestionMapper {
    List<QuestionResponse> findAll(RequestList<?> requestList);

    int getQuestionCount(SearchDto params);
}
