const $ = (id) => document.getElementById(id);

const views = {
    auth: $("auth-view"),
    menu: $("menu-view"),
    game: $("game-view")
};

const API_BASE = "";

const itemMeta = {
    key: { label: "key", weight: 0.1, icon: "assets/items/gold_key.png", description: "一把生锈的旧钥匙，可以打开上锁的房间。" },
    cookie: { label: "cookie", weight: 0.2, icon: "assets/items/pumpkin.png", description: "一块美味的饼干，吃了可以增加5kg的负重能力。" },
    computer: { label: "computer", weight: 2.5, icon: "assets/items/crystal_ore.png", description: "一台古老的计算机，似乎藏着什么秘密。" },
    cable: { label: "cable", weight: 0.1, icon: "assets/items/pickaxe.png", description: "一根数据线，可以连接计算机和其他设备。" },
    coin: { label: "coin", weight: 0.1, icon: "assets/items/shield.png", description: "一枚金币，可能在某个地方派上用场。" },
    bottle: { label: "bottle", weight: 0.4, icon: "assets/items/green_potion.png", description: "一瓶神秘的绿色药水，暂时不知道有什么用。" },
    map: { label: "map", weight: 0.1, icon: "assets/items/scroll.png", description: "一张校园地图，可以帮助你探索。", mapImage: "assets/maps/campus_overview.png" },
    treasure: { label: "treasure", weight: 0.1, icon: "assets/items/gold_key.png", description: "传说中的宝藏！" }
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
                    { left: 50, top: 76 },
                    { left: 50, top: 64 },
                    { left: 50, top: 52 },
                    { left: 50, top: 40 },
                    { left: 50, top: 28 },
                    { left: 50, top: 14 }
                ],
                exit: { left: 50, top: 50 },
                enter: { left: 50, top: 55 }
            }
        }
    },
    main_hall: {
        title: "主大厅",
        description: "主大厅连接着校园里的几个重要区域。",
        image: "assets/rooms/main_hall.png",
        exits: { south: "campus_gate", east: "library", west: "forest_path", north: "lab" },
        items: ["cookie"],
        start: { left: 50, top: 50 },
        paths: {
            north: {
                route: [{ left: 50, top: 63 }, { left: 50, top: 38 }, { left: 50, top: 14 }],
                exit: { left: 50, top: 14 },
                enter: { left: 50, top: 86 }
            },
            south: {
                route: [{ left: 50, top: 63 }, { left: 50, top: 74 }, { left: 50, top: 86 }],
                exit: { left: 50, top: 86 },
                enter: { left: 50, top: 14 }
            },
            east: {
                route: [{ left: 50, top: 63 }, { left: 62, top: 63 }, { left: 74, top: 63 }, { left: 86, top: 63 }],
                exit: { left: 86, top: 63 },
                enter: { left: 14, top: 63 }
            },
            west: {
                route: [{ left: 50, top: 63 }, { left: 38, top: 63 }, { left: 26, top: 63 }, { left: 14, top: 63 }],
                exit: { left: 14, top: 63 },
                enter: { left: 86, top: 63 }
            }
        }
    },
    forest_path: {
        title: "校园酒吧",
        description: "花园小路旁有休息区和温室，石板路通向更深处。",
        image: "assets/rooms/forest_path.png",
        exits: { east: "main_hall", north: "teleport_room" },
        items: ["bottle", "map"],
        start: { left: 50, top: 50 },
        paths: {
            east: {
                route: [{ left: 50, top: 50 }, { left: 62, top: 50 }, { left: 74, top: 50 }, { left: 86, top: 50 }],
                exit: { left: 86, top: 50 },
                enter: { left: 80, top: 50 }
            },
            north: {
                route: [{ left: 50, top: 50 }, { left: 50, top: 38 }, { left: 50, top: 26 }, { left: 50, top: 14 }],
                exit: { left: 50, top: 25 },
                enter: { left: 50, top: 30 }
            }
        }
    },
    library: {
        title: "图书馆",
        description: "温暖的灯光照在长桌和书架上，桌面上散落着笔记。",
        image: "assets/rooms/library.png",
        exits: { west: "main_hall", north: "locked_room" },
        items: ["coin"],
        start: { left: 50, top: 65 },
        paths: {
            west: {
                route: [{ left: 50, top: 65 }, { left: 38, top: 65 }, { left: 26, top: 65 }, { left: 14, top: 65 }],
                exit: { left: 14, top: 65 },
                enter: { left: 86, top: 65 }
            },
            north: {
                route: [{ left: 50, top: 65 }, { left: 50, top: 38 }, { left: 50, top: 26 }, { left: 50, top: 14 }],
                exit: { left: 50, top: 35 },
                enter: { left: 50, top: 40 }
            }
        }
    },
    lab: {
        title: "计算机实验室",
        description: "你在计算机实验室。设备、线缆和笔记本电脑摆放在实验台附近。",
        image: "assets/rooms/lab.png",
        exits: { south: "main_hall", east: "locked_room" },
        items: ["computer", "cable"],
        start: { left: 50, top: 50 },
        paths: {
            south: {
                route: [{ left: 50, top: 50 }, { left: 50, top: 62 }, { left: 50, top: 74 }, { left: 50, top: 86 }],
                exit: { left: 50, top: 86 },
                enter: { left: 50, top: 14 }
            },
            east: {
                route: [{ left: 50, top: 50 }, { left: 62, top: 50 }, { left: 74, top: 50 }, { left: 86, top: 50 }],
                exit: { left: 86, top: 50 },
                enter: { left: 14, top: 50 }
            }
        }
    },
    locked_room: {
        title: "上锁的宝库",
        description: "厚重的宝库门被锁住了。使用钥匙后，向北可以进入宝藏房间。",
        image: "assets/rooms/locked_room.png",
        exits: { west: "lab", south: "library", north: "unlocked_treasure_room" },
        items: [],
        start: { left: 75, top: 75 },
        paths: {
            west: {
                route: [{ left: 50, top: 50 }, { left: 38, top: 50 }, { left: 26, top: 50 }, { left: 14, top: 50 }],
                exit: { left: 14, top: 75 },
                enter: { left: 86, top: 50 }
            },
            south: {
                route: [{ left: 50, top: 50 }, { left: 50, top: 62 }, { left: 50, top: 74 }, { left: 50, top: 86 }],
                exit: { left: 75, top: 95 },
                enter: { left: 50, top: 14 }
            },
            north: {
                route: [{ left: 50, top: 50 }, { left: 50, top: 38 }, { left: 50, top: 26 }, { left: 50, top: 14 }],
                exit: { left: 75, top: 60 },
                enter: { left: 50, top: 86 }
            }
        }
    },
    unlocked_treasure_room: {
        title: "开锁后的宝库",
        description: "宝库已经打开，金色灯光照亮了收藏架和宝箱。",
        image: "assets/rooms/unlocked_treasure_room2.png",
        exits: { south: "locked_room" },
        items: ["treasure"],
        start: { left: 50, top: 50 },
        paths: {
            south: {
                route: [{ left: 50, top: 50 }, { left: 50, top: 62 }, { left: 50, top: 74 }, { left: 50, top: 86 }],
                exit: { left: 50, top: 86 },
                enter: { left: 50, top: 14 }
            }
        }
    },
    teleport_room: {
        title: "传送房间",
        description: "传送装置发出蓝色光芒，空气里有轻微的嗡鸣。",
        image: "assets/rooms/teleport_room.png",
        exits: { south: "forest_path", east: "main_hall" },
        items: [],
        start: { left: 50, top: 50 },
        paths: {
            south: {
                route: [{ left: 50, top: 50 }, { left: 50, top: 62 }, { left: 50, top: 74 }, { left: 50, top: 86 }],
                exit: { left: 50, top: 86 },
                enter: { left: 50, top: 14 }
            },
            east: {
                route: [{ left: 50, top: 50 }, { left: 62, top: 50 }, { left: 74, top: 50 }, { left: 86, top: 50 }],
                exit: { left: 86, top: 50 },
                enter: { left: 14, top: 50 }
            }
        }
    }
};

