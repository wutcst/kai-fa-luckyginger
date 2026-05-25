/**
 * 该类是"World-of-Zuul"应用程序的主类。
 * 《World of Zuul》是一款简单的文本冒险游戏。用户可以在一些房间组成的迷宫中探险。
 * 你们可以通过扩展该游戏的功能使它更有趣!
 *
 * 如果想开始执行这个游戏，用户需要创建Game类的一个实例并调用"play"方法。
 *
 * Game类的实例将创建并初始化所有其他类:它创建所有房间，并将它们连接成迷宫；它创建解析器
 * 接收用户输入，并将用户输入转换成命令后开始运行游戏。
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2.0 (重构为命令模式，添加玩家系统和物品系统)
 */
package cn.edu.whut.sept.zuul;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Game
{
    private Parser parser;
    private Player player;
    /**
     * 命令执行器映射表，键为命令词，值为对应的命令执行器。
     */
    private HashMap<String, CommandExecutor> commandExecutors;
    /**
     * 房间历史栈，用于实现back命令的多级回退功能。
     */
    private Stack<Room> roomHistory;

    /**
     * 创建游戏并初始化内部数据和解析器.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
        roomHistory = new Stack<>();
        initializeCommands();
        
        // 创建玩家，初始最大负重为10kg
        player = new Player("Player", 10.0);
        player.setCurrentRoom(startingRoom);
    }

    /**
     * 初始化命令执行器映射表。
     */
    private void initializeCommands()
    {
        commandExecutors = new HashMap<>();
        commandExecutors.put("go", new GoCommand());
        commandExecutors.put("help", new HelpCommand());
        commandExecutors.put("quit", new QuitCommand());
        commandExecutors.put("look", new LookCommand());
        commandExecutors.put("back", new BackCommand());
        commandExecutors.put("take", new TakeCommand());
        commandExecutors.put("drop", new DropCommand());
        commandExecutors.put("items", new ItemsCommand());
        commandExecutors.put("eat", new EatCookieCommand());
        commandExecutors.put("use", new UseCommand());
        commandExecutors.put("status", new StatusCommand());
        commandExecutors.put("save", new SaveCommand());
        commandExecutors.put("load", new LoadCommand());
    }

    /**
     * 所有房间的映射表，用于传输房间功能。
     */
    private HashMap<String, Room> allRoomsMap;
    
    /**
     * 创建所有房间对象并连接其出口用以构建迷宫.
     * 同时为房间添加物品和魔法饼干，并创建传输房间。
     */
    private void createRooms()
    {
        Room campus_gate, main_hall, forest_path, library, lab, locked_room, teleport_room;

        campus_gate = new Room("大学主入口外");
        main_hall = new Room("主大厅");
        forest_path = new Room("校园酒吧");
        library = new Room("图书馆");
        lab = new Room("计算机实验室");
        locked_room = new LockedRoom("上锁的宝库", "key");

        allRoomsMap = new HashMap<>();
        allRoomsMap.put("campus_gate", campus_gate);
        allRoomsMap.put("main_hall", main_hall);
        allRoomsMap.put("forest_path", forest_path);
        allRoomsMap.put("library", library);
        allRoomsMap.put("lab", lab);
        allRoomsMap.put("locked_room", locked_room);

        teleport_room = new TransporterRoom("一个神秘的传输房间", allRoomsMap);
        allRoomsMap.put("teleport_room", teleport_room);

        campus_gate.setExit("north", main_hall);

        main_hall.setExit("south", campus_gate);
        main_hall.setExit("east", library);
        main_hall.setExit("west", forest_path);
        main_hall.setExit("north", lab);

        forest_path.setExit("east", main_hall);
        forest_path.setExit("north", teleport_room);

        library.setExit("west", main_hall);
        library.setExit("north", locked_room);

        lab.setExit("south", main_hall);
        lab.setExit("east", locked_room);

        locked_room.setExit("south", library);
        locked_room.setExit("west", lab);

        teleport_room.setExit("south", forest_path);
        teleport_room.setExit("east", main_hall);

        campus_gate.addItem(new Item("key", "一把生锈的旧钥匙", 0.1, "KEY", "可以解锁上锁的房间"));
        main_hall.addItem(new Item("cookie", "一块可以增加负重的魔法饼干", 0.1));
        forest_path.addItem(new Item("bottle", "一个空瓶子", 0.3));
        forest_path.addItem(new Item("map", "一张校园地图", 0.2, "MAP", "显示当前位置的详细信息"));
        library.addItem(new Item("coin", "一枚金币", 0.05));
        lab.addItem(new Item("computer", "一台笔记本电脑", 2.5));
        lab.addItem(new Item("cable", "一根USB线", 0.1));

        startingRoom = campus_gate;
    }
    
    /**
     * 起始房间，用于back命令判断是否到达起点。
     */
    private Room startingRoom;

    /**
     *  游戏主控循环，直到用户输入退出命令后结束整个程序.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("感谢游玩！再见！");
    }

    /**
     * 向用户输出欢迎信息.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("欢迎来到 World of Zuul！");
        System.out.println("World of Zuul 是一款全新的文本冒险游戏。");
        System.out.println("输入 'help' 查看帮助信息。");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * 执行用户输入的游戏指令。
     * 使用命令模式处理命令，使系统更易扩展。
     * 
     * @param command 待处理的游戏指令，由解析器从用户输入内容生成.
     * @return 如果执行的是游戏结束指令，则返回true，否则返回false.
     */
    private boolean processCommand(Command command)
    {
        if (command.isUnknown()) {
            System.out.println("我不知道你在说什么...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord == null) {
            System.out.println("我不知道你在说什么...");
            return false;
        }
        
        CommandExecutor executor = commandExecutors.get(commandWord);
        
        if (executor != null) {
            return executor.execute(command, this);
        } else {
            System.out.println("我不知道你在说什么...");
            return false;
        }
    }

    /**
     * 获取玩家对象。
     * 
     * @return 玩家对象
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * 获取解析器对象。
     * 
     * @return 解析器对象
     */
    public Parser getParser()
    {
        return parser;
    }

    /**
     * 将房间添加到历史记录中（用于back命令）。
     * 
     * @param room 要添加的房间
     */
    public void addRoomToHistory(Room room)
    {
        roomHistory.push(room);
    }

    /**
     * 获取上一个房间（用于back命令）。
     * 
     * @return 上一个房间对象，如果没有历史记录则返回null
     */
    public Room getPreviousRoom()
    {
        if (roomHistory.isEmpty()) {
            return null;
        }
        return roomHistory.pop();
    }
    
    /**
     * 根据房间名称获取房间对象。
     * 
     * @param roomName 房间名称（简短描述）
     * @return 房间对象，如果不存在则返回null
     */
    public Room getRoomByName(String roomName) {
        if (allRoomsMap == null) {
            return null;
        }
        // 遍历所有房间查找匹配的房间
        for (Room room : allRoomsMap.values()) {
            if (room.getShortDescription().equals(roomName)) {
                return room;
            }
        }
        return null;
    }
    
    /**
     * 在所有房间中查找指定名称的物品。
     * 
     * @param itemName 物品名称
     * @return 物品对象，如果不存在则返回null
     */
    public Item findItemInAllRooms(String itemName) {
        if (allRoomsMap == null) {
            return null;
        }
        for (Room room : allRoomsMap.values()) {
            Item item = room.getItem(itemName);
            if (item != null) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * 查找物品所在的房间。
     * 
     * @param itemName 物品名称
     * @return 房间对象，如果物品不存在则返回null
     */
    public Room findItemRoom(String itemName) {
        if (allRoomsMap == null) {
            return null;
        }
        for (Room room : allRoomsMap.values()) {
            if (room.getItem(itemName) != null) {
                return room;
            }
        }
        return null;
    }
    
    /**
     * 获取起始房间（公开方法）。
     * 
     * @return 起始房间对象
     */
    public Room getStartingRoom() {
        return startingRoom;
    }

    public String getRoomId(Room room) {
        if (allRoomsMap == null) return null;
        for (Map.Entry<String, Room> entry : allRoomsMap.entrySet()) {
            if (entry.getValue() == room) {
                return entry.getKey();
            }
        }
        return null;
    }

    public HashMap<String, Room> getAllRoomsMap() {
        return allRoomsMap;
    }
}
