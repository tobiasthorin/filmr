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

                var existingTheater = TheaterService.get({id:$routeParams.id}, function(){
                    console.log(existingTheater);
                    $scope.name = existingTheater.name;
                    $scope.numberOfSeats = existingTheater.seats;
                    $scope.isDisabled = existingTheater.disabled;
                });

                $scope.submitTheater = function () {
                    console.log("Submitting edited theater...");
                    console.log($scope.name);

                    var newTheater = new TheaterService();

                    //newTheater.id = $routeParams.id;
                    newTheater.name = $scope.name;
                    newTheater.numberOfSeats = $scope.numberOfSeats;
                    newTheater.isDisabled = $scope.isDisabled;

                    TheaterService.save(newTheater, function () {
                        console.log("Saved!");
                        $location.path('/cinema');
                    });

                }
            }

        }]);