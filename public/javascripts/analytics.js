var wsURI;
var outputDiv;

var OUTPUT = "output";
var STATUS = "status";
var REQUEST = "REQUEST";
var plot;
var elecEmp;

$(function () {
	init();
	testWebSocket();
	
	plot = $.plot("#Electronics", [ getRandomData(0) ], {
		series: {
			shadowSize: 0	// Drawing is faster without shadows
		},
		yaxis: {
			min: 0,
			max: 100,
			tickDecimals: 2
		},
		xaxis: {
			mode: "time"
		}
	});
	elecEmp = $.plot("#ElectronicsEmps", [ getElecEmpData(0) ], {
		series: {
			shadowSize: 0	// Drawing is faster without shadows
		},
		yaxis: {
			min: 0,
			max: 6
		},
		xaxis: {
			mode: "time"
		}
	});

	//update();
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
	  var message;
	  message = JSON.parse(evt.data);
	  $("#footer").prepend(message);
	  if (message.response.hasOwnProperty('total')) {
		  if(message.response.deptId == 25) {
		  elecEmp.setData([getElecEmpData(message.response.total)]);
		  elecEmp.draw(); 
		  }
	 } else if (message.response.hasOwnProperty('amount')) {
		  plot.setData([getRandomData(message.response.amount)]);
		  plot.draw();
	 }
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

var dataElecSales = [],
dataElecEmps = [],
totalPoints = 300;

function getRandomData(amount) {

	if (dataElecSales.length > 0)
		dataElecSales = dataElecSales.slice(1);
	
	// Do a random walk
	
	while (dataElecSales.length < totalPoints) {
	
		dataElecSales.push(amount);
	}
	
	// Zip the generated y values with the x values
	
	var res = [];
	for (var i = 0; i < dataElecSales.length; ++i) {
		res.push([i, dataElecSales[i]])
	}

	return res;
}

function getElecEmpData(amount) {

	if (dataElecEmps.length > 0)
		dataElecEmps = dataElecEmps.slice(1);
	
	// Do a random walk
	
	while (dataElecEmps.length < totalPoints) {
	
		dataElecEmps.push(amount);
	}
	
	// Zip the generated y values with the x values
	
	var res = [];
	for (var i = 0; i < dataElecEmps.length; ++i) {
		res.push([i, dataElecEmps[i]])
	}
	return res;
}

function update(amount) {

	plot.setData([getRandomData(amount,dataElecEmps, 'elecEmps')]);

	// Since the axes don't change, we don't need to call plot.setupGrid()

	plot.draw();
	//setTimeout(update, updateInterval);
}
