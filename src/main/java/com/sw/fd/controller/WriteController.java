package com.sw.fd.controller;

import com.sw.fd.entity.Board;
import com.sw.fd.entity.Member;
import com.sw.fd.entity.Write;
import com.sw.fd.service.BoardService;
import com.sw.fd.service.WriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WriteController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private WriteService writeService;

    @GetMapping("/write")
    public String showWriteForm(@RequestParam("bno") int bno, HttpSession session, Model model){
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if(loggedInMember == null){
            return "redirect:/login";
        }else{

            model.addAttribute("bno", bno);
            model.addAttribute("member", loggedInMember.getMnick());
            model.addAttribute("write", new Write());
            return "write";
        }
    }

    @PostMapping("/submitWrite")
    public String submitWrite(@ModelAttribute Write write,HttpSession session, BindingResult result, Model model) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if (result.hasErrors()) {
            return "write";
        }

        write.setMember(loggedInMember);
        writeService.saveWrite(write);
        model.addAttribute("message", "글쓰기 성공!");

        return "redirect:/board?gno="+boardService.getGnoByBno(write.getBoard().getBno());
    }
}
