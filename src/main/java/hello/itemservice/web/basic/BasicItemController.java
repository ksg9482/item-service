package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //파이널 붙은 걸로 생성자 만들어줌
public class BasicItemController {

    private final ItemRepository itemRepository;

    //생성자:
    //생성자가 하나면 @AutoWired 생략 가능
    //RequiredArgsConstructor 쓰면 final 붙은 arg로 생성자 만들어준다

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();

        //모델 addAttribute에 담긴게 넘어감
        model.addAttribute("items", items);

        return "basic/items"; //이 위치에 뷰 만든다
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable() Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model
                       ) {
        Item item = new Item();
        //비교용으로 set 사용함.
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

    //@ModelAttribute 이용
    //@ModelAttribute 이용하면 생략할 수 있는게 많아짐.
//    @PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item //Item의 property로 만들어준다
            //Model model  //모델도 생략가능.
    ) {
        //객체 만들고 set으로 넣는 것과 같다.
//        Item item = new Item();
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);

        itemRepository.save(item);

        //model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        //basic/item 뷰를 넘겨주는 게 아니라 다른 URL로 이동시킴.
        return "redirect:/basic/items/" + item.getId(); //URL에 변수 넣는 건 위험함. 숫자가 아니라 한글 띄어쓰기가 포함되면 URL인코딩이 안됨.
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        //name 같으면 들어감. 남는건 query로 들어감
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        //spring에서 리다이렉트 하는법
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct //의존성 주입이 이루어진 후 초기화를 수행
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
