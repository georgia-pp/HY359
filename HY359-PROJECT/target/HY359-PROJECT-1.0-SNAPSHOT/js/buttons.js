window.onload = function() {
    availableKeepers();
};

function login(){
    document.getElementById('start').style.display = 'none';
    document.getElementById('login').style.display = 'block';
}

function signup(){
    document.getElementById('start').style.display = 'none';
    document.getElementById('signup').style.display = 'block';
}

function logInKeeper(){
    window.location.href = "loginKeeper.html";
}

function logInOwner(){
    window.location.href = "loginOwner.html";
}

function logInadmin(){
    window.location.href = "administrator.html";
}

function regKeeper(){
    window.location.href = "regKeeper.html";
}

function regOwner(){
    window.location.href = "regOwner.html";
}

function visitor(){
    window.location.href = "visitor.html";
}

function back(){
    document.getElementById('start').style.display = 'block';
    document.getElementById('signup').style.display = 'none';
    document.getElementById('login').style.display = 'none';
}

function availableKeepers(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('av');
            data.forEach(function (veh) {
                let veh_data = JSON.parse(veh);
                let row = table.insertRow();
                row.insertCell(0).innerHTML = veh_data['username'];
                row.insertCell(1).innerHTML = veh_data['firstname'];
                row.insertCell(2).innerHTML = veh_data['lastname'];
                row.insertCell(3).innerHTML = veh_data['email'];
                row.insertCell(4).innerHTML = veh_data['bithdate'];
                row.insertCell(5).innerHTML = veh_data['telephone'];
            });
        } else if (xhr.status !== 200) {
             $("#output").html("Wrong Credentials");
        }
    };
    xhr.open('GET', 'AllKeepers');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}