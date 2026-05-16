/**
 * 游戏Web API控制器
 * 处理HTTP请求，执行游戏命令并返回JSON响应
 * 
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    // 多玩家会话管理：使用sessionId作为键
    private Map<String, GameSession> sessions;
    private DatabaseManager dbManager;
    
    /**
     * 创建游戏控制器
     */
    public GameController() {
        sessions = new HashMap<>();
        dbManager = DatabaseManager.getInstance();
    }
    
    /**
     * 游戏会话类
     */
    private static class GameSession {
        Game game;
        Player player;
        Parser parser;
        String username;
        Integer userId;
        
        GameSession(String username, Integer userId) {
            this.username = username;
            this.userId = userId;
            this.game = new Game();
            this.player = game.getPlayer();
            this.player.setUserId(userId);
            this.player.setName(username);
            this.parser = game.getParser();
            
            // 创建游戏记录（仅当userId不为null时）
            if (userId != null) {
                DatabaseManager dbManager = DatabaseManager.getInstance();
                Integer recordId = dbManager.createGameRecord(userId);
                this.player.setGameRecordId(recordId);
            }
        }
    }
    
    /**
     * 生成唯一的会话ID。
     * 
     * <p>会话ID格式：session_[时间戳]_[随机数(0-9999)]
     * 使用时间戳和随机数组合，确保会话ID的唯一性。
     * 
     * @return 生成的会话ID字符串
     */
    private String generateSessionId() {
        return "session_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }
    
    /**
     * 注册新用户。
     * 
     * <p>注册流程：
     * <ol>
     *   <li>验证用户名和密码是否为空</li>
     *   <li>检查用户名是否已存在</li>
     *   <li>调用数据库管理器注册用户</li>
     *   <li>注册成功后自动登录并创建游戏会话</li>
     * </ol>
     * 
     * <p>返回的响应Map包含以下字段：
     * <ul>
     *   <li><code>success</code>: 注册是否成功（boolean）</li>
     *   <li><code>message</code>: 操作结果消息（String）</li>
     *   <li><code>sessionId</code>: 会话ID（String，仅在成功时返回）</li>
     *   <li><code>username</code>: 用户名（String，仅在成功时返回）</li>
     * </ul>
     * 
     * @param username 用户名，不能为空或仅包含空白字符
     * @param password 密码，不能为空或仅包含空白字符
     * @return 包含操作结果的Map对象，格式为 {"success": boolean, "message": String, ...}
     */
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> response = new HashMap<>();
        
        if (username == null || username.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "用户名不能为空");
            return response;
        }
        
        if (password == null || password.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "密码不能为空");
            return response;
        }
        
        if (dbManager.userExists(username)) {
            response.put("success", false);
            response.put("message", "用户名已存在");
            return response;
        }

        String registerError = dbManager.registerUserWithMessage(username, password);
        if (registerError == null) {
            // 注册成功后自动登录
            Integer userId = dbManager.loginUser(username, password);
            if (userId != null) {
                String sessionId = generateSessionId();
                sessions.put(sessionId, new GameSession(username, userId));
                response.put("success", true);
                response.put("message", "注册成功！");
                response.put("sessionId", sessionId);
                response.put("username", username);
            } else {
                response.put("success", false);
                response.put("message", "注册成功，但登录失败");
            }
        } else {
            response.put("success", false);
            response.put("message", registerError);
        }
        
        return response;
    }
    
    /**
     * 用户登录方法。
     * 
     * <p>登录流程：
     * <ol>
     *   <li>验证用户名和密码是否为空</li>
     *   <li>调用数据库管理器验证用户凭据</li>
     *   <li>验证成功后创建或更新游戏会话</li>
     *   <li>返回会话ID和用户信息</li>
     * </ol>
     * 
     * <p>如果用户已有会话，会先移除旧会话再创建新会话，确保每次登录都是新游戏。
     * 
     * <p>返回的响应Map包含以下字段：
     * <ul>
     *   <li><code>success</code>: 登录是否成功（boolean）</li>
     *   <li><code>message</code>: 操作结果消息（String）</li>
     *   <li><code>sessionId</code>: 会话ID（String，仅在成功时返回）</li>
     *   <li><code>username</code>: 用户名（String，仅在成功时返回）</li>
     * </ul>
     * 
     * @param username 用户名，不能为空或仅包含空白字符
     * @param password 密码，不能为空或仅包含空白字符
     * @return 包含操作结果的Map对象，格式为 {"success": boolean, "message": String, ...}
     */
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> response = new HashMap<>();
        
        // 输入验证
        if (username == null || username.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "用户名不能为空");
            return response;
        }
        if (password == null || password.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "密码不能为空");
            return response;
        }
        
        System.out.println("=== GameController.login ===");
        System.out.println("用户名: " + username);
        System.out.println("密码长度: " + password.length());
        
        Integer userId = dbManager.loginUser(username, password);
        System.out.println("loginUser返回的userId: " + userId);
        
        if (userId != null) {
            // 如果用户已有会话，先移除旧会话（确保每次登录都是新游戏）
            String oldSessionId = null;
            for (Map.Entry<String, GameSession> entry : sessions.entrySet()) {
                if (entry.getValue().username.equals(username) && 
                    entry.getValue().userId.equals(userId)) {
                    oldSessionId = entry.getKey();
                    break;
                }
            }
            if (oldSessionId != null) {
                sessions.remove(oldSessionId);
            }
            
            // 创建新会话（新游戏）
            String sessionId = generateSessionId();
            GameSession session = new GameSession(username, userId);
            sessions.put(sessionId, session);
            
            // 尝试自动加载保存的游戏进度
            GameStateManager stateManager = new GameStateManager(session.game);
            boolean loaded = stateManager.loadGameState();
            
            if (loaded) {
                response.put("success", true);
                response.put("message", "登录成功！已恢复上次的游戏进度");
                response.put("sessionId", sessionId);
                response.put("username", username);
                response.put("gameLoaded", true);
                // 返回当前游戏状态
                response.put("gameStatus", getGameStatus(sessionId));
            } else {
                response.put("success", true);
                response.put("message", "登录成功！开始新游戏");
                response.put("sessionId", sessionId);
                response.put("username", username);
                response.put("gameLoaded", false);
            }
        } else {
            response.put("success", false);
            response.put("message", "用户名或密码错误");
        }
        
        return response;
    }
    
    /**
     * 获取游戏会话
     */
    private GameSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
    
    /**
     * 执行游戏命令（向后兼容，使用默认会话）
     * 
     * @param commandString 命令字符串
     * @return 包含执行结果的Map
     */
    public Map<String, Object> executeCommand(String commandString) {
        // 如果没有会话，创建一个临时会话（游客模式）
        if (sessions.isEmpty()) {
            // 创建匿名会话（每次都是新游戏）
            String sessionId = generateSessionId();
            sessions.put(sessionId, new GameSession("Guest", null));
            return executeCommand(commandString, sessionId);
        }
        // 使用第一个会话
        return executeCommand(commandString, sessions.keySet().iterator().next());
    }
    
    /**
     * 执行游戏命令
     * 
     * @param commandString 命令字符串
     * @param sessionId 会话ID
     * @return 包含执行结果的Map
     */
    public Map<String, Object> executeCommand(String commandString, String sessionId) {
        Map<String, Object> response = new HashMap<>();
        
        GameSession session = getSession(sessionId);
        if (session == null) {
            response.put("success", false);
            response.put("message", "会话无效，请重新登录");
            return response;
        }
        
        Game game = session.game;
        Player player = session.player;
        Parser parser = session.parser;
        
        try {
            // 处理quit命令
            if (commandString.trim().equalsIgnoreCase("quit")) {
                // 更新游戏记录
                if (player.getGameRecordId() != null) {
                    GameCompletionChecker.CompletionInfo info = 
                        GameCompletionChecker.checkCompletion(player);
                    dbManager.updateGameRecord(
                        player.getGameRecordId(),
                        info.isCompleted(),
                        info.getRoomsExplored(),
                        info.getItemsCollected(),
                        info.isCookieEaten()
                    );
                }
                // 移除会话
                sessions.remove(sessionId);
                response.put("success", true);
                response.put("message", "感谢游玩！再见！");
                response.put("quit", true);
                return response;
            }
            
            // 解析命令（确保命令字符串被正确trim）
            String trimmedCommand = commandString != null ? commandString.trim() : "";
            Command command = parser.parseCommand(trimmedCommand);
            
            if (command.isUnknown()) {
                response.put("success", false);
                response.put("message", "我不知道你在说什么...");
                return response;
            }
            
            // 执行命令并捕获输出
            String output = captureCommandOutput(command, game, player);
            
            // 检查通关状态
            GameCompletionChecker.CompletionInfo info = 
                GameCompletionChecker.checkCompletion(player);
            response.put("completed", info.isCompleted());
            
            // 将CompletionInfo转换为Map以便JSON序列化
            Map<String, Object> progressMap = new HashMap<>();
            progressMap.put("completed", info.isCompleted());
            progressMap.put("atStartRoom", info.isAtStartRoom());
            progressMap.put("roomsExplored", info.getRoomsExplored());
            progressMap.put("totalRooms", info.getTotalRooms());
            progressMap.put("allRoomsExplored", info.isAllRoomsExplored());
            progressMap.put("itemsCollected", info.getItemsCollected());
            progressMap.put("totalItems", info.getTotalItems());
            progressMap.put("allItemsCollected", info.isAllItemsCollected());
            progressMap.put("cookieEaten", info.isCookieEaten());
            response.put("progress", progressMap);
            
            response.put("success", true);
            response.put("message", output);
            response.put("quit", false);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "执行命令时出错: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
    
    /**
     * 捕获命令执行的输出
     */
    private String captureCommandOutput(Command command, Game game, Player player) {
        StringBuilder output = new StringBuilder();
        
        String commandWord = command.getCommandWord();
        
        // 安全检查：如果命令词为null，返回错误消息
        if (commandWord == null) {
            return "我不知道你在说什么...";
        }
        
        // 确保命令词是小写的（防止大小写问题）
        commandWord = commandWord.toLowerCase().trim();
        
        if (commandWord.equals("go")) {
            if (!command.hasSecondWord()) {
                return "去哪里？";
            }
            String direction = command.getSecondWord();
            Room currentRoom = player.getCurrentRoom();

            if ("上锁的宝库".equals(currentRoom.getShortDescription())) {
                if ("north".equalsIgnoreCase(direction)) {
                    if (currentRoom instanceof LockedRoom && !((LockedRoom) currentRoom).isUnlocked()) {
                        return "宝库大门还锁着。请先使用 'use key' 解锁，然后再向北进入宝库。";
                    }
                    Room treasureInterior = game.getRoomByName("解锁后的宝库");
                    if (treasureInterior != null) {
                        game.addRoomToHistory(currentRoom);
                        player.setCurrentRoom(treasureInterior);
                        return player.getCurrentRoom().getLongDescription();
                    }
                } else if ("south".equalsIgnoreCase(direction)) {
                    Room lab = game.getRoomByName("计算机实验室");
                    if (lab != null) {
                        game.addRoomToHistory(currentRoom);
                        player.setCurrentRoom(lab);
                        return player.getCurrentRoom().getLongDescription();
                    }
                }
            }

            Room nextRoom = currentRoom.getExit(direction);
            
            if (nextRoom == null) {
                return "那里没有门！";
            } else {
                game.addRoomToHistory(currentRoom);
                player.setCurrentRoom(nextRoom);
                
                // 检查传输房间
                if (nextRoom instanceof TransporterRoom) {
                    TransporterRoom transporter = (TransporterRoom) nextRoom;
                    Room randomRoom = transporter.getRandomRoom();
                    if (randomRoom != null) {
                        output.append("你踏入了一个神秘的传输房间...\n");
                        output.append("突然，你被传送到另一个位置！\n");
                        // 传输房间已经被记录，现在记录目标房间
                        player.setCurrentRoom(randomRoom);
                    }
                }
                
                output.append(player.getCurrentRoom().getLongDescription());
            }
        } else if (commandWord.equals("look")) {
            output.append(player.getCurrentRoom().getLongDescription());
        } else if (commandWord.equals("back")) {
            Room previousRoom = game.getPreviousRoom();
            if (previousRoom == null) {
                output.append("你已经回到了起点！");
            } else {
                player.setCurrentRoom(previousRoom);
                output.append("你返回到: ").append(previousRoom.getLongDescription());
            }
        } else if (commandWord.equals("items")) {
            Room currentRoom = player.getCurrentRoom();
            output.append("房间内的物品:\n");
            String roomItems = currentRoom.getItemsString();
            if (roomItems.isEmpty()) {
                output.append("  (无)\n");
            } else {
                output.append(roomItems).append("\n");
            }
            output.append("房间总重量: ").append(String.format("%.2f", currentRoom.getTotalWeight())).append("kg\n\n");
            output.append(player.getInventoryString());
        } else if (commandWord.equals("take")) {
            if (!command.hasSecondWord()) {
                return "拾取什么？";
            }
            String itemName = command.getSecondWord();
            Room currentRoom = player.getCurrentRoom();
            Item item = currentRoom.getItem(itemName);
            
            if (item == null) {
                output.append("这里没有 ").append(itemName).append("！");
            } else if (!player.canCarry(item)) {
                output.append("你无法携带 ").append(item.getName())
                      .append("。它重 ").append(String.format("%.2f", item.getWeight()))
                      .append("kg，但你只能再携带 ")
                      .append(String.format("%.2f", player.getMaxWeight() - player.getTotalWeight()))
                      .append("kg。");
            } else {
                currentRoom.removeItem(itemName);
                player.takeItem(item);
                output.append("你拾取了 ").append(item.getName()).append("。");
            }
        } else if (commandWord.equals("drop")) {
            if (!command.hasSecondWord()) {
                return "丢弃什么？";
            }
            String itemName = command.getSecondWord();
            Item item = player.dropItem(itemName);
            
            if (item == null) {
                output.append("你没有 ").append(itemName).append("！");
            } else {
                player.getCurrentRoom().addItem(item);
                output.append("你丢弃了 ").append(item.getName()).append("。");
            }
        } else if (commandWord.equals("eat")) {
            if (!command.hasSecondWord() || !command.getSecondWord().equals("cookie")) {
                return "吃什么？";
            }
            Item cookie = player.getItem("cookie");
            if (cookie == null) {
                output.append("你没有魔法饼干！");
            } else {
                // 使用eatCookie方法，它会自动设置cookieEaten标志并移除物品
                player.eatCookie();
                player.increaseMaxWeight(5.0);
                output.append("你吃掉了魔法饼干。你的负重能力增加了5kg！\n");
                output.append("新的最大负重: ").append(String.format("%.2f", player.getMaxWeight())).append("kg");
            }
        } else if (commandWord.equals("use")) {
            if (!command.hasSecondWord()) {
                return "使用什么物品？";
            }
            String itemName = command.getSecondWord();
            Item item = player.getItem(itemName);
            
            if (item == null) {
                output.append("你没有 ").append(itemName).append("！");
            } else if (!item.isUsable()) {
                output.append(itemName).append(" 无法使用。");
            } else {
                // 使用物品
                String itemType = item.getItemType();
                Room currentRoom = player.getCurrentRoom();
                
                if ("KEY".equalsIgnoreCase(itemType)) {
                    // 使用钥匙解锁房间
                    if (currentRoom instanceof LockedRoom) {
                        LockedRoom lockedRoom = (LockedRoom) currentRoom;
                        if (lockedRoom.isUnlocked()) {
                            output.append("这个房间已经解锁了。");
                        } else if (lockedRoom.unlock(item.getName())) {
                            output.append("你使用 ").append(item.getName())
                                  .append(" 成功解锁了房间！\n")
                                  .append(currentRoom.getLongDescription());
                        } else {
                            output.append("这把钥匙无法解锁这个房间。需要 ")
                                  .append(lockedRoom.getRequiredKeyType())
                                  .append(" 类型的钥匙。");
                        }
                    } else {
                        // 检查相邻房间是否有上锁的房间
                        String[] directions = {"north", "south", "east", "west"};
                        boolean unlocked = false;
                        for (String direction : directions) {
                            Room exitRoom = currentRoom.getExit(direction);
                            if (exitRoom instanceof LockedRoom) {
                                LockedRoom lockedRoom = (LockedRoom) exitRoom;
                                if (!lockedRoom.isUnlocked() && 
                                    lockedRoom.getRequiredKeyType().equalsIgnoreCase(item.getName())) {
                                    lockedRoom.unlock(item.getName());
                                    output.append("你使用 ").append(item.getName())
                                          .append(" 解锁了 ").append(translateDirection(direction))
                                          .append(" 方向的房间！");
                                    unlocked = true;
                                    break;
                                }
                            }
                        }
                        if (!unlocked) {
                            output.append("你使用了 ").append(item.getName())
                                  .append("，但这里没有需要解锁的房间。");
                        }
                    }
                } else if ("MAP".equalsIgnoreCase(itemType)) {
                    // 使用地图
                    output.append("你打开了地图，查看当前位置的详细信息：\n")
                          .append(currentRoom.getLongDescription())
                          .append("\n\n地图显示：\n")
                          .append("- 当前房间：").append(currentRoom.getShortDescription());
                    
                    String[] directions = {"north", "south", "east", "west"};
                    boolean hasExits = false;
                    for (String direction : directions) {
                        Room exitRoom = currentRoom.getExit(direction);
                        if (exitRoom != null) {
                            if (!hasExits) {
                                output.append("\n- 出口信息：");
                                hasExits = true;
                            }
                            output.append("\n  ").append(translateDirection(direction))
                                  .append(" -> ").append(exitRoom.getShortDescription());
                        }
                    }
                    if (!hasExits) {
                        output.append("\n- 这是一个封闭的房间，没有出口。");
                    }
                } else if ("FOOD".equalsIgnoreCase(itemType)) {
                    // 使用食物
                    player.dropItem(item.getName());
                    String foodName = item.getName().toLowerCase();
                    if (foodName.contains("cookie") || foodName.contains("饼干")) {
                        player.increaseMaxWeight(2.0);
                        output.append("你吃掉了 ").append(item.getName())
                              .append("，感觉体力恢复了！\n")
                              .append("你的最大负重增加了2kg！当前最大负重: ")
                              .append(String.format("%.1f", player.getMaxWeight())).append("kg");
                    } else {
                        output.append("你吃掉了 ").append(item.getName())
                              .append("，感觉体力恢复了！");
                    }
                } else {
                    // 其他工具类物品
                    output.append("你使用了 ").append(item.getName()).append("。\n")
                          .append(item.getUseEffect());
                }
            }
        } else if (commandWord.equals("help")) {
            output.append("你可以使用以下命令:\n");
            output.append("  go <方向>  - 向指定方向移动 (north, south, east, west)\n");
            output.append("  look       - 查看当前房间的详细信息\n");
            output.append("  back       - 返回上一个房间\n");
            output.append("  take <物品> - 拾取房间内的物品\n");
            output.append("  drop <物品> - 丢弃身上的物品\n");
            output.append("  use <物品>  - 使用背包中的物品（如钥匙、地图等）\n");
            output.append("  items      - 查看房间和身上的物品\n");
            output.append("  eat cookie  - 吃掉魔法饼干（增加负重）\n");
            output.append("  status     - 查看游戏进度\n");
            output.append("  save       - 保存游戏状态\n");
            output.append("  load       - 加载游戏状态\n");
            output.append("  help       - 显示此帮助信息\n");
            output.append("  quit       - 退出游戏");
        } else if (commandWord.equals("save")) {
            // 处理save命令
            if (player.getUserId() == null) {
                output.append("保存失败：请先登录！");
            } else {
                GameStateManager stateManager = new GameStateManager(game);
                boolean success = stateManager.saveGameState();
                if (success) {
                    output.append("游戏状态已保存！");
                } else {
                    output.append("保存失败，请稍后重试。");
                }
            }
            // save命令不需要显示进度信息，直接返回
            return output.toString();
        } else if (commandWord.equals("load")) {
            // 处理load命令
            if (player.getUserId() == null) {
                output.append("加载失败：请先登录！");
            } else {
                GameStateManager stateManager = new GameStateManager(game);
                boolean loaded = stateManager.loadGameState();
                if (loaded) {
                    output.append("游戏状态已加载！\n");
                    output.append(player.getCurrentRoom().getLongDescription());
                } else {
                    output.append("没有找到保存的游戏状态，或加载失败。");
                }
            }
            // load命令不需要显示进度信息，直接返回
            return output.toString();
        } else if (commandWord.equals("status")) {
            // 处理status命令
            GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
            output.append("=== 游戏进度 ===\n");
            output.append("房间探索: ").append(info.getRoomsExplored()).append("/").append(info.getTotalRooms()).append("\n");
            output.append("物品收集: ").append(info.getItemsCollected()).append("/").append(info.getTotalItems()).append("\n");
            output.append("魔法饼干: ").append(info.isCookieEaten() ? "已吃" : "未吃").append("\n");
            output.append("当前位置: ").append(info.isAtStartRoom() ? "起始房间" : "其他房间").append("\n");
            if (info.isCompleted()) {
                output.append("\n🎉 恭喜！你已完成所有任务，游戏通关！");
            }
            // status命令不需要显示额外的进度信息，直接返回
            return output.toString();
        } else {
            // 其他命令通过Game的命令执行器处理
            // 重定向System.out以捕获输出
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream originalOut = System.out;
            System.setOut(new java.io.PrintStream(baos));
            
            try {
                // 使用Game的命令执行器处理命令
                String cmdWord = command.getCommandWord();
                if (cmdWord != null) {
                    // 获取命令执行器（通过反射访问Game的私有字段）
                    java.lang.reflect.Field field = Game.class.getDeclaredField("commandExecutors");
                    field.setAccessible(true);
                    @SuppressWarnings("unchecked")
                    java.util.HashMap<String, CommandExecutor> executors = 
                        (java.util.HashMap<String, CommandExecutor>) field.get(game);
                    
                    CommandExecutor executor = executors.get(cmdWord);
                    if (executor != null) {
                        executor.execute(command, game);
                        // 捕获输出
                        String capturedOutput = baos.toString();
                        if (!capturedOutput.isEmpty()) {
                            output.append(capturedOutput.trim());
                        }
                    } else {
                        output.append("我不知道你在说什么...");
                    }
                } else {
                    output.append("我不知道你在说什么...");
                }
            } catch (Exception e) {
                // 如果反射失败或命令执行器不存在，输出错误信息
                output.append("执行命令时出错: ").append(e.getMessage());
                e.printStackTrace();
            } finally {
                System.setOut(originalOut);
            }
        }
        
        // 附加进度提示，便于玩家了解通关进度
        GameCompletionChecker.CompletionInfo info = GameCompletionChecker.checkCompletion(player);
        output.append("\n\n进度：房间 ")
              .append(info.getRoomsExplored()).append("/").append(info.getTotalRooms())
              .append("  物品 ").append(info.getItemsCollected()).append("/").append(info.getTotalItems())
              .append("  饼干:").append(info.isCookieEaten() ? "已吃" : "未吃")
              .append("  位置:").append(info.isAtStartRoom() ? "起始房间" : "其他房间");
        if (info.isCompleted()) {
            output.append("\n🎉 恭喜！你已完成所有任务，游戏通关！");
        }
        return output.toString();
    }
    
    /**
     * 将方向翻译为中文（辅助方法）
     */
    private String translateDirection(String direction) {
        switch (direction.toLowerCase()) {
            case "north": return "北";
            case "south": return "南";
            case "east": return "东";
            case "west": return "西";
            default: return direction;
        }
    }
    
    /**
     * 保存游戏状态
     */
    public Map<String, Object> saveGame(String sessionId) {
        Map<String, Object> response = new HashMap<>();
        
        GameSession session = getSession(sessionId);
        if (session == null) {
            response.put("success", false);
            response.put("message", "会话无效，请重新登录");
            return response;
        }
        
        GameStateManager stateManager = new GameStateManager(session.game);
        boolean success = stateManager.saveGameState();
        
        if (success) {
            response.put("success", true);
            response.put("message", "游戏状态已保存！");
        } else {
            response.put("success", false);
            response.put("message", "保存失败");
        }
        
        return response;
    }
    
    /**
     * 加载游戏状态
     */
    public Map<String, Object> loadGame(String sessionId) {
        Map<String, Object> response = new HashMap<>();
        
        GameSession session = getSession(sessionId);
        if (session == null) {
            response.put("success", false);
            response.put("message", "会话无效，请重新登录");
            return response;
        }
        
        GameStateManager stateManager = new GameStateManager(session.game);
        boolean success = stateManager.loadGameState();
        
        if (success) {
            response.put("success", true);
            response.put("message", "游戏状态已加载！");
            response.put("gameStatus", getGameStatus(sessionId));
        } else {
            response.put("success", false);
            response.put("message", "没有找到保存的游戏状态");
        }
        
        return response;
    }
    
    /**
     * 获取游戏状态（向后兼容，使用默认会话）
     * 
     * @return 包含游戏状态的Map
     */
    public Map<String, Object> getGameStatus() {
        if (sessions.isEmpty()) {
            Map<String, Object> status = new HashMap<>();
            status.put("error", "没有活动会话");
            return status;
        }
        return getGameStatus(sessions.keySet().iterator().next());
    }
    
    /**
     * 获取游戏状态
     * 
     * @param sessionId 会话ID
     * @return 包含游戏状态的Map
     */
    public Map<String, Object> getGameStatus(String sessionId) {
        Map<String, Object> status = new HashMap<>();
        
        GameSession session = getSession(sessionId);
        if (session == null) {
            status.put("error", "会话无效");
            return status;
        }
        
        Player player = session.player;
        
        // 当前房间信息
        Room currentRoom = player.getCurrentRoom();
        Map<String, Object> roomInfo = new HashMap<>();
        roomInfo.put("shortDescription", currentRoom.getShortDescription());
        roomInfo.put("longDescription", currentRoom.getLongDescription());
        
        // 房间出口
        Map<String, Boolean> exits = new HashMap<>();
        exits.put("north", currentRoom.getExit("north") != null);
        exits.put("south", currentRoom.getExit("south") != null);
        exits.put("east", currentRoom.getExit("east") != null);
        exits.put("west", currentRoom.getExit("west") != null);
        roomInfo.put("exits", exits);
        
        // 房间物品
        List<Map<String, Object>> roomItems = new ArrayList<>();
        Collection<Item> items = currentRoom.getItems();
        // 调试：输出房间物品信息
        System.out.println("DEBUG: 房间 '" + currentRoom.getShortDescription() + "' 的物品数量: " + items.size());
        for (Item item : items) {
            System.out.println("DEBUG: 找到物品: " + item.getName() + " (" + item.getDescription() + ")");
            Map<String, Object> itemInfo = new HashMap<>();
            itemInfo.put("name", item.getName());
            itemInfo.put("description", item.getDescription());
            itemInfo.put("weight", item.getWeight());
            roomItems.add(itemInfo);
        }
        System.out.println("DEBUG: 返回的物品数组大小: " + roomItems.size());
        roomInfo.put("items", roomItems);
        
        status.put("currentRoom", roomInfo);
        
        // 玩家信息
        Map<String, Object> playerInfo = new HashMap<>();
        playerInfo.put("name", player.getName());
        playerInfo.put("maxWeight", player.getMaxWeight());
        playerInfo.put("totalWeight", player.getTotalWeight());
        
        // 玩家物品
        List<Map<String, Object>> inventory = new ArrayList<>();
        for (Item item : player.getInventory()) {
            Map<String, Object> itemInfo = new HashMap<>();
            itemInfo.put("name", item.getName());
            itemInfo.put("description", item.getDescription());
            itemInfo.put("weight", item.getWeight());
            inventory.add(itemInfo);
        }
        playerInfo.put("inventory", inventory);
        
        status.put("player", playerInfo);
        
        // 通关信息
        GameCompletionChecker.CompletionInfo completionInfo = 
            GameCompletionChecker.checkCompletion(player);
        Map<String, Object> completion = new HashMap<>();
        completion.put("completed", completionInfo.isCompleted());
        completion.put("roomsExplored", completionInfo.getRoomsExplored());
        completion.put("totalRooms", completionInfo.getTotalRooms());
        completion.put("itemsCollected", completionInfo.getItemsCollected());
        completion.put("totalItems", completionInfo.getTotalItems());
        completion.put("cookieEaten", completionInfo.isCookieEaten());
        completion.put("atStartRoom", completionInfo.isAtStartRoom());
        status.put("completion", completion);
        
        // 已访问的房间列表
        List<String> visitedRoomsList = new ArrayList<>(player.getRoomsVisited());
        playerInfo.put("visitedRooms", visitedRoomsList);
        
        return status;
    }
    
    /**
     * 获取游戏记录
     */
    public Map<String, Object> getGameRecord(String sessionId) {
        Map<String, Object> response = new HashMap<>();
        
        GameSession session = getSession(sessionId);
        if (session == null) {
            response.put("success", false);
            response.put("message", "会话无效，请重新登录");
            return response;
        }
        
        if (session.userId == null) {
            response.put("success", false);
            response.put("message", "未登录用户无法查看游戏记录");
            return response;
        }
        
        Map<String, Object> record = dbManager.getGameRecord(session.userId);
        if (record == null) {
            response.put("success", false);
            response.put("message", "没有找到游戏记录");
            return response;
        }
        
        response.put("success", true);
        response.put("record", record);
        return response;
    }
    
    /**
     * 获取所有游戏记录
     */
    public Map<String, Object> getAllGameRecords(String sessionId) {
        Map<String, Object> response = new HashMap<>();
        
        GameSession session = getSession(sessionId);
        if (session == null) {
            response.put("success", false);
            response.put("message", "会话无效，请重新登录");
            return response;
        }
        
        if (session.userId == null) {
            response.put("success", false);
            response.put("message", "未登录用户无法查看游戏记录");
            return response;
        }
        
        List<Map<String, Object>> records = dbManager.getAllGameRecords(session.userId);
        response.put("success", true);
        response.put("records", records);
        return response;
    }
    
    /**
     * 退出登录（清除会话）
     */
    public Map<String, Object> logout(String sessionId) {
        Map<String, Object> response = new HashMap<>();
        
        GameSession session = getSession(sessionId);
        if (session != null) {
            // 退出前更新游戏记录
            if (session.player.getGameRecordId() != null) {
                GameCompletionChecker.CompletionInfo info = 
                    GameCompletionChecker.checkCompletion(session.player);
                dbManager.updateGameRecord(
                    session.player.getGameRecordId(),
                    info.isCompleted(),
                    info.getRoomsExplored(),
                    info.getItemsCollected(),
                    info.isCookieEaten()
                );
            }
            // 移除会话
            sessions.remove(sessionId);
            response.put("success", true);
            response.put("message", "已退出登录");
        } else {
            response.put("success", false);
            response.put("message", "会话无效");
        }
        
        return response;
    }
    
    /**
     * 获取Game实例（用于命令执行器，兼容旧代码）
     */
    public Game getGame() {
        // 返回第一个会话的游戏（用于向后兼容）
        if (!sessions.isEmpty()) {
            return sessions.values().iterator().next().game;
        }
        return null;
    }
}
