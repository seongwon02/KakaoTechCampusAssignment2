package com.example.scheduler.lv6.entitiy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Page {
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
}
