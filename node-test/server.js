var http = require('http');

http.createServer(function (req, resp){
		
		resp.writeHead(200, {'Content-Type': 'text/pain'});
		resp.end('Hello World\n');
}).listen(8888);

console.log('Server running at http://localhost:8888/');
