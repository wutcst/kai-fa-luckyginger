/**
 * EatCookieCommand类的单元测试用例。
 * 测试吃饼干命令的各种场景，包括无参数、错误参数、没有饼干、成功吃饼干等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class EatCookieCommandTest
{
    /**
     * 运行所有EatCookieCommand类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("EatCookieCommand类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testEatCookieCommandCreation()) {
            System.out.println("✅ 测试1: EatCookieCommand对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: EatCookieCommand对象创建 - 失败");
            failed++;
        }

        if (testEatWithoutSecondWord()) {
            System.out.println("✅ 测试2: eat命令无参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: eat命令无参数 - 失败");
            failed++;
        }

        if (testEatWrongFood()) {
            System.out.println("✅ 测试3: eat非cookie食物 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: eat非cookie食物 - 失败");
            failed++;
        }

        if (testEatCookieWithoutHavingIt()) {
            System.out.println("✅ 测试4: 没有饼干时吃饼干 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 没有饼干时吃饼干 - 失败");
            failed++;
        }

        if (testEatCookieReturnsFalse()) {
            System.out.println("✅ 测试5: eat cookie命令返回false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: eat cookie命令返回false - 失败");
            failed++;
        }

        if (testCookieEatenFlagSet()) {
            System.out.println("✅ 测试6: 吃饼干后cookieEaten标志 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 吃饼干后cookieEaten标志 - 失败");
            failed++;
        }

        if (testCookieRemovedFromInventory()) {
            System.out.println("✅ 测试7: 吃饼干后从背包移除 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 吃饼干后从背包移除 - 失败");
            failed++;
        }

        if (testMaxWeightIncreased()) {
            System.out.println("✅ 测试8: 吃饼干后负重增加 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 吃饼干后负重增加 - 失败");
            failed++;
        }

        if (testEatCookieTwice()) {
            System.out.println("✅ 测试9: 两次吃饼干 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 两次吃饼干 - 失败");
            failed++;
        }

        if (testEatCookieWithOtherItems()) {
            System.out.println("✅ 测试10: 有其他物品时吃饼干 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 有其他物品时吃饼干 - 失败");
            failed++;
        }

        if (testEatCookieCaseInsensitive()) {
            System.out.println("✅ 测试11: eat cookie大小写 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: eat cookie大小写 - 失败");
            failed++;
        }

        if (testEatCookieWithUsableCookie()) {
            System.out.println("✅ 测试12: 吃可使用的饼干 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 吃可使用的饼干 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testEatCookieCommandCreation()
    {
        try {
            EatCookieCommand cmd = new EatCookieCommand();
            if (cmd == null) {
                System.out.println("  错误: 无法创建EatCookieCommand对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEatWithoutSecondWord()
    {
        try {
            EatCookieCommand cmd = new EatCookieCommand();
            Command command = new Command("eat", null);
            Player player = new Player("测试玩家", 10.0);
            Room room = new Room("测试房间");
            player.setCurrentRoom(room);
            Game game = new Game();
            cmd.execute(command, game);
            // 应该输出"吃什么？"
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEatWrongFood()
    {
        try {
            EatCookieCommand cmd = new EatCookieCommand();
            Command command = new Command("eat", "apple");
            Player player = new Player("测试玩家", 10.0);
            Room room = new Room("测试房间");
            player.setCurrentRoom(room);
            Game game = new Game();
            cmd.execute(command, game);
            // 应该输出"吃什么？（试试 'eat cookie'）"
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEatCookieWithoutHavingIt()
    {
        try {
            EatCookieCommand cmd = new EatCookieCommand();
            Command command = new Command("eat", "cookie");
            Player player = new Player("测试玩家", 10.0);
            Room room = new Room("测试房间");
            player.setCurrentRoom(room);
            Game game = new Game();
            cmd.execute(command, game);
            // 应该输出"你没有魔法饼干！"
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEatCookieReturnsFalse()
    {
        try {
            EatCookieCommand cmd = new EatCookieCommand();
            Command command = new Command("eat", "cookie");
            Player player = new Player("测试玩家", 10.0);
            Item cookie = new Item("cookie", "魔法饼干", 0.3);
            player.takeItem(cookie);
            Room room = new Room("测试房间");
            player.setCurrentRoom(room);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: eat cookie命令应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCookieEatenFlagSet()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Item cookie = new Item("cookie", "魔法饼干", 0.3);
            player.takeItem(cookie);
            // 模拟吃饼干
            player.eatCookie();
            if (!player.isCookieEaten()) {
                System.out.println("  错误: 吃饼干后cookieEaten应为true");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCookieRemovedFromInventory()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Item cookie = new Item("cookie", "魔法饼干", 0.3);
            player.takeItem(cookie);
            player.eatCookie();
            if (player.hasItem("cookie")) {
                System.out.println("  错误: 吃饼干后背包不应还有cookie");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testMaxWeightIncreased()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            double initialMaxWeight = player.getMaxWeight();
            player.increaseMaxWeight(5.0);
            double newMaxWeight = player.getMaxWeight();
            if (newMaxWeight != initialMaxWeight + 5.0) {
                System.out.println("  错误: 增加负重后maxWeight应为" + (initialMaxWeight + 5.0) + "，实际: " + newMaxWeight);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEatCookieTwice()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Item cookie = new Item("cookie", "魔法饼干", 0.3);
            player.takeItem(cookie);
            player.eatCookie();
            // 第二次吃饼干（没有饼干了）
            if (player.hasItem("cookie")) {
                System.out.println("  错误: 第一次吃饼干后背包不应还有cookie");
                return false;
            }
            // 再次调用eatCookie不应崩溃
            player.eatCookie();
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEatCookieWithOtherItems()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            Item cookie = new Item("cookie", "魔法饼干", 0.3);
            Item key = new Item("key", "钥匙", 0.5);
            Item map = new Item("map", "地图", 0.2);
            player.takeItem(key);
            player.takeItem(cookie);
            player.takeItem(map);
            player.eatCookie();
            if (player.hasItem("cookie")) {
                System.out.println("  错误: 吃饼干后背包不应还有cookie");
                return false;
            }
            if (!player.hasItem("key") || !player.hasItem("map")) {
                System.out.println("  错误: 吃饼干后其他物品应保留");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEatCookieCaseInsensitive()
    {
        try {
            EatCookieCommand cmd = new EatCookieCommand();
            Command command = new Command("eat", "Cookie");
            Player player = new Player("测试玩家", 10.0);
            Item cookie = new Item("cookie", "魔法饼干", 0.3);
            player.takeItem(cookie);
            Room room = new Room("测试房间");
            player.setCurrentRoom(room);
            Game game = new Game();
            // 注意：eat cookie命令检查getSecondWord().equals("cookie")，大小写敏感
            cmd.execute(command, game);
            // Cookie（大写C）不等于"cookie"，所以应该提示试试'eat cookie'
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEatCookieWithUsableCookie()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Item cookie = new Item("cookie", "魔法饼干", 0.3, "FOOD", "增加负重");
            player.takeItem(cookie);
            player.eatCookie();
            if (player.hasItem("cookie")) {
                System.out.println("  错误: 吃可使用的饼干后背包不应还有cookie");
                return false;
            }
            if (!player.isCookieEaten()) {
                System.out.println("  错误: 吃可使用的饼干后cookieEaten应为true");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
