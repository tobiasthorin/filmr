/**
 *  (some) CRUD operations for booking
 */

angular.module('filmr')
	.service('BookingService', ['$http','$rootScope', function($http, $rootScope) {

		
		var showingsBaseUrl = $rootScope.API_baseUrl + "showings/";
		
		this.getAllRelevantShowings = function(fromDate, toDate, mininum_available_tickets, only_for_movie_with_id,include_distinct_movies_in_header) {
			
			console.log("---");
			console.log("BookingService: getAllRelevantShowings");
			var params = Object();


			if(typeof fromDate != "undefined") params.fromDate = fromDate;
			if(typeof toDate != "undefined") params.toDate = toDate;
			if(typeof include_distinct_movies_in_header != "undefined") params.include_distinct_movies_in_header = include_distinct_movies_in_header;
			if(typeof mininum_available_tickets != "undefined") params.mininum_available_tickets = mininum_available_tickets;
			if(typeof only_for_movie_with_id != "undefined") params.only_for_movie_with_id = only_for_movie_with_id;

			console.log(showingsBaseUrl);
			console.log(params);
			return $http({ 
				"url": showingsBaseUrl,
				"params": params
			}); 
		};

		this.createShowing = function(showing){
			return $http.post(showingsBaseUrl, showing);
		}
		

		this.getShowing = function(id) {

			var url = showingsBaseUrl+id;
			console.log(url);
			return $http.get(url);
		}
		

		
		
// end of service		
}]);
