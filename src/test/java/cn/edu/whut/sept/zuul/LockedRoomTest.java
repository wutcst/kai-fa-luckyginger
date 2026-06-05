/**
 * LockedRoom类的单元测试用例。
 * 测试上锁房间的创建、解锁机制、出口访问、描述信息等功能。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class LockedRoomTest
{
    /**
     * 运行所有LockedRoom类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("LockedRoom类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testLockedRoomCreation()) {
            System.out.println("✅ 测试1: 上锁房间创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: 上锁房间创建 - 失败");
            failed++;
        }

        if (testInitialLockState()) {
            System.out.println("✅ 测试2: 初始锁定状态 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: 初始锁定状态 - 失败");
            failed++;
        }

        if (testUnlockWithCorrectKey()) {
            System.out.println("✅ 测试3: 正确钥匙解锁 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: 正确钥匙解锁 - 失败");
            failed++;
        }

        if (testUnlockWithWrongKey()) {
            System.out.println("✅ 测试4: 错误钥匙解锁 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: 错误钥匙解锁 - 失败");
            failed++;
        }

        if (testUnlockWithNullKey()) {
            System.out.println("✅ 测试5: null钥匙解锁 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: null钥匙解锁 - 失败");
            failed++;
        }

        if (testUnlockCaseInsensitive()) {
            System.out.println("✅ 测试6: 解锁大小写不敏感 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 解锁大小写不敏感 - 失败");
            failed++;
        }

        if (testGetRequiredKeyType()) {
            System.out.println("✅ 测试7: 获取所需钥匙类型 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 获取所需钥匙类型 - 失败");
            failed++;
        }

        if (testExitAccessWhileLocked()) {
            System.out.println("✅ 测试8: 锁定状态下出口可访问 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 锁定状态下出口可访问 - 失败");
            failed++;
        }

        if (testExitAccessWhileUnlocked()) {
            System.out.println("✅ 测试9: 解锁后出口可访问 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 解锁后出口可访问 - 失败");
            failed++;
        }

        if (testLongDescriptionWhileLocked()) {
            System.out.println("✅ 测试10: 锁定状态描述 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 锁定状态描述 - 失败");
            failed++;
        }

        if (testLongDescriptionWhileUnlocked()) {
            System.out.println("✅ 测试11: 解锁后描述 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: 解锁后描述 - 失败");
            failed++;
        }

        if (testDoubleUnlock()) {
            System.out.println("✅ 测试12: 重复解锁 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试12: 重复解锁 - 失败");
            failed++;
        }

        if (testLockedRoomInheritance()) {
            System.out.println("✅ 测试13: 继承Room类功能 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试13: 继承Room类功能 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testLockedRoomCreation()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            if (room == null) {
                System.out.println("  错误: 无法创建LockedRoom对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testInitialLockState()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            if (room.isUnlocked()) {
                System.out.println("  错误: 新创建的LockedRoom不应该是解锁状态");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testUnlockWithCorrectKey()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            boolean result = room.unlock("key");
            if (!result) {
                System.out.println("  错误: 使用正确钥匙应该解锁成功");
                return false;
            }
            if (!room.isUnlocked()) {
                System.out.println("  错误: 解锁后房间应该是解锁状态");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testUnlockWithWrongKey()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            boolean result = room.unlock("wrong_key");
            if (result) {
                System.out.println("  错误: 使用错误钥匙不应该解锁成功");
                return false;
            }
            if (room.isUnlocked()) {
                System.out.println("  错误: 使用错误钥匙后房间不应该解锁");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testUnlockWithNullKey()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            boolean result = room.unlock(null);
            if (result) {
                System.out.println("  错误: 使用null钥匙不应该解锁成功");
                return false;
            }
            if (room.isUnlocked()) {
                System.out.println("  错误: 使用null钥匙后房间不应该解锁");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testUnlockCaseInsensitive()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            boolean result = room.unlock("KEY");
            if (!result) {
                System.out.println("  错误: 解锁应该不区分大小写");
                return false;
            }
            if (!room.isUnlocked()) {
                System.out.println("  错误: 大写钥匙解锁后房间应该是解锁状态");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testGetRequiredKeyType()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "golden_key");
            String keyType = room.getRequiredKeyType();
            if (!keyType.equals("golden_key")) {
                System.out.println("  错误: 所需钥匙类型不匹配，期望: golden_key, 实际: " + keyType);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testExitAccessWhileLocked()
    {
        try {
            LockedRoom lockedRoom = new LockedRoom("上锁的宝库", "key");
            Room otherRoom = new Room("其他房间");
            lockedRoom.setExit("south", otherRoom);
            Room exit = lockedRoom.getExit("south");
            if (exit == null) {
                System.out.println("  错误: 锁定状态下出口应该可以访问");
                return false;
            }
            if (!exit.getShortDescription().equals("其他房间")) {
                System.out.println("  错误: 锁定状态下出口指向的房间不正确");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testExitAccessWhileUnlocked()
    {
        try {
            LockedRoom lockedRoom = new LockedRoom("上锁的宝库", "key");
            Room otherRoom = new Room("其他房间");
            lockedRoom.setExit("south", otherRoom);
            lockedRoom.unlock("key");
            Room exit = lockedRoom.getExit("south");
            if (exit == null) {
                System.out.println("  错误: 解锁后出口应该可以访问");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLongDescriptionWhileLocked()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            String desc = room.getLongDescription();
            if (desc == null || desc.isEmpty()) {
                System.out.println("  错误: 锁定状态描述不应为空");
                return false;
            }
            if (!desc.contains("锁")) {
                System.out.println("  错误: 锁定状态描述应该包含锁定提示");
                return false;
            }
            if (!desc.contains("key")) {
                System.out.println("  错误: 锁定状态描述应该包含所需钥匙类型");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLongDescriptionWhileUnlocked()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            room.unlock("key");
            String desc = room.getLongDescription();
            if (desc == null || desc.isEmpty()) {
                System.out.println("  错误: 解锁后描述不应为空");
                return false;
            }
            if (!desc.contains("上锁的宝库")) {
                System.out.println("  错误: 解锁后描述应该包含房间名称");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDoubleUnlock()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            boolean first = room.unlock("key");
            boolean second = room.unlock("key");
            if (!first || !second) {
                System.out.println("  错误: 重复解锁应该都返回true");
                return false;
            }
            if (!room.isUnlocked()) {
                System.out.println("  错误: 重复解锁后房间应该是解锁状态");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLockedRoomInheritance()
    {
        try {
            LockedRoom room = new LockedRoom("上锁的宝库", "key");
            room.addItem(new Item("treasure", "宝藏", 5.0));
            if (room.getItems().size() != 1) {
                System.out.println("  错误: LockedRoom应该继承Room的物品管理功能");
                return false;
            }
            if (!room.getShortDescription().equals("上锁的宝库")) {
                System.out.println("  错误: LockedRoom应该继承Room的描述功能");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
