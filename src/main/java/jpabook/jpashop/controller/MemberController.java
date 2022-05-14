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
}
