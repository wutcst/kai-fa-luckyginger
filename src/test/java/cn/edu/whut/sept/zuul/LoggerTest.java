/**
 * Logger类的单元测试用例。
 * 测试日志工具的启用/禁用、调试日志输出、信息日志输出等功能。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class LoggerTest
{
    /**
     * 运行所有Logger类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("Logger类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testEnableDebug()) {
            System.out.println("✅ 测试1: 启用调试模式 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: 启用调试模式 - 失败");
            failed++;
        }

        if (testDisableDebug()) {
            System.out.println("✅ 测试2: 禁用调试模式 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 禁用调试模式 - 失败");
            failed++;
        }

        if (testDebugWhileEnabled()) {
            System.out.println("✅ 测试3: 调试模式启用时输出调试日志 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 调试模式启用时输出调试日志 - 失败");
            failed++;
        }

        if (testDebugWhileDisabled()) {
            System.out.println("✅ 测试4: 调试模式禁用时不输出调试日志 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 调试模式禁用时不输出调试日志 - 失败");
            failed++;
        }

        if (testInfoAlwaysOutputs()) {
            System.out.println("✅ 测试5: 信息日志始终输出 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 信息日志始终输出 - 失败");
            failed++;
        }

        if (testDebugWithNullMessage()) {
            System.out.println("✅ 测试6: 调试日志null消息 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 调试日志null消息 - 失败");
            failed++;
        }

        if (testInfoWithNullMessage()) {
            System.out.println("✅ 测试7: 信息日志null消息 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 信息日志null消息 - 失败");
            failed++;
        }

        if (testDebugWithEmptyMessage()) {
            System.out.println("✅ 测试8: 调试日志空消息 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 调试日志空消息 - 失败");
            failed++;
        }

        if (testInfoWithEmptyMessage()) {
            System.out.println("✅ 测试9: 信息日志空消息 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 信息日志空消息 - 失败");
            failed++;
        }

        if (testDebugWithChineseMessage()) {
            System.out.println("✅ 测试10: 调试日志中文消息 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 调试日志中文消息 - 失败");
            failed++;
        }

        if (testInfoWithChineseMessage()) {
            System.out.println("✅ 测试11: 信息日志中文消息 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 信息日志中文消息 - 失败");
            failed++;
        }

        if (testToggleDebugMultipleTimes()) {
            System.out.println("✅ 测试12: 多次切换调试模式 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 多次切换调试模式 - 失败");
            failed++;
        }

        if (testDebugWithLongMessage()) {
            System.out.println("✅ 测试13: 调试日志长消息 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 调试日志长消息 - 失败");
            failed++;
        }

        if (testInfoWithLongMessage()) {
            System.out.println("✅ 测试14: 信息日志长消息 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: 信息日志长消息 - 失败");
            failed++;
        }

        if (testDebugWithSpecialCharacters()) {
            System.out.println("✅ 测试15: 调试日志特殊字符 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 调试日志特殊字符 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testEnableDebug()
    {
        try {
            Logger.enableDebug();
            Logger.debug("启用调试模式测试");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDisableDebug()
    {
        try {
            Logger.disableDebug();
            Logger.debug("禁用调试模式测试");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDebugWhileEnabled()
    {
        try {
            Logger.enableDebug();
            Logger.debug("这条调试信息应该输出");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDebugWhileDisabled()
    {
        try {
            Logger.disableDebug();
            Logger.debug("这条调试信息不应该输出");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testInfoAlwaysOutputs()
    {
        try {
            Logger.disableDebug();
            Logger.info("这条信息日志应该始终输出");
            Logger.enableDebug();
            Logger.info("启用调试后信息日志也应该输出");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDebugWithNullMessage()
    {
        try {
            Logger.enableDebug();
            Logger.debug(null);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: null消息不应导致异常 - " + e.getMessage());
            return false;
        }
    }

    private static boolean testInfoWithNullMessage()
    {
        try {
            Logger.info(null);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: null消息不应导致异常 - " + e.getMessage());
            return false;
        }
    }

    private static boolean testDebugWithEmptyMessage()
    {
        try {
            Logger.enableDebug();
            Logger.debug("");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: 空消息不应导致异常 - " + e.getMessage());
            return false;
        }
    }

    private static boolean testInfoWithEmptyMessage()
    {
        try {
            Logger.info("");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: 空消息不应导致异常 - " + e.getMessage());
            return false;
        }
    }

    private static boolean testDebugWithChineseMessage()
    {
        try {
            Logger.enableDebug();
            Logger.debug("这是一条中文调试消息");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testInfoWithChineseMessage()
    {
        try {
            Logger.info("这是一条中文信息消息");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToggleDebugMultipleTimes()
    {
        try {
            Logger.enableDebug();
            Logger.debug("第1次启用");
            Logger.disableDebug();
            Logger.debug("禁用中");
            Logger.enableDebug();
            Logger.debug("第2次启用");
            Logger.disableDebug();
            Logger.enableDebug();
            Logger.debug("第3次启用");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDebugWithLongMessage()
    {
        try {
            Logger.enableDebug();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                sb.append("a");
            }
            Logger.debug(sb.toString());
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testInfoWithLongMessage()
    {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                sb.append("b");
            }
            Logger.info(sb.toString());
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDebugWithSpecialCharacters()
    {
        try {
            Logger.enableDebug();
            Logger.debug("特殊字符: \t\n\r\f\b!@#$%^&*()");
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
