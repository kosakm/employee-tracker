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
	wsURI = "ws://localhost:9000/associateWs";
	outputDiv = document.getElementById("output"); 
	$("#sendLocation").click(function() {
		sendLocation();
	});
}

function sendLocation() {
	if (websocket.readyState == websocket.CLOSED) {
		testWebSocket();
	}
	var uuid = $('#uuid').val();
	var dept = $('#dept').val();
	var site = $('#site').val();
	var locationUpdate = '{"uuid": "' + uuid + '", "department": "' + dept + '", "site": "' + site + '"}';
	doSend(locationUpdate);
}

function testWebSocket() {
	websocket = new WebSocket(wsURI); 
	websocket.onopen = function(evt) { onOpen(evt) }; 
	websocket.onclose = function(evt) { onClose(evt) }; 
	websocket.onmessage = function(evt) { onMessage(evt) }; 
	websocket.onerror = function(evt) { onError(evt) }; 
}

function onOpen(evt) {
	writeToScreen("<p>CONNECTED</p>", STATUS); 
}  

function onClose(evt) {
	writeToScreen("<p>DISCONNECTED</p>", STATUS); 
}


function onMessage(evt) { 
		
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
		outputElement = output;
	} else if (to == STATUS) {
		outputElement = document.getElementById("status");
	} else if (to == REQUEST) {
		outputElement = document.getElementById("request")
	} else {
		return
	}
	outputElement.innerHTML = message; 
}  