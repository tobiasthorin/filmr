angular.module('filmr')
.controller('adminController', ['$rootScope', '$scope', '$routeParams', 'BookingService','TheaterService','MovieService', function($rootScope, $scope, $routeParams, BookingService, TheaterService, MovieService) {
	
	console.log("In admin");
	
	$scope.allTheaters = [];
	$scope.allMovies = [];
	
	getTheaters();
	getMovies();
	
	$scope.submitShowing = function() {
		console.log($scope.selectedDate);
		var showing = {
				"showDateTime": $scope.selectedDate,
				"theater": $scope.selectedTheater,
				"movie": $scope.selectedMovie
				};
		//showing.movie.title = "Koopa";
		BookingService.createShowing(showing).then(
				function(response){
					console.log("Response",response.data);
					alert("Successfully created showing for movie " + response.data.movie.title);
					$scope.selectedDate = null;
					$scope.selectedTheater = null;
					$scope.selectedMovie = null;
				}, 
				function(error){
					console.log("error", error);
				}
		)
	}
	
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
	
	function getMovies(){
		console.log("fetching all movies");
		
		MovieService.getAllMovies().then(
				function(response){
					$scope.allMovies = response.data;
				},
				function(error){
					console.log("Error!", error);
				}
		);
	}
	
}]);
