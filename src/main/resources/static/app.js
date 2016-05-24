// wrapped in a IIFE, good habit. (doensn't pollute global name-space)
    var app = angular.module('filmr', ['ngRoute', 'ngResource']); // name  [dependencies] , name is referenced in html tag

    var debug = true;

    // ROUTING - what should happen when url changes to path x
    app.config(['$routeProvider', '$logProvider', '$provide', function ($routeProvider,$logProvider,$provide) {

        if(!debug) {
            $provide.decorator('$log', function ($delegate) {

                var origLog = $delegate.log;
                var origInfo = $delegate.info;
                var origWarn = $delegate.warn;
                var origError = $delegate.error;
                var origDebug = $delegate.debug;

                $delegate.log = function () {};
                $delegate.info = function () {};
                $delegate.warn = function () {};
                $delegate.error = function () {};
                $delegate.debug = function () {};

                return $delegate;
            });
        }

        $routeProvider
            .when('/book', {
                title: 'Book',
                activeTab: 'Book',
                templateUrl: 'views/booking-view/bookShowing.html',
                controller: 'bookShowingController'
            })
            .when('/book/showing/:showingId/seat_select', {
                title: 'Select Seats',
                activeTab: 'Book',
                templateUrl: 'views/seatSelect-view/seatSelect.html',
                controller: 'seatSelectController'

            })
            .when('/book/showing/:showingId/confirm/:bookingId', {
                title: 'Confirmation',
                activeTab: 'Book',
                templateUrl: 'views/confirm-view/confirm.html',
                controller: 'confirmController'

            })
            // Cinema 
            .when('/cinema/:id', {
                title: 'Cinema',
                activeTab: 'Customer',
                templateUrl: 'views/cinema-view/cinema.html',
                controller: 'cinemaController'

            })
            //Showing
            .when('/showing', {
                title: 'Showing',
                activeTab: 'Showing',
                templateUrl: 'views/showing-view/showing.html',
                controller: 'showingController'

            })
            .when('/cinema/:cinema_id/theater/:theater_id', {
                title: 'Edit Theater',
                activeTab: 'Customer',
                templateUrl: 'views/theater-view/theater.html',
                controller: 'theaterController'

            })
            .when('/cinema/:cinema_id/theater/new', {
                title: 'New Theater',
                activeTab: 'Customer',
                templateUrl: 'views/theater-view/theater.html',
                controller: 'theaterController'

            })
            .when('/customer', {
                title: 'Customer Settings',
                activeTab: 'Customer',
                templateUrl: 'views/customer-view/customer.html',
                controller: 'customerController'

            })
            // all other (invalid) paths.
            .otherwise({redirectTo: "/customer"})
    }]);


    // middle-ware


    // make title names and other functionality accessible for every page, and every controller/service injecting $rootScope

    app.run(['$rootScope', '$timeout', '$log', function ($rootScope, $timeout,$log) {
        $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {


            function clear() {
                $log.debug("clear");
                $rootScope.alerts = [];
            }

            $rootScope.alerts = [];
            $rootScope.baseUrl = "/filmr/#/";
            $rootScope.API_baseUrl = "/filmr/api/";

            $rootScope.title = current.$$route.title;
            $rootScope.activeTab = current.$$route.activeTab;

            $rootScope.clearAlerts = function() {
                $log.info("clear");
                $rootScope.alerts = [];
            };

            $rootScope.alert = function(header,text,type) {
                $rootScope.alerts.push({"header":header,"text":text,"type":type});
                $timeout($rootScope.clearAlerts, 3000);
            };

            $rootScope.genericError = function() {
                $rootScope.alert("Error! ","",2);
            };

            $rootScope.errorHandler = (debug ?
                function (error) {
                    $log.error("Error!");
                    $log.error(error);
                    $rootScope.alert("Error! ", error, 2);
                } :
                function (error) {
                    $rootScope.genericError();
                }
            );

        });
    }]);

