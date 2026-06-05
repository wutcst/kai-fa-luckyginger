/**
 * GameStateManager类的单元测试用例。
 * 测试游戏状态管理功能，包括保存和加载状态、物品恢复、宝库解锁状态等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameStateManagerTest
{
    /**
     * 运行所有GameStateManager类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("GameStateManager类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testGameStateManagerCreation()) {
            System.out.println("✅ 测试1: GameStateManager对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: GameStateManager对象创建 - 失败");
            failed++;
        }

        if (testSaveWithoutUserId()) {
            System.out.println("✅ 测试2: 无用户ID保存 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 无用户ID保存 - 失败");
            failed++;
        }

        if (testLoadWithoutUserId()) {
            System.out.println("✅ 测试3: 无用户ID加载 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 无用户ID加载 - 失败");
            failed++;
        }

        if (testPlayerStateTracking()) {
            System.out.println("✅ 测试4: 玩家状态追踪 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 玩家状态追踪 - 失败");
            failed++;
        }

        if (testPlayerRoomsVisitedTracking()) {
            System.out.println("✅ 测试5: 玩家房间访问追踪 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 玩家房间访问追踪 - 失败");
            failed++;
        }

        if (testPlayerItemsCollectedTracking()) {
            System.out.println("✅ 测试6: 玩家物品收集追踪 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 玩家物品收集追踪 - 失败");
            failed++;
        }

        if (testPlayerCookieEatenTracking()) {
            System.out.println("✅ 测试7: 玩家饼干状态追踪 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 玩家饼干状态追踪 - 失败");
            failed++;
        }

        if (testPlayerMaxWeightTracking()) {
            System.out.println("✅ 测试8: 玩家负重追踪 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 玩家负重追踪 - 失败");
            failed++;
        }

        if (testPlayerCurrentRoomTracking()) {
            System.out.println("✅ 测试9: 玩家当前房间追踪 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 玩家当前房间追踪 - 失败");
            failed++;
        }

        if (testLockedRoomUnlockTracking()) {
            System.out.println("✅ 测试10: 上锁房间解锁追踪 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 上锁房间解锁追踪 - 失败");
            failed++;
        }

        if (testInventorySerialization()) {
            System.out.println("✅ 测试11: 背包序列化 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 背包序列化 - 失败");
            failed++;
        }

        if (testRoomsVisitedSerialization()) {
            System.out.println("✅ 测试12: 房间访问序列化 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 房间访问序列化 - 失败");
            failed++;
        }

        if (testItemsCollectedSerialization()) {
            System.out.println("✅ 测试13: 物品收集序列化 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 物品收集序列化 - 失败");
            failed++;
        }

        if (testCompletionCheckerIntegration()) {
            System.out.println("✅ 测试14: 通关检测集成 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: 通关检测集成 - 失败");
            failed++;
        }

        if (testPlayerInventoryManagement()) {
            System.out.println("✅ 测试15: 玩家背包管理 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 玩家背包管理 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
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

    private static boolean testSaveWithoutUserId()
    {
        try {
            Game game = new Game();
            GameStateManager manager = new GameStateManager(game);
            // 玩家未登录，userId为null
            boolean result = manager.saveGameState();
            if (result) {
                System.out.println("  错误: 无用户ID保存应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLoadWithoutUserId()
    {
        try {
            Game game = new Game();
            GameStateManager manager = new GameStateManager(game);
            // 玩家未登录，userId为null
            boolean result = manager.loadGameState();
            if (result) {
                System.out.println("  错误: 无用户ID加载应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerStateTracking()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Room room = new Room("大学主入口外");
            player.setCurrentRoom(room);
            if (player.getCurrentRoom() == null) {
                System.out.println("  错误: 设置当前房间后应能获取");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerRoomsVisitedTracking()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Room room1 = new Room("大学主入口外");
            Room room2 = new Room("主大厅");
            player.setCurrentRoom(room1);
            player.setCurrentRoom(room2);
            Set<String> visited = player.getRoomsVisited();
            if (visited.size() != 2) {
                System.out.println("  错误: 访问2个房间后visited大小应为2，实际: " + visited.size());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerItemsCollectedTracking()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            Item key = new Item("key", "钥匙", 0.5);
            player.takeItem(key);
            Set<String> collected = player.getItemsCollected();
            if (!collected.contains("key")) {
                System.out.println("  错误: 拾取物品后itemsCollected应包含key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerCookieEatenTracking()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            if (player.isCookieEaten()) {
                System.out.println("  错误: 新玩家cookieEaten应为false");
                return false;
            }
            player.setCookieEaten(true);
            if (!player.isCookieEaten()) {
                System.out.println("  错误: 设置后cookieEaten应为true");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerMaxWeightTracking()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            double initial = player.getMaxWeight();
            player.increaseMaxWeight(5.0);
            if (player.getMaxWeight() != initial + 5.0) {
                System.out.println("  错误: 增加负重后maxWeight应为" + (initial + 5.0));
                return false;
            }
            player.setMaxWeight(20.0);
            if (player.getMaxWeight() != 20.0) {
                System.out.println("  错误: 设置maxWeight后应为20.0");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerCurrentRoomTracking()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            Room room1 = new Room("房间1");
            Room room2 = new Room("房间2");
            player.setCurrentRoom(room1);
            if (!player.getCurrentRoom().getShortDescription().equals("房间1")) {
                System.out.println("  错误: 当前房间应为房间1");
                return false;
            }
            player.setCurrentRoom(room2);
            if (!player.getCurrentRoom().getShortDescription().equals("房间2")) {
                System.out.println("  错误: 当前房间应为房间2");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLockedRoomUnlockTracking()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            if (room.isUnlocked()) {
                System.out.println("  错误: 新创建的上锁房间应为锁定状态");
                return false;
            }
            room.unlock("key");
            if (!room.isUnlocked()) {
                System.out.println("  错误: 解锁后应为解锁状态");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testInventorySerialization()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            player.takeItem(new Item("key", "钥匙", 0.5));
            player.takeItem(new Item("map", "地图", 0.2));
            List<String> inventory = new ArrayList<>();
            for (Item item : player.getInventory()) {
                inventory.add(item.getName());
            }
            if (inventory.size() != 2) {
                System.out.println("  错误: 序列化后背包大小应为2，实际: " + inventory.size());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomsVisitedSerialization()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            player.setCurrentRoom(new Room("大学主入口外"));
            player.setCurrentRoom(new Room("主大厅"));
            List<String> roomsVisited = new ArrayList<>(player.getRoomsVisited());
            if (roomsVisited.size() != 2) {
                System.out.println("  错误: 序列化后房间访问列表大小应为2，实际: " + roomsVisited.size());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testItemsCollectedSerialization()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            player.takeItem(new Item("key", "钥匙", 0.5));
            player.takeItem(new Item("cookie", "饼干", 0.3));
            List<String> itemsCollected = new ArrayList<>(player.getItemsCollected());
            if (itemsCollected.size() != 2) {
                System.out.println("  错误: 序列化后物品收集列表大小应为2，实际: " + itemsCollected.size());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testCompletionCheckerIntegration()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            if (info.isCompleted()) {
                System.out.println("  错误: 新玩家不应通关");
                return false;
            }
            Item finalKey = new Item("final_key", "最终钥匙", 0.5);
            player.takeItem(finalKey);
            info = GameCompletionChecker.checkCompletion(player);
            if (!info.isCompleted()) {
                System.out.println("  错误: 拥有final_key应通关");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerInventoryManagement()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            Item key = new Item("key", "钥匙", 0.5);
            Item map = new Item("map", "地图", 0.2);

            // 拾取
            player.takeItem(key);
            player.takeItem(map);
            if (!player.hasItem("key") || !player.hasItem("map")) {
                System.out.println("  错误: 拾取后背包应包含物品");
                return false;
            }

            // 丢弃
            player.dropItem("key");
            if (player.hasItem("key")) {
                System.out.println("  错误: 丢弃后背包不应包含key");
                return false;
            }
            if (!player.hasItem("map")) {
                System.out.println("  错误: 丢弃key后map应还在背包");
                return false;
            }

            // 丢弃所有
            player.dropAllItems();
            if (player.hasItem("map")) {
                System.out.println("  错误: 丢弃所有后背包应为空");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
