package cn.edu.whut.sept.zuul;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests for GoCommand movement behavior.
 */
public class GoCommandTest
{
    public static boolean runAllTests()
    {
        int passed = 0;
        int failed = 0;

        if (testValidDirectionMovesPlayer()) passed++; else failed++;
        if (testInvalidDirectionDoesNotMovePlayer()) passed++; else failed++;
        if (testMissingDirectionDoesNotMovePlayer()) passed++; else failed++;
        if (testMoveRecordsPreviousRoom()) passed++; else failed++;
        if (testLockedRoomBlocksMovement()) passed++; else failed++;
        if (testUnlockedRoomAllowsMovement()) passed++; else failed++;

        printSummary("GoCommandTest", passed, failed);
        return failed == 0;
    }

    private static boolean testValidDirectionMovesPlayer()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room start = player.getCurrentRoom();
        Room target = new Room("测试房间");
        start.setExit("east", target);

        boolean result = executeSilently(new GoCommand(), new Command("go", "east"), game);

        return assertFalse(result, "go 命令不应结束游戏")
                && assertSame(target, player.getCurrentRoom(), "有效方向应移动玩家到目标房间");
    }

    private static boolean testInvalidDirectionDoesNotMovePlayer()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room start = player.getCurrentRoom();

        boolean result = executeSilently(new GoCommand(), new Command("go", "west"), game);

        return assertFalse(result, "无效方向 go 命令不应结束游戏")
                && assertSame(start, player.getCurrentRoom(), "无效方向不应改变玩家当前位置");
    }

    private static boolean testMissingDirectionDoesNotMovePlayer()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room start = player.getCurrentRoom();

        boolean result = executeSilently(new GoCommand(), new Command("go", null), game);

        return assertFalse(result, "缺少方向的 go 命令不应结束游戏")
                && assertSame(start, player.getCurrentRoom(), "缺少方向不应改变玩家当前位置");
    }

    private static boolean testMoveRecordsPreviousRoom()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room start = player.getCurrentRoom();
        Room next = new Room("下一间房");
        start.setExit("north", next);

        executeSilently(new GoCommand(), new Command("go", "north"), game);
        Room previous = game.getPreviousRoom();

        return assertSame(next, player.getCurrentRoom(), "玩家应移动到下一间房")
                && assertSame(start, previous, "移动后历史记录应包含原房间");
    }

    private static boolean testLockedRoomBlocksMovement()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room start = player.getCurrentRoom();
        LockedRoom lockedRoom = new LockedRoom("上锁宝库", "key");
        start.setExit("north", lockedRoom);

        executeSilently(new GoCommand(), new Command("go", "north"), game);

        return assertSame(start, player.getCurrentRoom(), "未解锁房间不应允许进入")
                && assertFalse(lockedRoom.isUnlocked(), "go 命令不应自动解锁房间");
    }

    private static boolean testUnlockedRoomAllowsMovement()
    {
        Game game = new Game();
        Player player = game.getPlayer();
        Room start = player.getCurrentRoom();
        LockedRoom lockedRoom = new LockedRoom("上锁宝库", "key");
        lockedRoom.unlock("key");
        start.setExit("north", lockedRoom);

        executeSilently(new GoCommand(), new Command("go", "north"), game);

        return assertSame(lockedRoom, player.getCurrentRoom(), "已解锁房间应允许进入")
                && assertTrue(player.getRoomsVisited().contains("上锁宝库"), "进入房间后应记录访问状态");
    }

    private static boolean executeSilently(GoCommand goCommand, Command command, Game game)
    {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        try {
            return goCommand.execute(command, game);
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
}
