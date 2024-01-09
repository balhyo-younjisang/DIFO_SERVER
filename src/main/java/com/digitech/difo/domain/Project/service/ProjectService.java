package com.digitech.difo.domain.Project.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.MemberProject.domain.MemberProject;
import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Member.repository.MemberRepository;
import com.digitech.difo.domain.MemberProject.repository.MemberProjectRepository;
import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.domain.Project.repository.ProjectRepository;
import com.digitech.difo.global.common.SuccessResponse;
import com.digitech.difo.global.common.utils.ConvertUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final EntityManager entityManager;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final MemberProjectRepository memberProjectRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3Client.putObject(bucket, originalFileName, multipartFile.getInputStream(), metadata);
        return amazonS3Client.getUrl(bucket, originalFileName).toString();
    }

    /**
     * Project 데이터를 Entity 화 시킨 후 데이터를 DB에 저장
     *
     * @param registerProjectRequestDTO
     * @return
     * @throws IOException
     */
    @Transactional
    public SuccessResponse<Project> registerProject(ProjectDTO.RegisterProjectRequestDTO registerProjectRequestDTO) throws IOException {
        // 썸네일 이미지 저장 후 이미지 url 가져오기
        String imageUrl = this.saveFile(registerProjectRequestDTO.getThumbnail());
        Project project = registerProjectRequestDTO.toEntity(imageUrl);
        entityManager.persist(project);

        for(String email : ConvertUtil.StringToArray(registerProjectRequestDTO.getUserEmails())) {
            Member member = memberRepository.findByEmail(email);

            MemberProject memberProject = new MemberProject();
            memberProject.setProject(project);
            memberProject.setMember(member);
            entityManager.persist(memberProject);
        }


        return new SuccessResponse<Project>(true, project);
    }

    /**
     * 프로젝트 삭제
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional
    public SuccessResponse<Void> deleteProject(Long id) throws Exception {
        try {
            Optional<Project> existsProject = this.projectRepository.findById(id);

            if(existsProject.isEmpty()) {
                throw new NotFoundException("Project is Not found");
            }

            this.projectRepository.deleteById(id);
            return new SuccessResponse<Void>(true, null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 프로젝트 디테일 가져와서 리턴
     * @param id
     * @return
     */
    @Transactional
    public SuccessResponse<ProjectDTO.ProjectDetailsResponseDTO> getProjectDetails(Long id) throws Exception {
        try {
            Optional<Project> existsProject = this.projectRepository.findById(id);

            if(existsProject.isEmpty()) {
                throw new NotFoundException("Project is Not Found");
            }

            List<MemberDTO.MemberResponseDTO> members = new ArrayList<>();
            for(MemberProject memberProject : existsProject.get().getMembers()) {
                Optional<Member> foundedMember = this.memberRepository.findById(memberProject.getMember().getMemberId());

                if(foundedMember.isEmpty()) continue;
                else members.add(foundedMember.get().toDTO());
            }

            return new SuccessResponse<>(true, existsProject.get().toDTO(members));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * 멤버 아이디로 멤버를 검색 후 해당 멤버가 참여한 프로젝트 리스트를 리턴
     * @param memberId
     * @return
     * @throws Exception
     */
    @Transactional
    public List<Long> findByMemberId(Long memberId) throws Exception {
        try {
            Optional<Member> joinedMember = this.memberRepository.findById(memberId);
            List<MemberProject> projects = this.memberProjectRepository.findAllByMember(joinedMember.get());
            List<Long> projects_id = new ArrayList<>();

            projects.forEach(project -> projects_id.add(project.getProject().getProject_id()));

            return projects_id;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
