package hjkim.miniprj.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hjkim.miniprj.domain.Member;
import hjkim.miniprj.domain.MemberSearch;
import hjkim.miniprj.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;

    /** 
     *  회원가입
    */
    @Transactional
    public Long join(Member member) {
        
        validateDupRsdRegistNum(member); //주민등록번호 중복체크
        validateDupBizNum(member); //사업자등록번호 중복체크

        memberRepository.save(member);
        return member.getId();
    
    }

    private void validateDupRsdRegistNum(Member member) {
        List<Member> findMembers = memberRepository.findByRsdRegistNum(member.getRsdRegistNum());
        if (!findMembers.isEmpty()) {
        throw new IllegalStateException("이미 등록되어 있습니다.");
        }
        }

    private void validateDupBizNum(Member member) {
        List<Member> findMembers = memberRepository.findByBizNum(member.getBizNum());
        if (!findMembers.isEmpty()) {
        throw new IllegalStateException("이미 등록되어 있습니다.");
        }
        }
    

    //조회 조건으로 회원 조회
    public List<Member> findMembers(MemberSearch memberSearch) {
        return memberRepository.findAllByString(memberSearch);
    }

    //회원 전체 조회
    public List<Member> findMembersAll() {
        return memberRepository.findAll();
    }

    //회원 한명 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //회원 수정
    @Transactional
    public void saveMember(Member member){
        memberRepository.save(member);
    }

  
    //회원핸드폰번호 수정(for API)
    @Transactional
    public void updateHandphone(Long id, String handphone){
        Member member = memberRepository.findOne(id);
        member.setHandphone(handphone);
    }

    //회원 삭제(for API)
    @Transactional
    public void deleteMember(Long id){
        memberRepository.deleteOne(id);
    }
    
 }