/**
 * 游戏状态管理类
 *
 * <p>负责游戏状态的保存和加载功能，包括：
 * <ul>
 *   <li>玩家当前位置和负重</li>
 *   <li>背包物品和已收集物品记录</li>
 *   <li>已访问房间历史</li>
 *   <li>魔法饼干使用状态</li>
 *   <li>宝库解锁状态</li>
 *   <li>游戏通关记录</li>
 * </ul>
 *
 * <p>游戏状态通过DatabaseManager持久化到数据库，
 * 支持玩家随时保存和加载游戏进度。
 *
 * @author 扩展功能实现
 * @version 1.0
 * @see DatabaseManager
 * @see Player
 * @see Room
 */
package cn.edu.whut.sept.zuul;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 游戏状态管理器
 *
 * <p>提供游戏状态的保存和加载功能，
 * 实现游戏进度的持久化存储。
 */
public class GameStateManager {
    /** 数据库管理器实例，用于持久化游戏状态 */
    private DatabaseManager dbManager;

    /** 游戏实例 */
    private Game game;

    /**
     * 创建游戏状态管理器
     *
     * @param game 游戏实例，用于访问玩家和房间信息
     */
    public GameStateManager(Game game) {
        this.game = game;
        this.dbManager = DatabaseManager.getInstance();
    }

    /**
     * 保存当前游戏状态到数据库
     *
     * <p>保存内容包括：
     * <ul>
     *   <li>玩家当前位置（房间ID）</li>
     *   <li>玩家当前最大负重</li>
     *   <li>背包物品列表</li>
     *   <li>已访问房间集合</li>
     *   <li>已收集物品集合</li>
     *   <li>魔法饼干使用状态</li>
     *   <li>宝库是否已解锁</li>
     * </ul>
     *
     * <p>只有在玩家已登录（userId不为null）时才会保存。
     * 保存成功后还会更新游戏通关记录。
     *
     * @return 保存是否成功
     */
    public boolean saveGameState() {
        Player player = game.getPlayer();

        // 检查玩家是否已登录
        if (player.getUserId() == null) {
            return false;
        }

        // 获取当前房间ID
        Room currentRoom = player.getCurrentRoom();
        String currentRoomId = game.getRoomId(currentRoom);
        if (currentRoomId == null) {
            // 如果找不到房间ID，使用默认值
            currentRoomId = "campus_gate";
        }

        // 收集背包物品列表
        List<String> inventory = new ArrayList<>();
        for (Item item : player.getInventory()) {
            inventory.add(item.getName());
        }

        // 收集已访问房间和已收集物品
        List<String> roomsVisited = new ArrayList<>(player.getRoomsVisited());
        List<String> itemsCollected = new ArrayList<>(player.getItemsCollected());

        // 检查宝库是否已解锁
        boolean treasureUnlocked = false;
        Room lockedRoom = game.getAllRoomsMap().get("locked_room");
        if (lockedRoom instanceof LockedRoom) {
            treasureUnlocked = ((LockedRoom) lockedRoom).isUnlocked();
        }

        // 调用数据库管理器保存状态
        boolean saved = dbManager.savePlayerState(
            player.getUserId(),
            currentRoomId,
            player.getMaxWeight(),
            inventory,
            roomsVisited,
            itemsCollected,
            player.isCookieEaten(),
            treasureUnlocked
        );

        // 如果保存成功，更新游戏通关记录
        if (saved && player.getGameRecordId() != null) {
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

        return saved;
    }

    /**
     * 从数据库加载游戏状态
     *
     * <p>加载过程包括：
     * <ol>
     *   <li>验证玩家已登录</li>
     *   <li>从数据库读取保存的状态</li>
     *   <li>恢复玩家位置、负重、物品等信息</li>
     *   <li>恢复魔法饼干和宝库状态</li>
     *   <li>重新拾取背包中的物品</li>
     * </ol>
     *
     * <p>如果在加载过程中某个状态无法恢复，
     * 会使用默认值或跳过该状态的处理。
     *
     * @return 加载是否成功
     */
    public boolean loadGameState() {
        Player player = game.getPlayer();

        // 检查玩家是否已登录
        if (player.getUserId() == null) {
            return false;
        }

        // 从数据库加载状态
        Map<String, Object> state = dbManager.loadPlayerState(player.getUserId());

        if (state == null) {
            return false;
        }

        // 恢复玩家位置
        String currentRoomName = (String) state.get("currentRoom");
        Room targetRoom = game.getAllRoomsMap().get(currentRoomName);
        if (targetRoom == null) {
            // 如果通过ID找不到，尝试通过名称查找
            targetRoom = game.getRoomByName(currentRoomName);
        }
        if (targetRoom != null) {
            player.setCurrentRoom(targetRoom);
        }

        // 恢复最大负重
        player.setMaxWeight((Double) state.get("maxWeight"));

        // 恢复已访问房间集合
        @SuppressWarnings("unchecked")
        List<String> roomsVisited = (List<String>) state.get("roomsVisited");
        if (roomsVisited != null) {
            player.setRoomsVisited(new HashSet<>(roomsVisited));
        }

        // 恢复已收集物品集合
        @SuppressWarnings("unchecked")
        List<String> itemsCollected = (List<String>) state.get("itemsCollected");
        if (itemsCollected != null) {
            player.setItemsCollected(new HashSet<>(itemsCollected));
        }

        // 恢复魔法饼干使用状态
        player.setCookieEaten((Boolean) state.get("cookieEaten"));

        // 如果魔法饼干已被吃掉，从主大厅移除
        if (player.isCookieEaten()) {
            Room mainHall = game.getAllRoomsMap().get("main_hall");
            if (mainHall != null) {
                mainHall.removeItem("cookie");
            }
        }

        // 恢复宝库解锁状态
        Boolean treasureUnlocked = (Boolean) state.get("treasureUnlocked");
        if (treasureUnlocked != null && treasureUnlocked) {
            Room lockedRoom = game.getAllRoomsMap().get("locked_room");
            if (lockedRoom instanceof LockedRoom) {
                ((LockedRoom) lockedRoom).unlock("key");
            }
        }

        // 恢复背包物品
        @SuppressWarnings("unchecked")
        List<String> inventory = (List<String>) state.get("inventory");
        if (inventory != null) {
            restoreInventory(inventory);
        }

        return true;
    }

    /**
     * 恢复玩家背包物品
     *
     * <p>该方法遍历物品名称列表，
     * 从游戏世界的所有房间中查找对应物品，
     * 如果找到且玩家可以携带，则将其从房间移除并放入背包。
     *
     * <p>只有当物品仍在游戏世界中存在时才会被恢复。
     * 如果物品已被移除（如被其他玩家拿走），则无法恢复。
     *
     * @param itemNames 要恢复的物品名称列表
     */
    private void restoreInventory(List<String> itemNames) {
        Player player = game.getPlayer();

        for (String itemName : itemNames) {
            // 在所有房间中查找物品
            Item item = game.findItemInAllRooms(itemName);
            if (item != null && player.canCarry(item)) {
                // 找到物品所在的房间
                Room itemRoom = game.findItemRoom(itemName);
                if (itemRoom != null) {
                    // 从房间移除并放入玩家背包
                    itemRoom.removeItem(itemName);
                    player.takeItem(item);
                }
            }
        }
    }
}
