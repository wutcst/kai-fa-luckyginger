/**
 * ItemsCommand类的单元测试用例。
 * 测试物品列表命令的各种场景，包括空房间、有物品房间、空背包、有物品背包等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class ItemsCommandTest
{
    /**
     * 运行所有ItemsCommand类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("ItemsCommand类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testItemsCommandCreation()) {
            System.out.println("✅ 测试1: ItemsCommand对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: ItemsCommand对象创建 - 失败");
            failed++;
        }

        if (testItemsCommandImplementsInterface()) {
            System.out.println("✅ 测试2: ItemsCommand实现CommandExecutor接口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: ItemsCommand实现CommandExecutor接口 - 失败");
            failed++;
        }

        if (testItemsReturnsFalse()) {
            System.out.println("✅ 测试3: items命令返回false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: items命令返回false - 失败");
            failed++;
        }

        if (testItemsCommandNoException()) {
            System.out.println("✅ 测试4: items命令不抛异常 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: items命令不抛异常 - 失败");
            failed++;
        }

        if (testRoomWithNoItems()) {
            System.out.println("✅ 测试5: 空房间物品显示 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 空房间物品显示 - 失败");
            failed++;
        }

        if (testRoomWithItems()) {
            System.out.println("✅ 测试6: 有物品房间显示 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 有物品房间显示 - 失败");
            failed++;
        }

        if (testRoomTotalWeight()) {
            System.out.println("✅ 测试7: 房间物品总重量 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 房间物品总重量 - 失败");
            failed++;
        }

        if (testPlayerEmptyInventory()) {
            System.out.println("✅ 测试8: 空背包显示 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 空背包显示 - 失败");
            failed++;
        }

        if (testPlayerWithItems()) {
            System.out.println("✅ 测试9: 有物品背包显示 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 有物品背包显示 - 失败");
            failed++;
        }

        if (testPlayerTotalWeight()) {
            System.out.println("✅ 测试10: 背包物品总重量 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 背包物品总重量 - 失败");
            failed++;
        }

        if (testRoomWithMultipleItems()) {
            System.out.println("✅ 测试11: 多物品房间显示 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 多物品房间显示 - 失败");
            failed++;
        }

        if (testRoomWithUsableItems()) {
            System.out.println("✅ 测试12: 可使用物品房间显示 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 可使用物品房间显示 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testItemsCommandCreation()
    {
        try {
            ItemsCommand cmd = new ItemsCommand();
            if (cmd == null) {
                System.out.println("  错误: 无法创建ItemsCommand对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testItemsCommandImplementsInterface()
    {
        try {
            ItemsCommand cmd = new ItemsCommand();
            if (!(cmd instanceof CommandExecutor)) {
                System.out.println("  错误: ItemsCommand应实现CommandExecutor接口");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testItemsReturnsFalse()
    {
        try {
            ItemsCommand cmd = new ItemsCommand();
            Command command = new Command("items", null);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: items命令应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testItemsCommandNoException()
    {
        try {
            ItemsCommand cmd = new ItemsCommand();
            Command command = new Command("items", null);
            Game game = new Game();
            cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: items命令不应抛出异常 - " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomWithNoItems()
    {
        try {
            Room room = new Room("空房间");
            String itemsStr = room.getItemsString();
            if (!itemsStr.contains("没有物品")) {
                System.out.println("  错误: 空房间应显示没有物品，实际: " + itemsStr);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomWithItems()
    {
        try {
            Room room = new Room("有物品的房间");
            room.addItem(new Item("key", "钥匙", 0.5));
            String itemsStr = room.getItemsString();
            if (itemsStr.contains("没有物品")) {
                System.out.println("  错误: 有物品的房间不应显示没有物品");
                return false;
            }
            if (!itemsStr.contains("key")) {
                System.out.println("  错误: 物品列表应包含key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomTotalWeight()
    {
        try {
            Room room = new Room("测试房间");
            room.addItem(new Item("key", "钥匙", 0.5));
            room.addItem(new Item("map", "地图", 0.2));
            double totalWeight = room.getTotalWeight();
            if (Math.abs(totalWeight - 0.7) > 0.001) {
                System.out.println("  错误: 房间物品总重量应为0.7，实际: " + totalWeight);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerEmptyInventory()
    {
        try {
            Player player = new Player("测试玩家", 10.0);
            String invStr = player.getInventoryString();
            if (!invStr.contains("没有携带")) {
                System.out.println("  错误: 空背包应显示没有携带物品，实际: " + invStr);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerWithItems()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            player.takeItem(new Item("key", "钥匙", 0.5));
            String invStr = player.getInventoryString();
            if (invStr.contains("没有携带")) {
                System.out.println("  错误: 有物品的背包不应显示没有携带");
                return false;
            }
            if (!invStr.contains("key")) {
                System.out.println("  错误: 背包列表应包含key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testPlayerTotalWeight()
    {
        try {
            Player player = new Player("测试玩家", 100.0);
            player.takeItem(new Item("key", "钥匙", 0.5));
            player.takeItem(new Item("map", "地图", 0.2));
            double totalWeight = player.getTotalWeight();
            if (Math.abs(totalWeight - 0.7) > 0.001) {
                System.out.println("  错误: 背包物品总重量应为0.7，实际: " + totalWeight);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomWithMultipleItems()
    {
        try {
            Room room = new Room("多物品房间");
            room.addItem(new Item("key", "钥匙", 0.5));
            room.addItem(new Item("map", "地图", 0.2));
            room.addItem(new Item("cookie", "饼干", 0.3));
            if (room.getItems().size() != 3) {
                System.out.println("  错误: 房间应有3个物品，实际: " + room.getItems().size());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomWithUsableItems()
    {
        try {
            Room room = new Room("可使用物品房间");
            room.addItem(new Item("key", "钥匙", 0.5, "KEY", "解锁房间"));
            String itemsStr = room.getItemsString();
            if (itemsStr.contains("没有物品")) {
                System.out.println("  错误: 有可使用物品的房间不应显示没有物品");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
