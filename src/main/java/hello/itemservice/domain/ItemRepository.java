package hello.itemservice.domain;

import hello.itemservice.domain.item.Item;

import java.util.List;

public interface ItemRepository {

    Item save(Item item);

    Item findById(long id);

    List<Item> findAll();

    void update(long itemId, Item itemParam);

    void clear();

    void delete(long itemId);
}
