'use strict';

describe("cinemaController.js", function () {

    //Specify module
    beforeEach(module('filmr'));

    // Variables and parameters
    var fetchedTheater;
    var MockedTheaterService;
    var $scope;
    var $controller;

    var Mocked$routeParams = {
        'cinema_id': 1,
        'theater_id': 2,
        'width': 10,
        'depth': 7
    };

    //Mocked services
    beforeEach(function () {

        MockedTheaterService = {
            'get': function (params) {
                return {
                    '$promise': {
                        then: function (success, fail) {

                            fetchedTheater = {
                                id: params.id,
                                name: 'testTheater',
                                disabled: false,
                                cinema: {id: 1},
                                rows: [
                                    {
                                        seats: [
                                            {seatName: 1},
                                            {seatName: 2},
                                            {seatName: 3}

                                        ]
                                    },
                                    {
                                        seats: [
                                            {seatName: 4},
                                            {seatName: 5},
                                            {seatName: 6}

                                        ]
                                    },
                                    {
                                        seats: [
                                            {seatName: 7},
                                            {seatName: 8},
                                            {seatName: 9}

                                        ]
                                    },
                                    {
                                        seats: [
                                            {seatName: 7},
                                            {seatName: 8},
                                            {seatName: 9}

                                        ]
                                    }
                                ]
                            };
                            success(fetchedTheater);
                        }
                    }
                }
            },
            'update': function (params) {
                return {
                    '$promise': {
                        then: function (success, fail) {
                            if (params.id && params.name && params.numberOfSeats && params.cinema) {
                                success();
                            } else {
                                fail();
                            }
                        }
                    }
                }
            }
        };


    });

    //Injections, defining which mocked services to use instead of the real ones
    beforeEach(inject(function (_$controller_) {
        $controller = _$controller_;
        $scope = {};

        $controller('theaterController', {
            $scope: $scope, $routeParams: Mocked$routeParams,
            TheaterService: MockedTheaterService
        });
    }));

    /* Test specifications */

    it("Fetches the theater specified in the route", function () {
        if (Mocked$routeParams.theater_id) {
            expect(fetchedTheater.id).toEqual(Mocked$routeParams.theater_id);
        }
    });

    it("Sets the width and height variables", function () {
        if (Mocked$routeParams.theater_id) {
            expect($scope.theaterDepth).toEqual(fetchedTheater.rows.length);
            expect($scope.theaterWidth).toEqual(fetchedTheater.rows[0].seats.length);

        } else if (Mocked$routeParams.depth && Mocked$routeParams.width){
            console.log("No id");
            expect($scope.theaterDepth).toEqual(Mocked$routeParams.depth);
            expect($scope.theaterWidth).toEqual(Mocked$routeParams.width);
        } else {
            console.log("No length or width");
            expect($scope.theaterDepth).toEqual($scope.defaultDepth);
            expect($scope.theaterWidth).toEqual($scope.defaultWidth);
        }
    });

    it("Sets the scope variables to the content of the fetched theater", function () {
        expect($scope.name).toEqual(fetchedTheater.name);
        expect($scope.isDisabled).toEqual(fetchedTheater.disabled);
    });
});
