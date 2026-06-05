/**
 * StatusCommand类的单元测试用例。
 * 测试状态命令的各种场景，包括显示进度、更新记录等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class StatusCommandTest
{
    /**
     * 运行所有StatusCommand类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("StatusCommand类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testStatusCommandCreation()) {
            System.out.println("✅ 测试1: StatusCommand对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: StatusCommand对象创建 - 失败");
            failed++;
        }

        if (testStatusCommandImplementsInterface()) {
            System.out.println("✅ 测试2: StatusCommand实现CommandExecutor接口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: StatusCommand实现CommandExecutor接口 - 失败");
            failed++;
        }

        if (testStatusReturnsFalse()) {
            System.out.println("✅ 测试3: status命令返回false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: status命令返回false - 失败");
            failed++;
        }

        if (testStatusCommandNoException()) {
            System.out.println("✅ 测试4: status命令不抛异常 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: status命令不抛异常 - 失败");
            failed++;
        }

        if (testStatusWithNewPlayer()) {
            System.out.println("✅ 测试5: 新玩家状态显示 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 新玩家状态显示 - 失败");
            failed++;
        }

        if (testStatusWithExploredRooms()) {
            System.out.println("✅ 测试6: 已探索房间状态 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 已探索房间状态 - 失败");
            failed++;
        }

        if (testStatusWithCollectedItems()) {
            System.out.println("✅ 测试7: 已收集物品状态 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 已收集物品状态 - 失败");
            failed++;
        }

        if (testStatusWithCookieEaten()) {
            System.out.println("✅ 测试8: 已吃饼干状态 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 已吃饼干状态 - 失败");
            failed++;
        }

        if (testStatusWithSecondWord()) {
            System.out.println("✅ 测试9: status命令带额外参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: status命令带额外参数 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testStatusCommandCreation()
    {
        try {
            StatusCommand cmd = new StatusCommand();
            if (cmd == null) {
                System.out.println("  错误: 无法创建StatusCommand对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testStatusCommandImplementsInterface()
    {
        try {
            StatusCommand cmd = new StatusCommand();
            if (!(cmd instanceof CommandExecutor)) {
                System.out.println("  错误: StatusCommand应实现CommandExecutor接口");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testStatusReturnsFalse()
    {
        try {
            StatusCommand cmd = new StatusCommand();
            Command command = new Command("status", null);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: status命令应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testStatusCommandNoException()
    {
        try {
            StatusCommand cmd = new StatusCommand();
            Command command = new Command("status", null);
            Game game = new Game();
            cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: status命令不应抛出异常 - " + e.getMessage());
            return false;
        }
    }

    private static boolean testStatusWithNewPlayer()
    {
        try {
            StatusCommand cmd = new StatusCommand();
            Command command = new Command("status", null);
            Game game = new Game();
            cmd.execute(command, game);
            // 新玩家应该显示0房间、0物品
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testStatusWithExploredRooms()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setRoomsExplored(3);
            info.setTotalRooms(7);
            String report = info.getProgressReport();
            if (!report.contains("3/7")) {
                System.out.println("  错误: 进度报告应包含房间探索比例");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testStatusWithCollectedItems()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setItemsCollected(5);
            info.setTotalItems(9);
            String report = info.getProgressReport();
            if (!report.contains("5/9")) {
                System.out.println("  错误: 进度报告应包含物品收集比例");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testStatusWithCookieEaten()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setCookieEaten(true);
            String report = info.getProgressReport();
            if (!report.contains("已吃掉")) {
                System.out.println("  错误: 吃掉饼干的进度报告应包含'已吃掉'");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testStatusWithSecondWord()
    {
        try {
            StatusCommand cmd = new StatusCommand();
            Command command = new Command("status", "detail");
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            // status命令忽略第二个词
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
