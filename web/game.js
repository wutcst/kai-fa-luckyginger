const $ = (id) => document.getElementById(id);

const views = {
    auth: $("auth-view"),
    game: $("game-view")
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
        title: "Campus Gate",
        description: "You are standing at the campus gate. A path leads north into the main hall.",
        image: "assets/rooms/campus_gate.png",
        exits: { north: "main_hall" },
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

function setPlayerPosition(position) {
    $("player-token").style.left = position.left + "%";
    $("player-token").style.top = position.top + "%";
}

function renderRoom(entryPosition) {
    const room = rooms[currentRoomId];
    $("scene-bg").src = room.image;
    $("room-name").textContent = room.title;
    $("room-description").textContent = room.description;
    setPlayerPosition(entryPosition || room.start);
}

function getDirection(command) {
    if (command.includes("north")) return "north";
    if (command.includes("south")) return "south";
    if (command.includes("west")) return "west";
    if (command.includes("east")) return "east";
    return null;
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

$("login-form").addEventListener("submit", (event) => {
    event.preventDefault();
    showView("game");
    renderRoom();
    appendLog("Entered the campus gate.");
});

$("register-form").addEventListener("submit", (event) => {
    event.preventDefault();
    $("register-message").textContent = "Account form is ready. Registration API will be connected later.";
});

$("logout-btn").addEventListener("click", () => {
    showView("auth");
});

$("submit-btn").addEventListener("click", () => {
    const command = $("command-input").value.trim();
    if (!command) {
        appendLog("Please enter a command.");
        return;
    }
    appendLog("> " + command);
    moveToDirection(getDirection(command));
    $("command-input").value = "";
});

document.querySelectorAll(".direction-pad button").forEach((button) => {
    button.addEventListener("click", () => {
        appendLog("> " + button.dataset.command);
        const direction = getDirection(button.dataset.command);
        if (direction) {
            moveToDirection(direction);
            return;
        }
        appendLog(rooms[currentRoomId].description);
    });
});
