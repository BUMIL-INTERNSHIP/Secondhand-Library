const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/stomp/chats',
  debug: function (str) {
    console.log(str);
  }
});

stompClient.onConnect = (frame) => {
  setConnected(true);
  console.log('Connected: ' + frame);
  stompClient.subscribe('/sub/chats', (chatMessage) => {
    console.log('Received message:', chatMessage.body); // 로그 추가
    showMessage(JSON.parse(chatMessage.body));
  });
  stompClient.publish({
    destination: "/pub/chats",
    body: JSON.stringify({'sender': $("#username").val(), 'message': "connected"})
  });
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
};

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

function sendMessage() {
  stompClient.publish({
    destination: "/pub/chats",
    body: JSON.stringify({'sender': $("#username").val(), 'message': $("#message").val()})
  });
  $("#message").val("")
}

function showMessage(chatMessage) {
  $("#messages").append(
      "<tr><td>" + chatMessage.sender + " : " + chatMessage.message
      + "</td></tr>");
}

$(function () {
  $("form").on('submit', (e) => e.preventDefault());
  $("#connect").click(() => connect());
  $("#disconnect").click(() => disconnect());
  $("#send").click(() => sendMessage());
});
