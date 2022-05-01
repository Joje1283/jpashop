package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded  // 내장 타입을 매핑
    private Address address;

    @OneToMany(mappedBy = "member")  // 연관관계의 주인이 아닌 거울. 상대 엔티티의 필드를 mappedBy 파라미터로 입력한다.
    private List<Order> orders = new ArrayList<>();
}
