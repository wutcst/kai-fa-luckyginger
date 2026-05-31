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
    cable: { label: "cable", weight: 0.1, icon: "assets/items/pickaxe.png", description: "一根USB数据线，可以连接计算机和其他设备。" },
    notebook: { label: "notebook", weight: 0.3, icon: "assets/items/scroll.png", description: "一本带锁的笔记本，或许需要宝库里的金钥匙才能打开。" },
    box: { label: "box", weight: 1.0, icon: "assets/items/green_potion.png", description: "花园中拾到的带密码锁的箱子，看起来很重要，需要4位数字密码才能打开。或许出口也在这附近。" },
    map: { label: "map", weight: 0.1, icon: "assets/items/scroll.png", description: "一张校园地图，可以帮助你探索。", mapImage: "assets/maps/campus_overview.png" },
    treasure: { label: "treasure", weight: 0.1, icon: "assets/items/gold_key.png", description: "装满金币的宝箱，里面还有一把金钥匙。" },
    final_key: { label: "final_key", weight: 0.1, icon: "assets/items/silver_key_new.png", description: "从箱子里得到的最终钥匙，似乎能打开最终的重要的门。" }
};

const directionNames = {
    north: "北",
    south: "南",
    east: "东",
    west: "西"
};

const mapRoomNodes = {
    campus_gate: { left: 50, top: 86 },
    main_hall: { left: 50, top: 60 },
    library: { left: 72, top: 48 },
    lab: { left: 50, top: 30 },
    forest_path: { left: 28, top: 62 },
    locked_room: { left: 72, top: 30 },
    unlocked_treasure_room: { left: 84, top: 18 },
    teleport_room: { left: 28, top: 38 }
};

const playerFrames = {
    north: [
        "assets/characters/adventurer_frames_v2/player_up_0.png",
        "assets/characters/adventurer_frames_v2/player_up_1.png",
        "assets/characters/adventurer_frames_v2/player_up_2.png",
        "assets/characters/adventurer_frames_v2/player_up_3.png",
        "assets/characters/adventurer_frames_v2/player_up_4.png"
    ],
    south: [
        "assets/characters/adventurer_frames_v2/player_down_0.png",
        "assets/characters/adventurer_frames_v2/player_down_1.png",
        "assets/characters/adventurer_frames_v2/player_down_2.png",
        "assets/characters/adventurer_frames_v2/player_down_3.png",
        "assets/characters/adventurer_frames_v2/player_down_4.png"
    ],
    west: [
        "assets/characters/adventurer_frames_v2/player_left_0.png",
        "assets/characters/adventurer_frames_v2/player_left_1.png",
        "assets/characters/adventurer_frames_v2/player_left_2.png",
        "assets/characters/adventurer_frames_v2/player_left_3.png",
        "assets/characters/adventurer_frames_v2/player_left_4.png"
    ],
    east: [
        "assets/characters/adventurer_frames_v2/player_right_0.png",
        "assets/characters/adventurer_frames_v2/player_right_1.png",
        "assets/characters/adventurer_frames_v2/player_right_2.png",
        "assets/characters/adventurer_frames_v2/player_right_3.png",
        "assets/characters/adventurer_frames_v2/player_right_4.png"
    ]
};

const playerFrameSequences = {
    north: [0, 1, 2, 3, 4],
    south: [0, 1, 2, 3, 4],
    west: [1, 3, 4, 2, 0],
    east: [1, 3, 4, 2, 0]
};

