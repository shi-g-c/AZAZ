package com.azaz.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 分页请求参数基类
 * @author shigc
 * &#064;date  2023-10-15
 */
@Data
@Slf4j
public class PageRequestDto {

    protected Integer size;
    protected Integer page;

    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }
}
