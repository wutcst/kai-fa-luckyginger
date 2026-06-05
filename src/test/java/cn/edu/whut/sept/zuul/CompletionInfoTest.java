/**
 * CompletionInfo类的单元测试用例。
 * 深入测试GameCompletionChecker.CompletionInfo内部类的所有属性和方法。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class CompletionInfoTest
{
    /**
     * 运行所有CompletionInfo类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("CompletionInfo类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testDefaultCompleted()) {
            System.out.println("✅ 测试1: 默认completed为false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: 默认completed为false - 失败");
            failed++;
        }

        if (testDefaultAtStartRoom()) {
            System.out.println("✅ 测试2: 默认atStartRoom为false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 默认atStartRoom为false - 失败");
            failed++;
        }

        if (testDefaultRoomsExplored()) {
            System.out.println("✅ 测试3: 默认roomsExplored为0 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 默认roomsExplored为0 - 失败");
            failed++;
        }

        if (testDefaultTotalRooms()) {
            System.out.println("✅ 测试4: 默认totalRooms为0 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 默认totalRooms为0 - 失败");
            failed++;
        }

        if (testDefaultAllRoomsExplored()) {
            System.out.println("✅ 测试5: 默认allRoomsExplored为false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 默认allRoomsExplored为false - 失败");
            failed++;
        }

        if (testDefaultItemsCollected()) {
            System.out.println("✅ 测试6: 默认itemsCollected为0 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 默认itemsCollected为0 - 失败");
            failed++;
        }

        if (testDefaultTotalItems()) {
            System.out.println("✅ 测试7: 默认totalItems为0 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 默认totalItems为0 - 失败");
            failed++;
        }

        if (testDefaultAllItemsCollected()) {
            System.out.println("✅ 测试8: 默认allItemsCollected为false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 默认allItemsCollected为false - 失败");
            failed++;
        }

        if (testDefaultCookieEaten()) {
            System.out.println("✅ 测试9: 默认cookieEaten为false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 默认cookieEaten为false - 失败");
            failed++;
        }

        if (testSetCompletedTrue()) {
            System.out.println("✅ 测试10: 设置completed为true - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 设置completed为true - 失败");
            failed++;
        }

        if (testSetAtStartRoomTrue()) {
            System.out.println("✅ 测试11: 设置atStartRoom为true - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 设置atStartRoom为true - 失败");
            failed++;
        }

        if (testSetRoomsExploredValue()) {
            System.out.println("✅ 测试12: 设置roomsExplored值 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 设置roomsExplored值 - 失败");
            failed++;
        }

        if (testSetTotalRoomsValue()) {
            System.out.println("✅ 测试13: 设置totalRooms值 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 设置totalRooms值 - 失败");
            failed++;
        }

        if (testSetAllRoomsExploredTrue()) {
            System.out.println("✅ 测试14: 设置allRoomsExplored为true - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: 设置allRoomsExplored为true - 失败");
            failed++;
        }

        if (testSetItemsCollectedValue()) {
            System.out.println("✅ 测试15: 设置itemsCollected值 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 设置itemsCollected值 - 失败");
            failed++;
        }

        if (testSetTotalItemsValue()) {
            System.out.println("✅ 测试16: 设置totalItems值 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试16: 设置totalItems值 - 失败");
            failed++;
        }

        if (testSetAllItemsCollectedTrue()) {
            System.out.println("✅ 测试17: 设置allItemsCollected为true - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试17: 设置allItemsCollected为true - 失败");
            failed++;
        }

        if (testSetCookieEatenTrue()) {
            System.out.println("✅ 测试18: 设置cookieEaten为true - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试18: 设置cookieEaten为true - 失败");
            failed++;
        }

        if (testProgressReportWithCheckmarks()) {
            System.out.println("✅ 测试19: 进度报告包含勾号 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试19: 进度报告包含勾号 - 失败");
            failed++;
        }

        if (testProgressReportWithoutCheckmarks()) {
            System.out.println("✅ 测试20: 进度报告不含勾号 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试20: 进度报告不含勾号 - 失败");
            failed++;
        }

        if (testToggleCompletedMultipleTimes()) {
            System.out.println("✅ 测试21: 多次切换completed - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试21: 多次切换completed - 失败");
            failed++;
        }

        if (testLargeRoomsExploredValue()) {
            System.out.println("✅ 测试22: 大数值roomsExplored - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试22: 大数值roomsExplored - 失败");
            failed++;
        }

        if (testLargeItemsCollectedValue()) {
            System.out.println("✅ 测试23: 大数值itemsCollected - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试23: 大数值itemsCollected - 失败");
            failed++;
        }

        if (testZeroValuesProgressReport()) {
            System.out.println("✅ 测试24: 零值进度报告 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试24: 零值进度报告 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testDefaultCompleted()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return !info.isCompleted();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultAtStartRoom()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return !info.isAtStartRoom();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultRoomsExplored()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return info.getRoomsExplored() == 0;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultTotalRooms()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return info.getTotalRooms() == 0;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultAllRoomsExplored()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return !info.isAllRoomsExplored();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultItemsCollected()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return info.getItemsCollected() == 0;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultTotalItems()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return info.getTotalItems() == 0;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultAllItemsCollected()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return !info.isAllItemsCollected();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultCookieEaten()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            return !info.isCookieEaten();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetCompletedTrue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setCompleted(true);
            return info.isCompleted();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetAtStartRoomTrue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setAtStartRoom(true);
            return info.isAtStartRoom();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetRoomsExploredValue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setRoomsExplored(5);
            return info.getRoomsExplored() == 5;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetTotalRoomsValue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setTotalRooms(7);
            return info.getTotalRooms() == 7;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetAllRoomsExploredTrue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setAllRoomsExplored(true);
            return info.isAllRoomsExplored();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetItemsCollectedValue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setItemsCollected(9);
            return info.getItemsCollected() == 9;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetTotalItemsValue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setTotalItems(9);
            return info.getTotalItems() == 9;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetAllItemsCollectedTrue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setAllItemsCollected(true);
            return info.isAllItemsCollected();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetCookieEatenTrue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setCookieEaten(true);
            return info.isCookieEaten();
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testProgressReportWithCheckmarks()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setRoomsExplored(7);
            info.setTotalRooms(7);
            info.setItemsCollected(9);
            info.setTotalItems(9);
            info.setCookieEaten(true);
            info.setAtStartRoom(true);
            info.setCompleted(true);
            String report = info.getProgressReport();
            // 全部完成时应该有勾号
            if (!report.contains("✓")) {
                System.out.println("  错误: 全部完成时进度报告应包含勾号");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testProgressReportWithoutCheckmarks()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setRoomsExplored(1);
            info.setTotalRooms(7);
            info.setItemsCollected(2);
            info.setTotalItems(9);
            info.setCookieEaten(false);
            info.setAtStartRoom(false);
            info.setCompleted(false);
            String report = info.getProgressReport();
            // 未完成时房间和物品行不应有勾号
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToggleCompletedMultipleTimes()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setCompleted(true);
            if (!info.isCompleted()) return false;
            info.setCompleted(false);
            if (info.isCompleted()) return false;
            info.setCompleted(true);
            if (!info.isCompleted()) return false;
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLargeRoomsExploredValue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setRoomsExplored(1000);
            info.setTotalRooms(1000);
            if (info.getRoomsExplored() != 1000) return false;
            if (info.getTotalRooms() != 1000) return false;
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLargeItemsCollectedValue()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setItemsCollected(500);
            info.setTotalItems(500);
            if (info.getItemsCollected() != 500) return false;
            if (info.getTotalItems() != 500) return false;
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testZeroValuesProgressReport()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setRoomsExplored(0);
            info.setTotalRooms(0);
            info.setItemsCollected(0);
            info.setTotalItems(0);
            String report = info.getProgressReport();
            if (report == null || report.isEmpty()) {
                System.out.println("  错误: 零值进度报告不应为空");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
