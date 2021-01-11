const TailingReadableStream = require('tailing-stream');
const { sshlog } = require('./lib/lookpuppy');

const sshStream = TailingReadableStream.createReadStream("/var/log/dpkg.log", { timeout: 0 });

sshStream.on('data', buffer => {
    let log = buffer.toString();
    // TODO real filter out multiline log start

    // sshlog(log);
    let args = log.split("\n");

    console.log(log);
    if (args.length < 10) {
        args.pop();
        console.log(args);    
    }

});

sshStream.on('close', () => {
    console.log("close");
});
