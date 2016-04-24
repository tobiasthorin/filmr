angular.module('filmr')
.controller('bookingController', ['$rootScope', '$scope', '$routeParams', 'BookingService', function($rootScope, $scope, $routeParams, BookingService) {

	$scope.sampleShowingsArray = [
	                         {id: 1, date: new Date(), theater: {name: "bergakungen 02"}, movie: {title:"Lion king", desc: "lions and stuff"}},
	                         {id: 2, date: new Date(), theater: {name: "bergakungen 01"}, movie: {title:"Lion queen", desc: "lions and stuff"}},
	                         {id: 3, date: new Date(), theater: {name: "bergakungen 05"}, movie: {title:"Lion prince", desc: "lions and stuff"}},
	                         {id: 4, date: new Date(), theater: {name: "bergakungen 11"}, movie: {title:"Lion princess", desc: "lions and stuff"}}
	                         ];
	
	$scope.relevantShowings = [];
	$scope.movies = [];
	
	// run this function as soon as page/view loads
	getAllRelevantShowings(null,null,null,null);
	
	
	// functions on $scope object will be available to pages/templates (html) that that use this controller (see routing in app.js)
	$scope.updateAvailableShowings = function(onlyForMovieWithId) {
		getAllRelevantShowings(null,null,null,onlyForMovieWithId);
	}
	
	$scope.functionForBtnClick = function() {
		alert("clicked");
	}
	

	$scope.cinemas = Array(1,2,3,4,5);
	$scope.seatlimits = Array(1,2,3,4,5,6,7,8,9,10);


	// COPY-PASTED FROM STACKOVERFLOW (almost)
	function contains(a, obj) {
	    for (var i = 0; i < a.length; i++) {
		if (JSON.stringify(a[i]) == JSON.stringify(obj)) {
		    return true;
		}
	    }
	    return false;
	}
	// ---

	
	// "private" functions. not visible on $scope.  
	function getAllRelevantShowings(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId) {
		
		console.log("fetching relevant showings based on selected options");
		
		// TODO: gather relevant variables to limit showings:  fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId
		
		BookingService.getAllRelevantShowings(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId).then(
				// on success
				function(response) {
					
					// new meta data in response
					var distinctMovieArray = JSON.parse(response.headers().distinct_movies);
					console.log("distinct movies", distinctMovieArray);
					
					var showingsArray = response.data;

					$scope.movies = [];
					for(i=0; i<showingsArray.length; i++) {
						var movie = showingsArray[i].movie;
						if(!contains($scope.movies,movie))
							$scope.movies.push(movie);
					}
					$scope.relevantShowings = showingsArray;
					
				}, // on error
				function(error) {
					console.log("error!", error);
				}
		);		
	}

}]);
