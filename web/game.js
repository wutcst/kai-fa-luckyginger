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

let playerAnimationTimer = null;

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

function getDirection(command) {
    if (command.includes("north")) return "north";
    if (command.includes("south")) return "south";
    if (command.includes("west")) return "west";
    if (command.includes("east")) return "east";
    return null;
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
    }, 540);
}

$("login-tab").addEventListener("click", () => setActiveAuthTab("login"));
$("register-tab").addEventListener("click", () => setActiveAuthTab("register"));

$("login-form").addEventListener("submit", (event) => {
    event.preventDefault();
    showView("game");
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
    playPlayerStep(getDirection(command));
    $("command-input").value = "";
});

document.querySelectorAll(".direction-pad button").forEach((button) => {
    button.addEventListener("click", () => {
        appendLog("> " + button.dataset.command);
        playPlayerStep(getDirection(button.dataset.command));
    });
});
