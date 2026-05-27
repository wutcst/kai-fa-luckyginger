/**
 * 游戏通关检测类
 * 检测玩家是否满足通关条件
 * 
 * @author 扩展功能实现
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.Set;

public class GameCompletionChecker {
    // 所有房间名称（用于通关检测）
    private static final String[] ALL_ROOMS = {
        "大学主入口外",
        "主大厅",
        "室外花园",
        "图书馆",
        "计算机实验室",
        "上锁的宝库",
        "一个神秘的传输房间"
    };
    
    private static final String[] ALL_ITEMS = {
        "key", "cookie", "box", "map", "notebook", "computer", "cable", "treasure", "final_key"
    };
    
    /**
     * 检查玩家是否满足通关条件
     * 
     * @param player 玩家对象
     * @return 通关信息对象
     */
    public static CompletionInfo checkCompletion(Player player) {
        CompletionInfo info = new CompletionInfo();
        
        // 检查是否在起始房间
        Room currentRoom = player.getCurrentRoom();
        boolean atStartRoom = currentRoom != null && 
                             currentRoom.getShortDescription().equals("大学主入口外");
        info.setAtStartRoom(atStartRoom);
        
        // 检查已访问的房间
        Set<String> roomsVisited = player.getRoomsVisited();
        int roomsCount = 0;
        for (String room : ALL_ROOMS) {
            if (roomsVisited.contains(room)) {
                roomsCount++;
            }
        }
        info.setRoomsExplored(roomsCount);
        info.setTotalRooms(ALL_ROOMS.length);
        info.setAllRoomsExplored(roomsCount >= ALL_ROOMS.length);
        
        // 检查已收集的物品
        Set<String> itemsCollected = player.getItemsCollected();
        int itemsCount = 0;
        for (String item : ALL_ITEMS) {
            if (itemsCollected.contains(item.toLowerCase())) {
                itemsCount++;
            }
        }
        info.setItemsCollected(itemsCount);
        info.setTotalItems(ALL_ITEMS.length);
        info.setAllItemsCollected(itemsCount >= ALL_ITEMS.length);
        
        // 检查是否已吃掉饼干
        boolean cookieEaten = player.isCookieEaten();
        info.setCookieEaten(cookieEaten);
        
        // 综合判断是否通关
        boolean hasFinalKey = player.hasItem("final_key");
        boolean isCompleted = hasFinalKey;
        info.setCompleted(isCompleted);
        
        return info;
    }
    
    /**
     * 通关信息类
     */
    public static class CompletionInfo {
        private boolean completed;
        private boolean atStartRoom;
        private int roomsExplored;
        private int totalRooms;
        private boolean allRoomsExplored;
        private int itemsCollected;
        private int totalItems;
        private boolean allItemsCollected;
        private boolean cookieEaten;
        
        // Getters and Setters
        public boolean isCompleted() {
            return completed;
        }
        
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
        
        public boolean isAtStartRoom() {
            return atStartRoom;
        }
        
        public void setAtStartRoom(boolean atStartRoom) {
            this.atStartRoom = atStartRoom;
        }
        
        public int getRoomsExplored() {
            return roomsExplored;
        }
        
        public void setRoomsExplored(int roomsExplored) {
            this.roomsExplored = roomsExplored;
        }
        
        public int getTotalRooms() {
            return totalRooms;
        }
        
        public void setTotalRooms(int totalRooms) {
            this.totalRooms = totalRooms;
        }
        
        public boolean isAllRoomsExplored() {
            return allRoomsExplored;
        }
        
        public void setAllRoomsExplored(boolean allRoomsExplored) {
            this.allRoomsExplored = allRoomsExplored;
        }
        
        public int getItemsCollected() {
            return itemsCollected;
        }
        
        public void setItemsCollected(int itemsCollected) {
            this.itemsCollected = itemsCollected;
        }
        
        public int getTotalItems() {
            return totalItems;
        }
        
        public void setTotalItems(int totalItems) {
            this.totalItems = totalItems;
        }
        
        public boolean isAllItemsCollected() {
            return allItemsCollected;
        }
        
        public void setAllItemsCollected(boolean allItemsCollected) {
            this.allItemsCollected = allItemsCollected;
        }
        
        public boolean isCookieEaten() {
            return cookieEaten;
        }
        
        public void setCookieEaten(boolean cookieEaten) {
            this.cookieEaten = cookieEaten;
        }
        
        /**
         * 生成进度报告字符串
         */
        public String getProgressReport() {
            StringBuilder report = new StringBuilder();
            report.append("========== 游戏进度 ==========\n");
            report.append("房间探索: ").append(roomsExplored).append("/").append(totalRooms);
            if (allRoomsExplored) {
                report.append(" ✓\n");
            } else {
                report.append("\n");
            }
            
            report.append("物品收集: ").append(itemsCollected).append("/").append(totalItems);
            if (allItemsCollected) {
                report.append(" ✓\n");
            } else {
                report.append("\n");
            }
            
            report.append("魔法饼干: ");
            if (cookieEaten) {
                report.append("已吃掉 ✓\n");
            } else {
                report.append("未吃掉\n");
            }
            
            report.append("当前位置: ");
            if (atStartRoom) {
                report.append("起始房间 ✓\n");
            } else {
                report.append("其他房间\n");
            }
            
            report.append("============================\n");
            
            if (completed) {
                report.append("\n🎉 恭喜！你已完成所有任务，游戏通关！\n");
            } else {
                report.append("\n继续努力，完成所有任务即可通关！\n");
            }
            
            return report.toString();
        }
    }
}

