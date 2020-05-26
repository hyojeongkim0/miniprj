package hjkim.miniprj.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id  @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Enumerated(EnumType.STRING)
    private MemberType memberType; //회원유형 [PERSON(개인), COMPANY(법인)]
    //private String memberType; 
    
    @Embedded
    private Address address;   
    
    private Integer lineCount;

    private String rsdRegistNum;

    private String handphone;

    private String bizNum;

    private String ceoName;

    private String officePhone;

}