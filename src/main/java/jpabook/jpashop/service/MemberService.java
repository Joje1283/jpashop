package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// JPA의 모든 데이터 변경이나 로직은 Transaction 어노테이션 안에서 실행되어야 한다 (그래야 지연로딩과 같은 동작이 가능)
// readOnly=true를 하면, 조회 시 성능 최적화를 지원한다. (default: false)
// 쓰기가 필요한 메서드에는 별도로 어노테이션을 적용했다.
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor  // 기본 생성자를 만들어 준다 (테스트를 위해 필요)
public class MemberService {
    private final MemberRepository memberRepository;

    /**
    * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        // 영속성 컨텍스트의 id값과 테이블의 id값이 같은게 보장이 되기에, member의 id를 반환해도 된다.
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        // 이 케이스에서는 멀티쓰레딩 환경을 상정하면 여전히 문제가 발생할 수 있다.
        // 따라서, DB에서 다시한번 제약조건을 통해 방어하는게 좋다.
        List<Member> findMembers = memberRepository.findByName(member.getName());  // 같은 이름이 존재하는지 확인
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
