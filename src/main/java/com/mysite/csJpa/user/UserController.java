package com.mysite.csJpa.user;

import com.mysite.csJpa.user.dto.AddUserRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(AddUserRequest request) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid AddUserRequest addUserRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!addUserRequest.getPassword().equals(addUserRequest.getPassword2())) {
            //bindingResult.rejectValue(필드명, 오류 코드, 오류 메시지)
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.save(addUserRequest);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
