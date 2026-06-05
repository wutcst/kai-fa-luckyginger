/**
 * LookCommand类的单元测试用例。
 * 测试查看命令的各种场景，包括房间描述、出口信息、上锁房间提示等。
 *
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class LookCommandTest
{
    /**
     * 运行所有LookCommand类的测试用例。
     *
     * @return 测试通过返回true，失败返回false
     */
    public static boolean runAllTests()
    {
        System.out.println("========================================");
        System.out.println("LookCommand类单元测试");
        System.out.println("========================================\n");

        int passed = 0;
        int failed = 0;

        if (testLookCommandCreation()) {
            System.out.println("✅ 测试1: LookCommand对象创建 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试1: LookCommand对象创建 - 失败");
            failed++;
        }

        if (testLookCommandImplementsInterface()) {
            System.out.println("✅ 测试2: LookCommand实现CommandExecutor接口 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试2: LookCommand实现CommandExecutor接口 - 失败");
            failed++;
        }

        if (testLookReturnsFalse()) {
            System.out.println("✅ 测试3: look命令返回false - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试3: look命令返回false - 失败");
            failed++;
        }

        if (testLookCommandNoException()) {
            System.out.println("✅ 测试4: look命令不抛异常 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试4: look命令不抛异常 - 失败");
            failed++;
        }

        if (testRoomLongDescription()) {
            System.out.println("✅ 测试5: 房间详细描述 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试5: 房间详细描述 - 失败");
            failed++;
        }

        if (testRoomShortDescription()) {
            System.out.println("✅ 测试6: 房间简短描述 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试6: 房间简短描述 - 失败");
            failed++;
        }

        if (testRoomWithExits()) {
            System.out.println("✅ 测试7: 有出口的房间描述 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试7: 有出口的房间描述 - 失败");
            failed++;
        }

        if (testRoomWithItems()) {
            System.out.println("✅ 测试8: 有物品的房间描述 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试8: 有物品的房间描述 - 失败");
            failed++;
        }

        if (testLockedRoomDescription()) {
            System.out.println("✅ 测试9: 上锁房间描述 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试9: 上锁房间描述 - 失败");
            failed++;
        }

        if (testDirectionTranslation()) {
            System.out.println("✅ 测试10: 方向翻译 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试10: 方向翻译 - 失败");
            failed++;
        }

        if (testLookWithSecondWord()) {
            System.out.println("✅ 测试11: look命令带额外参数 - 通过");
            passed++;
        } else {
            System.out.println("❌ 测试11: look命令带额外参数 - 失败");
            failed++;
        }

        System.out.println("\n========================================");
        System.out.println("测试结果: " + passed + " 通过, " + failed + " 失败");
        System.out.println("========================================\n");

        return failed == 0;
    }

    private static boolean testLookCommandCreation()
    {
        try {
            LookCommand cmd = new LookCommand();
            if (cmd == null) {
                System.out.println("  错误: 无法创建LookCommand对象");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLookCommandImplementsInterface()
    {
        try {
            LookCommand cmd = new LookCommand();
            if (!(cmd instanceof CommandExecutor)) {
                System.out.println("  错误: LookCommand应实现CommandExecutor接口");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLookReturnsFalse()
    {
        try {
            LookCommand cmd = new LookCommand();
            Command command = new Command("look", null);
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            if (result) {
                System.out.println("  错误: look命令应返回false");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLookCommandNoException()
    {
        try {
            LookCommand cmd = new LookCommand();
            Command command = new Command("look", null);
            Game game = new Game();
            cmd.execute(command, game);
            return true;
        } catch (Exception e) {
            System.out.println("  异常: look命令不应抛出异常 - " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomLongDescription()
    {
        try {
            Room room = new Room("图书馆");
            room.setExit("north", new Room("大厅"));
            String desc = room.getLongDescription();
            if (desc == null || desc.isEmpty()) {
                System.out.println("  错误: 房间详细描述不应为空");
                return false;
            }
            if (!desc.contains("图书馆")) {
                System.out.println("  错误: 房间详细描述应包含房间名称");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomShortDescription()
    {
        try {
            Room room = new Room("计算机实验室");
            String desc = room.getShortDescription();
            if (!desc.equals("计算机实验室")) {
                System.out.println("  错误: 房间简短描述应为'计算机实验室'，实际: " + desc);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testRoomWithExits()
    {
        try {
            Room room = new Room("大厅");
            room.setExit("north", new Room("花园"));
            room.setExit("south", new Room("入口"));
            String desc = room.getLongDescription();
            if (!desc.contains("出口")) {
                System.out.println("  错误: 有出口的房间描述应包含出口信息");
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
            Room room = new Room("实验室");
            room.addItem(new Item("computer", "电脑", 5.0));
            String desc = room.getLongDescription();
            if (!desc.contains("computer")) {
                System.out.println("  错误: 有物品的房间描述应包含物品信息");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLockedRoomDescription()
    {
        try {
            LockedRoom lockedRoom = new LockedRoom("上锁的宝库", "key");
            String desc = lockedRoom.getLongDescription();
            if (desc == null || desc.isEmpty()) {
                System.out.println("  错误: 上锁房间描述不应为空");
                return false;
            }
            if (!desc.contains("锁")) {
                System.out.println("  错误: 上锁房间描述应包含锁定提示");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testDirectionTranslation()
    {
        try {
            Room room = new Room("测试房间");
            room.setExit("north", new Room("北边房间"));
            room.setExit("south", new Room("南边房间"));
            room.setExit("east", new Room("东边房间"));
            room.setExit("west", new Room("西边房间"));
            String desc = room.getLongDescription();
            // 方向应该被翻译为中文
            if (!desc.contains("北") || !desc.contains("南") || !desc.contains("东") || !desc.contains("西")) {
                System.out.println("  错误: 出口方向应被翻译为中文，实际: " + desc);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }

    private static boolean testLookWithSecondWord()
    {
        try {
            LookCommand cmd = new LookCommand();
            Command command = new Command("look", "around");
            Game game = new Game();
            boolean result = cmd.execute(command, game);
            // look命令忽略第二个词
            return true;
        } catch (Exception e) {
            System.out.println("  异常: " + e.getMessage());
            return false;
        }
    }
}
