const $ = (id) => document.getElementById(id);

const views = {
    auth: $("auth-view"),
    game: $("game-view")
};

const API_BASE = "";

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
        title: "Campus Gate",
        description: "You are standing at the campus gate. A small key lies near the stone path.",
        image: "assets/rooms/campus_gate.png",
        exits: { north: "main_hall" },
        items: ["key"],
        start: { left: 48, top: 66 },
        paths: {
            north: {
                exit: { left: 52, top: 38 },
                enter: { left: 50, top: 70 }
            }
        }
    },
    main_hall: {
        title: "Main Hall",
        description: "The main hall connects the key study areas around the campus.",
        image: "assets/rooms/main_hall.png",
        exits: {
            south: "campus_gate",
            east: "library",
            west: "forest_path",
            north: "lab"
        },
        start: { left: 50, top: 70 },
        paths: {
            north: {
                exit: { left: 50, top: 35 },
                enter: { left: 50, top: 72 }
            },
            south: {
                exit: { left: 48, top: 86 },
                enter: { left: 52, top: 42 }
            },
            east: {
                exit: { left: 84, top: 58 },
                enter: { left: 18, top: 58 }
            },
            west: {
                exit: { left: 16, top: 58 },
                enter: { left: 82, top: 58 }
            }
        }
    },
    forest_path: {
        title: "Forest Path",
        description: "A quiet forest path runs beside the buildings.",
        image: "assets/rooms/forest_path.png",
        exits: { east: "main_hall", north: "teleport_room" },
        start: { left: 40, top: 64 },
        paths: {
            east: {
                exit: { left: 78, top: 56 },
                enter: { left: 16, top: 58 }
            },
            north: {
                exit: { left: 52, top: 34 },
                enter: { left: 48, top: 74 }
            }
        }
    },
    library: {
        title: "Library",
        description: "Warm lights and long desks fill the library.",
        image: "assets/rooms/library.png",
        exits: { west: "main_hall", north: "locked_room" },
        start: { left: 52, top: 62 },
        paths: {
            west: {
                exit: { left: 14, top: 60 },
                enter: { left: 84, top: 58 }
            },
            north: {
                exit: { left: 74, top: 40 },
                enter: { left: 42, top: 76 }
            }
        }
    },
    lab: {
        title: "Computer Lab",
        description: "The computer lab is filled with equipment and scattered notes.",
        image: "assets/rooms/lab.png",
        exits: { south: "main_hall", east: "locked_room" },
        start: { left: 50, top: 68 },
        paths: {
            south: {
                exit: { left: 50, top: 84 },
                enter: { left: 50, top: 35 }
            },
            east: {
                exit: { left: 82, top: 58 },
                enter: { left: 22, top: 76 }
            }
        }
    },
    locked_room: {
        title: "Locked Treasury",
        description: "A sealed treasury door blocks the way forward.",
        image: "assets/rooms/locked_room.png",
        exits: { south: "library", west: "lab" },
        start: { left: 50, top: 76 },
        paths: {
            south: {
                exit: { left: 42, top: 86 },
                enter: { left: 74, top: 40 }
            },
            west: {
                exit: { left: 18, top: 76 },
                enter: { left: 82, top: 58 }
            },
            north: {
                exit: { left: 58, top: 44 },
                enter: { left: 50, top: 76 }
            }
        }
    },
    unlocked_treasure_room: {
        title: "Treasure Room",
        description: "The treasure room glows with warm light and hidden rewards.",
        image: "assets/rooms/unlocked_treasure_room2.png",
        exits: { south: "locked_room" },
        start: { left: 50, top: 76 },
        paths: {
            south: {
                exit: { left: 50, top: 86 },
                enter: { left: 58, top: 76 }
            }
        }
    },
    teleport_room: {
        title: "Teleport Room",
        description: "A strange room hums with unstable magical energy.",
        image: "assets/rooms/teleport_room.png",
        exits: { south: "forest_path", east: "main_hall" },
        start: { left: 50, top: 70 },
        paths: {
            south: {
                exit: { left: 48, top: 84 },
                enter: { left: 52, top: 34 }
            },
            east: {
                exit: { left: 80, top: 58 },
                enter: { left: 16, top: 58 }
            }
        }
    }
};

const backendRoomAliases = [
    { patterns: ["大学主入口", "campus", "outside"], roomId: "campus_gate" },
    { patterns: ["演讲厅", "theater"], roomId: "library" },
    { patterns: ["校园酒吧", "pub"], roomId: "forest_path" },
    { patterns: ["计算机实验室", "lab"], roomId: "lab" },
    { patterns: ["计算机管理办公室", "office"], roomId: "main_hall" },
    { patterns: ["上锁的宝库", "宝库", "treasure"], roomId: "locked_room" },
    { patterns: ["传输房间", "传送房间", "transporter"], roomId: "teleport_room" }
];

