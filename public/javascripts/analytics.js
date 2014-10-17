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
	
	plot = $.plot("#Electronics", [ getRandomData(0,dataElecSales,'elecSales') ], {
		series: {
			shadowSize: 0	// Drawing is faster without shadows
		},
		yaxis: {
			min: 0,
			max: 100
		},
		xaxis: {
			show: false
		}
	});
	elecEmp = $.plot("#ElectronicsEmps", [ getRandomData(0,dataElecEmps,'elecEmps') ], {
		series: {
			shadowSize: 0	// Drawing is faster without shadows
		},
		yaxis: {
			min: 0,
			max: 6
		},
		xaxis: {
			show: false
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
	  if (message.response.hasOwnProperty('associateId')) {
		  elecEmp.setData([getRandomData(message.response.total,dataElecEmps, 'elecEmps')]);
		  elecEmp.draw(); 
	 } else if (message.response.hasOwnProperty('amount')) {
		  plot.setData([getRandomData(message.response.amount,dataElecSales, 'elecSales')]);
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

function getRandomData(amount,datag, type) {

	if (datag.length > 0)
		datag = datag.slice(1);
	
	// Do a random walk
	
	while (datag.length < totalPoints) {
	
		datag.push(amount);
	}
	
	// Zip the generated y values with the x values
	
	var res = [];
	for (var i = 0; i < datag.length; ++i) {
		res.push([i, datag[i]])
	}
	switch (type) {
		case "elecSales":
			dataElecSales = datag;
		case "elecEmp":
			dataElecEmps = datag;
	}
	return res;
}

function update(amount) {

	plot.setData([getRandomData(amount,dataElecEmps, 'elecEmps')]);

	// Since the axes don't change, we don't need to call plot.setupGrid()

	plot.draw();
	//setTimeout(update, updateInterval);
}
