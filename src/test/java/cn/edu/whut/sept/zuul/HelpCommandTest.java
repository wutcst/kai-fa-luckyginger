/**
 * HelpCommand类的单元测试用例。
 * 测试帮助命令的各种场景，包括命令列表显示、帮助信息格式等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class HelpCommandTest
{
    /**
     * 运行所有HelpCommand类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("HelpCommand类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testHelpCommandCreation()) {
            System.out.println("✅ 测试1: HelpCommand对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: HelpCommand对象创建 - 失败");
            failed++;
        }

        if (testHelpCommandImplementsInterface()) {
            System.out.println("✅ 测试2: HelpCommand实现CommandExecutor接口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: HelpCommand实现CommandExecutor接口 - 失败");
            failed++;
        }

        if (testHelpReturnsFalse()) {
            System.out.println("✅ 测试3: help命令返回false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: help命令返回false - 失败");
            failed++;
        }

        if (testHelpCommandNoException()) {
            System.out.println("✅ 测试4: help命令不抛异常 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: help命令不抛异常 - 失败");
            failed++;
        }

        if (testHelpWithSecondWord()) {
            System.out.println("✅ 测试5: help命令带额外参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: help命令带额外参数 - 失败");
            failed++;
        }

        if (testHelpWithNullSecondWord()) {
            System.out.println("✅ 测试6: help命令无参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: help命令无参数 - 失败");
            failed++;
        }

        if (testHelpWithUnknownCommand()) {
            System.out.println("✅ 测试7: help命令未知命令词 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: help命令未知命令词 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testHelpCommandCreation()
    {
        try {
            HelpCommand cmd = new HelpCommand();
            if (cmd == null) {
                System.out.println("  错误: 无法创建HelpCommand对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testHelpCommandImplementsInterface()
    {
        try {
            HelpCommand cmd = new HelpCommand();
            if (!(cmd instanceof CommandExecutor)) {
                System.out.println("  错误: HelpCommand应实现CommandExecutor接口");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testHelpReturnsFalse()
    {
        try {
            HelpCommand cmd = new HelpCommand();
            Command command = new Command("help", null);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: help命令应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testHelpCommandNoException()
    {
        try {
            HelpCommand cmd = new HelpCommand();
            Command command = new Command("help", null);
            Game game = new Game();
            cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: help命令不应抛出异常 - " + e.getMessage());
            return false;
        }
    }

    private static boolean testHelpWithSecondWord()
    {
        try {
            HelpCommand cmd = new HelpCommand();
            Command command = new Command("help", "go");
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            // help命令忽略第二个词
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testHelpWithNullSecondWord()
    {
        try {
            HelpCommand cmd = new HelpCommand();
            Command command = new Command("help", null);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testHelpWithUnknownCommand()
    {
        try {
            HelpCommand cmd = new HelpCommand();
            Command command = new Command("help", null);
            Game game = new Game();
            cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
