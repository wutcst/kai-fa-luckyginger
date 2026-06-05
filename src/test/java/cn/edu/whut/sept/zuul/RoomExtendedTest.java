/**
 * Room类扩展的单元测试用例。
 * 补充测试Room类的高级功能，包括物品管理、出口管理、描述格式等。
 * 与已有的RoomTest.java互补，不重复测试基础功能。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.Collection;

public class RoomExtendedTest
{
    /**
     * 运行所有Room类扩展测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("Room类扩展单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testAddItemCaseInsensitive()) {
            System.out.println("✅ 测试1: 添加物品大小写不敏感 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: 添加物品大小写不敏感 - 失败");
            failed++;
        }

        if (testRemoveItemCaseInsensitive()) {
            System.out.println("✅ 测试2: 移除物品大小写不敏感 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 移除物品大小写不敏感 - 失败");
            failed++;
        }

        if (testGetItemCaseInsensitive()) {
            System.out.println("✅ 测试3: 获取物品大小写不敏感 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 获取物品大小写不敏感 - 失败");
            failed++;
        }

        if (testRemoveNonExistentItem()) {
            System.out.println("✅ 测试4: 移除不存在的物品 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 移除不存在的物品 - 失败");
            failed++;
        }

        if (testGetNonExistentItem()) {
            System.out.println("✅ 测试5: 获取不存在的物品 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 获取不存在的物品 - 失败");
            failed++;
        }

        if (testAddMultipleItems()) {
            System.out.println("✅ 测试6: 添加多个物品 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 添加多个物品 - 失败");
            failed++;
        }

        if (testRemoveItemReturnsItem()) {
            System.out.println("✅ 测试7: 移除物品返回物品对象 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 移除物品返回物品对象 - 失败");
            failed++;
        }

        if (testGetItemsCollection()) {
            System.out.println("✅ 测试8: 获取物品集合 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 获取物品集合 - 失败");
            failed++;
        }

        if (testTotalWeightEmptyRoom()) {
            System.out.println("✅ 测试9: 空房间总重量为0 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 空房间总重量为0 - 失败");
            failed++;
        }

        if (testTotalWeightWithItems()) {
            System.out.println("✅ 测试10: 有物品房间总重量 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 有物品房间总重量 - 失败");
            failed++;
        }

        if (testHasExitTrue()) {
            System.out.println("✅ 测试11: 检查存在的出口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 检查存在的出口 - 失败");
            failed++;
        }

        if (testHasExitFalse()) {
            System.out.println("✅ 测试12: 检查不存在的出口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 检查不存在的出口 - 失败");
            failed++;
        }

        if (testGetExitDirectly()) {
            System.out.println("✅ 测试13: 直接获取出口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 直接获取出口 - 失败");
            failed++;
        }

        if (testGetExitDirectlyNull()) {
            System.out.println("✅ 测试14: 直接获取不存在的出口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: 直接获取不存在的出口 - 失败");
            failed++;
        }

        if (testLongDescriptionFormat()) {
            System.out.println("✅ 测试15: 详细描述格式 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 详细描述格式 - 失败");
            failed++;
        }

        if (testAddSameItemTwice()) {
            System.out.println("✅ 测试16: 添加同名物品覆盖 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试16: 添加同名物品覆盖 - 失败");
            failed++;
        }

        if (testSetExitOverwrite()) {
            System.out.println("✅ 测试17: 覆盖出口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试17: 覆盖出口 - 失败");
            failed++;
        }

        if (testItemsStringFormat()) {
            System.out.println("✅ 测试18: 物品列表字符串格式 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试18: 物品列表字符串格式 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testAddItemCaseInsensitive()
    {
        try {
            Room room = new Room("测试房间");
            Item item = new Item("KEY", "大写钥匙", 0.5);
            room.addItem(item);
            // addItem使用toLowerCase，所以应该可以用小写获取
            if (room.getItem("key") == null) {
                System.out.println("  错误: 添加大写KEY后应能用小写key获取");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRemoveItemCaseInsensitive()
    {
        try {
            Room room = new Room("测试房间");
            room.addItem(new Item("key", "钥匙", 0.5));
            Item removed = room.removeItem("KEY");
            if (removed == null) {
                System.out.println("  错误: 应能用大写KEY移除小写key的物品");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testGetItemCaseInsensitive()
    {
        try {
            Room room = new Room("测试房间");
            room.addItem(new Item("map", "地图", 0.2));
            if (room.getItem("MAP") == null) {
                System.out.println("  错误: 应能用大写MAP获取小写map的物品");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRemoveNonExistentItem()
    {
        try {
            Room room = new Room("测试房间");
            Item removed = room.removeItem("nonexistent");
            if (removed != null) {
                System.out.println("  错误: 移除不存在的物品应返回null");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testGetNonExistentItem()
    {
        try {
            Room room = new Room("测试房间");
            Item item = room.getItem("nonexistent");
            if (item != null) {
                System.out.println("  错误: 获取不存在的物品应返回null");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testAddMultipleItems()
    {
        try {
            Room room = new Room("测试房间");
            room.addItem(new Item("key", "钥匙", 0.5));
            room.addItem(new Item("map", "地图", 0.2));
            room.addItem(new Item("cookie", "饼干", 0.3));
            if (room.getItems().size() != 3) {
                System.out.println("  错误: 添加3个物品后size应为3，实际: " + room.getItems().size());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRemoveItemReturnsItem()
    {
        try {
            Room room = new Room("测试房间");
            Item original = new Item("key", "钥匙", 0.5);
            room.addItem(original);
            Item removed = room.removeItem("key");
            if (removed == null) {
                System.out.println("  错误: 移除物品应返回物品对象");
                return false;
            }
            if (!removed.getName().equals("key")) {
                System.out.println("  错误: 返回的物品名称应为key");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testGetItemsCollection()
    {
        try {
            Room room = new Room("测试房间");
            room.addItem(new Item("key", "钥匙", 0.5));
            Collection<Item> items = room.getItems();
            if (items == null) {
                System.out.println("  错误: getItems不应返回null");
                return false;
            }
            if (items.size() != 1) {
                System.out.println("  错误: 物品集合大小应为1");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testTotalWeightEmptyRoom()
    {
        try {
            Room room = new Room("空房间");
            if (room.getTotalWeight() != 0.0) {
                System.out.println("  错误: 空房间总重量应为0");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testTotalWeightWithItems()
    {
        try {
            Room room = new Room("测试房间");
            room.addItem(new Item("key", "钥匙", 0.5));
            room.addItem(new Item("map", "地图", 0.2));
            room.addItem(new Item("cookie", "饼干", 0.3));
            double total = room.getTotalWeight();
            if (Math.abs(total - 1.0) > 0.001) {
                System.out.println("  错误: 总重量应为1.0，实际: " + total);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testHasExitTrue()
    {
        try {
            Room room = new Room("测试房间");
            room.setExit("north", new Room("北边房间"));
            if (!room.hasExit("north")) {
                System.out.println("  错误: 设置了north出口后hasExit应返回true");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testHasExitFalse()
    {
        try {
            Room room = new Room("测试房间");
            if (room.hasExit("north")) {
                System.out.println("  错误: 未设置出口时hasExit应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testGetExitDirectly()
    {
        try {
            Room room = new Room("测试房间");
            Room northRoom = new Room("北边房间");
            room.setExit("north", northRoom);
            Room exit = room.getExitDirectly("north");
            if (exit != northRoom) {
                System.out.println("  错误: getExitDirectly应返回设置的出口房间");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testGetExitDirectlyNull()
    {
        try {
            Room room = new Room("测试房间");
            Room exit = room.getExitDirectly("north");
            if (exit != null) {
                System.out.println("  错误: 不存在的出口应返回null");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLongDescriptionFormat()
    {
        try {
            Room room = new Room("图书馆");
            room.setExit("north", new Room("大厅"));
            room.addItem(new Item("notebook", "笔记本", 0.5));
            String desc = room.getLongDescription();
            if (!desc.contains("图书馆")) {
                System.out.println("  错误: 描述应包含房间名称");
                return false;
            }
            if (!desc.contains("出口")) {
                System.out.println("  错误: 描述应包含出口信息");
                return false;
            }
            if (!desc.contains("notebook")) {
                System.out.println("  错误: 描述应包含物品信息");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testAddSameItemTwice()
    {
        try {
            Room room = new Room("测试房间");
            room.addItem(new Item("key", "旧钥匙", 0.5));
            room.addItem(new Item("key", "新钥匙", 0.3));
            // 同名物品应该覆盖
            if (room.getItems().size() != 1) {
                System.out.println("  错误: 同名物品应覆盖，size应为1，实际: " + room.getItems().size());
                return false;
            }
            if (!room.getItem("key").getDescription().equals("新钥匙")) {
                System.out.println("  错误: 同名物品应被覆盖为新物品");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetExitOverwrite()
    {
        try {
            Room room = new Room("测试房间");
            Room room1 = new Room("旧北边房间");
            Room room2 = new Room("新北边房间");
            room.setExit("north", room1);
            room.setExit("north", room2);
            Room exit = room.getExit("north");
            if (exit != room2) {
                System.out.println("  错误: 覆盖出口后应返回新出口");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testItemsStringFormat()
    {
        try {
            Room room = new Room("测试房间");
            room.addItem(new Item("key", "钥匙", 0.5));
            String itemsStr = room.getItemsString();
            if (!itemsStr.startsWith("物品:")) {
                System.out.println("  错误: 物品列表应以'物品:'开头，实际: " + itemsStr);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
