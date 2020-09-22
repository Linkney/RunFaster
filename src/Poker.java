import java.util.ArrayList;
import java.util.Random;

/**
 * 整幅扑克
 */
public class Poker
{
    public ArrayList<String> poker = new ArrayList<>();

    // 一副扑克牌
    static String[] POKER = {"A", "A", "A", "A", "2", "2", "2", "2",
                             "3", "3", "3", "3", "4", "4", "4", "4",
                             "5", "5", "5", "5", "6", "6", "6", "6",
                             "7", "7", "7", "7", "8", "8", "8", "8",
                             "9", "9", "9", "9", "10", "10", "10", "10",
                             "J", "J", "J", "J", "Q", "Q", "Q", "Q",
                             "K", "K", "K", "K", "#", "$"};
    // 手牌摆放顺序
    static String[] CARDLIST = {"$", "#", "2", "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3"};
    // 牌的大小顺序
    static String[] CARDSCORELIST = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2", "#", "$"};

    // 默认构造函数
    public Poker() {
        for (String item : Poker.POKER)
        {
            this.poker.add(item);
        }
    }

    // 比较 2个 牌 的 大小 1 cards1大  2 cards2 大  0 无法比较
    public static int checkBigger(String[] cards1, String[] cards2)
    {
        // cardInfo[牌型类别, 牌型分数] 1.牌型聚类  2.同形大小比较
        int[] cardInfo1 = Poker.checkPokerInfo(cards1);
        int[] cardInfo2 = Poker.checkPokerInfo(cards2);

        if (cardInfo1[0] != cardInfo2[0])
        {
            // 牌型不一致时 只有 pass 王炸 炸弹 可以通 1 3 6    王炸 max 炸弹 max PASS min
                // Error：牌型不匹配
            if (cardInfo1[0] != 1 && cardInfo1[0] != 3 && cardInfo1[0] != 6 &&
                    cardInfo2[0] != 1 && cardInfo2[0] != 3 && cardInfo2[0] != 6) return 0;
            // 两人玩的时候 只能有一个人 pass 另一个人就必须出牌
            if (cardInfo1[0] == 1) return 2;
            if (cardInfo2[0] == 1) return 1;
            if (cardInfo1[0] == 3) return 1;
            if (cardInfo2[0] == 3) return 2;
            if (cardInfo1[0] == 6) return 1;
            if (cardInfo2[0] == 6) return 2;

        }
        else
        {
            // 牌型一致 行内分数大小比较   不可能情况：2 PASS 2 王炸
            if (cardInfo1[1] > cardInfo2[1]) return 1;
            else return 2;
        }

        // Error 程序出口
        System.out.println("Error: checkBigger");
        return -1;
    }

