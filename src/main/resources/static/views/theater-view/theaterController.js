angular.module('filmr')
    .controller('theaterController', ['$rootScope', '$scope', '$routeParams', '$location', 'TheaterService',
        function ($rootScope, $scope, $routeParams, $location, TheaterService) {
            console.log("Theater Controller");

            if ($routeParams.id == undefined) {
                console.log("No id specified");

                $scope.submitTheater = function () {
                    console.log("Submitting new theater...");
                    console.log($scope.name);

                    var newTheater = new TheaterService();

                    newTheater.name = $scope.name;
                    newTheater.numberOfSeats = $scope.numberOfSeats;
                    newTheater.isDisabled = $scope.isDisabled;

                    TheaterService.save(newTheater, function () {
                        console.log("Saved!");
                        $location.path('/cinema');
                    });

                }

            } else {
                console.log("Id " + $routeParams.id + " specified.");

				TheaterService.get({id: $routeParams.id}).$promise.then(
					//Get success
					function (result) {
						console.log("Successfully retrieved data: ");
						console.log(result);
						$scope.name = result.name;
						$scope.numberOfSeats = result.numberOfSeats;
						$scope.isDisabled = result.disabled;
					},
					//Get error
					function (err) {
						$rootScope.errorHandler(err);
					});

                $scope.submitTheater = function () {
                    console.log("Submitting edited theater...");
                    console.log($scope.name + ", id: " + $routeParams.id);

                    var newTheater = new TheaterService();

                    newTheater.id = $routeParams.id;
                    newTheater.name = $scope.name;
                    newTheater.numberOfSeats = $scope.numberOfSeats;
                    newTheater.disabled = $scope.isDisabled;

                    TheaterService.update(newTheater, function () {
                        console.log("Updated!");
                        $location.path('/cinema');
                    });

                }
            }

        }]);