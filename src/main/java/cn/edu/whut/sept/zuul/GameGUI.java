/**
 * World of Zuul 游戏的图形用户界面。
 * 使用Java Swing创建现代化的游戏界面。
 */
package cn.edu.whut.sept.zuul;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GameGUI extends JFrame
{
    private Game game;
    private Player player;

    private JTextArea roomInfoArea;
    private JTextArea itemsArea;
    private JTextArea messageArea;
    private JTextField commandField;
    private JButton northButton, southButton, eastButton, westButton;
    private JButton lookButton, itemsButton, backButton, helpButton;
    private JButton takeButton, dropButton, eatCookieButton;

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private PrintStream customOut;

    /**
     * 创建游戏图形界面。
     */
    public GameGUI()
    {
        game = new Game();
        player = game.getPlayer();
        
        // 设置输出重定向
        setupOutputCapture();

        initializeGUI();
        updateDisplay();
        
        // 显示欢迎信息
        appendMessage("欢迎来到 World of Zuul！");
        appendMessage("这是一个文本冒险游戏。");
        appendMessage("使用方向按钮或输入命令来探索世界。");
        appendMessage("输入 'help' 查看所有可用命令。\n");
    }

    /**
     * 设置输出捕获，将System.out重定向到消息区域。
     */
    private void setupOutputCapture()
    {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        customOut = new PrintStream(outputStream, true);
        System.setOut(customOut);
    }

    /**
     * 初始化图形界面组件。
     */
    private void initializeGUI()
    {
        setTitle("World of Zuul - 图形界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 左侧面板：房间信息和物品
        JPanel leftPanel = createLeftPanel();
        
        // 中间面板：消息区域
        JPanel centerPanel = createCenterPanel();
        
        // 右侧面板：命令按钮
        JPanel rightPanel = createRightPanel();
        
        // 底部面板：命令输入
        JPanel bottomPanel = createBottomPanel();
        
        // 组装主面板
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * 创建左侧面板（房间信息和物品列表）。
     */
    private JPanel createLeftPanel()
    {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setPreferredSize(new Dimension(300, 0));
        
        // 房间信息区域
        JPanel roomPanel = new JPanel(new BorderLayout());
        roomPanel.setBorder(new TitledBorder("当前房间"));
        roomInfoArea = new JTextArea(10, 25);
        roomInfoArea.setEditable(false);
        roomInfoArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        roomInfoArea.setBackground(new Color(240, 248, 255));
        JScrollPane roomScroll = new JScrollPane(roomInfoArea);
        roomPanel.add(roomScroll, BorderLayout.CENTER);
        
        // 物品列表区域
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(new TitledBorder("物品信息"));
        itemsArea = new JTextArea(8, 25);
        itemsArea.setEditable(false);
        itemsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        itemsArea.setBackground(new Color(255, 250, 240));
        JScrollPane itemsScroll = new JScrollPane(itemsArea);
        itemsPanel.add(itemsScroll, BorderLayout.CENTER);

        panel.add(roomPanel, BorderLayout.NORTH);
        panel.add(itemsPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * 创建中间面板（消息输出区域）。
     */
    private JPanel createCenterPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("游戏消息"));

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        messageArea.setBackground(new Color(255, 255, 255));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * 创建右侧面板（命令按钮）。
     */
    private JPanel createRightPanel()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("快速命令"));
        panel.setPreferredSize(new Dimension(200, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // 方向按钮
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("方向移动:"), gbc);

        gbc.gridy = 1;
        northButton = createButton("北 (North)", e -> executeCommand("go north"));
        panel.add(northButton, gbc);

        gbc.gridy = 2;
        southButton = createButton("南 (South)", e -> executeCommand("go south"));
        panel.add(southButton, gbc);

        gbc.gridy = 3;
        eastButton = createButton("东 (East)", e -> executeCommand("go east"));
        panel.add(eastButton, gbc);

        gbc.gridy = 4;
        westButton = createButton("西 (West)", e -> executeCommand("go west"));
        panel.add(westButton, gbc);
        
        // 分隔线
        gbc.gridy = 5;
        panel.add(new JSeparator(), gbc);
        
        // 其他命令按钮
        gbc.gridy = 6;
        panel.add(new JLabel("其他命令:"), gbc);

        gbc.gridy = 7;
        lookButton = createButton("查看 (Look)", e -> executeCommand("look"));
        panel.add(lookButton, gbc);

        gbc.gridy = 8;
        itemsButton = createButton("物品 (Items)", e -> executeCommand("items"));
        panel.add(itemsButton, gbc);

        gbc.gridy = 9;
        backButton = createButton("返回 (Back)", e -> executeCommand("back"));
        panel.add(backButton, gbc);

        gbc.gridy = 10;
        helpButton = createButton("帮助 (Help)", e -> executeCommand("help"));
        panel.add(helpButton, gbc);
        
        // 分隔线
        gbc.gridy = 11;
        panel.add(new JSeparator(), gbc);
        
        // 物品操作按钮
        gbc.gridy = 12;
        panel.add(new JLabel("物品操作:"), gbc);

        gbc.gridy = 13;
        takeButton = createButton("拾取 (Take)", e -> showTakeDialog());
        panel.add(takeButton, gbc);

        gbc.gridy = 14;
        dropButton = createButton("丢弃 (Drop)", e -> showDropDialog());
        panel.add(dropButton, gbc);

        gbc.gridy = 15;
        eatCookieButton = createButton("吃饼干", e -> executeCommand("eat cookie"));
        panel.add(eatCookieButton, gbc);

        return panel;
    }

    /**
     * 创建底部面板（命令输入）。
     */
    private JPanel createBottomPanel()
    {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new TitledBorder("命令输入"));

        commandField = new JTextField();
        commandField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        commandField.addActionListener(e -> {
            String command = commandField.getText().trim();
            if (!command.isEmpty()) {
                executeCommand(command);
                commandField.setText("");
            }
        });

        JButton submitButton = new JButton("执行");
        submitButton.addActionListener(e -> {
            String command = commandField.getText().trim();
            if (!command.isEmpty()) {
                executeCommand(command);
                commandField.setText("");
            }
        });

        panel.add(new JLabel("输入命令: "), BorderLayout.WEST);
        panel.add(commandField, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.EAST);

        return panel;
    }

    /**
     * 创建按钮的辅助方法。
     */
    private JButton createButton(String text, ActionListener listener)
    {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(150, 30));
        return button;
    }
}