const playerIdleSequenceIndexes = {
    west: 3,
    east: 3
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
        start: { left: 50, top: 63 },
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
        title: "室外花园",
        description: "花园小路旁有休息区和温室，石板路通向更深处。",
        image: "assets/rooms/forest_path.png",
        exits: { east: "main_hall", north: "teleport_room" },
        items: ["box", "map"],
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
        items: ["notebook"],
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

const sceneItemPlacements = {
    campus_gate: {
        key: { left: 31, top: 71, icon: "assets/items/zuul_generated/key.png" }
    },
    main_hall: {
        cookie: { left: 33, top: 55, icon: "assets/items/zuul_generated/cookie.png" }
    },
    forest_path: {
        map: { left: 35, top: 38, icon: "assets/items/zuul_generated/map.png" },
        box: { left: 87, top: 49, icon: "assets/items/zuul_generated/box.png" }
    },
    library: {
        notebook: { left: 77, top: 66, icon: "assets/items/zuul_generated/notebook.png" }
    },
    lab: {
        computer: { left: 8, top: 42, icon: "assets/items/zuul_generated/computer.png" },
        cable: { left: 31, top: 71, icon: "assets/items/zuul_generated/cable.png" }
    },
    unlocked_treasure_room: {
        treasure: { left: 78, top: 46, icon: "assets/items/zuul_generated/treasure.png" }
    }
};

const GLOBAL_STEP_SIZE = 6;
const GLOBAL_ANIMATION_SPEED = 500;
const KEYBOARD_ACCELERATION_DELAY = 420;
const KEYBOARD_ACCELERATED_STEP_SIZE = 10;
const KEYBOARD_ACCELERATED_MOVE_TIME = 170;
const KEYBOARD_HOLD_REPEAT_TIME = 170;

const PASSWORDS = {
    computer: '1235',
    box: '1768'
};

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
        verticalRange: { min: 30, max: 95 },
        horizontalRange: { min: 10, max: 95 },
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
let currentPlayerPosition = { left: 50, top: 50 };
let currentGameRunId = createGameRunId();

function createGameRunId() {
    return `run_${Date.now()}_${Math.floor(Math.random() * 100000)}`;
}

const gameState = {
    inventory: [],
    treasureUnlocked: false,
    visitedRooms: new Set(["campus_gate"]),
    completion: {
        roomsExplored: 1,
        totalRooms: 8,
        itemsCollected: 0,
        totalItems: 9
    },
    notebookUnlocked: false,
    notebookOpened: false,
    computerStarted: false,
    computerPasswordCorrect: false,
    boxOpened: false,
    hasFinalKey: false,
    gameWon: false
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
    if (!room) return false;

    const roomConfig = ROOM_CONFIGS[currentRoomId] || ROOM_CONFIGS.main_hall;
    const channels = roomConfig.channels || ['vertical', 'horizontal'];
    if ((direction === 'north' || direction === 'south') && !channels.includes('vertical')) return false;
    if ((direction === 'east' || direction === 'west') && !channels.includes('horizontal')) return false;

    if (room.exits && room.exits[direction]) return true;

    return currentRoomId === 'forest_path' && direction === 'west';
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
    
    // 查看按钮点击处理
    $("look-room").addEventListener("click", () => {
        enterInspectMode();
    });

    // 物品详情关闭按钮
    $("item-detail-close").addEventListener("click", hideItemDetail);
    // 点击遮罩也关闭
    $("item-detail-modal").addEventListener("click", (e) => {
        if (e.target === $("item-detail-modal")) {
            hideItemDetail();
        }
    });

    $("map-dock-button").addEventListener("click", showCampusMap);
    $("campus-map-close").addEventListener("click", () => {
        $("campus-map-modal").classList.remove("open");
    });
    $("campus-map-modal").addEventListener("click", (e) => {
        if (e.target === $("campus-map-modal")) {
            $("campus-map-modal").classList.remove("open");
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

    let actionsHtml = '<button class="secondary-action" id="item-detail-close" type="button">关闭</button>';

    if (itemName === 'notebook') {
        if (gameState.notebookUnlocked) {
            actionsHtml = '<button class="primary-action" id="item-detail-action-btn" type="button">查看内容</button>' + actionsHtml;
        } else if (hasItem('treasure')) {
            actionsHtml = '<button class="primary-action" id="item-detail-action-btn" type="button">使用金钥匙打开</button>' + actionsHtml;
        } else {
            $("item-detail-description").textContent = "一本带锁的笔记本，或许需要宝库里的金钥匙才能打开。";
        }
    } else if (itemName === 'computer') {
        if (gameState.computerPasswordCorrect && hasItem('cable')) {
            actionsHtml = '<button class="primary-action" id="item-detail-action-btn" type="button">查看屏幕</button>' + actionsHtml;
        } else if (gameState.computerStarted && hasItem('cable')) {
            actionsHtml = '<button class="primary-action" id="item-detail-action-btn" type="button">输入密码</button>' + actionsHtml;
        } else if (hasItem('cable')) {
            actionsHtml = '<button class="primary-action" id="item-detail-action-btn" type="button">开启电脑</button>' + actionsHtml;
        } else {
            $("item-detail-description").textContent = "一台古老的计算机，似乎藏着什么秘密。需要数据线才能启动。";
        }
    } else if (itemName === 'box') {
        if (gameState.boxOpened) {
            $("item-detail-description").textContent = "箱子已经空了，你取出了里面的最终钥匙。";
        } else {
            actionsHtml = '<button class="primary-action" id="item-detail-action-btn" type="button">输入密码</button>' + actionsHtml;
        }
    } else if (itemName === 'treasure') {
        $("item-detail-description").textContent = "装满金币的宝箱，里面还有一把金钥匙。用金钥匙可以打开图书馆上锁的笔记本。";
    } else if (itemName === 'final_key') {
        $("item-detail-description").textContent = "从箱子里得到的最终钥匙，似乎能打开室外花园出口的大门。"
    } else if (itemName === 'map') {
        actionsHtml = '<button class="primary-action" id="item-detail-action-btn" type="button">查看校园地图</button>' + actionsHtml;
    }

    const actionsContainer = $("item-detail-actions");
    actionsContainer.innerHTML = actionsHtml;

    actionsContainer.querySelector("#item-detail-close").addEventListener("click", hideItemDetail);

    const actionBtn = actionsContainer.querySelector("#item-detail-action-btn");
    if (actionBtn) {
        actionBtn.addEventListener("click", () => {
            hideItemDetail();
            if (itemName === 'notebook') {
                if (gameState.notebookUnlocked) {
                    showContentModal('笔记本内容', 'assets/puzzles/notebook_puzzle.png', '笔记本中展示的谜题似乎暗示着实验室电脑的密码...');
                } else if (hasItem('treasure')) {
                    gameState.notebookUnlocked = true;
                    gameState.notebookOpened = true;
                    savePuzzleState();
                    appendLog("你用金钥匙打开了笔记本！");
                    showContentModal('笔记本内容', 'assets/puzzles/notebook_puzzle.png', '笔记本中展示的谜题似乎暗示着实验室电脑的密码...');
                }
            } else if (itemName === 'computer') {
                if (gameState.computerPasswordCorrect) {
                    showContentModal('电脑屏幕', 'assets/puzzles/computer_puzzle.png', '电脑屏幕上显示着一组图案，或许跟花园中箱子的密码有关...');
                } else if (gameState.computerStarted) {
                    showPasswordModal('输入电脑开机密码', (password) => {
                        if (password === PASSWORDS.computer) {
                            gameState.computerPasswordCorrect = true;
                            savePuzzleState();
                            appendLog("电脑密码正确！");
                            showContentModal('电脑屏幕', 'assets/puzzles/computer_puzzle.png', '电脑屏幕上显示着一组图案，或许跟花园中箱子的密码有关...');
                            return true;
                        } else {
                            return "密码错误，或许我该看看笔记本寻找线索...";
                        }
                    });
                } else if (hasItem('cable')) {
                    gameState.computerStarted = true;
                    savePuzzleState();
                    appendLog("你用数据线启动了电脑！屏幕上出现了密码输入框。");
                    showPasswordModal('输入电脑开机密码', (password) => {
                        if (password === PASSWORDS.computer) {
                            gameState.computerPasswordCorrect = true;
                            savePuzzleState();
                            appendLog("电脑密码正确！");
                            showContentModal('电脑屏幕', 'assets/puzzles/computer_puzzle.png', '电脑屏幕上显示着一组图案，或许跟花园中箱子的密码有关...');
                            return true;
                        } else {
                            return "密码错误，或许我该看看笔记本寻找线索...";
                        }
                    });
                }
            } else if (itemName === 'box') {
                if (!gameState.boxOpened) {
                    showPasswordModal('输入箱子密码', (password) => {
                        if (password === PASSWORDS.box) {
                            gameState.boxOpened = true;
                            gameState.hasFinalKey = true;
                            savePuzzleState();
                            addFinalKeyToInventory();
                            appendLog("箱子打开了！你获得了最终钥匙！");
                            showContentModal('箱子打开了！', '', '你在箱子中发现了一把闪烁着奇异光芒的钥匙——最终钥匙！它似乎能打开最终的重要大门...');
                            return true;
                        } else {
                            return "密码错误，我该认真查看电脑屏幕来寻找答案...";
                        }
                    });
                }
            } else if (itemName === 'map') {
                showCampusMap();
            }
        });
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
                if (itemName === "map") {
                    showCampusMap();
                } else if (itemName) {
                    showItemDetail(itemName);
                }
            });
        });
    }

    const playerInfo = lastBackendStatus && lastBackendStatus.player ? lastBackendStatus.player : {};
    const totalWeight = playerInfo.totalWeight != null ? playerInfo.totalWeight : inventoryWeight();
    const maxWeight = playerInfo.maxWeight != null ? playerInfo.maxWeight : 10;
    $("weight-info").textContent = `负重 ${formatWeight(totalWeight)}/${formatWeight(maxWeight)}kg`;
}

