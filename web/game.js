const $ = (id) => document.getElementById(id);

const views = {
    auth: $("auth-view"),
    menu: $("menu-view"),
    game: $("game-view")
};

const API_BASE = "";

const itemMeta = {
    key: { label: "key", weight: 0.1, icon: "assets/items/gold_key.png" },
    cookie: { label: "cookie", weight: 0.2, icon: "assets/items/pumpkin.png" },
    computer: { label: "computer", weight: 2.5, icon: "assets/items/crystal_ore.png" },
    cable: { label: "cable", weight: 0.1, icon: "assets/items/pickaxe.png" },
    coin: { label: "coin", weight: 0.1, icon: "assets/items/shield.png" },
    bottle: { label: "bottle", weight: 0.4, icon: "assets/items/green_potion.png" },
    map: { label: "map", weight: 0.1, icon: "assets/items/scroll.png" }
};

const playerFrames = {
    north: [
        "assets/characters/player_frames/player_up_0.png",
        "assets/characters/player_frames/player_up_1.png",
        "assets/characters/player_frames/player_up_2.png",
        "assets/characters/player_frames/player_up_3.png",
        "assets/characters/player_frames/player_up_4.png"
    ],
    south: [
        "assets/characters/player_frames/player_down_0.png",
        "assets/characters/player_frames/player_down_1.png",
        "assets/characters/player_frames/player_down_2.png",
        "assets/characters/player_frames/player_down_3.png",
        "assets/characters/player_frames/player_down_4.png"
    ],
    west: [
        "assets/characters/player_frames/player_left_0.png",
        "assets/characters/player_frames/player_left_1.png",
        "assets/characters/player_frames/player_left_2.png",
        "assets/characters/player_frames/player_left_3.png",
        "assets/characters/player_frames/player_left_4.png"
    ],
    east: [
        "assets/characters/player_frames/player_right_0.png",
        "assets/characters/player_frames/player_right_1.png",
        "assets/characters/player_frames/player_right_2.png",
        "assets/characters/player_frames/player_right_3.png",
        "assets/characters/player_frames/player_right_4.png"
    ]
};

