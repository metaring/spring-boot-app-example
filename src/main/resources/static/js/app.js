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
        if (connected) {
            $("#conversation").show();
        }
        else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
    }

    function connect() {
         // Create a connection to http://localhost:8080/ws-notification
         sockClient = new SockJS('/ws-notification');

         // Open the connection
         sockClient.onopen = function(e) {
              console.log('Socket is ' + e.type);
          };
          // On receive message from server
         sockClient.onmessage = function(e) {
              console.log(e.data);
              // Get and show the content
              showGreeting(JSON.stringify(e.data));
         };
          // On connection close
         sockClient.onclose = function() {
              console.log('Socket is closed');
         };
         setConnected(true);
    }

    function disconnect() {
        if (sockClient !== null) {
            sockClient.close();
        }
        setConnected(false);
        console.log("Socket Disconnected ... ");
    }

    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

    function sendPersonData() {
       // The object to send
       var personData = { 'firstName': $("#firstName").val(), 'lastName': $("#lastName").val() };
       // Send it now
       sockClient.send(JSON.stringify(personData));
    }

    $(function () {
        $("form").on('submit', function (e) { e.preventDefault(); });
        $( "#connect" ).click(function() { connect(); });
        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#send" ).click(function() { sendPersonData(); });
    });