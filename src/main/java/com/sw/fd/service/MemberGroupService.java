package com.sw.fd.service;

import com.sw.fd.dto.GroupDTO;
import com.sw.fd.dto.MemberGroupDTO;
import com.sw.fd.entity.Group;
import com.sw.fd.entity.Member;
import com.sw.fd.entity.MemberGroup;
import com.sw.fd.repository.GroupRepository;
import com.sw.fd.repository.MemberGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MemberGroupService {

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    public boolean isMemberInGroup(String memberId, int gno) {
        return memberGroupRepository.existsByGroupGnoAndMemberMid(gno, memberId);
    }

    public void addMemberToGroup(Member member, Group group, int jauth) {
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setGroup(group);
        memberGroup.setMember(member);
        memberGroup.setJauth(jauth);

        memberGroupRepository.save(memberGroup);
    }

    // 회원이 모임장인 모임을 찾는 메서드 추가
    public List<Group> findGroupsWhereMemberIsLeader(String memberId) {
        List<MemberGroup> memberGroups = memberGroupRepository.findByMemberMidAndJauth(memberId, 1);
        List<Group> groups = new ArrayList<>();

        for (MemberGroup memberGroup : memberGroups) {
            groups.add(memberGroup.getGroup());
        }

        return groups;
    }

    // 특정 그룹(gno)의 모든 회원 목록을 조회하는 메서드
    public List<MemberGroup> findMembersByGroupGno(Integer gno) {
        return memberGroupRepository.findByGroupGnoIn(List.of(gno));
    }

    public MemberGroup getMemberGroupByGroupGnoAndMemberMid(int gno, String mid) {
        return memberGroupRepository.findByGroupGnoAndMemberMid(gno, mid);
    }

    public void removeMemberGroup(MemberGroup memberGroup) {
        memberGroupRepository.delete(memberGroup);
    }

 /*   public List<MemberGroup> getMemberGroupsWithGroup(Member member) {
        return memberGroupRepository.findByMember(member);
    } */

    public List<MemberGroupDTO> getMemberGroupsWithGroup(Member member) {
        List<MemberGroup> memberGroups = memberGroupRepository.findByMember(member);
        List<MemberGroupDTO> memberGroupDTOs = new ArrayList<>();

        for (MemberGroup memberGroup : memberGroups) {
            Group group = memberGroup.getGroup();
            if (group != null) {
                GroupDTO groupDTO = new GroupDTO(group.getGno(), group.getGname(), group.getGdate());
                MemberGroupDTO memberGroupDTO = new MemberGroupDTO(
                        memberGroup.getJno(),
                        groupDTO,
                        memberGroup.getMember().getMnick(),
                        memberGroup.getJauth(),
                        memberGroup.getJdate()
                );
                memberGroupDTOs.add(memberGroupDTO);
            }
        }

        return memberGroupDTOs;
    }

    public List<MemberGroupDTO> findMembersByGroupGnoWithDTO(int gno) {
        List<MemberGroup> memberGroups = memberGroupRepository.findByGroupGno(gno);

        // Convert to MemberGroupDTO
        return memberGroups.stream().map(mg -> {
            GroupDTO groupDTO = groupService.getGroupById(mg.getGroup().getGno());
            return new MemberGroupDTO(
                    mg.getJno(),
                    groupDTO,
                    mg.getMember().getMnick(),
                    mg.getJauth(),
                    mg.getJdate()
            );
        }).collect(Collectors.toList());
    }

    public MemberGroup findMemberGroupByGroupGnoAndNick(int gno, String nick) {
        return memberGroupRepository.findByGroupGnoAndMemberNick(gno, nick);
    }

    public void updateMemberGroupJauth(int gno, String mid, int newJauth) {
        MemberGroup memberGroup = memberGroupRepository.findByGroupGnoAndMemberMid(gno, mid);
        if (memberGroup != null) {
            memberGroup.setJauth(newJauth);
            memberGroupRepository.save(memberGroup);
        }
    }

    public void delegateGroupLeadership(Member leavingMember) {
        // 1. 탈퇴하는 회원이 모임장인 모임을 찾습니다.
        List<Group> groups = findGroupsWhereMemberIsLeader(leavingMember.getMid());

        for (Group group : groups) {
            // 2. 각 모임의 모든 회원을 조회합니다.
            List<MemberGroup> memberGroups = findMembersByGroupGno(group.getGno());
            List<Member> eligibleMembers = new ArrayList<>();

            // 3. 모임장 권한이 없는 일반회원들만 필터링
            for (MemberGroup memberGroup : memberGroups) {
                if (memberGroup.getJauth() == 0) { // 일반회원
                    eligibleMembers.add(memberGroup.getMember());
                }
            }

            if (!eligibleMembers.isEmpty()) {
                // 4. 무작위로 한 명의 일반회원 선택
                Random rand = new Random();
                Member newLeader = eligibleMembers.get(rand.nextInt(eligibleMembers.size()));

                // 5. 새 모임장 권한 부여
                updateMemberGroupJauth(group.getGno(), newLeader.getMid(), 1); // 1: 모임장 권한

                // 기존 모임장 권한 제거
                MemberGroup currentLeaderGroup = getMemberGroupByGroupGnoAndMemberMid(group.getGno(), leavingMember.getMid());
                if (currentLeaderGroup != null) {
                    removeMemberGroup(currentLeaderGroup);
                }
            }
        }
    }
}