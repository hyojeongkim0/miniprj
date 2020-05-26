package hjkim.miniprj.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSearch {
    private String memberName; //회원 이름
    private MemberType memberType;//회원유형 (PERSON, COMPANY)
}