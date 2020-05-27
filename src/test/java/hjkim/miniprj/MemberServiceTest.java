package hjkim.miniprj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import hjkim.miniprj.domain.Address;
import hjkim.miniprj.domain.Member;
import hjkim.miniprj.domain.MemberType;
import hjkim.miniprj.repository.MemberRepository;
import hjkim.miniprj.service.MemberService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;
    
    @Test
    public void 회원가입() throws Exception {

        //Given
        Member member = new Member();

        member.setName("kim");
        member.setAddress(new Address("서울", "강가", "123-123"));
        member.setLineCount(2);
        member.setMemberType(MemberType.PERSON);
        member.setRsdRegistNum("123123123");
        member.setHandphone("010-1234-1234");

        //When
        Long saveId = memberService.join(member);

        //Then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));

    }

    
    @Test(expected = AssertionError.class)
    public void 중복_회원_가입() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("kim");
        member1.setAddress(new Address("서울", "강가", "123-123"));
        member1.setLineCount(2);
        member1.setMemberType(MemberType.PERSON);
        member1.setRsdRegistNum("123123123");
        member1.setHandphone("010-1234-1234");


        Member member2 = new Member();
        member2.setName("Hyojeong");
        member2.setAddress(new Address("서울", "강서", "123-123"));
        member2.setLineCount(1);
        member2.setMemberType(MemberType.PERSON);
        member2.setRsdRegistNum("123123123");
        member2.setHandphone("010-1111-2222");

        //When
        memberService.join(member1);
        memberService.join(member2);
        //Then

        fail("주민번호 중복!!");
    }

    
}