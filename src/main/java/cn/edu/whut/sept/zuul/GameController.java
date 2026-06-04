/**
 * 游戏控制器 - REST API端点
 *
 * <p>该类提供HTTP REST API接口，用于外部系统与游戏进行交互。
 * 支持命令执行、玩家管理、游戏保存加载等功能。
 *
 * <p>主要功能包括：
 * <ul>
 *   <li>命令执行接口：允许外部系统向游戏发送命令</li>
 *   <li>玩家管理：创建、获取、删除玩家</li>
 *   <li>游戏状态管理：保存和加载游戏进度</li>
 *   <li>游戏指标：获取游戏完成情况</li>
 * </ul>
 *
 * @author Web API实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring Boot REST控制器
 *
 * <p>处理所有与游戏相关的HTTP请求，
 * 提供JSON格式的API响应。
 */
@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    /**
     * 存储所有活跃游戏的会话映射表
     *
     * <p>键为会话ID，值为游戏实例。
     * 使用ConcurrentHashMap保证线程安全。
     */
    private final Map<String, Game> activeGames = new ConcurrentHashMap<>();

    /**
     * 存储所有玩家的映射表
     *
     * <p>键为用户ID，值为玩家对象。
     * 用于快速查找和管理玩家信息。
     */
    private final Map<String, Player> allPlayers = new ConcurrentHashMap<>();

    /**
     * 存储会话ID到用户ID的映射
     *
     * <p>用于追踪每个会话对应的玩家。
     */
    private final Map<String, String> sessionToUser = new ConcurrentHashMap<>();

    /**
     * 创建一个新的游戏会话
     *
     * <p>如果提供了用户ID，则为该用户创建或获取玩家对象。
     * 生成的会话ID可用于后续的游戏交互。
     *
     * @param userId 可选的用户ID，用于关联已有玩家
     * @param userName 可选的用户名，用于创建新玩家
     * @param request HTTP请求对象，用于获取客户端信息
     * @return 包含会话ID的JSON响应
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createGame(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userName,
            HttpServletRequest request) {

        // 生成新的会话ID
        String sessionId = UUID.randomUUID().toString();

        // 创建新游戏
        Game game = new Game();
        activeGames.put(sessionId, game);

        // 处理用户关联
        if (userId != null && !userId.isEmpty()) {
            // 使用已有用户ID
            Player player = allPlayers.get(userId);
            if (player == null) {
                player = game.getPlayer();
                player.setUserId(userId);
                allPlayers.put(userId, player);
            } else {
                // 替换游戏中的玩家对象
                game = replacePlayerInGame(game, player);
                activeGames.put(sessionId, game);
            }
            sessionToUser.put(sessionId, userId);
        } else if (userName != null && !userName.isEmpty()) {
            // 使用用户名创建新玩家
            Player player = game.getPlayer();
            String newUserId = "user_" + System.currentTimeMillis();
            player.setUserId(newUserId);
            allPlayers.put(newUserId, player);
            sessionToUser.put(sessionId, newUserId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", sessionId);
        response.put("message", "游戏会话创建成功");
        response.put("player", game.getPlayer().getInfo());

        return ResponseEntity.ok(response);
    }

    /**
     * 执行游戏命令
     *
     * <p>接收用户输入的命令并执行，返回执行结果。
     * 这是游戏的核心交互接口。
     *
     * @param sessionId 会话ID，用于识别游戏实例
     * @param command 用户输入的命令（格式：command word 或 command word word）
     * @return 包含命令执行结果的JSON响应
     */
    @PostMapping("/command")
    public ResponseEntity<Map<String, Object>> executeCommand(
            @RequestParam String sessionId,
            @RequestParam String command) {

        Game game = activeGames.get(sessionId);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "会话不存在或已过期"));
        }

        // 解析命令
        String[] parts = command.trim().split("\\s+");
        String commandWord = parts[0].toLowerCase();
        String secondWord = parts.length > 1 ? parts[1] : null;

        // 创建命令对象
        Command cmd = new Command();
        cmd.setCommandWord(commandWord);
        cmd.setSecondWord(secondWord);

        // 执行命令
        Parser parser = game.getParser();
        parser.setCommand(cmd);

        // 捕获命令输出
        String output = captureCommandOutput(() -> {
            return game.processCommand(cmd);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("output", output);
        response.put("currentRoom", game.getPlayer().getCurrentRoom().getShortDescription());
        response.put("playerInfo", game.getPlayer().getInfo());

        return ResponseEntity.ok(response);
    }

    /**
     * 保存游戏状态
     *
     * <p>将当前游戏状态保存到数据库。
     * 只能保存已关联用户的游戏会话。
     *
     * @param sessionId 会话ID
     * @return 保存结果的JSON响应
     */
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveGame(@RequestParam String sessionId) {
        Game game = activeGames.get(sessionId);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "会话不存在"));
        }

        String userId = sessionToUser.get(sessionId);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "该会话未关联用户，无法保存"));
        }

        GameStateManager manager = new GameStateManager(game);
        boolean success = manager.saveGameState();

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "游戏保存成功" : "游戏保存失败");

        return ResponseEntity.ok(response);
    }

    /**
     * 加载游戏状态
     *
     * <p>从数据库加载已保存的游戏状态。
     * 只会加载与用户关联的游戏会话。
     *
     * @param sessionId 会话ID
     * @return 加载结果的JSON响应
     */
    @PostMapping("/load")
    public ResponseEntity<Map<String, Object>> loadGame(@RequestParam String sessionId) {
        Game game = activeGames.get(sessionId);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "会话不存在"));
        }

        String userId = sessionToUser.get(sessionId);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "该会话未关联用户，无法加载"));
        }

        GameStateManager manager = new GameStateManager(game);
        boolean success = manager.loadGameState();

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "游戏加载成功" : "未找到保存的游戏");
        response.put("currentRoom", game.getPlayer().getCurrentRoom().getLongDescription());

        return ResponseEntity.ok(response);
    }

    /**
     * 获取玩家信息
     *
     * @param userId 用户ID
     * @return 玩家信息的JSON响应
     */
    @GetMapping("/player/{userId}")
    public ResponseEntity<Map<String, Object>> getPlayer(@PathVariable String userId) {
        Player player = allPlayers.get(userId);
        if (player == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "玩家不存在"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("player", player.getInfo());

        return ResponseEntity.ok(response);
    }

    /**
     * 获取游戏会话状态
     *
     * @param sessionId 会话ID
     * @return 会话状态的JSON响应
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<Map<String, Object>> getSession(@PathVariable String sessionId) {
        Game game = activeGames.get(sessionId);
        if (game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "会话不存在"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", sessionId);
        response.put("currentRoom", game.getPlayer().getCurrentRoom().getLongDescription());
        response.put("playerInfo", game.getPlayer().getInfo());

        String userId = sessionToUser.get(sessionId);
        if (userId != null) {
            response.put("userId", userId);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 获取游戏完成情况统计
     *
     * <p>检查玩家的游戏完成情况，
     * 包括访问的房间数、收集的物品数等。
     *
     * @param userId 用户ID
     * @return 完成情况统计的JSON响应
     */
    @GetMapping("/stats/{userId}")
    public ResponseEntity<Map<String, Object>> getGameStats(@PathVariable String userId) {
        Player player = allPlayers.get(userId);
        if (player == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "玩家不存在"));
        }

        GameCompletionChecker.CompletionInfo info =
                GameCompletionChecker.checkCompletion(player);

        Map<String, Object> response = new HashMap<>();
        response.put("completed", info.isCompleted());
        response.put("roomsExplored", info.getRoomsExplored());
        response.put("itemsCollected", info.getItemsCollected());
        response.put("cookieEaten", info.isCookieEaten());

        return ResponseEntity.ok(response);
    }

    /**
     * 创建新的游戏
     *
     * <p>为指定用户创建全新的游戏实例，
     * 重置玩家的所有状态。
     *
     * @param userId 用户ID
     * @return 新游戏信息的JSON响应
     */
    @PostMapping("/new/{userId}")
    public ResponseEntity<Map<String, Object>> newGame(@PathVariable String userId) {
        Player existingPlayer = allPlayers.get(userId);

        // 创建新游戏
        Game newGame = new Game();

        if (existingPlayer != null) {
            // 复用已有玩家对象
            newGame = replacePlayerInGame(newGame, existingPlayer);
        }

        // 生成新会话ID
        String sessionId = UUID.randomUUID().toString();
        activeGames.put(sessionId, newGame);
        sessionToUser.put(sessionId, userId);

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", sessionId);
        response.put("message", "新游戏创建成功");
        response.put("currentRoom", newGame.getPlayer().getCurrentRoom().getLongDescription());
        response.put("playerInfo", newGame.getPlayer().getInfo());

        return ResponseEntity.ok(response);
    }

    /**
     * 删除游戏会话
     *
     * @param sessionId 会话ID
     * @return 删除结果的JSON响应
     */
    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<Map<String, Object>> deleteSession(@PathVariable String sessionId) {
        Game removed = activeGames.remove(sessionId);
        sessionToUser.remove(sessionId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", removed != null);
        response.put("message", removed != null ? "会话已删除" : "会话不存在");

        return ResponseEntity.ok(response);
    }

    /**
     * 获取所有活跃会话
     *
     * @return 会话列表的JSON响应
     */
    @GetMapping("/sessions")
    public ResponseEntity<Map<String, Object>> getAllSessions() {
        Map<String, Object> response = new HashMap<>();
        response.put("activeSessions", activeGames.size());
        response.put("sessions", activeGames.keySet());

        return ResponseEntity.ok(response);
    }

    /**
     * 替换游戏中的玩家对象
     *
     * <p>该方法创建一个新游戏实例，
     * 但使用指定的玩家对象替代默认的玩家。
     *
     * @param game 新创建的游戏实例
     * @param player 要使用的玩家对象
     * @return 替换了玩家对象的新游戏
     */
    private Game replacePlayerInGame(Game game, Player player) {
        // 创建新游戏并替换玩家对象
        try {
            java.lang.reflect.Field playerField = Game.class.getDeclaredField("player");
            playerField.setAccessible(true);
            playerField.set(game, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

    /**
     * 捕获命令执行输出
     *
     * <p>该方法通过重定向System.out来捕获命令的文本输出。
     * 用于获取游戏命令的执行结果。
     *
     * @param action 要执行的命令动作
     * @return 命令的文本输出
     */
    private String captureCommandOutput(java.util.function.Supplier<Boolean> action) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream originalOut = System.out;
        System.setOut(new java.io.PrintStream(baos));

        try {
            action.get();
        } finally {
            System.setOut(originalOut);
        }

        return baos.toString().trim();
    }
}
