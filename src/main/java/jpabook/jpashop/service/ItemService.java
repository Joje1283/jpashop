package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    // 상품 리포지토리를 단순히 위임만 하고 있기에, 개발이 단순하다.
    // 이런 경우에는 컨트롤러에서 직접 리포지토리를 사용할 수도 있다.
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {  // 파라미터가 많아서 고민이라면 UpdateItemDto를 활용해서 풀어낼 수도 있다.
        // 변경 감지에 의한 데이터 변경 방법
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        // 영속성 컨텍스트이기에, 더이상 save를 호출할 필요가 없다.
        // 추가로, 위에처럼 set을 여러번 호출하기보다, findItem.addStock 등과 같이 의미있는 메서드를 정의해서 호출하는것이 좋다.
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
