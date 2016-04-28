// wrapped in a IIFE, good habit. (doensn't pollute global name-space)
(function () {
    var app = angular.module('filmr', ['ngRoute', 'ngResource']); // name  [dependencies] , name is referenced in html tag


    // ROUTING - what should happen when url changes to path x
    app.config(function ($routeProvider) {

        $routeProvider
            .when('/', {
                title: 'Home',
                activeTab: 'Home',
                templateUrl: 'views/home-view/home.html',
            })
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
            .when('/cinema', {
                title: 'Cinema',
                activeTab: 'Cinema',
                templateUrl: 'views/cinema-view/cinema.html',
                controller: 'cinemaController'

            })
            // admin
            .when('/admin', {
                title: 'Admin',
                activeTab: 'Admin',
                templateUrl: 'views/admin-view/admin.html',
                controller: 'adminController'

            })
            .when('/theater', {
                title: 'Create Theater',
                templateUrl: 'views/theater-view/theater.html',
                controller: 'theaterController'

            })
            .when('/theater/:id', {
                title: 'Edit Theater',
                templateUrl: 'views/theater-view/theater.html',
                controller: 'theaterController'

            }).otherwise({redirectTo: "/"})
    });


    // middle-ware

    // make title names and other functionality accessible for every page, and every controller/service injecting $rootScope
    app.run(['$rootScope', function ($rootScope) {
        $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {

            $rootScope.baseUrl = "/filmr/#/";
            $rootScope.API_baseUrl = "/filmr/api/";

            $rootScope.title = current.$$route.title;
            $rootScope.activeTab = current.$$route.activeTab;
            $rootScope.errorHandler = function (error) {
                console.log("Error!", error);
            }

        });
    }]);

})(); // run at once

