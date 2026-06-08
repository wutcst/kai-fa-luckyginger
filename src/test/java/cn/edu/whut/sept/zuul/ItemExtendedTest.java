/**
 * Item类扩展的单元测试用例。
 * 补充测试Item类的物品类型、使用效果、可使用性等高级功能。
 * 与已有的ItemTest.java互补，不重复测试基础功能。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class ItemExtendedTest
{
    /**
     * 运行所有Item类扩展测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("Item类扩展单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testNormalItemNotUsable()) {
            System.out.println("✅ 测试1: 普通物品不可使用 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: 普通物品不可使用 - 失败");
            failed++;
        }

        if (testSpecialItemUsable()) {
            System.out.println("✅ 测试2: 特殊物品可使用 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 特殊物品可使用 - 失败");
            failed++;
        }

        if (testKeyItemType()) {
            System.out.println("✅ 测试3: 钥匙物品类型 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 钥匙物品类型 - 失败");
            failed++;
        }

        if (testMapItemType()) {
            System.out.println("✅ 测试4: 地图物品类型 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 地图物品类型 - 失败");
            failed++;
        }

        if (testFoodItemType()) {
            System.out.println("✅ 测试5: 食物物品类型 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 食物物品类型 - 失败");
            failed++;
        }

        if (testToolItemType()) {
            System.out.println("✅ 测试6: 工具物品类型 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 工具物品类型 - 失败");
            failed++;
        }

        if (testSetItemTypeMakesUsable()) {
            System.out.println("✅ 测试7: 设置物品类型使其可使用 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 设置物品类型使其可使用 - 失败");
            failed++;
        }

        if (testSetUseEffectMakesUsable()) {
            System.out.println("✅ 测试8: 设置使用效果使其可使用 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 设置使用效果使其可使用 - 失败");
            failed++;
        }

        if (testDefaultItemType()) {
            System.out.println("✅ 测试9: 默认物品类型为NORMAL - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 默认物品类型为NORMAL - 失败");
            failed++;
        }

        if (testDefaultUseEffect()) {
            System.out.println("✅ 测试10: 默认使用效果为空 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 默认使用效果为空 - 失败");
            failed++;
        }

        if (testSpecialItemUseEffect()) {
            System.out.println("✅ 测试11: 特殊物品使用效果 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 特殊物品使用效果 - 失败");
            failed++;
        }

        if (testItemToString()) {
            System.out.println("✅ 测试12: 物品toString格式 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 物品toString格式 - 失败");
            failed++;
        }

        if (testItemNormalTypeConstant()) {
            System.out.println("✅ 测试13: NORMAL类型常量 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: NORMAL类型常量 - 失败");
            failed++;
        }

        if (testMultipleSetItemType()) {
            System.out.println("✅ 测试14: 多次设置物品类型 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试14: 多次设置物品类型 - 失败");
            failed++;
        }

        if (testMultipleSetUseEffect()) {
            System.out.println("✅ 测试15: 多次设置使用效果 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试15: 多次设置使用效果 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testNormalItemNotUsable()
    {
        try {
            Item item = new Item("rock", "石头", 1.0);
            if (item.isUsable()) {
                System.out.println("  错误: 普通物品不应可使用");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSpecialItemUsable()
    {
        try {
            Item item = new Item("key", "钥匙", 0.5, "KEY", "解锁房间");
            if (!item.isUsable()) {
                System.out.println("  错误: 特殊物品应可使用");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testKeyItemType()
    {
        try {
            Item item = new Item("key", "钥匙", 0.5, "KEY", "解锁房间");
            if (!item.getItemType().equals("KEY")) {
                System.out.println("  错误: 钥匙类型应为KEY，实际: " + item.getItemType());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testMapItemType()
    {
        try {
            Item item = new Item("map", "地图", 0.2, "MAP", "查看地图");
            if (!item.getItemType().equals("MAP")) {
                System.out.println("  错误: 地图类型应为MAP，实际: " + item.getItemType());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testFoodItemType()
    {
        try {
            Item item = new Item("cookie", "饼干", 0.3, "FOOD", "增加负重");
            if (!item.getItemType().equals("FOOD")) {
                System.out.println("  错误: 食物类型应为FOOD，实际: " + item.getItemType());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testToolItemType()
    {
        try {
            Item item = new Item("cable", "数据线", 0.3, "TOOL", "连接设备");
            if (!item.getItemType().equals("TOOL")) {
                System.out.println("  错误: 工具类型应为TOOL，实际: " + item.getItemType());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetItemTypeMakesUsable()
    {
        try {
            Item item = new Item("rock", "石头", 1.0);
            if (item.isUsable()) {
                System.out.println("  错误: 初始状态不应可使用");
                return false;
            }
            item.setItemType("WEAPON");
            if (!item.isUsable()) {
                System.out.println("  错误: 设置物品类型后应可使用");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSetUseEffectMakesUsable()
    {
        try {
            Item item = new Item("rock", "石头", 1.0);
            if (item.isUsable()) {
                System.out.println("  错误: 初始状态不应可使用");
                return false;
            }
            item.setUseEffect("投掷石头");
            if (!item.isUsable()) {
                System.out.println("  错误: 设置使用效果后应可使用");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultItemType()
    {
        try {
            Item item = new Item("rock", "石头", 1.0);
            if (!item.getItemType().equals("NORMAL")) {
                System.out.println("  错误: 默认物品类型应为NORMAL，实际: " + item.getItemType());
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDefaultUseEffect()
    {
        try {
            Item item = new Item("rock", "石头", 1.0);
            if (!item.getUseEffect().equals("")) {
                System.out.println("  错误: 默认使用效果应为空字符串，实际: '" + item.getUseEffect() + "'");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testSpecialItemUseEffect()
    {
        try {
            Item item = new Item("key", "钥匙", 0.5, "KEY", "解锁房间");
            if (!item.getUseEffect().equals("解锁房间")) {
                System.out.println("  错误: 使用效果应为'解锁房间'，实际: '" + item.getUseEffect() + "'");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testItemToString()
    {
        try {
            Item item = new Item("key", "钥匙", 0.5);
            String str = item.toString();
            if (!str.contains("key") || !str.contains("钥匙") || !str.contains("0.5")) {
                System.out.println("  错误: toString格式不正确，实际: " + str);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testItemNormalTypeConstant()
    {
        try {
            if (!Item.ITEM_TYPE_NORMAL.equals("NORMAL")) {
                System.out.println("  错误: ITEM_TYPE_NORMAL常量应为'NORMAL'");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testMultipleSetItemType()
    {
        try {
            Item item = new Item("wand", "魔杖", 1.0);
            item.setItemType("WEAPON");
            if (!item.getItemType().equals("WEAPON")) return false;
            item.setItemType("MAGIC");
            if (!item.getItemType().equals("MAGIC")) return false;
            item.setItemType("TOOL");
            if (!item.getItemType().equals("TOOL")) return false;
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testMultipleSetUseEffect()
    {
        try {
            Item item = new Item("wand", "魔杖", 1.0);
            item.setUseEffect("攻击");
            if (!item.getUseEffect().equals("攻击")) return false;
            item.setUseEffect("施法");
            if (!item.getUseEffect().equals("施法")) return false;
            item.setUseEffect("传送");
            if (!item.getUseEffect().equals("传送")) return false;
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
