package hello.itemservice.domain.item;


import hello.itemservice.domain.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemMemoryRepository implements ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>();//실제 만들 때는 hashmap 쓰면 안된다. concurrenthashmap쓰기
    private static long sequence = 0;//얘도 원래는 automic long을 사용한다.ㄴ

    public Item save(Item item){
        Set<Long> longs = store.keySet();
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(long itemId, Item itemParam){
        Item findItem = findById(itemId);
        findItem.setItemName(itemParam.getItemName());
        findItem.setPrice(itemParam.getPrice());
        findItem.setQuantity(itemParam.getQuantity());

    }

    public void clear(){
        store.clear();
    }

    public void delete(long itemId){
        Item item = findById(itemId);
        store.remove(itemId);
    }
}
