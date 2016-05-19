'use strict';

describe("Tests for seatSelectController.js", function () {

    beforeEach(module('filmr'));

    var $controller, $scope, $routeParams;

    //Mocks
    var fetchedShowing = {
        showDateTime: "2016-05-12T12:00",
        movie: {
            id: 1,
            title: "Lion King",
            description: "Story about lions",
            lengthInMinutes: 119,
            defaultPrice: 65
        },
        theater: {
            id:1,
            name: "Lasses Stora",
            rows:[
                {seats:[
                    {id: 1},
                    {id: 2}
                ]}
            ]
        }
    };

    var ShowingService = {
        "get": function (params) {
            return {
                "$promise": {
                    then: function (success, fail) {
                        fetchedShowing.id = params.id;
                        success(fetchedShowing);
                    }
                }
            }
        }
    };

    //Injections
    beforeEach(inject(function (_$controller_, _$routeParams_) {
        $controller = _$controller_;
        $scope = { };
        $routeParams = _$routeParams_;
        $controller(
            'seatSelectController', {
                $scope: $scope,
                $routeParams: $routeParams,
                ShowingService: ShowingService
            });
    }));

    //TESTS

    describe("Getting and setting variables", function () {

        it("Fetches the showing from the backend", function () {
            expect($scope.currentShowing).toEqual(fetchedShowing)
        });

        it("References the rows in a separate variable", function () {
            expect($scope.theaterRows).toEqual(fetchedShowing.theater.rows)
        });
    });

    describe("Selecting seats", function () {

        it("Puts a seat in the selected-seat-list when it is clicked", function () {
            $scope.toggleSelection(1);
            expect($scope.selectedSeats).toContain(1);
        });
    });



});
