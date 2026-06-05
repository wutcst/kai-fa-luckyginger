/**
 * DropCommand类的单元测试用例。
 * 测试丢弃物品命令的各种场景，包括无参数、不存在的物品、成功丢弃等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class DropCommandTest
{
    /**
     * 运行所有DropCommand类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("DropCommand类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testDropCommandCreation()) {
            System.out.println("✅ 测试1: DropCommand对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: DropCommand对象创建 - 失败");
            failed++;
        }

        if (testDropWithoutSecondWord()) {
            System.out.println("✅ 测试2: 丢弃命令无参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 丢弃命令无参数 - 失败");
            failed++;
        }

        if (testDropItemNotInInventory()) {
            System.out.println("✅ 测试3: 丢弃不存在的物品 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 丢弃不存在的物品 - 失败");
            failed++;
        }

        if (testDropItemSuccessfully()) {
            System.out.println("✅ 测试4: 成功丢弃物品 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 成功丢弃物品 - 失败");
            failed++;
        }

        if (testDropItemAddedToRoom()) {
            System.out.println("✅ 测试5: 丢弃物品添加到房间 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 丢弃物品添加到房间 - 失败");
            failed++;
        }

        if (testDropItemRemovedFromInventory()) {
            System.out.println("✅ 测试6: 丢弃物品从背包移除 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 丢弃物品从背包移除 - 失败");
            failed++;
        }

        if (testDropMultipleItems()) {
            System.out.println("✅ 测试7: 连续丢弃多个物品 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 连续丢弃多个物品 - 失败");
            failed++;
        }

        if (testDropCommandReturnsFalse()) {
            System.out.println("✅ 测试8: drop命令返回false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: drop命令返回false - 失败");
            failed++;
        }

        if (testDropSameItemTwice()) {
            System.out.println("✅ 测试9: 两次丢弃同一物品 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 两次丢弃同一物品 - 失败");
            failed++;
        }

        if (testDropUsableItem()) {
            System.out.println("✅ 测试10: 丢弃可使用物品 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 丢弃可使用物品 - 失败");
            failed++;
        }

        if (testDropHeavyItem()) {
            System.out.println("✅ 测试11: 丢弃重物 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 丢弃重物 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testDropCommandCreation()
    {
        try {
            DropCommand cmd = new DropCommand();
            if (cmd == null) {
                System.out.println("  错误: 无法创建DropCommand对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropWithoutSecondWord()
    {
        try {
            DropCommand cmd = new DropCommand();
            Command command = new Command("drop", null);
            Game game = new Game();
            cmd.execute(command, game);
            // 应该输出"丢弃什么？"并返回false
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropItemNotInInventory()
    {
        try {
            DropCommand cmd = new DropCommand();
            Command command = new Command("drop", "key");
            Game game = new Game();
            cmd.execute(command, game);
            // 应该输出"你没有 key！"
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropItemSuccessfully()
    {
        try {
            DropCommand cmd = new DropCommand();
            Command command = new Command("drop", "key");
            Game game = new Game();
            Player player = game.getPlayer();
            Item key = new Item("key", "钥匙", 0.5);
            player.takeItem(key);
            cmd.execute(command, game);
            if (player.hasItem("key")) {
                System.out.println("  错误: 丢弃后背包不应还有key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropItemAddedToRoom()
    {
        try {
            DropCommand cmd = new DropCommand();
            Command command = new Command("drop", "key");
            Game game = new Game();
            Player player = game.getPlayer();
            Item key = new Item("key", "钥匙", 0.5);
            player.takeItem(key);
            Room currentRoom = player.getCurrentRoom();
            cmd.execute(command, game);
            if (currentRoom.getItem("key") == null) {
                System.out.println("  错误: 丢弃后房间应包含key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropItemRemovedFromInventory()
    {
        try {
            DropCommand cmd = new DropCommand();
            Command command = new Command("drop", "map");
            Game game = new Game();
            Player player = game.getPlayer();
            Item map = new Item("map", "地图", 0.2);
            player.takeItem(map);
            cmd.execute(command, game);
            if (player.hasItem("map")) {
                System.out.println("  错误: 丢弃后背包不应还有map");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropMultipleItems()
    {
        try {
            DropCommand cmd = new DropCommand();
            Game game = new Game();
            Player player = game.getPlayer();
            Item key = new Item("key", "钥匙", 0.5);
            Item map = new Item("map", "地图", 0.2);
            Item cookie = new Item("cookie", "饼干", 0.3);
            player.takeItem(key);
            player.takeItem(map);
            player.takeItem(cookie);

            cmd.execute(new Command("drop", "key"), game);
            cmd.execute(new Command("drop", "map"), game);
            cmd.execute(new Command("drop", "cookie"), game);

            if (player.hasItem("key") || player.hasItem("map") || player.hasItem("cookie")) {
                System.out.println("  错误: 丢弃后背包不应还有物品");
                return false;
            }
            Room currentRoom = player.getCurrentRoom();
            if (currentRoom.getItem("key") == null || currentRoom.getItem("map") == null || currentRoom.getItem("cookie") == null) {
                System.out.println("  错误: 丢弃后房间应包含所有物品");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropCommandReturnsFalse()
    {
        try {
            DropCommand cmd = new DropCommand();
            Command command = new Command("drop", "key");
            Game game = new Game();
            Player player = game.getPlayer();
            Item key = new Item("key", "钥匙", 0.5);
            player.takeItem(key);
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: drop命令应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropSameItemTwice()
    {
        try {
            DropCommand cmd = new DropCommand();
            Game game = new Game();
            Player player = game.getPlayer();
            Item key = new Item("key", "钥匙", 0.5);
            player.takeItem(key);

            cmd.execute(new Command("drop", "key"), game);
            // 第二次丢弃同一个物品
            cmd.execute(new Command("drop", "key"), game);
            // 应该输出"你没有 key！"，不会崩溃
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropUsableItem()
    {
        try {
            DropCommand cmd = new DropCommand();
            Command command = new Command("drop", "key");
            Game game = new Game();
            Player player = game.getPlayer();
            Item key = new Item("key", "钥匙", 0.5, "KEY", "解锁房间");
            player.takeItem(key);
            cmd.execute(command, game);
            if (player.hasItem("key")) {
                System.out.println("  错误: 丢弃可使用物品后背包不应还有该物品");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDropHeavyItem()
    {
        try {
            DropCommand cmd = new DropCommand();
            Command command = new Command("drop", "computer");
            Game game = new Game();
            Player player = game.getPlayer();
            Item computer = new Item("computer", "电脑", 5.0);
            player.takeItem(computer);
            cmd.execute(command, game);
            if (player.hasItem("computer")) {
                System.out.println("  错误: 丢弃重物后背包不应还有该物品");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
