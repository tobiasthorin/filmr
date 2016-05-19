/**
 * Created by Marco on 2016-05-18.
 */
/**
 *  (some) CRUD operations for booking
 */
angular.module('filmr').factory('BookingService', function($resource,$rootScope) {
	var bookingsBaseUrl = $rootScope.API_baseUrl + "bookings/";
	return $resource(bookings + ":id", { id : '@id' }, {
		update: {method: 'PUT'}
	});
// end of service
});