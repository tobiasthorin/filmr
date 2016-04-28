/**
 *  (some) CRUD operations for movies
 */
angular.module('filmr').factory('TheaterService', function($resource,$rootScope) {
	var theaterBaseUrl = $rootScope.API_baseUrl + "theaters/";
	return $resource(theaterBaseUrl + ":id", { id: '@id' }, {
		update: {method: 'PUT'}
	});
// end of service		
});
