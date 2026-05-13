# Create initial GitHub Issues for the team migration phase.
# Prerequisite: run `gh auth login` once before executing this script.

$ErrorActionPreference = "Stop"

$repo = "wutcst/kai-fa-luckyginger"

function New-ZuulIssue {
    param(
        [string]$Title,
        [string]$Body
    )

    gh issue create --repo $repo --title $Title --body $Body
}

New-ZuulIssue `
    -Title "[Core] 迁移核心世界、玩家、物品与房间模型" `
    -Body @"
负责人：组长

对应分支：feature/core-world-player-items

任务目标：
- 迁移并扩展 Game、Room、Player、Item 等核心领域模型。
- 支持玩家当前位置、房间地图、物品重量、背包容量和核心移动逻辑。
- 增加特殊房间与通关判定，为后续完整游戏扩展提供核心规则基础。

验收标准：
- Game、Room、Player、Item、GoCommand、Parser 等核心类完成迁移。
- 新增 LockedRoom、TransporterRoom、GameCompletionChecker 等扩展类。
- 提交核心玩法相关测试。
- 分支提交记录能够体现分阶段开发过程。
"@

New-ZuulIssue `
    -Title "[Core] 迁移命令解析与玩家交互命令系统" `
    -Body @"
负责人：组员A

对应分支：feature/core-command-interactions

任务目标：
- 迁移命令框架和输入解析相关代码。
- 完成玩家常用交互命令，包括 look、back、take、drop、use、items、save、load 等。
- 保持命令系统与核心游戏模型解耦，便于后续扩展新命令。

验收标准：
- Command、CommandWords、CommandExecutor、Main、HelpCommand、QuitCommand 等基础类完成迁移。
- BackCommand、LookCommand、TakeCommand、DropCommand、UseCommand 等命令完成迁移。
- 提交命令系统相关测试。
"@

New-ZuulIssue `
    -Title "[Service] 迁移 Web API、会话控制与数据库持久化模块" `
    -Body @"
负责人：组员B

对应分支：feature/service-persistence-api

任务目标：
- 迁移 Web 服务端、游戏控制器和数据库访问模块。
- 支持注册、登录、会话管理、存档、读档和游戏记录查询。
- 保留 MySQL 持久化能力，为后续多人模式和用户系统扩展做准备。

验收标准：
- GameWebServer、GameController、DatabaseManager 等类完成迁移。
- 本地 MySQL 可连接，程序能自动创建 zuul_game 数据库。
- Web API 能支持前端登录、注册和游戏命令调用。
"@

New-ZuulIssue `
    -Title "[UI] 迁移 Web 前端与图形化界面模块" `
    -Body @"
负责人：组员C

对应分支：feature/ui-quality-devops-docs

任务目标：
- 迁移 Web 前端页面、样式和交互脚本。
- 保留桌面 GUI 展示能力。
- 前端能够通过 API 调用后端完成登录、注册和基础游戏操作。

验收标准：
- web/index.html、web/style.css、web/game.js 等资源完成迁移。
- GameGUI 完成迁移。
- 浏览器可以访问本地 Web 游戏界面。
"@

New-ZuulIssue `
    -Title "[DevOps] 配置自动化构建、测试与分支集成流程" `
    -Body @"
负责人：组员C，组长协助

对应分支：feature/ui-quality-devops-docs 或后续 feature/devops-ci

任务目标：
- 配置 GitHub Actions 自动检查。
- 提供基本编译、测试和运行脚本。
- 明确 feature -> dev -> master 的集成流程。

验收标准：
- push 或 Pull Request 时可触发自动化检查。
- 项目具备可重复执行的编译和测试命令。
- README 或报告中说明分支模型、提交规范和 DevOps 流程。
"@

New-ZuulIssue `
    -Title "[Integration] 合并四个功能分支并验证完整项目" `
    -Body @"
负责人：组长

对应分支：dev

任务目标：
- 检查四个成员 feature 分支的提交和文件范围。
- 按顺序合并 feature/core-world-player-items、feature/core-command-interactions、feature/service-persistence-api、feature/ui-quality-devops-docs 到 dev。
- 解决冲突并验证完整项目可编译、可运行。

验收标准：
- 四个功能分支均已推送到远程仓库。
- dev 分支包含完整项目代码。
- 本地可以启动 Web 游戏并访问 http://localhost:8080。
"@

New-ZuulIssue `
    -Title "[Plan] 设计后续 2D 场景化游戏扩展方案" `
    -Body @"
负责人：组长，组员C协助

对应分支：后续 feature/scene-system 或 feature/phaser-client

任务目标：
- 在现有 Java + Web 项目基础上规划 2D 场景化升级。
- 为每个房间设计背景图、出口区域、交互物品和玩家坐标。
- 评估引入 Phaser.js 作为 2D Web 游戏引擎的可行性。

验收标准：
- 给出房间到场景资源的映射方案。
- 明确前端移动、后端命令和状态同步的交互流程。
- 后续根据开发进度拆分为更细的功能 Issue。
"@

New-ZuulIssue `
    -Title "[Config] 优化 MySQL 本地运行配置方式" `
    -Body @"
负责人：组员B

对应分支：后续 feature/db-config

任务目标：
- 当前数据库默认配置为 root/root、localhost:3306、zuul_game。
- 后续优化为支持环境变量或配置文件，减少不同电脑运行时直接修改源码的情况。

验收标准：
- 支持通过环境变量设置数据库 URL、用户名和密码。
- 默认配置仍可直接连接本地 root/root 测试环境。
- README 中补充本地 MySQL 配置说明。
"@

Write-Host "Initial issues have been submitted to $repo"
