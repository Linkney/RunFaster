import java.util.ArrayList;

/**
 * Ai Player 电脑玩家
 * 所谓继承就是把共有的数据项和行为抽取到父类中，这样所有子类都会自动具备，从而达到复用性。
 * 无论父类中的成员变量是pirvate、public还是其它类型的，子类都会拥有（继承）父类中的这些成员变量。
 * 但是父类中的私有成员变量，无法在子类中直接访问，可以通过从父类中继承得到的protected、public方法（如getter、setter方法）来访问。
 * 更好的提现了JAVA特性中的封装，而且符合软件工程的设计思想：低耦合。
 */
public class Ai_v1 extends Player
{
    // private Card card = new Card();      // 子类已经继承了父类数据域 但是不能 通过 super或者this调用该数据

    public Ai_v1() {}

    public void aiBB(String bb)
    {
        System.out.println("Ai: " + bb);
    }


    // 当上家出 pass 时, Ai 的抉择 todo 出最后一张单牌
    private String upPassBehavior()
    {
        return super.getAndRemoveLastCard_ai();
    }

    // 当上家正常出牌时, Ai 的抉择 找 表行中的右侧是否存在 或者是否炸
    private ArrayList<String> up2to11Behavior(int[] upCardsInfo)
    {
        ArrayList<String> aiChooseCards = new ArrayList<>();
        // todo... 很特么的复杂 还得调用父类方法才能 操纵数据域
            // 单张 todo 直接特么地给你压死
        if (upCardsInfo[0] == 2)
        {
            // A 能压死就出 A
            if (super.checkIFHasThisCard("A") && upCardsInfo[1] < 12)
            {
                super.removeCard("A");
                aiChooseCards.add("A");
                return aiChooseCards;
            }
            else if (super.checkIFHasThisCard("2") && upCardsInfo[1] < 13)
            {
                this.aiBB("压死！");
                super.removeCard("2");
                aiChooseCards.add("2");
                return aiChooseCards;
            }
            else
            {
                this.aiBB("你咋这么猛呢！");
                aiChooseCards.add("pass");
                return aiChooseCards;
            }
        }
            // todo 3.4.5..... 类型牌型的 ai 回应




        return aiChooseCards;
    }


    // 自动出牌 Override Player.play()
    public String[] autoPlay(String[] upCards)
    {
        // ai 准备出的牌
        ArrayList<String> aiSendCards = new ArrayList<>();

        System.out.println("Ai_v1 auto playing...");
        // 1.分析上家手牌类型 （解码）
        int[] upCardsInfo = Poker.checkPokerInfo(upCards);

        System.out.println("上家手牌类型：" + upCardsInfo[0]);
        System.out.println("上家手牌分数：" + upCardsInfo[1]);

        // 2.遍历手牌是否有 在 表右侧的
            // 2.1 pass
        if (upCardsInfo[0] == 1)
        {
            aiSendCards.add(this.upPassBehavior());
        }
            // 2.2 其他类型 2~11+
        else
        {
            ArrayList<String> temp = this.up2to11Behavior(upCardsInfo);
            for (String item:temp)
            {
                aiSendCards.add(item);
            }
        }

        // 3.整理函数输出  （编码）
        String[] re = aiSendCards.toArray(new String[aiSendCards.size()]);
        return re;
    }


    @Override
    public String[] play(String[] upCards) {
        return super.play(upCards);
    }


    public static void main(String[] args) {
        Ai_v1 ai = new Ai_v1();
        ai.getCard("A");
        ai.getCard("A");
        ai.getCard("3");
        ai.getCard("2");
        ai.sortCard();
        ai.showCard();

        String[] upCards = {"4"};
        System.out.println("上家出的牌：" + upCards[0]);

        String[] get = ai.autoPlay(upCards);

        ai.showCard();

        System.out.println("Ai 出的牌：");
        for (String item:get)
        {
            System.out.print(item);
        }
        System.out.println();
    }
}

