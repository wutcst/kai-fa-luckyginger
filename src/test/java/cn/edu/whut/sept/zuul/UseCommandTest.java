package cn.edu.whut.sept.zuul;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests for UseCommand key item behavior.
 */
public class UseCommandTest
{
    public static boolean runAllTests()
    {
        int passed = 0;
        int failed = 0;

        if (testUseKeyUnlocksAdjacentLockedRoom()) passed++; else failed++;
        if (testUseWrongKeyDoesNotUnlockRoom()) passed++; else failed++;
        if (testUseKeyOnAlreadyUnlockedCurrentRoomKeepsUnlocked()) passed++; else failed++;
        if (testUseMapDoesNotConsumeItem()) passed++; else failed++;
        if (testUseFoodConsumesItemAndIncreasesWeightForCookie()) passed++; else failed++;
        if (testUseToolKeepsItemInInventory()) passed++; else failed++;
        if (testUseNormalItemDoesNotConsumeItem()) passed++; else failed++;
        if (testUseMissingInventoryItemDoesNothing()) passed++; else failed++;
        if (testUseWithoutItemNameDoesNothing()) passed++; else failed++;

        printSummary("UseCommandTest", passed, failed);
        return failed == 0;
    }

    private static boolean testUseKeyUnlocksAdjacentLockedRoom()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room currentRoom = new Room("走廊");
        LockedRoom lockedRoom = new LockedRoom("宝库", "key");
        currentRoom.setExit("north", lockedRoom);
        player.setCurrentRoom(currentRoom);
        Item key = new Item("key", "钥匙", 0.1, "KEY", "解锁房间");
        player.takeItem(key);

        boolean result = executeSilently(new UseCommand(), new Command("use", "key"), game);

        return assertFalse(result, "use 命令不应结束游戏")
                && assertTrue(lockedRoom.isUnlocked(), "正确钥匙应解锁相邻上锁房间")
                && assertTrue(player.hasItem("key"), "钥匙使用后不应被消耗");
    }

    private static boolean testUseWrongKeyDoesNotUnlockRoom()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room currentRoom = new Room("走廊");
        LockedRoom lockedRoom = new LockedRoom("宝库", "silver_key");
        currentRoom.setExit("east", lockedRoom);
        player.setCurrentRoom(currentRoom);
        player.takeItem(new Item("key", "普通钥匙", 0.1, "KEY", "尝试解锁"));

        executeSilently(new UseCommand(), new Command("use", "key"), game);

        return assertFalse(lockedRoom.isUnlocked(), "错误钥匙不应解锁房间")
                && assertTrue(player.hasItem("key"), "错误钥匙使用失败后仍应留在背包");
    }

    private static boolean testUseKeyOnAlreadyUnlockedCurrentRoomKeepsUnlocked()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        LockedRoom lockedRoom = new LockedRoom("宝库", "key");
        lockedRoom.unlock("key");
        player.setCurrentRoom(lockedRoom);
        player.takeItem(new Item("key", "钥匙", 0.1, "KEY", "解锁房间"));

        executeSilently(new UseCommand(), new Command("use", "key"), game);

        return assertTrue(lockedRoom.isUnlocked(), "已解锁房间再次使用钥匙后应保持解锁")
                && assertTrue(player.hasItem("key"), "钥匙不应因重复使用被消耗");
    }

    private static boolean testUseMapDoesNotConsumeItem()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room room = new Room("大厅");
        room.setExit("north", new Room("实验室"));
        player.setCurrentRoom(room);
        Item map = new Item("map", "校园地图", 0.2, "MAP", "显示当前位置");
        player.takeItem(map);

        executeSilently(new UseCommand(), new Command("use", "map"), game);

        return assertSame(map, player.getItem("map"), "地图使用后应留在背包")
                && assertDoubleEquals(0.2, player.getTotalWeight(), 0.0001, "地图使用不应改变背包重量");
    }

    private static boolean testUseFoodConsumesItemAndIncreasesWeightForCookie()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        double initialMaxWeight = player.getMaxWeight();
        Item cookie = new Item("cookie", "魔法饼干", 0.1, "FOOD", "增加负重");
        player.takeItem(cookie);

        executeSilently(new UseCommand(), new Command("use", "cookie"), game);

        return assertFalse(player.hasItem("cookie"), "食物使用后应从背包移除")
                && assertDoubleEquals(initialMaxWeight + 2.0, player.getMaxWeight(), 0.0001,
                        "使用 cookie 类型食物应增加 2kg 最大负重");
    }

    private static boolean testUseToolKeepsItemInInventory()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Item box = new Item("box", "密码箱", 1.0, "TOOL", "需要密码才能打开");
        player.takeItem(box);

        executeSilently(new UseCommand(), new Command("use", "box"), game);

        return assertSame(box, player.getItem("box"), "工具使用后当前实现不应消耗物品");
    }

    private static boolean testUseNormalItemDoesNotConsumeItem()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Item computer = new Item("computer", "电脑", 2.5);
        player.takeItem(computer);

        executeSilently(new UseCommand(), new Command("use", "computer"), game);

        return assertSame(computer, player.getItem("computer"), "不可使用物品尝试 use 后应仍在背包")
                && assertFalse(computer.isUsable(), "普通物品应保持不可使用状态");
    }

    private static boolean testUseMissingInventoryItemDoesNothing()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        int initialInventorySize = player.getInventory().size();

        executeSilently(new UseCommand(), new Command("use", "missing_item"), game);

        return assertEquals(initialInventorySize, player.getInventory().size(), "使用不存在物品不应改变背包");
    }

    private static boolean testUseWithoutItemNameDoesNothing()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Item map = new Item("map", "地图", 0.2, "MAP", "查看位置");
        player.takeItem(map);

        boolean result = executeSilently(new UseCommand(), new Command("use", null), game);

        return assertFalse(result, "缺少物品名的 use 命令不应结束游戏")
                && assertSame(map, player.getItem("map"), "缺少物品名时背包不应变化");
    }

    private static boolean executeSilently(UseCommand useCommand, Command command, Game game)
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        try {
            return useCommand.execute(command, game);
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
