package cn.edu.whut.sept.zuul;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests for TakeCommand item pickup behavior.
 */
public class TakeCommandTest
{
    public static boolean runAllTests()
    {
        int passed = 0;
        int failed = 0;

        if (testTakeExistingItemMovesItToInventory()) passed++; else failed++;
        if (testTakeIsCaseInsensitiveThroughRoomLookup()) passed++; else failed++;
        if (testTakeMissingItemDoesNotChangeInventory()) passed++; else failed++;
        if (testTakeOverweightItemLeavesItemInRoom()) passed++; else failed++;
        if (testTakeWithoutItemNameDoesNothing()) passed++; else failed++;
        if (testTakeCommandAlwaysReturnsFalse()) passed++; else failed++;

        printSummary("TakeCommandTest", passed, failed);
        return failed == 0;
    }

    private static boolean testTakeExistingItemMovesItToInventory()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room room = player.getCurrentRoom();
        Item key = new Item("test_key", "测试钥匙", 0.1, "KEY", "开门");
        room.addItem(key);

        executeSilently(new TakeCommand(), new Command("take", "test_key"), game);

        return assertNull(room.getItem("test_key"), "拾取后物品应从房间移除")
                && assertTrue(player.hasItem("test_key"), "拾取后物品应进入玩家背包")
                && assertSame(key, player.getItem("test_key"), "背包中的物品应是原对象");
    }

    private static boolean testTakeIsCaseInsensitiveThroughRoomLookup()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room room = player.getCurrentRoom();
        Item map = new Item("Test_Map", "测试地图", 0.2, "MAP", "查看地图");
        room.addItem(map);

        executeSilently(new TakeCommand(), new Command("take", "test_map"), game);

        return assertNull(room.getItem("TEST_MAP"), "大小写不同也应从房间移除物品")
                && assertSame(map, player.getItem("TEST_MAP"), "大小写不同也应拾取到背包");
    }

    private static boolean testTakeMissingItemDoesNotChangeInventory()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        int initialInventorySize = player.getInventory().size();

        executeSilently(new TakeCommand(), new Command("take", "missing_item"), game);

        return assertEquals(initialInventorySize, player.getInventory().size(), "拾取不存在物品不应改变背包")
                && assertFalse(player.hasItem("missing_item"), "不存在物品不应进入背包");
    }

    private static boolean testTakeOverweightItemLeavesItemInRoom()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room room = player.getCurrentRoom();
        player.setMaxWeight(1.0);
        Item heavy = new Item("heavy_box", "很重的箱子", 2.0);
        room.addItem(heavy);

        executeSilently(new TakeCommand(), new Command("take", "heavy_box"), game);

        return assertSame(heavy, room.getItem("heavy_box"), "超重物品应留在房间")
                && assertFalse(player.hasItem("heavy_box"), "超重物品不应进入背包")
                && assertDoubleEquals(0.0, player.getTotalWeight(), 0.0001, "超重拾取失败不应增加负重");
    }

    private static boolean testTakeWithoutItemNameDoesNothing()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room room = player.getCurrentRoom();
        Item item = new Item("coin", "硬币", 0.1);
        room.addItem(item);
        int initialInventorySize = player.getInventory().size();

        executeSilently(new TakeCommand(), new Command("take", null), game);

        return assertSame(item, room.getItem("coin"), "缺少物品名时房间物品不应变化")
                && assertEquals(initialInventorySize, player.getInventory().size(), "缺少物品名时背包不应变化");
    }

    private static boolean testTakeCommandAlwaysReturnsFalse()
    {
        Game game = new Game();
        boolean result = executeSilently(new TakeCommand(), new Command("take", "missing_item"), game);

        return assertFalse(result, "take 命令不应结束游戏");
    }

    private static boolean executeSilently(TakeCommand takeCommand, Command command, Game game)
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        try {
            return takeCommand.execute(command, game);
        } finally {
            System.setOut(originalOut);
        }
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
