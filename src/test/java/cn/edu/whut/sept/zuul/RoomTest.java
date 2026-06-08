package cn.edu.whut.sept.zuul;

/**
 * Tests for Room exit management, item storage, and room descriptions.
 */
public class RoomTest
{
    public static boolean runAllTests()
    {
        int passed = 0;
        int failed = 0;

        if (testRoomCreation()) {
            passed++;
        } else {
            failed++;
        }

        if (testSetAndGetExits()) {
            passed++;
        } else {
            failed++;
        }

        if (testMissingExitReturnsNull()) {
            passed++;
        } else {
            failed++;
        }

        if (testHasExitAndDirectExit()) {
            passed++;
        } else {
            failed++;
        }

        if (testAddGetAndRemoveItem()) {
            passed++;
        } else {
            failed++;
        }

        if (testItemLookupIsCaseInsensitive()) {
            passed++;
        } else {
            failed++;
        }

        if (testRoomTotalWeight()) {
            passed++;
        } else {
            failed++;
        }

        if (testLongDescriptionContainsExitsAndItems()) {
            passed++;
        } else {
            failed++;
        }

        printSummary("RoomTest", passed, failed);
        return failed == 0;
    }

    private static boolean testRoomCreation()
    {
        Room room = new Room("测试房间");

        return assertEquals("测试房间", room.getShortDescription(), "房间简短描述不正确")
                && assertEquals(0, room.getItems().size(), "新房间不应该包含物品");
    }

    private static boolean testSetAndGetExits()
    {
        Room hall = new Room("大厅");
        Room library = new Room("图书馆");
        Room lab = new Room("实验室");

        hall.setExit("east", library);
        hall.setExit("north", lab);

        return assertSame(library, hall.getExit("east"), "east 出口应该通向图书馆")
                && assertSame(lab, hall.getExit("north"), "north 出口应该通向实验室");
    }

    private static boolean testMissingExitReturnsNull()
    {
        Room room = new Room("单向房间");
        room.setExit("south", new Room("南侧房间"));

        return assertNull(room.getExit("west"), "不存在的出口应该返回 null");
    }

    private static boolean testHasExitAndDirectExit()
    {
        Room room = new Room("有锁门的房间");
        LockedRoom lockedRoom = new LockedRoom("宝库", "key");

        room.setExit("north", lockedRoom);

        return assertTrue(room.hasExit("north"), "hasExit 应该识别已设置的出口")
                && assertFalse(room.hasExit("south"), "hasExit 不应该误报未设置出口")
                && assertSame(lockedRoom, room.getExitDirectly("north"), "getExitDirectly 应返回原始出口房间");
    }

    private static boolean testAddGetAndRemoveItem()
    {
        Room room = new Room("储藏室");
        Item key = new Item("key", "一把钥匙", 0.1, "KEY", "可以开门");

        room.addItem(key);
        Item found = room.getItem("key");
        Item removed = room.removeItem("key");

        return assertSame(key, found, "getItem 应返回添加的物品")
                && assertSame(key, removed, "removeItem 应返回被移除的物品")
                && assertNull(room.getItem("key"), "移除后不应再能找到该物品")
                && assertEquals(0, room.getItems().size(), "移除后房间物品数量应为 0");
    }

    private static boolean testItemLookupIsCaseInsensitive()
    {
        Room room = new Room("箱子房");
        Item map = new Item("Map", "校园地图", 0.2, "MAP", "显示地图");

        room.addItem(map);

        return assertSame(map, room.getItem("map"), "小写名称应能查到物品")
                && assertSame(map, room.getItem("MAP"), "大写名称应能查到物品")
                && assertSame(map, room.removeItem("mAp"), "混合大小写名称应能移除物品");
    }

    private static boolean testRoomTotalWeight()
    {
        Room room = new Room("物品房");
        room.addItem(new Item("book", "书", 0.5));
        room.addItem(new Item("computer", "电脑", 2.5));
        room.addItem(new Item("cable", "数据线", 0.1));

        return assertDoubleEquals(3.1, room.getTotalWeight(), 0.0001, "房间物品总重量不正确");
    }

    private static boolean testLongDescriptionContainsExitsAndItems()
    {
        Room room = new Room("大厅");
        room.setExit("north", new Room("实验室"));
        room.addItem(new Item("key", "旧钥匙", 0.1));

        String description = room.getLongDescription();

        return assertContains(description, "大厅", "详细描述应包含房间名")
                && assertContains(description, "北", "详细描述应包含出口方向")
                && assertContains(description, "key", "详细描述应包含物品名称")
                && assertContains(description, "旧钥匙", "详细描述应包含物品说明");
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

    private static boolean assertContains(String text, String expected, String message)
    {
        return assertTrue(text != null && text.contains(expected), message);
    }
}
