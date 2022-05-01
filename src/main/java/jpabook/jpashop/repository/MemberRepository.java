package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Member member) {
        // JPA를 통해 저장
        em.persist(member);
    }

    public Member findOne(Long id) {
        // JPA를 통해 멤버를 찾아서 반환 (단건 조회)
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // 멤버 목록을 조회
        // JPQL의 작성이 필요하다.
        // 파라미터 -> 첫번째: JPQL, 두번째: 반환 타입
        // JPQL: SQL과 문법/기능은 비슷하다. 차이점은 테이블이 아닌 엔티티 객체를 상대로 쿼리한다
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)  // name 파라미터 바인딩
                .setParameter("name", name)  // 바인딩한 name 파라미터 입력
                .getResultList();
    }
}
