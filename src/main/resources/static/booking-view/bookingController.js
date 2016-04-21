angular.module('filmr')
.controller('bookingController', ['$rootScope', '$scope', '$routeParams', 'BookingService', function($rootScope, $scope, $routeParams, BookingService) {

	$scope.sampleShowingsArray = [
	                         {id: 1, date: new Date(), theater: {name: "bergakungen 02"}, movie: {title:"Lion king", desc: "lions and stuff"}},
	                         {id: 2, date: new Date(), theater: {name: "bergakungen 01"}, movie: {title:"Lion queen", desc: "lions and stuff"}},
	                         {id: 3, date: new Date(), theater: {name: "bergakungen 05"}, movie: {title:"Lion prince", desc: "lions and stuff"}},
	                         {id: 4, date: new Date(), theater: {name: "bergakungen 11"}, movie: {title:"Lion princess", desc: "lions and stuff"}}
	                         ];
	
	$scope.relevantShowings = [];
	
	
	// run this function as soon as page/view loads
	getAllRelevantShowings();
	
	
	// functions on $scope object will be available to pages/templates (html) that that use this controller (see routing in app.js)
	$scope.updateAvailableShowings = function() {
		
		getAllRelevantShowings();
	}
	
	$scope.functionForBtnClick = function() {
		alert("clicked");
	}
	
	
	
	// "private" functions. not visible on $scope.  
	function getAllRelevantShowings() {
		
		console.log("fetching relevant showings based on selected options");
		
		// TODO: gather relevant variables to limit showings:  fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId
		
		BookingService.getAllRelevantShowings().then(
				// on success
				function(response) {
					
					var showingsArray = response.data;
					
					$scope.relevantShowings = showingsArray;
					
				}, // on error
				function(error) {
					console.log("error!", error);
				}
		);		
	}

}]);