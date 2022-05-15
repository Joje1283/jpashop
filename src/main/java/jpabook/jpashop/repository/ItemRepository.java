package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);  // 생성
        } else {
            em.merge(item);  // 업데이트 역할을 함: itemId로 아이템을 찾고, 수정된 모든 값을 바꿔치기한다. 때문에 커밋되면 반영된다.
            // merge 보다는 변경감지 업데이트 사용을 권장한다.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
