package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//Component 아님. 컴포넌트 스캔의 대상이 아니다.
@Data //@Data는 위험하다. 다 만들어주는데 예측할 수 없는 움직임을 하게하는 위험이 있음.
//@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    // Integer를 쓰는 이유: 값이 안들어 갈 수 있기때문. null을 넣어야 할지도 모른다. null은 0과 다르다
    private Integer price;
    private Integer quantity;

    //기본 생성자
    public Item() {
    }

    //id를 제외한 생성자
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
