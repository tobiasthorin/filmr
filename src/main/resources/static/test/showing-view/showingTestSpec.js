describe("Tests for showingController.js", function () {

    beforeEach(module('filmr'));

    var $controller, $scope, MovieService, RepertoireService, CinemaService, ShowingService;

    //Mocks
    CinemaService = {
        "query": function () {
            return {
                "$promise": {
                    then: function (success, fail) {

                    }
                }
            }
        },
        "get": function (params) {
            return {
                "$promise": {
                    then: function (success, fail) {

                    }
                }
            }
        }
    };

    ShowingService = {
        "query": function () {
            return {
                "$promise": {
                    then: function (success, fail) {

                    }
                }
            }
        }
    };


    //Injections
    beforeEach(inject(function (_$controller_) {
        $controller = _$controller_;
        $scope = {};
        $controller(
            'showingController', {
                $scope: $scope,
                MovieService: MovieService,
                RepertoireService: RepertoireService,
                CinemaService: CinemaService,
                ShowingService: ShowingService
            });
    }));

    //TESTS

    it("Fails", function () {
        fail("yes");
    });
});
