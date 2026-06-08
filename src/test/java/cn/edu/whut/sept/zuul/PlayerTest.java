package cn.edu.whut.sept.zuul;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for Player location, inventory, carrying weight, and progress state.
 */
public class PlayerTest
{
    public static boolean runAllTests()
    {
        int passed = 0;
        int failed = 0;

        if (testPlayerCreation()) passed++; else failed++;
        if (testCurrentRoomChangeRecordsVisit()) passed++; else failed++;
        if (testTakeItemAddsToInventory()) passed++; else failed++;
        if (testTakeItemRejectsNull()) passed++; else failed++;
        if (testTakeItemRejectsOverweightItem()) passed++; else failed++;
        if (testDropItemRemovesFromInventory()) passed++; else failed++;
        if (testInventoryLookupIsCaseInsensitive()) passed++; else failed++;
        if (testDropAllItemsClearsInventory()) passed++; else failed++;
        if (testIncreaseMaxWeightAndCanCarry()) passed++; else failed++;
        if (testCookieStateAndEatCookie()) passed++; else failed++;
        if (testProgressSetsAreDefensiveCopies()) passed++; else failed++;

        printSummary("PlayerTest", passed, failed);
        return failed == 0;
    }

    private static boolean testPlayerCreation()
    {
        Player player = new Player("Alice", 5.0);

        return assertEquals("Alice", player.getName(), "玩家姓名不正确")
                && assertDoubleEquals(5.0, player.getMaxWeight(), 0.0001, "玩家最大负重不正确")
                && assertDoubleEquals(0.0, player.getTotalWeight(), 0.0001, "新玩家总负重应为 0")
                && assertFalse(player.isCookieEaten(), "新玩家不应已吃掉饼干");
    }

    private static boolean testCurrentRoomChangeRecordsVisit()
    {
        Player player = new Player("Bob", 10.0);
        Room start = new Room("起点");
        Room lab = new Room("实验室");

        player.setCurrentRoom(start);
        player.setCurrentRoom(lab);

        return assertSame(lab, player.getCurrentRoom(), "玩家当前位置应切换到实验室")
                && assertTrue(player.getRoomsVisited().contains("起点"), "访问记录应包含起点")
                && assertTrue(player.getRoomsVisited().contains("实验室"), "访问记录应包含实验室");
    }

    private static boolean testTakeItemAddsToInventory()
    {
        Player player = new Player("Carol", 10.0);
        Item key = new Item("Key", "旧钥匙", 0.1);

        boolean taken = player.takeItem(key);

        return assertTrue(taken, "轻量物品应该能拾取")
                && assertTrue(player.hasItem("key"), "背包应包含拾取的物品")
                && assertSame(key, player.getItem("KEY"), "按名称获取物品应返回同一对象")
                && assertTrue(player.getItemsCollected().contains("key"), "收集记录应包含物品名的小写形式");
    }

    private static boolean testTakeItemRejectsNull()
    {
        Player player = new Player("Dan", 10.0);

        return assertFalse(player.takeItem(null), "拾取 null 应失败")
                && assertEquals(0, player.getInventory().size(), "拾取 null 后背包应为空");
    }

    private static boolean testTakeItemRejectsOverweightItem()
    {
        Player player = new Player("Eve", 1.0);
        Item anvil = new Item("anvil", "很重的铁砧", 2.0);

        return assertFalse(player.takeItem(anvil), "超过负重的物品不应被拾取")
                && assertFalse(player.hasItem("anvil"), "超重物品不应进入背包")
                && assertDoubleEquals(0.0, player.getTotalWeight(), 0.0001, "失败拾取不应增加负重");
    }

    private static boolean testDropItemRemovesFromInventory()
    {
        Player player = new Player("Frank", 10.0);
        Item map = new Item("map", "地图", 0.2);
        player.takeItem(map);

        Item dropped = player.dropItem("MAP");

        return assertSame(map, dropped, "dropItem 应返回被丢弃的物品")
                && assertFalse(player.hasItem("map"), "丢弃后背包不应再包含该物品")
                && assertNull(player.dropItem("map"), "重复丢弃不存在物品应返回 null");
    }

