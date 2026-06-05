/**
 * Parser类的单元测试用例。
 * 测试命令解析功能，包括有效命令解析、无效命令处理、eat命令特殊处理等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class ParserTest
{
    /**
     * 运行所有Parser类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("Parser类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testParserCreation()) {
            System.out.println("✅ 测试1: Parser对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: Parser对象创建 - 失败");
            failed++;
        }

        if (testParseGoCommand()) {
            System.out.println("✅ 测试2: 解析go命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 解析go命令 - 失败");
            failed++;
        }

        if (testParseGoNorthCommand()) {
            System.out.println("✅ 测试3: 解析go north命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 解析go north命令 - 失败");
            failed++;
        }

        if (testParseQuitCommand()) {
            System.out.println("✅ 测试4: 解析quit命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 解析quit命令 - 失败");
            failed++;
        }

        if (testParseHelpCommand()) {
            System.out.println("✅ 测试5: 解析help命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 解析help命令 - 失败");
            failed++;
        }

        if (testParseTakeCommand()) {
            System.out.println("✅ 测试6: 解析take命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 解析take命令 - 失败");
            failed++;
        }

        if (testParseDropCommand()) {
            System.out.println("✅ 测试7: 解析drop命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 解析drop命令 - 失败");
            failed++;
        }

        if (testParseEatCookieCommand()) {
            System.out.println("✅ 测试8: 解析eat cookie命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 解析eat cookie命令 - 失败");
            failed++;
        }

        if (testParseUseCommand()) {
            System.out.println("✅ 测试9: 解析use命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 解析use命令 - 失败");
            failed++;
        }

        if (testParseInvalidCommand()) {
            System.out.println("✅ 测试10: 解析无效命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 解析无效命令 - 失败");
            failed++;
        }

        if (testParseEmptyString()) {
            System.out.println("✅ 测试11: 解析空字符串 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 解析空字符串 - 失败");
            failed++;
        }

        if (testParseCommandWithExtraSpaces()) {
            System.out.println("✅ 测试12: 解析带多余空格的命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 解析带多余空格的命令 - 失败");
            failed++;
        }

        if (testParseCommandCaseInsensitive()) {
            System.out.println("✅ 测试13: 命令解析大小写不敏感 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 命令解析大小写不敏感 - 失败");
            failed++;
        }

        if (testParseEatWithMultipleWords()) {
            System.out.println("✅ 测试14: eat命令多词参数处理 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: eat命令多词参数处理 - 失败");
            failed++;
        }

        if (testParseLookCommand()) {
            System.out.println("✅ 测试15: 解析look命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 解析look命令 - 失败");
            failed++;
        }

        if (testParseStatusCommand()) {
            System.out.println("✅ 测试16: 解析status命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试16: 解析status命令 - 失败");
            failed++;
        }

        if (testParseSaveCommand()) {
            System.out.println("✅ 测试17: 解析save命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试17: 解析save命令 - 失败");
            failed++;
        }

        if (testParseLoadCommand()) {
            System.out.println("✅ 测试18: 解析load命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试18: 解析load命令 - 失败");
            failed++;
        }

        if (testParseBackCommand()) {
            System.out.println("✅ 测试19: 解析back命令 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试19: 解析back命令 - 失败");
            failed++;
        }

        if (testShowCommandsNoException()) {
            System.out.println("✅ 测试20: showCommands不抛异常 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试20: showCommands不抛异常 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testParserCreation()
    {
        try {
            Parser parser = new Parser();
            if (parser == null) {
                System.out.println("  错误: 无法创建Parser对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseGoCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("go");
            if (cmd == null) {
                System.out.println("  错误: 解析go命令返回null");
                return false;
            }
            if (!cmd.getCommandWord().equals("go")) {
                System.out.println("  错误: 命令词应为go，实际: " + cmd.getCommandWord());
                return false;
            }
            if (cmd.hasSecondWord()) {
                System.out.println("  错误: go命令不应有第二个词");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseGoNorthCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("go north");
            if (!cmd.getCommandWord().equals("go")) {
                System.out.println("  错误: 命令词应为go");
                return false;
            }
            if (!cmd.getSecondWord().equals("north")) {
                System.out.println("  错误: 第二个词应为north，实际: " + cmd.getSecondWord());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseQuitCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("quit");
            if (!cmd.getCommandWord().equals("quit")) {
                System.out.println("  错误: 命令词应为quit");
                return false;
            }
            if (cmd.hasSecondWord()) {
                System.out.println("  错误: quit命令不应有第二个词");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseHelpCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("help");
            if (!cmd.getCommandWord().equals("help")) {
                System.out.println("  错误: 命令词应为help");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseTakeCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("take key");
            if (!cmd.getCommandWord().equals("take")) {
                System.out.println("  错误: 命令词应为take");
                return false;
            }
            if (!cmd.getSecondWord().equals("key")) {
                System.out.println("  错误: 第二个词应为key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseDropCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("drop key");
            if (!cmd.getCommandWord().equals("drop")) {
                System.out.println("  错误: 命令词应为drop");
                return false;
            }
            if (!cmd.getSecondWord().equals("key")) {
                System.out.println("  错误: 第二个词应为key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseEatCookieCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("eat cookie");
            if (!cmd.getCommandWord().equals("eat")) {
                System.out.println("  错误: 命令词应为eat");
                return false;
            }
            if (!cmd.getSecondWord().equals("cookie")) {
                System.out.println("  错误: 第二个词应为cookie，实际: " + cmd.getSecondWord());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseUseCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("use key");
            if (!cmd.getCommandWord().equals("use")) {
                System.out.println("  错误: 命令词应为use");
                return false;
            }
            if (!cmd.getSecondWord().equals("key")) {
                System.out.println("  错误: 第二个词应为key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseInvalidCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("fly north");
            if (!cmd.isUnknown()) {
                System.out.println("  错误: 无效命令应该返回未知命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseEmptyString()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("");
            if (!cmd.isUnknown()) {
                System.out.println("  错误: 空字符串应该返回未知命令");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseCommandWithExtraSpaces()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("  go   north  ");
            if (!cmd.getCommandWord().equals("go")) {
                System.out.println("  错误: 带多余空格的命令词应为go");
                return false;
            }
            if (!cmd.getSecondWord().equals("north")) {
                System.out.println("  错误: 带多余空格的第二个词应为north");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseCommandCaseInsensitive()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("GO NORTH");
            if (!cmd.getCommandWord().equals("go")) {
                System.out.println("  错误: 大写命令应被转为小写go，实际: " + cmd.getCommandWord());
                return false;
            }
            if (!cmd.getSecondWord().equals("north")) {
                System.out.println("  错误: 大写参数应被转为小写north，实际: " + cmd.getSecondWord());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseEatWithMultipleWords()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("eat magic cookie");
            if (!cmd.getCommandWord().equals("eat")) {
                System.out.println("  错误: 命令词应为eat");
                return false;
            }
            if (!cmd.getSecondWord().equals("magic cookie")) {
                System.out.println("  错误: eat命令的第二个词应包含所有后续内容，实际: " + cmd.getSecondWord());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseLookCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("look");
            if (!cmd.getCommandWord().equals("look")) {
                System.out.println("  错误: 命令词应为look");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseStatusCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("status");
            if (!cmd.getCommandWord().equals("status")) {
                System.out.println("  错误: 命令词应为status");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseSaveCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("save");
            if (!cmd.getCommandWord().equals("save")) {
                System.out.println("  错误: 命令词应为save");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseLoadCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("load");
            if (!cmd.getCommandWord().equals("load")) {
                System.out.println("  错误: 命令词应为load");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testParseBackCommand()
    {
        try {
            Parser parser = new Parser();
            Command cmd = parser.parseCommand("back");
            if (!cmd.getCommandWord().equals("back")) {
                System.out.println("  错误: 命令词应为back");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testShowCommandsNoException()
    {
        try {
            Parser parser = new Parser();
            parser.showCommands();
            return true;
        } catch (Exception e) {
            System.out.println("  异常: showCommands()不应抛出异常 - " + e.getMessage());
            return false;
        }
    }
}
