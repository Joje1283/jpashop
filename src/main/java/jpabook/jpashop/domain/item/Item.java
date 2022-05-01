package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    // 도메인 주도 설계에서는 엔티티 자체가 해결할 수 있다면, 엔티티에 비지니스 로직을 위치시킨다.
    // 다시말해 데이터가 있는 쪽에서 로직도 같이 가지고 있는게 좋다.
    // 이로인해 설계적으로 응집도를 높일 수 있다.
    /**
    * stock 증가
     */
    public void addStock(int quantity) {
        // 재고를 늘린다
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        // 재고를 줄인다
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {  // 재고가 0보다 작으면 안된다.
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
