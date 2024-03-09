package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/add")
    public String save() {
        return "basic/addForm";
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
