angular.module('users', ['ngRoute']).config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'user-page.html',
        controller: 'userCtr'
    })
}).controller('userCtr', function ($scope, $http) {
    $http.get('users').success(function (data) {
    	//alert(data+"");
        $scope.userList = data;

        $scope.uploadFile = function(){
            var form = new FormData();
            var file = document.getElementById("fileUpload").files[0];
            form.append('file', file);
            $http({
                method: 'POST',
                url: '/upload',
                data: form,
                headers: {'Content-Type': undefined},
                transformRequest: angular.identity
            }).success(function (data) {
                console.log('upload success');
            }).error(function (data) {
                console.log('upload fail');
            })
        }
    });
});

