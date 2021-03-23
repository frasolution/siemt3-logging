
/*++
Project Name:
watchdog_client

Author:
Maximilian M (Meshstyles)

Description:
main program, actually working with the log

--*/

const TailingReadableStream = require('tailing-stream');
const { apache2log } = require('./lib/lookpuppy');

const apache2Stream = TailingReadableStream.createReadStream("/var/log/apache2/access.log", { timeout: 0 });

apache2Stream.on('data', buffer => {
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
            if (!(elog === "")) {
                console.log("apache2: " + log)
                apache2log(elog)
            } 
        });
    }
});


apache2Stream.on('close', () => {
    console.log("close");
});