const rooms = {
    campus_gate: {
        title: "校园入口",
        description: "你站在校园入口。石板路旁放着一把小钥匙。",
        image: "assets/rooms/campus_gate.png",
        exits: { north: "main_hall" },
        items: ["key"],
        start: { left: 50, top: 76 },
        paths: {
            north: {
                route: [
                    { left: 44, top: 76 },
                    { left: 47, top: 64 },
                    { left: 50, top: 52 },
                    { left: 50, top: 40 }
                ],
                exit: { left: 50, top: 40 },
                enter: { left: 50, top: 78 }
            }
        }
    },
    main_hall: {
        title: "主大厅",
        description: "主大厅连接着校园里的几个重要区域。",
        image: "assets/rooms/main_hall.png",
        exits: { south: "campus_gate", east: "library", west: "forest_path", north: "lab" },
        items: ["cookie"],
        start: { left: 50, top: 72 },
        paths: {
            north: {
                route: [{ left: 50, top: 64 }, { left: 50, top: 52 }, { left: 50, top: 38 }],
                exit: { left: 50, top: 38 },
                enter: { left: 50, top: 78 }
            },
            south: {
                route: [{ left: 50, top: 78 }, { left: 50, top: 86 }],
                exit: { left: 50, top: 86 },
                enter: { left: 50, top: 42 }
            },
            east: {
                route: [{ left: 62, top: 68 }, { left: 74, top: 64 }, { left: 84, top: 62 }],
                exit: { left: 84, top: 62 },
                enter: { left: 18, top: 62 }
            },
            west: {
                route: [{ left: 38, top: 68 }, { left: 26, top: 64 }, { left: 16, top: 62 }],
                exit: { left: 16, top: 62 },
                enter: { left: 82, top: 62 }
            }
        }
    },
    forest_path: {
        title: "校园酒吧",
        description: "花园小路旁有休息区和温室，石板路通向更深处。",
        image: "assets/rooms/forest_path.png",
        exits: { east: "main_hall", north: "teleport_room" },
        items: ["bottle", "map"],
        start: { left: 50, top: 72 },
        paths: {
            east: {
                route: [{ left: 62, top: 72 }, { left: 74, top: 68 }, { left: 84, top: 66 }],
                exit: { left: 84, top: 66 },
                enter: { left: 16, top: 62 }
            },
            north: {
                route: [{ left: 50, top: 62 }, { left: 50, top: 52 }, { left: 50, top: 42 }],
                exit: { left: 50, top: 42 },
                enter: { left: 50, top: 76 }
            }
        }
    },
    library: {
        title: "图书馆",
        description: "温暖的灯光照在长桌和书架上，桌面上散落着笔记。",
        image: "assets/rooms/library.png",
        exits: { west: "main_hall", north: "locked_room" },
        items: ["coin"],
        start: { left: 52, top: 64 },
        paths: {
            west: {
                route: [{ left: 40, top: 64 }, { left: 26, top: 62 }, { left: 14, top: 60 }],
                exit: { left: 14, top: 60 },
                enter: { left: 84, top: 62 }
            },
            north: {
                route: [{ left: 62, top: 58 }, { left: 70, top: 50 }, { left: 74, top: 42 }],
                exit: { left: 74, top: 42 },
                enter: { left: 42, top: 78 }
            }
        }
    },
    lab: {
        title: "Computer Lab",
        description: "你在计算机实验室。设备、线缆和笔记本电脑摆放在实验台附近。",
        image: "assets/rooms/lab.png",
        exits: { south: "main_hall", east: "locked_room" },
        items: ["computer", "cable"],
        start: { left: 50, top: 68 },
        paths: {
            south: {
                route: [{ left: 50, top: 74 }, { left: 50, top: 84 }],
                exit: { left: 50, top: 84 },
                enter: { left: 50, top: 38 }
            },
            east: {
                route: [{ left: 62, top: 66 }, { left: 74, top: 62 }, { left: 82, top: 60 }],
                exit: { left: 82, top: 60 },
                enter: { left: 22, top: 76 }
            }
        }
    },
    locked_room: {
        title: "上锁的宝库",
        description: "厚重的宝库门被锁住了。使用钥匙后，向北可以进入宝藏房间。",
        image: "assets/rooms/locked_room.png",
        exits: { south: "library", west: "lab" },
        items: [],
        start: { left: 50, top: 76 },
        paths: {
            south: {
                route: [{ left: 46, top: 80 }, { left: 42, top: 86 }],
                exit: { left: 42, top: 86 },
                enter: { left: 74, top: 42 }
            },
            west: {
                route: [{ left: 38, top: 76 }, { left: 28, top: 76 }, { left: 18, top: 76 }],
                exit: { left: 18, top: 76 },
                enter: { left: 82, top: 60 }
            },
            north: {
                route: [{ left: 52, top: 68 }, { left: 56, top: 56 }, { left: 58, top: 44 }],
                exit: { left: 58, top: 44 },
                enter: { left: 50, top: 78 }
            }
        }
    },
    unlocked_treasure_room: {
        title: "开锁后的宝库",
        description: "宝库已经打开，金色灯光照亮了收藏架和宝箱。",
        image: "assets/rooms/unlocked_treasure_room2.png",
        exits: { south: "locked_room" },
        items: [],
        start: { left: 50, top: 78 },
        paths: {
            south: {
                route: [{ left: 50, top: 82 }, { left: 50, top: 86 }],
                exit: { left: 50, top: 86 },
                enter: { left: 58, top: 44 }
            }
        }
    },
    teleport_room: {
        title: "传送房间",
        description: "传送装置发出蓝色光芒，空气里有轻微的嗡鸣。",
        image: "assets/rooms/teleport_room.png",
        exits: { south: "forest_path", east: "main_hall" },
        items: [],
        start: { left: 50, top: 70 },
        paths: {
            south: {
                route: [{ left: 50, top: 76 }, { left: 48, top: 84 }],
                exit: { left: 48, top: 84 },
                enter: { left: 50, top: 42 }
            },
            east: {
                route: [{ left: 62, top: 66 }, { left: 72, top: 60 }, { left: 80, top: 58 }],
                exit: { left: 80, top: 58 },
                enter: { left: 16, top: 62 }
            }
        }
    }
};

