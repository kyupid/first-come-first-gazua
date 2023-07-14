const WebSocketClient = require('websocket').client;

let count = 0;
const maxCount = 10000;

function createConnection() {
    if (count >= maxCount) {
        return;
    }

    const client = new WebSocketClient();

    client.on('connectFailed', function(error) {
        console.log('Connect Error: ' + error.toString());
    });

    client.on('connect', function(connection) {
        console.log('WebSocket Client Connected');
        count++;

        connection.on('error', function(error) {
            console.log("Connection Error: " + error.toString());
        });

        connection.on('close', function() {
            console.log('Connection Closed');
        });

        createConnection();
    });

    client.connect('ws://localhost:8080/websocket-endpoint', 'echo-protocol');
}

createConnection();
