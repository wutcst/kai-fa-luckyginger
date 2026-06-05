/**
 * GameCompletionChecker类的单元测试用例。
 * 测试游戏通关检测逻辑，包括房间探索、物品收集、饼干状态、综合通关判断等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.HashSet;
import java.util.Set;

public class GameCompletionCheckerTest
{
    /**
     * 运行所有GameCompletionChecker类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("GameCompletionChecker类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testCompletionInfoCreation()) {
            System.out.println("✅ 测试1: CompletionInfo对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: CompletionInfo对象创建 - 失败");
            failed++;
        }

        if (testCompletionInfoDefaultValues()) {
            System.out.println("✅ 测试2: CompletionInfo默认值 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: CompletionInfo默认值 - 失败");
            failed++;
        }

        if (testCompletionInfoSettersAndGetters()) {
            System.out.println("✅ 测试3: CompletionInfo设置和获取 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: CompletionInfo设置和获取 - 失败");
            failed++;
        }

        if (testCheckCompletionWithNewPlayer()) {
            System.out.println("✅ 测试4: 新玩家通关检测 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 新玩家通关检测 - 失败");
            failed++;
        }

        if (testCheckCompletionWithFinalKey()) {
            System.out.println("✅ 测试5: 拥有final_key的通关检测 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 拥有final_key的通关检测 - 失败");
            failed++;
        }

        if (testCheckCompletionWithoutFinalKey()) {
            System.out.println("✅ 测试6: 没有final_key的通关检测 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 没有final_key的通关检测 - 失败");
            failed++;
        }

        if (testCheckCompletionAtStartRoom()) {
            System.out.println("✅ 测试7: 在起始房间的检测 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 在起始房间的检测 - 失败");
            failed++;
        }

        if (testCheckCompletionNotAtStartRoom()) {
            System.out.println("✅ 测试8: 不在起始房间的检测 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 不在起始房间的检测 - 失败");
            failed++;
        }

        if (testCheckCompletionRoomsExplored()) {
            System.out.println("✅ 测试9: 房间探索计数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 房间探索计数 - 失败");
            failed++;
        }

        if (testCheckCompletionItemsCollected()) {
            System.out.println("✅ 测试10: 物品收集计数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 物品收集计数 - 失败");
            failed++;
        }

        if (testCheckCompletionCookieEaten()) {
            System.out.println("✅ 测试11: 饼干状态检测 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 饼干状态检测 - 失败");
            failed++;
        }

        if (testProgressReportFormat()) {
            System.out.println("✅ 测试12: 进度报告格式 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 进度报告格式 - 失败");
            failed++;
        }

        if (testProgressReportCompleted()) {
            System.out.println("✅ 测试13: 通关进度报告 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 通关进度报告 - 失败");
            failed++;
        }

        if (testProgressReportNotCompleted()) {
            System.out.println("✅ 测试14: 未通关进度报告 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: 未通关进度报告 - 失败");
            failed++;
        }

        if (testAllRoomsExplored()) {
            System.out.println("✅ 测试15: 全部房间探索完成 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 全部房间探索完成 - 失败");
            failed++;
        }

        if (testAllItemsCollected()) {
            System.out.println("✅ 测试16: 全部物品收集完成 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试16: 全部物品收集完成 - 失败");
            failed++;
        }

        if (testCompletionInfoBooleans()) {
            System.out.println("✅ 测试17: CompletionInfo布尔属性 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试17: CompletionInfo布尔属性 - 失败");
            failed++;
        }

        if (testCheckCompletionWithNullCurrentRoom()) {
            System.out.println("✅ 测试18: 当前房间为null的检测 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试18: 当前房间为null的检测 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testCompletionInfoCreation()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            if (info == null) {
                System.out.println("  错误: 无法创建CompletionInfo对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCompletionInfoDefaultValues()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            if (info.isCompleted()) {
                System.out.println("  错误: 默认completed应该为false");
                return false;
            }
            if (info.isAtStartRoom()) {
                System.out.println("  错误: 默认atStartRoom应该为false");
                return false;
            }
            if (info.getRoomsExplored() != 0) {
                System.out.println("  错误: 默认roomsExplored应该为0");
                return false;
            }
            if (info.getTotalRooms() != 0) {
                System.out.println("  错误: 默认totalRooms应该为0");
                return false;
            }
            if (info.isAllRoomsExplored()) {
                System.out.println("  错误: 默认allRoomsExplored应该为false");
                return false;
            }
            if (info.getItemsCollected() != 0) {
                System.out.println("  错误: 默认itemsCollected应该为0");
                return false;
            }
            if (info.getTotalItems() != 0) {
                System.out.println("  错误: 默认totalItems应该为0");
                return false;
            }
            if (info.isAllItemsCollected()) {
                System.out.println("  错误: 默认allItemsCollected应该为false");
                return false;
            }
            if (info.isCookieEaten()) {
                System.out.println("  错误: 默认cookieEaten应该为false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCompletionInfoSettersAndGetters()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();

            info.setCompleted(true);
            if (!info.isCompleted()) {
                System.out.println("  错误: setCompleted(true)后isCompleted()应返回true");
                return false;
            }

            info.setAtStartRoom(true);
            if (!info.isAtStartRoom()) {
                System.out.println("  错误: setAtStartRoom(true)后isAtStartRoom()应返回true");
                return false;
            }

            info.setRoomsExplored(5);
            if (info.getRoomsExplored() != 5) {
                System.out.println("  错误: setRoomsExplored(5)后getRoomsExplored()应返回5");
                return false;
            }

            info.setTotalRooms(7);
            if (info.getTotalRooms() != 7) {
                System.out.println("  错误: setTotalRooms(7)后getTotalRooms()应返回7");
                return false;
            }

            info.setAllRoomsExplored(true);
            if (!info.isAllRoomsExplored()) {
                System.out.println("  错误: setAllRoomsExplored(true)后应返回true");
                return false;
            }

            info.setItemsCollected(8);
            if (info.getItemsCollected() != 8) {
                System.out.println("  错误: setItemsCollected(8)后应返回8");
                return false;
            }

            info.setTotalItems(9);
            if (info.getTotalItems() != 9) {
                System.out.println("  错误: setTotalItems(9)后应返回9");
                return false;
            }

            info.setAllItemsCollected(true);
            if (!info.isAllItemsCollected()) {
                System.out.println("  错误: setAllItemsCollected(true)后应返回true");
                return false;
            }

            info.setCookieEaten(true);
            if (!info.isCookieEaten()) {
                System.out.println("  错误: setCookieEaten(true)后应返回true");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionWithNewPlayer()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (info.isCompleted()) {
                System.out.println("  错误: 新玩家不应该通关");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionWithFinalKey()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Item finalKey = new Item("final_key", "最终钥匙", 0.5);
            player.takeItem(finalKey);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (!info.isCompleted()) {
                System.out.println("  错误: 拥有final_key应该通关");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionWithoutFinalKey()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Item key = new Item("key", "普通钥匙", 0.5);
            player.takeItem(key);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (info.isCompleted()) {
                System.out.println("  错误: 没有final_key不应该通关");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionAtStartRoom()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Room startRoom = new Room("大学主入口外");
            player.setCurrentRoom(startRoom);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (!info.isAtStartRoom()) {
                System.out.println("  错误: 在起始房间应该检测到atStartRoom为true");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionNotAtStartRoom()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Room otherRoom = new Room("图书馆");
            player.setCurrentRoom(otherRoom);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (info.isAtStartRoom()) {
                System.out.println("  错误: 不在起始房间应该检测到atStartRoom为false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionRoomsExplored()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Set<String> roomsVisited = new HashSet<>();
            roomsVisited.add("大学主入口外");
            roomsVisited.add("主大厅");
            roomsVisited.add("图书馆");
            player.setRoomsVisited(roomsVisited);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (info.getRoomsExplored() != 3) {
                System.out.println("  错误: 探索3个房间后roomsExplored应为3，实际: " + info.getRoomsExplored());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionItemsCollected()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            Item key = new Item("key", "钥匙", 0.5);
            Item cookie = new Item("cookie", "饼干", 0.3);
            Item map = new Item("map", "地图", 0.2);
            player.takeItem(key);
            player.takeItem(cookie);
            player.takeItem(map);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (info.getItemsCollected() < 3) {
                System.out.println("  错误: 收集3个物品后itemsCollected应至少为3，实际: " + info.getItemsCollected());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionCookieEaten()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            player.setCookieEaten(true);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (!info.isCookieEaten()) {
                System.out.println("  错误: 吃掉饼干后cookieEaten应为true");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testProgressReportFormat()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setRoomsExplored(3);
            info.setTotalRooms(7);
            info.setItemsCollected(4);
            info.setTotalItems(9);
            info.setCookieEaten(false);
            info.setAtStartRoom(false);
            info.setCompleted(false);
            String report = info.getProgressReport();
            if (report == null || report.isEmpty()) {
                System.out.println("  错误: 进度报告不应为空");
                return false;
            }
            if (!report.contains("游戏进度")) {
                System.out.println("  错误: 进度报告应包含'游戏进度'标题");
                return false;
            }
            if (!report.contains("3/7")) {
                System.out.println("  错误: 进度报告应包含房间探索比例3/7");
                return false;
            }
            if (!report.contains("4/9")) {
                System.out.println("  错误: 进度报告应包含物品收集比例4/9");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testProgressReportCompleted()
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
            if (!report.contains("恭喜")) {
                System.out.println("  错误: 通关报告应包含'恭喜'");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testProgressReportNotCompleted()
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
            if (!report.contains("继续努力")) {
                System.out.println("  错误: 未通关报告应包含'继续努力'");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testAllRoomsExplored()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Set<String> roomsVisited = new HashSet<>();
            roomsVisited.add("大学主入口外");
            roomsVisited.add("主大厅");
            roomsVisited.add("室外花园");
            roomsVisited.add("图书馆");
            roomsVisited.add("计算机实验室");
            roomsVisited.add("上锁的宝库");
            roomsVisited.add("一个神秘的传输房间");
            player.setRoomsVisited(roomsVisited);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (!info.isAllRoomsExplored()) {
                System.out.println("  错误: 探索所有房间后allRoomsExplored应为true");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testAllItemsCollected()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            Item key = new Item("key", "钥匙", 0.5);
            Item cookie = new Item("cookie", "饼干", 0.3);
            Item box = new Item("box", "箱子", 2.0);
            Item map = new Item("map", "地图", 0.2);
            Item notebook = new Item("notebook", "笔记本", 0.5);
            Item computer = new Item("computer", "电脑", 3.0);
            Item cable = new Item("cable", "数据线", 0.3);
            Item treasure = new Item("treasure", "宝藏", 5.0);
            Item finalKey = new Item("final_key", "最终钥匙", 0.5);
            player.takeItem(key);
            player.takeItem(cookie);
            player.takeItem(box);
            player.takeItem(map);
            player.takeItem(notebook);
            player.takeItem(computer);
            player.takeItem(cable);
            player.takeItem(treasure);
            player.takeItem(finalKey);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (!info.isAllItemsCollected()) {
                System.out.println("  错误: 收集所有物品后allItemsCollected应为true");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCompletionInfoBooleans()
    {
        try {
            GameCompletionChecker.CompletionInfo info = new GameCompletionChecker.CompletionInfo();
            info.setCompleted(true);
            info.setAtStartRoom(true);
            info.setAllRoomsExplored(true);
            info.setAllItemsCollected(true);
            info.setCookieEaten(true);
            if (!info.isCompleted() || !info.isAtStartRoom() || !info.isAllRoomsExplored()
                || !info.isAllItemsCollected() || !info.isCookieEaten()) {
                System.out.println("  错误: 所有布尔属性设置为true后应返回true");
                return false;
            }
            info.setCompleted(false);
            info.setAtStartRoom(false);
            info.setAllRoomsExplored(false);
            info.setAllItemsCollected(false);
            info.setCookieEaten(false);
            if (info.isCompleted() || info.isAtStartRoom() || info.isAllRoomsExplored()
                || info.isAllItemsCollected() || info.isCookieEaten()) {
                System.out.println("  错误: 所有布尔属性设置为false后应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCheckCompletionWithNullCurrentRoom()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            // 不设置currentRoom，保持为null
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (info.isAtStartRoom()) {
                System.out.println("  错误: currentRoom为null时atStartRoom应为false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
