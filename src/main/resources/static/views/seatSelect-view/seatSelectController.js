'use strict';

app.controller('seatSelectController', ['$scope', '$log', '$rootScope', '$routeParams', 'ShowingService', 'BookingService',
    function ($scope, $log, $rootScope, $routeParams, ShowingService, BookingService) {

        $scope.theaterRows = [];
        $scope.currentShowing = {};
        $scope.selectedSeats = new Set();
        $scope.bookedSeats = new Set;
        $scope.numberOfSelectedSeats = 0;

        $scope.fetchShowing = function () {
            ShowingService.get({id: $routeParams.showingId}).$promise.then(
                function (result) {
                    $scope.currentShowing = result;
                    $scope.theaterRows = result.theater.rows;

                    findBookedSeats();
                },
                function () {
                    //fail
                }
            )
        };

        $scope.toggleSelection = function (id) {

            if (!$scope.bookedSeats.has(id)) {

                if ($scope.selectedSeats.has(id)) {
                    unselectSeat(id);
                } else {
                    selectSeat(id);
                }

                updateNumberOfBookedSeats();

            }
        };

        $scope.checkIfSelected = function (id) {
            return $scope.selectedSeats.has(id);
        };

        $scope.checkIfBooked = function (id) {
            return $scope.bookedSeats.has(id);
        };

        $scope.submitBooking = function () {
            if ($scope.validateInputs()) {

                var params = {
                    for_showing_with_id: $scope.currentShowing.id
                };

                var body = {
                    bookedSeats: createSeatArray(),
                    phoneNumber: $scope.phoneNumber
                };

                BookingService.save(params, body).$promise.then(
                    function (result) {
                        $rootScope.alert("Thank you!", "Your booking is now confirmed.", 1);
                    },
                    function () {
                        $rootScope.genericError();
                    }
                );

                resetFields();
                $scope.fetchShowing();

            }
        };

        $scope.validateInputs = function() {
            $scope.phoneNumber = parseInt($scope.phoneNumber);

            if(typeof $scope.phoneNumber != "number" || !$scope.phoneNumber) return false;
            if($scope.numberOfSelectedSeats < 1) return false;

            return true;

        };

        function resetFields() {

            $scope.selectedSeats.clear();
            $scope.phoneNumber = "";
        }

        function selectSeat(id) {
            $scope.selectedSeats.add(id);
        }

        function unselectSeat(id) {
            $scope.selectedSeats.delete(id);
        }

        function findBookedSeats() {
            for (var i = 0; i < $scope.currentShowing.bookings.length; i++) {
                for (var j = 0; j < $scope.currentShowing.bookings[i].bookedSeats.length; j++) {
                    $scope.bookedSeats.add($scope.currentShowing.bookings[i].bookedSeats[j].id);
                }
            }
        }

        function updateNumberOfBookedSeats() {
            $scope.numberOfSelectedSeats = $scope.selectedSeats.size;
        }

        function createSeatArray() {
            var returnArray = [];
            var selectedSeats = [...$scope.selectedSeats];

            for (var i = 0; i < selectedSeats.length; i++) {
                returnArray.push({id: selectedSeats[i]});
            }

            return returnArray;
        }



        //Run on page load
        $scope.fetchShowing();
    }]);