const GLOBAL_STEP_SIZE = 6;
const GLOBAL_ANIMATION_SPEED = 500;

const ROOM_CONFIGS = {
    campus_gate: {
        center: { x: 50, y: 76 },
        channels: ['vertical'],
        verticalRange: { min: 50, max: 95 },
        entryPoints: {
            south: { x: 50, y: 55 }
        },
        walkTo: { x: 50, y: 76 }
    },
    main_hall: {
        center: { x: 50, y: 63 },
        channels: ['vertical', 'horizontal'],
        verticalRange: { min: 5, max: 95 },
        horizontalRange: { min: 5, max: 95 },
        entryPoints: {
            north: { x: 50, y: 86 },
            south: { x: 50, y: 14 },
            east: { x: 14, y: 63 },
            west: { x: 86, y: 63 }
        },
        walkTo: { x: 50, y: 63 }
    },
    forest_path: {
        center: { x: 50, y: 50 },
        channels: ['vertical', 'horizontal'],
        verticalRange: { min: 20, max: 95 },
        horizontalRange: { min: 20, max: 95 },
        entryPoints: {
            west: { x: 86, y: 50 },
            south: { x: 50, y: 30 }
        },
        walkTo: { x: 50, y: 50 }
    },
    library: {
        center: { x: 50, y: 65 },
        channels: ['vertical', 'horizontal'],
        verticalRange: { min: 5, max: 95 },
        horizontalRange: { min: 5, max: 95 },
        entryPoints: {
            east: { x: 14, y: 65 },
            south: { x: 50, y: 40 }
        },
        walkTo: { x: 50, y: 65 }
    },
    lab: {
        center: { x: 50, y: 50 },
        channels: ['vertical', 'horizontal'],
        verticalRange: { min: 40, max: 95 },
        horizontalRange: { min: 15, max: 95 },
        entryPoints: {
            north: { x: 50, y: 86 },
            west: { x: 86, y: 50 }
        },
        walkTo: { x: 50, y: 50 }
    },
    locked_room: {
        center: { x: 75, y: 75 },
        channels: ['vertical', 'horizontal'],
        verticalRange: { min: 60, max: 95 },
        horizontalRange: { min: 5, max: 95 },
        entryPoints: {
            north: { x: 75, y: 80 },
            east: { x: 14, y: 75 },
            south: { x: 75, y: 65 }
        },
        walkTo: { x: 75, y: 75 }
    },
    unlocked_treasure_room: {
        center: { x: 50, y: 50 },
        channels: ['vertical'],
        verticalRange: { min: 40, max: 95 },
        entryPoints: {
            north: { x: 50, y: 86 }
        },
        walkTo: { x: 50, y: 50 }
    },
    teleport_room: {
        center: { x: 50, y: 50 },
        channels: ['vertical', 'horizontal'],
        verticalRange: { min: 5, max: 95 },
        horizontalRange: { min: 5, max: 95 },
        entryPoints: {
            north: { x: 50, y: 86 },
            west: { x: 86, y: 50 }
        },
        walkTo: { x: 50, y: 50 }
    }
};

