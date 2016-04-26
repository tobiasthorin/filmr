angular.module('filmr')
    .controller('bookingConfirmedController', ['$window','$rootScope', '$scope', '$routeParams', 'BookingService', function($window,$rootScope, $scope, $routeParams, BookingService) {

        $scope.showing=null;

        getCurrentShowingToBookIfSet();

        function getCurrentShowingToBookIfSet(){

        BookingService.getShowing($routeParams.id).then(
            function(response) {
                console.log("getShowing");
                $scope.showing = response.data;
            },
            $rootScope.errorHandler

        );

    }

}]);