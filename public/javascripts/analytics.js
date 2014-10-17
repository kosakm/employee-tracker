var wsURI;
var outputDiv;

var OUTPUT = "output";
var STATUS = "status";
var REQUEST = "REQUEST";

$(function () {
	init();
	testWebSocket();
});

function init() {
	wsURI = "ws://localhost:9000/analyticsWs";
	outputDiv = document.getElementById("output"); 
}

function testWebSocket() {
	websocket = new WebSocket(wsURI); 
	websocket.onopen = function(evt) { onOpen(evt) }; 
	websocket.onclose = function(evt) { onClose(evt) }; 
	websocket.onmessage = function(evt) { onMessage(evt) }; 
	websocket.onerror = function(evt) { onError(evt) }; 
}

function onOpen(evt) {
	writeToScreen("<p>LISTENING</p>", STATUS); 
}  

function onClose(evt) {
	writeToScreen("<p>DISCONNECTED</p>", STATUS); 
}


function onMessage(evt) { 
	console.log("Received message");
	console.log(evt);
	writeToScreen("RECEIVED MESSAGE", OUTPUT); 
}  


function onError(evt) { 
	writeToScreen("<p>ERROR</p>", STATUS); 
}  

function doSend(message) { 
	writeToScreen("<code>SENT: " + message + "</code>", REQUEST);  
	websocket.send(message); 
}  

function writeToScreen(message, to) {
	var outputElement;
	if (to == OUTPUT) {
		outputElement = $("#output");
		outputElement.append("</br>" + message);
	} else if (to == STATUS) {
		outputElement = $("#status").html(message);
	} else if (to == REQUEST) {
		outputElement = $("#request").html(message)
	} else {
		return
	}
	 
}  