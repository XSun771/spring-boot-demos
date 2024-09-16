package com.example.mybatis_sql_injection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebController {

    private final ArticleMapper articleMapper;

    @RequestMapping("/enquiry")
    public String enquiry(@RequestBody Enquiry enquiry) {
        return this.articleMapper.select(enquiry.getColumnName(),enquiry.getColumnValue()).toString();
    }
}
