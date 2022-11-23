function subscribeForNotification(scope) {
    if (vms.isDevelopment) {
        console.log("subscribeForNotification");
    }

    var $navFormScope = $('#navForm');

    // configuration of connection
    var api = atmosphere;
    var request = new api.AtmosphereRequest();
    request.url = 'http://' + document.location.host + '/notify';
    request.transport = "long-polling";
    request.fallbackTransport = "streaming";
    request.contentType = 'application/json';
    request.shared = true;
    request.reconnectInterval = 1000 * 10;
    request.trackMessageLength = true;
    request.logLevel = (vms.isDevelopment ? 'debug' : 'info');


    /**
     * Everytime a new message is received
     * @param response should be a json of type tasksData or other implemented event types
     */
    request.onMessage = function (response) {
        if (vms.isDevelopment) {
            console.log("onMessage");
        }

        var message = response.responseBody;
        try {
            var json = $.parseJSON(message);
        } catch (e) {
            // TODO may request re-send of event?
            console.log('Not a valid JSON: ', message);
            return;
        }
        console.log(json);


        // event control for task notifications
        if (json.tasksData != undefined) {
            // for non-data event
            $("#navForm\\:notification-update-btn", $navFormScope).click();

            /*
            // for data event
            var $boxUserAction = $(".notification > a.user_action", $navFormScope);
            var $boxError = $(".notification > a.error", $navFormScope);
            json.tasksData.forEach(function (task) {
                switch (task.type) {
                    case "USER_ACTION":
                        $boxUserAction.text(task.count);
                        break;
                    case "ERROR":
                        $boxError.text(task.count);
                        break;
                }
            });
            */
        }

    };

    var socket = null;
    configureListeners(request, socket);

    // connect to server with defined parameters
    socket = api.subscribe(request);

}