/**
 * @author hsingh36
 */
(function() {

    var controllers = angular.module('bgms.controllers', []);

    controllers.controller('homeCtrl',[ '$scope', '$log', function($scope, $log) {
        $log.log("Loading..");

        $scope.contractDetailsField = [
            { name: 'contractId', displayName: 'Contract Id', isSortable: 'true' },
            { name: 'validTo', displayName: 'Valid To', isSortable: 'true' },
            { name: 'extendedTo', displayName: 'Extended To', isSortable: 'true' },
            { name: 'daysLeft', displayName: 'Days Left', isSortable: 'false' },
            { name: 'random1', displayName: 'random1', isSortable: 'false' },
            { name: 'random2', displayName: 'random2', isSortable: 'false' },
            { name: 'contractorName', displayName: 'Contractor Name', isSortable: 'false' },
            { name: 'bankName', displayName: 'Bank Name', isSortable: 'false' },
            { name: 'ifscCode', displayName: 'IFSC Code', isSortable: 'false' },
            { name: 'guarantee', displayName: 'Guarantee', isSortable: 'false' },
            { name: 'validFrom', displayName: 'Valid From', isSortable: 'false' }
        ];

        $scope.contractDetails = [
            {
                id: 1,
                value: '122431',
                validTo: '22/2/2015',
                extendedTo: '01/10/2017',
                daysLeft:   12,
                random1: 'link1',
                random2: 'link2',
                contractorName: 'Satish kumar agraval',
                bankName:   'State Bank of india',
                ifcsCode:   'SBI00002321',
                guarantee:  '12112312',
                validFrom:  '12/04/2012'
            },
            {
                id: 2,
                value: '3242334',
                validTo: '2/12/2013',
                extendedTo: '25/6/2017',
                daysLeft:   12,
                random1: 'link1',
                random2: 'link2',
                contractorName: 'Satish kumar agraval mahaveer agrasen district kanpur',
                bankName:   'State Bank of india',
                ifcsCode:   'SBI00002321',
                guarantee:  '12112312',
                validFrom:  '12/04/2012'
            }
        ];
    }]);

    controllers.controller('accessDeniedCtrl',[ '$scope', '$log', function($scope, $log) {
        $log.log("Loading Access Denied..");
    }]);

    controllers.controller('addEditBankCtrl',[ '$scope', '$log', function($scope, $log) {
        $log.log("Loading Access Denied..");
    }]);

})();