var http = require('http');
var server = http.createServer();

function sleep(ms) {
  return new Promise((resolve) => {
    setTimeout(resolve, ms);
  });
}

server.on('request', async function(req, res) {
  await sleep(1000);

  res.writeHead(200, {'Content-Type' : 'text/plain'});
  res.write('hello world');
  res.end();
});

server.listen(8081);
