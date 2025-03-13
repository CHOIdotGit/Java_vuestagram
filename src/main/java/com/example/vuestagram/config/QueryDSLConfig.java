package com.example.vuestagram.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // << 얘는 무조건 설정 파일
public class QueryDSLConfig {
    // @PersistenceContext는 JPA에서 DB와 상호작용 하기 위한 객체 << 이 객체가 EntityManager
    // Spring Context에 자동으로 주입해주는 어노테이션이다.
    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
