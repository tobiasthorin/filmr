angular.module('filmr')
    .controller('theaterController', ['$rootScope', '$scope', '$location', 'TheaterService',
        function ($rootScope, $scope, $location, TheaterService) {

            $scope.submitTheater = function () {
                console.log("Submitting theater...");

                console.log($scope.selectedName);
                console.log($scope.numberOfSeats);

                var newTheater = new TheaterService();
                console.log(newTheater);

                newTheater.name = $scope.selectedName;
                newTheater.numberOfSeats = $scope.numberOfSeats;
                newTheater.isDisabled = $scope.isDisabled;

                TheaterService.save(newTheater, function () {
                    console.log("Saved!");
                    $location.path('/cinema');
                });

            }

        }]);
