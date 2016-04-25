angular.module('filmr')
.controller('adminController', ['$rootScope', '$scope', '$routeParams', 'BookingService','TheaterService', function($rootScope, $scope, $routeParams, BookingService, TheaterService) {
	
	console.log("In admin");
	
	$scope.allTheaters = [];
	
	getTheaters();
	
	function getTheaters(){
		console.log("fetching all theaters");
		
		TheaterService.getAllTheaters().then(
				function(response){
					$scope.allTheaters = response.data;
				},
				function(error){
					console.log("error", error);
				}
			);
		
	}
	
}]);
