var TOKEN = null;
var RETRY_COUNT = 25;
var RETRY_DELAY = 60;

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
        headers: {"Authorization": localStorage.getItem('token')},
        data: JSON.stringify(data),
        dataType: "json",
        timeout: 100000,
        success: function (responses, textStatus, request) {
            localStorage.setItem('token', request.getResponseHeader('Authorization'));
            responses.forEach(response => {
                if (response.metadata.successful == true) {
                    response.content.forEach(content => {
                        terminal.echo(content);
                    })
                }
                else {
                    response.content.forEach(content => {
                        terminal.error(content);
                    })
                }
            })
        },
        error: function (e) {
            terminal.error("error: " + e.status);
        }
    });
}

(function () {
    "use strict";
    jQuery(function ($, undefined) {
        $("#term").terminal(function (command) {
            // Preserve teterminal
            let terminal = this;
            sendCommand(command, terminal);
        }, {
            greetings: "AsciiPic Web CLI",
            prompt: "asciipic ~$ ",
            resize: true,
            history: true,
        });
    });

    // Resize
    setSize();
    $(window).resize(setSize);
})();
