/**
 * Crus operations for Showings
 */
angular.module('filmr').factory('ShowingService', function($resource, $rootScope){
    var showingBaseUrl = $rootScope.API_baseUrl + "showings/";
    return $resource(showingBaseUrl+":id", { id: '@id' }, {
        update: {method: 'PUT'}
    });
    //End of service
});