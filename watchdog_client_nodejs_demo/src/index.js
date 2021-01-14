
/*++
Project Name:
watchdog_client

Author:
Maximilian M (Meshstyles)

Description:
main program, actually working with the log

--*/

const TailingReadableStream = require('tailing-stream');
const { sshlog } = require('./lib/lookpuppy');

const sshStream = TailingReadableStream.createReadStream("./log.log", { timeout: 0 });

sshStream.on('data', buffer => {
    let log = buffer.toString();

    // split on newline => log array
    let args = log.split("\n");

    //filter out file beginning
    if (args.length < 10) {

        // removes last empty line /n/n
        args.pop();

        // for each log
        args.forEach(elog => {
            
            // check if there is a empty line in the log
            if (! (elog === "")) {
                
                // send log to matching method in lib/lookpuppy
                sshlog(elog)
            } 
        });
    }
});

sshStream.on('close', () => {
    console.log("close");
});
