package com.mysite.csJpa.common.paging;

import lombok.Builder;
import lombok.Data;

@Builder
public class SearchDto {
    private int categoryId;
    private String subject;
}