function renderMapDock() {
    const button = $("map-dock-button");
    if (!button) return;
    button.classList.toggle("visible", hasItem("map"));
}

function showCampusMap() {
    const modal = $("campus-map-modal");
    const room = rooms[currentRoomId] || rooms.campus_gate;
    $("campus-map-current").textContent = `当前位置：${room.title}`;

    const markers = $("campus-map-markers");
    markers.innerHTML = Object.keys(mapRoomNodes).map((roomId) => {
        const node = mapRoomNodes[roomId];
        const mapRoom = rooms[roomId];
        const isCurrent = roomId === currentRoomId;
        const isVisited = gameState.visitedRooms.has(roomId);
        const stateClass = isCurrent ? "current" : (isVisited ? "visited" : "unvisited");
        return `
            <button class="campus-map-marker ${stateClass}" type="button"
                style="left: ${node.left}%; top: ${node.top}%"
                title="${mapRoom ? mapRoom.title : roomId}">
                <span>${mapRoom ? mapRoom.title : roomId}</span>
            </button>`;
    }).join("");

    const exits = Object.keys(room.exits || {}).filter((dir) => room.exits[dir] && canMoveToDirection(dir));
    $("campus-map-exits").innerHTML = exits.length
        ? `<strong>可前往方向</strong>${exits.map((dir) => `<span>${directionNames[dir] || dir}</span>`).join("")}`
        : "<strong>可前往方向</strong><span>暂无</span>";

    modal.classList.add("open");
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

// 是否处于查看模式
let isInspectMode = false;

// 进入查看模式：展开背包 + 显示日志
function enterInspectMode() {
    isInspectMode = true;
    
    // 展开左侧背包面板
    $("inventory-dock").classList.add("open");
    
    // 切换到日志面板（同时激活日志按钮）
    document.querySelectorAll(".round-tool").forEach((tool) => {
        tool.classList.remove("active");
        // 找到日志按钮并激活
        if (tool.dataset.panel === "log-floating") {
            tool.classList.add("active");
        }
    });
    document.querySelectorAll(".floating-panel").forEach((panel) => {
        panel.classList.remove("active");
    });
    $("log-floating").classList.add("active");
    
    // 执行look命令显示房间描述
    setPlayerPosition(rooms[currentRoomId].start);
    appendLog(rooms[currentRoomId].description);
}

// 退出查看模式：恢复原样
function exitInspectMode() {
    if (isInspectMode) {
        isInspectMode = false;
        // 关闭背包面板
        $("inventory-dock").classList.remove("open");
        
        // 恢复房间按钮和房间面板的显示
        document.querySelectorAll(".round-tool").forEach((tool) => {
            tool.classList.remove("active");
            // 找到房间按钮并激活
            if (tool.dataset.panel === "room-floating") {
                tool.classList.add("active");
            }
        });
        document.querySelectorAll(".floating-panel").forEach((panel) => {
            panel.classList.remove("active");
        });
        $("room-floating").classList.add("active");
    }
}

function showRoomStatus() {
    const room = rooms[currentRoomId];
    
    // 显示当前位置
    $("room-status-location").textContent = `当前位置：${room.title || currentRoomId}`;
    
    // 显示可走的方向
    const exitsContainer = $("room-status-exits");
    exitsContainer.innerHTML = "";
    
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
    renderMapDock();
    renderQuickActions();
    renderProgress();
}

function syncFromBackendStatus(status, options = {}) {
    if (!status || status.error) {
        if (status && status.error) appendLog(status.error, "error");
        return;
    }

    lastBackendStatus = status;

    if (status.treasureUnlocked !== undefined) {
        gameState.treasureUnlocked = status.treasureUnlocked;
    }

    if (status.completion && status.completion.cookieEaten) {
        rooms.main_hall.items = (rooms.main_hall.items || []).filter((i) => i !== "cookie");
    }

    if (status.completion) {
        gameState.completion = {
            roomsExplored: gameState.visitedRooms.size,
            totalRooms: Object.keys(rooms).length,
            itemsCollected: status.completion.itemsCollected || gameState.completion.itemsCollected,
            totalItems: status.completion.totalItems || gameState.completion.totalItems
        };
    }

    if (!options.allowRoomChange) {
        gameState.visitedRooms.add(currentRoomId);
        updateLockedTreasuryExit();
        updateHud();
        return;
    }

    const roomInfo = status.currentRoom || {};
    
    let visualRoomId;
    if (currentRoomId === 'unlocked_treasure_room') {
        visualRoomId = 'unlocked_treasure_room';
    } else {
        visualRoomId = roomInfo.roomId || findVisualRoomId(roomInfo);
    }
    
    const visualRoom = rooms[visualRoomId];

    if (visualRoom) {
        if (visualRoomId !== currentRoomId) {
            currentRoomId = visualRoomId;
            gameState.visitedRooms.add(currentRoomId);
        }
        visualRoom.description = roomInfo.longDescription || roomInfo.shortDescription || visualRoom.description;
        if (visualRoomId !== 'locked_room' && visualRoomId !== 'unlocked_treasure_room') {
            visualRoom.items = itemNames(roomInfo.items);
        }
    }

    const playerInfo = status.player || {};
    const backendInventory = itemNames(playerInfo.inventory);
    const frontendOnlyItems = gameState.inventory.filter(item => item === 'final_key' || item === 'treasure');
    gameState.inventory = [...new Set([...backendInventory, ...frontendOnlyItems])];

    gameState.visitedRooms.add(currentRoomId);
    updateLockedTreasuryExit();
    renderRoom();
}

async function refreshGameStatus(options = {}) {
    if (!sessionId) return;

    try {
        const status = await getApi("status", { sessionId });
        syncFromBackendStatus(status, options);
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
        syncFromBackendStatus(data.gameStatus, { allowRoomChange: true });
    } else {
        refreshGameStatus({ allowRoomChange: true });
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
        const username = pendingAuthData.username || $("login-username").value.trim() || $("register-username").value.trim();
        resetFrontendState(username);
        sessionId = pendingAuthData.sessionId || null;
        currentUsername = username;
        updateLockedTreasuryExit();
        showView("game");
        if (sessionId) {
            try {
                const response = await callApi("newgame", { sessionId });
                if (response && response.success && response.gameStatus) {
                    syncFromBackendStatus(response.gameStatus, { allowRoomChange: true });
                }
            } catch (error) {
                appendLog("后端新游戏重置失败，已使用前端初始状态。", "error");
            }
        }
        currentRoomId = "campus_gate";
        const gateConfig = ROOM_CONFIGS.campus_gate || ROOM_CONFIGS.main_hall;
        currentPlayerPosition = { left: gateConfig.center.x, top: gateConfig.center.y };
        renderRoom({ left: gateConfig.center.x, top: gateConfig.center.y }, { instantPlayerPosition: true });
        appendLog("新游戏已开始！");
    } else if (mode === "load") {
        enterGameFromAuth(pendingAuthData);
        await loadSavedGame();
    } else {
        sessionId = pendingAuthData.sessionId || null;
        currentUsername = pendingAuthData.username || $("login-username").value.trim() || $("register-username").value.trim();
        showView("game");
        
        const autoKey = 'zuul_auto_' + currentUsername;
        const loaded = loadFullState(autoKey);
        if (loaded) {
            renderRoom({ left: currentPlayerPosition.left, top: currentPlayerPosition.top }, { instantPlayerPosition: true });
            appendLog("已恢复上次进度。");
        } else {
            if (pendingAuthData.gameStatus) {
                syncFromBackendStatus(pendingAuthData.gameStatus, { allowRoomChange: true });
            } else if (sessionId) {
                await refreshGameStatus({ allowRoomChange: true });
            }
            const roomConfig = ROOM_CONFIGS[currentRoomId] || ROOM_CONFIGS.main_hall;
            currentPlayerPosition = { left: roomConfig.center.x, top: roomConfig.center.y };
            renderRoom({ left: roomConfig.center.x, top: roomConfig.center.y }, { instantPlayerPosition: true });
            appendLog(`欢迎进入游戏，${currentUsername || "player"}。`);
        }
        if (pendingAuthData.message) appendLog(pendingAuthData.message);
    }
}

function resetFrontendState(username) {
    currentRoomId = "campus_gate";
    currentGameRunId = createGameRunId();
    currentPlayerPosition = { left: 50, top: 76 };
    gameState.inventory = [];
    gameState.treasureUnlocked = false;
    gameState.visitedRooms = new Set(["campus_gate"]);
    gameState.completion = {
        roomsExplored: 1,
        totalRooms: 8,
        itemsCollected: 0,
        totalItems: 9
    };
    gameState.notebookUnlocked = false;
    gameState.notebookOpened = false;
    gameState.computerStarted = false;
    gameState.computerPasswordCorrect = false;
    gameState.boxOpened = false;
    gameState.hasFinalKey = false;
    gameState.gameWon = false;
    lastBackendStatus = null;
    rooms.campus_gate.items = ["key"];
    rooms.main_hall.items = ["cookie"];
    rooms.forest_path.items = ["box", "map"];
    rooms.library.items = ["notebook"];
    rooms.lab.items = ["computer", "cable"];
    rooms.locked_room.items = [];
    rooms.unlocked_treasure_room.items = ["treasure"];
    rooms.teleport_room.items = [];
    updateLockedTreasuryExit();
    try { localStorage.removeItem('zuul_puzzle_state'); } catch (e) {}
    if (username) {
        try { localStorage.removeItem('zuul_save_' + username); } catch (e) {}
        try { localStorage.removeItem('zuul_auto_' + username); } catch (e) {}
    }
}

function closeGameMenu() {
    $("game-menu-popover").classList.remove("open");
}

async function saveCurrentGame() {
    try {
        if (sessionId) {
            try {
                const response = await callApi("save", { sessionId });
                appendApiMessage(response);
            } catch (error) {
                appendLog("服务器保存暂时不可用，已保存到本地存档槽。", "error");
            }
        }
        const slot = saveManualSlot();
        appendLog(`游戏进度已保存：${slot.summary.roomTitle}。`);
        showSaveSlotsModal("save", slot.id);
    } catch (error) {
        appendLog("保存失败，请确认浏览器存储是否可用。", "error");
    }
}

async function loadSavedGame() {
    const slots = getManualSaveSlots();
    if (slots.length > 0) {
        showSaveSlotsModal("load");
        return;
    }

    if (!sessionId) {
        appendLog("当前没有可读取的本地存档。", "error");
        showSaveSlotsModal("load");
        return;
    }

    try {
        const response = await callApi("load", { sessionId });
        appendApiMessage(response);
        if (response && response.success) {
            const saveKey = 'zuul_save_' + currentUsername;
            const loaded = loadFullState(saveKey);
            if (!loaded) {
                if (response.gameStatus) {
                    syncFromBackendStatus(response.gameStatus, { allowRoomChange: true });
                } else {
                    await refreshGameStatus({ allowRoomChange: true });
                }
                loadPuzzleState();
                if (gameState.hasFinalKey && !gameState.inventory.includes('final_key')) {
                    gameState.inventory.push('final_key');
                }
                if (gameState.treasureUnlocked) {
                    updateLockedTreasuryExit();
                }
                if (gameState.boxOpened) {
                    rooms.forest_path.items = (rooms.forest_path.items || []).filter(i => i !== 'box');
                }
                if (gameState.notebookUnlocked) {
                    rooms.library.items = (rooms.library.items || []).filter(i => i !== 'notebook');
                }
            } else {
                if (response.gameStatus) {
                    lastBackendStatus = response.gameStatus;
                }
            }
            const roomConfig = ROOM_CONFIGS[currentRoomId] || ROOM_CONFIGS.main_hall;
            currentPlayerPosition = { left: roomConfig.center.x, top: roomConfig.center.y };
            renderRoom({ left: roomConfig.center.x, top: roomConfig.center.y }, { instantPlayerPosition: true });
            renderInventory();
            appendLog("存档已读取！");
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
    
    const roomConfig = ROOM_CONFIGS[currentRoomId] || ROOM_CONFIGS.main_hall;
    const snappedPosition = snapToCrossPath(position, roomConfig);
    
    if (instant) {
        token.classList.add("no-position-transition");
    }

    currentPlayerPosition = { left: snappedPosition.left, top: snappedPosition.top };
    token.style.left = snappedPosition.left + "%";
    token.style.top = snappedPosition.top + "%";

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

    if (entryPosition) {
        setPlayerPosition(entryPosition, { instant: options.instantPlayerPosition });
    }
    renderSceneItems();
    updateHud();
    updateDirectionControls();
}

function renderSceneItems() {
    const layer = $("scene-item-layer");
    if (!layer) return;

    const room = rooms[currentRoomId];
    const placements = sceneItemPlacements[currentRoomId] || {};
    const items = (room.items || []).filter((itemName) => placements[itemName]);

    layer.innerHTML = items.map((itemName) => {
        const meta = itemMeta[itemName] || { label: itemName, icon: "assets/items/scroll.png" };
        const placement = placements[itemName];
        const icon = placement.icon || meta.icon;
        return `
            <button class="scene-item-bubble ${placement.visual ? `scene-item-bubble--${placement.visual}` : ""}" type="button" data-item="${itemName}"
                style="left: ${placement.left}%; top: ${placement.top}%"
                aria-label="拾取 ${meta.label}" title="拾取 ${meta.label}">
                <img src="${icon}" alt="">
                <span>${meta.label}</span>
            </button>`;
    }).join("");

    layer.querySelectorAll(".scene-item-bubble").forEach((button) => {
        button.addEventListener("click", () => pickSceneItem(button.dataset.item));
    });
}

function getDirection(command) {
    if (command.includes("north")) return "north";
    if (command.includes("south")) return "south";
    if (command.includes("west")) return "west";
    if (command.includes("east")) return "east";
    return null;
}

function takeItem(itemName, options = {}) {
    if (isMoving && !options.allowWhileMoving) {
        appendLog("请等走到位置后再拾取物品。", "error");
        return false;
    }

    const room = rooms[currentRoomId];
    const items = room.items || [];

    if (!items.includes(itemName)) {
        appendLog(`这里没有 ${itemName}。`, "error");
        return false;
    }

    room.items = items.filter((item) => item !== itemName);
    gameState.inventory.push(itemName);
    gameState.completion.itemsCollected = Math.max(gameState.completion.itemsCollected, gameState.inventory.length);
    renderRoom();
    appendLog(`你拾取了 ${itemName}。`);
    return true;
}

function pickSceneItem(itemName) {
    const meta = itemMeta[itemName] || { label: itemName, icon: "assets/items/scroll.png" };
    if (takeItem(itemName, { allowWhileMoving: true })) {
        showContentModal("拾取成功", "", `你拾取了 ${meta.label}，已放入背包。`);
    }
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

    const isSpecialCommand = 
        (currentRoomId === 'unlocked_treasure_room' && normalized.startsWith('take')) ||
        (currentRoomId === 'locked_room' && normalized.startsWith('use'));

    const isItemCommand = normalized.startsWith("take") || normalized.startsWith("get") || 
                           normalized.startsWith("drop") || normalized.startsWith("use") || 
                           normalized.startsWith("eat");

    if (!isItemCommand && !isSpecialCommand && normalized !== "look" && normalized !== "status" && normalized !== "items") {
        await refreshGameStatus();
    }
}

// WASD小步移动 - 每次只移动一小段，走到出口才进入下一房间
async function movePlayerStep(direction, options = {}) {
    if (!direction || isMoving) return { moved: false, roomChanged: false };

    const room = rooms[currentRoomId];
    const roomConfig = ROOM_CONFIGS[currentRoomId] || ROOM_CONFIGS.main_hall;

    const nextPosition = calculateNextStep(currentPlayerPosition, direction, roomConfig, options.stepSize);
    if (!nextPosition) return { moved: false, roomChanged: false };

    if (currentRoomId === 'forest_path' && direction === 'west') {
        const hRange = roomConfig.horizontalRange || { min: 5, max: 95 };
        if (nextPosition.left <= hRange.min + 2) {
            showExitDialog();
            return { moved: false, roomChanged: false };
        }
    }

    const nextRoomId = room.exits[direction];
    const path = room.paths && room.paths[direction];

    if (nextRoomId && path && isAtExit(currentPlayerPosition, path.exit)) {
        await enterNextRoom(direction, nextRoomId, path);
        return { moved: true, roomChanged: true };
    }

    isMoving = true;
    startPlayerStep(direction);
    $("player-token").style.setProperty("--player-move-duration", `${options.moveTime || 300}ms`);
    setPlayerPosition(nextPosition);

    await wait(options.moveTime || 300);
    stopPlayerStep(direction);
    isMoving = false;

    if (nextRoomId && path && isAtExit(nextPosition, path.exit)) {
        await enterNextRoom(direction, nextRoomId, path);
        return { moved: true, roomChanged: true };
    }

    return { moved: true, roomChanged: false };
}

// 检查是否到达出口位置
function isAtExit(position, exit) {
    if (!exit) return false;
    const threshold = 6; // 允许的误差范围，稍微增大避免太敏感
    return Math.abs(position.left - exit.left) < threshold && 
           Math.abs(position.top - exit.top) < threshold;
}

// 检查位置是否在十字通道上
function isOnCrossPath(position) {
    const centerX = 50;
    const centerY = 50;
    const tolerance = 12; // 十字通道的宽度容忍度
    
    // 检查是否在垂直通道上（x约等于50）
    const onVertical = Math.abs(position.left - centerX) < tolerance;
    // 检查是否在水平通道上（y约等于50）
    const onHorizontal = Math.abs(position.top - centerY) < tolerance;
    
    return onVertical || onHorizontal;
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

function calculateNextStep(current, direction, roomConfig, customStepSize) {
    const stepSize = customStepSize || GLOBAL_STEP_SIZE;
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

function getKeyboardMoveOptions() {
    const heldTime = Date.now() - keyHoldState.startTime;
    if (keyHoldState.isHolding && heldTime >= KEYBOARD_ACCELERATION_DELAY) {
        return {
            stepSize: KEYBOARD_ACCELERATED_STEP_SIZE,
            moveTime: KEYBOARD_ACCELERATED_MOVE_TIME
        };
    }
    return {};
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
        currentPlayerPosition = { left: targetX, top: targetY };
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

function getEntryPositionForDirection(direction, path) {
    const roomConfig = ROOM_CONFIGS[currentRoomId] || ROOM_CONFIGS.main_hall;
    return roomConfig.entryPoints[direction] || { x: 50, y: 50 };
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

        async function onConfirm() {
            cleanup();
            try {
                const response = await sendGameCommand("teleport");
                if (response && response.success === false) {
                    appendApiMessage(response);
                    resolve();
                    return;
                }

                const validTargets = ['campus_gate', 'main_hall', 'forest_path', 'library', 'lab'];
                let targetRoomId = findVisualRoomId(response && response.currentRoom ? response.currentRoom : {});
                if (!validTargets.includes(targetRoomId)) {
                    targetRoomId = findVisualRoomId({ longDescription: response && response.message ? response.message : "" });
                }
                if (!validTargets.includes(targetRoomId)) {
                    targetRoomId = validTargets[Math.floor(Math.random() * validTargets.length)];
                }

                const targetConfig = ROOM_CONFIGS[targetRoomId] || ROOM_CONFIGS.main_hall;
                isMoving = true;
                setPlayerVisible(false);
                await wait(180);

                currentRoomId = targetRoomId;
                gameState.visitedRooms.add(currentRoomId);
                gameState.completion.roomsExplored = gameState.visitedRooms.size;
                const cx = targetConfig.center.x;
                const cy = targetConfig.center.y;
                currentPlayerPosition = { left: cx, top: cy };
                renderRoom({ left: cx, top: cy }, { instantPlayerPosition: true });
                setPlayerVisible(true);
                isMoving = false;
                updateDirectionControls();

                const roomTitle = rooms[targetRoomId].title;
                appendLog(`传送到了 ${roomTitle}！`);
                showContentModal("传送完成", "", `你已被随机传送到：${roomTitle}`);
            } catch (error) {
                appendLog("传送失败。", "error");
            } finally {
                resolve();
            }
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
    const sequence = playerFrameSequences[direction] || playerFrameSequences.south;
    const frameNumber = sequence[frameIndex % sequence.length];
    $("player-token").style.backgroundImage = `url("${frames[frameNumber % frames.length]}")`;
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
    setPlayerFrame(direction || "south", playerIdleSequenceIndexes[direction] || 0);
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
    if (currentUsername && currentRoomId) {
        saveFullState('zuul_auto_' + currentUsername);
    }
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
$("save-slots-close").addEventListener("click", () => {
    $("save-slots-modal").classList.remove("open");
});
$("save-slots-modal").addEventListener("click", (event) => {
    if (event.target === $("save-slots-modal")) {
        $("save-slots-modal").classList.remove("open");
    }
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
        
        // 退出查看模式
        exitInspectMode();
        
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
                // 退出查看模式
                exitInspectMode();
                
                movePlayerStep(action.dir, getKeyboardMoveOptions()).then(result => {
                    if (result.roomChanged) {
                        sendGameCommand(action.cmd).then(response => {
                            if (response && response.message) {
                                appendLog(response.message, response.success === false ? "error" : "");
                            }
                        }).catch(() => {});
                    }
                });
            }
        }, KEYBOARD_HOLD_REPEAT_TIME);
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
    button.addEventListener("click", () => {
        const direction = getDirection(button.dataset.command || "");
        if (direction) {
            // 退出查看模式
            exitInspectMode();
            
            movePlayerStep(direction).then(result => {
                if (result.roomChanged) {
                    sendGameCommand(button.dataset.command).then(response => {
                        if (response && response.message) {
                            appendLog(response.message, response.success === false ? "error" : "");
                        }
                    }).catch(() => {});
                }
            });
        }
    });
});

bindFloatingPanels();
updateLockedTreasuryExit();
loadPuzzleState();
renderRoom();

function showPasswordModal(title, callback) {
    const modal = $("password-modal");
    const titleEl = modal.querySelector("h3");
    const input = $("password-input");
    const confirmBtn = $("password-confirm");
    const cancelBtn = $("password-cancel");
    const errorMsg = $("password-error");

    titleEl.textContent = title;
    input.value = "";
    errorMsg.textContent = "";
    modal.classList.add("open");

    function cleanup() {
        modal.classList.remove("open");
        confirmBtn.removeEventListener("click", onConfirm);
        cancelBtn.removeEventListener("click", onCancel);
        input.removeEventListener("keydown", onKeydown);
    }

    function onConfirm() {
        const password = input.value.trim();
        if (password.length !== 4) {
            errorMsg.textContent = "请输入4位数字密码";
            return;
        }
        const result = callback(password);
        if (result === true) {
            cleanup();
        } else {
            errorMsg.textContent = result || "密码错误";
            input.value = "";
            input.focus();
        }
    }

    function onCancel() {
        cleanup();
    }

    function onKeydown(e) {
        if (e.key === "Enter") {
            e.preventDefault();
            onConfirm();
        }
    }

    confirmBtn.addEventListener("click", onConfirm);
    cancelBtn.addEventListener("click", onCancel);
    input.addEventListener("keydown", onKeydown);
    setTimeout(() => input.focus(), 100);
}

function showContentModal(title, imageUrl, text) {
    const modal = $("content-modal");
    const titleEl = modal.querySelector("h3");
    const image = $("content-image");
    const textEl = $("content-text");
    const closeBtn = $("content-close");

    titleEl.textContent = title;
    image.src = imageUrl;
    image.style.display = imageUrl ? "block" : "none";
    textEl.textContent = text || "";
    modal.classList.add("open");

    function cleanup() {
        modal.classList.remove("open");
        closeBtn.removeEventListener("click", cleanup);
    }

    closeBtn.addEventListener("click", cleanup);
}

function showExitDialog() {
    const modal = $("exit-modal");
    const titleEl = modal.querySelector("h3");
    const descEl = $("exit-description");
    const openBtn = $("exit-open");
    const backBtn = $("exit-back");

    if (gameState.hasFinalKey) {
        titleEl.textContent = "出口";
        descEl.textContent = "你已到达出口，是否使用最终钥匙开门？";
        openBtn.textContent = "开门";
        openBtn.style.display = "inline-block";
    } else {
        titleEl.textContent = "锁住的门";
        descEl.textContent = "你已到达出口，但门锁住了，需要一把特殊的钥匙。";
        openBtn.style.display = "none";
    }

    modal.classList.add("open");

    function cleanup() {
        modal.classList.remove("open");
        openBtn.removeEventListener("click", onOpen);
        backBtn.removeEventListener("click", onBack);
    }

    function onOpen() {
        cleanup();
        if (gameState.hasFinalKey) {
            gameState.gameWon = true;
            savePuzzleState();
            showWinModal();
        }
    }

    function onBack() {
        cleanup();
    }

    openBtn.addEventListener("click", onOpen);
    backBtn.addEventListener("click", onBack);
}

function showWinModal() {
    $("win-modal").classList.add("open");
    $("win-close").onclick = () => {
        $("win-modal").classList.remove("open");
        showView("menu");
    };
}

function addFinalKeyToInventory() {
    if (!gameState.inventory.includes('final_key')) {
        gameState.inventory.push('final_key');
        renderInventory();
    }
}

function savePuzzleState() {
    try {
        const state = {
            notebookUnlocked: gameState.notebookUnlocked,
            notebookOpened: gameState.notebookOpened,
            computerStarted: gameState.computerStarted,
            computerPasswordCorrect: gameState.computerPasswordCorrect,
            boxOpened: gameState.boxOpened,
            hasFinalKey: gameState.hasFinalKey,
            gameWon: gameState.gameWon
        };
        localStorage.setItem('zuul_puzzle_state', JSON.stringify(state));
    } catch (e) {}
    if (currentUsername) {
        saveFullState('zuul_auto_' + currentUsername);
    }
}

function loadPuzzleState() {
    try {
        const saved = localStorage.getItem('zuul_puzzle_state');
        if (saved) {
            const state = JSON.parse(saved);
            if (state.notebookUnlocked !== undefined) gameState.notebookUnlocked = state.notebookUnlocked;
            if (state.notebookOpened !== undefined) gameState.notebookOpened = state.notebookOpened;
            if (state.computerStarted !== undefined) gameState.computerStarted = state.computerStarted;
            if (state.computerPasswordCorrect !== undefined) gameState.computerPasswordCorrect = state.computerPasswordCorrect;
            if (state.boxOpened !== undefined) gameState.boxOpened = state.boxOpened;
            if (state.hasFinalKey !== undefined) gameState.hasFinalKey = state.hasFinalKey;
            if (state.gameWon !== undefined) gameState.gameWon = state.gameWon;
            if (gameState.hasFinalKey && !gameState.inventory.includes('final_key')) {
                gameState.inventory.push('final_key');
            }
        }
    } catch (e) {}
}

function buildFullState() {
    if (!currentGameRunId) {
        currentGameRunId = createGameRunId();
    }
    const state = {
        gameRunId: currentGameRunId,
        currentRoomId: currentRoomId,
        currentPlayerPosition: { ...currentPlayerPosition },
        inventory: [...gameState.inventory],
        visitedRooms: [...gameState.visitedRooms],
        treasureUnlocked: gameState.treasureUnlocked,
        notebookUnlocked: gameState.notebookUnlocked,
        notebookOpened: gameState.notebookOpened,
        computerStarted: gameState.computerStarted,
        computerPasswordCorrect: gameState.computerPasswordCorrect,
        boxOpened: gameState.boxOpened,
        hasFinalKey: gameState.hasFinalKey,
        gameWon: gameState.gameWon,
        roomItems: {}
    };
    for (const roomId in rooms) {
        state.roomItems[roomId] = [...(rooms[roomId].items || [])];
    }
    return state;
}

function saveFullState(storageKey) {
    try {
        localStorage.setItem(storageKey, JSON.stringify(buildFullState()));
    } catch (e) {}
}

function loadFullState(storageKey) {
    try {
        const saved = localStorage.getItem(storageKey);
        if (!saved) return false;
        const state = JSON.parse(saved);
        applyFullState(state);
        return true;
    } catch (e) {
        return false;
    }
}

function applyFullState(state) {
    currentGameRunId = state.gameRunId || currentGameRunId || createGameRunId();
    currentRoomId = state.currentRoomId || 'campus_gate';
    currentPlayerPosition = state.currentPlayerPosition || { left: 50, top: 50 };
    gameState.inventory = state.inventory || [];
    gameState.visitedRooms = new Set(state.visitedRooms || ['campus_gate']);
    gameState.treasureUnlocked = !!state.treasureUnlocked;
    gameState.notebookUnlocked = !!state.notebookUnlocked;
    gameState.notebookOpened = !!state.notebookOpened;
    gameState.computerStarted = !!state.computerStarted;
    gameState.computerPasswordCorrect = !!state.computerPasswordCorrect;
    gameState.boxOpened = !!state.boxOpened;
    gameState.hasFinalKey = !!state.hasFinalKey;
    gameState.gameWon = !!state.gameWon;

    if (state.roomItems) {
        for (const roomId in state.roomItems) {
            if (rooms[roomId]) {
                rooms[roomId].items = state.roomItems[roomId];
            }
        }
    }

    gameState.completion = {
        roomsExplored: gameState.visitedRooms.size,
        totalRooms: Object.keys(rooms).length,
        itemsCollected: gameState.inventory.length,
        totalItems: 9
    };

    updateLockedTreasuryExit();
}

function getManualSaveKey() {
    return 'zuul_saves_' + (currentUsername || 'guest');
}

function getManualSaveSlots() {
    try {
        const saved = localStorage.getItem(getManualSaveKey());
        if (!saved) return [];
        const slots = JSON.parse(saved);
        return Array.isArray(slots) ? slots.filter(slot => slot && slot.state) : [];
    } catch (e) {
        return [];
    }
}

function setManualSaveSlots(slots) {
    localStorage.setItem(getManualSaveKey(), JSON.stringify(slots.slice(0, 3)));
}

function buildSaveSummary(state) {
    const totalRooms = Object.keys(rooms).length;
    const roomCount = Array.isArray(state.visitedRooms) ? state.visitedRooms.length : 1;
    const itemCount = Array.isArray(state.inventory) ? state.inventory.length : 0;
    const roomProgress = totalRooms ? roomCount / totalRooms : 0;
    const itemProgress = gameState.completion.totalItems ? itemCount / gameState.completion.totalItems : 0;
    const percent = Math.max(4, Math.min(100, Math.round((roomProgress * 0.55 + itemProgress * 0.45) * 100)));
    const room = rooms[state.currentRoomId];
    const roomTitle = room ? room.title : "未知房间";
    return {
        roomTitle,
        roomImage: room ? room.image : "",
        roomCount,
        totalRooms,
        itemCount,
        totalItems: gameState.completion.totalItems,
        percent,
        text: `${roomTitle} · 房间 ${roomCount}/${totalRooms} · 物品 ${itemCount}/${gameState.completion.totalItems}`
    };
}

function saveManualSlot() {
    if (!currentGameRunId) {
        currentGameRunId = createGameRunId();
    }
    const state = buildFullState();
    const slots = getManualSaveSlots();
    const existingIndex = slots.findIndex(slot => {
        const slotRunId = slot.gameRunId || (slot.state && slot.state.gameRunId);
        return slotRunId === currentGameRunId;
    });
    const slot = {
        id: existingIndex >= 0 ? slots[existingIndex].id : currentGameRunId,
        gameRunId: currentGameRunId,
        savedAt: new Date().toISOString(),
        summary: buildSaveSummary(state),
        state
    };
    const nextSlots = existingIndex >= 0
        ? [slot, ...slots.filter((_, index) => index !== existingIndex)]
        : [slot, ...slots];
    setManualSaveSlots(nextSlots.slice(0, 3));
    return slot;
}

function formatSaveTime(isoText) {
    try {
        return new Date(isoText).toLocaleString('zh-CN', {
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        });
    } catch (e) {
        return isoText || "";
    }
}

function showSaveSlotsModal(mode, highlightedId) {
    const modal = $("save-slots-modal");
    const title = $("save-slots-title");
    const hint = $("save-slots-hint");
    const list = $("save-slots-list");
    const slots = getManualSaveSlots();

    title.textContent = mode === "save" ? "游戏进度已保存" : "读取存档";
    hint.textContent = mode === "save"
        ? "最多保留 3 条手动存档，新的存档会自动排在最上方。"
        : "选择一条存档继续游戏。";

    if (!slots.length) {
        list.innerHTML = '<div class="save-slot save-slot--empty">暂无可读取的存档</div>';
    } else {
        list.innerHTML = slots.map((slot, index) => {
            const summary = slot.summary || buildSaveSummary(slot.state || {});
            const active = slot.id === highlightedId ? " · 刚刚保存" : "";
            const roomImage = summary.roomImage || (slot.state && rooms[slot.state.currentRoomId] ? rooms[slot.state.currentRoomId].image : "");
            const actionText = mode === "save" ? "已保存" : "继续此存档";
            return `
                <button class="save-slot" type="button" data-save-id="${slot.id}" ${mode === "save" ? "disabled" : ""}>
                    <div class="save-slot-thumb" style="background-image: url('${roomImage}')"></div>
                    <div class="save-slot-content">
                        <div class="save-slot-header">
                            <span class="save-slot-title">存档 ${index + 1}${active}</span>
                            <span class="save-slot-time">${formatSaveTime(slot.savedAt)}</span>
                        </div>
                        <p class="save-slot-room">${summary.roomTitle}</p>
                        <div class="save-slot-stats">
                            <span>已探索 ${summary.roomCount}/${summary.totalRooms}</span>
                            <span>已收集 ${summary.itemCount}/${summary.totalItems}</span>
                        </div>
                        <div class="save-slot-footer">
                            <div class="save-slot-progress" aria-label="游戏进度 ${summary.percent}%">
                                <span style="width: ${summary.percent}%"></span>
                            </div>
                            <span class="save-slot-action">${actionText}</span>
                        </div>
                    </div>
                </button>`;
        }).join("");
    }

    if (mode === "load") {
        list.querySelectorAll(".save-slot[data-save-id]").forEach((button) => {
            button.addEventListener("click", () => {
                const slot = getManualSaveSlots().find(item => item.id === button.dataset.saveId);
                if (!slot) return;
                if (!slot.state.gameRunId) {
                    slot.state.gameRunId = slot.gameRunId || slot.id || createGameRunId();
                }
                applyFullState(slot.state);
                renderRoom(
                    { left: currentPlayerPosition.left, top: currentPlayerPosition.top },
                    { instantPlayerPosition: true }
                );
                modal.classList.remove("open");
                appendLog(`已读取存档：${slot.summary ? slot.summary.roomTitle : rooms[currentRoomId].title}。`);
                showView("game");
            });
        });
    }

    modal.classList.add("open");
}

window.addEventListener('beforeunload', () => {
    if (currentUsername && currentRoomId) {
        saveFullState('zuul_auto_' + currentUsername);
    }
});
