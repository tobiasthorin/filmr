angular.module('filmr')
	.controller('theaterController', ['$rootScope', '$scope', '$routeParams', '$location', 'TheaterService',
		function ($rootScope, $scope, $routeParams, $location, TheaterService) {
			console.log("Theater Controller");

			//Scoped functions

			$scope.redirect = function (path) {

			};

			$scope.fetchTheater = function () {
				console.log("Id " + $routeParams.theater_id + " specified.");

				TheaterService.get({id: $routeParams.theater_id}).$promise.then(
					//success
					function (result) {
						console.log("Successfully retrieved data: ");
						console.log(result);
						$scope.name = result.name;
						$scope.numberOfSeats = result.numberOfSeats;
						$scope.isDisabled = result.disabled;
					},
					//fail
					function (err) {
						$rootScope.errorHandler(err);
					});
			};

            $scope.validateScheduleEditTheater = function() {
                if(!$scope.numberOfSeats) return true;
                if($scope.numberOfSeats<1) return true;
                return false;
            }

			$scope.submitTheater = function () {
				console.log("Submitting edited theater...");
				console.log($scope.name + ", id: " + $routeParams.theater_id);
				
				$scope.newTheater = {};

				$scope.newTheater.id = $routeParams.theater_id;
				$scope.newTheater.name = $scope.name;
				$scope.newTheater.numberOfSeats = $scope.numberOfSeats;
				$scope.newTheater.disabled = $scope.isDisabled;
				$scope.newTheater.cinema = {id: $routeParams.cinema_id};

				TheaterService.update($scope.newTheater).$promise.then(
					function () {
						//success
						console.log("Updated!");
						$location.path('/cinema/' + $routeParams.cinema_id);
					},
					function () {
						//fail
					});
			};

			//Run on load
			if ($routeParams.theater_id == undefined) {
				console.log("No id specified");

			} else {
				$scope.fetchTheater();
			}

		}]);
