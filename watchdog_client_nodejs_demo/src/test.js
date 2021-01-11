const Journalctl = require('journalctl');
const journalctl = new Journalctl({unit:"sshd"});

journalctl.on('event', (event) => {
  console.error(event)
});

