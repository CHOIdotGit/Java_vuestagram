package com.example.vuestagram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class) //vue의 watchers? 같은 것
@Table(name = "boards")
@SQLDelete(sql = "UPDATE boards SET updated_at = now(), deleted_at = NOW() WHERE board_id = ?") // 기본 값은 물리 삭제. deletedAt을 감지하지 않는다.
@Where(clause = "deleted_at IS NULL")
public class Board {
    @Id // PK로 설정되어 기본적으로 NOT NULL이 적용되어 있다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne // Eager, Lazy. 라라벨은 기본 Lazy. 연결하기 - 자바에서 사용하기 위해 해당 모델 선택 괄호()를 붙여서 안에 fetch. ManyToOne은 Eager.
    @JoinColumn(name = "user_id") // FK 지정할 컬럼 적기. Board 테이블의 user_id이다.
    private User user; // 연결할 객체를 프로퍼티를 지정. 연결할 모델을 적어야 한다.

    @Column(name = "content", nullable = false, length = 200)
    private String content;

    @Column(name = "img", nullable = false, length = 100)
    private String img;

    @Column(name = "likes", nullable = false, length = 11) // ColumnDefault는 DB에서 제어하는 것.
    // 또는 ColumnDefinition 사용?
    private int likes;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAT;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAT;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAT;
}
