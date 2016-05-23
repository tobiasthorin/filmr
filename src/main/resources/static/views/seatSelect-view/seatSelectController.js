'use strict';

var trailerUrl;

app.controller('seatSelectController', ['$scope', '$log', '$rootScope', '$routeParams', 'ShowingService', 'BookingService',
    function ($scope, $log, $rootScope, $routeParams, ShowingService, BookingService) {

        var activeRequest = false;
        $scope.theaterRows = [];
        $scope.currentShowing = {};
        $scope.selectedSeats = new Set();
        $scope.bookedSeats = new Set;
        $scope.numberOfSelectedSeats = 0;

        $scope.fetchShowing = function () {
            if (!activeRequest) {
                activeRequest = true;
                ShowingService.get({id: $routeParams.showingId}).$promise.then(
                    function (result) {
                        $scope.currentShowing = result;
                        $scope.theaterRows = result.theater.rows;
                        trailerUrl = result.movie.trailerUrl;
                        onYouTubeIframeAPIReady();

                        findBookedSeats();
                        loadYouTubePlayer()
                    },
                    function () {
                        //fail
                    }
                )
                activeRequest = false;
            }
        };

        $scope.toggleSelection = function (id) {
            //Make sure seat hasnt been taken while user is booking
            if (!activeRequest) {
                activeRequest = true;
                console.log("trying to update"); //TODO must wait for server
                findBookedSeats( function () {
                    if (!$scope.bookedSeats.has(id)) {

                        if ($scope.selectedSeats.has(id)) {
                            unselectSeat(id);
                        } else {
                            selectSeat(id);
                        }
                        updateNumberOfBookedSeats();
                        activeRequest = false;
                    }
                    else {
                        activeRequest = false;
                    }
                })
            }
        };

        $scope.checkIfSelected = function (id) {
            return $scope.selectedSeats.has(id);
        };

        $scope.checkIfBooked = function (id) {
            return $scope.bookedSeats.has(id);
        };

        $scope.submitBooking = function () {
            if (!activeRequest) {
                activeRequest = true;
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
                            window.location.assign("#/book/showing/" + $scope.currentShowing.id + "/confirm/" + result.id);
                            activeRequest = false;
                        },
                        function () {
                            $rootScope.genericError();
                            activeRequest = false;
                        }
                    );

                }
            }
        };

        $scope.validateInputs = function () {
            $scope.phoneNumber = parseInt($scope.phoneNumber);

            if (typeof $scope.phoneNumber != "number" || !$scope.phoneNumber) return false;
            if ($scope.numberOfSelectedSeats < 1) return false;

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

        function findBookedSeats(callback) {
            for (var i = 0; i < $scope.currentShowing.bookings.length; i++) {
                for (var j = 0; j < $scope.currentShowing.bookings[i].bookedSeats.length; j++) {
                    $scope.bookedSeats.add($scope.currentShowing.bookings[i].bookedSeats[j].id);
                }
            }
            callback();
        }

        function updateNumberOfBookedSeats() {
            $scope.numberOfSelectedSeats = $scope.selectedSeats.size;
            activeRequest = false;
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

var tag = document.createElement('script');

tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

var player;
function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        height: '390',
        width: '640',
        videoId: trailerUrl,
        events: {
        }
    });
}