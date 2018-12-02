var fs = require("fs");

var data = 'hello world';

var writeStream = fs.createWriteStream('output.txt');

writeStream.write(data, 'UTF8');

writeStream.end();

writeStream.on('finish', function() {
  console.log('write over');
});

writeStream.on('error',function(err) {
  console.log(err.stack);
});

console.log('over');


