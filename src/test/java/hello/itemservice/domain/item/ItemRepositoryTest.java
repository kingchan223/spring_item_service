package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

//    public Item save(Item item){
//        Set<Long> longs = store.keySet();
//        item.setId(++sequence);
//        store.put(item.getId(), item);
//        return item;
//    }
//
//    public Item findById(long id){
//        return store.get(id);
//    }
//
//    public List<Item> findAll(){
//        return new ArrayList<>(store.values());
//    }
//
//    public void update(long itemId, Item itemParam){
//        Item findItem = findById(itemId);
//        findItem.setItemName(itemParam.getItemName());
//        findItem.setPrice(itemParam.getPrice());
//        findItem.setQuantity(itemParam.getQuantity());
//
//    }
//
//    public void clear(){
//        store.clear();
//    }

    @AfterEach
    void afterEach(){
        itemRepository.clear();
    }


    @Test
    @DisplayName("아이템 추가 저장테스트")
    void saveTest(){
        //given
        Item itemA = new Item("두부", 1000, 100);
        //when
        itemRepository.save(itemA);
        //then
        assertThat(itemA).isSameAs(itemRepository.findById(itemA.getId()));
    }

    @Test
    @DisplayName("아이템 조회")
    void findByIdTest(){
        //given
        Item itemA = new Item( "두부", 1000, 100);
        //when
        itemRepository.save(itemA);
        //then
        assertThat(itemA).isSameAs(itemRepository.findById(itemA.getId()));
    }

    @Test
    @DisplayName("모든 아이템 조회")
    void findAllTest(){
        //given
        Item itemA = new Item( "두부", 1000, 100);
        Item itemB = new Item( "양파", 1200, 120);

        //when
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //then
        assertThat(itemRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("아이템 수정")
    void updateTest(){
        //given
        Item itemA = new Item("두부", 1000, 100);
        itemRepository.save(itemA);
        Item itemParam = new Item();
        itemParam.setItemName("건두부");
        itemParam.setPrice(1300);
        itemParam.setQuantity(110);

        //when
        itemRepository.update(itemA.getId(), itemParam);

        //then
        assertThat(itemRepository.findById(itemA.getId()).getItemName()).isEqualTo("건두부");
        assertThat(itemRepository.findById(itemA.getId())).isSameAs(itemParam);

    }

    @Test
    @DisplayName("아이템 전체 삭제")
    void clearTest(){
        //given
        Item itemA = new Item( "두부", 1000, 100);
        Item itemB = new Item( "양파", 1200, 120);
        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        itemRepository.clear();

        //then
        assertThat(itemRepository.findAll().size()).isEqualTo(0);
    }

}
