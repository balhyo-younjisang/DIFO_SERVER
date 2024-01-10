package com.digitech.difo.domain.MemberProject.domain;

import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.Project.domain.Project;
import jakarta.persistence.*;
import lombok.*;

/**
 * 프로젝트와 멤버를 다대다 관계 매핑하기 위한 연결 클래스
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberProjectId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberID")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="ProjectID")
    private Project project;
}
