package com.digitech.difo.domain.Board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Date time;

    @Builder.Default
    private long likes = 0;

    public void setLikes(long likes) {
        this.likes = likes;
    }
    public long getBoardId() {
        return boardId;
    }

}
