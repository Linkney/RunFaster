/**
 * “跑得快”
 * 顶层设计没预先设计好 直接开始敲代码肯定是不太行的 又不是一个简单的算法题
 *      类和类之间的关系 类的功能得先设计了 还有主程序逻辑
 */
public class Game
{
    // private Player[] players;
    private Player player1 = new Player();
    private Player player2 = new Player();

    // 牌堆
    private Poker poker = new Poker();

    private int turnTime = 9999;

    // 游戏是否Running
    private boolean gameOver;

    // winner   player = 1 or 2
    private int winner = -1;

    public Game() {
        this.gameOver = false;
    }

    // 发牌
    public void sendCard()
    {
        while (!this.poker.checkIfisOver())
        {
            String randomCardFormPoker = this.poker.getRandomCardFormPoker();
            this.player1.getCard(randomCardFormPoker);
            player1.showCard();
            player2.showCard();
            randomCardFormPoker = this.poker.getRandomCardFormPoker();
            this.player2.getCard(randomCardFormPoker);
            player1.showCard();
            player2.showCard();
        }
        System.out.println("发牌完毕，检查Player手牌");
        player1.showCard();
        player2.showCard();
        System.out.println("整理手牌顺序...");
        player1.sortCard();
        player2.sortCard();
        System.out.println("手牌整理完毕！");
        player1.showCard();
        player2.showCard();
    }


    // 打牌
    public void playCard()
    {
        String[] cards = {"pass"};
        while (!this.gameOver)
        {
            cards = this.player1.play(cards);
            if (player1.getCardsSize() == 0)
            {
                this.gameOver = true;
                this.winner = 1;
                break;
            }
            cards = this.player2.play(cards);
            if (player2.getCardsSize() == 0)
            {
                this.gameOver = true;
                this.winner = 2;
                break;
            }
        }
    }


    // 如果人人都能遵循规则 世界上就不需要那么多差错检测 :)
    public void run()
    {
        System.out.println("跑得快");
        System.out.println("发牌阶段");
        this.sendCard();
        System.out.println("打牌阶段");
        this.playCard();
        System.out.println("游戏结束");
        System.out.println("Winner is Player" + this.winner);
    }



    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
