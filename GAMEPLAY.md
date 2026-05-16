# World of Zuul 游戏玩法指南

## 🎮 游戏简介

《World of Zuul》是一款基于文本的冒险游戏。你扮演一名探险者，在一个由多个房间组成的迷宫中探索。游戏的目标是探索所有房间，收集物品，并发现隐藏的秘密。

---

## 🚀 如何开始游戏

### 运行游戏

1. **编译游戏**（如果还没有编译）：
   ```bash
   javac -encoding UTF-8 -d bin -sourcepath src src/cn/edu/whut/sept/zuul/*.java
   ```

2. **运行游戏**：
   ```bash
   java -cp bin cn.edu.whut.sept.zuul.Main
   ```
   
.\compile_and_run.bat

游戏启动后，你会看到欢迎信息和当前房间的描述。

编译 Java 代码：
javac -d bin -encoding UTF-8 src/cn/edu/whut/sept/zuul/*.java

启动 Web 服务器
java -cp bin cn.edu.whut.sept.zuul.WebMain

http://localhost:8080


   cd D:\PycharmProjects\zuul-dev\se23-sept1-Shellykoi
   javac -d bin -encoding UTF-8 src/cn/edu/whut/sept/zuul/*.java
   java -cp bin cn.edu.whut.sept.zuul.WebMain

   # Windows系统（使用分号;作为classpath分隔符）
   javac -d bin -encoding UTF-8 -cp "lib\mysql-connector-j-8.0.33.jar" src\cn\edu\whut\sept\zuul\*.java
   java -cp "bin;lib\mysql-connector-j-8.0.33.jar" cn.edu.whut.sept.zuul.WebMain
   
   # Linux/Mac系统（使用冒号:作为classpath分隔符）
   # javac -d bin -encoding UTF-8 -cp "lib/mysql-connector-j-8.0.33.jar" src/cn/edu/whut/sept/zuul/*.java
   # java -cp "bin:lib/mysql-connector-j-8.0.33.jar" cn.edu.whut.sept.zuul.WebMain
---

   .\compile_and_run.bat

   详细的修复测试脚本：
   运行修复脚本：
   .\fix_login.bat
   重启服务器（如果服务器正在运行，请先停止，然后重新启动）：
      .\test_api_comprehensive.ps1
   重新测试登录：
   .\test_api_comprehensive.ps1

清理并编译
Remove-Item -Recurse -Force bin -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path bin
$mysqlJar = Get-ChildItem -Path "lib\mysql-connector-j-*.jar" | Select-Object -First 1
javac -d bin -encoding UTF-8 -cp "$($mysqlJar.FullName)" src\cn\edu\whut\sept\zuul\*.java


批处理文件 (.bat)
fix_login.bat - 修复登录问题（编译代码并运行UserFixer）
compile_and_run.bat - 编译并运行服务器
run_only.bat - 仅运行服务器（不编译，需要先编译）
PowerShell脚本 (.ps1)
test_api_comprehensive.ps1 - API端点综合测试（你提到的测试脚本）
test_all.ps1 - 综合功能测试（数据库连接、登录、API等）
setup_git_chinese.ps1 - Git中文配置脚本
Setup-java.ps1 - Java环境设置脚本


## 🗺️ 游戏世界

游戏世界由以下房间组成：

- **outside**（大学主入口外）- 起始房间
- **theater**（演讲厅）
- **pub**（校园酒吧）
- **lab**（计算机实验室）
- **office**（计算机管理办公室）
- **transporter**（神秘传输房间）- 特殊房间

每个房间都有四个方向的出口：**north**（北）、**south**（南）、**east**（东）、**west**（西）。

---

## 📋 可用命令

### 基础移动命令

| 命令 | 说明 | 示例 |
|------|------|------|
| `go <方向>` | 向指定方向移动 | `go north`、`go east` |
| `back` | 返回上一个房间（支持多级回退） | `back` |

### 查看命令

| 命令 | 说明 | 示例 |
|------|------|------|
| `look` | 查看当前房间的详细信息（包括房间描述、出口和物品） | `look` |
| `items` | 查看房间内所有物品和玩家携带的物品 | `items` |
| `help` | 显示所有可用命令 | `help` |

### 物品操作命令

| 命令 | 说明 | 示例 |
|------|------|------|
| `take <物品名>` | 拾取房间内的物品 | `take key`、`take map` |
| `drop <物品名>` | 丢弃身上携带的物品到当前房间 | `drop key` |
| `eat cookie` | 吃掉魔法饼干，增加5kg最大负重 | `eat cookie` |

### 系统命令

| 命令 | 说明 | 示例 |
|------|------|------|
| `status` | 查看游戏进度和通关状态 | `status` |
| `save` | 保存当前游戏状态到数据库 | `save` |
| `load` | 从数据库加载上次保存的游戏 | `load` |
| `quit` | 退出游戏 | `quit` |

---

## 🎯 游戏玩法详解

### 1. 探索迷宫

**基本移动**：
- 使用 `go <方向>` 命令在不同房间之间移动
- 例如：`go north` 向北移动，`go south` 向南移动

**查看房间信息**：
- 使用 `look` 命令查看当前房间的完整信息
- 会显示房间描述、所有出口方向和房间内的物品

**示例**：
```
> look
You are outside the main entrance of the university
Exits: north east south west
Items in room:
  - key (a rusty old key) [0.1kg]
  - map (a campus map) [0.2kg]
```

### 2. 物品系统

**房间物品**：
- 每个房间可能包含多个物品
- 每个物品有名称、描述和重量
- 使用 `look` 或 `items` 命令查看房间物品

**拾取物品**：
- 使用 `take <物品名>` 拾取房间内的物品
- 物品会被添加到你的背包中
- **注意**：你的负重有限制（初始10kg）

**丢弃物品**：
- 使用 `drop <物品名>` 丢弃物品到当前房间
- 物品会从你的背包移除，出现在房间中

**查看物品**：
- 使用 `items` 命令同时查看：
  - 房间内的所有物品及总重量
  - 你携带的所有物品及总重量

**示例**：
```
> items
Items in room:
  - key (a rusty old key) [0.1kg]
  - map (a campus map) [0.2kg]
Total weight in room: 0.3kg

Items you are carrying:
  - coin (a golden coin) [0.05kg]
Total weight you are carrying: 0.05kg / 10.0kg
```

### 3. 负重系统

**负重限制**：
- 初始最大负重：**10kg**
- 拾取物品时，系统会检查总重量
- 如果超过负重限制，会提示无法拾取

**示例**：
```
> take computer
You cannot carry the computer. It weighs 2.5kg, but you can only carry 0.0kg more.
```

**增加负重**：
- 找到并吃掉**魔法饼干**（magic cookie）可以增加5kg最大负重
- 使用 `eat cookie` 命令吃掉饼干
- 吃掉后，你的最大负重会从10kg增加到15kg

**示例**：
```
> eat cookie
You eat the magic cookie. Your carrying capacity increases by 5kg!
Your new maximum weight: 15.0kg
```

### 4. 返回功能（back命令）

**单次返回**：
- 使用 `back` 命令返回上一个房间
- 系统会记录你的移动历史

**多级回退**：
- 可以连续使用 `back` 命令
- 每次使用会返回更早的房间
- 直到回到游戏的起点（outside房间）

**示例**：
```
> go north
You are in a mysterious transporter room
...
> back
You go back to: outside the main entrance of the university
> back
You are already at the starting point!
```

### 5. 传输房间（特殊功能）

**传输房间**：
- 从起始房间（大学主入口外）的**北面**可以进入传输房间
- 进入传输房间后，会被**随机传送到其他房间**
- 这是一个神秘的功能，增加游戏的趣味性

**注意**：
- 传输是随机的，无法预测会传送到哪里
- 使用 `back` 命令可以返回传输房间（但再次进入又会随机传送）

**⚠️ 避免死循环的技巧**：
- 如果你感觉在几个房间之间来回移动，陷入死循环：
  1. **使用 `look` 命令**：仔细查看当前房间的出口和物品
  2. **使用 `back` 命令**：返回到上一个房间，重新规划路线
  3. **记住房间布局**：
     - 大学主入口外（起始点）- 有4个出口：东、南、西、北
     - 演讲厅 - 只有1个出口：西（返回主入口）
     - 校园酒吧 - 只有1个出口：东（返回主入口）
     - 计算机实验室 - 有2个出口：北（返回主入口）、东（到办公室）
     - 计算机管理办公室 - 只有1个出口：西（返回实验室）
     - 传输房间 - 随机传送到任意房间
  4. **探索策略**：
     - 从主入口开始，先探索固定出口的房间（演讲厅、酒吧）
     - 再探索实验室和办公室
     - 最后再尝试传输房间（如果觉得有趣）
  5. **如果迷路了**：
     - 连续使用 `back` 命令可以返回到起点
     - 从起点重新开始探索

---

## 🎲 游戏中的物品

### 普通物品

游戏开始时，以下房间包含物品：

- **outside**：钥匙（key）、地图（map）
- **theater**：书（book）
- **pub**：硬币（coin）、瓶子（bottle）
- **lab**：电脑（computer）、USB线（cable）

### 特殊物品

- **魔法饼干（cookie）**：
  - 随机出现在某个房间中
  - 吃掉后可以增加5kg最大负重
  - 使用 `eat cookie` 命令吃掉

---

## 💡 游戏技巧

1. **探索策略**：
   - 先使用 `look` 命令查看每个房间的详细信息
   - 使用 `items` 命令了解可用的物品
   - 规划好拾取顺序，避免超重
   - **记住房间布局**，避免在相同房间之间来回移动

2. **负重管理**：
   - 优先拾取轻量物品
   - 如果超重，先丢弃一些物品
   - 找到魔法饼干后立即吃掉，增加负重能力

3. **使用back命令**：
   - 如果迷路了，使用 `back` 命令返回
   - 可以逐层回退到起点，重新规划路线
   - **这是避免死循环的最佳方法**

4. **传输房间**：
   - 进入传输房间可以快速传送到其他位置
   - 但位置是随机的，适合冒险探索
   - **如果不想随机传送，避免进入传输房间**

5. **避免死循环**：
   - 如果感觉在几个房间之间来回移动：
     - 立即使用 `back` 命令返回
     - 使用 `look` 命令查看当前房间的出口
     - 选择不同的方向继续探索
     - 记住：主入口是中心，其他房间都是单向或双向连接

---

## 🎮 游戏流程示例

```
游戏开始
↓
你在 outside 房间
↓
> look                    # 查看房间信息
> take key               # 拾取钥匙
> go north               # 进入传输房间
↓
随机传送到某个房间
↓
> look                   # 查看新房间
> items                  # 查看物品
> take cookie            # 如果找到魔法饼干
> eat cookie             # 吃掉增加负重
↓
> go south               # 继续探索
> back                   # 返回上一个房间
↓
探索所有房间，收集物品
↓
> quit                   # 退出游戏
```

---

## ⚠️ 注意事项

1. **命令格式**：
   - 命令不区分大小写
   - 多词命令如 `eat cookie` 需要输入完整
   - 方向词：north, south, east, west

2. **物品名称**：
   - 物品名称不区分大小写
   - 使用 `items` 命令查看准确的物品名称

3. **负重限制**：
   - 超过负重无法拾取新物品
   - 需要先丢弃一些物品

4. **退出游戏**：
   - 使用 `quit` 命令退出
   - 游戏状态不会保存

---

## 🎯 游戏目标与通关条件

### 游戏目标

游戏的主要目标是**探索并收集所有物品**，完成以下任务：

1. **探索所有房间** - 访问游戏世界中的每个房间
2. **收集所有物品** - 拾取所有房间中的物品（共8个物品）
3. **找到并吃掉魔法饼干** - 增加负重能力
4. **返回起点** - 收集完所有物品后，回到起始房间（outside）

### 通关条件

**游戏胜利条件**：
- ✅ 已探索所有6个房间（outside, theater, pub, lab, office, transporter）
- ✅ 已收集所有8个物品（key, map, book, coin, bottle, computer, cable, cookie）
- ✅ 已吃掉魔法饼干（增加负重）
- ✅ 当前在起始房间（outside）

**如何查看进度**：
- 使用 `status` 命令查看游戏进度和完成情况
- 系统会自动检测你是否满足通关条件

**通关后**：
- 系统会显示胜利消息
- 你的游戏记录会被保存到数据库
- 可以继续游戏或退出

### 游戏物品清单

游戏中共有**8个物品**需要收集：

1. **key** - 一把生锈的旧钥匙 (0.1kg) - 在 outside
2. **map** - 一张校园地图 (0.2kg) - 在 outside
3. **book** - 一本编程教科书 (1.5kg) - 在 theater
4. **coin** - 一枚金币 (0.05kg) - 在 pub
5. **bottle** - 一个空瓶子 (0.3kg) - 在 pub
6. **computer** - 一台笔记本电脑 (2.5kg) - 在 lab
7. **cable** - 一根USB线 (0.1kg) - 在 lab
8. **cookie** - 魔法饼干 (0.1kg) - 随机出现在某个房间

**注意**：魔法饼干（cookie）必须被**吃掉**（使用 `eat cookie` 命令），而不是仅仅拾取。

---

## 🎮 多人游戏模式

### 玩家登录/注册

游戏支持多人游戏模式，每个玩家都有独立的游戏进度：

1. **注册新账号**：
   - 首次游戏时，系统会提示你注册
   - 输入用户名和密码即可创建账号
   - 账号信息会保存到数据库

2. **登录已有账号**：
   - 使用注册时的用户名和密码登录
   - 可以继续之前的游戏进度

3. **游戏状态保存**：
   - 使用 `save` 命令保存当前游戏状态
   - 包括：当前位置、物品清单、探索进度等
   - 下次登录时可以 `load` 继续游戏

### 游戏记录

系统会自动记录：
- 游戏开始时间
- 游戏结束时间
- 通关状态
- 收集的物品数量
- 探索的房间数量

## 🆘 需要帮助？

- 输入 `help` 命令查看所有可用命令
- 使用 `look` 命令随时查看当前房间信息
- 使用 `items` 命令查看物品状态
- 使用 `status` 命令查看游戏进度
- 使用 `back` 命令返回上一个房间
- 使用 `save` 命令保存游戏进度

---

**祝你游戏愉快！🎮**


***

# 九、最后说明

## 9.1 Commit 时间线问题

在项目版本管理过程中，因想要规范化commit的分支内容，将 **2025-11-20 至 2025-12-06** 期间的所有提交修改了名字为规范化提交，通过 `git push -f` 强制推送至 2025-12-07 的分支节点，导致 GitHub 仓库上的提交时间线部分未正常显示，无法直观体现真实的开发进度与迭代顺序。

为还原项目完整的开发脉络，已整理出无乱码、按真实提交时间排序的 Commit 时间线文件，可清晰追溯从项目初始化到功能完善的全流程迭代记录。
---

### 完整 Commit 时间线（附 txt）

```txt
提交时间: 2025-11-08 02:11:56 +0000
提交哈希: 221b9b32c22141c6bb4b08a2f3a84ccca246b9bb
提交信息: Initial commit
作者: github-classroom[bot]
---
提交时间: 2025-11-14 02:43:15 +0000
提交哈希: d2697dc25f6a24b51056dc0e5354a5d7186fb13e
提交信息: add deadline
作者: github-classroom[bot]
---
提交时间: 2025-11-18 22:52:22 +0800
提交哈希: a8001dd9a80097e17338740062940765dbd43932
提交信息: 添加Javadoc注释：为所有类和方法添加完整的文档注释
作者: shellykoi
---
提交时间: 2025-11-18 23:18:08 +0800
提交哈希: aceeec100b43c6a9731d066760c24f12b00cba57
提交信息: Update .gitignore: ignore bin directory
作者: shellykoi
---
提交时间: 2025-11-18 23:54:19 +0800
提交哈希: cfadae762797cf41fcf6db274eece26127e100cd
提交信息: Complete World of Zuul game features: item system, player system, back command, magic cookie, transport room, and refactored to command pattern
作者: shellykoi
---
提交时间: 2025-11-19 00:04:01 +0800
提交哈希: 9bb5d48c8573ceb4db8f9b51948432bd99a8a694
提交信息: Add GUI interface using Swing for better user experience
作者: shellykoi
---
提交时间: 2025-11-19 12:52:14 +0800
提交哈希: caa3ee3f7cfefab4215e151c3f377448e94d99e7
提交信息: 改为中文化，然后增加前端页面8080访问
作者: shellykoi
---
提交时间: 2025-11-19 23:45:44 +0800
提交哈希: 450a3e7ee0dc4becaa285ee00df93f3d9d579bd7
提交信息: 连接本地数据库，增加zuul-game数据库
作者: shellykoi
---
提交时间: 2025-11-20 14:39:11 +0800
提交哈希: ca7e762e5654d0700f54a23241f6f3b3e1e993e0
提交信息: 修复端口问题，实现玩家登录功能，以及优化前端页面样式
作者: shellykoi
---
提交时间: 2025-11-20 19:01:08 +0800
提交哈希: db45b46221d9a3f7963fbbaa8378b1f2aa310010
提交信息: 修复未知api端口问题，优化游戏逻辑，实时显示进度，增加游戏通关提示
作者: shellykoi
---
提交时间: 2025-11-20 23:00:05 +0800
提交哈希: c77cafe398d4b37bf01561b8f58d0e20a97b78df
提交信息: feat(ui)：使用Lottie实现动态角色动画
作者: shellykoi
---
提交时间: 2025-11-21 13:37:28 +0800
提交哈希: 7233a100624a09afc59d8a9295fb0ab260ee89a7
提交信息: test: 编写单元测试用例完成ItemTest、RoomTest、PlayerTest等
作者: shellykoi
---
提交时间: 2025-11-21 15:54:12 +0800
提交哈希: 8254ba2ebf9cc88e1cc13ac4d9b72da9634e9e5c
提交信息: feat(ui): 在游戏日志上方加入3d-spline组件优化交互效果
作者: shellykoi
---
提交时间: 2025-11-24 19:02:51 +0800
提交哈希: 48c332a11a974255808c66ce9076c1aa20929800
提交信息: refactor: 完善代码优化，检查并提交所有任务代码
作者: shellykoi
---
提交时间: 2025-11-24 19:43:47 +0800
提交哈希: b6417b9ff3ef394be69b6a87ab2ef16d8f1a3e62
提交信息: feat: 新增use命令支持钥匙解锁/地图查看，扩展Item类及LockedRoom上锁房间
作者: shellykoi
---
提交时间: 2025-11-24 21:43:28 +0800
提交哈希: f61f4ed8bf450a85980fcfee9042458eaabffaf9
提交信息: docs: 补充系统总体架构与命令模式UML类图
作者: shellykoi
---
提交时间: 2025-11-30 23:53:09 +0800
提交哈希: 60872303698c7e8c790be5c1e5bf1c722c2c8364
提交信息: refactor: 规范化功能类注释，优化项目结构增强可读性
作者: shellykoi
---
提交时间: 2025-12-06 21:08:35 +0800
提交哈希: 8972cd26eb4c78ae97d94abf0020d06043db54d5
提交信息: feat: 新增可选调试日志工具
作者: shellykoi
---
提交时间: 2025-12-06 21:24:24 +0800
提交哈希: 0ba6d8a1c7e8b613c2c8ff63a162b916fc3e337d
提交信息: fix: 完善Item类构造函数注释，补充参数说明
作者: shellykoi
---
提交时间: 2025-12-06 21:25:16 +0800
提交哈希: e4054ab0277563a148c3b1f8f1440386bbec069a
提交信息: refactor: 提取魔法值为常量，提升代码可维护性
作者: shellykoi
---
提交时间: 2025-12-06 21:29:46 +0800
提交哈希: 542c3f43720812a772dbc58891e61966be5e3e03
提交信息: docs: 增强代码版本管理章节，补充示例和最佳实践
作者: shellykoi
---
提交时间: 2025-12-06 21:36:46 +0800
提交哈希: 96a55782661e6e485267278b0ba617b666f1fe16
提交信息: chore: 更新编译和测试脚本
作者: shellykoi
---
提交时间: 2025-12-06 22:06:37 +0800
提交哈希: 0f8aec692ba2f309ba37f3070152e0def728ae9a
提交信息: docs: 更新 REPORT.md新增分支管理说明
作者: shellykoi
---
提交时间: 2025-12-06 22:27:26 +0800
提交哈希: c7028c424cc09a05cfdcdd8b800cf5e840f1df50
提交信息: merge: 合并dev 分支到docs/update-version-management
作者: shellykoi
```