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


			if(typeof fromDate != "undefined") params.from_date = fromDate;
			if(typeof toDate != "undefined") params.to_date = toDate;
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
		
        //TODO: use a method like this so we do not rely on Date own to string method. We have better control if using our own to interact with backed and frontend MAY have different issues on it to string method
        function parseDateToApiDateStringFormat(date) {
           // throw "do not use this method that do not work yet";
            var month = date.getMonth();
            month++;
            month = "" + ( month<10 ? "0" : "") + month;

            var hours = date.getHours();
            hours = "" + ( hours<10 ? "0" : "") + hours;

            var r = ""+date.getFullYear()+"-"+month+"-"+date.getDate()+"T"+hours+":00";
            console.log(r);
            return r;
        }
		
		
// end of service		
}]);
