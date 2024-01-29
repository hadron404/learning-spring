package com.example.quickstartes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.Query;
import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class QuickstartEsApplicationTests {

	@Test
	void contextLoads() {
	}

	@DisplayName("含所有标签")
	@Test
	void findByTag() {
		Pageable pageable = PageRequest.of(0, 10);
		List<Long> tagIds = List.of(43L, 7L);
//        tag 中 含有所有条件
		List<Query> queries = tagIds.stream()
			.map(i -> Query.of(m -> m.match(t -> t.field("tag").query(i))))
			.collect(Collectors.toList());
		NativeQueryBuilder nativeQueryBuilder = NativeQuery.builder()
			.withQuery(q -> q.bool(BoolQuery.of(b -> b.must(queries))))
			.withPageable(pageable);
//        Criteria criteria = new Criteria()
//                .and(new Criteria("tag").is("43"), new Criteria("tag").is("7"));
//        等价于
//        Criteria criteria = new Criteria()
//                .and("tag").is("43")
//                .and("tag").is("7");
//        Query query = new CriteriaQuery(criteria);
//        等价于
//        Query query = new StringQuery("""
//                { "bool": { "must": { "bool": { "must": [ { "match": { "tag": "43" } }, { "match": { "tag": "6" } } ] } } } }
//                """);
		SearchHits<EsGroupTagDTO> result =
			elasticsearchOperations.search(nativeQueryBuilder.build(), EsGroupTagDTO.class);
		System.out.println(result);
	}

	@DisplayName("含任意标签")
	@Test
	void findByTag2() {
		List<Long> tagIds = List.of(43L, 7L);
		Pageable pageable = PageRequest.of(0, 10);
		Page<EsGroupTagDTO> result = weworkTagGroupRepo.findAllByTagIn(tagIds, pageable);
		System.out.println(result);
//         等价于 CriteriaQuery
//        Criteria criteria = new Criteria()
//                .or("tag").is("43")
//                .or("tag").is("7");
//        Query query = new CriteriaQuery(criteria);
//        等价于 StringQuery
//        Query query = new StringQuery("""
//                 { "bool": { "must": { "bool": { "should": [ { "match": { "tag": "43" } }, { "match": { "tag": "6" } } ] } } } }
//                """);
//        SearchHits<EsGroupTagDTO> result =
//        elasticsearchOperations.search(query, EsGroupTagDTO.class);
	}

}
