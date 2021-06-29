package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        model.addAttribute("item",itemRepository.findById(itemId));

        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, Model model){
        model.addAttribute("item",itemRepository.findById(itemId));
        return "basic/editForm";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    @PostMapping("/add")
    public String save(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        return "basic/items";
    }

//    @Postapping("/items/{itemId}/edit")
//    public String edit(@PathVariable long itemId, Model model){
//        model.addAttribute("item",itemRepository.findById(itemId));
//        return "basic/editForm";
//    }

    /*
    * 테스트용 데이터 추가
    * */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 12000, 20));
    }
}
