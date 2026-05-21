/**
 * 游戏状态管理类
 * 负责保存和加载游戏状态到数据库。
 */
package cn.edu.whut.sept.zuul;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GameStateManager {
    private DatabaseManager dbManager;
    private Game game;

    public GameStateManager(Game game) {
        this.game = game;
        this.dbManager = DatabaseManager.getInstance();
    }

    public boolean saveGameState() {
        Player player = game.getPlayer();

        if (player.getUserId() == null) {
            return false;
        }

        Room currentRoom = player.getCurrentRoom();
        String currentRoomId = game.getRoomId(currentRoom);
        if (currentRoomId == null) {
            currentRoomId = "campus_gate";
        }

        List<String> inventory = new ArrayList<>();
        for (Item item : player.getInventory()) {
            inventory.add(item.getName());
        }

        List<String> roomsVisited = new ArrayList<>(player.getRoomsVisited());
        List<String> itemsCollected = new ArrayList<>(player.getItemsCollected());

        boolean treasureUnlocked = false;
        Room lockedRoom = game.getAllRoomsMap().get("locked_room");
        if (lockedRoom instanceof LockedRoom) {
            treasureUnlocked = ((LockedRoom) lockedRoom).isUnlocked();
        }

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

    public boolean loadGameState() {
        Player player = game.getPlayer();

        if (player.getUserId() == null) {
            return false;
        }

        Map<String, Object> state = dbManager.loadPlayerState(player.getUserId());

        if (state == null) {
            return false;
        }

        String currentRoomName = (String) state.get("currentRoom");
        Room targetRoom = game.getAllRoomsMap().get(currentRoomName);
        if (targetRoom == null) {
            targetRoom = game.getRoomByName(currentRoomName);
        }
        if (targetRoom != null) {
            player.setCurrentRoom(targetRoom);
        }

        player.setMaxWeight((Double) state.get("maxWeight"));

        @SuppressWarnings("unchecked")
        List<String> roomsVisited = (List<String>) state.get("roomsVisited");
        if (roomsVisited != null) {
            player.setRoomsVisited(new HashSet<>(roomsVisited));
        }

        @SuppressWarnings("unchecked")
        List<String> itemsCollected = (List<String>) state.get("itemsCollected");
        if (itemsCollected != null) {
            player.setItemsCollected(new HashSet<>(itemsCollected));
        }

        player.setCookieEaten((Boolean) state.get("cookieEaten"));

        if (player.isCookieEaten()) {
            Room mainHall = game.getAllRoomsMap().get("main_hall");
            if (mainHall != null) {
                mainHall.removeItem("cookie");
            }
        }

        Boolean treasureUnlocked = (Boolean) state.get("treasureUnlocked");
        if (treasureUnlocked != null && treasureUnlocked) {
            Room lockedRoom = game.getAllRoomsMap().get("locked_room");
            if (lockedRoom instanceof LockedRoom) {
                ((LockedRoom) lockedRoom).unlock("key");
            }
        }

        @SuppressWarnings("unchecked")
        List<String> inventory = (List<String>) state.get("inventory");
        if (inventory != null) {
            restoreInventory(inventory);
        }

        return true;
    }

    private void restoreInventory(List<String> itemNames) {
        Player player = game.getPlayer();

        for (String itemName : itemNames) {
            Item item = game.findItemInAllRooms(itemName);
            if (item != null && player.canCarry(item)) {
                Room itemRoom = game.findItemRoom(itemName);
                if (itemRoom != null) {
                    itemRoom.removeItem(itemName);
                    player.takeItem(item);
                }
            }
        }
    }
}
