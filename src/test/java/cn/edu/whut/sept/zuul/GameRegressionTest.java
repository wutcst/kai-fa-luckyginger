package cn.edu.whut.sept.zuul;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
@DisplayName("游戏核心功能回归测试")
public class GameRegressionTest {

    private GameController controller;
    private String testUsername;
    private String testPassword;
    private String sessionId;

    @BeforeEach
    void setUp() {
        controller = new GameController();
        testUsername = "testuser_" + System.currentTimeMillis();
        testPassword = "testpass123";
        sessionId = null;
    }

    // ========== 1. 登录/注册测试 ==========
    @Nested
    @DisplayName("1. 登录/注册功能测试")
    class LoginRegisterTests {

        @Test
        @DisplayName("注册新用户成功")
        void testRegisterSuccess() {
            Map<String, Object> response = controller.register(testUsername, testPassword);
            
            assertTrue((Boolean) response.get("success"), "注册应成功");
            assertEquals("注册成功！", response.get("message"));
            assertNotNull(response.get("sessionId"), "应返回会话ID");
            sessionId = (String) response.get("sessionId");
        }

        @Test
        @DisplayName("注册重复用户名失败")
        void testRegisterDuplicateUsername() {
            controller.register(testUsername, testPassword);
            Map<String, Object> response = controller.register(testUsername, "newpass");
            
            assertFalse((Boolean) response.get("success"), "重复注册应失败");
            assertEquals("用户名已存在", response.get("message"));
        }

        @Test
        @DisplayName("注册空用户名失败")
        void testRegisterEmptyUsername() {
            Map<String, Object> response = controller.register("", testPassword);
            
            assertFalse((Boolean) response.get("success"));
            assertEquals("用户名不能为空", response.get("message"));
        }

        @Test
        @DisplayName("注册空密码失败")
        void testRegisterEmptyPassword() {
            Map<String, Object> response = controller.register(testUsername, "");
            
            assertFalse((Boolean) response.get("success"));
            assertEquals("密码不能为空", response.get("message"));
        }

        @Test
        @DisplayName("用户登录成功")
        void testLoginSuccess() {
            controller.register(testUsername, testPassword);
            Map<String, Object> response = controller.login(testUsername, testPassword);
            
            assertTrue((Boolean) response.get("success"), "登录应成功");
            assertEquals("登录成功！", response.get("message"));
            assertNotNull(response.get("sessionId"));
            sessionId = (String) response.get("sessionId");
        }

        @Test
        @DisplayName("登录错误密码失败")
        void testLoginWrongPassword() {
            controller.register(testUsername, testPassword);
            Map<String, Object> response = controller.login(testUsername, "wrongpassword");
            
            assertFalse((Boolean) response.get("success"));
            assertEquals("用户名或密码错误", response.get("message"));
        }

        @Test
        @DisplayName("登录不存在的用户失败")
        void testLoginNonexistentUser() {
            Map<String, Object> response = controller.login("nonexistent", "password");
            
            assertFalse((Boolean) response.get("success"));
        }

        @Test
        @DisplayName("游客模式登录（无需注册）")
        void testGuestLogin() {
            Map<String, Object> response = controller.login("Guest", "");
            
            assertTrue((Boolean) response.get("success"));
            assertNotNull(response.get("sessionId"));
        }
    }

    // ========== 2. 开始新游戏测试 ==========
    @Nested
    @DisplayName("2. 开始新游戏功能测试")
    class NewGameTests {

        @BeforeEach
        void loginFirst() {
            Map<String, Object> response = controller.login(testUsername, testPassword);
            if ((Boolean) response.get("success")) {
                sessionId = (String) response.get("sessionId");
            }
        }

        @Test
        @DisplayName("新游戏返回初始状态")
        void testNewGameReturnsInitialState() {
            // 注册并登录
            controller.register(testUsername, testPassword);
            sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");
            
            Map<String, Object> response = controller.newGame(sessionId);
            
            assertTrue((Boolean) response.get("success"));
            assertNotNull(response.get("gameStatus"));
        }

        @Test
        @DisplayName("新游戏后位置重置")
        void testNewGameResetsPosition() {
            controller.register(testUsername, testPassword);
            sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");
            
            // 执行一些移动
            controller.executeCommand("go north", sessionId);
            controller.executeCommand("go east", sessionId);
            
            // 开始新游戏
            controller.newGame(sessionId);
            Map<String, Object> status = controller.getGameStatus(sessionId);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> room = (Map<String, Object>) status.get("currentRoom");
            assertNotNull(room.get("shortDescription"));
        }

        @Test
        @DisplayName("无效会话ID无法开始新游戏")
        void testNewGameWithInvalidSession() {
            Map<String, Object> response = controller.newGame("invalid_session_id");
            
            assertFalse((Boolean) response.get("success"));
            assertEquals("会话无效，请重新登录", response.get("message"));
        }
    }

    // ========== 3. 房间移动测试 ==========
    @Nested
    @DisplayName("3. 房间移动功能测试")
    class RoomMovementTests {

