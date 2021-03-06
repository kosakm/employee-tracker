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
	var associate_id = $('#associate_id').val();
	var department_id = $('#department_id').val();
	var site = $('#site').val();
	var event_type = $('#event_type').val();	
	var now = "2014-10-16 18:16:22 MST";
	var json = '{"associateId":' + associate_id + ',"departmentId":' + department_id + ',"site":1,"eventTime":"2014-10-16 18:16:22","eventType":'+event_type+'}';
	var locationUpdate = '"associate_id": ' + associate_id + ', "department_id": ' + department_id + ', "site": "' + site + '", "event_time": "' + now + '", "event_type": ' + event_type;
	doSend(json);
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