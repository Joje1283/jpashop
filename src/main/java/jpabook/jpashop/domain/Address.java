package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable  // JPA의 내장 타입을 의미
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
        // JPA 기본 스펙이 JPA가 생성할 때 리플렉션 등의 기술을 써야하는데, 기본 생성자가 없으면 문제가 발생한다.
        // public은 모두가 접근할 수 있으니, JPA만 접근이 가능할것이라는 가정으로 protected로 작성한다. (JPA는 protected부터 접근 가능)
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
