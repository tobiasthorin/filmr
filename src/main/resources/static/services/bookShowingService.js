/**
 * Created by Marco on 2016-05-18.
 */
/**
 *  (some) CRUD operations for booking
 */
angular.module('filmr').factory('BookingService', ['$resource', '$rootScope',  function($resource,$rootScope) {
	var bookingsBaseUrl = $rootScope.API_baseUrl + "bookings/";
	return $resource(bookingsBaseUrl + ":id", { id : '@id' }, {
		update: {method: 'PUT'}
	});
// end of service
}]);
