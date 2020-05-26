package hjkim.miniprj.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hjkim.miniprj.domain.Address;
import hjkim.miniprj.domain.Member;
import hjkim.miniprj.domain.MemberType;
import hjkim.miniprj.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원 전체조회 API
    @GetMapping("/customer")
    public Result memberFind() {

        List<Member> findMembers = memberService.findMembersAll();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getId(), m.getName(), m.getAddress(), m.getLineCount(), m.getHandphone()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    // 회원개별 조회 API
    @GetMapping("/customer/{id}")
    public Result memebrFindOne(@PathVariable("id") Long id) {
        List<Member> oneMember = memberService.findMemberById(id);
        List<MemberDto> collect = oneMember.stream()
                .map(m -> new MemberDto(m.getId(), m.getName(), m.getAddress(), m.getLineCount(), m.getHandphone()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    //전화번호 변경 API
    @PutMapping("/customer/{id}")
    public MemberResponse updateMemberHandphone(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
 
        String status;
        String message;

        memberService.updateHandphone(id, request.getHandphone());
        List<Member> oneMember =  memberService.findMemberById(id);

        if (oneMember.size() > 0) {
            status = "SUCCESS";
            message = "전화번호 변경 성공";
        } else {
            status = "ERROR";
            message = "전화번호 수정 오류";
        }
        return new MemberResponse(oneMember.size(), status, message);
    }


    //개인고객 회원등록 API
    @PostMapping("/customer")
    public MemberResponse memberSave(@RequestBody @Valid CreateMemberRequest request) {
        int cnt;
        String status;
        String message;

        Member member = new Member();
        member.setName(request.getName());
        member.setAddress(request.getAddress());
        member.setLineCount(request.getLineCount());
        member.setMemberType(MemberType.PERSON);
        
        Long id = memberService.join(member);

        if (id != null) {
            cnt  = 1;
            status = "SUCCESS";
            message = "회원등록 성공";
        } else {
            cnt = 0;
            status = "ERROR";
            message = "회원등록 실패";
        }

        return new MemberResponse(cnt, status, message);
    }

    //회원삭제 API
    @DeleteMapping("/customer/{id}")
    public MemberResponse memberDelete(@PathVariable("id") Long id, @RequestBody @Valid DeletMemberRequest request){

        int cnt;
        String status;
        String message;

        memberService.deleteMember(id);
        List<Member> oneMember =  memberService.findMemberById(id);

        if (oneMember.size() > 0) {
            cnt = 0;
            status = "ERROR";
            message = "회원 삭제 오류";
        } else {
            cnt = 1;
            status = "SUCCESS";
            message = "회원 삭제 성공";
        }
        return new MemberResponse(cnt, status, message);

    }
    
  
    //고객등록
    @Data
    static class CreateMemberRequest {
        private String name;
        private Address address;
        private int lineCount;
    }

    //고객수정
    @Data
    static class UpdateMemberRequest {
        private String handphone;
    }

    //고객삭제
    @Data
    static class DeletMemberRequest {
        private Long id;
    }
    //transaction 응답
    @Data
    @AllArgsConstructor
    class MemberResponse {
        private int count;
        private String status;
        private String message;
    }

    //고객조회
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
     
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private long id;
        private String name;
        private Address address;
        private int lineCount;
        private String handphone;
    }

}