window.onload = function () {
    var optionOut = document.getElementById('out');
    var optionCat = document.getElementById('cat');
    var optionDog = document.getElementById('dog');

    optionOut.addEventListener('change', updateInputField);
    optionCat.addEventListener('change', updateInputField);
    optionDog.addEventListener('change', updateInputField);
}


function showPassword() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

function checkPasswordStrength() {
    var password = document.getElementById("password").value;
    var strengthMessage = document.getElementById("strengthMessage");

    // Check if the password is weak, medium, or strong
    var numCount = password.replace(/[^0-9]/g, "").length;
    var symCount = password.replace(/[a-zA-Z0-9]/g, "").length;
    var upperCount = password.replace(/[^A-Z]/g, "").length;
    var lowerCount = password.replace(/[^a-z]/g, "").length;

    if (numCount >= password.length / 2) {
        strengthMessage.textContent = "Password Strength: Weak";
    } else if (numCount > 0 && symCount > 0 && upperCount > 0 && lowerCount > 0) {
        strengthMessage.textContent = "Password Strength: Strong";
    } else {
        strengthMessage.textContent = "Password Strength: Medium";
    }
}

function togglePetKeeperFields() {
    var checkBox = document.getElementById("keeper");
    var text = document.getElementById("pet_keeper_fields");
    if (checkBox.checked == true) {
        text.style.display = "block";
    } else {
        text.style.display = "none";
    }
}

function updateInputField() {
    var optionOut = document.getElementById('out');
    var optionCat = document.getElementById('catkeeper');
    var optionDog = document.getElementById('dogkeeper');

    if (optionOut.checked) {
        optionDog.checked = true;
        optionCat.checked = false;
    }
}

function checkLocationExistence() {
    const x = document.getElementById("demo");

    var country = document.getElementById("country").value;
    var city = document.getElementById("city").value;
    var address = document.getElementById("address").value;

    var combinedAddress = country + ", " + city + ", " + address;

    var nominatimURL = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodeURIComponent(combinedAddress);

    var xhr = new XMLHttpRequest();
    xhr.open("GET", nominatimURL, true);

    xhr.onload = function () {
        if (xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);

            if (response.length > 0) {
                x.innerHTML = "The area exists!";
                var latitude = response[0].lat;
                var longitude = response[0].lon;
                document.getElementById("lat").value = latitude;
                document.getElementById("lon").value = longitude;
            } else {
                x.innerHTML = "The area does not exist.";
            }
        } else {
            x.innerHTML = "There was a problem communicating with the Nominatim API.";
        }
    };

    xhr.send();
}