    /**
     * 牌型聚类
     * -1. not impletement
     * 0. others 牌型不正确
     * 1. ["pass"]
     * 2. [单张]
     * 3. ["#", "$"]
     * 4. [对子, 对子]
     * 5. [三张, 三张, 三张]
     * 6. [炸弹, 炸弹, 炸弹, 炸弹]
     * 7. [三带一对, 三带一对, 三带一对, 三带一对, 三带一对]
     * 8. [4带2张, 4带2张, 4带2张, 4带2张, 4带2张, 4带2张] 333369
     * 9. [三连对, 三连对, 三连对, 三连对, 三连对, 三连对] 334455
     * _10. 飞机 todo 飞机就算了 规则都没搞清楚
     * _11+. _[顺子, 顺子, 顺子, 顺子, 顺子, _顺子] todo 不同长度的顺子 不是同一类  5 6 7 8 9...长度
     *
     * Idea: 巨大的 Lookup Table 行：牌型类 列：大小顺序
     */
    public static int[] checkPokerInfo(String[] cards)
    {
        int[] typeScore = {-1, -1};
        if (cards.length == 1)
        {
            // 1, 2 pass, 单张
            if (cards[0].equals("pass"))
            {
                typeScore[0] = 1;
                typeScore[1] = 0;
                return typeScore;
            }
            else
            {
                typeScore[0] = 2;
                typeScore[1] = findScore(cards[0]);
                return typeScore;
            }
        }
        if (cards.length == 2)
        {
            // 3, 4, 0 王炸, 对子
            if (cards[0].equals(cards[1]))
            {
                typeScore[0] = 4;
                typeScore[1] = findScore(cards[0]);
                return typeScore;
            }
            else if ((cards[0].equals("#") && cards[1].equals("$")) || (cards[1].equals("#") && cards[0].equals("$")))
            {
                typeScore[0] = 3;
                typeScore[1] = 100; //王炸在炸弹堆里的分数
                return typeScore;
            }
            else
            {
                typeScore[0] = 0;
                return typeScore;
            }
        }
        if (cards.length == 3)
        {
            // 5, 0
            if (cards[0].equals(cards[1]) && cards[1].equals(cards[2]))
            {
                typeScore[0] = 5;
                typeScore[1] = findScore(cards[0]);
                return typeScore;
            }
            else
            {
                typeScore[0] = 0;
                return typeScore;
            }
        }
        if (cards.length == 4)
        {
            // 6, 7, 0
            if (cards[0].equals(cards[1]) && cards[1].equals(cards[2]) && cards[2].equals(cards[3]))
            {
                typeScore[0] = 6;
                typeScore[1] = findScore(cards[0]);
                return typeScore;
            }
            // 3同1异 7  todo 输入容错处理 必须 xxxy 不能 xxyx yxxx ....
            else if (cards[0].equals(cards[1]) && cards[1].equals(2))
            {
                typeScore[0] = 7;
                typeScore[1] = findScore(cards[0]);
                return typeScore;
            }
            else
            {
                typeScore[0] = 0;
                return typeScore;
            }
        }
        if (cards.length == 5)
        {
            // 11 -- 5长度顺子 ,  0 todo 输入容错处理 34567 不能 45637...
            int[] getinfo = Poker.check34567(cards);
            if (getinfo[0] == 11)
            {
                typeScore[0] = getinfo[0];      // 11
                typeScore[1] = getinfo[1];      // score
                return typeScore;
            }
            else
            {
                System.out.println("getinfo[0]只能是-2吧："+getinfo[0]);
                typeScore[0] = 0;
                return typeScore;
            }
        }
        if (cards.length == 6)
        {
            int[] getinfo = check34567(cards);
            // 8 四带二 , 9 三连对 , 12 六长度顺子 , 0
            if (cards[0].equals(cards[1]) && cards[1].equals(cards[2]) && cards[2].equals(cards[3]))
            {
                typeScore[0] = 8;
                typeScore[1] = findScore(cards[0]);
                return typeScore;
            }
            else if (cards[0].equals(cards[1]) && cards[2].equals(cards[3]) && cards[4].equals(cards[5]) &&
                    findScore(cards[0])+1 == findScore(cards[2]) && findScore(cards[2])+1 == findScore(cards[4]))
            {
                typeScore[0] = 9;
                typeScore[1] = findScore(cards[0]);
                return typeScore;
            }
            else if (getinfo[0] == 12)
            {
                typeScore[0] = getinfo[0];
                typeScore[1] = getinfo[1];
                return typeScore;
            }
            else
            {
                typeScore[0] = 0;
                return typeScore;
            }
        }
        if (cards.length >= 7)
        {
            // 13+ 七+长度顺子 , 0
            int[] getinfo = check34567(cards);
            if (getinfo[0] == -2)
            {
                typeScore[0] = 0;
                return typeScore;
            }
            else
            {
                typeScore[0] = getinfo[0];
                typeScore[1] = getinfo[1];
                return typeScore;
            }
        }
        // todo 其他复杂牌型
        System.out.println("Error：复杂牌型代码出口");
        return typeScore;
    }

    private static int findScore(String card)
    {
        for (int i = 0; i < Poker.CARDSCORELIST.length; i++)
        {
            if (Poker.CARDSCORELIST[i].equals(card)) return (i+1);
        }
        System.out.println("Error：牌符号溢出错误");
        return -1;
    }

    // 判断输入输入是否是 顺子  返回[0] 类型 [1] 11+---5+  分数 minNumber   类型 -2 表示非顺子
    public static int[] check34567(String[] cards)
    {
        int[] typeScore = {-9, -9};
        for (int i = 0; i < cards.length-1; i++)
        {
            if (findScore(cards[i]) + 1 != findScore(cards[i+1]))
            {
                // 前后不连贯 那么
                typeScore[0] = -2;
                return typeScore;
            }
        }
        typeScore[0] = cards.length + 6;
        typeScore[1] = findScore(cards[0]);
        return typeScore;
    }


    public void showPoker()
    {
        System.out.print("Poker List:");
        for (String item : this.poker)
        {
            System.out.print('[' + item + "]  ");
        }
        System.out.println();
    }

    // 如果发完牌了 返回 true
    public boolean checkIfisOver()
    {
        if (this.poker.size() == 0) return true;
        else return false;
    }


    // 随机发牌
    public String getRandomCardFormPoker()
    {
        int resCardNum = this.poker.size();
        if (resCardNum == 0)
        {
            System.out.println("发牌完毕！");
            return null;
        }

        Random random = new Random();
        int getIndex = random.nextInt(resCardNum);
        String getCard = this.poker.get(getIndex);
        this.poker.remove(getIndex);

        System.out.println("牌堆发牌:" + getCard);

        return getCard;
    }



    //Test 发牌 比牌大小
    public static void main(String[] args) {
        String[] cards1 = {"3", "4", "5", "6", "7", "8"};
        String[] cards2 = {"3", "3", "3", "3"};
        int ans = Poker.checkBigger(cards1, cards2);
        System.out.println("较大者"+ans);
        System.out.println("------------------");
        cards2 = new String[]{"pass"};
        ans = Poker.checkBigger(cards1, cards2);
        System.out.println("较大者"+ans);
        System.out.println("------------------");
        cards2 = new String[]{"3"};
        ans = Poker.checkBigger(cards1, cards2);
        System.out.println("较大者"+ans);
        System.out.println("------------------");
        cards2 = new String[]{"4", "5", "6", "7", "8", "9"};
        ans = Poker.checkBigger(cards1, cards2);
        System.out.println("较大者"+ans);
        System.out.println("------------------");

    }

}
