package com.sw.fd.controller;

import com.sw.fd.entity.Member;
import com.sw.fd.entity.Pfolder;
import com.sw.fd.entity.Pick;
import com.sw.fd.entity.Store;
import com.sw.fd.service.PfolderService;
import com.sw.fd.service.PickService;
import com.sw.fd.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.ManyToOne;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;

@Controller
public class PickController {

    @Autowired
    private PickService pickService;

    @Autowired
    private PfolderService pfolderService;

    @Autowired
    private StoreService storeService;

    @GetMapping("/pickList")
    public String pickList(HttpSession session, Model model) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "redirect:/login";
        }

        int pfno = 1;
        List<Pick> pickList = pickService.getPicksByPfnoAndMno(pfno, loggedInMember.getMno());
        List<Pfolder> pfolderList = pfolderService.getPfoldersByMno(loggedInMember.getMno());

        model.addAttribute("pickList", pickList);
        model.addAttribute("pfolderList", pfolderList);

        return "pickPage";
    }


    @PostMapping("/pick")
    @ResponseBody
    public String pickStore(@RequestParam("sno") int sno, @RequestParam(value = "pfno", defaultValue = "1") int pfno, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "error"; // 로그인되지 않은 상태에서 예외 처리
        }

        int mno = loggedInMember.getMno();
        boolean isPicked = pickService.togglePick(mno, sno, pfno);
        return isPicked ? "picked" : "unpicked";
    }

    @PostMapping("/checkPick")
    @ResponseBody
    public String checkPick(@RequestParam("sno") int sno, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "unpicked"; // 로그인되지 않은 상태에서 예외 처리
        }

        int mno = loggedInMember.getMno();
        boolean isPicked = pickService.isPicked(mno, sno);
        return isPicked ? "picked" : "unpicked";
    }

    /*@PostMapping("/removePick")
    @ResponseBody
    public String removePick(@RequestParam("pno") int pno) {
        try {
            pickService.removePickByPno(pno);
            return "success";
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/pickList";
    }*/

    @PostMapping("/removePick")
    @ResponseBody
    public String removePick(@RequestParam("snos") List<Integer> snos) {
        for (int sno : snos) {
            pickService.removePicksBySno(sno);
        }
        return "success";
    }

    @PostMapping("/createFolder")
    public String createFolder(@RequestParam("pfname") String pfname, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");


        Pfolder pfolder = new Pfolder();
        pfolder.setPfname(pfname);
        pfolder.setMember(loggedInMember);

        pfolderService.savePfolder(pfolder);

        return "redirect:/pickList";
    }

    @PostMapping("/deleteFolder")
    public String deleteFolder(@RequestParam(value = "selectedFolders", required = false) List<Integer> selectedFolders, HttpSession session) {
        if (selectedFolders != null && !selectedFolders.isEmpty()) {
            for (Integer pfno : selectedFolders) {
                pfolderService.deletePfolderByPfno(pfno);
            }
        }
        return "redirect:/pickList";
    }

    @PostMapping("/updateFolderName")
    @ResponseBody
    public String updateFolderName(@RequestParam("pfno") Integer pfno, @RequestParam("pfname") String pfname, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        Pfolder pfolder = pfolderService.findByPfno(pfno);

        pfolder.setPfname(pfname);
        pfolderService.savePfolder(pfolder);

        return "success";
    }

    @PostMapping("/addPickToFolder")
    @ResponseBody
    public String addPickToFolder(@RequestParam("pfnos") String pfnos, @RequestParam("snos") String snos, HttpSession httpSession) {
        Member loggedInMember = (Member) httpSession.getAttribute("loggedInMember");

        try {
            List<Pfolder> pfolders = pfolderService.findPfoldersByPfnos(pfnos);
            List<Store> stores = storeService.findStoresBySnos(snos);

            for (Pfolder pfolder : pfolders) {
                if (!pfolder.getMember().equals(loggedInMember)) {
                    System.out.println("사용자와 폴더 소유자 불일치: " + pfolder);
                    System.out.println("LoggedIn Member: " + loggedInMember.getMid());
                    System.out.println("Pfolder Member: " + pfolder.getMember().getMid());
                    continue;
                }

                for (Store store : stores) {
                    Pick newPick = new Pick();
                    newPick.setPfolder(pfolder);
                    newPick.setStore(store);
                    newPick.setMember(loggedInMember);

                    pickService.savePick(newPick);
                }
            }
        } catch (Exception e) {
            return "error";
        }
        return "success";
    }

    @GetMapping("/getFolderContent")
    @ResponseBody
    public List<Pick> getFolderContent(@RequestParam("pfno") Integer pfno, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        Pfolder pfolder = pfolderService.findByPfno(pfno);

        List<Pick> picks = pickService.getPicksByPfolder(pfolder);
        if (picks  == null || picks.isEmpty()) {
            return Collections.emptyList();  // 비어있는 리스트 반환
        }
        for (Pick pick : picks) {
            pick.getPfolder().getMember().setMemberGroupList(null);
        }

        return picks;
        /*if (picks == null || picks.isEmpty()) {
            return "폴더가 비어있습니다.";
        }

        StringBuilder contentHtml = new StringBuilder("<ul>");
        for (Pick pick : picks) {
            contentHtml.append("<li>").append(pick.getStore().getSname()).append("</li>");
            System.out.println("contentHtml= " + contentHtml);
        }
        contentHtml.append("</ul>");
        return contentHtml.toString();*/
    }
}
