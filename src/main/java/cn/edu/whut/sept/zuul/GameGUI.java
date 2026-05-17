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
}