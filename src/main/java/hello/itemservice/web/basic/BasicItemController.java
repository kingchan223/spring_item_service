package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemMemoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemMemoryRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);

        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")/*Model model -> @ModelAttribute가 model 지정한 이름("item")으로 addAttribute해준다.
//    그래서 model.addAttribute("item", item)안해도 된다.*/
    public String addItemV2(@ModelAttribute("item") Item item, Model model){
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);     ==   @ModelAttribute("item") Item item
        itemRepository.save(item);
//        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    /* ("item")를 지우면 클래스명의 첫글자를 소문자로 바꾼 item으로 객체를 만들고, item을 model에 addAttribute한다.  */
    public String addItemV3(@ModelAttribute Item item, Model model){
        itemRepository.save(item);
        return "basic/item";
    }

    //    @PostMapping("/add")
    /* @ModelAttribute를 생략할 수 있다.  */
    public String addItemV4(Item item, Model model){
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, Model model){
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(@ModelAttribute Item item, Model model, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);//남는 애들은 쿼리스트링으로 들어감

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/delete")
    public String deleteOne(@PathVariable long itemId, RedirectAttributes redirectAttributes, Model model){
        Item deletedItem = itemRepository.findById(itemId);
        itemRepository.delete(itemId);
        redirectAttributes.addAttribute("status", true);
//        model.addAttribute("dItem", deletedItem);
        return "redirect:/basic/items";
    }

    @GetMapping("/delete")
    public String deleteAll(){
        itemRepository.clear();
        return "/basic/items";
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
