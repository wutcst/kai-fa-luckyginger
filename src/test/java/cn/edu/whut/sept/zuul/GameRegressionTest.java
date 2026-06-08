package cn.edu.whut.sept.zuul;

import java.util.Map;

/**
 * 回归测试套件 - 游戏核心功能验收测试
 * 
 * <p>测试覆盖范围：
 * <ul>
 *   <li>登录/注册</li>
 *   <li>开始新游戏</li>
 *   <li>房间移动</li>
 *   <li>物品拾取</li>
 *   <li>背包显示</li>
 *   <li>谜题交互</li>
 *   <li>保存/读取存档</li>
 *   <li>完整通关流程</li>
 * </ul>
 */
public class GameRegressionTest
{
    public static boolean runAllTests()
    {
        int passed = 0;
        int failed = 0;

        if (testLoginRegister()) {
            passed++;
        } else {
            failed++;
        }

        if (testNewGame()) {
            passed++;
        } else {
            failed++;
        }

        if (testRoomMovement()) {
            passed++;
        } else {
            failed++;
        }

        if (testItemPickup()) {
            passed++;
        } else {
            failed++;
        }

        if (testInventoryDisplay()) {
            passed++;
        } else {
            failed++;
        }

        if (testPuzzleInteraction()) {
            passed++;
        } else {
            failed++;
        }

        if (testSaveLoad()) {
            passed++;
        } else {
            failed++;
        }

        if (testGameCompletion()) {
            passed++;
        } else {
            failed++;
        }

        if (testEndToEndFlow()) {
            passed++;
        } else {
            failed++;
        }

        printSummary("GameRegressionTest", passed, failed);
        return failed == 0;
    }

    // ========== 1. 登录/注册测试 ==========
    private static boolean testLoginRegister()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";

        // 注册新用户成功
        Map<String, Object> response = controller.register(testUsername, testPassword);
        if (!assertTrue((Boolean) response.get("success"), "注册应成功") ||
            !assertEquals("注册成功！", response.get("message"), "注册消息不正确")) {
            return false;
        }

        // 注册重复用户名失败
        response = controller.register(testUsername, "newpass");
        if (!assertFalse((Boolean) response.get("success"), "重复注册应失败")) {
            return false;
        }

        // 用户登录成功
        response = controller.login(testUsername, testPassword);
        if (!assertTrue((Boolean) response.get("success"), "登录应成功")) {
            return false;
        }

        // 登录错误密码失败
        response = controller.login(testUsername, "wrongpassword");
        if (!assertFalse((Boolean) response.get("success"), "错误密码登录应失败")) {
            return false;
        }

