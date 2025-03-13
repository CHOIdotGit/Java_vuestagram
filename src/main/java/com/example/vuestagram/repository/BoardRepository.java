package com.example.vuestagram.repository;


import com.example.vuestagram.model.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>  {
    @EntityGraph(attributePaths = {"user"})
    Optional<Board> findById(long id);
    // 레이지로 사용할 경우 엔티티를 사용해서 데이터를 함꼐 가져와야 한다.
}
