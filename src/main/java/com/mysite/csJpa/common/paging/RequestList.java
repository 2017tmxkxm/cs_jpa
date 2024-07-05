package com.mysite.csJpa.common.paging;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Builder
public class RequestList<T> {
    private T data;
    private Pageable pageable;
}
