describe("Tests for showingController.js", function () {

    beforeEach(module('filmr'));

    var $controller, $scope, MovieService, RepertoireService, CinemaService, ShowingService;

    //Mocks
    var createdShowing = {};
    var allCinemas = [
        {
            name: "cinema1",
            id:1,
            theaters: [{
                id:1,
                name: "grand hall"
            }],
            repertoire: {
                id: 99,
                movies: [{
                    id: 20,
                    name: "Tron"
                }]
            }
        }
    ];

    CinemaService = {
        "query": function () {
            return {
                "$promise": {
                    then: function (success, fail) {
                        success(allCinemas);
                    }
                }
            }
        },
        "get": function (params) {
            return {
                "$promise": {
                    then: function (success, fail) {
                        success(allCinemas[params.id])
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

    MockedScheduleService = {
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
        $scope = { };
        $controller(
            'showingController', {
                $scope: $scope,
                MovieService: MovieService,
                ScheduleService: MockedScheduleService,
                RepertoireService: RepertoireService,
                CinemaService: CinemaService,
                ShowingService: ShowingService
            });
    }));

    //TESTS

    it("Loads all cinemas", function () {
        expect($scope.allCinemas).toEqual(allCinemas);
    });

    it("Loads the theaters", function () {
        expect($scope.theatersInCinema).toEqual(allCinemas[0].theaters);
    });

    it("Loads the repertoire", function () {
        expect($scope.moviesInRepertoire).toEqual(allCinemas[0].repertoire.movies);
    });

});
