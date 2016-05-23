'use strict';

angular.module('filmr')
    .controller('theaterController', ['$rootScope', '$scope', '$routeParams', '$location', 'TheaterService',
        function ($rootScope, $scope, $routeParams, $location, TheaterService) {
			var activeRequest = false;
            var resetSeatNumbers = false;

            //Scoped variables
            $scope.defaultWidth = 1;
            $scope.defaultDepth = 1;

            $scope.currentTheater = {};
            $scope.theaterRows = {};
            
            $scope.rowReset;

            //Scoped functions

            $scope.fetchTheater = function () {
                $log.info("Id " + $routeParams.theater_id + " specified.");

                TheaterService.get({id: $routeParams.theater_id}).$promise.then(
                    //success
                    function (result) {
                        $scope.original_name = result.name;
                        $scope.name = result.name;
                        $scope.currentTheater = result;
                        $scope.currentTheater.cinema = {id: result.cinemaId};
                        $scope.rowReset = !result.usingContinuousSeatLabeling;

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

            $scope.validateTheater = function() {
                if(!$scope.validateNameInput()) {
                    return false;
                }

                if(!$scope.validateRowInput()) {
                    return false;
                }

                if(!$scope.validateSeatInput()) {
                    return false;
                }

                return true;
            };

            $scope.setTheaterName = function () {
                $scope.name = $scope.original_name;
                $scope.updateTheater();
            }
            $scope.updateTheater = function () {

                $rootScope.clearAlerts();

                if(!$scope.validateTheater()) {
                    $rootScope.genericError();
                    return;
                }

                if(!activeRequest){

					$scope.currentTheater.name = $scope.name;
                    $scope.currentTheater.

					activeRequest = true;

                    var updateParams = {
                        new_number_of_rows: $scope.theaterDepth,
                        new_max_row_size: $scope.theaterWidth,
                        reset_seat_numbers_for_each_row: resetSeatNumbers
                    };

					TheaterService.update(updateParams,$scope.currentTheater).$promise.then(
						function (result) {
							//success
							activeRequest = false;

							$scope.currentTheater = result;
							$scope.currentTheater.cinema = {id: result.cinemaId};
                            $scope.original_name = result.name;
							updateRows();
                            $rootScope.alert("Success! ","Theater was updated",1);
						},
						function (error) {
                            //fail
							activeRequest = false;
							$rootScope.errorHandler(error);
						});
				}

            };

	        $scope.toggleSeatState = function(seat){
                if (!activeRequest) {
                    activeRequest = true;
                    switch (seat.state) {
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
                    activeRequest = false;
                    $scope.updateTheater();
                }
	        };

	        $scope.addRow = function (){

                $scope.theaterDepth++;

                if(!$scope.validateTheater()) {
                    $rootScope.genericError();
                    $scope.theaterDepth--;
                    return;
                }
                $scope.updateTheater();
	        };

	        $scope.removeRow = function (){

                $scope.theaterDepth--;

                if(!$scope.validateTheater()) {
                    $rootScope.genericError();
                    $scope.theaterDepth++;
                    return;
                };
		        $scope.updateTheater();
	        };

	        $scope.addSeats = function() {

                $scope.theaterWidth++;

                if(!$scope.validateTheater()) {
                    $rootScope.genericError();
                    $scope.theaterWidth--;
                    return;
                }
                $scope.updateTheater();
	        };

	        $scope.removeSeats = function(){

                $scope.theaterWidth--;

                if(!$scope.validateTheater()) {
                    $rootScope.genericError();
                    $scope.theaterWidth++;
                    return;
                }
    	        $scope.updateTheater();
	        };

            $scope.validateNameInput = function() {
                if(typeof $scope.original_name != "string") return false;
                if(!($scope.original_name.length>0 && $scope.original_name.length<=48)) return false;
                //if($scope.name === $scope.original_name) return false;
                return true;

            };

            $scope.validateRowInput = function (offset) {

                offset = typeof $scope.theaterDepth == "undefined" ? 0 : offset;

                if(!(typeof $scope.theaterDepth == "number")) return false;
                if(typeof $scope.theaterDepth == "number" && $scope.theaterDepth+offset<=0) return false;
                if(typeof $scope.theaterDepth == "number" && $scope.theaterDepth+offset>64) return false;
                return true;
            };

            $scope.validateSeatInput = function (offset) {

                offset = typeof $scope.theaterDepth == "undefined" ? 0 : offset;

                if(!(typeof $scope.theaterWidth == "number")) return false;
                if(typeof $scope.theaterWidth == "number" && $scope.theaterWidth+offset<=0) return false;
                if(typeof $scope.theaterWidth == "number" && $scope.theaterWidth+offset>64) return false;
                return true;
            };

            $scope.setRowReset = function (value) {
                resetSeatNumbers = value;
                $scope.updateTheater();
            };

            function setWidthAndDepthFromParams() {
                $scope.theaterWidth = $routeParams.width;
                $scope.theaterDepth = $routeParams.depth;
            }

            function setDefaultWidthAndDepth() {
                $scope.theaterWidth = $scope.defaultWidth;
                $scope.theaterDepth = $scope.defaultDepth;
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
