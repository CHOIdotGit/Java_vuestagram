package com.example.vuestagram.service;

import com.example.vuestagram.model.Board;
import com.example.vuestagram.model.QBoard;
import com.example.vuestagram.repository.BoardRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final JPAQueryFactory queryFactory;

    public Board test() {
        //Optional<Board> board = boardRepository.findById(150L);

        // QueryDSL. // JPA가 아닌 별도로 따로 동작하는 라이브러리. // 중간 중간에 더 연결해서 사용한다.
        QBoard qBoard = QBoard.board; // QueryDSL이 자동으로 생성해주는 Board 엔티티 기반의 클래스. new를 사용해 인스턴스 하지 않음?

        // q class를 넣으면 q class에 저장되어 있는 컬럼이 나온다.
        JPAQuery<Board> query = queryFactory.selectFrom(qBoard); // select 대신 selectFrom으로 한 번에 가져올 수 있다.
//                                            .where(
//                                                    qBoard.boardId.eq(150L)
//                                            );
        if(true) {
            query.where(
                    qBoard.boardId.eq(150L)
            );
        }

        return query.fetchFirst();
        // board.get();
        // 라라벨로 비유하면 get과 first의 차이. 하지만 JPA로 반환하는 것은 가공하기 힘들다.
    }
}
