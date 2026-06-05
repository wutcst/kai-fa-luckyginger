/**
 * CommandWords类的单元测试用例。
 * 测试命令词汇验证功能，包括有效命令识别、无效命令拒绝、null处理等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class CommandWordsTest
{
    /**
     * 运行所有CommandWords类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("CommandWords类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testCommandWordsCreation()) {
            System.out.println("✅ 测试1: CommandWords对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: CommandWords对象创建 - 失败");
            failed++;
        }

        if (testValidCommandGo()) {
            System.out.println("✅ 测试2: 有效命令go - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 有效命令go - 失败");
            failed++;
        }

        if (testValidCommandQuit()) {
            System.out.println("✅ 测试3: 有效命令quit - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 有效命令quit - 失败");
            failed++;
        }

        if (testValidCommandHelp()) {
            System.out.println("✅ 测试4: 有效命令help - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 有效命令help - 失败");
            failed++;
        }

        if (testValidCommandLook()) {
            System.out.println("✅ 测试5: 有效命令look - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 有效命令look - 失败");
            failed++;
        }

        if (testValidCommandBack()) {
            System.out.println("✅ 测试6: 有效命令back - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 有效命令back - 失败");
            failed++;
        }

        if (testValidCommandTake()) {
            System.out.println("✅ 测试7: 有效命令take - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 有效命令take - 失败");
            failed++;
        }

        if (testValidCommandDrop()) {
            System.out.println("✅ 测试8: 有效命令drop - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 有效命令drop - 失败");
            failed++;
        }

        if (testValidCommandItems()) {
            System.out.println("✅ 测试9: 有效命令items - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 有效命令items - 失败");
            failed++;
        }

        if (testValidCommandEat()) {
            System.out.println("✅ 测试10: 有效命令eat - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 有效命令eat - 失败");
            failed++;
        }

        if (testValidCommandStatus()) {
            System.out.println("✅ 测试11: 有效命令status - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 有效命令status - 失败");
            failed++;
        }

        if (testValidCommandUse()) {
            System.out.println("✅ 测试12: 有效命令use - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 有效命令use - 失败");
            failed++;
        }

        if (testValidCommandSave()) {
            System.out.println("✅ 测试13: 有效命令save - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 有效命令save - 失败");
            failed++;
        }

        if (testValidCommandLoad()) {
            System.out.println("✅ 测试14: 有效命令load - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: 有效命令load - 失败");
            failed++;
        }

        if (testValidCommandTeleport()) {
            System.out.println("✅ 测试15: 有效命令teleport - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 有效命令teleport - 失败");
            failed++;
        }

        if (testInvalidCommand()) {
            System.out.println("✅ 测试16: 无效命令识别 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试16: 无效命令识别 - 失败");
            failed++;
        }

        if (testNullCommand()) {
            System.out.println("✅ 测试17: null命令处理 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试17: null命令处理 - 失败");
            failed++;
        }

        if (testEmptyStringCommand()) {
            System.out.println("✅ 测试18: 空字符串命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试18: 空字符串命令 - 失败");
            failed++;
        }

        if (testCaseSensitiveCommand()) {
            System.out.println("✅ 测试19: 命令大小写敏感 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试19: 命令大小写敏感 - 失败");
            failed++;
        }

        if (testShowAllNoException()) {
            System.out.println("✅ 测试20: showAll不抛异常 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试20: showAll不抛异常 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testCommandWordsCreation()
    {
        try {
            CommandWords cw = new CommandWords();
            if (cw == null) {
                System.out.println("  错误: 无法创建CommandWords对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandGo()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("go")) {
                System.out.println("  错误: 'go'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandQuit()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("quit")) {
                System.out.println("  错误: 'quit'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandHelp()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("help")) {
                System.out.println("  错误: 'help'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandLook()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("look")) {
                System.out.println("  错误: 'look'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandBack()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("back")) {
                System.out.println("  错误: 'back'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandTake()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("take")) {
                System.out.println("  错误: 'take'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandDrop()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("drop")) {
                System.out.println("  错误: 'drop'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandItems()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("items")) {
                System.out.println("  错误: 'items'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandEat()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("eat")) {
                System.out.println("  错误: 'eat'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandStatus()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("status")) {
                System.out.println("  错误: 'status'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandUse()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("use")) {
                System.out.println("  错误: 'use'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandSave()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("save")) {
                System.out.println("  错误: 'save'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandLoad()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("load")) {
                System.out.println("  错误: 'load'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testValidCommandTeleport()
    {
        try {
            CommandWords cw = new CommandWords();
            if (!cw.isCommand("teleport")) {
                System.out.println("  错误: 'teleport'应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testInvalidCommand()
    {
        try {
            CommandWords cw = new CommandWords();
            if (cw.isCommand("fly")) {
                System.out.println("  错误: 'fly'不应该是有效命令");
                return false;
            }
            if (cw.isCommand("run")) {
                System.out.println("  错误: 'run'不应该是有效命令");
                return false;
            }
            if (cw.isCommand("jump")) {
                System.out.println("  错误: 'jump'不应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testNullCommand()
    {
        try {
            CommandWords cw = new CommandWords();
            if (cw.isCommand(null)) {
                System.out.println("  错误: null不应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testEmptyStringCommand()
    {
        try {
            CommandWords cw = new CommandWords();
            if (cw.isCommand("")) {
                System.out.println("  错误: 空字符串不应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCaseSensitiveCommand()
    {
        try {
            CommandWords cw = new CommandWords();
            // CommandWords的isCommand使用equals，区分大小写
            if (cw.isCommand("GO")) {
                System.out.println("  错误: 'GO'（大写）不应该是有效命令（区分大小写）");
                return false;
            }
            if (cw.isCommand("Quit")) {
                System.out.println("  错误: 'Quit'（首字母大写）不应该是有效命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testShowAllNoException()
    {
        try {
            CommandWords cw = new CommandWords();
            cw.showAll();
            return true;
        } catch (Exception e) {
            System.out.println("  异常: showAll()不应抛出异常 - " + e.getMessage());
            return false;
        }
    }
}
