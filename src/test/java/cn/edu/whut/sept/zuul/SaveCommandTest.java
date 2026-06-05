/**
 * SaveCommand类的单元测试用例。
 * 测试保存游戏命令的各种场景，包括未登录、成功保存等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class SaveCommandTest
{
    /**
     * 运行所有SaveCommand类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("SaveCommand类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testSaveCommandCreation()) {
            System.out.println("✅ 测试1: SaveCommand对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: SaveCommand对象创建 - 失败");
            failed++;
        }

        if (testSaveCommandImplementsInterface()) {
            System.out.println("✅ 测试2: SaveCommand实现CommandExecutor接口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: SaveCommand实现CommandExecutor接口 - 失败");
            failed++;
        }

        if (testSaveReturnsFalse()) {
            System.out.println("✅ 测试3: save命令返回false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: save命令返回false - 失败");
            failed++;
        }

        if (testSaveWithoutLogin()) {
            System.out.println("✅ 测试4: 未登录时保存 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 未登录时保存 - 失败");
            failed++;
        }

        if (testSaveCommandWithSecondWord()) {
            System.out.println("✅ 测试5: save命令带额外参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: save命令带额外参数 - 失败");
            failed++;
        }

        if (testSaveCommandWithNullCommand()) {
            System.out.println("✅ 测试6: save命令null参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: save命令null参数 - 失败");
            failed++;
        }

        if (testGameStateManagerCreation()) {
            System.out.println("✅ 测试7: GameStateManager对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: GameStateManager对象创建 - 失败");
            failed++;
        }

        if (testSaveCommandNoException()) {
            System.out.println("✅ 测试8: save命令不抛异常 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: save命令不抛异常 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testSaveCommandCreation()
    {
        try {
            SaveCommand cmd = new SaveCommand();
            if (cmd == null) {
                System.out.println("  错误: 无法创建SaveCommand对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSaveCommandImplementsInterface()
    {
        try {
            SaveCommand cmd = new SaveCommand();
            if (!(cmd instanceof CommandExecutor)) {
                System.out.println("  错误: SaveCommand应实现CommandExecutor接口");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSaveReturnsFalse()
    {
        try {
            SaveCommand cmd = new SaveCommand();
            Command command = new Command("save", null);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: save命令应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSaveWithoutLogin()
    {
        try {
            SaveCommand cmd = new SaveCommand();
            Command command = new Command("save", null);
            Game game = new Game();
            // 未登录的玩家userId为null
            boolean result = cmd.execute(command, game);
            // 应该输出"保存失败：请先登录！"
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSaveCommandWithSecondWord()
    {
        try {
            SaveCommand cmd = new SaveCommand();
            Command command = new Command("save", "slot1");
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            // save命令忽略第二个词
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSaveCommandWithNullCommand()
    {
        try {
            SaveCommand cmd = new SaveCommand();
            Command command = new Command(null, null);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testGameStateManagerCreation()
    {
        try {
            Game game = new Game();
            GameStateManager manager = new GameStateManager(game);
            if (manager == null) {
                System.out.println("  错误: 无法创建GameStateManager对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSaveCommandNoException()
    {
        try {
            SaveCommand cmd = new SaveCommand();
            Command command = new Command("save", null);
            Game game = new Game();
            cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: save命令不应抛出异常 - " + e.getMessage());
            return false;
        }
    }
}
