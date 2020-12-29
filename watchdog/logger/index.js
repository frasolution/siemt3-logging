const axios = require('axios')
const TailingReadableStream = require('tailing-stream');

const stream = TailingReadableStream.createReadStream("log.log", { timeout: 0 });
const api = axios.create({ baseURL: 'http://localhost:3000'});

stream.on('data', buffer => {
    sender(buffer.toString());
});

stream.on('close', () => {
    console.log("close");
});

function sender(msg) {
    return api.post('http://localhost:3000/', {
        type: 'sshd',
        message: msg
    });
}
