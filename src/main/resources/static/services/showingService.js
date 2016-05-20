/**
 * Crus operations for Showings
 */
angular.module('filmr').factory('ShowingService', function($resource, $rootScope){
    var showingBaseUrl = $rootScope.API_baseUrl + "showings/";
    return $resource(showingBaseUrl+":id", { id: '@id' }, {
        update: {method: 'PUT'},
        query: {method: 'GET', isArray: false}
        
    });

    //End of service
});

angular.module('filmr').factory('ScheduleService', function($resource, $rootScope) {
	var showingBaseUrl = $rootScope.API_baseUrl + "showings/schedule";
	return $resource(showingBaseUrl + ":id", {id: '@id'}, {
		query: {method: 'GET', isArray: false}

	});

});