    private static boolean testInventoryLookupIsCaseInsensitive()
    {
        Player player = new Player("Grace", 10.0);
        Item cable = new Item("Cable", "数据线", 0.1);
        player.takeItem(cable);

        return assertTrue(player.hasItem("cable"), "小写名称应识别物品")
                && assertTrue(player.hasItem("CABLE"), "大写名称应识别物品")
                && assertSame(cable, player.getItem("CaBlE"), "混合大小写名称应返回物品");
    }

    private static boolean testDropAllItemsClearsInventory()
    {
        Player player = new Player("Helen", 10.0);
        player.takeItem(new Item("key", "钥匙", 0.1));
        player.takeItem(new Item("map", "地图", 0.2));

        int droppedCount = player.dropAllItems().size();

        return assertEquals(2, droppedCount, "dropAllItems 应返回所有被丢弃物品")
                && assertEquals(0, player.getInventory().size(), "dropAllItems 后背包应为空");
    }

    private static boolean testIncreaseMaxWeightAndCanCarry()
    {
        Player player = new Player("Ivan", 1.0);

        boolean beforeIncrease = player.canCarry(2.0);
        player.increaseMaxWeight(2.0);

        return assertFalse(beforeIncrease, "增加负重前不应能携带 2kg")
                && assertDoubleEquals(3.0, player.getMaxWeight(), 0.0001, "最大负重应增加")
                && assertTrue(player.canCarry(2.0), "增加负重后应能携带 2kg");
    }

    private static boolean testCookieStateAndEatCookie()
    {
        Player player = new Player("Jane", 10.0);
        player.takeItem(new Item("cookie", "魔法饼干", 0.1));

        player.eatCookie();

        return assertTrue(player.isCookieEaten(), "eatCookie 后应标记饼干已吃")
                && assertFalse(player.hasItem("cookie"), "eatCookie 应从背包移除 cookie");
    }

    private static boolean testProgressSetsAreDefensiveCopies()
    {
        Player player = new Player("Kate", 10.0);
        Set<String> rooms = new HashSet<>();
        rooms.add("起点");
        player.setRoomsVisited(rooms);

        Set<String> items = new HashSet<>();
        items.add("key");
        player.setItemsCollected(items);

        rooms.add("外部修改");
        items.add("external");
        Set<String> returnedRooms = player.getRoomsVisited();
        Set<String> returnedItems = player.getItemsCollected();
        returnedRooms.add("再次外部修改");
        returnedItems.add("again");

        return assertFalse(player.getRoomsVisited().contains("外部修改"), "设置后修改原集合不应影响玩家房间记录")
                && assertFalse(player.getItemsCollected().contains("external"), "设置后修改原集合不应影响玩家物品记录")
                && assertFalse(player.getRoomsVisited().contains("再次外部修改"), "修改返回集合不应影响玩家房间记录")
                && assertFalse(player.getItemsCollected().contains("again"), "修改返回集合不应影响玩家物品记录");
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

    private static boolean assertSame(Object expected, Object actual, String message)
    {
        return assertTrue(expected == actual, message + "，期望同一对象");
    }

    private static boolean assertEquals(Object expected, Object actual, String message)
    {
        boolean matches = expected == null ? actual == null : expected.equals(actual);
        if (!matches) {
            System.out.println("失败: " + message + "，期望: " + expected + "，实际: " + actual);
        }
        return matches;
    }

    private static boolean assertDoubleEquals(double expected, double actual, double delta, String message)
    {
        boolean matches = Math.abs(expected - actual) <= delta;
        if (!matches) {
            System.out.println("失败: " + message + "，期望: " + expected + "，实际: " + actual);
        }
        return matches;
    }
}
