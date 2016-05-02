app.controller('customerController', ['$scope', 'CinemaService',
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

		$scope.resetFields = function () {
			$scope.add_cinema_name = '';
			$scope.add_cinema_disabled = false;
		};

		$scope.submitCinema = function () {
			console.log("Saving...");
			if(!$scope.add_cinema_name) {
				$scope.alert("Error!");
				return;
			}

			if (!$scope.add_cinema_disabled) {
				$scope.add_cinema_disabled = false;
			}

			$scope.newCinema = {name: $scope.add_cinema_name, disabled:$scope.add_cinema_disabled};

			console.log($scope.newCinema);

			CinemaService.save($scope.newCinema).$promise.then(
				function () {
					$scope.alert("Success!");
					$scope.fetchCinemas();
					$scope.resetFields();
				},
				function () {
					$scope.alert("Error!");
				});


		};

		$scope.alert = function(message){
			//print message
		};



		$scope.fetchCinemas();

	}]);
