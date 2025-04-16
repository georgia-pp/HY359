function back() {
    window.location.href = "index.html";
}

function loginAdmin() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            adminOptions();
        } else if (xhr.status !== 200) {
            $("#output").html("Wrong Credentials");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'LoginAdmin?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}

function adminOptions() {
    document.getElementById('adminOptions').style.display = 'block';
    document.getElementById('log').style.display = 'none';
    allKeepers();
    allOwners();
    cats_number();
    dogs_number();
    total_income();
    owner_number();
    keeper_number();
}

function allKeepers() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('keep');
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
    xhr.open('GET', 'AllKeepers');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function allOwners() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const data = JSON.parse(xhr.responseText);
            const table = document.getElementById('own');
            data.forEach(function (veh) {
                let veh_data = JSON.parse(veh);
                let row = table.insertRow();
                row.insertCell(0).innerHTML = veh_data['username'];
                row.insertCell(1).innerHTML = veh_data['firstname'];
                row.insertCell(2).innerHTML = veh_data['lastname'];
                row.insertCell(3).innerHTML = veh_data['email'];
                row.insertCell(4).innerHTML = veh_data['bithdate'];
                row.insertCell(5).innerHTML = veh_data['telephone'];
                row.insertCell(6).innerHTML = veh_data['owner_id'];
            });
        } else if (xhr.status !== 200) {
            $("#output").html("Wrong Credentials");
        }
    };
    xhr.open('GET', 'AllOwners');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function deleteKeeper() {
    var id = document.getElementById("keeper_id").value;
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#delOutput").html("Delete keeper with id: " + id);
        } else if (xhr.status !== 200) {
            $("#delOutput").html("Delete failed");
        }
    };

    xhr.open('POST', 'DeleteKeeper?keeper_id=' + encodeURIComponent(id));
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function deleteOwner() {
    var id = document.getElementById("owner_id").value;
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#delOutput").html("Delete owner with id: " + id);
        } else if (xhr.status !== 200) {
            $("#delOutput").html("Delete failed");
        }
    };

    xhr.open('POST', 'DeleteOwner?owner_id=' + encodeURIComponent(id));
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function cats_number() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = xhr.responseText;
            if (response.trim() !== "") {
                $("#statistics").html("Total number of cats: ");
                $("#statistics").append(JSON.parse(xhr.responseText));
                createPieChart('catsChart', 'Total number of cats', JSON.parse(xhr.responseText));
            }
        } else if (xhr.status !== 200) {
            var response = xhr.responseText;
            console.log(response);
            $("#statistics").html("Error");
        }
    };
    xhr.open('GET', 'CatNumber');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function dogs_number() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = xhr.responseText;
            if (response.trim() !== "") {
                $("#statistics_d").html("Total number of dogs: ");
                $("#statistics_d").append(JSON.parse(xhr.responseText));
                createPieChart('dogsChart', 'Total number of dogs', JSON.parse(xhr.responseText));
            }
        } else if (xhr.status !== 200) {
            var response = xhr.responseText;
            console.log(response);
            $("#statistics_d").html("Error");
        }
    };
    xhr.open('GET', 'DogNumber');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function total_income() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = xhr.responseText;
            if (response.trim() !== "") {
                $("#total_income").html("Total income: "+JSON.parse(xhr.responseText));
                incomes(JSON.parse(xhr.responseText));
            }
        } else if (xhr.status !== 200) {
            var response = xhr.responseText;
            console.log(response);
            $("#income").html("Error");
        }
    };
    xhr.open('GET', 'Income');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function incomes(num) {
    var keeper = 0.75 * num;
    var admin = 0.25 * num;
    $("#income").html("Total income for keepers: " + keeper.toFixed(2));
    $("#income").append("<br>Total income for the app: " + admin.toFixed(2));

}

function keeper_number() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = xhr.responseText;
            if (response.trim() !== "") {
                $("#keeper").html("Total number of keeper: ");
                $("#keeper").append(JSON.parse(xhr.responseText));
                createPieChart('keepersChart', 'Total number of cats', JSON.parse(xhr.responseText));
            }
        } else if (xhr.status !== 200) {
            reject(new Error(xhr.responseText));
        }
    };
    xhr.open('GET', 'KeeperNumber');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function owner_number() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = xhr.responseText;
            if (response.trim() !== "") {
                $("#owner").html("Total number of owner: ");
                $("#owner").append(JSON.parse(xhr.responseText));
                createPieChart('ownersChart', 'Total number of cats', JSON.parse(xhr.responseText));
            }
        } else if (xhr.status !== 200) {
            reject(new Error(xhr.responseText));
        }
    };
    xhr.open('GET', 'OwnerNumber');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

function logoutAdmin() {
    window.location.href = "administrator.html";
}

function createPieChart(chartId, label, data) {
    var ctx = document.getElementById(chartId).getContext('2d');
    new Chart(ctx, {
        type: 'pie',
        data: {
            labels: Object.keys(data),
            datasets: [{
                data: Object.values(data),
                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#8e5ea2', '#3cba9f', '#e8c3b9'],
            }],
        },
        options: {
            title: {
                display: true,
                text: label,
            },
        },
    });
}