const backendRoomAliases = [
    { patterns: ["outside", "campus", "入口", "校园入口"], roomId: "campus_gate" },
    { patterns: ["hall", "大厅", "main"], roomId: "main_hall" },
    { patterns: ["library", "图书馆"], roomId: "library" },
    { patterns: ["computer lab", "lab", "实验室", "计算机实验室"], roomId: "lab" },
    { patterns: ["forest", "pub", "bar", "酒吧", "小路"], roomId: "forest_path" },
    { patterns: ["locked", "treasury", "宝库", "上锁"], roomId: "locked_room" },
    { patterns: ["teleport", "transporter", "传送"], roomId: "teleport_room" }
];

let playerAnimationTimer = null;
let currentRoomId = "campus_gate";
let isMoving = false;
let commandBusy = false;
let sessionId = null;
let currentUsername = null;
let pendingAuthData = null;
let lastBackendStatus = null;
let currentPlayerPosition = { left: 50, top: 76 };

const gameState = {
    inventory: [],
    treasureUnlocked: false,
    visitedRooms: new Set(["campus_gate"]),
    completion: {
        roomsExplored: 1,
        totalRooms: 7,
        itemsCollected: 0,
        totalItems: 8
    }
};

function showView(name) {
    Object.values(views).forEach((view) => view.classList.remove("active"));
    views[name].classList.add("active");
}

function setActiveAuthTab(mode) {
    $("login-tab").classList.toggle("active", mode === "login");
    $("register-tab").classList.toggle("active", mode === "register");
    $("login-form").classList.toggle("active", mode === "login");
    $("register-form").classList.toggle("active", mode === "register");
}

function appendLog(text, type = "") {
    const line = document.createElement("p");
    line.textContent = text;
    if (type) line.classList.add(type);
    $("output-area").appendChild(line);
    $("output-area").scrollTop = $("output-area").scrollHeight;
}

function setFormMessage(id, text) {
    $(id).textContent = text;
}

function setMenuMessage(text, type = "") {
    const message = $("menu-message");
    message.textContent = text;
    message.className = "form-message";
    if (type) message.classList.add(type);
}

function setBusy(buttonId, isBusy, busyText) {
    const button = $(buttonId);
    if (!button) return;

    if (!button.dataset.idleText) {
        button.dataset.idleText = button.textContent;
    }

    button.disabled = isBusy;
    button.textContent = isBusy ? busyText : button.dataset.idleText;
}

function setCommandControlsDisabled(disabled) {
    $("submit-btn").disabled = disabled;
    $("command-input").disabled = disabled;
    document.querySelectorAll(".direction-pad button").forEach((button) => {
        const direction = getDirection(button.dataset.command || "");
        button.disabled = disabled || (direction && !canMoveToDirection(direction));
    });
}

function canMoveToDirection(direction) {
    const room = rooms[currentRoomId];
    return Boolean(room && room.exits && room.exits[direction]);
}

function updateDirectionControls() {
    document.querySelectorAll(".direction-pad button").forEach((button) => {
        const direction = getDirection(button.dataset.command || "");
        if (direction) {
            button.disabled = isMoving || !canMoveToDirection(direction);
        }
    });
}

function wait(ms) {
    return new Promise((resolve) => window.setTimeout(resolve, ms));
}

