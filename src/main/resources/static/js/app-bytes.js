//angular.module('byte-app', [])
//    .controller('bytes', function($scope) {
//        $scope.file = {id: 'xxx', read: 'Hello World'};
//
//        $scope.name = 'namenamename';
//
//})


var app = angular.module('byte-app', []);

app.controller('bytes', function($scope, $http) {

    $scope.searchAndReplace=function(){
        $http({
            method: 'POST',
            url: '/search',
            data: {"pathToFile":$scope.pathToFile, "fileExtension":$scope.fileExtension, "inputBytes":$scope.inputBytes,
                    "outputBytes":$scope.outputBytes},
            headers: {'Content-Type':'application/json'}
        })
    };



    $scope.file = {id: 'xxx', read: 'Hello World'};

    $scope.name = 'namenamename';




});