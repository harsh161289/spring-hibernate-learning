/**
 * @author hsingh36
 */
(function () {

    var myApp = angular.module("bgmsApp", ['bgms.controllers', 'bgms.services', 'ngRoute', 'ui.bootstrap']);

    myApp.config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when("/", {
                controller: "homeCtrl",
                templateUrl: "/resources/partials/home.html"
            })
            .when("contractRegistration", {
                controller: "addContractCtrl",
                templateUrl: "/resources/partials/addContract.html"
            })
            .when("bankRegistration", {
                controller: "addEditBankCtrl",
                templateUrl: "/resources/partials/bankDetails.html"
            })
            .otherwise('/access-denied', {
                controller: "accessDeniedCtrl",
                templateUrl: "/resources/partials/access-denied.html"
            });

    }]);

})();