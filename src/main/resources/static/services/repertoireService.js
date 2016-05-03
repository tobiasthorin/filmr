/**
 *  (some) CRUD operations for repertoires
 */
angular.module('filmr').factory('RepertoireService', function($resource,$rootScope) {
	var moviesBaseUrl = $rootScope.API_baseUrl + "repertoires/";
	return $resource(moviesBaseUrl + ":id", { id: '@id' }, {
		update: {method: 'PUT'}
	});
// end of service		
});
