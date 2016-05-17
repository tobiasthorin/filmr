'use strict';

angular.module('filmr')
    .controller('theaterController', ['$rootScope', '$scope', '$routeParams', '$location', 'TheaterService',
        function ($rootScope, $scope, $routeParams, $location, TheaterService) {

            //Scoped variables
            $scope.defaultWidth = 1;
            $scope.defaultDepth = 1;

            $scope.currentTheater = {};
            $scope.theaterRows = {};

            //Scoped functions

            $scope.fetchTheater = function () {
                console.log("Id " + $routeParams.theater_id + " specified.");

                TheaterService.get({id: $routeParams.theater_id}).$promise.then(
                    //success
                    function (result) {
                        $scope.original_name = result.name;
                        $scope.name = result.name;
                        $scope.currentTheater = result;
                        $scope.currentTheater.cinema = {id: $routeParams.cinema_id};

                        if (!result.rows[0]) {
                            $scope.theaterWidth = $scope.defaultWidth
                        } else {
                            $scope.theaterWidth = result.rows[0].seats.length;
                        }

                        if (!result.rows.length) {
                            $scope.theaterDepth = $scope.defaultDepth;
                        } else {
                            $scope.theaterDepth = result.rows.length;
                        }

                        updateRows();

                    },
                    //fail
                    function (err) {
                        $rootScope.errorHandler(err);
                    });
            };
            
            $scope.updateTheater = function () {

                $scope.currentTheater.name = $scope.name;

                TheaterService.update($scope.currentTheater).$promise.then(
                    function () {
                        //success
                        console.log("Updated!");
                        $location.path('/cinema/' + $routeParams.cinema_id);
                    },
                    function () {
                        //fail
                    });
            };

            $scope.newTheater = function () {
                console.log("New theater");
                if ($routeParams.width && $routeParams.depth) {
                    setWidthAndDepthFromParams();
                    createNewTheater();
                } else {
                    setDefaultWidthAndDepth();
                }
            }

	        $scope.toggleSeatState = function(seat){
				switch(seat.state){
					case "ENABLED":
						seat.state = "DISABLED";
						break;
					case "DISABLED":
						seat.state = "NOT_A_SEAT";
						break;
					case "NOT_A_SEAT":
						seat.state = "ENABLED";
						break;
				}

	        }

            function setWidthAndDepthFromParams() {
                $scope.theaterWidth = $routeParams.width;
                $scope.theaterDepth = $routeParams.depth;
            }

            function setDefaultWidthAndDepth() {
                $scope.theaterWidth = $scope.defaultWidth;
                $scope.theaterDepth = $scope.defaultDepth;
            }

            function createNewTheater() {
                var newTheater = {
                    name: $routeParams.name,
                    cinema: {id: $routeParams.cinema_id}
                };

                if (!!$routeParams.name) {
                    newTheater.name = $routeParams.name;
                } else {
                    newTheater.name = 'New Theater';
                }

                var theaterParams = {
                    number_of_rows: $scope.theaterDepth,
                    max_row_size: $scope.theaterWidth
                };

                TheaterService.save(theaterParams, newTheater).$promise.then(
                    function (result) {
                        $scope.currentTheater = result;
                        $scope.currentTheater.cinema = {id: $routeParams.cinema_id};
                        updateRows();
                    },
                    function (error) {
                        $rootScope.errorHandler(error)
                    });

                $scope.original_name = newTheater.name;
                $scope.name = newTheater.name;
            }

            function updateRows() {
                $scope.theaterRows = $scope.currentTheater.rows;
            }

            //Run on page load
            if ($routeParams.theater_id === 'new') {
                $scope.newTheater();
            } else {
                $scope.fetchTheater();
            }

        }]);
