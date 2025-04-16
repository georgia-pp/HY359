function back(){
    window.location.href = "index.html";
}

function options(){
    document.getElementById('options').style.display = 'block';
    document.getElementById('logIn').style.display = 'block';
    document.getElementById('messImg').style.display = 'block';
    document.getElementById('alertImg').style.display = 'block';
    document.getElementById('log').style.display = 'none';
    my_pet();
}

function owner_messages(){
    var blurContainer = document.getElementById('options');
    var messageContainer = document.getElementById('messageContainer');

    if (blurContainer.style.filter === 'blur(0px)') {
        blurContainer.style.filter = 'blur(5px)';
        messageContainer.style.display = 'block';
    } else {
        blurContainer.style.filter = 'blur(0px)';
        messageContainer.style.display = 'none';
    }
    allOwners_messages();
}

function owner_bookings() {
    var blurContainer = document.getElementById('options');
    var messageContainer = document.getElementById('bookingsContainer');

    if (blurContainer.style.filter === 'blur(0px)') {
        blurContainer.style.filter = 'blur(5px)';
        messageContainer.style.display = 'block';
    } else {
        blurContainer.style.filter = 'blur(0px)';
        messageContainer.style.display = 'none';
    }
    ownerBookings();
}

function messagePost(){
    let myForm = document.getElementById('messageForm');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            $('#mess').html("Successful!<br>");
        } else if (xhr.status !== 200) {
            console.log(xhr.responseText);
            $('#mess').append('Request failed. Not added in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'AddMessage');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function allOwners_messages() {
    document.getElementById('message_table').innerHTML = "";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('message_table');
            data.forEach(function (veh) {
                let veh_data = JSON.parse(veh);
                let row = table.insertRow();
                row.insertCell(0).innerHTML = veh_data['booking_id'];
                row.insertCell(1).innerHTML = veh_data['sender'];
                row.insertCell(2).innerHTML = veh_data['message'];
            });
        } else if (xhr.status !== 200) {
            $("#output").html("Wrong Credentials");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'AllOwnerMessage?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function ownerBookings(){
    document.getElementById('booking_table').innerHTML = "";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('booking_table');
            if (data) {
                document.getElementById('finish_button').style.display = 'block';
                let row = table.insertRow();
                row.insertCell(0).innerHTML = data['pet_id'];
                row.insertCell(1).innerHTML = data['fromDate'];
                row.insertCell(2).innerHTML = data['toDate'];
                row.insertCell(3).innerHTML = data['price'];
                row.insertCell(4).innerHTML = data['keeper_id']
            }
        } else if (xhr.status !== 200) {
            
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'OwnerBooking?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function finish() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#status_output").html("Status updated!");
            document.getElementById('review_button').style.display = 'block';
        } else if (xhr.status !== 200) {
            $("#status_output").html("Something went wrong!");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'FinishBooking?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function loginOwner() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('owner');
            if (data) {
                let row = table.insertRow();
                row.insertCell(0).innerHTML = data['username'];
                row.insertCell(1).innerHTML = data['firstname'];
                row.insertCell(2).innerHTML = data['lastname'];
                row.insertCell(3).innerHTML = data['email'];
                row.insertCell(4).innerHTML = data['bithdate'];
                row.insertCell(5).innerHTML = data['telephone'];
                row.insertCell(6).innerHTML = data['owner_id'];
            }
            options();
        } else if (xhr.status !== 200) {
             $("#output").html("Wrong Credentials");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'LoginOwner?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(data);
}

function my_pet(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('pet_owner');
            if (data) {
                let row = table.insertRow();
                row.insertCell(0).innerHTML = data['pet_id'];
                row.insertCell(1).innerHTML = data['name'];
                row.insertCell(2).innerHTML = data['type'];
                row.insertCell(3).innerHTML = data['breed'];
                row.insertCell(4).innerHTML = data['gender'];
                if(typeof data['name'] === 'undefined'){
                    document.getElementById('register_pet').style.display = 'block';
                }
            }
        } else if (xhr.status !== 200) {
             $("#output").html("Wrong Credentials");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'MyPet?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(data);
}

function editOwner(){
    window.location.href = "editOwner.html";
}

function submitPet(){
    window.location.href = "regPet.html";
}

function availableKeepers(){
    document.getElementById('avKeepers').style.display = 'block';
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('available_table');
            data.forEach(function (veh) {
                let veh_data = JSON.parse(veh);
                let row = table.insertRow();
                row.insertCell(0).innerHTML = veh_data['username'];
                row.insertCell(1).innerHTML = veh_data['firstname'];
                row.insertCell(2).innerHTML = veh_data['lastname'];
                row.insertCell(3).innerHTML = veh_data['email'];
                row.insertCell(4).innerHTML = veh_data['bithdate'];
                row.insertCell(5).innerHTML = veh_data['telephone'];
                row.insertCell(6).innerHTML = veh_data['keeper_id'];
            });
        } else if (xhr.status !== 200) {
             $("#output").html("Wrong Credentials");
        }
    };
    xhr.open('GET', 'AvailableKeepers');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

function bookings(){
    document.getElementById('booking').style.display = 'block';
}

function bookingForm(){
    
    let myForm = document.getElementById('booking');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#book').html("Successful booking!<br>");
        } else if (xhr.status !== 200) {
            $('#book').append('Request failed. Not added in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'AddBooking');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function reviews(){
    document.getElementById('review').style.display = 'block';
}

function reviewForm(){
    let myForm = document.getElementById('review');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#re').html("Successful!<br>");
        } else if (xhr.status !== 200) {
            $('#re').append('Request failed. Not added in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'AddReview');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function logoutOwner(){
    window.location.href = "loginOwner.html";
}