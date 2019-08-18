/**
 *    Copyright 2019 MetaRing s.r.l.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
function showGreeting(event, message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendPersonData() {
    var message = $('#message').val();
    if(!message) {
        return alert('Write something first!');
    }
    $('#message').val('');
    message = $('#person').html() + ' says: ' + message;
    window.functionalities.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_MESSAGE(message);
}

async function login() {
    var lastName = $("#lastName").val().split(' ').join('');

    if(!lastName) {
        return alert('Insert Last Name first');
    }

    $('#person').html('');

    var response = await window.functionalities.COM_METARING_SPRINGBOOTAPPEXAMPLE_SERVICE_GET_PERSONS_BY_LAST_NAME(lastName);
    try {
        var person = response.result[0];
        $('#person').html(person.firstName + " " + person.lastName);
    } catch(e) {
        return alert('Login failed');
    }
}

$(function () {
    $("form").on('submit', function (e) { e.preventDefault(); });
    $( "#login" ).click(login);
    $( "#send" ).click(sendPersonData);
    $.subscribe('message', showGreeting);
    window.base_url = window.location.origin;
    window.serverManager = new ServerManager();
    window.functionalities = new functionalities(window.serverManager.call);
});