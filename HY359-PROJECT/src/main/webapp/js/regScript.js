function add_keeper(){
    let myForm = document.getElementById('myform');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            $('#signUpInfo').html("Successful Registration!<br>");
        } else if (xhr.status !== 200) {
            console.log(xhr.responseText);
            $('#signUpInfo').append('Request failed. Not added in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'RegisterKeeper');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function add_owner(){
    let myForm = document.getElementById('petOwner');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            $('#signUpInfo').html("Successful Registration!<br>");
        } else if (xhr.status !== 200) {
            console.log(xhr.responseText);
            $('#signUpInfo').append('Request failed. Not added in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'RegisterOwner');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function add_pet(){
    let myForm = document.getElementById('myPet');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            $('#signUpInfo').html("Successful Registration!<br>");
        } else if (xhr.status !== 200) {
            console.log(xhr.responseText);
            $('#signUpInfo').append('Request failed. Not added in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'RegisterPet');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}