const backendRoomAliases = [
    { patterns: ["outside", "campus", "入口", "校园入口"], roomId: "campus_gate" },
    { patterns: ["hall", "大厅", "main"], roomId: "main_hall" },
    { patterns: ["library", "图书馆"], roomId: "library" },
    { patterns: ["computer lab", "lab", "实验室", "计算机实验室"], roomId: "lab" },
    { patterns: ["forest", "pub", "bar", "酒吧", "小路"], roomId: "forest_path" },
    { patterns: ["locked", "treasury", "宝库", "上锁"], roomId: "locked_room" },
    { patterns: ["unlocked", "开锁后", "宝藏", "treasure"], roomId: "unlocked_treasure_room" },
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

// WASD按键按住状态追踪
let keyHoldState = {
    key: null,
    startTime: 0,
    isHolding: false,
    holdTimer: null
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

    // 物品详情关闭按钮
    $("item-detail-close").addEventListener("click", hideItemDetail);
    // 点击遮罩也关闭
    $("item-detail-modal").addEventListener("click", (e) => {
        if (e.target === $("item-detail-modal")) {
            hideItemDetail();
        }
    });
    
    // 房间状态关闭按钮
    $("room-status-close").addEventListener("click", () => {
        $("room-status-modal").classList.remove("open");
    });
    $("room-status-modal").addEventListener("click", (e) => {
        if (e.target === $("room-status-modal")) {
            $("room-status-modal").classList.remove("open");
        }
    });
    
    // 房间物品关闭按钮
    $("room-items-close").addEventListener("click", () => {
        $("room-items-modal").classList.remove("open");
    });
    $("room-items-modal").addEventListener("click", (e) => {
        if (e.target === $("room-items-modal")) {
            $("room-items-modal").classList.remove("open");
        }
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
    return `<button class="item-pill" data-item="${name}"><img src="${meta.icon}" alt="">${label}</button>`;
}

function showItemDetail(itemName) {
    const meta = itemMeta[itemName] || { label: itemName, icon: "assets/items/scroll.png", description: "未知物品", weight: 0 };
    $("item-detail-icon").src = meta.icon;
    $("item-detail-title").textContent = meta.label;
    $("item-detail-weight").textContent = `重量：${meta.weight}kg`;
    $("item-detail-description").textContent = meta.description || "暂无描述";
    
    // 处理地图预览
    const mapPreview = $("item-map-preview");
    const modalCard = $("item-detail-modal").querySelector(".modal-card");
    if (meta.mapImage) {
        mapPreview.src = meta.mapImage;
        mapPreview.style.display = "block";
        modalCard.classList.add("item-detail-card--map");
    } else {
        mapPreview.style.display = "none";
        modalCard.classList.remove("item-detail-card--map");
    }
    
    $("item-detail-modal").classList.add("open");
}

function hideItemDetail() {
    $("item-detail-modal").classList.remove("open");
}

function renderInventory() {
    const box = $("inventory-list");
    if (!gameState.inventory.length) {
        box.textContent = "暂无物品";
    } else {
        box.innerHTML = gameState.inventory.map(itemPill).join("");
        // 添加点击事件
        box.querySelectorAll(".item-pill").forEach(btn => {
            btn.addEventListener("click", () => {
                const itemName = btn.getAttribute("data-item");
                if (itemName) showItemDetail(itemName);
            });
        });
    }

    const playerInfo = lastBackendStatus && lastBackendStatus.player ? lastBackendStatus.player : {};
    const totalWeight = playerInfo.totalWeight != null ? playerInfo.totalWeight : inventoryWeight();
    const maxWeight = playerInfo.maxWeight != null ? playerInfo.maxWeight : 10;
    $("weight-info").textContent = `负重 ${formatWeight(totalWeight)}/${formatWeight(maxWeight)}kg`;
}

function renderQuickActions() {
    const room = rooms[currentRoomId];
    const actions = [
        { label: "查看状态", action: "showRoomStatus" },
        { label: "查看物品", action: "showRoomItems" },
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
        if (action.action === "showRoomStatus") {
            button.addEventListener("click", showRoomStatus);
        } else if (action.action === "showRoomItems") {
            button.addEventListener("click", showRoomItems);
        } else {
            button.addEventListener("click", () => submitCommand(action.command));
        }
        box.appendChild(button);
    });
}

function showRoomStatus() {
    const room = rooms[currentRoomId];
    
    // 显示当前位置
    $("room-status-location").textContent = `当前位置：${room.title || currentRoomId}`;
    
    // 显示可走的方向
    const exitsContainer = $("room-status-exits");
    exitsContainer.innerHTML = "";
    
    const directionNames = {
        "north": "北",
        "south": "南",
        "east": "东",
        "west": "西"
    };
    
    let hasExits = false;
    for (const dir in room.exits) {
        if (room.exits[dir]) {
            hasExits = true;
            const span = document.createElement("span");
            span.className = "room-status-exit";
            span.textContent = directionNames[dir] || dir;
            exitsContainer.appendChild(span);
        }
    }
    
    if (!hasExits) {
        exitsContainer.textContent = "没有可前往的方向";
    }
    
    $("room-status-modal").classList.add("open");
}

function showRoomItems() {
    const room = rooms[currentRoomId];
    const itemsList = $("room-items-list");
    
    if (!room.items || room.items.length === 0) {
        itemsList.innerHTML = '<div class="room-items-empty">这个房间里没有物品了</div>';
    } else {
        let html = '<div class="room-items-grid">';
        room.items.forEach((itemName) => {
            const meta = itemMeta[itemName] || { label: itemName, weight: 0, icon: "assets/items/scroll.png" };
            html += `
                <div class="room-item-card">
                    <img class="room-item-icon" src="${meta.icon}" alt="${meta.label}">
                    <div class="room-item-info">
                        <div class="room-item-name">${meta.label}</div>
                        <div class="room-item-weight">重量：${meta.weight}kg</div>
                    </div>
                </div>`;
        });
        html += '</div>';
        itemsList.innerHTML = html;
    }
    
    $("room-items-modal").classList.add("open");
}

function renderProgress() {
    const completion = gameState.completion;
    $("room-progress").textContent =
        `房间 ${completion.roomsExplored}/${completion.totalRooms}，物品 ${gameState.inventory.length}/${completion.totalItems}`;
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
    
    // 如果当前是在 unlocked_treasure_room，保持当前房间不变
    let visualRoomId;
    if (currentRoomId === 'unlocked_treasure_room') {
        visualRoomId = 'unlocked_treasure_room';
    } else {
        visualRoomId = roomInfo.roomId || findVisualRoomId(roomInfo);
    }
    
    const visualRoom = rooms[visualRoomId];

    if (visualRoom) {
        // 如果当前不是在特殊房间，才更新 currentRoomId
        if (currentRoomId !== 'unlocked_treasure_room') {
            currentRoomId = visualRoomId;
        }
        visualRoom.description = roomInfo.longDescription || roomInfo.shortDescription || visualRoom.description;
        // 不要让后端覆盖我们对 locked_room 和 unlocked_treasure_room 的物品配置
        if (visualRoomId !== 'locked_room' && visualRoomId !== 'unlocked_treasure_room') {
            visualRoom.items = itemNames(roomInfo.items);
        }
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

    if (status.completion && status.completion.cookieEaten) {
        rooms.main_hall.items = (rooms.main_hall.items || []).filter((i) => i !== "cookie");
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

    if (mode === "new") {
        resetFrontendState();
        sessionId = pendingAuthData.sessionId || null;
        currentUsername = pendingAuthData.username || $("login-username").value.trim() || $("register-username").value.trim();
        updateLockedTreasuryExit();
        showView("game");
        renderRoom();
        appendLog("新游戏已开始！");
        if (sessionId) {
            try {
                const response = await callApi("newgame", { sessionId });
                if (response && response.success && response.gameStatus) {
                    syncFromBackendStatus(response.gameStatus);
                }
            } catch (error) {
                appendLog("后端新游戏重置失败，已使用前端初始状态。", "error");
            }
        }
    } else if (mode === "load") {
        enterGameFromAuth(pendingAuthData);
        await loadSavedGame();
    } else {
        enterGameFromAuth(pendingAuthData);
    }
}

function resetFrontendState() {
    currentRoomId = "campus_gate";
    currentPlayerPosition = { left: 50, top: 76 };
    gameState.inventory = [];
    gameState.treasureUnlocked = false;
    gameState.visitedRooms = new Set(["campus_gate"]);
    gameState.completion = {
        roomsExplored: 1,
        totalRooms: 7,
        itemsCollected: 0,
        totalItems: 8
    };
    lastBackendStatus = null;
    rooms.campus_gate.items = ["key"];
    rooms.main_hall.items = ["cookie"];
    rooms.forest_path.items = ["bottle", "map"];
    rooms.library.items = ["coin"];
    rooms.lab.items = ["computer", "cable"];
    rooms.locked_room.items = [];
    rooms.unlocked_treasure_room.items = ["treasure"];
    rooms.teleport_room.items = [];
    updateLockedTreasuryExit();
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

    if (normalized.startsWith("eat ")) {
        const foodName = normalized.split(" ").slice(1).join(" ");
        if (foodName === "cookie" && gameState.inventory.includes("cookie")) {
            gameState.inventory = gameState.inventory.filter((i) => i !== "cookie");
            rooms.main_hall.items = (rooms.main_hall.items || []).filter((i) => i !== "cookie");
            renderRoom();
        }
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
        movePlayerStep(direction);
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

    // 特殊处理：从 locked_room 向北移动，直接进入 unlocked_treasure_room
    if (direction === 'north' && currentRoomId === 'locked_room' && gameState.treasureUnlocked) {
        await movePlayerStep(direction);
        return;
    }

    if (direction === 'south' && currentRoomId === 'unlocked_treasure_room') {
        await movePlayerStep(direction);
        return;
    }

    if (direction) {
        const result = await movePlayerStep(direction);
        if (result.roomChanged) {
        try {
            response = await sendGameCommand(normalized);
            appendApiMessage(response);
        } catch (error) {
            appendLog("后端命令暂时不可用，已保留前端移动结果。", "error");
        }
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

    // 特殊处理：在 unlocked_treasure_room 拾取 treasure 或在 locked_room 使用 key 后，不刷新状态
    const isSpecialCommand = 
        (currentRoomId === 'unlocked_treasure_room' && normalized.startsWith('take')) ||
        (currentRoomId === 'locked_room' && normalized.startsWith('use'));

    if (!isSpecialCommand && normalized !== "look" && normalized !== "status" && normalized !== "items") {
        await refreshGameStatus();
    }
}

// 一键移动到下一个房间（用于方向按钮）
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

// WASD小步移动 - 每次只移动一小段，走到出口才进入下一房间
async function movePlayerStep(direction) {
    if (!direction || isMoving) return false;

    const room = rooms[currentRoomId];
    const nextRoomId = room.exits[direction];
    const path = room.paths && room.paths[direction];

    if (!nextRoomId || !path) {
        appendLog("这个方向没有出口。", "error");
        return false;
    }

// ========================================
// 🛤️ 十字通道核心函数
// ========================================

function snapToCrossPath(position, roomConfig, direction) {
    const centerX = roomConfig.center.x;
    const centerY = roomConfig.center.y;
    const channels = roomConfig.channels || ['vertical', 'horizontal'];
    const hasVertical = channels.includes('vertical');
    const hasHorizontal = channels.includes('horizontal');
    
    if (hasVertical && !hasHorizontal) {
        return { left: centerX, top: position.top };
    }
    if (hasHorizontal && !hasVertical) {
        return { left: position.left, top: centerY };
    }
    
    if (direction === 'north' || direction === 'south') {
        return { left: centerX, top: position.top };
    }
    if (direction === 'east' || direction === 'west') {
        return { left: position.left, top: centerY };
    }
    
    const distToVertical = Math.abs(position.left - centerX);
    const distToHorizontal = Math.abs(position.top - centerY);
    
    if (distToVertical < distToHorizontal) {
        return { left: centerX, top: position.top };
    } else {
        return { left: position.left, top: centerY };
    }
}

function calculateNextStep(current, direction, roomConfig) {
    const stepSize = GLOBAL_STEP_SIZE;
    const channels = roomConfig.channels || ['vertical', 'horizontal'];
    const hasVertical = channels.includes('vertical');
    const hasHorizontal = channels.includes('horizontal');

    if ((direction === 'north' || direction === 'south') && !hasVertical) return null;
    if ((direction === 'east' || direction === 'west') && !hasHorizontal) return null;

    let newLeft = current.left;
    let newTop = current.top;

    const centerX = roomConfig.center.x;
    const centerY = roomConfig.center.y;

    const snapped = snapToCrossPath(current, roomConfig, direction);
    newLeft = snapped.left;
    newTop = snapped.top;
    
    switch(direction) {
        case 'north': newTop -= stepSize; break;
        case 'south': newTop += stepSize; break;
        case 'west': newLeft -= stepSize; break;
        case 'east': newLeft += stepSize; break;
    }
    
    const afterMove = { left: newLeft, top: newTop };
    const snappedAfter = snapToCrossPath(afterMove, roomConfig, direction);
    newLeft = snappedAfter.left;
    newTop = snappedAfter.top;

    const vRange = roomConfig.verticalRange || { min: 5, max: 95 };
    const hRange = roomConfig.horizontalRange || { min: 5, max: 95 };

    if (hasVertical) {
        newTop = Math.max(vRange.min, Math.min(vRange.max, newTop));
    }
    if (hasHorizontal) {
        newLeft = Math.max(hRange.min, Math.min(hRange.max, newLeft));
    }
    
    return { left: newLeft, top: newTop };
}

async function enterNextRoom(direction, nextRoomId, path) {
    isMoving = true;
    setPlayerVisible(false);
    await wait(180);
    
    currentRoomId = nextRoomId;
    gameState.visitedRooms.add(currentRoomId);
    gameState.completion.roomsExplored = gameState.visitedRooms.size;
    
    const roomConfig = ROOM_CONFIGS[currentRoomId] || ROOM_CONFIGS.main_hall;
    const targetX = roomConfig.walkTo.x;
    const targetY = roomConfig.walkTo.y;
    const entryPos = roomConfig.entryPoints[direction] || { x: targetX, y: targetY };
    
    // 传送房间直接到中心，不需要动画
    if (currentRoomId === "teleport_room") {
        renderRoom({ left: targetX, top: targetY }, { instantPlayerPosition: true });
    setPlayerVisible(true);
    appendLog(`你来到了 ${rooms[currentRoomId].title}。`);
    isMoving = false;
        updateDirectionControls();
        await showTeleportDialog();
        return;
    }
    
    // 其他房间：先在入口位置出现（不要经过中心！）
    const token = $("player-token");
    token.classList.add("no-position-transition");
    currentPlayerPosition = { left: entryPos.x, top: entryPos.y };
    token.style.left = entryPos.x + "%";
    token.style.top = entryPos.y + "%";
    
    // 切换房间背景（玩家已经在入口位置了）
    renderRoom({ left: entryPos.x, top: entryPos.y }, { instantPlayerPosition: true });
    
    setPlayerFrame(direction, 0);
    await wait(50);
    setPlayerVisible(true);
    
    // 移除 no-position-transition，让后续移动有动画
    token.classList.remove("no-position-transition");
    token.offsetHeight;
    
    // 从入口慢慢走到中心
    const stepsToCenter = calculateStepsToCenter(entryPos, targetX, targetY);
    
    startPlayerStep(direction);
    
    for (let i = 0; i < stepsToCenter.length; i++) {
        const step = stepsToCenter[i];
        currentPlayerPosition = { left: step.left, top: step.top };
        token.style.left = step.left + "%";
        token.style.top = step.top + "%";
        
        await wait(GLOBAL_ANIMATION_SPEED);
    }
    
    currentPlayerPosition = { left: targetX, top: targetY };
    token.style.left = targetX + "%";
    token.style.top = targetY + "%";
    stopPlayerStep(direction);
    
    await wait(100);
    
    appendLog(`你来到了 ${rooms[currentRoomId].title}。`);
    isMoving = false;
    updateDirectionControls();
}

function calculateStepsToCenter(entryPos, targetX, targetY) {
    const steps = [];
    const stepSize = GLOBAL_STEP_SIZE;
    
    let currentX = entryPos.x;
    let currentY = entryPos.y;
    
    const stepsX = Math.floor(Math.abs(currentX - targetX) / stepSize);
    for (let i = 0; i < stepsX; i++) {
        if (currentX < targetX) {
            currentX = Math.min(currentX + stepSize, targetX);
        } else {
            currentX = Math.max(currentX - stepSize, targetX);
        }
        steps.push({ left: currentX, top: currentY });
    }

    const stepsY = Math.floor(Math.abs(currentY - targetY) / stepSize);
    for (let i = 0; i < stepsY; i++) {
        if (currentY < targetY) {
            currentY = Math.min(currentY + stepSize, targetY);
        } else {
            currentY = Math.max(currentY - stepSize, targetY);
        }
        steps.push({ left: currentX, top: currentY });
    }

    if (steps.length > 0) {
        steps[steps.length - 1] = { left: targetX, top: targetY };
    } else {
        steps.push({ left: targetX, top: targetY });
    }

    return steps;
}
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
    return new Promise((resolve) => {
        const modal = $("teleport-modal");
        const confirmBtn = $("teleport-confirm");
        const cancelBtn = $("teleport-cancel");

        function cleanup() {
            modal.classList.remove("open");
            confirmBtn.removeEventListener("click", onConfirm);
            cancelBtn.removeEventListener("click", onCancel);
        }

        function onConfirm() {
            cleanup();
            sendGameCommand("teleport").then((response) => {
                if (response && response.success) {
                    appendApiMessage(response);
                    refreshGameStatus();
                }
            }).catch(() => {
                appendLog("传送失败。", "error");
            }).finally(() => resolve());
        }

        function onCancel() {
            cleanup();
            resolve();
        }

        confirmBtn.addEventListener("click", onConfirm);
        cancelBtn.addEventListener("click", onCancel);
        modal.classList.add("open");
    });
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

// WASD键盘方向控制
document.addEventListener("keydown", (e) => {
    if (document.activeElement === $("command-input")) return;
    if (!sessionId) return;
    
    const keyMap = {
        'w': { dir: 'north', cmd: 'go north' },
        'W': { dir: 'north', cmd: 'go north' },
        'ArrowUp': { dir: 'north', cmd: 'go north' },
        's': { dir: 'south', cmd: 'go south' },
        'S': { dir: 'south', cmd: 'go south' },
        'ArrowDown': { dir: 'south', cmd: 'go south' },
        'a': { dir: 'west', cmd: 'go west' },
        'A': { dir: 'west', cmd: 'go west' },
        'ArrowLeft': { dir: 'west', cmd: 'go west' },
        'd': { dir: 'east', cmd: 'go east' },
        'D': { dir: 'east', cmd: 'go east' },
        'ArrowRight': { dir: 'east', cmd: 'go east' }
    };
    
    const action = keyMap[e.key];
    if (action) {
        e.preventDefault();
        
        // 如果已经在按住同一方向键，忽略
        if (keyHoldState.isHolding && keyHoldState.key === e.key) {
            return;
        }
        
        // 开始按住状态
        keyHoldState.key = e.key;
        keyHoldState.startTime = Date.now();
        keyHoldState.isHolding = true;
        
        // 立即执行一次小步移动
        movePlayerStep(action.dir).then(result => {
            if (result.roomChanged) {
                sendGameCommand(action.cmd).then(response => {
                    if (response && response.message) {
                        appendLog(response.message, response.success === false ? "error" : "");
                    }
                }).catch(() => {});
            }
        });
        
        keyHoldState.holdTimer = setInterval(() => {
            if (keyHoldState.isHolding && !isMoving) {
                movePlayerStep(action.dir).then(result => {
                    if (result.roomChanged) {
                        sendGameCommand(action.cmd).then(response => {
                            if (response && response.message) {
                                appendLog(response.message, response.success === false ? "error" : "");
                            }
                        }).catch(() => {});
                    }
                });
            }
        }, 400);
    }
});

// 键盘按键松开事件
document.addEventListener("keyup", (e) => {
    const keyMap = {
        'w': true, 'W': true, 'ArrowUp': true,
        's': true, 'S': true, 'ArrowDown': true,
        'a': true, 'A': true, 'ArrowLeft': true,
        'd': true, 'D': true, 'ArrowRight': true
    };
    
    if (keyMap[e.key] && keyHoldState.key === e.key) {
        keyHoldState.isHolding = false;
        keyHoldState.key = null;
        
        if (keyHoldState.holdTimer) {
            clearInterval(keyHoldState.holdTimer);
            keyHoldState.holdTimer = null;
        }
    }
});

document.querySelectorAll(".direction-pad button").forEach((button) => {
    button.addEventListener("click", () => submitCommand(button.dataset.command));
});

bindFloatingPanels();
updateLockedTreasuryExit();
renderRoom();
