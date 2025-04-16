function edit_keeper(){
    let myForm = document.getElementById('myform');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            $('#editInfo').html("Successful update!<br>");
        } else if (xhr.status !== 200) {
            console.log(xhr.responseText);
            $('#editInfo').append('Request failed. Not edited in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'EditKeeper');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}

function edit_owner(){
    let myForm = document.getElementById('petOwner');
    let formData = new FormData(myForm);
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            $('#editInfo').html("Successful update!<br>");
        } else if (xhr.status !== 200) {
            console.log(xhr.responseText);
            $('#editInfo').append('Request failed. Not edited in the database. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'EditOwner');

    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(data));
}
