const form = document.querySelector(".register_form");
form.addEventListener("submit", submitCheck);

function submitCheck(event) {
    const password = document.getElementById("password").value;
    const repeatPassword = document.getElementById("repeatPassword").value;

    if (!password || password !== repeatPassword) {
        event.preventDefault();
        document.getElementById("frontEndErrors").innerText = "The passwords doesn't coincide";
    }
    else {
        document.getElementById("frontEndErrors").innerText = "";
    }
}
