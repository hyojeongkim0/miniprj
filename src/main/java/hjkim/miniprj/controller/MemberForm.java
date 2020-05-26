package hjkim.miniprj.controller;

import javax.validation.constraints.NotEmpty;

import hjkim.miniprj.domain.MemberType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotEmpty   //(message = "필수 입니다.")
    private String name;

    private MemberType memberType;

    @NotEmpty   //(message = "필수 입니다.")
    private String city;

    @NotEmpty   //(message = "필수 입니다.")
    private String street;

    @NotEmpty   //(message = "필수 입니다.")
    private String zipcode;

    private Integer lineCount;
 
    // 개인고객
    private String rsdRegistNum;
    private String handphone;

    // 법인고객
    private String bizNum;
    private String ceoName;
    private String officePhone;

    private Long id;

}