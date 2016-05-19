// wrapped in a IIFE, good habit. (doensn't pollute global name-space)
    var app = angular.module('filmr', ['ngRoute', 'ngResource']); // name  [dependencies] , name is referenced in html tag


    // ROUTING - what should happen when url changes to path x
    app.config(function ($routeProvider) {

        $routeProvider
            // booking
            .when('/book', {
                title: 'Book',
                activeTab: 'Book',
                templateUrl: 'views/booking-view/showings.html',
                controller: 'bookingController'
            })
            // booking_confirm 
            .when('/booking_confirm/:id', {
                title: 'Book',
                activeTab: 'Book',
                templateUrl: 'views/booking_confirmed-view/confirm.html',
                controller: 'bookingConfirmedController'

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
    });


    // middle-ware


    // make title names and other functionality accessible for every page, and every controller/service injecting $rootScope
    app.run(['$rootScope', '$timeout', function ($rootScope, $timeout) {
        $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {

            var debug = true;

            function clear() {
                console.log("clear");
                $rootScope.alerts = [];
            }

            $rootScope.alerts = [];
            $rootScope.baseUrl = "/filmr/#/";
            $rootScope.API_baseUrl = "/filmr/api/";

            $rootScope.title = current.$$route.title;
            $rootScope.activeTab = current.$$route.activeTab;

            $rootScope.clearAlerts = function() {
                console.log("clear");
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
                    console.log("Error!", error);
                    $rootScope.alert("Error! ", error, 2);
                } :
                function (error) {
                    $rootScope.genericError();
                }
            );

        });
    }]);

