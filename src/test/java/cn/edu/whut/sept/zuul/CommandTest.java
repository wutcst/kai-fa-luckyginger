package cn.edu.whut.sept.zuul;

/**
 * Tests for Command word parsing state.
 */
public class CommandTest
{
    public static boolean runAllTests()
    {
        int passed = 0;
        int failed = 0;

        if (testCommandWithTwoWords()) passed++; else failed++;
        if (testCommandWithoutSecondWord()) passed++; else failed++;
        if (testUnknownCommand()) passed++; else failed++;
        if (testUnknownCommandCanStillStoreSecondWord()) passed++; else failed++;
        if (testEmptyStringCommandIsNotUnknown()) passed++; else failed++;
        if (testEmptySecondWordCountsAsSecondWord()) passed++; else failed++;

        printSummary("CommandTest", passed, failed);
        return failed == 0;
    }

    private static boolean testCommandWithTwoWords()
    {
        Command command = new Command("go", "north");

        return assertEquals("go", command.getCommandWord(), "命令词不正确")
                && assertEquals("north", command.getSecondWord(), "第二个词不正确")
                && assertFalse(command.isUnknown(), "有效命令不应被识别为未知")
                && assertTrue(command.hasSecondWord(), "带方向的命令应有第二个词");
    }

    private static boolean testCommandWithoutSecondWord()
    {
        Command command = new Command("look", null);

        return assertEquals("look", command.getCommandWord(), "命令词不正确")
                && assertNull(command.getSecondWord(), "没有第二个词时应返回 null")
                && assertFalse(command.isUnknown(), "命令词非 null 时不应未知")
                && assertFalse(command.hasSecondWord(), "第二个词为 null 时 hasSecondWord 应为 false");
    }

    private static boolean testUnknownCommand()
    {
        Command command = new Command(null, null);

        return assertNull(command.getCommandWord(), "未知命令的命令词应为 null")
                && assertTrue(command.isUnknown(), "命令词为 null 时应识别为未知")
                && assertFalse(command.hasSecondWord(), "第二个词为 null 时不应有第二个词");
    }

    private static boolean testUnknownCommandCanStillStoreSecondWord()
    {
        Command command = new Command(null, "anything");

        return assertTrue(command.isUnknown(), "命令词为 null 时应识别为未知")
                && assertEquals("anything", command.getSecondWord(), "未知命令仍应保留第二个词")
                && assertTrue(command.hasSecondWord(), "第二个词非 null 时 hasSecondWord 应为 true");
    }

    private static boolean testEmptyStringCommandIsNotUnknown()
    {
        Command command = new Command("", null);

        return assertEquals("", command.getCommandWord(), "空字符串命令词应原样保存")
                && assertFalse(command.isUnknown(), "只有 null 命令词才应被识别为未知");
    }

    private static boolean testEmptySecondWordCountsAsSecondWord()
    {
        Command command = new Command("take", "");

        return assertEquals("", command.getSecondWord(), "空字符串第二个词应原样保存")
                && assertTrue(command.hasSecondWord(), "第二个词为空字符串但非 null，应视为存在");
    }

    private static void printSummary(String testName, int passed, int failed)
    {
        System.out.println(testName + ": " + passed + " passed, " + failed + " failed.");
    }

    private static boolean assertTrue(boolean condition, String message)
    {
        if (!condition) {
            System.out.println("失败: " + message);
        }
        return condition;
    }

    private static boolean assertFalse(boolean condition, String message)
    {
        return assertTrue(!condition, message);
    }

    private static boolean assertNull(Object actual, String message)
    {
        return assertTrue(actual == null, message);
    }

    private static boolean assertEquals(Object expected, Object actual, String message)
    {
        boolean matches = expected == null ? actual == null : expected.equals(actual);
        if (!matches) {
            System.out.println("失败: " + message + "，期望: " + expected + "，实际: " + actual);
        }
        return matches;
    }
}
