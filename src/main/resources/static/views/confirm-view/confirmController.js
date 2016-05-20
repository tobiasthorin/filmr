'use strict';

app.controller('confirmController', ['$scope', '$rootScope', '$routeParams', 'BookingService',
    function ($scope, $rootScope, $routeParams, BookingService) {

        $scope.bookingId = -1;
        $scope.cinemaName = "N/A";
        $scope.phoneNumber = "N/A";
        $scope.movieName = "N/A";
        
        $scope.fetchBooking = function () {

            BookingService.get({id: $routeParams.bookingId}).$promise.then(
                function (result) {
                    $scope.bookingId = result.id;
                    $scope.cinemaName = result.bookingCinemaName;
                    $scope.phoneNumber = result.phoneNumber;
                    $scope.movieName = result.bookingMovieTitle;
                },
                function () {
                    $rootScope.genericError();
                }
            )
        };

        //Run on page load
        $scope.fetchBooking();

    }]);