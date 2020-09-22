import java.util.ArrayList;
import java.util.Scanner;

/**
    玩家
 */
public class Player
{
    //手牌
    private Card card = new Card();

    public void getCard(String card)
    {
        this.card.addCard(card);
    }

    public void showCard()
    {
        this.card.showCards();
    }

    public void sortCard()
    {
        this.card.sortCards();
    }

    public void showUpCards(String[] upCards)
    {
        for (String item: upCards)
        {
            System.out.print(item);
        }
        System.out.println();
    }

    // 解析cmd窗口输入的str为牌     todo 输入的容错   输入必须和 sout 的大小写完全一致 且 必须完全连续输入
    public ArrayList<String> parseStrIn(String strIn)
    {
        ArrayList<String> ansString = new ArrayList<>();
        for(int index = 0; index < strIn.length(); index++)
        {
            // 10 检测器
            if (strIn.substring(index, index+1).equals("1"))
            {
                ansString.add("10");
                continue;
            }
            if (strIn.substring(index, index+1).equals("0")) continue;
            // pass 检测器
            if (strIn.substring(index, index+1).equals("p"))
            {
                ansString.add("pass");
                continue;
            }
            if (strIn.substring(index, index+1).equals("a")) continue;
            if (strIn.substring(index, index+1).equals("s")) continue;

            // 其余都是单字符
            ansString.add(strIn.substring(index, index+1));
        }
        return ansString;
    }

    // 从手牌中移除
    public void removeCards(ArrayList<String> cards)
    {
        for (String card: cards)
        {
            this.card.removeCard(card);
        }
    }

    public int getCardsSize()
    {
        return this.card.getCardsSize();
    }

    public String[] string2StringArray(String strIn)
    {
        ArrayList<String> temp = this.parseStrIn(strIn);
        String[] re = temp.toArray(new String[temp.size()]);
        return re;
    }

    // 差错检测  1.检测 this.card中是否有    2.能够匹配并超过上家 或 pass
    public boolean wrongCheck(String[] upCards, String uCardsStrIn)
    {

        // 1   如果出牌行为不成立的话 可以短路return 但是如果出牌行为成立 得 接下去判断大小
            // 但但是 如果 成立且 Pass 不用比较大小了
        if (!this.card.checkSendCardsInHands(this.parseStrIn(uCardsStrIn))) return true;

        // 2
        String[] uCard2Check = this.string2StringArray(uCardsStrIn);
        int bigger = Poker.checkBigger(upCards, uCard2Check);
        // 牌型不匹配
        if (bigger == 0) return true;
        // 出的牌没上家大
        if (bigger == 1)
        {
            // 唯二指定正确出口 出的是 Pass 比上家小
            if (this.parseStrIn(uCardsStrIn).get(0).equals("pass")) return false;
            // 我出的是 小牌
            else return true;
        }
        // 唯二指定正确出口 （非 pass 且 我出的牌 比 上家大 前面路都走过）
        if (bigger == 2) return false;

        // Error 程序出口
        System.out.println("Error: wrongCheck");
        return true;        // 有错误 得重输
    }



    // 行动： 出牌 或者 Pass    参数：  上家的牌 ["pass"]  或者 such as ["A", "A"]
    // 已知上家出的牌 现在你行动 返回你出的牌
    public String[] play(String[] upCards)
    {
        // 上家的牌
        System.out.print("上家的牌：");
        this.showUpCards(upCards);

        // 看一眼自己的牌
        System.out.print("你的手牌：");
        this.showCard();

        // cmd 打牌
        System.out.print("到你了,弟弟：");

        Scanner sc = new Scanner(System.in);

        String strIn = sc.next();  // next 遇到空格 nextLine 遇到回车
        // str --> ArrayList<String> --> String[]
        while (this.wrongCheck(upCards, strIn))
        {
            // 你这出的是个啥啊 重出
            System.out.println("你这出的是个锤子???别乱搞：");

            strIn = sc.next();
        }

        // 解析 cmd 字符串 并 移除 对应手牌
        ArrayList<String> temp = this.parseStrIn(strIn);
        this.removeCards(temp);

        String[] re = temp.toArray(new String[temp.size()]);

        return re;
    }


    public static void main(String[] args) {
        Player player1 = new Player();
        player1.getCard("A");
        player1.getCard("3");
        player1.getCard("3");
        player1.getCard("$");
        player1.sortCard();
        Player player2 = new Player();
        player2.getCard("2");
        player2.getCard("2");
        player2.getCard("4");
        player2.sortCard();

        String[] cards = {"pass"};
        cards = player1.play(cards);

        player1.showCard();

        for (String card :cards)
        {
            System.out.println(card);
        }
        System.out.println("-----------------------");

        cards = player2.play(cards);

        player2.showCard();
        for (String card :cards)
        {
            System.out.println(card);
        }
        System.out.println("-----------------------");
    }

}