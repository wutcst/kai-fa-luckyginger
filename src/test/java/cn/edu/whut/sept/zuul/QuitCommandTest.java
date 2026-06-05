/**
 * QuitCommand类的单元测试用例。
 * 测试退出命令的各种场景，包括正常退出、带参数退出等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class QuitCommandTest
{
    /**
     * 运行所有QuitCommand类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("QuitCommand类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testQuitCommandCreation()) {
            System.out.println("✅ 测试1: QuitCommand对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: QuitCommand对象创建 - 失败");
            failed++;
        }

        if (testQuitCommandImplementsInterface()) {
            System.out.println("✅ 测试2: QuitCommand实现CommandExecutor接口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: QuitCommand实现CommandExecutor接口 - 失败");
            failed++;
        }

        if (testQuitWithoutSecondWordReturnsTrue()) {
            System.out.println("✅ 测试3: quit无参数返回true - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: quit无参数返回true - 失败");
            failed++;
        }

        if (testQuitWithSecondWordReturnsFalse()) {
            System.out.println("✅ 测试4: quit带参数返回false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: quit带参数返回false - 失败");
            failed++;
        }

        if (testQuitWithDirectionArg()) {
            System.out.println("✅ 测试5: quit带方向参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: quit带方向参数 - 失败");
            failed++;
        }

        if (testQuitWithItemArg()) {
            System.out.println("✅ 测试6: quit带物品参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: quit带物品参数 - 失败");
            failed++;
        }

        if (testQuitWithRandomArg()) {
            System.out.println("✅ 测试7: quit带随机参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: quit带随机参数 - 失败");
            failed++;
        }

        if (testQuitCommandNoException()) {
            System.out.println("✅ 测试8: quit命令不抛异常 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: quit命令不抛异常 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testQuitCommandCreation()
    {
        try {
            QuitCommand cmd = new QuitCommand();
            if (cmd == null) {
                System.out.println("  错误: 无法创建QuitCommand对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testQuitCommandImplementsInterface()
    {
        try {
            QuitCommand cmd = new QuitCommand();
            if (!(cmd instanceof CommandExecutor)) {
                System.out.println("  错误: QuitCommand应实现CommandExecutor接口");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testQuitWithoutSecondWordReturnsTrue()
    {
        try {
            QuitCommand cmd = new QuitCommand();
            Command command = new Command("quit", null);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (!result) {
                System.out.println("  错误: quit无参数应返回true（表示要退出）");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testQuitWithSecondWordReturnsFalse()
    {
        try {
            QuitCommand cmd = new QuitCommand();
            Command command = new Command("quit", "game");
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: quit带参数应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testQuitWithDirectionArg()
    {
        try {
            QuitCommand cmd = new QuitCommand();
            Command command = new Command("quit", "north");
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: quit带方向参数应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testQuitWithItemArg()
    {
        try {
            QuitCommand cmd = new QuitCommand();
            Command command = new Command("quit", "key");
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: quit带物品参数应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testQuitWithRandomArg()
    {
        try {
            QuitCommand cmd = new QuitCommand();
            Command command = new Command("quit", "abc123");
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: quit带随机参数应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testQuitCommandNoException()
    {
        try {
            QuitCommand cmd = new QuitCommand();
            Command command = new Command("quit", null);
            Game game = new Game();
            cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: quit命令不应抛出异常 - " + e.getMessage());
            return false;
        }
    }
}
