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

			$scope.submitCinema = function (cinema) {
				console.log("Saving...");
				CinemaService.save(cinema).$promise.then(function () {
						$scope.logMessage = "Success!";
				},
				function () {

				});
			}

		}]);