let playerAnimationTimer = null;
let currentRoomId = "campus_gate";
let isMoving = false;
let commandBusy = false;
let sessionId = null;
let currentUsername = null;
let lastBackendStatus = null;
const gameState = {
    inventory: [],
    treasureUnlocked: false
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

function appendLog(text) {
    const line = document.createElement("p");
    line.textContent = text;
    $("output-area").appendChild(line);
    $("output-area").scrollTop = $("output-area").scrollHeight;
}

function setFormMessage(id, text) {
    $(id).textContent = text;
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
        button.disabled = disabled;
    });
}

async function callApi(endpoint, payload) {
    const response = await fetch(`${API_BASE}/api/${endpoint}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
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

function appendApiMessage(data) {
    if (data && data.message) {
        appendLog(data.message);
    }
}

function findVisualRoomId(roomInfo) {
    if (!roomInfo) return currentRoomId;

    const text = [
        roomInfo.shortDescription,
        roomInfo.longDescription
    ].filter(Boolean).join(" ").toLowerCase();

    const match = backendRoomAliases.find((entry) => {
        return entry.patterns.some((pattern) => text.includes(pattern.toLowerCase()));
    });

    return match ? match.roomId : currentRoomId;
}

function itemNames(items) {
    if (!Array.isArray(items)) return [];
    return items.map((item) => item && item.name).filter(Boolean);
}

function syncFromBackendStatus(status) {
    if (!status || status.error) {
        if (status && status.error) {
            appendLog(status.error);
        }
        return;
    }

    lastBackendStatus = status;

    const roomInfo = status.currentRoom || {};
    const visualRoomId = findVisualRoomId(roomInfo);
    const visualRoom = rooms[visualRoomId];

    if (visualRoom) {
        currentRoomId = visualRoomId;
        visualRoom.description = roomInfo.longDescription || roomInfo.shortDescription || visualRoom.description;
        visualRoom.items = itemNames(roomInfo.items);
        renderRoom();
    }

    const playerInfo = status.player || {};
    gameState.inventory = itemNames(playerInfo.inventory);

    const inventoryText = gameState.inventory.length ? gameState.inventory.join(", ") : "空";
    appendLog("背包：" + inventoryText + "。");

    if (status.completion) {
        const completion = status.completion;
        appendLog(
            "进度：房间 " + completion.roomsExplored + "/" + completion.totalRooms +
            "，物品 " + completion.itemsCollected + "/" + completion.totalItems + "。"
        );
    }
}

async function refreshGameStatus() {
    if (!sessionId) return;

    try {
        const status = await getApi("status", { sessionId });
        syncFromBackendStatus(status);
    } catch (error) {
        appendLog("状态同步暂时不可用。");
    }
}

function enterGameFromAuth(data) {
    sessionId = data.sessionId || null;
    currentUsername = data.username || $("login-username").value.trim() || $("register-username").value.trim();
    updateLockedTreasuryExit();
    showView("game");
    renderRoom();
    appendLog("已登录：" + (currentUsername || "player") + "。");
    if (data.message) {
        appendLog(data.message);
    }
    if (data.gameStatus) {
        syncFromBackendStatus(data.gameStatus);
    } else {
        refreshGameStatus();
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

function setPlayerPosition(position) {
    $("player-token").style.left = position.left + "%";
    $("player-token").style.top = position.top + "%";
}

function renderRoom(entryPosition) {
    const room = rooms[currentRoomId];
    $("scene-bg").src = room.image;
    $("room-name").textContent = room.title;
    const itemText = room.items && room.items.length
        ? " Items here: " + room.items.join(", ") + "."
        : "";
    $("room-description").textContent = room.description + itemText;
    setPlayerPosition(entryPosition || room.start);
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
        appendLog("这里没有 " + itemName + "。");
        return;
    }

    room.items = items.filter((item) => item !== itemName);
    gameState.inventory.push(itemName);
    renderRoom();
    appendLog("已拾取：" + itemName + "。");
}

function useItem(itemName) {
    if (itemName !== "key") {
        appendLog("这里不能使用该物品。");
        return;
    }

    if (!hasItem("key")) {
        appendLog("你需要先获得钥匙。");
        return;
    }

    if (currentRoomId !== "locked_room") {
        appendLog("附近没有可以用钥匙打开的入口。");
        return;
    }

    if (gameState.treasureUnlocked) {
        appendLog("宝藏房间已经解锁。");
        return;
    }

    gameState.treasureUnlocked = true;
    updateLockedTreasuryExit();
    appendLog("宝库门锁已打开，北侧路径已经开放。");
}

function applyFrontEndCommand(command, options = {}) {
    const normalized = normalizeCommand(command);
    const shouldEchoLook = options.echoLook !== false;

    if (normalized === "take key" || normalized === "get key") {
        takeItem("key");
        return;
    }

    if (normalized === "use key") {
        useItem("key");
        return;
    }

    if (normalized === "items" || normalized === "inventory") {
        appendLog("背包：" + (gameState.inventory.join(", ") || "空") + "。");
        return;
    }

    if (normalized === "look") {
        if (shouldEchoLook) {
            appendLog(rooms[currentRoomId].description);
        }
        return;
    }

    const direction = getDirection(normalized);
    if (direction) {
        moveToDirection(direction);
        return;
    }

    appendLog("无法识别该命令。");
}

async function handleCommand(command) {
    const normalized = normalizeCommand(command);
    let response = null;

    try {
        response = await sendGameCommand(normalized);
    } catch (error) {
        appendLog("命令服务暂时不可用。");
        return;
    }

    if (response && response.success === false) {
        appendApiMessage(response);
        return;
    }

    appendApiMessage(response);
    applyFrontEndCommand(normalized, { echoLook: !response || !response.message });
    await refreshGameStatus();
}

function moveToDirection(direction) {
    if (!direction || isMoving) return;

    const room = rooms[currentRoomId];
    const nextRoomId = room.exits[direction];
    const path = room.paths && room.paths[direction];

    playPlayerStep(direction);

    if (!nextRoomId || !path) {
        appendLog("这个方向没有出口。");
        return;
    }

    isMoving = true;
    setPlayerPosition(path.exit);
    window.setTimeout(() => {
        currentRoomId = nextRoomId;
        renderRoom(path.enter);
        appendLog("已到达：" + rooms[currentRoomId].title + "。");
        isMoving = false;
    }, 980);
}

function setPlayerFrame(direction, frameIndex) {
    const frames = playerFrames[direction] || playerFrames.south;
    $("player-token").style.backgroundImage = `url("${frames[frameIndex % frames.length]}")`;
}

function playPlayerStep(direction) {
    if (!direction) return;

    const token = $("player-token");
    let frameIndex = 0;
    window.clearInterval(playerAnimationTimer);
    token.classList.add("walking");
    setPlayerFrame(direction, frameIndex);

    playerAnimationTimer = window.setInterval(() => {
        frameIndex += 1;
        setPlayerFrame(direction, frameIndex);
    }, 90);

    window.setTimeout(() => {
        window.clearInterval(playerAnimationTimer);
        playerAnimationTimer = null;
        token.classList.remove("walking");
        setPlayerFrame(direction, 0);
    }, 980);
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
        enterGameFromAuth(data);
    } catch (error) {
        setFormMessage("login-message", "暂时无法连接登录服务。");
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

    setFormMessage("register-message", "正在创建账号...");
    setBusy("register-btn", true, "创建中");
    try {
        const data = await callApi("register", { username, password });
        if (!data.success) {
            setFormMessage("register-message", data.message || "注册失败。");
            return;
        }
        setFormMessage("register-message", "");
        enterGameFromAuth(data);
    } catch (error) {
        setFormMessage("register-message", "暂时无法连接注册服务。");
    } finally {
        setBusy("register-btn", false);
    }
});

$("logout-btn").addEventListener("click", async () => {
    if (sessionId) {
        try {
            await callApi("logout", { sessionId });
        } catch (error) {
            appendLog("退出服务暂时不可用。");
        }
    }
    sessionId = null;
    currentUsername = null;
    showView("auth");
});

async function submitCommand() {
    if (commandBusy) return;

    const command = $("command-input").value.trim();
    if (!command) {
        appendLog("请输入命令。");
        return;
    }

    commandBusy = true;
    setCommandControlsDisabled(true);
    appendLog("> " + command);
    await handleCommand(command);
    $("command-input").value = "";
    setCommandControlsDisabled(false);
    commandBusy = false;
}

$("submit-btn").addEventListener("click", submitCommand);

$("command-input").addEventListener("keydown", (event) => {
    if (event.key === "Enter") {
        event.preventDefault();
        submitCommand();
    }
});

document.querySelectorAll(".direction-pad button").forEach((button) => {
    button.addEventListener("click", async () => {
        if (commandBusy) return;

        commandBusy = true;
        setCommandControlsDisabled(true);
        appendLog("> " + button.dataset.command);
        await handleCommand(button.dataset.command);
        setCommandControlsDisabled(false);
        commandBusy = false;
    });
});
