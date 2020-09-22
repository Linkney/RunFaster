import java.util.ArrayList;
import java.util.Comparator;

/**
 * Player中的手牌
 */
public class Card
{
    private ArrayList<String> cards = new ArrayList<>();

    // 牌序整理
    public void sortCards()
    {

        Comparator c = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 搞一个list 下标对应顺序大小
                int i1 = -1;
                int i2 = -1;
                String[] cardList = Poker.CARDLIST;
                for (int index = 0; index < cardList.length; index++)
                {
                    if (cardList[index].equals(o1)) i1 = index;
                    if (cardList[index].equals(o2)) i2 = index;
                }
                if(i1<i2) return -1;
                else return 1;
            }
        };
        this.cards.sort(c);
    }

    // 发牌阶段 收牌
    public void addCard(String card)
    {
        this.cards.add(card);
    }

    // 出牌阶段 出牌
    public void removeCard(String card)
    {
        this.cards.remove(card);
    }

    // 展示手牌
    public void showCards()
    {
        System.out.print("手牌：");
        for (String item : this.cards)
        {
            System.out.print("[" + item + "]  ");
        }
        System.out.println();
    }

    // 返回牌的数量
    public int getCardsSize()
    {
        return this.cards.size();
    }

    // 检测出的牌是否在手牌池里 true:出牌行为成立
    public boolean checkSendCardsInHands(ArrayList<String> cards)
    {
        if (cards.get(0).equals("pass"))
        {
            return true;
        }

        // todo 这个 浅表拷贝  强制转型  感觉摇摇欲坠
        ArrayList<String> handsCopy = (ArrayList<String>) this.cards.clone();
        // 类型 + 数量 同时 check
        for (String card: cards)
        {
            // 手牌中 不存在 不能移除了
            if (!handsCopy.remove(card)) return false;
        }

        return true;
    }


    public static void main(String[] args) {
        Card card = new Card();
        card.addCard("A");
        card.addCard("A");
        card.addCard("3");

        ArrayList<String> check = new ArrayList<>();
        check.add("A");
        check.add("A");
        check.add("A");
        card.checkSendCardsInHands(check);

    }
}
