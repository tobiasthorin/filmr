angular.module('filmr', [])
	.controller('customerController', ['$scope', 'CinemaService',
		function ($scope, CinemaService) {
			console.log('In Customer Controller');

			$scope.fetchCinemas = function () {
				console.log("Fetching cinemas");
				CinemaService.query().$promise.then(function (result) {
						$scope.cinemas = result;
					},
					function () {

					});
			};

			$scope.submitCinema = function () {
				console.log("Saving...");

				if(!$scope.name) {
					$scope.alert("Error!");
					return;
				}

				$scope.newCinema = {name: $scope.name, disabled:$scope.disabled};

				CinemaService.save($scope.newCinema).$promise.then(
					function () {
						$scope.alert("Success!");
					},
					function () {
						$scope.alert("Error!");
					});
			};

			$scope.alert = function(message){
				//print message
			};

		}]);