package com.sw.fd.controller;

import com.sw.fd.entity.Member;
import com.sw.fd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public String registerMember(Member member, Model model) {
        memberService.saveMember(member);
        model.addAttribute("message", "가입 성공! 환영합니다!");
        return "login"; // 회원가입 성공 후 이동할 페이지를 지정합니다.
    }
}
