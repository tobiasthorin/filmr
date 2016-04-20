/**
 *  (some) CRUD operations for booking
 */

angular.module('filmr')
	.service('BookingService', ['$http','$rootScope', function($http, $rootScope) {

		
		var showingsBaseUrl = $rootScope.API_baseUrl + "showings/";
		
		this.getAllRelevantShowings = function(fromDate, toDate, mininumAvailableTickets, onlyForMovieWithId) {
			
			// set default values if all values where not provided
			var fromDate = fromDate || (new Date());
			var toDate = toDate; // TODO: add some time from today, 2-3 weeks?
			var mininumAvailableTickets = mininumAvailableTickets || 2;
			var onlyForMovieWithId = onlyForMovieWithId || -999;
			
			
			return $http.get(showingsBaseUrl); // 
		};
		
		
		
		
		
		
		
// end of service		
}]);