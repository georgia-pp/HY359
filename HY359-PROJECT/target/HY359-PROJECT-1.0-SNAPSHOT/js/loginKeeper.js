function back() {
    window.location.href = "index.html";
}

function options() {
    document.getElementById('options').style.display = 'block';
    document.getElementById('logIn').style.display = 'block';
    document.getElementById('messImg').style.display = 'block';
    document.getElementById('alertImg').style.display = 'block';
    document.getElementById('infoImg').style.display = 'block';
    document.getElementById('log').style.display = 'none';
}

function keeper_messages() {
    var blurContainer = document.getElementById('options');
    var messageContainer = document.getElementById('messageContainer');

    if (blurContainer.style.filter === 'blur(0px)') {
        blurContainer.style.filter = 'blur(5px)';
        messageContainer.style.display = 'block';
    } else {
        blurContainer.style.filter = 'blur(0px)';
        messageContainer.style.display = 'none';
    }
    allKeepers_messages();
}

function messagePost() {
    let myForm = document.getElementById('messageForm');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            $('#message_output').html("Successful!<br>");
        } else if (xhr.status !== 200) {
            console.log(xhr.responseText);
            $('#message_output').append('Request failed. Not added in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'AddMessage');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function keeper_info() {
    var blurContainer = document.getElementById('options');
    var messageContainer = document.getElementById('info');

    if (blurContainer.style.filter === 'blur(0px)') {
        blurContainer.style.filter = 'blur(5px)';
        messageContainer.style.display = 'block';
    } else {
        blurContainer.style.filter = 'blur(0px)';
        messageContainer.style.display = 'none';
    }
    bookings_number();
    finishedBookings();
    allReviews();
}

function keeper_rent() {
    var blurContainer = document.getElementById('options');
    var messageContainer = document.getElementById('requests');

    if (blurContainer.style.filter === 'blur(0px)') {
        blurContainer.style.filter = 'blur(5px)';
        messageContainer.style.display = 'block';
    } else {
        blurContainer.style.filter = 'blur(0px)';
        messageContainer.style.display = 'none';
    }
    keeperBookings();
    petInfo();
}

function allKeepers_messages() {
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
    xhr.open('GET', 'AllMessages?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function accept() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#status_output").html("Status updated!");
        } else if (xhr.status !== 200) {
            $("#status_output").html("Something went wrong!");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'AcceptBooking?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function reject() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#status_output").html("Status updated!");
        } else if (xhr.status !== 200) {
            $("#status_output").html("Something went wrong!");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', 'RejectBooking?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function bookings_number() {
    document.getElementById('num_bookings').innerHTML = "";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = xhr.responseText;
            if (response.trim() !== "") {
                $("#num_bookings").html(JSON.parse(xhr.responseText));
            }
        } else if (xhr.status !== 200) {
            var response = xhr.responseText;
            console.log(response);
            $("#num_bookings").html("Error");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'BookingsNumber?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function loginKeeper() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('keeper_table');
            if (data) {
                let row = table.insertRow();
                row.insertCell(0).innerHTML = data['username'];
                row.insertCell(1).innerHTML = data['firstname'];
                row.insertCell(2).innerHTML = data['lastname'];
                row.insertCell(3).innerHTML = data['email'];
                row.insertCell(4).innerHTML = data['bithdate'];
                row.insertCell(5).innerHTML = data['telephone'];
            }
            options();
        } else if (xhr.status !== 200) {
            $("#login_output").html("Wrong Credentials");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'LoginKeeper?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function editKeeper() {
    window.location.href = "editKeeper.html";
}

function keeperBookings() {
    document.getElementById('request_table').innerHTML = "";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('request_table');
            if (data) {
                let row = table.insertRow();
                row.insertCell(0).innerHTML = data['pet_id'];
                row.insertCell(1).innerHTML = data['fromDate'];
                row.insertCell(2).innerHTML = data['toDate'];
                row.insertCell(3).innerHTML = data['price'];
            }
        } else if (xhr.status !== 200) {

        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'KeeperBooking?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function petInfo() {
    document.getElementById('pet_table').innerHTML = "";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('pet_table');
            if (data) {
                let row = table.insertRow();
                row.insertCell(0).innerHTML = data['owner_id'];
                row.insertCell(1).innerHTML = data['name'];
                row.insertCell(2).innerHTML = data['type'];
                row.insertCell(3).innerHTML = data['breed'];
                row.insertCell(4).innerHTML = data['gender'];
            }
        } else if (xhr.status !== 200) {

        }
    };

    xhr.open('GET', 'PetsInfo');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function finishedBookings() {
    document.getElementById('fin_table').innerHTML = "";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('fin_table');
            data.forEach(function (veh) {
                let veh_data = JSON.parse(veh);
                let row = table.insertRow();
                row.insertCell(0).innerHTML = veh_data['borrowing_id'];
                row.insertCell(1).innerHTML = veh_data['owner_id'];
                row.insertCell(2).innerHTML = veh_data['pet_id'];
                row.insertCell(3).innerHTML = veh_data['fromDate'];
                row.insertCell(4).innerHTML = veh_data['toDate'];
                row.insertCell(5).innerHTML = veh_data['status'];
                row.insertCell(6).innerHTML = veh_data['price'];
            });
        } else if (xhr.status !== 200) {

        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'FinishedBookings?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function allReviews() {
    document.getElementById('reviews_table').innerHTML = "";
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('reviews_table');
            data.forEach(function (veh) {
                let veh_data = JSON.parse(veh);
                let row = table.insertRow();
                row.insertCell(0).innerHTML = veh_data['review_id'];
                row.insertCell(1).innerHTML = veh_data['owner_id'];
                row.insertCell(2).innerHTML = veh_data['reviewText'];
                row.insertCell(3).innerHTML = veh_data['reviewScore'];
            });
        } else if (xhr.status !== 200) {
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'AllReviews?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function logoutKeeper() {
    window.location.href = "loginKeeper.html";
}