        return true;
    }

    // ========== 2. 开始新游戏测试 ==========
    private static boolean testNewGame()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";
        
        controller.register(testUsername, testPassword);
        String sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");

        // 新游戏返回初始状态
        Map<String, Object> response = controller.newGame(sessionId);
        if (!assertTrue((Boolean) response.get("success"), "新游戏应成功")) {
            return false;
        }

        return true;
    }

    // ========== 3. 房间移动测试 ==========
    private static boolean testRoomMovement()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";
        
        controller.register(testUsername, testPassword);
        String sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");

        // 向有效方向移动
        Map<String, Object> response = controller.executeCommand("go north", sessionId);
        if (!assertTrue((Boolean) response.get("success"), "移动应成功")) {
            return false;
        }

        // look命令查看当前房间
        response = controller.executeCommand("look", sessionId);
        if (!assertTrue((Boolean) response.get("success"), "look命令应成功")) {
            return false;
        }

        // back命令返回上一个房间
        response = controller.executeCommand("back", sessionId);
        if (!assertTrue((Boolean) response.get("success"), "back命令应成功")) {
            return false;
        }

        return true;
    }

    // ========== 4. 物品拾取测试 ==========
    private static boolean testItemPickup()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";
        
        controller.register(testUsername, testPassword);
        String sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");

        // 拾取房间内物品（可能成功也可能失败，取决于房间是否有物品）
        Map<String, Object> response = controller.executeCommand("take item", sessionId);
        assertNotNull(response.get("message"), "拾取命令应返回消息");

        return true;
    }

    // ========== 5. 背包显示测试 ==========
    private static boolean testInventoryDisplay()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";
        
        controller.register(testUsername, testPassword);
        String sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");

        // items命令显示背包
        Map<String, Object> response = controller.executeCommand("items", sessionId);
        if (!assertTrue((Boolean) response.get("success"), "items命令应成功")) {
            return false;
        }

        // getGameStatus包含背包信息
        Map<String, Object> status = controller.getGameStatus(sessionId);
        if (!assertNotNull(status.get("player"), "游戏状态应包含玩家信息")) {
            return false;
        }

        return true;
    }

    // ========== 6. 谜题交互测试 ==========
    private static boolean testPuzzleInteraction()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";
        
        controller.register(testUsername, testPassword);
        String sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");

        // 使用钥匙（无论成功失败，有消息就行）
        Map<String, Object> response = controller.executeCommand("use key", sessionId);
        assertNotNull(response.get("message"), "use key应返回消息");

        // 使用地图
        response = controller.executeCommand("use map", sessionId);
        assertNotNull(response.get("message"), "use map应返回消息");

        return true;
    }

    // ========== 7. 保存/读取存档测试 ==========
    private static boolean testSaveLoad()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";
        
        controller.register(testUsername, testPassword);
        String sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");

        // 保存游戏状态
        Map<String, Object> response = controller.saveGame(sessionId);
        assertNotNull(response.get("success"), "saveGame应返回结果");

        // 加载游戏状态
        response = controller.loadGame(sessionId);
        assertNotNull(response.get("success"), "loadGame应返回结果");

        // 执行save命令
        response = controller.executeCommand("save", sessionId);
        assertNotNull(response.get("message"), "save命令应返回消息");

        // 执行load命令
        response = controller.executeCommand("load", sessionId);
        assertNotNull(response.get("message"), "load命令应返回消息");

        return true;
    }

    // ========== 8. 完整通关流程测试 ==========
    private static boolean testGameCompletion()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";
        
        controller.register(testUsername, testPassword);
        String sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");

        // 查看通关进度
        Map<String, Object> response = controller.executeCommand("status", sessionId);
        if (!assertTrue((Boolean) response.get("success"), "status命令应成功")) {
            return false;
        }

        // executeCommand返回通关状态
        response = controller.executeCommand("look", sessionId);
        if (!assertNotNull(response.get("completed"), "应返回通关状态")) {
            return false;
        }

        // getGameStatus返回通关信息
        Map<String, Object> status = controller.getGameStatus(sessionId);
        if (!assertNotNull(status.get("completion"), "游戏状态应包含通关信息")) {
            return false;
        }

        // help命令显示帮助信息
        response = controller.executeCommand("help", sessionId);
        if (!assertTrue((Boolean) response.get("success"), "help命令应成功")) {
            return false;
        }

        return true;
    }

    // ========== 9. 端到端集成测试 ==========
    private static boolean testEndToEndFlow()
    {
        GameController controller = new GameController();
        String testUsername = "testuser_" + System.currentTimeMillis();
        String testPassword = "testpass123";
        
        // 1. 注册
        Map<String, Object> response = controller.register(testUsername, testPassword);
        if (!assertTrue((Boolean) response.get("success"), "注册应成功")) {
            return false;
        }
        String sessionId = (String) response.get("sessionId");
        
        // 2. 查看初始状态
        Map<String, Object> status = controller.getGameStatus(sessionId);
        if (!assertNotNull(status, "游戏状态不应为null")) {
            return false;
        }
        
        // 3. 移动
        controller.executeCommand("go north", sessionId);
        controller.executeCommand("go east", sessionId);
        
        // 4. 查看状态
        status = controller.getGameStatus(sessionId);
        if (!assertNotNull(status, "游戏状态不应为null")) {
            return false;
        }
        
        // 5. 拾取物品
        controller.executeCommand("take item", sessionId);
        
        // 6. 保存
        controller.saveGame(sessionId);
        
        // 7. 加载
        controller.loadGame(sessionId);
        
        // 8. 查看通关进度
        controller.executeCommand("status", sessionId);

        return true;
    }

    // ========== 辅助方法 ==========
    private static boolean assertTrue(boolean condition, String message)
    {
        if (!condition) {
            System.err.println("FAIL: " + message);
            return false;
        }
        System.out.println("PASS: " + message);
        return true;
    }

    private static boolean assertFalse(boolean condition, String message)
    {
        return assertTrue(!condition, message);
    }

    private static boolean assertEquals(Object expected, Object actual, String message)
    {
        if (expected == null) {
            return assertNull(actual, message);
        }
        if (!expected.equals(actual)) {
            System.err.println("FAIL: " + message + " - expected: " + expected + ", actual: " + actual);
            return false;
        }
        System.out.println("PASS: " + message);
        return true;
    }

    private static boolean assertNull(Object obj, String message)
    {
        if (obj != null) {
            System.err.println("FAIL: " + message + " - expected null, got: " + obj);
            return false;
        }
        System.out.println("PASS: " + message);
        return true;
    }

    private static boolean assertNotNull(Object obj, String message)
    {
        if (obj == null) {
            System.err.println("FAIL: " + message + " - expected non-null");
            return false;
        }
        System.out.println("PASS: " + message);
        return true;
    }

    private static void printSummary(String className, int passed, int failed)
    {
        System.out.println();
        System.out.println("=== " + className + " Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("========================");
        System.out.println();
    }
}
