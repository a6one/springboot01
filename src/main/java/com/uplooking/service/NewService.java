package com.uplooking.service;

import com.uplooking.dao.NewsRepository;
import com.uplooking.pojo.New;
import com.uplooking.pojo.Person;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NewService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * SearchQuery
     * NativeSearchQueryBuilder
     * QueryBuilder ===>QueryBuilders
     * SortBuilder ===>SortBuilders
     * Pageable
     *
     */

    /*public void es() {
        GeoDistanceQueryBuilder queryBuilder = QueryBuilders.geoDistanceQuery("address").distance(100, DistanceUnit.METERS);
        GeoDistanceSortBuilder sortBuilder = SortBuilders.geoDistanceSort("adders").unit(DistanceUnit.METERS).order(SortOrder.DESC);
        Pageable pageable = new PageRequest(1, 10);
        SearchQuery query = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withSort(sortBuilder)
                .withPageable(pageable)
                .build();
        elasticsearchTemplate.queryForList(query, New.class);
    }*/

//    public void esTest(String word, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable) {
//        SearchQuery query = new NativeSearchQueryBuilder()
//                                    .withQuery(QueryBuilders.queryStringQuery(word)) //单值匹配
//                                    .withQuery(QueryBuilders.matchQuery("word", word)) //模糊匹配
//                                    .withQuery(QueryBuilders.matchPhraseQuery("word", word)//短语匹配（连续的）
//                                                .slop(2)) //允许短语间隔2位的差距
//                                    .withQuery(QueryBuilders.termQuery("word", word))//精致匹配
//                                    .withQuery(QueryBuilders.multiMatchQuery("word",word,"苹果" ))//多值匹配
//                                    //ES默认将中文是按照每个汉字进行分词的
//                                    .withQuery(QueryBuilders.matchQuery("word", word).operator(MatchQueryBuilder.Operator.AND).minimumShouldMatch("75%"))//必须组合匹配（匹配精度）
//                                    .withQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("word", word))//合并匹配
//                                                                        .should(QueryBuilders.matchQuery("word", word))
//                                                                        .filter(QueryBuilders.rangeQuery("word").gt(10))
//                                                                        .mustNot(QueryBuilders.termQuery("word",word )))
//                                    .build();
//        elasticsearchTemplate.queryForList(query, New.class);
//    }

    public Iterable<New> findAll() {
        return newsRepository.findAll();
    }

    public Iterable<New> search(QueryBuilder builder) {
        return newsRepository.search(builder);
    }

    public List<New> findByTitle(String title) {
        return this.newsRepository.findByTitle(title);
    }

    public void deleteAll(Long id) {
        this.newsRepository.delete(id);
    }

    /*public void init() {
        for (int i = 0; i < 100; i++) {
            New aNew = new New();
            aNew.setId(Long.valueOf(i));
            aNew.setTitle(i + "单元测试");
            aNew.setContent(i + "单元内容");
            aNew.setCreateDateTime(new Date());
            this.newsRepository.save(aNew);
        }
    }*/


    public static void main(String[] args) throws InterruptedException {
        long convert = TimeUnit.MINUTES.convert(1, TimeUnit.HOURS);
        long hours = TimeUnit.HOURS.toMinutes(1);
        TimeUnit.HOURS.timedWait(new Person(), 1L);
        System.out.println(convert);
        System.out.println(hours);
    }
}
