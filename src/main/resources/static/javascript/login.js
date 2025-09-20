
function changeVisibility() {
    const img = document.getElementById("visibility-img");
    const passwordInput = document.getElementById("casete-password");

    if (passwordInput.type === "password") {
        img.src = "/assets/ochi.png";
        passwordInput.type = "text";
    }
    else {
        img.src = "/assets/ochi-taiat.png";
        passwordInput.type = "password";
    }
}
