package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository //spring. 컴포넌트 스캔의 대상이 됨
public class ItemRepository {

    //static임에 주의. 객체가 따로 생성되면 분리된다. 싱글톤이면 괜찮지만 주의해야 할점.
    //실무에서 여러 스레드 동시에 접근할 땐 HashMap 쓰면 안됨. 사용하고 싶으면 ConcurrentHashMap 써야함
    private static final Map<Long, Item> store = new HashMap<>(); //static
    //long도 마찬가지. 동시에 접근하면 꼬인다. AtomicLong등 이용해야함
    private static long sequence = 0L; //static

    public Item save(Item item) {
        item.setId(++sequence); //sequence에 1더해진 후 값 입력됨.
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    //store.values 바로 줘도 되는데 한 번 감싸서 주는 이유:
    //감싸서 반환하면 실제 스토어에 변동을 주지 않도록 store를 보호 할 수 있음.
    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    //정석대로 하려면 updatePramDto 처럼 필요한 파라미터만 갖는 객체 쓰는게 맞다.
    //도메인 객체를 그대로 쓰다보니 setId 처럼 도메인 모델중 안쓰는데 접근 가능한 부분이 생긴다.
    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setName(updateParam.getName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();//test 용
    }

}
