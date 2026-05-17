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
}