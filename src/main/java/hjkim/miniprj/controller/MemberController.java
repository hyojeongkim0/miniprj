package hjkim.miniprj.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hjkim.miniprj.domain.Address;
import hjkim.miniprj.domain.Member;
import hjkim.miniprj.domain.MemberSearch;
import hjkim.miniprj.repository.MemberRepository;
import hjkim.miniprj.service.MemberService;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
        
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        Member member = new Member();
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        member.setName(form.getName());
        member.setMemberType(form.getMemberType());
        member.setAddress(address);

        member.setLineCount(form.getLineCount());
  
        member.setRsdRegistNum(form.getRsdRegistNum());
        member.setHandphone(form.getHandphone());

        member.setBizNum(form.getBizNum());
        member.setCeoName(form.getCeoName());
        member.setOfficePhone(form.getOfficePhone());

        new MemberValidator().validate(member, result);
        

        if ((member.getRsdRegistNum() !=null && !member.getRsdRegistNum().equals(""))) {
            List<Member> findMembers = memberRepository.findByRsdRegistNum(member.getRsdRegistNum());
            if (!findMembers.isEmpty()) {
                result.rejectValue("rsdRegistNum", "dupCheck");
            }
        }

        if ((member.getBizNum() !=null && !member.getBizNum().equals(""))) {
            List<Member> findMembers = memberRepository.findByBizNum(member.getBizNum());
            if (!findMembers.isEmpty()) {
                result.rejectValue("bizNum", "dupCheck");
            }
        }

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        memberService.join(member);
        return "redirect:/members";

    }

    @GetMapping(value = "/members")
    public String list(@ModelAttribute("memberSearch") MemberSearch memberSearch, Model model) {
        List<Member> members = memberService.findMembers(memberSearch);
        model.addAttribute("members",  members);

        return "members/memberList";
    }

    @GetMapping("members/{memberId}/edit")
    public String updateMemberForm(@PathVariable("memberId") Long memberId, Model model) {

        Member member = memberService.findOne(memberId);

        MemberForm form = new MemberForm();
        form.setId(member.getId());
        form.setName(member.getName());

        form.setMemberType(member.getMemberType());
        form.setCity(member.getAddress().getCity());
        form.setStreet(member.getAddress().getStreet());
        form.setZipcode(member.getAddress().getZipcode());
        form.setLineCount(member.getLineCount());

        form.setRsdRegistNum(member.getRsdRegistNum());
        form.setHandphone(member.getHandphone());
        
        form.setBizNum(member.getBizNum());
        form.setCeoName(member.getCeoName());
        form.setOfficePhone(member.getOfficePhone());


        model.addAttribute("form", form);
        return "members/updateMemberForm";

    }
    //  public String create(@Valid MemberForm form, BindingResult result) {
    @PostMapping("members/{memberId}/edit")
    public String updateMember(@PathVariable String memberId, @ModelAttribute("form") @Valid MemberForm form, BindingResult result){

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setId(form.getId());
        member.setName(form.getName());
    
        member.setMemberType(form.getMemberType());
        member.setAddress(address);
        member.setLineCount(form.getLineCount());
        member.setRsdRegistNum(form.getRsdRegistNum());
        member.setHandphone(form.getHandphone());

        member.setBizNum(form.getBizNum());
        member.setCeoName(form.getCeoName());
        member.setOfficePhone(form.getOfficePhone());

        new MemberValidator().validate(member, result);

        if (result.hasErrors()) {
            return "members/updateMemberForm";
        }

        memberService.saveMember(member);
        return "redirect:/members";
    }
    
}