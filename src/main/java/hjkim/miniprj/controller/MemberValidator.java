package hjkim.miniprj.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hjkim.miniprj.domain.Member;
import hjkim.miniprj.domain.MemberType;

public class MemberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Member member = (Member) object;
        

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberType", "required", "필수입니다.");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lineCount", "required", "필수입니다.");

            if (member.getMemberType() != null && member.getMemberType().equals(MemberType.PERSON)) {

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rsdRegistNum", "required", "필수입니다.");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "handphone", "required", "필수입니다.");

            } else if (member.getMemberType() != null && member.getMemberType().equals(MemberType.COMPANY)) {

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bizNum", "required", "필수입니다.");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ceoName", "required", "필수입니다.");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "officePhone", "required", "필수입니다.");
            }
    
    }
    
}