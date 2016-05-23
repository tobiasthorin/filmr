app.controller('customerController', ['$scope', 'CinemaService','$log','$rootScope',
	function ($scope, CinemaService, $log, $rootScope) {
		$log.info("In customer controller");

		$scope.fetchCinemas = function () {
			$log.info("Fetching cinemas");
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

		$scope.validateCinema = function() {
			if(!$scope.add_cinema_name) return false;
			if($scope.add_cinema_name.length>48) return;
			return true;
		};

		$scope.submitCinema = function () {

			if(!$scope.validateCinema()) {
				$rootScope.genericError();
				return;
			}

			$log.info("Saving...");

			if (!$scope.add_cinema_disabled) {
				$scope.add_cinema_disabled = false;
			}

			$scope.newCinema = {name: $scope.add_cinema_name, disabled:$scope.add_cinema_disabled};

			CinemaService.save($scope.newCinema).$promise.then(
				function () {
					$scope.fetchCinemas();
					$scope.resetFields();
					$rootScope.alert("Success! ","Cinema "+$scope.newCinema.name+" was created",1);
				},
				function(error){
					$rootScope.errorHandler(error);
				});


		};



		$scope.fetchCinemas();

	}]);
