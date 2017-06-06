var TOKEN = null,
    RETRY_COUNT = 25,
    RETRY_DELAY = 60;

var username = 'guest';

function setSize() {
    let height = $("#term-row").height() - $("footer").height() - 100;
    $("#term").css("height", height);
}

function sendCommand(command, terminal) {
    var data = new Object();
    data.command = command;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            terminal.echo(data["content"]);
        },
        error: function (e) {
            terminal.error("error: " + e.status);
        }
    });
}

(function () {
    'use strict';
    jQuery(function ($, undefined) {
        $('#term').terminal(function (command) {
            // Preserve theterminal 
            let terminal = this;
            sendCommand(command, terminal);
        }, {
            greetings: 'AsciiPic Web CLI',
            prompt: username + '@asciipic ~$ ',
            resize: true,
            history: true,
        });
    });

    // Resize
    setSize();
    $(window).resize(setSize);
})();
