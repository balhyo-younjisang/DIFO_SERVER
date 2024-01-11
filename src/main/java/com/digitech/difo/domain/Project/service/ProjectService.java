package com.digitech.difo.domain.Project.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.digitech.difo.domain.Member.domain.Member;
import com.digitech.difo.domain.MemberProject.domain.MemberProject;
import com.digitech.difo.domain.Member.dto.MemberDTO;
import com.digitech.difo.domain.Member.repository.MemberRepository;
import com.digitech.difo.domain.MemberProject.repository.MemberProjectRepository;
import com.digitech.difo.domain.Portfolio.domain.Portfolio;
import com.digitech.difo.domain.Portfolio.dto.PortfolioDTO;
import com.digitech.difo.domain.Portfolio.repository.PortfolioRepository;
import com.digitech.difo.domain.Project.domain.Project;
import com.digitech.difo.domain.Project.dto.ProjectDTO;
import com.digitech.difo.domain.Project.repository.ProjectRepository;
import com.digitech.difo.domain.ProjectStack.domain.ProjectStack;
import com.digitech.difo.domain.ProjectStack.repository.ProjectStackRepository;
import com.digitech.difo.domain.TechStack.domain.Stack;
import com.digitech.difo.domain.TechStack.dto.StackDTO;
import com.digitech.difo.domain.TechStack.repository.StackReposiroty;
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
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final MemberProjectRepository memberProjectRepository;
    private final ProjectStackRepository projectStackRepository;
    private final StackReposiroty stackReposiroty;
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
        this.projectRepository.save(project);

        List<MemberProject> projectMembers = new ArrayList<>();
        for(String email : ConvertUtil.StringToArray(registerProjectRequestDTO.getUserEmails())) {
            Member member = memberRepository.findByEmail(email);

            MemberProject memberProject = new MemberProject();
            memberProject.setProject(project);
            memberProject.setMember(member);

            projectMembers.add(memberProject);
            this.memberProjectRepository.saveAndFlush(memberProject);
        }

        List<ProjectStack> projectStackList = new ArrayList<>();
        for(String stackName : ConvertUtil.StringToArray(registerProjectRequestDTO.getTechStacks())) {
            Optional<Stack> stack = stackReposiroty.findByStackName(stackName);

            ProjectStack projectStack = new ProjectStack();
            projectStack.setProject(project);
            projectStack.setStack(stack.get());

            projectStackList.add(projectStack);
            this.projectStackRepository.saveAndFlush(projectStack);
        }

        project.setStacks(projectStackList);
        project.setMembers(projectMembers);
        this.projectRepository.saveAndFlush(project);

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
     * @todo stacks 데이터에 project id를 넣기
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
                Optional<Member> existsMember = this.memberRepository.findById(memberProject.getMember().getMemberId());

                if(existsMember.isEmpty()) continue;
                else members.add(existsMember.get().toDTO());
            }

            List<StackDTO.StackProjectResponseDTO> stacks = new ArrayList<>();
            for(ProjectStack projectStack : existsProject.get().getStacks()) {
                Optional<Stack> existsStack = this.stackReposiroty.findById(projectStack.getStack().getStackId());

                if(existsStack.isEmpty()) continue;
                else stacks.add(projectStack.getStack().toSummaryDTO());
            }

            return new SuccessResponse<>(true, existsProject.get().toDTO(members, stacks));
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

            projects.forEach(project -> projects_id.add(project.getProject().getProjectId()));

            return projects_id;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 프로젝트의 현재 좋아요수를 가져와 1을 증가시킨 후 저장
     * @param id
     * @return
     * @throws Exception
     */
    public SuccessResponse<Void> likeProject(Long id) throws Exception {
        try {
            Optional<Project> project = this.projectRepository.findById(id);
            long currentLike = project.get().getLikes();

            project.get().setLikes(currentLike + 1);
            this.projectRepository.saveAndFlush(project.get());

            return new SuccessResponse<>(true, null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public SuccessResponse<List<ProjectDTO.ProjectSummaryResponseDTO>> getRecommendProjects() throws Exception {
        try {
            List<Project> projects = this.projectRepository.findAllByOrderByLikes();
            List<ProjectDTO.ProjectSummaryResponseDTO> recommendProjectsDTO = new ArrayList<>();
            projects.forEach(project -> {
                Long projectId = project.getProjectId();
                String projectName = project.getProjectName();
                String thunmnail = project.getThumbnail();

                recommendProjectsDTO.add(project.toSummaryDTO(projectId, projectName, thunmnail));
            });
            return new SuccessResponse<>(true, recommendProjectsDTO);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public SuccessResponse<List<ProjectDTO.ProjectDetailsResponseDTO>> findAllProject() {
        List<Project> projects = this.projectRepository.findAll();

        List<MemberDTO.MemberResponseDTO> members = new ArrayList<>();
        for(int i = 0; i < projects.size(); i++) {
            for (MemberProject memberProject : projects.get(i).getMembers()) {
                Optional<Member> existsMember = this.memberRepository.findById(memberProject.getMember().getMemberId());

                if (existsMember.isEmpty()) continue;
                else members.add(existsMember.get().toDTO());
            }
        }

        List<StackDTO.StackProjectResponseDTO> stacks = new ArrayList<>();
        for(int i = 0; i < projects.size(); i++) {
            for (ProjectStack projectStack : projects.get(i).getStacks()) {
                Optional<Stack> existsStack = this.stackReposiroty.findById(projectStack.getStack().getStackId());

                if (existsStack.isEmpty()) continue;
                else stacks.add(projectStack.getStack().toSummaryDTO());
            }
        }

        List<ProjectDTO.ProjectDetailsResponseDTO> response = new ArrayList<>();
        for(int i = 0; i < projects.size(); i++) {
            response.add(projects.get(i).toDTO(members, stacks));
        }

        return new SuccessResponse<>(true, response);

    }
}
