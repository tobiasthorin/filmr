angular.module('filmr').factory('BookingService', 
		['$resource', '$rootScope', function($resource,$rootScope) {
	var theaterBaseUrl = $rootScope.API_baseUrl + "bookings/";
	return $resource(theaterBaseUrl + ":id", { id: '@id' }, {
		update: {method: 'PUT'}
	});
}]);