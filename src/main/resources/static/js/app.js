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

var sockClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
    !connected && $("#message").val("");
    !connected && $('#person').html('');
}

function connect() {

    var lastName = $("#lastName").val().split(' ').join('');

    if(!lastName) {
        return alert('Insert Last Name first');
    }

    $('#person').html('');

     // Create a connection to http://localhost:8080/ws-p2p
     sockClient = new SockJS('/ws-p2p');

     // Open the connection
     sockClient.onopen = function(e) {
          console.log('Socket is ' + e.type);
          sockClient.send('lastName=' + lastName);
      };
      // On receive message from server
     sockClient.onmessage = function(e) {
          var message = e.data;
          console.log(message);
          if(message.indexOf('{') === 0) {
              var person = JSON.parse(message);
              if(!person || !person.firstName) {
                alert('User not found!');
                sockClient.close();
                return;
              }
              $('#person').html(person.firstName + ' ' + person.lastName);
              setConnected(true);
              sockClient.send('message=' + $('#person').html() + ' Joined chat');
              return;
          }
          // Get and show the content
          showGreeting(message);
     };
      // On connection close
     sockClient.onclose = function() {
          console.log('socket is closed');
          setConnected(false);
          sockClient = null;
     };
}

function disconnect() {
    if(!sockClient) {
        return;
    }
    sockClient.send('message=' + $('#person').html() + ' Left chat');
    sockClient.close();
    console.log("Socket Disconnected ... ");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function sendPersonData() {
    var message = $('#message').val();
    if(!message) {
        return alert('Write something first!');
    }
    $('#message').val('');
    sockClient.send('message=' + $('#person').html() + ' says: '+ message);
}

$(function () {
    $("form").on('submit', function (e) { e.preventDefault(); });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendPersonData(); });
});