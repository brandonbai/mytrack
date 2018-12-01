const buf = Buffer.from('abbc', 'ascii');

console.log(buf.toString('hex'));

console.log(buf.toString('base64'));

const  buf1 = Buffer.alloc(10);

const  buf2 = Buffer.alloc(10, 1);

const buf3 = Buffer.allocUnsafe(10);

const buf4 = Buffer.from([1, 2, 3]);

console.log(JSON.stringify(buf4));

const buf5 = Buffer.from('test');

const buf6 = Buffer.from('test', 'latin1');


const buf7 = Buffer.alloc(26);

for (var i = 0; i < 26; i++) {
	buf7[i] = i + 97;
}

console.log(buf7.toString('ascii'));
console.log(buf7.toString('ascii', 0, 5));
console.log(buf7.toString('utf8', 0, 5));
console.log(buf7.toString(undefined, 0, 5));


var buffer1 = Buffer.concat([buf, buf6]);

console.log(buffer1.toString());

var result = buf5.compare(buf6);


console.log(result);
