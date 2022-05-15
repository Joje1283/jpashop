package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        // 엔티티가 아닌 폼을 넣는 이유:
        // 1. 엔티티와 폼은 일치하지 않는다.
        // 2. 도메인의 벨리데이션과 화면에서 넘어올 때 벨리베이션은 다를 수 있기에, 깔끔하게 화면 전용의 폼을 만드는게 좋다.
        // 3. 억지로 맞추다보면 엔티티가 조잡해질 수 있다.
        // 엔티티는 핵심 비즈니스 로직만 갖는다. 화면을 위한 로직은 없다. (엔티티는 최대한 순수하게 유지한다.)
        // 화면에 맞는 API 등은 폼 객체나 DTO를 사용하면 된다.
        if (result.hasErrors()) {  // BindingResult 객체를 받으면, 이와같이 오류가 있을 경우에 로직을 분기할 수 있다.
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        // 지금처럼 엔티티를 그대로 쓰기보다 화면에 꼭 필요한 데이터만을 뽑아서 DTO로 사용하기를 권장
        // 다만 API를 만들 때는 엔티티를 반환하면 안된다. 엔티티에 필드 추가 시 API 스펙이 변경되기 때문
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
