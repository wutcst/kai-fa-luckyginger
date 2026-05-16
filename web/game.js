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

let playerAnimationTimer = null;
let currentRoomId = "campus_gate";
let isMoving = false;
let sessionId = null;
let currentUsername = null;
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

function enterGameFromAuth(data) {
    sessionId = data.sessionId || null;
    currentUsername = data.username || $("login-username").value.trim() || $("register-username").value.trim();
    updateLockedTreasuryExit();
    showView("game");
    renderRoom();
    appendLog("Signed in as " + (currentUsername || "player") + ".");
    if (data.message) {
        appendLog(data.message);
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
        appendLog("There is no " + itemName + " here.");
        return;
    }

    room.items = items.filter((item) => item !== itemName);
    gameState.inventory.push(itemName);
    renderRoom();
    appendLog("You picked up the " + itemName + ".");
}

function useItem(itemName) {
    if (itemName !== "key") {
        appendLog("You cannot use that here.");
        return;
    }

    if (!hasItem("key")) {
        appendLog("You need a key first.");
        return;
    }

    if (currentRoomId !== "locked_room") {
        appendLog("The key does not fit anything nearby.");
        return;
    }

    if (gameState.treasureUnlocked) {
        appendLog("The treasury is already unlocked.");
        return;
    }

    gameState.treasureUnlocked = true;
    updateLockedTreasuryExit();
    appendLog("The treasury door unlocks. A northern path is now open.");
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
        appendLog("Inventory: " + (gameState.inventory.join(", ") || "empty") + ".");
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

    appendLog("Unknown command.");
}

async function handleCommand(command) {
    const normalized = normalizeCommand(command);
    let response = null;

    try {
        response = await sendGameCommand(normalized);
    } catch (error) {
        appendLog("Command service is unavailable.");
        return;
    }

    if (response && response.success === false) {
        appendApiMessage(response);
        return;
    }

    appendApiMessage(response);
    applyFrontEndCommand(normalized, { echoLook: !response || !response.message });
}

function moveToDirection(direction) {
    if (!direction || isMoving) return;

    const room = rooms[currentRoomId];
    const nextRoomId = room.exits[direction];
    const path = room.paths && room.paths[direction];

    playPlayerStep(direction);

    if (!nextRoomId || !path) {
        appendLog("No exit in that direction.");
        return;
    }

    isMoving = true;
    setPlayerPosition(path.exit);
    window.setTimeout(() => {
        currentRoomId = nextRoomId;
        renderRoom(path.enter);
        appendLog("Moved to " + rooms[currentRoomId].title + ".");
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
        setFormMessage("login-message", "Please enter username and password.");
        return;
    }

    setFormMessage("login-message", "Signing in...");
    try {
        const data = await callApi("login", { username, password });
        if (!data.success) {
            setFormMessage("login-message", data.message || "Login failed.");
            return;
        }
        setFormMessage("login-message", "");
        enterGameFromAuth(data);
    } catch (error) {
        setFormMessage("login-message", "Unable to connect to the login service.");
    }
});

$("register-form").addEventListener("submit", async (event) => {
    event.preventDefault();
    const username = $("register-username").value.trim();
    const password = $("register-password").value;

    if (!username || !password) {
        setFormMessage("register-message", "Please enter username and password.");
        return;
    }

    setFormMessage("register-message", "Creating account...");
    try {
        const data = await callApi("register", { username, password });
        if (!data.success) {
            setFormMessage("register-message", data.message || "Registration failed.");
            return;
        }
        setFormMessage("register-message", "");
        enterGameFromAuth(data);
    } catch (error) {
        setFormMessage("register-message", "Unable to connect to the registration service.");
    }
});

$("logout-btn").addEventListener("click", async () => {
    if (sessionId) {
        try {
            await callApi("logout", { sessionId });
        } catch (error) {
            appendLog("Logout service is unavailable.");
        }
    }
    sessionId = null;
    currentUsername = null;
    showView("auth");
});

$("submit-btn").addEventListener("click", async () => {
    const command = $("command-input").value.trim();
    if (!command) {
        appendLog("Please enter a command.");
        return;
    }
    appendLog("> " + command);
    await handleCommand(command);
    $("command-input").value = "";
});

document.querySelectorAll(".direction-pad button").forEach((button) => {
    button.addEventListener("click", async () => {
        appendLog("> " + button.dataset.command);
        await handleCommand(button.dataset.command);
    });
});
