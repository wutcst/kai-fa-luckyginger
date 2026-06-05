/**
 * 新增测试运行器。
 * 运行所有新增的单元测试用例并汇总结果。
 * 与原有的TestRunner.java独立，不修改原文件。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class NewTestRunner
{
    public static void main(String[] args)
    {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║        World of Zuul - 新增单元测试套件                 ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();

        int totalPassed = 0;
        int totalFailed = 0;

        // GameCompletionCheckerTest
        if (GameCompletionCheckerTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // CommandWordsTest
        if (CommandWordsTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // ParserTest
        if (ParserTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // JsonUtilTest
        if (JsonUtilTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // LoggerTest
        if (LoggerTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }


        // EatCookieCommandTest
        if (EatCookieCommandTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // SaveCommandTest
        if (SaveCommandTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // LoadCommandTest
        if (LoadCommandTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // StatusCommandTest
        if (StatusCommandTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // ItemsCommandTest
        if (ItemsCommandTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // LookCommandTest
        if (LookCommandTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // HelpCommandTest
        if (HelpCommandTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // QuitCommandTest
        if (QuitCommandTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // GameStateManagerTest
        if (GameStateManagerTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // CompletionInfoTest
        if (CompletionInfoTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // CommandExecutorTest
        if (CommandExecutorTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // ItemExtendedTest
        if (ItemExtendedTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // RoomExtendedTest
        if (RoomExtendedTest.runAllTests()) {
            totalPassed++;
        } else {
            totalFailed++;
        }

        // 汇总测试结果
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║                     新增测试结果汇总                    ║");
        System.out.println("╠════════════════════════════════════════════════════════╣");
        System.out.printf("║  测试套件: %-43d  ║\n", totalPassed + totalFailed);
        System.out.printf("║  通过: %-47d  ║\n", totalPassed);
        System.out.printf("║  失败: %-47d  ║\n", totalFailed);

        double passRate = (totalPassed + totalFailed) > 0 ?
            (double) totalPassed / (totalPassed + totalFailed) * 100 : 0;
        System.out.printf("║  通过率: %-45.2f%%  ║\n", passRate);

        if (totalFailed == 0) {
            System.out.println("║                                                          ║");
            System.out.println("║  ✅ 所有新增测试通过！                                    ║");
        } else {
            System.out.println("║                                                          ║");
            System.out.println("║  ❌ 部分新增测试失败，请检查上述错误信息                  ║");
        }
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();

        System.exit(totalFailed == 0 ? 0 : 1);
    }
}
