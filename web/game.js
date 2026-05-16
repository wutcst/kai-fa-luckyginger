const $ = (id) => document.getElementById(id);

const views = {
    auth: $("auth-view"),
    game: $("game-view")
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

$("login-tab").addEventListener("click", () => setActiveAuthTab("login"));
$("register-tab").addEventListener("click", () => setActiveAuthTab("register"));

$("login-form").addEventListener("submit", (event) => {
    event.preventDefault();
    showView("game");
    appendLog("进入校园入口。");
});

$("register-form").addEventListener("submit", (event) => {
    event.preventDefault();
    $("register-message").textContent = "账号信息已填写，后续将接入注册接口。";
});

$("logout-btn").addEventListener("click", () => {
    showView("auth");
});

$("submit-btn").addEventListener("click", () => {
    const command = $("command-input").value.trim();
    if (!command) {
        appendLog("请输入命令。");
        return;
    }
    appendLog("> " + command);
    $("command-input").value = "";
});

document.querySelectorAll(".direction-pad button").forEach((button) => {
    button.addEventListener("click", () => {
        appendLog("> " + button.dataset.command);
    });
});
