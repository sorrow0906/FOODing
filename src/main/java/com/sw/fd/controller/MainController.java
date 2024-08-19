package com.sw.fd.controller;

import com.sw.fd.dto.MemberGroupDTO;
import com.sw.fd.entity.Member;
import com.sw.fd.entity.MemberGroup;
import com.sw.fd.entity.Store;
import com.sw.fd.service.MemberGroupService;
import com.sw.fd.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private MemberGroupService memberGroupService;
    @Autowired
    private StoreService storeService;

    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        List<MemberGroupDTO> myMemberGroups = new ArrayList<>();
        Map<Integer, String> leaderList = new HashMap<>();
        Map<Integer, String> allMemberList = new HashMap<>();

        if (loggedInMember != null) {
            myMemberGroups = memberGroupService.getMemberGroupsWithGroup(loggedInMember);

            for (MemberGroupDTO memberGroup : myMemberGroups) {
                int thisGno = memberGroup.getGroup().getGno();
                // 해당 gno 그룹의 모든 맴버 닉네임을 한줄의 String으로 만들어서 gno와 함께 Map화 (key= gno, value= 모임방의 모든 맴버 닉네임)
                allMemberList.put(thisGno, memberGroupService.findMnicksByGroupGno(thisGno));
                // 해당 gno 그룹의 모임장을 찾아서 모임장의 닉네임을 gno와 함께 Map화 (key= gno, value= 모임장 닉네임)
                leaderList.put(thisGno, memberGroupService.getLeaderByGno(thisGno).getMember().getMnick());
            }
        }

        List<Store> stores = storeService.getAllStoresWithRank();
        stores.sort(Comparator.comparingDouble(Store::getScoreArg).reversed());
        List<Store> rankedStores = stores.subList(0, 5);

        model.addAttribute("myMemberGroups", myMemberGroups);
        model.addAttribute("allMemberList", allMemberList);
        model.addAttribute("leaderList", leaderList);
        model.addAttribute("stores", rankedStores);


        return "main";
    }
}
