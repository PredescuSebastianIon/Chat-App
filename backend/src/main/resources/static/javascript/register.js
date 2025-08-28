const submitButton = document.getElementById("butondone");
submitButton.addEventListener("click", submit)

async function submit() {
    const email = document.getElementById("email").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const repeatPassword = document.getElementById("repeatPassword").value;

    const data = {
        username,
        password,
        email
    }

    if (password === repeatPassword) {
        const jsonData = JSON.stringify(data);
        fetch("/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: jsonData
        })
        .then(response => {
            // To do later
            alert("registrare reusita");
        })
    }
}