async function callApi(endpoint, payload) {
    const response = await fetch(`${API_BASE}/api/${endpoint}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
    }

    return response.json();
}

async function getApi(endpoint, params = {}) {
    const query = new URLSearchParams(params).toString();
    const url = `${API_BASE}/api/${endpoint}${query ? "?" + query : ""}`;
    const response = await fetch(url);

    if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
    }

    return response.json();
}

async function sendGameCommand(command) {
    if (!sessionId) {
        return { success: true, message: "" };
    }

    return callApi("command", { command, sessionId });
}

function bindFloatingPanels() {
    document.querySelectorAll(".round-tool").forEach((button) => {
        button.addEventListener("click", () => {
            const target = button.dataset.panel;
            document.querySelectorAll(".round-tool").forEach((tool) => {
                tool.classList.toggle("active", tool === button);
            });
            document.querySelectorAll(".floating-panel").forEach((panel) => {
                panel.classList.toggle("active", panel.id === target);
            });
        });
    });

    const dock = $("inventory-dock");
    dock.querySelector(".dock-tab").addEventListener("click", () => {
        dock.classList.toggle("open");
    });
}

function findVisualRoomId(roomInfo) {
    if (!roomInfo) return currentRoomId;

    const text = [
        roomInfo.shortDescription,
        roomInfo.longDescription,
        roomInfo.name
    ].filter(Boolean).join(" ").toLowerCase();

    const match = backendRoomAliases.find((entry) => {
        return entry.patterns.some((pattern) => text.includes(pattern.toLowerCase()));
    });

    return match ? match.roomId : currentRoomId;
}

function normalizeItemName(item) {
    if (!item) return "";
    if (typeof item === "string") return item;
    return item.name || item.label || "";
}

function itemNames(items) {
    if (!Array.isArray(items)) return [];
    return items.map(normalizeItemName).filter(Boolean);
}

function formatWeight(value) {
    const number = Number(value || 0);
    return Number.isInteger(number) ? String(number) : number.toFixed(1);
}

function itemWeight(name) {
    return itemMeta[name] ? itemMeta[name].weight : 0;
}

function inventoryWeight() {
    return gameState.inventory.reduce((total, name) => total + itemWeight(name), 0);
}

function itemPill(name) {
    const meta = itemMeta[name] || { label: name, icon: "assets/items/scroll.png" };
    const label = meta.label || name;
    return `<span class="item-pill"><img src="${meta.icon}" alt="">${label}</span>`;
}

function renderInventory() {
    const box = $("inventory-list");
    if (!gameState.inventory.length) {
        box.textContent = "暂无物品";
    } else {
        box.innerHTML = gameState.inventory.map(itemPill).join("");
    }

    const playerInfo = lastBackendStatus && lastBackendStatus.player ? lastBackendStatus.player : {};
    const totalWeight = playerInfo.totalWeight != null ? playerInfo.totalWeight : inventoryWeight();
    const maxWeight = playerInfo.maxWeight != null ? playerInfo.maxWeight : 10;
    $("weight-info").textContent = `负重 ${formatWeight(totalWeight)}/${formatWeight(maxWeight)}kg`;
}

function renderQuickActions() {
    const room = rooms[currentRoomId];
    const actions = [
        { label: "查看状态", command: "status" },
        { label: "查看物品", command: "items" },
        { label: "返回上一房间", command: "back" }
    ];

    (room.items || []).forEach((item) => {
        actions.push({ label: `拾取 ${item}`, command: `take ${item}` });
    });

    if (currentRoomId === "locked_room" && hasItem("key") && !gameState.treasureUnlocked) {
        actions.push({ label: "使用 key", command: "use key" });
    }

    const box = $("quick-actions");
    box.innerHTML = "";
    actions.forEach((action) => {
        const button = document.createElement("button");
        button.type = "button";
        button.textContent = action.label;
        button.addEventListener("click", () => submitCommand(action.command));
        box.appendChild(button);
    });
}

function renderProgress() {
    const completion = gameState.completion;
    $("room-progress").textContent =
        `房间 ${completion.roomsExplored}/${completion.totalRooms}，物品 ${completion.itemsCollected}/${completion.totalItems}`;
}

function updateHud() {
    renderInventory();
    renderQuickActions();
    renderProgress();
}

function syncFromBackendStatus(status) {
    if (!status || status.error) {
        if (status && status.error) appendLog(status.error, "error");
        return;
    }

    lastBackendStatus = status;
    const roomInfo = status.currentRoom || {};
    const visualRoomId = roomInfo.roomId || findVisualRoomId(roomInfo);
    const visualRoom = rooms[visualRoomId];

    if (visualRoom) {
        currentRoomId = visualRoomId;
        visualRoom.description = roomInfo.longDescription || roomInfo.shortDescription || visualRoom.description;
        visualRoom.items = itemNames(roomInfo.items);
    }

    const playerInfo = status.player || {};
    gameState.inventory = itemNames(playerInfo.inventory);

    if (status.completion) {
        gameState.completion = {
            roomsExplored: status.completion.roomsExplored || gameState.completion.roomsExplored,
            totalRooms: status.completion.totalRooms || gameState.completion.totalRooms,
            itemsCollected: status.completion.itemsCollected || gameState.completion.itemsCollected,
            totalItems: status.completion.totalItems || gameState.completion.totalItems
        };
    }

    if (status.treasureUnlocked !== undefined) {
        gameState.treasureUnlocked = status.treasureUnlocked;
    }

    gameState.visitedRooms.add(currentRoomId);
    updateLockedTreasuryExit();
    renderRoom();
}

async function refreshGameStatus() {
    if (!sessionId) return;

    try {
        const status = await getApi("status", { sessionId });
        syncFromBackendStatus(status);
    } catch (error) {
        appendLog("暂时无法同步后端状态，已保留当前前端状态。", "error");
    }
}

function enterGameFromAuth(data) {
    sessionId = data.sessionId || null;
    currentUsername = data.username || $("login-username").value.trim() || $("register-username").value.trim();
    updateLockedTreasuryExit();
    showView("game");
    renderRoom();
    appendLog(`欢迎进入游戏，${currentUsername || "player"}。`);
    if (data.message) appendLog(data.message);

    if (data.gameStatus) {
        syncFromBackendStatus(data.gameStatus);
    } else {
        refreshGameStatus();
    }
}

function showGameMenu(data) {
    pendingAuthData = data || {};
    sessionId = pendingAuthData.sessionId || null;
    currentUsername = pendingAuthData.username || $("login-username").value.trim() || $("register-username").value.trim();
    $("menu-welcome").textContent = `${currentUsername || "player"}，请选择进入游戏的方式。`;
    setMenuMessage("");
    showView("menu");
}

async function startGameFromMenu(mode) {
    if (!pendingAuthData) return;

    if (mode === "load" && !sessionId) {
        setMenuMessage("当前为离线会话，无法读取服务器存档。", "error");
        return;
    }

    setMenuMessage(mode === "load" ? "正在读取存档..." : "正在进入游戏...");
    enterGameFromAuth(pendingAuthData);

    if (mode === "load") {
        await loadSavedGame();
    }
}

function closeGameMenu() {
    $("game-menu-popover").classList.remove("open");
}

async function saveCurrentGame() {
    if (!sessionId) {
        appendLog("当前为离线会话，无法保存到服务器。", "error");
        return;
    }

    try {
        const response = await callApi("save", { sessionId });
        appendApiMessage(response);
    } catch (error) {
        appendLog("保存失败，请确认服务器和数据库连接正常。", "error");
    }
}

async function loadSavedGame() {
    if (!sessionId) {
        appendLog("当前为离线会话，无法读取服务器存档。", "error");
        return;
    }

    try {
        const response = await callApi("load", { sessionId });
        appendApiMessage(response);
        if (response && response.success) {
            if (response.gameStatus) {
                syncFromBackendStatus(response.gameStatus);
            } else {
                await refreshGameStatus();
            }
        }
    } catch (error) {
        appendLog("读取存档失败，请确认服务器已保存过进度。", "error");
    }
}

function normalizeCommand(command) {
    return command.trim().toLowerCase().replace(/\s+/g, " ");
}

function hasItem(itemName) {
    return gameState.inventory.includes(itemName);
}

function updateLockedTreasuryExit() {
    if (gameState.treasureUnlocked) {
        rooms.locked_room.exits.north = "unlocked_treasure_room";
    } else {
        delete rooms.locked_room.exits.north;
    }
}

function setPlayerPosition(position, options = {}) {
    const token = $("player-token");
    const instant = options.instant === true;

    if (instant) {
        token.classList.add("no-position-transition");
    }

    currentPlayerPosition = { left: position.left, top: position.top };
    token.style.left = position.left + "%";
    token.style.top = position.top + "%";

    if (instant) {
        token.offsetHeight;
        window.requestAnimationFrame(() => token.classList.remove("no-position-transition"));
    }
}

function setPlayerVisible(visible) {
    $("player-token").style.opacity = visible ? "1" : "0";
}

function renderRoom(entryPosition, options = {}) {
    const room = rooms[currentRoomId];
    $("scene-bg").src = room.image;
    $("room-name").textContent = room.title;

    const itemText = room.items && room.items.length
        ? ` 物品：${room.items.join("、")}。`
        : " 当前房间没有可拾取物品。";
    $("room-description").textContent = room.description + itemText;

    setPlayerPosition(entryPosition || room.start, { instant: options.instantPlayerPosition });
    updateHud();
    updateDirectionControls();
}

function getDirection(command) {
    if (command.includes("north")) return "north";
    if (command.includes("south")) return "south";
    if (command.includes("west")) return "west";
    if (command.includes("east")) return "east";
    return null;
}

function takeItem(itemName) {
    const room = rooms[currentRoomId];
    const items = room.items || [];

    if (!items.includes(itemName)) {
        appendLog(`这里没有 ${itemName}。`, "error");
        return;
    }

    room.items = items.filter((item) => item !== itemName);
    gameState.inventory.push(itemName);
    gameState.completion.itemsCollected = Math.max(gameState.completion.itemsCollected, gameState.inventory.length);
    renderRoom();
    appendLog(`你拾取了 ${itemName}。`);
}

function useItem(itemName) {
    if (itemName !== "key") {
        appendLog("这个物品暂时不能在这里使用。", "error");
        return;
    }

    if (!hasItem("key")) {
        appendLog("你的背包里没有钥匙。", "error");
        return;
    }

    if (currentRoomId !== "locked_room") {
        appendLog("钥匙需要在上锁的宝库门前使用。", "error");
        return;
    }

    if (gameState.treasureUnlocked) {
        appendLog("宝库已经打开了。");
        return;
    }

    gameState.treasureUnlocked = true;
    updateLockedTreasuryExit();
    renderRoom();
    appendLog("钥匙转动后，宝库北侧的门打开了。现在可以向北进入。");
}

function applyFrontEndCommand(command, options = {}) {
    const normalized = normalizeCommand(command);
    const shouldEchoLook = options.echoLook !== false;

    if (normalized.startsWith("take ") || normalized.startsWith("get ")) {
        takeItem(normalized.split(" ").slice(1).join(" "));
        return;
    }

    if (normalized.startsWith("use ")) {
        useItem(normalized.split(" ").slice(1).join(" "));
        return;
    }

    if (normalized === "items" || normalized === "inventory") {
        appendLog("背包：" + (gameState.inventory.join("、") || "空") + "。");
        return;
    }

    if (normalized === "status") {
        appendLog(`${rooms[currentRoomId].title}，背包 ${gameState.inventory.length} 件物品。`);
        return;
    }

    if (normalized === "look") {
        setPlayerPosition(rooms[currentRoomId].start);
        if (shouldEchoLook) appendLog(rooms[currentRoomId].description);
        return;
    }

    const direction = getDirection(normalized);
    if (direction) {
        moveToDirection(direction);
        return;
    }

    if (normalized === "back") {
        appendLog("返回上一房间将在后续步骤接入完整历史记录。");
        return;
    }

    appendLog("无法识别这条命令。", "error");
}

function appendApiMessage(data) {
    if (data && data.message) {
        appendLog(data.message, data.success === false ? "error" : "");
    }
}

async function handleCommand(command) {
    const normalized = normalizeCommand(command);
    const direction = getDirection(normalized);
    let response = null;

    if (direction) {
        const moved = await moveToDirection(direction);

        try {
            response = await sendGameCommand(normalized);
            appendApiMessage(response);
        } catch (error) {
            appendLog("后端命令暂时不可用，已保留前端移动结果。", "error");
        }

        if (!moved) {
            return;
        }

        return;
    }

    try {
        response = await sendGameCommand(normalized);
    } catch (error) {
        appendLog("后端命令暂时不可用，已执行前端交互。", "error");
    }

    if (response && response.success === false) {
        appendApiMessage(response);
        return;
    }

    appendApiMessage(response);
    applyFrontEndCommand(normalized, { echoLook: !response || !response.message });

    if (normalized !== "look" && normalized !== "status" && normalized !== "items") {
        await refreshGameStatus();
    }
}

async function moveToDirection(direction) {
    if (!direction || isMoving) return false;

    const room = rooms[currentRoomId];
    const nextRoomId = room.exits[direction];
    const path = room.paths && room.paths[direction];

    if (!nextRoomId || !path) {
        appendLog("这个方向没有出口。", "error");
        return false;
    }

    isMoving = true;
    startPlayerStep(direction);

    const route = getMovementRoute(path);
    for (const point of route) {
        setPlayerPosition(point);
        await wait(520);
    }

    stopPlayerStep(direction);
    setPlayerVisible(false);
    await wait(180);

    currentRoomId = nextRoomId;
    gameState.visitedRooms.add(currentRoomId);
    gameState.completion.roomsExplored = gameState.visitedRooms.size;
    renderRoom(getEntryPositionForDirection(direction, path), { instantPlayerPosition: true });
    setPlayerFrame(direction, 0);
    await wait(80);
    setPlayerVisible(true);
    appendLog(`你来到了 ${rooms[currentRoomId].title}。`);
    isMoving = false;

    if (currentRoomId === "teleport_room") {
        await showTeleportDialog();
    }

    return true;
}

function getEntryPositionForDirection(direction, path) {
    const fallback = path.enter || rooms[currentRoomId].start;

    if (direction === "north") {
        return { left: fallback.left, top: 86 };
    }

    if (direction === "south") {
        return { left: fallback.left, top: 38 };
    }

    if (direction === "east") {
        return { left: 14, top: fallback.top };
    }

    if (direction === "west") {
        return { left: 86, top: fallback.top };
    }

    return fallback;
}

function getMovementRoute(path) {
    if (Array.isArray(path.route) && path.route.length) {
        return path.route;
    }

    const start = currentPlayerPosition;
    const exit = path.exit;
    const mid = {
        left: Math.round(((start.left + exit.left) / 2) * 10) / 10,
        top: Math.round(((start.top + exit.top) / 2) * 10) / 10
    };

    return [mid, exit];
}

async function showTeleportDialog() {
    const confirmed = confirm("你进入了传送房间！是否要进行随机传送？\n\n点击[确定]随机传送到其他房间，点击[取消]留在此处用方向键移动。");
    if (confirmed) {
        try {
            const response = await sendGameCommand("teleport");
            if (response && response.success) {
                appendApiMessage(response);
                await refreshGameStatus();
            }
        } catch (error) {
            appendLog("传送失败。", "error");
        }
    }
}

function setPlayerFrame(direction, frameIndex) {
    const frames = playerFrames[direction] || playerFrames.south;
    $("player-token").style.backgroundImage = `url("${frames[frameIndex % frames.length]}")`;
}

function startPlayerStep(direction) {
    if (!direction) return;

    const token = $("player-token");
    let frameIndex = 0;
    window.clearInterval(playerAnimationTimer);
    token.classList.add("walking");
    setPlayerFrame(direction, frameIndex);

    playerAnimationTimer = window.setInterval(() => {
        frameIndex += 1;
        setPlayerFrame(direction, frameIndex);
    }, 110);

}

function stopPlayerStep(direction) {
    window.clearInterval(playerAnimationTimer);
    playerAnimationTimer = null;
    $("player-token").classList.remove("walking");
    setPlayerFrame(direction || "south", 0);
}

async function submitCommand(presetCommand) {
    if (commandBusy) return;

    const command = (presetCommand || $("command-input").value).trim();
    if (!command) {
        appendLog("请输入命令。", "error");
        return;
    }

    commandBusy = true;
    setCommandControlsDisabled(true);
    appendLog("> " + command, "command");
    await handleCommand(command);
    if (!presetCommand) $("command-input").value = "";
    setCommandControlsDisabled(false);
    commandBusy = false;
}

$("login-tab").addEventListener("click", () => setActiveAuthTab("login"));
$("register-tab").addEventListener("click", () => setActiveAuthTab("register"));

$("login-form").addEventListener("submit", async (event) => {
    event.preventDefault();
    const username = $("login-username").value.trim();
    const password = $("login-password").value;

    if (!username || !password) {
        setFormMessage("login-message", "请输入用户名和密码。");
        return;
    }

    setFormMessage("login-message", "正在登录...");
    setBusy("login-btn", true, "登录中");
    try {
        const data = await callApi("login", { username, password });
        if (!data.success) {
            setFormMessage("login-message", data.message || "登录失败。");
            return;
        }
        setFormMessage("login-message", "");
        showGameMenu(data);
    } catch (error) {
        setFormMessage("login-message", "暂时无法连接后端，已进入本地预览。");
        showGameMenu({ username, sessionId: null });
    } finally {
        setBusy("login-btn", false);
    }
});

$("register-form").addEventListener("submit", async (event) => {
    event.preventDefault();
    const username = $("register-username").value.trim();
    const password = $("register-password").value;

    if (!username || !password) {
        setFormMessage("register-message", "请输入用户名和密码。");
        return;
    }

    setFormMessage("register-message", "正在注册...");
    setBusy("register-btn", true, "注册中");
    try {
        const data = await callApi("register", { username, password });
        if (!data.success) {
            setFormMessage("register-message", data.message || "注册失败。");
            return;
        }
        setFormMessage("register-message", "");
        showGameMenu(data);
    } catch (error) {
        setFormMessage("register-message", "暂时无法连接后端，已进入本地预览。");
        showGameMenu({ username, sessionId: null });
    } finally {
        setBusy("register-btn", false);
    }
});

$("logout-btn").addEventListener("click", async () => {
    if (sessionId) {
        try {
            await callApi("logout", { sessionId });
        } catch (error) {
            appendLog("退出时无法连接后端。", "error");
        }
    }
    sessionId = null;
    currentUsername = null;
    pendingAuthData = null;
    showView("auth");
});

$("start-game-btn").addEventListener("click", () => startGameFromMenu("new"));
$("load-game-btn").addEventListener("click", () => startGameFromMenu("load"));
$("continue-game-btn").addEventListener("click", () => startGameFromMenu("continue"));

$("save-game-btn").addEventListener("click", () => saveCurrentGame());
$("game-menu-btn").addEventListener("click", () => $("game-menu-popover").classList.toggle("open"));
$("menu-back-btn").addEventListener("click", () => {
    closeGameMenu();
    showView("menu");
});
$("menu-load-btn").addEventListener("click", async () => {
    closeGameMenu();
    await loadSavedGame();
});

$("submit-btn").addEventListener("click", () => submitCommand());

$("command-input").addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
        event.preventDefault();
        submitCommand();
    }
});

document.querySelectorAll(".direction-pad button").forEach((button) => {
    button.addEventListener("click", () => submitCommand(button.dataset.command));
});

bindFloatingPanels();
updateLockedTreasuryExit();
renderRoom();
