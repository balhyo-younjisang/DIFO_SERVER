package com.digitech.difo.domain.Member.domain;

import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Project.domain.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String picture;

    @Column
    private String githubUrl;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "member", orphanRemoval = true)
    private List<MemberProject> projects = new ArrayList<>();


    public MemberDTO.MemberResponseDTO toDTO() {
        return MemberDTO.MemberResponseDTO.builder().memberId(memberId).email(email).name(name).githubUrl(githubUrl).build();
    }
}