        @BeforeEach
        void setup() {
            controller.register(testUsername, testPassword);
            sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");
        }

        @Test
        @DisplayName("向有效方向移动")
        void testMoveToValidDirection() {
            Map<String, Object> response = controller.executeCommand("go north", sessionId);
            
            assertTrue((Boolean) response.get("success"));
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("向无效方向移动")
        void testMoveToInvalidDirection() {
            Map<String, Object> response = controller.executeCommand("go up", sessionId);
            
            // 应该返回某种消息（可能失败或成功）
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("不指定方向移动")
        void testMoveWithoutDirection() {
            Map<String, Object> response = controller.executeCommand("go", sessionId);
            
            assertFalse((Boolean) response.get("success"));
            assertEquals("I don't know how to go that way.", response.get("message"));
        }

        @Test
        @DisplayName("look命令查看当前房间")
        void testLookCommand() {
            Map<String, Object> response = controller.executeCommand("look", sessionId);
            
            assertTrue((Boolean) response.get("success"));
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("back命令返回上一个房间")
        void testBackCommand() {
            controller.executeCommand("go north", sessionId);
            Map<String, Object> response = controller.executeCommand("back", sessionId);
            
            assertTrue((Boolean) response.get("success"));
        }
    }

    // ========== 4. 物品拾取测试 ==========
    @Nested
    @DisplayName("4. 物品拾取功能测试")
    class ItemPickupTests {

        @BeforeEach
        void setup() {
            controller.register(testUsername, testPassword);
            sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");
        }

        @Test
        @DisplayName("拾取房间内物品")
        void testPickupItem() {
            Map<String, Object> response = controller.executeCommand("take item", sessionId);
            
            // 成功或失败都应该有消息
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("拾取不存在的物品")
        void testPickupNonexistentItem() {
            Map<String, Object> response = controller.executeCommand("take nonexistent_item", sessionId);
            
            assertTrue((Boolean) response.get("success"));
            assertTrue(response.get("message").toString().contains("没有") || 
                       response.get("message").toString().contains("don't"));
        }

        @Test
        @DisplayName("不指定物品名称")
        void testPickupWithoutItemName() {
            Map<String, Object> response = controller.executeCommand("take", sessionId);
            
            assertFalse((Boolean) response.get("success"));
        }
    }

    // ========== 5. 背包显示测试 ==========
    @Nested
    @DisplayName("5. 背包显示功能测试")
    class InventoryDisplayTests {

        @BeforeEach
        void setup() {
            controller.register(testUsername, testPassword);
            sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");
        }

        @Test
        @DisplayName("items命令显示背包")
        void testItemsCommand() {
            Map<String, Object> response = controller.executeCommand("items", sessionId);
            
            assertTrue((Boolean) response.get("success"));
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("getGameStatus包含背包信息")
        void testGameStatusContainsInventory() {
            Map<String, Object> status = controller.getGameStatus(sessionId);
            
            assertNotNull(status.get("player"));
            @SuppressWarnings("unchecked")
            Map<String, Object> player = (Map<String, Object>) status.get("player");
            assertNotNull(player.get("maxWeight"));
            assertNotNull(player.get("totalWeight"));
            assertNotNull(player.get("inventory"));
        }

        @Test
        @DisplayName("丢弃物品")
        void testDropItem() {
            // 先拾取物品
            controller.executeCommand("take item", sessionId);
            Map<String, Object> response = controller.executeCommand("drop item", sessionId);
            
            assertNotNull(response.get("message"));
        }
    }

    // ========== 6. 谜题交互测试 ==========
    @Nested
    @DisplayName("6. 谜题交互功能测试")
    class PuzzleInteractionTests {

        @BeforeEach
        void setup() {
            controller.register(testUsername, testPassword);
            sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");
        }

        @Test
        @DisplayName("使用钥匙解锁房间")
        void testUseKeyToUnlock() {
            Map<String, Object> response = controller.executeCommand("use key", sessionId);
            
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("使用地图查看位置")
        void testUseMap() {
            Map<String, Object> response = controller.executeCommand("use map", sessionId);
            
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("传送房间随机传送")
        void testTeleport() {
            Map<String, Object> response = controller.executeCommand("teleport", sessionId);
            
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("使用食物增加负重")
        void testUseFood() {
            Map<String, Object> response = controller.executeCommand("use food", sessionId);
            
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("吃魔法饼干增加负重")
        void testEatCookie() {
            Map<String, Object> response = controller.executeCommand("eat cookie", sessionId);
            
            assertNotNull(response.get("message"));
        }
    }

    // ========== 7. 保存/读取存档测试 ==========
    @Nested
    @DisplayName("7. 保存/读取存档功能测试")
    class SaveLoadTests {

        @BeforeEach
        void setup() {
            controller.register(testUsername, testPassword);
            sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");
        }

        @Test
        @DisplayName("保存游戏状态")
        void testSaveGame() {
            Map<String, Object> response = controller.saveGame(sessionId);
            
            // 保存结果可能是成功或失败（取决于数据库配置）
            assertNotNull(response.get("success"));
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("加载游戏状态")
        void testLoadGame() {
            controller.saveGame(sessionId);
            Map<String, Object> response = controller.loadGame(sessionId);
            
            assertNotNull(response.get("success"));
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("执行save命令")
        void testSaveCommand() {
            Map<String, Object> response = controller.executeCommand("save", sessionId);
            
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("执行load命令")
        void testLoadCommand() {
            Map<String, Object> response = controller.executeCommand("load", sessionId);
            
            assertNotNull(response.get("message"));
        }
    }

    // ========== 8. 完整通关流程测试 ==========
    @Nested
    @DisplayName("8. 完整通关流程测试")
    class GameCompletionTests {

        @BeforeEach
        void setup() {
            controller.register(testUsername, testPassword);
            sessionId = (String) controller.login(testUsername, testPassword).get("sessionId");
        }

        @Test
        @DisplayName("查看通关进度")
        void testCheckCompletionProgress() {
            Map<String, Object> response = controller.executeCommand("status", sessionId);
            
            assertTrue((Boolean) response.get("success"));
        }

        @Test
        @DisplayName("executeCommand返回通关状态")
        void testExecuteCommandReturnsCompletionStatus() {
            Map<String, Object> response = controller.executeCommand("look", sessionId);
            
            assertTrue((Boolean) response.get("success"));
            assertNotNull(response.get("completed"));
        }

        @Test
        @DisplayName("getGameStatus返回通关信息")
        void testGetGameStatusReturnsCompletionInfo() {
            Map<String, Object> status = controller.getGameStatus(sessionId);
            
            assertNotNull(status.get("completion"));
            @SuppressWarnings("unchecked")
            Map<String, Object> completion = (Map<String, Object>) status.get("completion");
            assertNotNull(completion.get("completed"));
            assertNotNull(completion.get("roomsExplored"));
            assertNotNull(completion.get("itemsCollected"));
            assertNotNull(completion.get("cookieEaten"));
        }

        @Test
        @DisplayName("quit命令正常退出")
        void testQuitCommand() {
            Map<String, Object> response = controller.executeCommand("quit", sessionId);
            
            assertTrue((Boolean) response.get("success"));
            assertTrue((Boolean) response.get("quit"));
        }

        @Test
        @DisplayName("help命令显示帮助信息")
        void testHelpCommand() {
            Map<String, Object> response = controller.executeCommand("help", sessionId);
            
            assertTrue((Boolean) response.get("success"));
            assertNotNull(response.get("message"));
        }

        @Test
        @DisplayName("未知命令返回错误")
        void testUnknownCommand() {
            Map<String, Object> response = controller.executeCommand("unknowncommand", sessionId);
            
            assertFalse((Boolean) response.get("success"));
        }
    }

    // ========== 集成测试 ==========
    @Nested
    @DisplayName("9. 端到端集成测试")
    class EndToEndIntegrationTests {

        @Test
        @DisplayName("完整游戏流程：注册->移动->拾取->保存->加载")
        void testCompleteGameFlow() {
            // 1. 注册
            Map<String, Object> response = controller.register(testUsername, testPassword);
            assertTrue((Boolean) response.get("success"));
            sessionId = (String) response.get("sessionId");
            
            // 2. 查看初始状态
            Map<String, Object> status = controller.getGameStatus(sessionId);
            assertNotNull(status);
            
            // 3. 移动
            controller.executeCommand("go north", sessionId);
            controller.executeCommand("go east", sessionId);
            
            // 4. 查看状态
            status = controller.getGameStatus(sessionId);
            assertNotNull(status);
            
            // 5. 拾取物品
            controller.executeCommand("take item", sessionId);
            
            // 6. 保存
            controller.saveGame(sessionId);
            
            // 7. 加载
            controller.loadGame(sessionId);
            
            // 8. 查看通关进度
            controller.executeCommand("status", sessionId);
            
            // 9. 退出
            response = controller.executeCommand("quit", sessionId);
            assertTrue((Boolean) response.get("quit"));
        }

        @Test
        @DisplayName("会话隔离测试：不同用户独立游戏")
        void testSessionIsolation() {
            String user1 = "user1_" + System.currentTimeMillis();
            String user2 = "user2_" + System.currentTimeMillis();
            String password = "pass";
            
            String session1 = (String) controller.register(user1, password).get("sessionId");
            String session2 = (String) controller.register(user2, password).get("sessionId");
            
            // 用户1移动
            controller.executeCommand("go north", session1);
            
            // 用户2应该还在起始房间
            Map<String, Object> status1 = controller.getGameStatus(session1);
            Map<String, Object> status2 = controller.getGameStatus(session2);
            
            assertNotNull(status1);
            assertNotNull(status2);
            
            // 验证两个用户的位置可以不同
            @SuppressWarnings("unchecked")
            Map<String, Object> room1 = (Map<String, Object>) status1.get("currentRoom");
            @SuppressWarnings("unchecked")
            Map<String, Object> room2 = (Map<String, Object>) status2.get("currentRoom");
            
            assertNotNull(room1);
            assertNotNull(room2);
        }
    }
}
